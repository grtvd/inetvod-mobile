<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=windows-1252" language="java" %>
<%
	/**
	 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
	 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
	 */
%>
<jsp:useBean id="mobileManager" class="com.inetvod.mobile.MobileManager" scope="request"/>
<%
	if(!mobileManager.initialize(request))
	{
		response.sendRedirect("logon.jsp");
		return;
	}
	String msg = mobileManager.rentShow(request, response);
%>
<html>
<head>
	<title>iNetVOD Featured</title>
</head>
<body>
<br/>
<%=msg%>
<br/>
<br/>
Go: <a href="home.jsp">Home</a> <a href="nowPlaying.jsp">Playlist</a> <a href="searchResults.jsp">Featured</a>
</body>
</html>