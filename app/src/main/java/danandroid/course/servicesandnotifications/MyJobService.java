package danandroid.course.servicesandnotifications;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Jakars on 11/07/2017.
 */

public class MyJobService extends JobService {
    private static final String TAG = "Ness";

    @Override
    public boolean onStartJob(JobParameters job) {
        //do the job:
        showNotification();

        return false; //is there ongoing progress?
    }

    private void showNotification() {
        Log.d(TAG, "showNotification: ");
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;//should this job be retired?
    }
}
