/*
 * *
 *  * Developed by Saravana  on 8/13/19 1:25 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/13/19 1:16 PM
 *  *
 */

package com.poojaelectronics.technician.login;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.poojaelectronics.technician.Activity.ServiceList;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.databinding.ActivityLoginBinding;
import com.poojaelectronics.technician.viewmodel.LoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity
{
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    View rootLayout;
    boolean doubleBackToExitPressedOnce = false;
    private int revealX;
    private int revealY;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView( this, R.layout.activity_login );
        loginViewModel = ViewModelProviders.of( this ).get( LoginViewModel.class );
        activityLoginBinding.setLogin( loginViewModel );
        activityLoginBinding.setClickHandler( new LoginActivityClickHandler(this) );
        Objects.requireNonNull( getSupportActionBar() ).hide();
        Window w = getWindow();
        w.setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS );
        final Intent intent = getIntent();
        rootLayout = findViewById( R.id.rootLay );
        if( savedInstanceState == null && intent.hasExtra( EXTRA_CIRCULAR_REVEAL_X ) && intent.hasExtra( EXTRA_CIRCULAR_REVEAL_Y ) )
        {
            rootLayout.setVisibility( View.INVISIBLE );
            revealX = intent.getIntExtra( EXTRA_CIRCULAR_REVEAL_X, 0 );
            revealY = intent.getIntExtra( EXTRA_CIRCULAR_REVEAL_Y, 0 );
            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if( viewTreeObserver.isAlive() )
            {
                viewTreeObserver.addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener()
                {
                    @Override
                    public void onGlobalLayout()
                    {
                        revealActivity( revealX, revealY );
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener( this );
                    }
                } );
            }
        }
        else
        {
            rootLayout.setVisibility( View.VISIBLE );
        }
    }

    protected void revealActivity( int x, int y )
    {
        float finalRadius = ( float ) ( Math.max( rootLayout.getWidth(), rootLayout.getHeight() ) * 1.1 );
        Animator circularReveal = ViewAnimationUtils.createCircularReveal( rootLayout, x, y, 0, finalRadius );
        circularReveal.setDuration( 800 );
        circularReveal.setInterpolator( new AccelerateInterpolator() );
        rootLayout.setVisibility( View.VISIBLE );
        circularReveal.start();
    }

    public class LoginActivityClickHandler
    {
        Context context;

        private LoginActivityClickHandler( Context context )
        {
            this.context = context;
        }

        public void onSignInClicked( View view )
        {
            if( loginViewModel.isValid() )
            {
                Intent serviceListIntent = new Intent( context, ServiceList.class );
                context.startActivity( serviceListIntent );
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        if( doubleBackToExitPressedOnce )
        {
            super.onBackPressed();
            moveTaskToBack( true );
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText( this, "Please click BACK again to exit", Toast.LENGTH_SHORT ).show();
        new Handler().postDelayed( new Runnable()
        {
            @Override
            public void run()
            {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000 );
    }

}


