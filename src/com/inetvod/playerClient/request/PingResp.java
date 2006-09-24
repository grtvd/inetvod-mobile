/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Readable;

public class PingResp implements Readable
{
	/* Contruction */
	public PingResp(DataReader reader)
	{
		readFrom(reader);
	}

	/* Implementation */
	public void readFrom(DataReader reader)
	{
		// No Fields
	}
}
