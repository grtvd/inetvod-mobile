/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.rqdata;

import java.util.Date;
import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.StrUtil;
import com.inetvod.common.data.ProviderID;
import com.inetvod.common.data.RentedShowID;
import com.inetvod.common.data.ShowID;

public class RentedShowSearch implements Readable
{
	/* Constants */
	public static Constructor<RentedShowSearch> CtorDataReader = DataReader.getCtor(RentedShowSearch.class);

	/* Fields */
	protected RentedShowID fRentedShowID;

	protected ShowID fShowID;
	protected ProviderID fProviderID;
	protected String fName;
	protected String fEpisodeName;

	public Date fAvailableUntil;

	/* Getters and Setters */
	public RentedShowID getRentedShowID() { return fRentedShowID; }
	public ShowID getShowID() { return fShowID; }
	public ProviderID getProviderID() { return fProviderID; }

	public String getName() { return fName; }

	public String getNameWithEpisode()
	{
		if(StrUtil.hasLen(fEpisodeName))
			return fName + " - \"" + fEpisodeName + "\"";
		else
			return fName;
	}

	public String getEpisodeName() { return fEpisodeName; }
	public Date getAvailableUntil() { return fAvailableUntil; }

	/* Constuction */
	public RentedShowSearch(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fRentedShowID = reader.readDataID("RentedShowID", RentedShowID.MaxLength, RentedShowID.CtorString);

		fShowID = reader.readDataID("ShowID", ShowID.MaxLength, ShowID.CtorString);
		fProviderID = reader.readDataID("ProviderID", ProviderID.MaxLength, ProviderID.CtorString);
		fName = reader.readString("Name", ShowDetail.NameMaxLength);
		fEpisodeName = reader.readString("EpisodeName", ShowDetail.EpisodeNameMaxLength);

		fAvailableUntil = reader.readDateTime("AvailableUntil");
	}
}
