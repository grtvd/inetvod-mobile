/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.mobile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inetvod.common.core.StrUtil;
import com.inetvod.common.data.CategoryID;
import com.inetvod.common.data.ProviderID;
import com.inetvod.common.data.ShowCost;
import com.inetvod.common.data.ShowCostType;
import com.inetvod.common.data.ShowID;
import com.inetvod.playerClient.PlayerRequestor;
import com.inetvod.playerClient.request.CheckShowAvailResp;
import com.inetvod.playerClient.request.SignonResp;
import com.inetvod.playerClient.request.StatusCode;
import com.inetvod.playerClient.rqdata.RentedShowSearchList;
import com.inetvod.playerClient.rqdata.ShowDetail;
import com.inetvod.playerClient.rqdata.ShowProvider;
import com.inetvod.playerClient.rqdata.ShowSearch;
import com.inetvod.playerClient.rqdata.ShowSearchList;

public class MobileManager
{
	/* Constants */
	private static final String SESSION_COOKIE = "session";
	private static final String SHOWID_PARAM = "showid";
	private static final String USERID_PARAM = "userid";
	private static final String PASSWORD_PARAM = "password";

	/* Fields */
	private static final String fNetworkURL = "http://api.inetvod.com/inetvod/playerapi/xml";

	private PlayerRequestor fPlayerRequestor;
	private boolean fCanPingServer;
	private boolean fIsUserLoggedOn;
	private String fSessionData;
	private String fErrorMessage;

	/* Getters and Setters */
	private PlayerRequestor getPlayerRequestor()
	{
		if(fPlayerRequestor == null)
			fPlayerRequestor = PlayerRequestor.newInstance(fNetworkURL, fSessionData);
		return fPlayerRequestor;
	}

	/* Construction */
	public boolean initialize(HttpServletRequest request)
	{
		loadDataSettings(request);

		return fIsUserLoggedOn;
	}

	/* Implementation */
	public String getErrorMessage()
	{
		String msg;

		if(StrUtil.hasLen(fErrorMessage))
			msg = fErrorMessage;
		else
			msg = getPlayerRequestor().getStatusMessage();

		if(!StrUtil.hasLen(msg))
			msg = "An unknown error has occurred.";
		return "Error: " + msg;
	}

	public boolean logonToService(HttpServletRequest request, HttpServletResponse response)
	{
		String userID = request.getParameter(USERID_PARAM);
		String password = request.getParameter(PASSWORD_PARAM);

		if(pingServer())
		{
			if(signon(userID, password))
			{
				saveDataSettings(response);
				return true;
			}
		}

		return false;
	}

	public ShowSearchList getFeaturedList(HttpServletResponse response)
	{
		ShowSearchList showSearchList = null;

		if(this.fIsUserLoggedOn)
			showSearchList = showSearchFeatured();

		if(showSearchList == null)
			checkInvalidSession(response);
		return showSearchList;
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

	public RentedShowSearchList getPlaylist(HttpServletResponse response)
	{
		RentedShowSearchList rentedShowSearchList = null;

		if(this.fIsUserLoggedOn)
			rentedShowSearchList = rentedShowList();

		if(rentedShowSearchList == null)
			checkInvalidSession(response);
		return rentedShowSearchList;
	}

	public String rentShow(HttpServletRequest request, HttpServletResponse response)
	{
		ShowID showID = new ShowID(request.getParameter(SHOWID_PARAM));

		if(this.fIsUserLoggedOn)
		{
			ShowDetail showDetail = showDetail(showID);
			if(showDetail != null)
				if(doRentalLogic(showDetail))
					return String.format("%s has been successfully added to your Playlist.",
						showDetail.getNameWithEpisode());
		}

		checkInvalidSession(response);
		return getErrorMessage();
	}

	private void loadDataSettings(HttpServletRequest request)
	{
		Cookie cookies[] = request.getCookies();
		String value;

		if(cookies != null)
		{
			for(Cookie cookie : cookies)
			{
				if(SESSION_COOKIE.equals(cookie.getName()))
				{
					value = cookie.getValue();
					if(StrUtil.hasLen(value))
					{
						fIsUserLoggedOn = true;
						fSessionData = value;
					}

					break;
				}
			}
		}
	}

	private void saveDataSettings(HttpServletResponse response)
	{
		Cookie cookie = new Cookie(SESSION_COOKIE, StrUtil.hasLen(fSessionData) ? fSessionData : "");
		response.addCookie(cookie);
	}

	private void checkInvalidSession(HttpServletResponse response)
	{
		if(StatusCode.sc_InvalidSession.equals(getPlayerRequestor().getStatusCode()))
		{
			fIsUserLoggedOn = false;
			fSessionData = null;

			saveDataSettings(response);
		}
	}

	private boolean pingServer()
	{
		if(!fCanPingServer)
			fCanPingServer = getPlayerRequestor().pingServer();
		return fCanPingServer;
	}

	private boolean signon(String userID, String password)
	{
		if(!fIsUserLoggedOn)
		{
			PlayerRequestor playerRequestor = getPlayerRequestor();
			SignonResp signonResp = playerRequestor.signon(userID, password);
			if(StatusCode.sc_Success.equals(playerRequestor.getStatusCode()))
			{
				fSessionData = signonResp.getSessionData();
				playerRequestor.setSessionData(fSessionData);
				fIsUserLoggedOn = true;
			}
		}

		return fIsUserLoggedOn;
	}

	private ShowSearchList showSearchFeatured()
	{
		return getPlayerRequestor().showSearch(new CategoryID("featured"));
	}

	private ShowDetail showDetail(ShowID showID)
	{
		return getPlayerRequestor().showDetail(showID);
	}

	private boolean doRentalLogic(ShowDetail showDetail)
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

	private boolean checkShowAvail(ShowID showID, ProviderID providerID, ShowCost showCost)
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

	private boolean rentShow(ShowID showID, ProviderID providerID, ShowCost approvedCost)
	{
		PlayerRequestor playerRequestor = getPlayerRequestor();
		playerRequestor.rentShow(showID, providerID, approvedCost);

		return StatusCode.sc_Success.equals(playerRequestor.getStatusCode());
	}

	private RentedShowSearchList rentedShowList()
	{
		return getPlayerRequestor().rentedShowList();
	}
}
