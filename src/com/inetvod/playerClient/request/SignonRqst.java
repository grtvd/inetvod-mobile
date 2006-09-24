/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.playerClient.rqdata.Player;

public class SignonRqst implements Writeable
{
	/* Constants */
	private static final int UserIDMaxLength = 128;
	private static final int PasswordMaxLength = 32;

	/* Fields */
	private String fUserID;
	private String fPassword;
	private Player fPlayer;

	/* Getters and Setters */
	public void setUserID(String userID) { fUserID = userID; }
	public void setPassword(String password) { fPassword = password; }
	public void setPlayer(Player player) { fPlayer = player; }

	/* Construction */
	public static SignonRqst newInstance()
	{
		return new SignonRqst();
	}

	/* Implementation */
	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("UserID", fUserID, UserIDMaxLength);
		writer.writeString("Password", fPassword, PasswordMaxLength);
		writer.writeObject("Player", fPlayer);
	}
}
