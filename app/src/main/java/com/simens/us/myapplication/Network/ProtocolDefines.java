package com.simens.us.myapplication.Network;


import com.simens.us.myapplication.Constance.Constance;

import static com.simens.us.myapplication.Network.ProtocolDefines.BBUrl.getURL;

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

		public static String URL_LOGOUT = getURL() + "mdevice/api_auth/logout?";

		//버젼조회
		public static String URL_VERSION = getURL() + "mdevice/api_config/get-version?";

		//상태 체크
		public static String URL_STATE_CHECK = getURL() + "mdevice/api_auth/get-status?";

		//사용자 등록
		public static String URL_USER_REGIST = getURL() + "mdevice/api_member/regist-member?";


		//제품데모 리스트조회
		public static String URL_DEVICE_SCHEDULE_LIST = getURL() + "mdevice/api_product/get-product-demos?";
		//제품데모 상세조회
		public static String URL_DEVICE_SCHEDULE_INFO = getURL() + "mdevice/api_product/get-product-demo?";
		//제품데모 정보 변경
		public static String URL_DEVICE_SCHEDULE_EDIT = getURL() + "mdevice/api_product/edit-product-demo?";
		//제품데모 정보등록
		public static String URL_DEVICE_SCHEDULE_ADD = getURL() + "mdevice/api_product/add-product-demo?";

		//제품데모 상태변경
		public static String URL_DEVICE_SCHEDULE_STATE_EDIT = getURL() + "mdevice/api_product/edit-product-demo-state?";
		//데모 가능한 제품조회
		public static String URL_DEVICE_SCHEDULE_ABLE_LIST = getURL() + "mdevice/api_product/get-demo-able-products?";




		//임상 스케쥴 리스트조회
		public static String URL_MEMBER_SCHEDULE = getURL() + "mdevice/api_member/get-schedules?";
		//임상 스케줄 조회 상세
		public static String URL_MEMBER_SCHEDULE_DETAIL = getURL() + "mdevice/api_member/get-schedule?";
		//임상 스케줄 수정
		public static String URL_MEMBER_SCHEDULE_EDIT = getURL() + "mdevice/api_member/edit-schedule?";
		//임상 스케줄 등록
		public static String URL_MEMBER_SCHEDULE_ADD = getURL() + "mdevice/api_member/add-schedule?";

		//본사 및 대리점명 조회
		public static String URL_AGENCY_SEARCH = getURL() + "mdevice/api_member/get-agency-names?";
        //본사 및 대리점 사원 조회
        public static String URL_AGENCY_MEMBER_SEARCH = getURL() + "mdevice/api_member/get-member-names?";


		//스케쥴 등록
		public static String URL_SCHEDULE_ADD = getURL() + "mdevice/api_member/add-schedule?";
		//스케쥴 수정
		public static String URL_SCHEDULE_EDIT = getURL() + "mdevice/api_member/edit-schedule?";
		//스케쥴 상세
		public static String URL_SCHEDULE_DETAIL = getURL() + "mdevice/api_member/get-schedules?";

		//목적지 조회
		public static String URL_DESTNATION_LIST = getURL() + "mdevice/api_product/get-destination-names?";


		//제품모델 조회
		public static String URL_DEVICE_MODEL_LIST = getURL() + "mdevice/api_product/get-model-names?";

		//장비의OS버젼정보 조회
		public static String URL_DEVICE_OS_LIST = getURL() + "mdevice/api_product/get-mdevice-os-names?";


		//제품등록
		public static String URL_DEVICE_ADD = getURL() + "mdevice/api_product/add-product?";

		//제품수정
		public static String URL_DEVICE_EDIT = getURL() + "mdevice/api_product/edit-product?";


		//제품조회
		public static String URL_DEVICE_INFO = getURL() + "mdevice/api_product/get-product-demos?";




		// 제품 요약 집계
		public static String URL_SUMMARY_DEVICE = getURL() + "mdevice/api_product/get-summary-by-state?";


		//제품조회 리스트
		public static String URL_MAIN_LIST = getURL() + "mdevice/api_product/get-products?";

		//제품삭제
		public static String URL_DEVICE_DELETE = getURL() + "mdevice/api_product/delete?";



	}
}
