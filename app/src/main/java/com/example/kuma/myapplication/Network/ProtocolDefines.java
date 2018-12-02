package com.example.kuma.myapplication.Network;


import com.example.kuma.myapplication.Constance.Constance;

import static com.example.kuma.myapplication.Network.ProtocolDefines.BBUrl.getURL;

public class ProtocolDefines {

	public ProtocolDefines() {
	}

	public static class BBUrl {
		private static final String URL_DEV = "http://106.10.84.105:8080/"; // 개발
		private static final String URL_COM = "http://106.10.84.105:8080/"; // 운영
//		lionska4.cafe24.com
//		umj64-008 | 183.111.100.230
		public static final String getURL()
		{
			if(Constance.DEV) return URL_DEV; else return URL_COM;
		}
	}
//	git remote add origin http://github.com/KumaPark/KumaProduct.git

	public class NetworkDefine {
		public static final String NETWORK_SUCCESS = "0000";
	}

	public static class UrlConstance {

		//단말기 상태 체크
		public static final String CHECK_STATUS = "/chk/getDeviceInfo?";

		public static String URL_LOGIN = getURL() + "mdevice/api_auth/login?";
		public static String URL_MAIN_LIST = getURL() + "mdevice/api_product/get-products?";

	}
}
