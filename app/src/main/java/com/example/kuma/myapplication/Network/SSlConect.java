package com.example.kuma.myapplication.Network;


import com.example.kuma.myapplication.Network.request.MyTrustManager;
import com.example.kuma.myapplication.Utils.KumaLog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Created by 재성 on 2016-11-21.
 */
public class SSlConect {

    public SSlConect(){

    }

    public boolean getSSLState(){
        return  m_bSSLState;
    }

    MyTrustManager tm = new MyTrustManager();
    private boolean m_bSSLState = true;
    private static SSLContext sslContext;
    public HttpsURLConnection getHttpsConnection (String strurl) {

        try {
            tm.setServerURL(strurl);

            KumaLog.e("========================= makeSSLFactory =================================");
            tm.setHostNameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
//			tm.setHostNameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            KumaLog.d("========================= makeSSLFactory =================================");

            tm.setOnAuthCallbackListhner(new MyTrustManager.onadtAuthCallback() {
                @Override
                public void onSuccess() {
                    m_bSSLState = true;
                }

                @Override
                public void onError(String strError) {
                    KumaLog.v("onError strError is " + strError );
                    m_bSSLState = false;
                }
            });
        } catch (Exception e) {
            KumaLog.e("makeSSLFactory Exception is " + e.toString() );
        }

        try {
            KumaLog.d("========================= makeSSLFactory 1 =================================");
            SSLSocketFactory ddd = new TLSSocketFactory();
//			sslContext = SSLContext.getInstance("SSL");
            KumaLog.d("========================= makeSSLFactory 2 =================================");
//			sslContext.init(null, new TrustManager[]{tm}, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(ddd);

            try {
                KumaLog.d("========================= sslContext.getProtocol() =================================\n" + sslContext.getProtocol());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            KumaLog.d("checkClientTrusted Exception is " + e.toString());
            e.printStackTrace();
        }

        HttpsURLConnection httpsConn = null;
        try {
            URL strConnectionUrl = new URL( strurl);
            httpsConn =(HttpsURLConnection) strConnectionUrl.openConnection();
            httpsConn.setHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    KumaLog.d("setHostnameVerifier session is " + session);
                    // TODO Auto-generated method stub
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpsConn;

    }

    public class TLSSocketFactory extends SSLSocketFactory {

        private SSLSocketFactory internalSSLSocketFactory;

        public TLSSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {
//			SSLContext context = SSLContext.getInstance("TLS");
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{tm}, new SecureRandom());
            internalSSLSocketFactory = sslContext.getSocketFactory();
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return internalSSLSocketFactory.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return internalSSLSocketFactory.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return enableTLSOnSocket(internalSSLSocketFactory.createSocket(address, port, localAddress, localPort));
        }

        private Socket enableTLSOnSocket(Socket socket) {
            if(socket != null && (socket instanceof SSLSocket)) {
                ((SSLSocket)socket).setEnabledProtocols(new String[] {"TLSv1.2"});
            }
            return socket;
        }
    }
}
