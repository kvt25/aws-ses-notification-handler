package com.kienvo.aws.notification.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Abstract data object to map with Amazon Simple Notification Message (SNS) JSON
 * @author kien.vo
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractSNSNotificationJSONData
{
	@JsonProperty("Type")
	private String type;

	@JsonProperty("MessageId")
	private String messageId;

	@JsonProperty("Token")
	private String token;

	@JsonProperty("TopicArn")
	private String topicArn;

	@JsonProperty("Subject")
	private String subject;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("Timestamp")
	private String timestamp;

	@JsonProperty("SignatureVersion")
	private String signatureVersion;

	@JsonProperty("Signature")
	private String signature;

	@JsonProperty("SigningCertURL")
	private String signingCertURL;

	@JsonProperty("SubscribeURL")
	private String subscribeURL;

	@JsonProperty("UnsubscribeURL")
	private String unsubscribeURL;

	public String getType()
	{
		return type;
	}

	public void setType(final String type)
	{
		this.type = type;
	}

	public String getMessageId()
	{
		return messageId;
	}

	public void setMessageId(final String messageId)
	{
		this.messageId = messageId;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(final String token)
	{
		this.token = token;
	}

	public String getTopicArn()
	{
		return topicArn;
	}

	public void setTopicArn(final String topicArn)
	{
		this.topicArn = topicArn;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(final String subject)
	{
		this.subject = subject;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(final String message)
	{
		this.message = message;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(final String timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getSignatureVersion()
	{
		return signatureVersion;
	}

	public void setSignatureVersion(final String signatureVersion)
	{
		this.signatureVersion = signatureVersion;
	}

	public String getSignature()
	{
		return signature;
	}

	public void setSignature(final String signature)
	{
		this.signature = signature;
	}

	public String getSigningCertURL()
	{
		return signingCertURL;
	}

	public void setSigningCertURL(final String signingCertURL)
	{
		this.signingCertURL = signingCertURL;
	}

	public String getSubscribeURL()
	{
		return subscribeURL;
	}

	public void setSubscribeURL(final String subscribeURL)
	{
		this.subscribeURL = subscribeURL;
	}

	public String getUnsubscribeURL()
	{
		return unsubscribeURL;
	}

	public void setUnsubscribeURL(final String unsubscribeURL)
	{
		this.unsubscribeURL = unsubscribeURL;
	}
}
