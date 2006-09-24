/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"MagicNumber"})
public class StatusCode
{
	public static final StatusCode sc_Success = new StatusCode(0);

	public static final StatusCode sc_InvalidUserIDPassword = new StatusCode(1000);
	public static final StatusCode sc_InvalidSession = new StatusCode(1001);
	public static final StatusCode sc_InvalidProviderUserIDPassword = new StatusCode(1003);

	// NOTE: These errors are not returned by from the Provider
	public static final StatusCode sc_PlayerConnectionError = new StatusCode(9000);
	// NOTE: These errors are not returned by from the Provider

	public static final StatusCode sc_GeneralError = new StatusCode(9999);

	private static List<StatusCode> fAllValues = Arrays.asList(new StatusCode[]
		{
			sc_Success,
			sc_InvalidUserIDPassword,
			sc_InvalidSession,
			sc_InvalidProviderUserIDPassword,
			sc_PlayerConnectionError,
			sc_GeneralError
		});

	protected int fValue;

	private StatusCode(int value)
	{
		fValue = value;
	}

	public static StatusCode convertFromInt(Integer value)
	{
		if(value == null)
			return sc_Success;

		for(StatusCode statusCode : fAllValues)
			if(statusCode.fValue == value)
				return statusCode;

		return sc_GeneralError;
	}

	public static Integer convertToInt(StatusCode value)
	{
		if(value == null)
			return sc_Success.fValue;

		return value.fValue;
	}
}
