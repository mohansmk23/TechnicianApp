package com.poojaelectronics.technician.common;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;

import androidx.core.app.NotificationCompat;

import com.poojaelectronics.technician.R;

public class NotificationUtils
{
    private Context mContext;
    private Uri defaultSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );

    public NotificationUtils( Context mContext )
    {
        this.mContext = mContext;
    }

    public void showSmallNotification( String title, String message, Intent intent, Bitmap bitmap )
    {
        PendingIntent resultPendingIntent = PendingIntent.getActivity( mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT );
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder( mContext );
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
        NotificationManager notificationManager = ( NotificationManager ) mContext.getSystemService( Context.NOTIFICATION_SERVICE );
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
        {
            CharSequence name = "Technician Details";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel ;
            String id = "my_channel_01";
            mChannel = new NotificationChannel( id, name, importance );
            mChannel.enableLights( true );
            mChannel.setLightColor( Color.RED );
            mChannel.enableVibration( true );
            mChannel.setVibrationPattern( new long[] { 100, 200, 300, 400, 500, 400, 300, 200, 400 } );
            mBuilder.setSmallIcon( R.drawable.pe_logo_small ).setTicker( title ).setWhen( 0 ).setAutoCancel( true ).setContentIntent( resultPendingIntent ).setContentTitle( title ).setStyle( new NotificationCompat.BigTextStyle().bigText( Html.fromHtml( message ) ) ).setLargeIcon( bitmap ).setSound( defaultSound ).setContentText( Html.fromHtml( message ) ).setChannelId( id ).setVibrate( new long[] { 100, 200, 300, 400, 500, 400, 300, 200, 100 } ).setOngoing( false );
            assert notificationManager != null ;
            notificationManager.createNotificationChannel( mChannel );
            notificationManager.notify( 100, mBuilder.build() );
        }
        else
        {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.addLine( message );
            mBuilder.setTicker( title ).setWhen( 0 ).setAutoCancel( true ).setContentTitle( title ).setContentIntent( resultPendingIntent ).setStyle( inboxStyle ).setSmallIcon( R.drawable.pe_logo_small ).setLargeIcon( bitmap ).setContentText( message );
            NotificationManager notificationManager1 = ( NotificationManager ) mContext.getSystemService( Context.NOTIFICATION_SERVICE );
            notificationManager1.notify( 100, mBuilder.build() );
        }
    }
}
