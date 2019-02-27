package com.example.track.sync;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.track.utilities.ServerUtilities;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class SendLocationService extends JobService {
    public  String TAG="Inside SendLocationService: syncing....";
    private static AsyncTask mBackgroundTask;
    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {

            // COMPLETED (6) Override doInBackground
            @SuppressLint("WrongThread")
            @Override
            protected Object doInBackground(Object[] params) {
                Context context = SendLocationService.this;
                // TODO: 27/2/19 send the location to server
                Log.i(TAG, "doInBackground: ");
                ServerUtilities.syncToServer(context);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
            }
        };
        // COMPLETED (9) Execute the AsyncTask
        mBackgroundTask.execute();
        // COMPLETED (10) Return true
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}
