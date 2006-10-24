/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.rqdata;

import java.lang.reflect.Constructor;
import java.util.Date;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.StrUtil;
import com.inetvod.common.data.CategoryID;
import com.inetvod.common.data.CategoryIDList;
import com.inetvod.common.data.RatingID;
import com.inetvod.common.data.ShowID;

public class ShowDetail implements Readable
{
	/* Constants */
	public static Constructor<ShowDetail> CtorDataReader = DataReader.getCtor(ShowDetail.class);

	/* Constants */
	public static final int NameMaxLength = 64;
	public static final int EpisodeNameMaxLength = 64;
	public static final int EpisodeNumberMaxLength = 32;
	public static final int DescriptionMaxLength = Short.MAX_VALUE;
	public static final int ShowURLMaxLength = 4096;
	public static final int PictureURLMaxLength = 4096;

	/* Fields */
	protected ShowID fShowID;
	protected String fName;
	protected String fEpisodeName;
	protected String fEpisodeNumber;
	protected Date fReleasedOn;
	protected Short fReleasedYear;
	protected String fDescription;
	protected Short fRunningMins;
	protected String fPictureURL;

	protected CategoryIDList fCategoryIDList;
	protected RatingID fRatingID;
	protected Boolean fIsAdult;

	protected ShowProviderList fShowProviderList;

	/* Getters and Setters */
	public ShowID getShowID() { return fShowID; }
	public String getName() { return fName; }

	public String getNameWithEpisode()
	{
		if(StrUtil.hasLen(fEpisodeName))
			return fName + " - \"" + fEpisodeName + "\"";
		else
			return fName;
	}

	public String getEpisodeName() { return fEpisodeName; }
	public String getEpisodeNumber() { return fEpisodeNumber; }
	public Date getReleasedOn() { return fReleasedOn; }
	public Short getReleasedYear() { return fReleasedYear; }
	public String getDescription() { return fDescription; }
	public Short getRunningMins() { return fRunningMins; }
	public String getPictureURL() { return fPictureURL; }

	public CategoryIDList getCategoryIDList() { return fCategoryIDList; }
	public RatingID getRatingID() { return fRatingID; }
	public Boolean getIsAdult() { return fIsAdult; }
	public ShowProviderList getShowProviderList() { return fShowProviderList; }

	/* Constuction */
	public ShowDetail(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowID = reader.readDataID("ShowID", ShowID.MaxLength, ShowID.CtorString);
		fName = reader.readString("Name", NameMaxLength);
		fEpisodeName = reader.readString("EpisodeName", EpisodeNameMaxLength);
		fEpisodeNumber = reader.readString("EpisodeNumber", EpisodeNumberMaxLength);

		fReleasedOn = reader.readDate("ReleasedOn");
		fReleasedYear = reader.readShort("ReleasedYear");
		fDescription = reader.readString("Description", DescriptionMaxLength);
		fRunningMins = reader.readShort("RunningMins");
		fPictureURL = reader.readString("PictureURL", PictureURLMaxLength);

		fCategoryIDList = reader.readStringList("CategoryID", CategoryID.MaxLength, CategoryIDList.Ctor, CategoryID.CtorString);
		fRatingID = reader.readDataID("RatingID", RatingID.MaxLength, RatingID.CtorString);
		fIsAdult = reader.readBoolean("IsAdult");

		fShowProviderList = reader.readList("ShowProvider", ShowProviderList.Ctor, ShowProvider.CtorDataReader);
	}
}
