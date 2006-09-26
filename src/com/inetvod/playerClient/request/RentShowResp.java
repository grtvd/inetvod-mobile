/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.data.RentedShowID;
import com.inetvod.playerClient.rqdata.License;

public class RentShowResp implements com.inetvod.common.core.Readable
{
	/* Fields */
	protected RentedShowID fRentedShowID;
	protected License fLicense;

	/* Getters and Setters */
	public RentedShowID getRentedShowID() { return fRentedShowID; }
	public License getLicense() { return fLicense; }

	/* Construction */
	public RentShowResp(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	public void readFrom(DataReader reader) throws Exception
	{
		fRentedShowID = reader.readDataID("RentedShowID", RentedShowID.MaxLength, RentedShowID.CtorString);
		fLicense = reader.readObject("License", License.CtorDataReader);
	}
}
