package com.test.september.Service;

/**
 * Created by KUNLAN
 * on 2019-04-05
 */
import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;

/**
 * Created by xy on 2016/12/2.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService{
    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
//
//    @Override
//    public boolean onStartJob(JobParameters params) {
//        startMainService();
//        jobFinished(params, false);
//        return true;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters params) {
//        startMainService();
//        return false;
//    }
//
//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        startMainService();
//    }
//
//    public void startMainService(){
//        startService(KeepliveService.getIntentAlarm(this));
//    }
}