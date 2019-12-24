package com.poojaelectronics.technician.view;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.common.Session;

public class SplashScreen extends AppCompatActivity
{
    RelativeLayout card;
    Session session;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );
        NotificationManager notificationManager = ( NotificationManager ) getSystemService( Context.NOTIFICATION_SERVICE );
        assert notificationManager != null;
        notificationManager.cancelAll();
        Window w = getWindow();
        w.setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS );
        session = new Session( this );
        if( getSupportActionBar() != null ) getSupportActionBar().hide();
        final AppCompatImageView btnOvershoot = findViewById( R.id.ivLogo );
        card = findViewById( R.id.center_card );
        final Animation animOvershoot = AnimationUtils.loadAnimation( this, R.anim.overshoot );
        btnOvershoot.startAnimation( animOvershoot );
        final Handler handler = new Handler();
        handler.postDelayed( new Runnable()
        {
            @Override
            public void run()
            {
                presentActivity( card );
            }
        }, 2000 );
    }

    public void presentActivity( View view )
    {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation( this, view, "transition" );
        int revealX = ( int ) ( view.getX() + view.getWidth() / 2 );
        int revealY = ( int ) ( view.getY() + view.getHeight() / 2 );
        if( session.getTechId().isEmpty() )
        {
            Intent intent = new Intent( this, LoginActivity.class );
            intent.putExtra( LoginActivity.EXTRA_CIRCULAR_REVEAL_X, revealX );
            intent.putExtra( LoginActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY );
            ActivityCompat.startActivity( this, intent, options.toBundle() );
        }
        else
        {
            if( session.getpicked().isEmpty() )
            {
                Intent intent = new Intent( this, ServiceList.class );
                intent.putExtra( LoginActivity.EXTRA_CIRCULAR_REVEAL_X, revealX );
                intent.putExtra( LoginActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY );
                intent.putExtra( "form_splash", "1" );
                ActivityCompat.startActivity( this, intent, options.toBundle() );
            }
            else
            {
                Intent intent = new Intent( this, StartTask.class );
                intent.putExtra( "service_id", session.getpicked() );
                ActivityCompat.startActivity( this, intent, options.toBundle() );
            }
        }
    }
}
