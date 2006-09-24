/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import java.util.Date;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Readable;

public class SignonResp implements Readable
{
	/* Constants */
	private static final int SessionDataMaxLength = Short.MAX_VALUE;

	/* Fields */
	protected String fSessionData;
	protected Date fSessionExpires;
	//protected MemberState fMemberState;

	/* Getters and Setters */
	public String getSessionData() { return fSessionData; }

	/* Contruction */
	public SignonResp(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	public void readFrom(DataReader reader) throws Exception
	{
		fSessionData = reader.readString("SessionData", SessionDataMaxLength);
		fSessionExpires = reader.readDateTime("SessionExpires");
	}
}
