package com.kienvo.aws.notification.data;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Notification Message Object Data for Amazon Simple Notification Message (SNS) JSON
 * @author kien.vo
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SNSNotificationRecipientObjectData
{
	@JsonProperty("emailAddress")
	private String emailAddress;

	@JsonProperty("status")
	private String status;

	@JsonProperty("action")
	private String action;

	@JsonProperty("diagnosticCode")
	private String diagnosticCode;

	@Override
	public String toString()
	{
		final StringBuilder result = new StringBuilder();
		result.append("recipient=" + getEmailAddress());
		if (StringUtils.isNotEmpty(getStatus()))
		{
			result.append("+++status=" + getStatus());
		}
		if (StringUtils.isNotEmpty(getAction()))
		{
			result.append("+++action=" + getAction());
		}
		if (StringUtils.isNotEmpty(getDiagnosticCode()))
		{
			result.append("+++diagnosticCode=" + getDiagnosticCode());
		}

		return result.toString();
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(final String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(final String status)
	{
		this.status = status;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(final String action)
	{
		this.action = action;
	}

	public String getDiagnosticCode()
	{
		return diagnosticCode;
	}

	public void setDiagnosticCode(final String diagnosticCode)
	{
		this.diagnosticCode = diagnosticCode;
	}
}