<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=iso-8859-1" language="java" %>
<%
	/**
	 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
	 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
	 */
%>
<jsp:useBean id="mobileManager" class="com.inetvod.mobile.MobileManager" scope="request"/>
<%@ page import="com.inetvod.playerClient.rqdata.ShowSearch"%>
<%@ page import="com.inetvod.playerClient.rqdata.ShowSearchList"%>
<%
	if(!mobileManager.initialize(request))
	{
		response.sendRedirect("logon.jsp");
		return;
	}
%>
<html>
<head>
	<title>iNetVOD Featured</title>
</head>
<body>
<br/>
Featured<br/>

<%
	String errorMsg = "";
	ShowSearchList showSearchList = mobileManager.getFeaturedList(response);
	if(showSearchList != null)
	{
		for(ShowSearch showSearch : showSearchList)
		{
			if(mobileManager.includeShowSearch(showSearch))
%>
			<a href="<%=mobileManager.getRentShowLink(showSearch)%>"
				><%=showSearch.getNameWithEpisode()%></a><br/>
<%
		}
	}
	else
		errorMsg = mobileManager.getErrorMessage();
%>
<%=errorMsg%>
<br/>
<br/>
Go: <a href="home.jsp">Home</a> <a href="nowPlaying.jsp">Playlist</a>

</body>
</html>