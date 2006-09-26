/**
 * Copyright © 2005-2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.rqdata;

import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.data.LicenseMethod;

public class License implements Readable, Writeable
{
	/* Constants */
	public static Constructor<License> CtorDataReader = DataReader.getCtor(License.class);

	private static final int ShowURLMaxLength = 4096;
	private static final int LicenseURLMaxLength = 4096;
	private static final int ContentIDMaxLength = 64;
	private static final int UserIDMaxLength = 64;
	private static final int PasswordMaxLength = 32;

	/* Fields */
	protected LicenseMethod fLicenseMethod;
	protected String fShowURL;
	protected String fLicenseURL;
	protected String fContentID;
	protected String fUserID;
	protected String fPassword;

	/* Getters and Setters */
	public void setLicenseMethod(LicenseMethod licenseMethod) { fLicenseMethod = licenseMethod; }
	public void setShowURL(String showURL) { fShowURL = showURL; }
	public void setLicenseURL(String licenseURL) { fLicenseURL = licenseURL; }
	public void setContentID(String contentID) { fContentID = contentID; }
	public void setUserID(String userID) { fUserID = userID; }
	public void setPassword(String password) { fPassword = password; }

	/* Constuction */
	public License()
	{
	}

	public License(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	public void readFrom(DataReader reader) throws Exception
	{
		fLicenseMethod = LicenseMethod.convertFromString(reader.readString("LicenseMethod", LicenseMethod.MaxLength));
		fShowURL = reader.readString("ShowURL", ShowURLMaxLength);
		fLicenseURL = reader.readString("LicenseURL", LicenseURLMaxLength);
		fContentID = reader.readString("ContentID", ContentIDMaxLength);
		fUserID = reader.readString("UserID", UserIDMaxLength);
		fPassword = reader.readString("Password", PasswordMaxLength);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("LicenseMethod", LicenseMethod.convertToString(fLicenseMethod), LicenseMethod.MaxLength);
		writer.writeString("ShowURL", fShowURL, ShowURLMaxLength);
		writer.writeString("LicenseURL", fLicenseURL, LicenseURLMaxLength);
		writer.writeString("ContentID", fContentID, ContentIDMaxLength);
		writer.writeString("UserID", fUserID, UserIDMaxLength);
		writer.writeString("Password", fPassword, PasswordMaxLength);
	}
}
