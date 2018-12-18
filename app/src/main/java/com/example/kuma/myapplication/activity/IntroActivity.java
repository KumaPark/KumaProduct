package com.example.kuma.myapplication.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.kuma.myapplication.BaseActivity;
import com.example.kuma.myapplication.BuildConfig;
import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.request.ReqLogin;
import com.example.kuma.myapplication.Network.request.ReqVersion;
import com.example.kuma.myapplication.Network.response.ResLogin;
import com.example.kuma.myapplication.Network.response.ResVersion;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.Utils.DateUtility;
import com.example.kuma.myapplication.Utils.DeviceUtils;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.Utils.SharedPref.SharedPref;
import com.example.kuma.myapplication.ui.dialog.CommonDialog;
import com.example.kuma.myapplication.ui.dialog.IDialogListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kuma on 2017-12-12.
 */

public class IntroActivity extends BaseActivity {

    private final static int TAG_VERSION = 100;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000;
    private File outputFile; //파일명까지 포함한 경로
    private File path;//디렉토리경로

    private ProgressDialog progressBar;
    private String m_strApkPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getPermision();
    }

    private void getPermision() {
        // Activity에서 실행하는경우
        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            reqVersion();
        }
    }

    private void gotoLogin(){
//        getDownloadApk();
        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 버젼조회
     */
    private void reqVersion()
    {
        try {
            ReqVersion reqVersion = new ReqVersion();

            reqVersion.setTag(TAG_VERSION);
            requestProtocol(true, reqVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 버젼조회 결과
     */
    private void resVersion(ResVersion resprotocol)
    {
        KumaLog.d("++++++++++++resVersion++++++++++++++");
        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {

            if( resprotocol.isUpdate(DeviceUtils.getAppVersion(this))) {
                    if( resprotocol.isForceUpdate()) {
                        showAlterDialog(true, "보다 안정적이고 편리한 서비스 이용을 위해 앱을 최신버전으로 업데이트 해주세요.");
                    } else {
                        showAlterDialog(false, "보다 안정적이고 편리한 서비스 이용을 위해 앱을 최신버전으로 업데이트 해주세요.");
                    }
            } else {
                gotoLogin();
            }
        }  else {
            if( !TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }
    private void getDownloadApk(){
        progressBar=new ProgressDialog(IntroActivity.this);
        progressBar.setMessage("다운로드중");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setIndeterminate(true);
        progressBar.setCancelable(true);

        String fileUrl = "http://lionskaphp.cafe24.com/apk/app-debug_v1.0.0.apk";

        final DownloadFilesTask downloadTask = new DownloadFilesTask(IntroActivity.this);
        downloadTask.execute(fileUrl);

        progressBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });
//        new apkDownload().execute();
    }
    private void showAlterDialog( boolean forceState, String strMsg){
        CommonDialog updateDlg = new CommonDialog(this);
        if( forceState ) {
            updateDlg.setType(CommonDialog.DLG_TYPE_NOTI);
        } else {
            updateDlg.setType(CommonDialog.DLG_TYPE_YES_NO);
        }

        updateDlg.setDialogListener(DLG_ID_NOTI, new IDialogListener() {
            @Override
            public void onDialogResult(int nTag, int nResult, Dialog dialog) {
                // TODO Auto-generated method stub
                if (nResult == CommonDialog.RESULT_OK) {
                    getDownloadApk();
                } else {
                    gotoLogin();
                }
            }
        });
        if( forceState ) {
            updateDlg.setCancelable(false);
        }
        updateDlg.setMessage(strMsg);
        updateDlg.show();
    }

    private class DownloadFilesTask extends AsyncTask<String, String, Long> {

        private Context context;
//        private PowerManager.WakeLock mWakeLock;

        public DownloadFilesTask(Context context) {
            this.context = context;
        }


        //파일 다운로드를 시작하기 전에 프로그레스바를 화면에 보여줍니다.
        @Override
        protected void onPreExecute() { //2
            super.onPreExecute();

            //사용자가 다운로드 중 파워 버튼을 누르더라도 CPU가 잠들지 않도록 해서
            //다시 파워버튼 누르면 그동안 다운로드가 진행되고 있게 됩니다.
//            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
//            mWakeLock.acquire();

            progressBar.show();
        }


        //파일 다운로드를 진행합니다.
        @Override
        protected Long doInBackground(String... string_url) { //3
            int count;
            long FileSize = -1;
            InputStream input = null;
            OutputStream output = null;
            URLConnection connection = null;

            try {
                URL url = new URL(string_url[0]);
                connection = url.openConnection();
                connection.connect();

                //파일 크기를 가져옴
                FileSize = connection.getContentLength();

                String strFolder = "SIEMENSProduct";
                String savePath = Environment.getExternalStorageDirectory() + File.separator + strFolder + "/temp/";

                path = new File(savePath);
                if( !path.isDirectory() ) {
                    path.mkdirs();
                }
                outputFile = new File(path, "app-debug_v1.0.0.apk"); //파일명까지 포함함 경로의 File 객체 생성

                m_strApkPath = outputFile.getPath();

                if( !outputFile.isFile() ) {
                    //URL 주소로부터 파일다운로드하기 위한 input stream
                    input = new BufferedInputStream(url.openStream(), 8192);

                    // SD카드에 저장하기 위한 Output stream
                    output = new FileOutputStream(outputFile);


                    byte data[] = new byte[1024];
                    long downloadedSize = 0;
                    while ((count = input.read(data)) != -1) {
                        //사용자가 BACK 버튼 누르면 취소가능
                        if (isCancelled()) {
                            input.close();
                            return Long.valueOf(-1);
                        }

                        downloadedSize += count;

                        if (FileSize > 0) {
                            float per = ((float)downloadedSize/FileSize) * 100;
                            String str = "Downloaded " + downloadedSize + "KB / " + FileSize + "KB (" + (int)per + "%)";
                            publishProgress("" + (int) ((downloadedSize * 100) / FileSize), str);
                        }

                        //파일에 데이터를 기록합니다.
                        output.write(data, 0, count);
                    }
                    // Flush output
                    output.flush();

                    // Close streams
                    output.close();
                    input.close();
                }

            } catch (Exception e) {
                KumaLog.e("Error: ", e.getMessage());
            }finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
//                mWakeLock.release();
            }
            return FileSize;
        }


        //다운로드 중 프로그레스바 업데이트
        @Override
        protected void onProgressUpdate(String... progress) { //4
            super.onProgressUpdate(progress);

            // if we get here, length is known, now set indeterminate to false
            progressBar.setIndeterminate(false);
            progressBar.setMax(100);
            progressBar.setProgress(Integer.parseInt(progress[0]));
            progressBar.setMessage(progress[1]);
        }

        //파일 다운로드 완료 후
        @Override
        protected void onPostExecute(Long size) { //5
            super.onPostExecute(size);

            progressBar.dismiss();
            File apkFile = new File(m_strApkPath);
            if( apkFile.isFile() ) {
                Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(Uri.fromFile(apkFile));
                sendBroadcast(mediaScanIntent);


                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    try {
                        Uri apkUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", apkFile);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType( apkUri, "application/vnd.android.package-archive");
                    }catch (Exception e) {
                        KumaLog.e(e.toString());
                    }


                } else {
                    intent.setDataAndType( Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                }

                startActivity(intent);
            } else {
                KumaLog.e("Error File is Not ");
            }
        }

    }
    @Override
    public void onResponseProtocol(int nTag, ResponseProtocol resProtocol) {
        switch (nTag) {
            case TAG_VERSION:
                resVersion((ResVersion)resProtocol);
                break;
                default:
                    break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    reqVersion();
                } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                }
                return;
        }
    }

    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {

    }
}
