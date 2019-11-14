package com.simens.us.myapplication.Network.request;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class SSLFactory extends SSLSocketFactory{
    private SSLContext sslContext = null;


    public SSLFactory(MyTrustManager trustManager, String url) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException
    {
        //super(trustManager.getKeyStore());

//        sslContext = SSLContext.getInstance("TLS");
        sslContext = SSLContext.getInstance("TLS");
//        SSLContext.setDefault(sslContext);
        sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
    }

    public SSLContext getSSLContext()
    {
        return sslContext;
    }

    public Socket createSocket(Socket paramSocket, String paramString,
                               int paramInt, boolean paramBoolean) throws IOException{
        return sslContext.getSocketFactory().createSocket(paramSocket, paramString, paramInt, paramBoolean);
    }

    @Override
    public String[] getDefaultCipherSuites() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getSupportedCipherSuites() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Socket createSocket(String paramString, int paramInt)
            throws IOException, UnknownHostException {
        // TODO Auto-generated method stub
        return sslContext.getSocketFactory().createSocket(paramString, paramInt);
    }

    @Override
    public Socket createSocket(InetAddress paramInetAddress, int paramInt)
            throws IOException {
        // TODO Auto-generated method stub
        return sslContext.getSocketFactory().createSocket(paramInetAddress, paramInt);
    }

    @Override
    public Socket createSocket(String paramString, int paramInt1,
                               InetAddress paramInetAddress, int paramInt2) throws IOException,
            UnknownHostException {
        // TODO Auto-generated method stub
        return sslContext.getSocketFactory().createSocket(paramString, paramInt1, paramInetAddress, paramInt2);
    }

    @Override
    public Socket createSocket(InetAddress paramInetAddress1, int paramInt1,
                               InetAddress paramInetAddress2, int paramInt2) throws IOException {
        // TODO Auto-generated method stub
        return sslContext.getSocketFactory().createSocket(paramInetAddress1, paramInt1, paramInetAddress2, paramInt2);
    }
}