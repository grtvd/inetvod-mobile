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
<html>
<head>
	<title>Fresh VOD</title>
</head>
<body>
<br/>

Go: <a href="nowPlaying.jsp">Playlist</a> <a href="searchResults.jsp">Featured</a>

</body>
</html>