<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=iso-8859-1" language="java" %>
<%
	/**
	 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
	 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
	 */
%>
<jsp:useBean id="mobileManager" class="com.inetvod.mobile.MobileManager" scope="request"/>
<%@ page import="com.inetvod.playerClient.rqdata.RentedShowSearchList"%>
<%@ page import="com.inetvod.playerClient.rqdata.RentedShowSearch"%>
<%
	if(!mobileManager.initialize(request))
	{
		response.sendRedirect("logon.jsp");
		return;
	}
%>
<html>
<head>
	<title>iNetVOD Playlist</title>
</head>
<body>
<br/>
Playlist<br/>

<%
	String errorMsg = "";
	RentedShowSearchList rentedShowSearchList = mobileManager.getPlaylist(response);
	if(rentedShowSearchList != null)
	{
		for(RentedShowSearch rentedShowSearch : rentedShowSearchList)
		{
%>
			<%=rentedShowSearch.getNameWithEpisode()%><br/>
<%
		}
	}
	else
		errorMsg = mobileManager.getErrorMessage();
%>
<%=errorMsg%>
<br/>
<br/>
Go: <a href="home.jsp">Home</a> <a href="searchResults.jsp">Featured</a>

</body>
</html>