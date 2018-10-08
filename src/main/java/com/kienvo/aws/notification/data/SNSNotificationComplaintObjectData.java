package com.kienvo.aws.notification.data;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SNSNotificationComplaintObjectData
{
	@JsonProperty("userAgent")
	private String userAgent;

	@JsonProperty("complainedRecipients")
	private List<SNSNotificationRecipientObjectData> complainedRecipients;

	@JsonProperty("complaintFeedbackType")
	private String complaintFeedbackType;

	@JsonProperty("arrivalDate")
	private String arrivalDate;

	@JsonProperty("timestamp")
	private String timestamp;

	@JsonProperty("feedbackId")
	private String feedbackId;

	@Override
	public String toString()
	{
		final StringBuilder result = new StringBuilder();
		result.append("userAgent: " + getUserAgent());
		if (CollectionUtils.isNotEmpty(getComplainedRecipients()))
		{
			result.append(" - bouncedRecipients=");
			for (final SNSNotificationRecipientObjectData recipient : getComplainedRecipients())
			{
				result.append(recipient.toString() + " ");
			}
		}
		result.append(" - complaintFeedbackType=" + getComplaintFeedbackType());
		result.append(" - arrivalDate=" + getArrivalDate());
		result.append(" - timestamp=" + getTimestamp());
		result.append(" - feedbackId=" + getFeedbackId());

		return result.toString();
	}

	public String getUserAgent()
	{
		return userAgent;
	}

	public void setUserAgent(final String userAgent)
	{
		this.userAgent = userAgent;
	}

	public List<SNSNotificationRecipientObjectData> getComplainedRecipients()
	{
		return complainedRecipients;
	}

	public void setComplainedRecipients(final List<SNSNotificationRecipientObjectData> complainedRecipients)
	{
		this.complainedRecipients = complainedRecipients;
	}

	public String getComplaintFeedbackType()
	{
		return complaintFeedbackType;
	}

	public void setComplaintFeedbackType(final String complaintFeedbackType)
	{
		this.complaintFeedbackType = complaintFeedbackType;
	}

	public String getArrivalDate()
	{
		return arrivalDate;
	}

	public void setArrivalDate(final String arrivalDate)
	{
		this.arrivalDate = arrivalDate;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(final String timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getFeedbackId()
	{
		return feedbackId;
	}

	public void setFeedbackId(final String feedbackId)
	{
		this.feedbackId = feedbackId;
	}
}
