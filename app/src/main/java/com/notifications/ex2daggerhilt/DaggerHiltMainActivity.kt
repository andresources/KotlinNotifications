package com.notifications.ex2daggerhilt

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.notifications.MainActivity
import com.notifications.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DaggerHiltMainActivity : AppCompatActivity() {
    @Inject
    lateinit var mNotificationManager: NotificationManager

    @Inject
    lateinit var mNotificationBuilder: NotificationCompat.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dragger)
        runTimePermission()
        setupNotifications()
    }
    private fun setupNotifications(){
        //Step1:(Taken From Dagger)
        //mNotificationManager= getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        //Step2: Notification Channel -> (Taken From Dagger)
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            mNotificationManager.createNotificationChannel(mChannel)
        }*/

        //Step3: Create Pending Intent -> (Taken From Dagger)
        /*val pendingIntent= PendingIntent.getActivity(this, 420,
            Intent(this, MainActivity::class.java).apply {
                this.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            },
            PendingIntent.FLAG_IMMUTABLE
        )*/

        //Step4: Notification.Builder
        /*mNotificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(androidx.vectordrawable.animated.R.drawable.notification_bg_normal_pressed)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            //.setStyle() -> BigText,BigImage
            .setAutoCancel(true)*/
    }
    private fun runTimePermission(){
        if (Build.VERSION.SDK_INT >= 33) {
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED){
                //User granted notifications
            }else { //User not granted notifications
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS) , 1);
                }
            }
        }
    }

    fun showNotifications(view: View) {
        mNotificationBuilder.setContentTitle("This is title")
            .setContentText("This is context")
        mNotificationManager.notify(222,mNotificationBuilder.build())
    }
}