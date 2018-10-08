package com.kienvo.aws.notification.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Bounce Notification Object Data for Amazon Simple Notification Message (SNS) JSON
 * @author kien.vo
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SNSNotificationMessageData
{
	@JsonProperty("notificationType")
	private String notificationType;

	@JsonProperty("bounce")
	private SNSNotificationBounceObjectData bounce;

	@JsonProperty("complaint")
	private SNSNotificationComplaintObjectData complaint;

	public String getNotificationType()
	{
		return notificationType;
	}

	public void setNotificationType(final String notificationType)
	{
		this.notificationType = notificationType;
	}

	public SNSNotificationBounceObjectData getBounce()
	{
		return bounce;
	}

	public void setBounce(final SNSNotificationBounceObjectData bounce)
	{
		this.bounce = bounce;
	}

	public SNSNotificationComplaintObjectData getComplaint()
	{
		return complaint;
	}

	public void setComplaint(final SNSNotificationComplaintObjectData complaint)
	{
		this.complaint = complaint;
	}
}
