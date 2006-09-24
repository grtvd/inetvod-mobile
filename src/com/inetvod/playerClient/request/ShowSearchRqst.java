/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.data.CategoryID;
import com.inetvod.common.data.CategoryIDList;
import com.inetvod.common.data.ProviderID;
import com.inetvod.common.data.ProviderIDList;
import com.inetvod.common.data.RatingID;
import com.inetvod.common.data.RatingIDList;

public class ShowSearchRqst implements Writeable
{
	/* Constants */
	private static final int SearchMaxLength = 64;

	/* Fields */
	private String fSearch;

	private ProviderIDList fProviderIDList = new ProviderIDList();
	private CategoryIDList fCategoryIDList = new CategoryIDList();
	private RatingIDList fRatingIDList = new RatingIDList();

	private Short fMaxResults;

	/* Getters and Setters */
	public void setSearch(String search) { fSearch = search; }

	public ProviderIDList getProviderIDList() { return fProviderIDList; }
	public CategoryIDList getCategoryIDList() { return fCategoryIDList; }
	public RatingIDList getRatingIDList() { return fRatingIDList; }

	public void setMaxResults(Short maxResults) { fMaxResults = maxResults; }

	/* Implementation */
	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("Search", fSearch, SearchMaxLength);

		writer.writeStringList("ProviderID", fProviderIDList, ProviderID.MaxLength);
		writer.writeStringList("CategoryID", fCategoryIDList, CategoryID.MaxLength);
		writer.writeStringList("RatingID", fRatingIDList, RatingID.MaxLength);

		writer.writeShort("MaxResults", fMaxResults);
	}
}
