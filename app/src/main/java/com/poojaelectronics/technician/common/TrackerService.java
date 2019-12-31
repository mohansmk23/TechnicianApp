package com.poojaelectronics.technician.common;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.view.ServiceList;

public class TrackerService extends Service
{
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    Uri defaultSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
    LocationRequest request = new LocationRequest();
    Session session;
    protected BroadcastReceiver stopReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive( Context context, Intent intent )
        {
            unregisterReceiver( stopReceiver );
            stopSelf();
        }
    };

    @Override
    public IBinder onBind( Intent intent ) {return null;}

    @Override
    public void onCreate()
    {
        super.onCreate();
        session = new Session( this );
        buildNotification();
        loginToFirebase();
    }

    private void buildNotification()
    {
        registerReceiver( stopReceiver, new IntentFilter( "stop" ) );
        PendingIntent broadcastIntent = PendingIntent.getBroadcast( this, 0, new Intent( this, ServiceList.class ), PendingIntent.FLAG_UPDATE_CURRENT );
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder( this, CHANNEL_ID );
        NotificationManager notificationManager = ( NotificationManager ) this.getSystemService( NOTIFICATION_SERVICE );
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
        {
            CharSequence name = "Background Service";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel;
            mChannel = new NotificationChannel( CHANNEL_ID, name, importance );
            mChannel.enableLights( true );
            mChannel.setLightColor( Color.RED );
            mChannel.enableVibration( true );
            mChannel.setVibrationPattern( new long[] { 100, 200, 300, 400, 500, 400, 300, 200, 400 } );
            assert notificationManager != null;
            mBuilder.setSmallIcon( R.drawable.pe_logo_small ).setTicker( "Your Job have been Picked Successfully" ).setWhen( 0 ).setAutoCancel( true ).setContentIntent( broadcastIntent ).setContentTitle( "Your Job have been Picked Successfully" ).setStyle( new NotificationCompat.BigTextStyle().bigText( HtmlCompat.fromHtml( "You Location is Currently Sharing..." ,HtmlCompat.FROM_HTML_MODE_LEGACY) ) ).setSound( defaultSound ).setContentText( HtmlCompat.fromHtml( "You Location is Currently Sharing...",HtmlCompat.FROM_HTML_MODE_LEGACY ) ).setChannelId( CHANNEL_ID ).setVibrate( new long[] { 100, 200, 300, 400, 500, 400, 300, 200, 100 } ).setOngoing( true );
            notificationManager.createNotificationChannel( mChannel );
            startForeground( 1, mBuilder.build() );
        }
    }

    private void loginToFirebase()
    {
        String email = "istridesappteam@gmail.com";
        String password = "Istridesappteam@123";
        FirebaseAuth.getInstance().signInWithEmailAndPassword( email, password ).addOnCompleteListener( new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete( @NonNull Task<AuthResult> task )
            {
                if( task.isSuccessful() )
                {
                    requestLocationUpdates();
                }
            }
        } );
    }

    private void requestLocationUpdates()
    {
        request.setInterval( 3000 );
        request.setFastestInterval( 1000 );
        request.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient( this );
        final String path = "locations/" + session.getTechId();
        int permission = ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION );
        if( permission == PackageManager.PERMISSION_GRANTED )
        {
            client.requestLocationUpdates( request, new LocationCallback()
            {
                @Override
                public void onLocationResult( LocationResult locationResult )
                {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference( path );
                    Location location = locationResult.getLastLocation();
                    if( location != null )
                    {
                        ref.setValue( location );
                    }
                }
            }, null );
        }
    }

    @Override
    public void onDestroy()
    {
        /*if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
        {
            stopForeground( true );
        }*/
    }
}