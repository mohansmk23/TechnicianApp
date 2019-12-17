package com.poojaelectronics.technician.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.poojaelectronics.technician.Retrofit.Session;
import com.poojaelectronics.technician.view.LoginActivity;

import java.util.prefs.Preferences;

public class SplashScreen extends AppCompatActivity
{
    RelativeLayout card;
    Session session;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );
        Window w = getWindow();
        w.setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS );
        session = new Session(this);
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
            Intent intent = new Intent( this, ServiceList.class );
            intent.putExtra( LoginActivity.EXTRA_CIRCULAR_REVEAL_X, revealX );
            intent.putExtra( LoginActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY );
            intent.putExtra( "form_splash", "1" );
            ActivityCompat.startActivity( this, intent, options.toBundle() );
        }

    }
}
