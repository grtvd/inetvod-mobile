/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient;

import com.inetvod.common.core.StrUtil;
import com.inetvod.common.data.ManufacturerID;
import com.inetvod.common.data.CategoryID;
import com.inetvod.common.data.ShowID;
import com.inetvod.common.data.ProviderID;
import com.inetvod.common.data.ShowCost;
import com.inetvod.playerClient.request.StatusCode;
import com.inetvod.playerClient.rqdata.Player;
import com.inetvod.playerClient.rqdata.ShowSearchList;
import com.inetvod.playerClient.rqdata.ShowDetail;
import com.inetvod.playerClient.rqdata.RentedShowSearchList;
import com.inetvod.playerClient.request.DataRequestor;
import com.inetvod.playerClient.request.SignonRqst;
import com.inetvod.playerClient.request.SignonResp;
import com.inetvod.playerClient.request.ShowSearchRqst;
import com.inetvod.playerClient.request.ShowSearchResp;
import com.inetvod.playerClient.request.RentShowRqst;
import com.inetvod.playerClient.request.RentShowResp;
import com.inetvod.playerClient.request.CheckShowAvailResp;
import com.inetvod.playerClient.request.CheckShowAvailRqst;
import com.inetvod.playerClient.request.ShowDetailRqst;
import com.inetvod.playerClient.request.ShowDetailResp;
import com.inetvod.playerClient.request.RentedShowListRqst;
import com.inetvod.playerClient.request.RentedShowListResp;

public class PlayerRequestor
{
	/* Fields */
	private String fRequestURL;
	private String fSessionData;

	private StatusCode fStatusCode;
	private String fStatusMessage;

	/* Getters and Setters */
	public void setSessionData(String sessionData)
	{
		fSessionData = sessionData;
	}

	public StatusCode getStatusCode() { return fStatusCode; }
	public String getStatusMessage() { return fStatusMessage; }

	/* Construction */
	private PlayerRequestor(String requestURL)
	{
		fRequestURL = requestURL;
	}

	public static PlayerRequestor newInstance(String requestURL)
	{
		return new PlayerRequestor(requestURL);
	}

	public static PlayerRequestor newInstance(String requestURL, String sessionData)
	{
		PlayerRequestor playerRequestor = newInstance(requestURL);

		if(StrUtil.hasLen(sessionData))
			playerRequestor.setSessionData(sessionData);

		return playerRequestor;
	}

	/* Implementation */
	private DataRequestor newDataRequestor()
	{
		return DataRequestor.newInstance(fRequestURL, fSessionData);
	}

	private void getDataRequestorStatus(DataRequestor dataRequestor)
	{
		fStatusCode = dataRequestor.getStatusCode();
		fStatusMessage = dataRequestor.getStatusMessage();
	}

	public boolean pingServer()
	{
		DataRequestor dataRequestor = newDataRequestor();
		dataRequestor.pingRequest();

		getDataRequestorStatus(dataRequestor);
		return StatusCode.sc_Success.equals(fStatusCode);
	}

	public SignonResp signon(String userID, String password)
	{
		SignonRqst signonRqst = SignonRqst.newInstance();
		signonRqst.setUserID(userID);
		signonRqst.setPassword(password);

		Player player = Player.newInstance(new ManufacturerID("inetvod"), "winwid", "1", "1.0.0000");
		signonRqst.setPlayer(player);

		DataRequestor dataRequestor = newDataRequestor();
		SignonResp signonResp = dataRequestor.signonRequest(signonRqst);

		getDataRequestorStatus(dataRequestor);
		return signonResp;

	}

	public ShowSearchList showSearch(CategoryID categoryID)
	{
		ShowSearchRqst showSearchRqst = new ShowSearchRqst();
		showSearchRqst.getCategoryIDList().add(categoryID);
		showSearchRqst.setMaxResults(Short.MAX_VALUE);

		DataRequestor dataRequestor = newDataRequestor();
		ShowSearchResp showSearchResp = dataRequestor.showSearchRequest(showSearchRqst);
		getDataRequestorStatus(dataRequestor);

		if(StatusCode.sc_Success.equals(fStatusCode))
			return showSearchResp.getShowSearchList();
		return null;
	}

	public ShowDetail showDetail(ShowID showID)
	{
		ShowDetailRqst showDetailRqst = new ShowDetailRqst();
		showDetailRqst.setShowID(showID);

		DataRequestor dataRequestor = newDataRequestor();
		ShowDetailResp showDetailResp = dataRequestor.showDetailRequest(showDetailRqst);
		getDataRequestorStatus(dataRequestor);

		if(StatusCode.sc_Success.equals(fStatusCode))
			return showDetailResp.getShowDetail();
		return null;
	}

	public CheckShowAvailResp checkShowAvail(ShowID showID, ProviderID providerID, ShowCost showCost)
	{
		CheckShowAvailRqst checkShowAvailRqst = new CheckShowAvailRqst();
		checkShowAvailRqst.setShowID(showID);
		checkShowAvailRqst.setProviderID(providerID);
		checkShowAvailRqst.setShowCost(showCost);

		DataRequestor dataRequestor = newDataRequestor();
		CheckShowAvailResp checkShowAvailResp = dataRequestor.checkShowAvailRequest(checkShowAvailRqst);
		getDataRequestorStatus(dataRequestor);

		if(StatusCode.sc_Success.equals(fStatusCode))
			return checkShowAvailResp;
		return null;
	}

	public RentShowResp rentShow(ShowID showID, ProviderID providerID, ShowCost approvedCost)
	{
		RentShowRqst rentShowRqst = new RentShowRqst();
		rentShowRqst.setShowID(showID);
		rentShowRqst.setProviderID(providerID);
		rentShowRqst.setApprovedCost(approvedCost);

		DataRequestor dataRequestor = newDataRequestor();
		RentShowResp rentShowResp = dataRequestor.rentShowRequest(rentShowRqst);
		getDataRequestorStatus(dataRequestor);

		if(StatusCode.sc_Success.equals(fStatusCode))
			return rentShowResp;
		return null;
	}

	public RentedShowSearchList rentedShowList()
	{
		RentedShowListRqst rentedShowListRqst = new RentedShowListRqst();
		DataRequestor dataRequestor = newDataRequestor();
		RentedShowListResp rentedShowListResp = dataRequestor.rentedShowListRequest(rentedShowListRqst);
		getDataRequestorStatus(dataRequestor);

		if(StatusCode.sc_Success.equals(fStatusCode))
			return rentedShowListResp.getRentedShowSearchList();
		return null;
	}
}
