/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.data.ProviderID;
import com.inetvod.common.data.ShowCost;
import com.inetvod.common.data.ShowID;

public class RentShowRqst implements Writeable
{
	/* Fields */
	private ShowID fShowID;
	private ProviderID fProviderID;
	private ShowCost fApprovedCost;

	/* Getters and Setters */
	public void setShowID(ShowID showID) { fShowID = showID; }
	public void setProviderID(ProviderID providerID) { fProviderID = providerID; }
	public void setApprovedCost(ShowCost approvedCost) { fApprovedCost = approvedCost; }

	/* Implementation */
	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ShowID", fShowID, ShowID.MaxLength);
		writer.writeDataID("ProviderID", fProviderID, ProviderID.MaxLength);
		writer.writeObject("ApprovedCost", fApprovedCost);
	}
}
