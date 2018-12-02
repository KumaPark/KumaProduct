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

}

