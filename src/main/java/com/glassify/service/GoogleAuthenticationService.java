package com.glassify.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.glassify.domain.MyCredential;
import com.glassify.facade.CredentialFacade;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

public class GoogleAuthenticationService {

//	private CredentialFacade credentialFacade = new CredentialFacadeImpl();
	
	@Autowired
	private CredentialFacade credentialFacade;
	
	public GoogleAuthenticationService(CredentialFacade credentialFacade) {
		this.credentialFacade = credentialFacade;
	}
	
	
	ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource("client_secrets.json").getFile());

	private static final String REDIRECT_URI = "http://localhost:8080/oauth2callback";
	private static final List<String> SCOPES = Arrays.asList(
			"https://www.googleapis.com/auth/glass.timeline",
			"https://www.googleapis.com/auth/userinfo.profile");

	private static GoogleAuthorizationCodeFlow flow = null;
	private Credential credentials;

	/**
	 * Exception thrown when an error occurred while retrieving credentials.
	 */
	@SuppressWarnings("serial")
	public static class GetCredentialsException extends Exception {

		protected String authorizationUrl;

		/**
		 * Construct a GetCredentialsException.
		 *
		 * @param authorizationUrl The authorization URL to redirect the user to.
		 */
		public GetCredentialsException(String authorizationUrl) {
			this.authorizationUrl = authorizationUrl;
		}

		/**
		 * Set the authorization URL.
		 */
		public void setAuthorizationUrl(String authorizationUrl) {
			this.authorizationUrl = authorizationUrl;
		}

		/**
		 * @return the authorizationUrl
		 */
		public String getAuthorizationUrl() {
			return authorizationUrl;
		}
	}

	/**
	 * Exception thrown when a code exchange has failed.
	 */
	@SuppressWarnings("serial")
	public static class CodeExchangeException extends GetCredentialsException {

		/**
		 * Construct a CodeExchangeException.
		 *
		 * @param authorizationUrl The authorization URL to redirect the user to.
		 */
		public CodeExchangeException(String authorizationUrl) {
			super(authorizationUrl);
		}

	}

	/**
	 * Exception thrown when no refresh token has been found.
	 */
	@SuppressWarnings("serial")
	public static class NoRefreshTokenException extends GetCredentialsException {

		/**
		 * Construct a NoRefreshTokenException.
		 *
		 * @param authorizationUrl The authorization URL to redirect the user to.
		 */
		public NoRefreshTokenException(String authorizationUrl) {
			super(authorizationUrl);
		}

	}

	/**
	 * Exception thrown when no user ID could be retrieved.
	 */
	@SuppressWarnings("serial")
	private static class NoUserIdException extends Exception {
	}

	/**
	 * Retrieved stored credentials for the provided user ID.
	 *
	 * @param userId User's ID.
	 * @return Stored Credential if found, {@code null} otherwise.
	 */
	 Credential getStoredCredentials(String userId) {
		 //Credential instance with stored accessToken and refreshToken.
		 return credentialFacade.getCredentialForUser(userId).getCredential();
	}

	/**
	 * Store OAuth 2.0 credentials in the application's database.
	 *
	 * @param userId User's ID.
	 * @param credentials The OAuth 2.0 credentials to store.
	 */
	 void storeCredentials(String userId, Credential credentials) {
		// TODO: Implement this method to work with your database.
		// Store the credentials.getAccessToken() and credentials.getRefreshToken()
		// string values in your database.
		System.out.println("Inside Store Credentials");
		System.out.println("UserId - " + userId);
		System.out.println("Access Token - " + credentials.getAccessToken());
		System.out.println("Refresh Token - "  + credentials.getRefreshToken());
		MyCredential myCredential = new MyCredential();
		myCredential.setCredential(credentials);
		myCredential.setUserId(userId);
		credentialFacade.saveCredential(myCredential);
	}

	/**
	 * Build an authorization flow and store it as a static class attribute.
	 *
	 * @return GoogleAuthorizationCodeFlow instance.
	 * @throws IOException Unable to load client_secrets.json.
	 */
	 GoogleAuthorizationCodeFlow getFlow() throws IOException {
		if (flow == null) {
			HttpTransport httpTransport = new NetHttpTransport();
			JacksonFactory jsonFactory = new JacksonFactory();
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new FileReader(file));
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, SCOPES)
			.setAccessType("offline").setApprovalPrompt("force").build();
		}
		return flow;
	}

	/**
	 * Exchange an authorization code for OAuth 2.0 credentials.
	 *
	 * @param authorizationCode Authorization code to exchange for OAuth 2.0
	 *        credentials.
	 * @return OAuth 2.0 credentials.
	 * @throws CodeExchangeException An error occurred.
	 */
	 Credential exchangeCode(String authorizationCode)
			throws CodeExchangeException {
		try {
			GoogleAuthorizationCodeFlow flow = getFlow();
			GoogleTokenResponse response = flow.newTokenRequest(authorizationCode).setRedirectUri(REDIRECT_URI).execute();
			return flow.createAndStoreCredential(response, null);
		} catch (IOException e) {
			System.err.println("An error occurred: " + e);
			throw new CodeExchangeException(null);
		}
	}

	/**
	 * Send a request to the UserInfo API to retrieve the user's information.
	 *
	 * @param credentials OAuth 2.0 credentials to authorize the request.
	 * @return User's information.
	 * @throws NoUserIdException An error occurred.
	 */
	 Userinfoplus getUserInfo(Credential credentials)
			throws NoUserIdException {
		Oauth2 userInfoService =
				new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credentials).build();
		Userinfoplus userInfo = null;
		try {
			userInfo = userInfoService.userinfo().get().execute();
		} catch (IOException e) {
			System.err.println("An error occurred: " + e);
		}
		if (userInfo != null && userInfo.getId() != null) {
			return userInfo;
		} else {
			throw new NoUserIdException();
		}
	}

	/**
	 * Retrieve the authorization URL.
	 *
	 * @param userId User's Google ID.
	 * @param state State for the authorization URL.
	 * @return Authorization URL to redirect the user to.
	 * @throws IOException Unable to load client_secrets.json.
	 */
	public  String getAuthorizationUrl(String userId, String state) throws IOException {
		GoogleAuthorizationCodeRequestUrl urlBuilder =
				getFlow().newAuthorizationUrl().setRedirectUri(REDIRECT_URI).setState(state);
		urlBuilder.set("user_id", userId);
		return urlBuilder.build();
	}

	/**
	 * Retrieve credentials using the provided authorization code.
	 *
	 * This function exchanges the authorization code for an access token and
	 * queries the UserInfo API to retrieve the user's Google ID. If a
	 * refresh token has been retrieved along with an access token, it is stored
	 * in the application database using the user's Google ID as key. If no
	 * refresh token has been retrieved, the function checks in the application
	 * database for one and returns it if found or throws a NoRefreshTokenException
	 * with the authorization URL to redirect the user to.
	 *
	 * @param authorizationCode Authorization code to use to retrieve an access
	 *        token.
	 * @param state State to set to the authorization URL in case of error.
	 * @return OAuth 2.0 credentials instance containing an access and refresh
	 *         token.
	 * @throws NoRefreshTokenException No refresh token could be retrieved from
	 *         the available sources.
	 * @throws IOException Unable to load client_secrets.json.
	 */

	public  Credential getCredentials(String authorizationCode, String state)
			throws CodeExchangeException, NoRefreshTokenException, IOException {
		String userId = "";
		try {
			credentials = exchangeCode(authorizationCode);
			Userinfoplus userInfo = getUserInfo(credentials);
			userId = userInfo.getId();
			System.out.println(userInfo.getEmail());
			System.out.println(userInfo.getName());
			if (credentials.getRefreshToken() != null) {
				storeCredentials(userInfo.getEmail(), credentials);
				return credentials;
			} else {
				credentials = getStoredCredentials(userInfo.getEmail());
				if (credentials != null && credentials.getRefreshToken() != null) {
					return credentials;
				}
			}
		} catch (CodeExchangeException e) {
			e.printStackTrace();
			// Glass services should try to retrieve the user and credentials for the current
			// session.
			// If none is available, redirect the user to the authorization URL.
			e.setAuthorizationUrl(getAuthorizationUrl(userId, state));
			throw e;
		} catch (NoUserIdException e) {
			e.printStackTrace();
		}
		// No refresh token has been retrieved.
		String authorizationUrl = getAuthorizationUrl(userId, state);
		throw new NoRefreshTokenException(authorizationUrl);
	}

}
