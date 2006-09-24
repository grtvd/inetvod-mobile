/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Readable;
import com.inetvod.playerClient.rqdata.ShowDetail;

public class ShowDetailResp implements Readable
{
	/* Fields */
	private ShowDetail fShowDetail;

	/* Getters and Setters */
	public ShowDetail getShowDetail() { return fShowDetail; }

	/* Contruction */
	public ShowDetailResp(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	public void readFrom(DataReader reader) throws Exception
	{
		fShowDetail = reader.readObject("ShowDetail", ShowDetail.CtorDataReader);
	}
}
