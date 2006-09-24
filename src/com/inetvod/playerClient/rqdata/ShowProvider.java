/**
 * Copyright © 2004-2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.rqdata;

import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Readable;
import com.inetvod.common.data.ProviderID;
import com.inetvod.common.data.ShowCost;
import com.inetvod.common.data.ShowCostList;

public class ShowProvider implements Readable
{
	/* Constants */
	public static Constructor<ShowProvider> CtorDataReader = DataReader.getCtor(ShowProvider.class);

	/* Fields */
	protected ProviderID fProviderID;
	protected ShowCostList fShowCostList;

	/* Getters and Setters */
	public ProviderID getProviderID() { return fProviderID; }
	public ShowCostList getShowCostList() { return fShowCostList; }

	/* Constuction Methods */
	public ShowProvider(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fProviderID = reader.readDataID("ProviderID", ProviderID.MaxLength, ProviderID.CtorString);
		fShowCostList = reader.readList("ShowCost", ShowCostList.Ctor, ShowCost.CtorDataReader);
	}
}

