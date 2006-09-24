/**
 * Copyright © 2004-2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Readable;
import com.inetvod.playerClient.rqdata.ShowSearch;
import com.inetvod.playerClient.rqdata.ShowSearchList;

public class ShowSearchResp implements Readable
{
	/* Fields */
	private ShowSearchList fShowSearchList = new ShowSearchList();
	private Boolean fReachedMax;

	/* Getters and Setters */
	public ShowSearchList getShowSearchList() { return fShowSearchList; }
	public Boolean getReachedMax() { return fReachedMax; }

	/* Contruction */
	public ShowSearchResp(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	public void readFrom(DataReader reader) throws Exception
	{
		fShowSearchList = reader.readList("ShowSearch", ShowSearchList.Ctor, ShowSearch.CtorDataReader);
		fReachedMax = reader.readBoolean("ReachedMax");
	}
}
