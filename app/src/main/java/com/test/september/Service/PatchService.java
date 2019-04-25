package com.test.september.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.taobao.sophix.SophixManager;
import com.test.september.R;
import com.test.september.constant.NotificationIDConstant;

public class PatchService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //构建通知消息
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel mChannel = null;
            mChannel = new NotificationChannel(String.valueOf(NotificationIDConstant.PATCH_NOTIFICATION_ID), "1", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.info_small_icon)
                    .setChannelId(String.valueOf(NotificationIDConstant.PATCH_NOTIFICATION_ID))
                    .setContentTitle("检查补丁更新")
                    .setContentText("正在查询和加载应用新补丁...")
                    .setWhen(System.currentTimeMillis()).setAutoCancel(true)
                    .build();
        } else {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("检查补丁更新").setContentText("正在查询和加载应用新补丁...")
                    .setWhen(System.currentTimeMillis()).setAutoCancel(true);
            notification = builder.build();
        }
        //显示新服务的用户可见通知
        startForeground(NotificationIDConstant.PATCH_NOTIFICATION_ID, notification);
        //查询并加载服务器新的可用补丁
        QueryAndLoadNewPatch();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 查询并加载新补丁，若补丁生效需要冷启动，则在PatchLoadStatusListener监听器中发送广播通知监听的Activity
     */
    private void QueryAndLoadNewPatch() {
        SophixManager.getInstance().queryAndLoadNewPatch();
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

