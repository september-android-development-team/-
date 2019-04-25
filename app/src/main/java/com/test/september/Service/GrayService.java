package com.test.september.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.taobao.sophix.SophixManager;

public class GrayService  extends KeepliveService{
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //do something
        int i = super.onStartCommand(intent, flags, startId);

        Log.d("keeplive","DemoService process = " + android.os.Process.myPid());
//        QueryAndLoadNewPatch();
        return i;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        stopSelf();
    }
//    /**
//     * 查询并加载新补丁，若补丁生效需要冷启动，则在PatchLoadStatusListener监听器中发送广播通知监听的Activity
//     */
//    private void QueryAndLoadNewPatch() {
//        SophixManager.getInstance().queryAndLoadNewPatch();
//        stopSelf();
//    }
}

