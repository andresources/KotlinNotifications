package com.notifications.ex2daggerhilt

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.notifications.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context,mChannel: NotificationChannel) : NotificationManager{
        val mNotificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(mChannel)
        }
        return mNotificationManager;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideNotificationChannel() : NotificationChannel{
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        return mChannel
    }

    @Provides
    fun providePendingIntent(@ApplicationContext context: Context) : PendingIntent{
        return PendingIntent.getActivity(context, 420,
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                action = "Delete"
            },
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    @Provides
    fun provideNotificationBuilder(@ApplicationContext context: Context,pendingIntent: PendingIntent) : NotificationCompat.Builder{
       return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(androidx.vectordrawable.animated.R.drawable.notification_bg_normal_pressed)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            //.setStyle() -> BigText,BigImage
            .setAutoCancel(true)
    }

}