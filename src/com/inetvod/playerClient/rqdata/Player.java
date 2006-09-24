/**
 * Copyright © 2004-2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.rqdata;

import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.data.ManufacturerID;

public class Player implements Readable, Writeable
{
	/* Constants */
	public static final Constructor<Player> CtorDataReader = DataReader.getCtor(Player.class);
	public static final int ModelNoMaxLength = 32;
	public static final int SerialNoMaxLength = 64;
	public static final int VersionMaxLength = 16;

	/* Fields */
	protected ManufacturerID fManufacturerID;
	protected String fModelNo;
	protected String fSerialNo;
	protected String fVersion;

	/* Getters and Setters */
	public ManufacturerID getManufacturerID() { return fManufacturerID; }
	public String getModelNo() { return fModelNo; }
	public String getSerialNo() { return fSerialNo; }
	public String getVersion() { return fVersion; }

	/* Construction */
	private Player(ManufacturerID manufacturerID, String modelNo, String serialNo, String version)
	{
		fManufacturerID = manufacturerID;
		fModelNo = modelNo;
		fSerialNo = serialNo;
		fVersion = version;
	}

	public Player(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public static Player newInstance(ManufacturerID manufacturerID, String modelNo, String serialNo, String version)
	{
		return new Player(manufacturerID, modelNo, serialNo,version);
	}

	/* Implementation */
	public void readFrom(DataReader reader) throws Exception
	{
		fManufacturerID = reader.readDataID("ManufacturerID", ManufacturerID.MaxLength, ManufacturerID.CtorString);
		fModelNo = reader.readString("ModelNo", ModelNoMaxLength);
		fSerialNo = reader.readString("SerialNo", SerialNoMaxLength);
		fVersion = reader.readString("Version", VersionMaxLength);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ManufacturerID", fManufacturerID, ManufacturerID.MaxLength);
		writer.writeString("ModelNo", fModelNo, ModelNoMaxLength);
		writer.writeString("SerialNo", fSerialNo, SerialNoMaxLength);
		writer.writeString("Version", fVersion, VersionMaxLength);
	}
}
