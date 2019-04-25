package com.test.september;

import android.app.Activity;
import android.app.Application;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.test.september.util.APKVersionCodeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KUNLAN
 * on 2019-04-09
 */
public class App extends Application {
    private static final String VALUE = "Harvey";
    public static List<Object> activitys = new ArrayList<Object>();
    private static App instance;
    private String value;

    @Override
    public void onCreate()
    {
        super.onCreate();
//        SophixManager.getInstance().queryAndLoadNewPatch();

//        SophixManager.getInstance().setContext(this)
//                .setAppVersion(versionName)
//                .setAesKey(null)
//                .setEnableDebug(true)
//                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
//                    @Override
//                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
//                        // 补丁加载回调通知
//                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
//                            // 表明补丁加载成功
//                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
//                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
//                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
//                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
//                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
//                            // SophixManager.getInstance().cleanPatches();
//                        } else {
//                            // 其它错误信息, 查看PatchStatus类说明
//                        }
//                    }
//                }).initialize();

//        setValue(VALUE); // 初始化全局变量
    }

//    public void setValue(String value)
//    {
//        this.value = value;
//    }
//
//    public String getValue()
//    {
//        return value;
//    }

    public static App getInstance() {
        if (instance == null)
        instance = new App();
        return instance;
}

        // 添加Activity到容器中
        public void addActivity(Activity activity) {
        if (!activitys.contains(activity))
            activitys.add(activity);
    }

        // 遍历所有Activity并finish
    public void destory() {
        for (Object activity : activitys) {
            ((Activity) activity).finish();
        }
        System.exit(0);
    }
}

