package com.simens.us.myapplication.Network.request;

import com.simens.us.myapplication.Utils.KumaLog;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.net.ssl.SSLException;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;


public class MyTrustManager implements X509TrustManager {

	private onadtAuthCallback m_btnAuthCallback = null;

	public void setOnAuthCallbackListhner(onadtAuthCallback callback) {
		m_btnAuthCallback = callback;
	}

	public interface onadtAuthCallback {
		void onSuccess();
		void onError(String strError);
	}

	private final static String[] BAD_COUNTRY_2LDS =
			{ "ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info",
					"lg", "ne", "net", "or", "org" };

	//private final static String cnLabel = "COMMONNAME";
	private final static String cnLabel = "CN";
	private final static String cnOID = "2.5.4.3";

	private static final Pattern IPV4_PATTERN =
			Pattern.compile(
					"^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");


	private String serverURL;
	// private X509HostnameVerifier verifier;
	private KeyStore keyStore;

	private X509HostnameVerifier verifier;

	public MyTrustManager() {
	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// No client authentication

	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {

		KumaLog.d( "checkServerTrusted : ");

		if ((chain != null) && (chain.length > 0)) {

			X509Certificate certificate = chain[0];

			certificate.checkValidity(); // 유효기간을 확인

			KumaLog.d( "serverURL : " + serverURL);

			try {
				verifier = SSLSocketFactory.STRICT_HOSTNAME_VERIFIER;
				verifier.verify(serverURL, certificate);
//				verify(serverURL, certificate);
			} catch (SSLException e) {
				if( m_btnAuthCallback != null ) {
					m_btnAuthCallback.onError("SSLException");
				}
				KumaLog.e("SSLException >>>  " + e.getMessage());
//				e.printStackTrace();
				throw new CertificateException(e.getMessage());
			}
		}
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

	/**
	 * @param serverURL
	 *            the serverURL to set
	 */
	public void setServerURL(String serverURL) {
		try {
			URL url = new URL(serverURL);
			this.serverURL = url.getHost();
		} catch (MalformedURLException e) {
//			e.printStackTrace();
		}
	}


	/**
	 * @param keyStore
	 *            the keyStore to set
	 */
	public void setKeyStore(KeyStore keyStore) {
		this.keyStore = keyStore;
	}

	/**
	 * @return the keyStore
	 */
	public KeyStore getKeyStore() {
		return keyStore;
	}

	public final void verify(String host, X509Certificate cert)
			throws SSLException, CertificateParsingException {
		String[] cns = getCNs(cert);
		String[] subjectAlts = getDNSSubjectAlts(cert);
		verify(host, cns, subjectAlts, false);
	}

	public final void verify(final String host, final String[] cns, final String[] subjectAlts, final boolean strictWithSubDomains)
			throws SSLException {

		// Build the list of names we're going to check. Our DEFAULT and
		// STRICT implementations of the HostnameVerifier only use the
		// first CN provided. All other CNs are ignored.
		// (Firefox, wget, curl, Sun Java 1.4, 5, 6 all work this way).
		LinkedList<String> names = new LinkedList<String>();
		if (cns != null && cns.length > 0 && cns[0] != null) {
			names.add(cns[0]);
		}
		if (subjectAlts != null) {
			for (String subjectAlt : subjectAlts) {
				if (subjectAlt != null) {
					names.add(subjectAlt);
				}
			}
		}

		if (names.isEmpty()) {
			String msg = "Certificate for <" + host
					+ "> doesn't contain CN or DNS subjectAlt";
			throw new SSLException(msg);
		}

		// StringBuffer for building the error message.
		StringBuffer buf = new StringBuffer();

		// We're can be case-insensitive when comparing the host we used to
		// establish the socket to the hostname in the certificate.
		String hostName = host.trim().toLowerCase(Locale.ENGLISH);
		boolean match = false;
		for (Iterator<String> it = names.iterator(); it.hasNext();) {
			// Don't trim the CN, though!
			String cn = it.next();
			cn = cn.toLowerCase(Locale.ENGLISH);
			// Store CN in StringBuffer in case we need to report an error.
			buf.append(" <");
			buf.append(cn);
			buf.append('>');
			if (it.hasNext()) {
				buf.append(" OR");
			}

			// The CN better have at least two dots if it wants wildcard
			// action. It also can't be [*.co.uk] or [*.co.jp] or
			// [*.org.uk], etc...
			boolean doWildcard = cn.startsWith("*.")
					&& cn.indexOf('.', 2) != -1
					&& acceptableCountryWildcard(cn)
					&& !IPV4_PATTERN.matcher(host).matches();

			if (doWildcard) {
				match = hostName.endsWith(cn.substring(1));
				if (match && strictWithSubDomains) {
					// If we're in strict mode, then [*.foo.com] is not
					// allowed to match [a.b.foo.com]
					match = countDots(hostName) == countDots(cn);
				}
			} else {
				//android.util.Log.v("psj", "hostName : " + hostName + ", cn : " + cn);
				match = hostName.equals(cn);
			}
			if (match) {
				break;
			}
		}
		if (!match) {
			throw new SSLException("hostname in certificate didn't match: <"
					+ host + "> !=" + buf);
		}
	}

	public static boolean acceptableCountryWildcard(String cn) {
		int cnLen = cn.length();
		if (cnLen >= 7 && cnLen <= 9) {
			// Look for the '.' in the 3rd-last position:
			if (cn.charAt(cnLen - 3) == '.') {
				// Trim off the [*.] and the [.XX].
				String s = cn.substring(2, cnLen - 3);
				// And test against the sorted array of bad 2lds:
				int x = Arrays.binarySearch(BAD_COUNTRY_2LDS, s);
				return x < 0;
			}
		}
		return true;
	}

	public static String[] getCNs(X509Certificate cert) {
//		DistinguishedNameParser dnParser = new DistinguishedNameParser(
//				cert.getSubjectX500Principal());		
//		List<String> cnList = dnParser.getAllMostSpecificFirst("cn");
//		
//		
//		if (!cnList.isEmpty()) {
//			String[] cns = new String[cnList.size()];
//			cnList.toArray(cns);
//			return cns;
//		} else {
//			return null;
//		}
		String[] cns = null;
		ArrayList<String> result = new ArrayList<String>();

		X500Principal p = cert.getSubjectX500Principal();
		Map<String, String> cnMap = new HashMap<String, String>();

		// Request that the returned String will use our label for any values
		// with the commonName OID
		cnMap.put(cnOID, cnLabel);
		String s = p.getName("RFC2253",cnMap);

		//android.util.Log.v("psj", s);

		String cnPrefix = cnLabel + "=";

		int x = s.indexOf(cnPrefix);
		if (x == -1) {
			return null; // No CN values to add
		}

		// Crude attempt to split, noting that this may result in values
		// that contain an escaped comma being chopped between more than one
		// element, so we need to go through this subsequently and handle that..
		String[] split=s.split(",");

		boolean inQuote = false;
		boolean escape = false;

		int e = 0;
		String field = "";

		while (e < split.length) {
			String element = split[e];
			int quoteCount = 0;
			for (int i=0; i<element.length(); i++) {
				char c = element.charAt(i);
				if (c == '"') {
					quoteCount++;
				}
			}
			escape = (element.endsWith("\\"));

			inQuote = ((quoteCount % 2) == 1);
			if (!inQuote && !escape) {
				// We got to the end of a field
				field += element;
				if (field.startsWith(cnPrefix)) {
					//android.util.Log.v("psj", "cn = " +field.substring(cnPrefix.length()));
					result.add(field.substring(cnPrefix.length()));
				}
				field = "";
			}
			else {
				// the split has consumed a comma that was part of a quoted
				// String.
				field = field + element + ",";
			}
			e++;
		}
		if(!result.isEmpty()){
			cns = new String[result.size()];
			result.toArray(cns);
		}
		return cns;
	}


	public static String[] getDNSSubjectAlts(X509Certificate cert) throws CertificateParsingException {
		LinkedList<String> subjectAltList = new LinkedList<String>();
		Collection<List<?>> c = null;

		c = cert.getSubjectAlternativeNames();

		if (c != null) {
			for (List<?> aC : c) {
				List<?> list = aC;
				int type = ((Integer) list.get(0)).intValue();
				// If type is 2, then we've got a dNSName
				if (type == 2) {
					String s = (String) list.get(1);
					subjectAltList.add(s);
				}
			}
		}
		if (!subjectAltList.isEmpty()) {
			String[] subjectAlts = new String[subjectAltList.size()];
			subjectAltList.toArray(subjectAlts);
			return subjectAlts;
		} else {
			return null;
		}
	}


	public static int countDots(final String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '.') {
				count++;
			}
		}
		return count;
	}


	/**
	 * @param verifier the verifier to set
	 */
	public void setHostNameVerifier(X509HostnameVerifier verifier) {
		this.verifier = verifier;
	}

}
