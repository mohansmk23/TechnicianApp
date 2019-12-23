package com.poojaelectronics.technician.common;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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
import com.poojaelectronics.technician.common.Session;

public class TrackerService extends Service
{
    LocationRequest request = new LocationRequest();
    Session session;

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
        String stop = "stop";
        registerReceiver( stopReceiver, new IntentFilter( stop ) );
        PendingIntent.getBroadcast( this, 0, new Intent( stop ), PendingIntent.FLAG_UPDATE_CURRENT );
    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive( Context context, Intent intent )
        {
            unregisterReceiver( stopReceiver );
            stopSelf();
        }
    };

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
    }
}