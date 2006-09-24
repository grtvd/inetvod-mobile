/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;


import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;

public class INetVODPlayerRqst implements Writeable
{
	/* Constants */
	private static final int VersionMaxLength = 16;
	public static final int RequestIDMaxLength = 64;
	private static final int SessionDataMaxLength = Short.MAX_VALUE;

	/* Fields */
	private String fVersion;
	private String fRequestID;
	private String fSessionData;
	private RequestData fRequestData;

	/* Getters and Setters */
//	public void setVersion(String version) { fVersion = version; }
//	public void setRequestID(String requestID) { fRequestID = requestID; }
	public void setRequestData(RequestData requestData) { fRequestData = requestData; }

	/* Construction */
	private INetVODPlayerRqst(String version, String requestID, String sessionData)
	{
		fVersion = version;
		fRequestID = requestID;
		fSessionData = sessionData;
	}

	public static INetVODPlayerRqst newInstance(String version, String requestID, String sessionData)
	{
		return new INetVODPlayerRqst(version, requestID, sessionData);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("Version", fVersion, VersionMaxLength);
		writer.writeString("RequestID", fRequestID, RequestIDMaxLength);
		writer.writeString("SessionData", fSessionData, SessionDataMaxLength);

		writer.writeObject("RequestData", fRequestData);
	}
}
