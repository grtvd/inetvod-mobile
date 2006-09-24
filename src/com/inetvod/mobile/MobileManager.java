/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.mobile;

import javax.servlet.http.HttpServletRequest;

import com.inetvod.common.core.StrUtil;
import com.inetvod.common.data.CategoryID;
import com.inetvod.common.data.ShowCost;
import com.inetvod.common.data.ShowCostType;
import com.inetvod.common.data.ShowID;
import com.inetvod.common.data.ProviderID;
import com.inetvod.playerClient.PlayerRequestor;
import com.inetvod.playerClient.request.SignonResp;
import com.inetvod.playerClient.request.CheckShowAvailResp;
import com.inetvod.playerClient.request.StatusCode;
import com.inetvod.playerClient.rqdata.ShowProvider;
import com.inetvod.playerClient.rqdata.ShowSearch;
import com.inetvod.playerClient.rqdata.ShowSearchList;
import com.inetvod.playerClient.rqdata.ShowDetail;
import com.inetvod.playerClient.rqdata.RentedShowSearchList;

public class MobileManager
{
	/* Constants */
	private static final String SHOWID_PARAM = "showid";

	/* Fields */
	private static final String fNetworkURL = "http://api.inetvod.com/inetvod/playerapi/xml";

	private PlayerRequestor fPlayerRequestor;
	private boolean fCanPingServer;
	private boolean fIsUserLoggedOn;
	private String fSessionData;
	private String fErrorMessage;

	/* Getters and Setters */
	private PlayerRequestor getPlayerRequestor() throws Exception
	{
		if(fPlayerRequestor == null)
			fPlayerRequestor = PlayerRequestor.newInstance(fNetworkURL, fSessionData);
		return fPlayerRequestor;
	}

	/* Construction */

	/* Implementation */
	public String getErrorMessage()
	{
		String msg = null;

		try
		{
			if(StrUtil.hasLen(fErrorMessage))
				msg = fErrorMessage;
			else
				msg = getPlayerRequestor().getStatusMessage();
		}
		catch(Exception e)
		{
		}

		if(!StrUtil.hasLen(msg))
			msg = "An unknown error has occurred.";
		return "Error: " + msg;
	}

	public ShowSearchList getFeaturedList()
	{
		try
		{
			if(pingServer())
			{
				if(signon())
				{
					return showSearchFeatured();
				}
			}
		}
		catch(Exception e)
		{
		}

		return null;
	}

	public boolean includeShowSearch(ShowSearch showSearch)
	{
		for(ShowProvider showProvider : showSearch.getShowProviderList())
			for(ShowCost showCost : showProvider.getShowCostList())
				if(ShowCostType.Free.equals(showCost.getShowCostType()))
					return true;

		return false;
	}

	public String getRentShowLink(ShowSearch showSearch)
	{
		return String.format("rentShow.jsp?showid=%s", showSearch.getShowID().toString());
	}

	public RentedShowSearchList getPlaylist()
	{
		try
		{
			if(pingServer())
			{
				if(signon())
				{
					return rentedShowList();
				}
			}
		}
		catch(Exception e)
		{
		}

		return null;
	}

	public String rentShow(HttpServletRequest request) throws Exception
	{
		try
		{
			ShowID showID = new ShowID(request.getParameter(SHOWID_PARAM));

			if(pingServer())
			{
				if(signon())
				{
					ShowDetail showDetail = showDetail(showID);
					if(showDetail != null)
						if(doRentalLogic(showDetail))
							return String.format("%s has been successfully added to your Playlist.",
								showDetail.getNameWithEpisode());
				}
			}
		}
		catch(Exception e)
		{
		}

		return getErrorMessage();
	}

	private boolean pingServer() throws Exception
	{
		if(!fCanPingServer)
			fCanPingServer = getPlayerRequestor().pingServer();
		return fCanPingServer;
	}

	private boolean signon() throws Exception
	{
		if(!fIsUserLoggedOn)
		{
			PlayerRequestor playerRequestor = getPlayerRequestor();
			SignonResp signonResp = playerRequestor.signon("100000000", "123456");
			if(StatusCode.sc_Success.equals(playerRequestor.getStatusCode()))
			{
				fSessionData = signonResp.getSessionData();
				playerRequestor.setSessionData(fSessionData);
				fIsUserLoggedOn = true;
			}
		}

		return fIsUserLoggedOn;
	}

	private ShowSearchList showSearchFeatured() throws Exception
	{
		return getPlayerRequestor().showSearch(new CategoryID("featured"));
	}

	private ShowDetail showDetail(ShowID showID) throws Exception
	{
		return getPlayerRequestor().showDetail(showID);
	}

	private boolean doRentalLogic(ShowDetail showDetail) throws Exception
	{
		for(ShowProvider showProvider : showDetail.getShowProviderList())
		{
			for(ShowCost showCost : showProvider.getShowCostList())
			{
				if(ShowCostType.Free.equals(showCost.getShowCostType()))
				{
					return checkShowAvail(showDetail.getShowID(), showProvider.getProviderID(), showCost);
				}
			}
		}

		fErrorMessage = "Can only get shows that are Free.";
		return false;
	}

	private boolean checkShowAvail(ShowID showID, ProviderID providerID, ShowCost showCost) throws Exception
	{
		PlayerRequestor playerRequestor = getPlayerRequestor();
		CheckShowAvailResp checkShowAvailResp = playerRequestor.checkShowAvail(showID, providerID, showCost);
		if(!StatusCode.sc_Success.equals(playerRequestor.getStatusCode()))
			return false;

		if(!ShowCostType.Free.equals(checkShowAvailResp.getShowCost().getShowCostType()))
		{
			fErrorMessage = "Can only get shows that are Free.";
			return false;
		}

		return rentShow(showID, providerID, showCost);
	}

	private boolean rentShow(ShowID showID, ProviderID providerID, ShowCost approvedCost) throws Exception
	{
		PlayerRequestor playerRequestor = getPlayerRequestor();
		playerRequestor.rentShow(showID, providerID, approvedCost);

		return StatusCode.sc_Success.equals(playerRequestor.getStatusCode());
	}

	private RentedShowSearchList rentedShowList() throws Exception
	{
		return getPlayerRequestor().rentedShowList();
	}
}
