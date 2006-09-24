/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.data.ShowID;

public class ShowDetailRqst implements Writeable
{
	/* Fields */
	protected ShowID fShowID;

	/* Getters and Setters */
	public void setShowID(ShowID showID) { fShowID = showID; }

	/* Implementation */
	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ShowID", fShowID, ShowID.MaxLength);
	}
}
