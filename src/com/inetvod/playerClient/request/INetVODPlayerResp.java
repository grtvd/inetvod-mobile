/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Readable;

public class INetVODPlayerResp implements Readable
{
	/* Constants */
	public static final Constructor<INetVODPlayerResp> CtorDataReader = DataReader.getCtor(INetVODPlayerResp.class);
	public static final int RequestIDMaxLength = 64;
	public static final int StatusMessageMaxLength = 1024;

	/* Fields */
	protected String fRequestID;
	protected StatusCode fStatusCode;
	protected String fStatusMessage;
	protected ResponseData fResponseData;

	/* Getters and Setters */
	public StatusCode getStatusCode() { return fStatusCode; }
	public String getStatusMessage() { return fStatusMessage; }
	public ResponseData getResponseData() { return fResponseData; }

	/* Construction */
	public INetVODPlayerResp(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	public void readFrom(DataReader reader) throws Exception
	{
		fRequestID = reader.readString("RequestID", RequestIDMaxLength);
		fStatusCode = StatusCode.convertFromInt(reader.readInt("StatusCode"));
		fStatusMessage = reader.readString("StatusMessage", StatusMessageMaxLength);
		fResponseData = reader.readObject("ResponseData", ResponseData.CtorDataReader);
	}
}
