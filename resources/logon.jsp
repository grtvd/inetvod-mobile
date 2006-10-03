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
	<title>iNetVOD Logon</title>
</head>
<body>
<br/>
<form action="logon_save.jsp" method="post" name="mobile">

Logon ID: <input name="userid" size="9" maxlength="9"/><br/>
PIN: <input name="password" size="6" maxlength="6"/><br/>

<input name="logon" type="submit" value="Logon">

</form>
</body>
</html>