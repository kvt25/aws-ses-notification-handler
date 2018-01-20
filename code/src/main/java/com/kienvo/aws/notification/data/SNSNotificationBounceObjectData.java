package com.kienvo.aws.notification.data;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Notification Message Object Data for Amazon Simple Notification Message (SNS) JSON
 * @author kien.vo
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SNSNotificationBounceObjectData
{
	@JsonProperty("bounceType")
	private String bounceType;

	@JsonProperty("reportingMTA")
	private String reportingMTA;

	@JsonProperty("bouncedRecipients")
	private List<SNSNotificationRecipientObjectData> bouncedRecipients;

	@JsonProperty("bounceSubType")
	private String bounceSubType;

	@JsonProperty("timestamp")
	private String timestamp;

	@JsonProperty("feedbackId")
	private String feedbackId;

	@JsonProperty("remoteMtaIp")
	private String remoteMtaIp;

	@Override
	public String toString()
	{
		final StringBuilder result = new StringBuilder();
		result.append("bounceType=" + getBounceType());
		result.append(" - reportingMTA=" + getReportingMTA());
		result.append(" - bounceSubType=" + getBounceSubType());
		if (CollectionUtils.isNotEmpty(getBouncedRecipients()))
		{
			result.append(" - bouncedRecipients=");
			for (final SNSNotificationRecipientObjectData recipient : getBouncedRecipients())
			{
				result.append(recipient.toString() + " ");
			}
		}
		result.append(" - timestamp=" + getTimestamp());
		result.append(" - feedbackId=" + getFeedbackId());
		result.append(" - remoteMtaIp=" + getRemoteMtaIp());

		return result.toString();
	}

	public String getBounceType()
	{
		return bounceType;
	}

	public void setBounceType(final String bounceType)
	{
		this.bounceType = bounceType;
	}

	public String getReportingMTA()
	{
		return reportingMTA;
	}

	public void setReportingMTA(final String reportingMTA)
	{
		this.reportingMTA = reportingMTA;
	}

	public List<SNSNotificationRecipientObjectData> getBouncedRecipients()
	{
		return bouncedRecipients;
	}

	public void setBouncedRecipients(final List<SNSNotificationRecipientObjectData> bouncedRecipients)
	{
		this.bouncedRecipients = bouncedRecipients;
	}

	public String getBounceSubType()
	{
		return bounceSubType;
	}

	public void setBounceSubType(final String bounceSubType)
	{
		this.bounceSubType = bounceSubType;
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

	public String getRemoteMtaIp()
	{
		return remoteMtaIp;
	}

	public void setRemoteMtaIp(final String remoteMtaIp)
	{
		this.remoteMtaIp = remoteMtaIp;
	}
}
