package main.java.LinkedIn;
import static main.java.LinkedIn.Constants.L_APP_ID__;
import static main.java.LinkedIn.Constants.l_APP_SECRET__;
import static main.java.LinkedIn.Constants.REDIRECT_URI__;
import static main.java.LinkedIn.Constants.l_STATE__;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class LinkedInConnection {

	
	
static String accessToken = "";

	public String getFBAuthUrl() {

		System.getProperties().put("http.proxyHost", "proxy.sgp.hp.com");
		System.getProperties().put("http.proxyPort", "8080");

		String fbLoginUrl = "";
		try {
			System.out.println("inside try1");
			fbLoginUrl = "https://www.linkedin.com/uas/oauth2/authorization?response_type=code&" + "client_id=" + L_APP_ID__ + "&redirect_uri="
					+ URLEncoder.encode((REDIRECT_URI__), "UTF-8") + "&state="+l_STATE__+"&scope=r_basicprofile";
			System.out.println("inside try11");
			System.out.println("here"+(REDIRECT_URI__));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbLoginUrl;
	}

	public String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		try {
			System.out.println("inside try111");
			fbGraphUrl = "https://www.linkedin.com/uas/oauth2/accessToken?grant_type=authorization_code&code="+ code + "&redirect_uri="
					+ URLEncoder.encode((REDIRECT_URI__), "UTF-8") + "client_id"+L_APP_ID__ +"&client_secret=" + l_APP_SECRET__;
			System.out.println("inside try1111cccc" + code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbGraphUrl;
	}

	public String getAccessToken(String code) {

		if ("".equals(accessToken)) {
			URL url;
			try {
				url = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection urlConnection;
			StringBuffer b = null;
			try {
				System.getProperties().put("https.proxyHost", "proxy.sgp.hp.com");
				System.getProperties().put("https.proxyPort", "8080");

				urlConnection = url.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					b.append(inputLine + "\n");
				}
				in.close();

			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook " + e);
			}
			accessToken = b.toString();
			if (accessToken.startsWith("{")) {
				throw new RuntimeException("ERROR: Access Token Invalid: " + accessToken);
			}
		}
		return accessToken;
	}

}
