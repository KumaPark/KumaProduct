package com.example.kuma.myapplication.Constance;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.data.DropBoaxCommonDTO;

import java.util.ArrayList;

/**
 * Created by Kuma on 2018-01-20.
 */

public class Constance {

    public final static boolean DEV = true;
    public final static boolean LOG_STATE = true;
    //            permission : 권한 ex) 1 : 대리점, 32 : 임상, 64 : 관리자
    public static int USER_PERMISSION = 32;

    public static String USER_NAME = "";

    public final static String NETWORK_ERR = "네트워크 상태가 불안정합니다, 다시 시도해주세요.";


    public static ArrayList<DropBoaxCommonDTO> mArrDestinationList = new ArrayList<>();
    public static ArrayList<DropBoaxCommonDTO> mArrAgencyList = new ArrayList<>();


    public static DropBoaxCommonDTO getDestinationData(int pk){
        KumaLog.d("  getDestinationData pk  >>>  " + pk);
        DropBoaxCommonDTO data = null;
        for (int i = 0; i < mArrDestinationList.size();  i++)  {
            KumaLog.i("  getAgencyData getPk  >>>  " + mArrDestinationList.get(i).getPk());
            KumaLog.i("  getAgencyData getName  >>>  " + mArrDestinationList         .get(i).getName());
            if(mArrDestinationList.get(i).pk ==  pk)  {
                data = mArrDestinationList.get(i);
                KumaLog.d("  getDestinationData getName  >>>  " + data.getName());
            }
        }
        return data;
    }

    public static DropBoaxCommonDTO getAgencyData(int pk){
        KumaLog.d("  getAgencyData pk  >>>  " + pk);
        DropBoaxCommonDTO data = null;
        for (int i = 0; i < mArrAgencyList.size();  i++)  {
            KumaLog.i("  getAgencyData getPk  >>>  " + mArrAgencyList.get(i).getPk());
            KumaLog.i("  getAgencyData getName  >>>  " + mArrAgencyList.get(i).getName());
            if(mArrAgencyList.get(i).pk ==  pk)  {
                data = mArrAgencyList.get(i);
                KumaLog.d("  getAgencyData getName  >>>  " + data.getName());
            }
        }
        return data;
    }


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
    public static final String TAG_DEVICE_STATE_MOVE_HOSPITAL = "MH";
    public static final String TAG_DEVICE_STATE_DEMO = "I";
    public static final String TAG_DEVICE_STATE_MOVE_COMPANY = "MC";
    public static final String TAG_DEVICE_STATE_DEMO_COMPLETE = "C";
    public static final String TAG_DEVICE_STATE_DEMO_CANCLE = "CN";

    public static String changeStringToState(String strCode){
        String result = "";

        switch (strCode) {
            case TAG_DEVICE_STATE_STANDBY:
                result = "예약";
                break;
            case TAG_DEVICE_STATE_MOVE_HOSPITAL:
                result = "이동(병원)";
                break;
            case TAG_DEVICE_STATE_DEMO:
                result = "데모중";
                break;
            case TAG_DEVICE_STATE_MOVE_COMPANY:
                result = "이동(회사)";
                break;
            case TAG_DEVICE_STATE_DEMO_COMPLETE:
                result = "데모완료";
                break;
            case TAG_DEVICE_STATE_DEMO_CANCLE:
                result = "취소";
                break;
            default:
                break;
        }
        return result;
    }

    public static void changeStateView(String strCode, LinearLayout[]  back, TextView[] text){
        int position = 0;

        switch (strCode) {
            case TAG_DEVICE_STATE_STANDBY:
                position = 0;
                break;
            case TAG_DEVICE_STATE_MOVE_HOSPITAL:
                position = 1;
                break;
            case TAG_DEVICE_STATE_DEMO:
                position = 2;
                break;
            case TAG_DEVICE_STATE_MOVE_COMPANY:
                position = 3;
                break;
            case TAG_DEVICE_STATE_DEMO_COMPLETE:
                position = 4;
                break;
            case TAG_DEVICE_STATE_DEMO_CANCLE:
                position = 5;
                break;
            default:
                break;
        }

        for( int i = 0; i < back.length; i++) {
            if( i == position ) {
                back[i].setBackgroundResource(R.drawable.back_state_sel);
                text[i].setTextColor(Color.parseColor("#ffffff"));
            } else {
                back[i].setBackgroundResource(R.drawable.back_state_non_sel);
                text[i].setTextColor(Color.parseColor("#222222"));
            }
        }
    }

    public static void changeScheduleView(String strCode, LinearLayout[]  back, TextView[] text){
        int position = 0;
//        - kind : 스케쥴 구분 ex) D : 데모, A : AS, O : 회사, E : 기타
        switch (strCode) {
            case "D":
                position = 0;
                break;
            case "A":
                position = 1;
                break;
            case "O":
                position = 2;
                break;
            case "E":
                position = 3;
                break;
            default:
                break;
        }

        for( int i = 0; i < back.length; i++) {
            if( i == position ) {
                back[i].setBackgroundResource(R.drawable.back_state_sel);
                text[i].setTextColor(Color.parseColor("#ffffff"));
            } else {
                back[i].setBackgroundResource(R.drawable.back_state_non_sel);
                text[i].setTextColor(Color.parseColor("#222222"));
            }
        }
    }


}

