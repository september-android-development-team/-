package com.test.september;

/**
 * Created by KUNLAN
 * on 2019-04-09
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Keep;
import android.widget.Toast;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.test.september.util.APKVersionCodeUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * hotfix专用Application类，无其他业务
 */
public class SophixStubApplication extends SophixApplication {

    /**
     * 此处SophixEntry应指定真正的Application, 这里为MyApplication, 并且保证RealApplicationStub类名不被混淆。
     */
    @Keep
    @SophixEntry(App.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initSophix();
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    /**
     * hotfix初始化工作
     */
    private void initSophix() {
        String verName = "";
        try {
            verName = this.getPackageManager().
                    getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(verName)
                .setSecretMetaData("25977075", "685fdd2f4cb9c48fd6a8f14726001161", "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDQ7SpeDm8L5igUT/QjOPkYKHpZKX7PFaf+xjFqW1ja949oSn2Zxtq3mUPB5wimB95yleA4GOP6+ASwYQ/Ou8DEXR46/psurmpVXFgRBQz2i8lBeYcj3RPEddFjn9yTgbSb5ht343JVQVmd+Sk0jfeMDC0wpUqcDjI7JyGXLlLYNSpXbGzImYr3TJ91mgPhyxbEPlj9OK8LkJX2Vszape721c6CfHtMu4w0AV+/u3CoVr6BJeqz1+wD04jWFWG9qecy3crI5GJqwKrOYrNgwax0fayksn494Ljkch4JvwkljuC1jVyz+fOTkR/iN4iUfKkWHz0AQJ1Ga0+qmZJmyPHDAgMBAAECggEASOeJkjqtK1TMBZKE2cmmunRdhUGCm5auAax9T+TCU95xL25W99M5PToBN+d70BwM1qVQEaM14ryrPjUQhA5mTN9FjkaMNjTsrA7GOGIvzNSojO1wZ+ZcYIDQaVg+a0amlIQub55xpihgskxgwZMMn9Ub686yA1S82IFmKlAi3ojFI16+eNKx2l8On9jqENWTCR+BgTZ7fkrCIxuI2d2bhUZJiNomzHSlr5QTKkbrxpSVrv3jfe+vX3hWtAga3WR/YpBix65qhzo36ZeJJFmSiB03GOt5EuNeRuf8I/Ux8DE4NwVE9Q8ednU0dA8I8rITNaZEA/uSINoYEMydHlwFAQKBgQD1uErotfXSuMiRrzdVP9vfJ/xmbC2chwI2bsKYv+5gsyM/PV/H/f7+XwfGu5mjx/5oZSMIDGryjHyHK+7088lXK3Pk3lFpRLCz5yTn0CNocu9zTPbqqoozxna21itSZ1SE314RSyfjfDAnlrCC5r5GSeEPfL+0qX9fY088/idogQKBgQDZqs7K5z+8ntILNmkyuhK6Bq+ZS8jwuZ0iLPzs+NO6KH9tPPGhCwSTbS9jgQNzxrH0QSiMZL1pmL8fKxv+PYgTM6q4Rt43360Vp+ZN6VAhqnH3rRIkJ9uJ6pYpjkHliqpuMUQ3fcZ1t4mvJjNVlfppBl53MOrcRbGpnFjxZmyYQwKBgEgydhAaJrhbgtMLYH6z3K6c2RRfBKfRI11K/fJTsn7HI7wZFGN2xc2Hrok95c6xnJaf4MMkoufRT2WICxX961KpecvtZo4/mIvDhpx2E3FFFAYUvxgEM8cysP7XvrLfmX3RA5qhMn8sphl7tTFBCvcsDey3ArzacIpea5HIO1wBAoGAUFrwUJOiQf3XyO/jUijDNQ220b7ahZPgYHd/P/R0JWtB74/qG+u3Wr2aGv/LyHf3xhYv4/RkwJD1LYp6oRcSHxSZ18HB05qtiSscqHKTdHo57Jdl9RcJwxZpLRopVZYlaNyjNGEoXNjO6WwO02+0rAT/5ryWV48bEWR4qVWIjYUCgYA3alR1WXTeWUOm8UIl6aZVBLdF8uJXDX/gqgRzcyfNcBfNLdCRx4gsITPw1q/fcZTizrkI8Zqw/A5NpP4yYMmVMz7dVO3A9AxJ4PdxbBQGNCq9INn0FeH7fOjmLmjFuwE8SW9yNqyEQHGwxIFBEELWjDCV0cYdWVONGft6BNXjyg==")
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_RELAUNCH) {

                            //补丁生效需要冷启动
                            Intent intent = new Intent("PATCH_RELAUNCH");
                            //发送广播通知更新
                            sendBroadcast(intent);
//                            // 新补丁生效需要重启, 强制退出;
//                            Toast.makeText(getApplicationContext(),"有重要补丁更新，需要重启本应用!",Toast.LENGTH_SHORT).show();
//                            TimerTask task = new TimerTask() {
//                                @Override
//                                public void run() {
//                                    /**
//                                     *要执行的操作
//                                     */
//                                    SophixManager.getInstance().killProcessSafely();
//                                }
//                            };
//                            Timer timer = new Timer();
//                            timer.schedule(task, 3000);//3秒后执行TimeTask的run方法

                        }
                    }
                }).initialize();
    }
}