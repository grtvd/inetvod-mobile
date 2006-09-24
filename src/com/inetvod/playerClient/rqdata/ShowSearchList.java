/**
 * Copyright © 2004-2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.rqdata;

import java.util.ArrayList;
import java.lang.reflect.Constructor;

import com.inetvod.common.core.CtorUtil;

public class ShowSearchList extends ArrayList<ShowSearch>
{
	public static final Constructor<ShowSearchList> Ctor = CtorUtil.getCtorDefault(ShowSearchList.class);
}
