package danandroid.course.servicesandnotifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.Date;

/**
 * Created by Jakars on 11/07/2017.
 */

public class MyJobService extends JobService {
    private static final String TAG = "Ness";

    @Override
    public boolean onStartJob(final JobParameters job) {
        //do the job:
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {Thread.sleep(1000);} catch (InterruptedException ignored) { }
                showNotification();
                jobFinished(job, true);
            }
        });
        t.start();

        return true; //is there ongoing work?
    }



    @TargetApi(Build.VERSION_CODES.O)
    private void showNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("time", new Date().toString());
        PendingIntent pi = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        Log.d(TAG, "showNotification: ");
//        android.support.v7.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setContentTitle("Hello, From Recurring service");
//        builder.setContentText("This is the Text");
//        builder.setSmallIcon(R.drawable.ic_note);
//        builder.setChannel("channel1");
//        //action:
//        builder.setAutoCancel(true);
//        builder.setContentIntent(pi);
//
//        Notification notification = builder.build();
//
//        NotificationManagerCompat.from(this).notify(1, notification);



        String channelId = "channel2";
        setupChannel(channelId);
        Notification.Builder builder = new Notification.Builder(this, channelId);
        builder.setContentTitle("Hello, From Recurring service");
        builder.setContentText("This is the Text");


        builder.setStyle(new Notification.BigPictureStyle().
                bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.worldwidewebmap)));

        builder.setSmallIcon(R.drawable.ic_note);
       //action:
        builder.setAutoCancel(true);
        builder.setContentIntent(pi);
        Notification notification = builder.build();

        NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mgr.notify(3,builder.build());

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void setupChannel (String id){
        String channelName = getResources().getString(R.string.chanel1_name);
        NotificationChannel channel = new NotificationChannel(id,
                channelName,
                NotificationManager.IMPORTANCE_HIGH);

        channel.setDescription(getResources().getString(R.string.channel_description));
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel.enableVibration(true);
        channel.enableLights(true);
        channel.setSound(RingtoneManager.getDefaultUri(
                RingtoneManager.TYPE_NOTIFICATION),
                Notification.AUDIO_ATTRIBUTES_DEFAULT);

        channel.setShowBadge(true);

        //TODO: custom sounds
        NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mgr.createNotificationChannel(channel);
    }

    private void dostuff(){

    }


    @Override
    public boolean onStopJob(JobParameters job) {
        return false;//should this job be retired?
    }
}
