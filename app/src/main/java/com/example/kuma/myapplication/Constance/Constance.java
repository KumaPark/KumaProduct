package com.example.kuma.myapplication.Constance;

/**
 * Created by Kuma on 2018-01-20.
 */

public class Constance {

    public final static boolean DEV = true;
    public final static boolean LOG_STATE = true;

    public final static String NETWORK_ERR = "네트워크 상태가 불안정합니다, 다시 시도해주세요.";

    /**
     *  장비 :  NX3, NX2, NX3 Elite NX2 Elite  ( 장비명, 기간, 시리얼, 병원명, 상태값, OS Vesion, 담당자)
     *  트랜듀서 : NX2 >> C5-2v, L10-5v
     *   MX3, NX2 >> CH%-2, VF10-5, P4-2
     *
     *
     *
     */
    /**
     * 바코드  Type
     *  장비
     *  트랜듀서
     *  풋 스위치
     *  바이옵시
     *  동글
     *  워크스테이션
     */
    public static final int TAG_CAPTURE_DEVICE = 1001;
    public static final int TAG_CAPTURE_TRANDUCER = 1002;
    public static final int TAG_CAPTURE_FOOT_SWITCH = 1003;
    public static final int TAG_CAPTURE_BIOPSY = 1004;
    public static final int TAG_CAPTURE_DOGGLE = 1005;
    public static final int TAG_CAPTURE_WORK_STATION = 1006;

    /**
     * 장비 상태
     *
       R	대기 : 회사에서 장비를 보관 중인 상태

     M	이동 : 배달자가 회사에서 장비를 꺼내고 이동 시키는 상태
     - 배달자 정보가 보관 됨

     RE	수령 : 수령자가 배달자로 부터 물건을 받은 상태
     - 수령자 정보가 보관 됨

     DS	데모중 : 병원에서 데모 테스트를 하고있는 상태
     - 데모시작일이 보관 됨

     DE	데모완료 : 병원에서 데모 테스트를 종료한 상태
     - 데모종료일이 보관 됨

     특이 사항 (장비가 고장난 경우의 상태가 필요)
     고장 : 장비에 문제가있는 상태

     수리 : 장비를 수리하고있는 상태

     폐기 : 장비를 폐기처분한 상태

     판매완료 : 데모제품을 판매한 상태
     */
    public static final String TAG_DEVICE_STATE_STANDBY = "R";
    public static final String TAG_DEVICE_STATE_MOVE_HOSPITAL = "M";
    public static final String TAG_DEVICE_STATE_MOVE_COMPANY = "MC";
    public static final String TAG_DEVICE_STATE_RECEIVE = "RE";
    public static final String TAG_DEVICE_STATE_DEMO_ING = "DS";
    public static final String TAG_DEVICE_STATE_DEMO_COMPLETE = "DE";

    public static final String TAG_DEVICE_STATE_TROBLE = "TS1";
    public static final String TAG_DEVICE_STATE_REPAIR = "TS2";
    public static final String TAG_DEVICE_STATE_SCRAP = "TS3";
    public static final String TAG_DEVICE_STATE_SALE = "TS4";

    public static String changeStringToState(String strCode){
        String result = "";

        switch (strCode) {
            case TAG_DEVICE_STATE_STANDBY:
                result = "대기";
                break;
            case TAG_DEVICE_STATE_MOVE_HOSPITAL:
                result = "병원이동";
                break;
            case TAG_DEVICE_STATE_MOVE_COMPANY:
                result = "회사이동";
                break;
            case TAG_DEVICE_STATE_RECEIVE:
                result = "수령";
                break;
            case TAG_DEVICE_STATE_DEMO_ING:
                result = "데모중";
                break;
            case TAG_DEVICE_STATE_DEMO_COMPLETE:
                result = "데모완료";
                break;
            case TAG_DEVICE_STATE_TROBLE:
                result = "고장";
                break;
            case TAG_DEVICE_STATE_REPAIR:
                result = "수리중";
                break;
            case TAG_DEVICE_STATE_SCRAP:
                result = "폐기";
                break;
            case TAG_DEVICE_STATE_SALE:
                result = "판매";
                break;
            default:
                break;
        }

        return result;
    }
}

