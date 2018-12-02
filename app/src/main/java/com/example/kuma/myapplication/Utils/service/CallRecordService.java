package com.example.kuma.myapplication.Utils.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.kuma.myapplication.Utils.CallRecord;
import com.example.kuma.myapplication.Utils.helper.PrefsHelper;

import java.io.File;
import java.io.IOException;


/**
 * Created by aykutasil on 19.10.2016.
 */

public class CallRecordService extends Service {

    private static final String TAG = CallRecordService.class.getSimpleName();

    protected CallRecord mCallRecord;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand()");

        String file_name = PrefsHelper.readPrefString(this, CallRecord.PREF_FILE_NAME);
        String dir_path = PrefsHelper.readPrefString(this, CallRecord.PREF_DIR_PATH);
        String dir_name = PrefsHelper.readPrefString(this, CallRecord.PREF_DIR_NAME);
        boolean show_seed = PrefsHelper.readPrefBool(this, CallRecord.PREF_SHOW_SEED);
        boolean show_phone_number = PrefsHelper.readPrefBool(this, CallRecord.PREF_SHOW_PHONE_NUMBER);
        int output_format = PrefsHelper.readPrefInt(this, CallRecord.PREF_OUTPUT_FORMAT);
        int audio_source = PrefsHelper.readPrefInt(this, CallRecord.PREF_AUDIO_SOURCE);
        int audio_encoder = PrefsHelper.readPrefInt(this, CallRecord.PREF_AUDIO_ENCODER);

        mCallRecord = new CallRecord.Builder(this)
                .setRecordFileName(file_name)
                .setRecordDirName(dir_name)
                .setRecordDirPath(dir_path)
                .setAudioEncoder(audio_encoder)
                .setAudioSource(audio_source)
                .setOutputFormat(output_format)
                .setShowSeed(show_seed)
                .setShowPhoneNumber(show_phone_number)
                .build();

        Log.i(TAG, "mCallRecord.startCallReceiver()");
//        mCallRecord.startCallReceiver();

        try {
            startRecord(this, "", "");
        } catch ( Exception e) {
            e.printStackTrace();
        }

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mCallRecord.stopCallReceiver();

        stopRecord(this);
        Log.i(TAG, "onDestroy()");
    }
    private static MediaRecorder recorder;
    private File audiofile;
    private boolean isRecordStarted = false;
    private void startRecord(Context context, String seed, String phoneNumber) {
        try {
            boolean isSaveFile = PrefsHelper.readPrefBool(context, CallRecord.PREF_SAVE_FILE);
            Log.i(TAG, "isSaveFile: " + isSaveFile);

            // dosya kayıt edilsin mi?
            if (!isSaveFile) {
                return;
            }

            String file_name = PrefsHelper.readPrefString(context, CallRecord.PREF_FILE_NAME);
            String dir_path = PrefsHelper.readPrefString(context, CallRecord.PREF_DIR_PATH);
            String dir_name = PrefsHelper.readPrefString(context, CallRecord.PREF_DIR_NAME);
            boolean show_seed = PrefsHelper.readPrefBool(context, CallRecord.PREF_SHOW_SEED);
            boolean show_phone_number = PrefsHelper.readPrefBool(context, CallRecord.PREF_SHOW_PHONE_NUMBER);
            int output_format = PrefsHelper.readPrefInt(context, CallRecord.PREF_OUTPUT_FORMAT);
            int audio_source = PrefsHelper.readPrefInt(context, CallRecord.PREF_AUDIO_SOURCE);
            int audio_encoder = PrefsHelper.readPrefInt(context, CallRecord.PREF_AUDIO_ENCODER);

            File sampleDir = new File(dir_path + "/" + dir_name);

            if (!sampleDir.exists()) {
                sampleDir.mkdirs();
            }


            StringBuilder fileNameBuilder = new StringBuilder();

            if (show_phone_number) {
                fileNameBuilder.append(phoneNumber);
                fileNameBuilder.append("_");
            }

            fileNameBuilder.append(file_name);

            file_name = fileNameBuilder.toString();

            String suffix = "";
            switch (output_format) {
                case MediaRecorder.OutputFormat.AMR_NB: {
                    suffix = ".amr";
                    break;
                }
                case MediaRecorder.OutputFormat.AMR_WB: {
                    suffix = ".amr";
                    break;
                }
                case MediaRecorder.OutputFormat.MPEG_4: {
                    suffix = ".mp4";
                    break;
                }
                case MediaRecorder.OutputFormat.THREE_GPP: {
                    suffix = ".3gp";
                    break;
                }
                default: {
                    suffix = ".amr";
                    break;
                }
            }
            Log.d(TAG, " file_name " + file_name);
            Log.d(TAG, " suffix " + suffix);
            Log.d(TAG, " sampleDir " + sampleDir);

            audiofile = File.createTempFile(file_name, suffix, sampleDir);

            if (recorder != null) {
                recorder.stop();
                recorder.release();
                recorder = null;
            }

            recorder = new MediaRecorder();
            recorder.setAudioSource(audio_source);
            recorder.setOutputFormat(output_format);
            recorder.setAudioEncoder(audio_encoder);
            recorder.setOutputFile(audiofile.getAbsolutePath());

            recorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                @Override
                public void onError(MediaRecorder mediaRecorder, int i, int i1) {
                    Log.i("CallRecord", i + "");
                    Log.i("CallRecord", i1 + "");
                }
            });

            recorder.prepare();
            recorder.start();

            isRecordStarted = true;

            Log.i(TAG, "record start");
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecord(Context context) {
        try {
            if (recorder != null && isRecordStarted) {
                recorder.stop();
                recorder.reset();
                recorder.release();
                recorder = null;

                isRecordStarted = false;

                Log.i(TAG, "record stop");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
