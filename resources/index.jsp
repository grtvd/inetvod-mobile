<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=iso-8859-1" language="java" %>
<%
	/**
	 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
	 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
	 */
%>
<jsp:useBean id="mobileManager" class="com.inetvod.mobile.MobileManager" scope="request"/>
<html>
<head>
	<title>Playlist Manager</title>
	<style type="text/css">
	<!--
	body,td,a,p{font-family:arial,sans-serif;font-size:6pt;}
	h1{font-size:7pt;color:navy;}
	-->
	</style>
</head>
<body style=" margin-left: 0px; margin-top: 0px; margin-right: 0px; margin-bottom: 0px;">
<table cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td><img src="images/banner.jpg" alt="iNetVOD"/></td>
	</tr>
	<tr>
		<td align="center" style="color:red;">Moblie Playlist Service</td>
	</tr>
	<tr>
		<td align="center"><h1>Videos, TV, Movies</h1></td>
	</tr>
	<tr>
		<td align="center"><a href="home.jsp"><img src="images/play.gif" alt="Ready To Play" border="0"/></a></td>
	</tr>
	<tr>
		<td align="center">a Service of<br>iNetVOD.com</td>
	</tr>
</table>
</body>
</html>