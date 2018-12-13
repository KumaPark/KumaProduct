package com.example.kuma.myapplication.Network;


import com.example.kuma.myapplication.Constance.Constance;

import static com.example.kuma.myapplication.Network.ProtocolDefines.BBUrl.getURL;

public class ProtocolDefines {

	public ProtocolDefines() {
	}

	public static class BBUrl {
//		private static final String URL_DEV = "http://106.10.84.105:8080/"; // 개발
		private static final String URL_DEV = "http://lionskaphp.cafe24.com/"; // 개발
		private static final String URL_COM = "http://106.10.84.105:8080/"; // 운영


		public static final String getURL()
		{
			if(Constance.DEV) return URL_DEV; else return URL_COM;
		}
	}

	public class NetworkDefine {
		public static final String NETWORK_SUCCESS = "0000";

		public static final String NO_DEVICE = "E003";
	}

	public static class UrlConstance {

		//단말기 상태 체크
		public static final String CHECK_STATUS = "/chk/getDeviceInfo?";

		public static String URL_LOGIN = getURL() + "mdevice/api_auth/login?";

		// 제품 요약 집계
		public static String URL_SUMMARY_DEVICE = getURL() + "mdevice/api_product/get-summary-by-state?";


		//제품조회 리스트
		public static String URL_MAIN_LIST = getURL() + "mdevice/api_product/get-products?";
		//제품조회
		public static String URL_DEVICE_INFO = getURL() + "mdevice/api_product/get-product?";

		//제품등록
		public static String URL_DEVICE_INSERT = getURL() + "mdevice/api_product/add?";
		//제품수정
		public static String URL_DEVICE_EDIT = getURL() + "mdevice/api_product/edit?";
		//제품삭제
		public static String URL_DEVICE_DELETE = getURL() + "mdevice/api_product/delete?";

		//데모조회 (전체)
		public static String URL_DEVICE_SCHEDULE_LIST = getURL() + "mdevice/api_product/get-products-demo?";
		//데모조회
		public static String URL_DEVICE_SCHEDULE_INFO = getURL() + "mdevice/api_product/get-product-demo?";
		//데모 정보 수정
		public static String URL_DEVICE_SCHEDULE_EDIT = getURL() + "mdevice/api_product/edit-product-demo?";

	}
}
