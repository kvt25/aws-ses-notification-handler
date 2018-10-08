package com.kienvo.aws.notification;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kienvo.aws.notification.data.AbstractSNSNotificationJSONData;
import com.kienvo.aws.notification.data.SNSNotificationBounceObjectData;
import com.kienvo.aws.notification.data.SNSNotificationComplaintObjectData;
import com.kienvo.aws.notification.data.SNSNotificationMessageData;
import com.kienvo.aws.notification.data.SNSNotificationRecipientObjectData;


/**
 * Controller
 */
@Controller
@RequestMapping("/aws/notification")
public class HandleAWSNotificationsController
{
	private static final Logger LOG = Logger.getLogger(HandleAWSNotificationsController.class);

	private static final String AWS_SNS_MESSAGE_HEADER = "x-amz-sns-message-type";
	private static final String AWS_SNS_MESSAGE_TYPE_NOTIFICATION = "Notification";
	private static final String AWS_SNS_MESSAGE_TYPE_SUBCRIPTION_CONFIRMATION = "SubscriptionConfirmation";
	private static final String AWS_SNS_MESSAGE_TYPE_UNSUBCRIPTION_CONFIRMATION = "UnsubscribeConfirmation";
	private static final String AWS_SNS_NOTIFICATION_TYPE_BOUNCE = "Bounce";
	private static final String AWS_SNS_NOTIFICATION_TYPE_COMPLAINT = "Complaint";
	private static final String AWS_SIGNATURE_VERSION = "1";

	@RequestMapping(method = RequestMethod.POST)
	public void process(final HttpServletRequest request, final HttpServletResponse response) throws IOException
	{
		LOG.info("Begin handling notification from Amazon SNS");
		//Get the message type header.
		final String messagetype = request.getHeader(AWS_SNS_MESSAGE_HEADER);
		//If message doesn't have the message type header, don't process it.
		if (messagetype == null)
		{
			return;
		}

		final Scanner scan = new Scanner(request.getInputStream());
		final StringBuilder builder = new StringBuilder();
		while (scan.hasNextLine())
		{
			builder.append(scan.nextLine());
		}

		final ObjectMapper objectMapper = new ObjectMapper();
		final AbstractSNSNotificationJSONData messageData = objectMapper.readValue(builder.toString(), AbstractSNSNotificationJSONData.class);

		// The signature is based on SignatureVersion 1.
		// If the signature version is something other than 1, throw an exception.
		if (AWS_SIGNATURE_VERSION.equals(messageData.getSignatureVersion()))
		{
			if (isMessageSignatureValid(messageData))
			{
				LOG.info(">>Signature verification succeeded");
			}
			else
			{
				LOG.info(">>Signature verification failed");
				scan.close();
				throw new SecurityException("Signature verification failed.");
			}
		}
		else
		{
			LOG.info(">>Unexpected signature version. Unable to verify signature.");
			scan.close();
			throw new SecurityException("Unexpected signature version. Unable to verify signature.");
		}

		// Process the message based on type.
		if (AWS_SNS_MESSAGE_TYPE_NOTIFICATION.equals(messagetype))
		{
			final SNSNotificationMessageData notificationMessage = objectMapper.readValue(messageData.getMessage(),
					SNSNotificationMessageData.class);
			if (notificationMessage != null)
			{
				handleNotificationMessage(notificationMessage);
			}
		}
		else if (AWS_SNS_MESSAGE_TYPE_SUBCRIPTION_CONFIRMATION.equals(messagetype))
		{
			//Confirm the subscription by going to the subscribeURL location
			//and capture the return value (XML message body as a string)
			final Scanner sc = new Scanner(new URL(messageData.getSubscribeURL()).openStream());
			final StringBuilder sb = new StringBuilder();
			while (sc.hasNextLine())
			{
				sb.append(sc.nextLine());
			}
			LOG.info(sb.toString());
			sc.close();
		}
		else
		{
			LOG.warn(">>Unknown message type: " + messagetype);
		}

		scan.close();
		LOG.info("End handling notification from Amazon SNS: " + messageData.getMessageId());
	}

	private boolean isMessageSignatureValid(final AbstractSNSNotificationJSONData messageData)
	{
		try
		{
			final URL url = new URL(messageData.getSigningCertURL());
			final InputStream inStream = url.openStream();
			final CertificateFactory cf = CertificateFactory.getInstance("X.509");
			final X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
			inStream.close();

			final Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initVerify(cert.getPublicKey());
			sig.update(getMessageBytesToSign(messageData));
			return sig.verify(Base64.decodeBase64(messageData.getSignature()));
		}
		catch (final Exception e)
		{
			throw new SecurityException("Verify method failed.", e);
		}
	}

	private byte[] getMessageBytesToSign(final AbstractSNSNotificationJSONData messageData)
	{
		byte[] bytesToSign = null;
		if (AWS_SNS_MESSAGE_TYPE_NOTIFICATION.equals(messageData.getType()))
		{
			bytesToSign = buildNotificationStringToSign(messageData).getBytes();
		}
		else if (AWS_SNS_MESSAGE_TYPE_SUBCRIPTION_CONFIRMATION.equals(messageData.getType())
				|| AWS_SNS_MESSAGE_TYPE_UNSUBCRIPTION_CONFIRMATION.equals(messageData.getType()))
		{
			bytesToSign = buildSubscriptionStringToSign(messageData).getBytes();
		}
		return bytesToSign;
	}

	private String buildSubscriptionStringToSign(final AbstractSNSNotificationJSONData messageData)
	{
		final StringBuilder stringToSign = new StringBuilder();
		//Build the string to sign from the values in the message.
		//Name and values separated by newline characters
		//The name value pairs are sorted by name in byte sort order.
		stringToSign.append("Message\n");
		stringToSign.append(messageData.getMessage());
		stringToSign.append("\n");
		stringToSign.append("MessageId\n");
		stringToSign.append(messageData.getMessageId());
		stringToSign.append("\n");
		stringToSign.append("SubscribeURL\n");
		stringToSign.append(messageData.getSubscribeURL());
		stringToSign.append("\n");
		stringToSign.append("Timestamp\n");
		stringToSign.append(messageData.getTimestamp());
		stringToSign.append("\n");
		stringToSign.append("Token\n");
		stringToSign.append(messageData.getToken());
		stringToSign.append("\n");
		stringToSign.append("TopicArn\n");
		stringToSign.append(messageData.getTopicArn());
		stringToSign.append("\n");
		stringToSign.append("Type\n");
		stringToSign.append(messageData.getType());
		stringToSign.append("\n");

		return stringToSign.toString();
	}

	private String buildNotificationStringToSign(final AbstractSNSNotificationJSONData messageData)
	{
		final StringBuilder stringToSign = new StringBuilder();

		//Build the string to sign from the values in the message.
		//Name and values separated by newline characters
		//The name value pairs are sorted by name in byte sort order.
		stringToSign.append("Message\n");
		stringToSign.append(messageData.getMessage());
		stringToSign.append("\n");
		stringToSign.append("MessageId\n");
		stringToSign.append(messageData.getMessageId());
		stringToSign.append("\n");
		if (messageData.getSubject() != null)
		{
			stringToSign.append("Subject\n");
			stringToSign.append(messageData.getSubject());
			stringToSign.append("\n");
		}
		stringToSign.append("Timestamp\n");
		stringToSign.append(messageData.getTimestamp());
		stringToSign.append("\n");
		stringToSign.append("TopicArn\n");
		stringToSign.append(messageData.getTopicArn());
		stringToSign.append("\n");
		stringToSign.append("Type\n");
		stringToSign.append(messageData.getType());
		stringToSign.append("\n");

		return stringToSign.toString();
	}

	private void handleNotificationMessage(final SNSNotificationMessageData notification)
	{
		if (AWS_SNS_NOTIFICATION_TYPE_BOUNCE.equals(notification.getNotificationType()))
		{
			handleBounceNotification(notification.getBounce());
		}
		else if (AWS_SNS_NOTIFICATION_TYPE_COMPLAINT.equals(notification.getNotificationType()))
		{
			handleComplaintNotification(notification.getComplaint());
		}
	}

	private void handleBounceNotification(final SNSNotificationBounceObjectData bounce)
	{
		if ("Permanent".equals(bounce.getBounceType()))
		{
			final List<SNSNotificationRecipientObjectData> bouncedRecipients = bounce
					.getBouncedRecipients();
			if (CollectionUtils.isNotEmpty(bouncedRecipients))
			{
				for (final SNSNotificationRecipientObjectData recipient : bouncedRecipients)
				{
					LOG.info("To do with email address in bounce: " + recipient.getEmailAddress());
				}
			}
		}
	}

	private void handleComplaintNotification(final SNSNotificationComplaintObjectData complaint)
	{
		LOG.info("Just log complaint email address, not put into blacklist: " + complaint.toString());
	}
}
