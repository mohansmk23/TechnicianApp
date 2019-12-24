package com.poojaelectronics.technician.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.poojaelectronics.technician.view.LoginActivity;
import com.poojaelectronics.technician.view.StartTask;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    NotificationUtils notificationUtils;
    String title, message, ServiceId, imageUrl, type;
    public static final String INTENT_FILTER = "INTENT_FILTER";
    public static final String LOGOUT_FILTER = "LOGOUT_FILTER";
    Session session;
    Intent resultIntent;

    @Override
    public void onMessageReceived( @NonNull RemoteMessage remoteMessage )
    {
        super.onMessageReceived( remoteMessage );
        session = new Session( getBaseContext() );
        if( remoteMessage.getData().size() > 0 )
        {
            try
            {
                JSONObject json = new JSONObject( remoteMessage.getData().toString() );
                handleMessage( json );
            }
            catch( Exception ignored )
            {
            }
        }
    }

    private void handleMessage( JSONObject json )
    {
        JSONObject data;
        try
        {
            data = json.getJSONObject( "message" );
            type = data.getString( "type" );
            message = data.getString( "msg" );
            title = data.getString( "title" );
            ServiceId = data.getString( "id" );
            imageUrl = data.getString( "image" );
        }
        catch( JSONException e )
        {
            e.printStackTrace();
        }
        if( type.equalsIgnoreCase( "logout" ) )
        {
            resultIntent = new Intent( getApplicationContext(), LoginActivity.class );
            session.clear();
            Intent intent = new Intent( LOGOUT_FILTER );
            sendBroadcast( intent );
        }
        else
        {
            resultIntent = new Intent( getApplicationContext(), StartTask.class );
            resultIntent.putExtra( "service_id", ServiceId );
            Intent intent = new Intent( INTENT_FILTER );
            intent.putExtra( "service_id", ServiceId );
            sendBroadcast( intent );
        }
        Glide.with( getApplicationContext() ).asBitmap().load( imageUrl ).into( new CustomTarget<Bitmap>()
        {
            @Override
            public void onResourceReady( @NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition )
            {
                if( session.getServiceList().isEmpty() )
                {
                    showNotificationMessageWithBigImage( getApplicationContext(), title, message, resultIntent, resource );
                }
            }

            @Override
            public void onLoadCleared( @Nullable Drawable placeholder )
            {
            }
        } );
    }

    private void showNotificationMessageWithBigImage( Context context, String title, String message, Intent intent, Bitmap imageUrl )
    {
        notificationUtils = new NotificationUtils( context );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        notificationUtils.showSmallNotification( title, message, intent, imageUrl );
    }
}
