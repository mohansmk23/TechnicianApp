package com.poojaelectronics.technician.common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;

import androidx.core.app.NotificationCompat;

import com.poojaelectronics.technician.R;

public class NotificationUtils
{
    private Context mContext;

    public NotificationUtils( Context mContext )
    {
        this.mContext = mContext;
    }

    public void showSmallNotification( String title, String message, Intent intent, Bitmap bitmap )
    {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder( mContext );
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
        final PendingIntent resultPendingIntent = PendingIntent.getActivity( mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT );
        NotificationManager notificationManager = ( NotificationManager ) mContext.getSystemService( Context.NOTIFICATION_SERVICE );
        String id ;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
        {
            id = "my_channel_01";
            CharSequence name = "new_channel";
            String description = "my  new channel";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel ;
            mChannel = new NotificationChannel( id, name, importance );
            mChannel.setDescription( description );
            mChannel.enableLights( true );
            mChannel.setLightColor( Color.RED );
            mChannel.enableVibration( true );
            mChannel.setVibrationPattern( new long[] { 100, 200, 300, 400, 500, 400, 300, 200, 400 } );
            notificationManager.createNotificationChannel( mChannel );
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder( mContext );
            Notification notification;
            notification = nBuilder.setSmallIcon( R.drawable.pe_logo_small ).setTicker( title ).setWhen( 0 ).setAutoCancel( true ).setContentIntent( resultPendingIntent ).setContentTitle( title ).setStyle( new NotificationCompat.BigTextStyle().bigText( Html.fromHtml( message ) ) ).setLargeIcon( bitmap ).setContentText( Html.fromHtml( message ) ).setChannelId( id ).setVibrate( new long[] { 100, 200, 300, 400, 500, 400, 300, 200, 100 } ).setOngoing( false ).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify( 100, notification );
        }
        else
        {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.addLine( message );
            Notification notification;
            notification = mBuilder.setTicker( title ).setWhen( 0 ).setAutoCancel( true ).setContentTitle( title ).setContentIntent( resultPendingIntent ).setStyle( inboxStyle ).setSmallIcon( R.drawable.pe_logo_small ).setLargeIcon( bitmap ).setContentText( message ).build();
            NotificationManager notiman = ( NotificationManager ) mContext.getSystemService( Context.NOTIFICATION_SERVICE );
            notiman.notify( 100, notification );
        }
    }
}
