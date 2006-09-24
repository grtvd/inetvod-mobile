/**
 * Copyright © 2006 iNetVOD, Inc. All Rights Reserved.
 * iNetVOD Confidential and Proprietary.  See LEGAL.txt.
 */
package com.inetvod.playerClient.request;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.inetvod.common.core.Logger;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.XmlDataReader;
import com.inetvod.common.core.XmlDataWriter;

public class DataRequestor
{
	/* Constants */
	private static final String Version = "1.0.0";
	private static final int fPingTimeoutMillis = 5000;			// (5 seconds) Timeout for PingRqst - TODO: move to config file
	private static final int fRequestTimeoutMillis = 30000;		// (30 seconds) Default timeout for requests - TODO: move to config file

	/* Fields */
	private String fRequestURL;
	private String fSessionData;
	private StatusCode fStatusCode;
	private String fStatusMessage;

	/* Getters and Setters */
	public StatusCode getStatusCode() { return fStatusCode; }
	public String getStatusMessage() { return fStatusMessage; }

	/* Construction */
	private DataRequestor(String requestURL, String sessionData)
	{
		fRequestURL = requestURL;
		fSessionData = sessionData;
	}

	public static DataRequestor newInstance(String requestURL, String sessionData)
	{
		return new DataRequestor(requestURL, sessionData);
	}

	/* Implementation */
	private INetVODPlayerRqst createHeader(Writeable payload)
	{
		INetVODPlayerRqst request = INetVODPlayerRqst.newInstance(Version, UUID.randomUUID().toString(), fSessionData);

		RequestData requestData = RequestData.newInstance(payload);
		request.setRequestData(requestData);

		return request;
	}

	private Readable sendRequest(Writeable payload, int timeoutMillis)
	{
		INetVODPlayerRqst iNetVODPlayerRqst;
		INetVODPlayerResp iNetVODPlayerResp;
		String requestName = "INetVODPlayerRqst";
		String responseName = "INetVODPlayerResp";

		try
		{
			// create request envelop
			iNetVODPlayerRqst = createHeader(payload);

			// Convert 'writeable' to XML
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			XmlDataWriter dataWriter = new XmlDataWriter(printWriter, "UTF-8");
			dataWriter.writeObject(requestName, iNetVODPlayerRqst);
			String requestXml = stringWriter.toString();
			//System.out.println(requestXml);

			// Send HTTP request to server
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setParameter("http.socket.timeout", timeoutMillis);
			String contentType = "text/xml; charset=ISO-8859-1";
			PostMethod postMethod = new PostMethod(fRequestURL);
			postMethod.setRequestEntity(new StringRequestEntity(requestXml, contentType, null));

			postMethod.setRequestHeader("Content-type", contentType);
			try
			{
				httpClient.executeMethod(postMethod);
				InputStream responseStream = postMethod.getResponseBodyAsStream();

				// Convert response XML to FieldReadable
				XmlDataReader dataReader = new XmlDataReader(responseStream);
				iNetVODPlayerResp = dataReader.readObject(responseName, INetVODPlayerResp.CtorDataReader);
				fStatusCode = iNetVODPlayerResp.getStatusCode();
				fStatusMessage = iNetVODPlayerResp.getStatusMessage();

				if(iNetVODPlayerResp.getResponseData() != null)
					return iNetVODPlayerResp.getResponseData().getResponse();
			}
			finally
			{
				postMethod.releaseConnection();
			}
		}
		catch(Exception e)
		{
			Logger.logInfo(this, "sendRequest", e);
			fStatusCode = StatusCode.sc_PlayerConnectionError;
		}

		return null;
	}

	public StatusCode pingRequest()
	{
		sendRequest(PingRqst.newInstance(), fPingTimeoutMillis);

		if(!StatusCode.sc_Success.equals(fStatusCode))
			Logger.logInfo(this, "pingRequest", String.format("bad StatusCode(%d) returned", StatusCode.convertToInt(fStatusCode)));
		return fStatusCode;
	}

	public SignonResp signonRequest(SignonRqst signonRqst)
	{
		return (SignonResp)sendRequest(signonRqst, fRequestTimeoutMillis);
	}

	public ShowSearchResp showSearchRequest(ShowSearchRqst showSearchRqst)
	{
		return (ShowSearchResp)sendRequest(showSearchRqst, fRequestTimeoutMillis);
	}

	public ShowDetailResp showDetailRequest(ShowDetailRqst showDetailRqst)
	{
		return (ShowDetailResp)this.sendRequest(showDetailRqst, fRequestTimeoutMillis);
	}

	public CheckShowAvailResp checkShowAvailRequest(CheckShowAvailRqst checkShowAvailRqst)
	{
		return (CheckShowAvailResp)sendRequest(checkShowAvailRqst, fRequestTimeoutMillis);
	}

	public RentShowResp rentShowRequest(RentShowRqst rentShowRqst)
	{
		return (RentShowResp)sendRequest(rentShowRqst, fRequestTimeoutMillis);
	}

	public RentedShowListResp rentedShowListRequest(RentedShowListRqst rentedShowListRqst)
	{
		return (RentedShowListResp)sendRequest(rentedShowListRqst, fRequestTimeoutMillis);
	}
}
