/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Readable;
import com.inetvod.playerClient.rqdata.RentedShowSearch;
import com.inetvod.playerClient.rqdata.RentedShowSearchList;

public class RentedShowListResp implements Readable
{
	/* Fields */
	protected RentedShowSearchList fRentedShowSearchList = new RentedShowSearchList();

	/* Getters and Setters */
	public RentedShowSearchList getRentedShowSearchList() { return fRentedShowSearchList; }

	/* Construction */
	public RentedShowListResp(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	public void readFrom(DataReader reader) throws Exception
	{
		fRentedShowSearchList = reader.readList("RentedShowSearch", RentedShowSearchList.Ctor, RentedShowSearch.CtorDataReader);
	}
}