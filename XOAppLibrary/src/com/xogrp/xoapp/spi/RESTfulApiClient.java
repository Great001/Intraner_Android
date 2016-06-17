package com.xogrp.xoapp.spi;


import com.xogrp.xoapp.XOConfiguration;
import com.xogrp.xoapp.XOConfigurationException;
import com.xogrp.xoapp.XOLogger;
import com.xogrp.xoapp.model.MemberProfile;

import org.json.JSONException;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RESTfulApiClient {
	protected static final String REQUEST_METHOD_GET = "GET";
	protected static final String REQUEST_METHOD_POST = "POST";
	protected static final String REQUEST_METHOD_PUT = "PUT";
	protected static final String REQUEST_METHOD_DELETE = "DELETE";

	private static final int HTTP_STATUS_UNPROCESSABLE_ENTITY = 422;


	private XOConfiguration xoConfiguration;
	private String requestMethod;

	public static RESTfulApiClient getRESTfulGetClient(XOConfiguration xoConfiguration) {
		return new RESTfulApiClient(xoConfiguration, REQUEST_METHOD_GET);
	}

	public static RESTfulApiClient getRESTfulPostClient(XOConfiguration xoConfiguration) {
		return new RESTfulApiClient(xoConfiguration, REQUEST_METHOD_POST);
	}

	public static RESTfulApiClient getRESTfulPutClient(XOConfiguration xoConfiguration) {
		return new RESTfulApiClient(xoConfiguration, REQUEST_METHOD_PUT);
	}

	public static RESTfulApiClient getRESTDeleteClient(XOConfiguration xoConfiguration) {
		return new RESTfulApiClient(xoConfiguration, REQUEST_METHOD_DELETE);
	}

	private RESTfulApiClient(XOConfiguration xoConfiguration, String requestMethod) {
		this.xoConfiguration = xoConfiguration;
		this.requestMethod = requestMethod;
	}

	public RESTResponse sendRequest(String url, RESTfulApiCallback callback, Logger logger) {
		RESTResponse restResponse = null;
		if (!callback.isCanceled()) {
			HttpURLConnection httpURLConnection = null;
			OutputStream outputStream = null;
			try {
				System.out.println(String.format("(XOAppLibraryDebug) URL: %s", url));
				httpURLConnection = (HttpURLConnection) (new URL(url)).openConnection();
				httpURLConnection.setConnectTimeout(xoConfiguration.getConnectTimeout());
				httpURLConnection.setRequestMethod(requestMethod);
				httpURLConnection.setRequestProperty("Accept", "application/json");
				httpURLConnection.setRequestProperty("Content-Type", "application/json");
				callback.setRequestHeader(httpURLConnection);
				if (requestMethod.equals(REQUEST_METHOD_POST) || requestMethod.equals(REQUEST_METHOD_PUT)) {
					httpURLConnection.setDoInput(true);
					httpURLConnection.setDoOutput(true);
					outputStream = httpURLConnection.getOutputStream();
					callback.writeRequestBody(outputStream);
					outputStream.flush();
					outputStream.close();
				}

				httpURLConnection.connect();
				int statusCode = 0;
				try {
					statusCode = httpURLConnection.getResponseCode();
				} catch (IOException ioe) {
					logger.debug(ioe.getMessage());
					// in system version of 4.1 ~ 4.3, getResponseCode() will throw a IOException.
					//so add this code to handle it.
					statusCode = httpURLConnection.getResponseCode();
				}

				if (!callback.isCanceled()) {
					switch (statusCode) {
						case HttpURLConnection.HTTP_OK:
						case HttpURLConnection.HTTP_CREATED:
						case HttpURLConnection.HTTP_NO_CONTENT:
							callback.readResponseBody(httpURLConnection.getResponseCode(), httpURLConnection.getInputStream());
							restResponse = new RESTResponse(httpURLConnection.getResponseCode(), httpURLConnection.getHeaderFields());
							break;
						case HttpURLConnection.HTTP_BAD_REQUEST:
						case HttpURLConnection.HTTP_UNAUTHORIZED:
						case HttpURLConnection.HTTP_NOT_FOUND:
						case HTTP_STATUS_UNPROCESSABLE_ENTITY:
						case HttpURLConnection.HTTP_INTERNAL_ERROR:
						case HttpURLConnection.HTTP_UNAVAILABLE:
							callback.readResponseBody(httpURLConnection.getResponseCode(), httpURLConnection.getErrorStream());
							break;
						case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
							String responseMessage = null;
							try {
								responseMessage = httpURLConnection.getResponseMessage();
							} catch (IOException ioe) {
								responseMessage = "Unknown error";
							}
//                            logger.debug(String.format("(XOAppLibraryDebug) error: %s", responseMessage));
							restResponse = new RESTResponse(statusCode, String.format("Error returned from API: %s", responseMessage));
							break;
					}
				}
			} catch (MalformedURLException mfurle) {
				System.out.print("url------------" + url);
				restResponse = new RESTResponse("Incorrect RESTful API URL", new XOConfigurationException(String.format("Invalid URL for RESTful API call: %s", url)));
//                logger.debug(mfurle.getMessage());
			} catch (JSONException jsone) {
				restResponse = new RESTResponse("Error in reading JSON response data", jsone);
//                logger.debug(jsone.getMessage());
			} catch (IOException ioe) {
				restResponse = new RESTResponse("Error in calling RESTful API call", ioe);
//                logger.debug(ioe.getMessage());
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException ioe) {
						// no harm to ignore exception here
					}
				}
				if (httpURLConnection != null) {
					httpURLConnection.disconnect();
					httpURLConnection = null;
				}
			}
		}

		return restResponse;
	}

	public static abstract class RESTfulApiCallback {
		private static Logger DEFAULT_LOGGER = new XOLogger();

		private MemberProfile memberProfile;
		private Logger logger;
		private boolean isCanceled;

		public RESTfulApiCallback() {
			logger = DEFAULT_LOGGER;
		}

		protected Logger getLogger() {
			return logger;
		}

		public void setLogger(Logger logger) {
			this.logger = logger;
		}

		public void cancel() {
			isCanceled = true;
		}

		public boolean isCanceled() {
			return isCanceled;
		}

		public void writeRequestBody(OutputStream outputStream)
				throws IOException, JSONException {
		}

		public abstract void readResponseBody(int statusCode, InputStream inStream)
				throws IOException, JSONException;

		protected static String readResponseBodyAsString(InputStream inStream)
				throws IOException {
			StringBuilder responseBody = new StringBuilder();
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(inStream));
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					responseBody.append(line);
				}
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException ioe) {
						// no harm to ignore error here
					}
				}
			}

			return responseBody.toString();
		}

		public RESTfulApiCallback(MemberProfile memberProfile) {
			this.memberProfile = memberProfile;
		}

		protected MemberProfile getMemberProfile() {
			return memberProfile;
		}

		protected void setRequestHeader(HttpURLConnection httpURLConnection) {
			if (memberProfile != null) {
				httpURLConnection.setRequestProperty(memberProfile.getTokenName(), memberProfile.getTokenValue());
			}
		}

		public final static boolean isTextEmptyOrNull(String text) {
			return text == null || text.isEmpty() || "null".equalsIgnoreCase(text);
		}
	}
}
