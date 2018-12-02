package com.example.kuma.myapplication.Network.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.CertificateEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kuma.myapplication.Network.ProtocolListener;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.Utils.KumaLog;


public abstract class RequestJSON {

	private static final int TIME_OUT_REQUEST_CONNECT = 10000;
	private static final int TIME_OUT_REQUEST_READ = 10000;


	private ProtocolListener m_listener = null;

	public abstract String getURL();
	public abstract Integer getTAG();
	public abstract StringBuffer getJson();
	protected abstract ResponseProtocol createResponseProtocol();

	private static Context m_Context;

	private static String serverURL;

	public void request(Context context, ProtocolListener listener)
	{		
		m_Context = context;
		m_listener = listener;

		new ReqJsonAsync().execute();
	}

	class ReqJsonAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		JSONObject jsonChannels;
		@Override
		protected String doInBackground(String... aurl) {

			try {

				jsonChannels = getHttpJson(getURL(), getJson() );
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onProgressUpdate(String... progress) {
		}

		@Override
		protected void onPostExecute(String unused) {
			//write jsondata to file
			try {
				if( jsonChannels != null )
				{
					ResponseProtocol resPortocol = createResponseProtocol();

					if(resPortocol != null)
					{
						resPortocol.setBodyAndParsing(jsonChannels);
					}
					m_listener.onResponse( getTAG(), resPortocol);
				}
				else
				{
					m_listener.onResponse( getTAG(), null);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private static String convertStreamToString(InputStream is) {
		StringBuilder sb = new StringBuilder();
		if( is != null ){
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	public JSONObject getHttpJson(String url, StringBuffer burffer) {
		JSONObject json = null;
		try {
			String result = libRequest(url, burffer);
			if( result != null ){
				json = new JSONObject(result);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}


	@SuppressWarnings("deprecation")
	public String libRequest(String url, StringBuffer burffer) {
		InputStream tmp = null;
		String result = "";
		URL m_sConnectUrl = null;
		HttpURLConnection http = null;

		try {
			KumaLog.d(" url " + url);
			KumaLog.d(" burffer " + burffer.toString());

			m_sConnectUrl = new URL(url);

			http = (HttpURLConnection) m_sConnectUrl.openConnection();

			http.setDefaultUseCaches(false);
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setReadTimeout(TIME_OUT_REQUEST_READ);
			http.setConnectTimeout(TIME_OUT_REQUEST_CONNECT);

			http.setRequestMethod("POST");
			http.setRequestProperty("connect-type",
					"application/x-www-urlencoded");

			OutputStreamWriter outStream = new OutputStreamWriter(
					http.getOutputStream(), "UTF-8");

			PrintWriter writer = new PrintWriter(outStream);
			writer.write( burffer.toString() );
			writer.flush();

			tmp = http.getInputStream();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			result = convertStreamToString(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			if( tmp != null ){
				tmp.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			http.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Log.w("Kuma","result : " + result);
		} catch (Exception e) {

		}


		return result;
	}
	private static SSLContext sslContext;
	private static byte[] getServerCert()
	{
		if (sslContext != null) {
			SSLSessionContext cSessionContext = sslContext
					.getClientSessionContext();

			Enumeration<byte[]> sessionids = cSessionContext.getIds();
			javax.security.cert.X509Certificate x509 = null;

			//session 1개만 처리..
			if ((sessionids != null) && (sessionids.hasMoreElements() == true)) {

				byte[] tsession = sessionids.nextElement();

				byte[] encodedcert = null;
				try {
					x509 = cSessionContext.getSession(tsession).getPeerCertificateChain()[0];

					encodedcert = x509.getEncoded();
				} catch (SSLPeerUnverifiedException e) {
					e.printStackTrace();
				} catch (CertificateEncodingException e2) {
					e2.printStackTrace();
				} catch (SSLException e1) {
					e1.printStackTrace();
				}
				return encodedcert;
			}
		}else{

		}
		return null;
	}
}
