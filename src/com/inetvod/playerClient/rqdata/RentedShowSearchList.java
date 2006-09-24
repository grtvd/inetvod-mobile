/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.rqdata;

import java.util.ArrayList;
import java.lang.reflect.Constructor;

import com.inetvod.common.core.CtorUtil;

public class RentedShowSearchList extends ArrayList<RentedShowSearch>
{
	public static final Constructor<RentedShowSearchList> Ctor = CtorUtil.getCtorDefault(RentedShowSearchList.class);
}
