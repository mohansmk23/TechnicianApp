/*
 * *
 *  * Developed by Saravana  on 8/13/19 1:25 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/13/19 1:16 PM
 *  *
 */

package com.poojaelectronics.technician.view;

import android.animation.Animator;
import android.app.ProgressDialog;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.poojaelectronics.technician.activity.ServiceList;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.databinding.ActivityLoginBinding;
import com.poojaelectronics.technician.model.LoginResponse;
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
    ProgressDialog pDialog;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        pDialog = new ProgressDialog( LoginActivity.this );
        pDialog.setTitle( "Logging in" );
        pDialog.setMessage( "Please wait..." );
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView( this, R.layout.activity_login );
        loginViewModel = ViewModelProviders.of( this ).get( LoginViewModel.class );
        if( savedInstanceState == null )
        {
            loginViewModel.init();
        }
        activityLoginBinding.setLogin( loginViewModel );
        activityLoginBinding.setClickHandler( new LoginActivityClickHandler(this) );
        Objects.requireNonNull( getSupportActionBar() ).hide();
        Window w = getWindow();
        w.setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS );
        final Intent intent = getIntent();
        rootLayout = findViewById( R.id.rootLay );
        setupObservers();
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

    public class LoginActivityClickHandler extends LoginActivity
    {
        Context context;

        private LoginActivityClickHandler( Context context )
        {
            this.context = context;
        }

        public void onSignInClicked( View view )
        {
            loginViewModel.passwordEditorAction();
        }
    }

    public void setupObservers()
    {
        LiveData<LoginResponse> loginResponseLiveData = loginViewModel.getLoginResponse();
        loginResponseLiveData.observe( this, new Observer<LoginResponse>()
        {
            @Override
            public void onChanged( LoginResponse loginResponse )
            {
                if( loginResponse != null )
                {
                    if( loginResponse.getOutput().get( 0 ).getStatus().equalsIgnoreCase( "success" ) )
                    {
                        Intent serviceListIntent = new Intent( LoginActivity.this, ServiceList.class );
                        startActivity( serviceListIntent );
                        finish();
                    }
                    else
                    {
                        Snackbar.make( rootLayout, loginResponse.getOutput().get( 0 ).getMessage(), Snackbar.LENGTH_LONG ).show();
                    }
                }
                //ToDo handle else case
            }
        } );
        loginViewModel.getIsLoading().observe( this, new Observer<Boolean>()
        {
            @Override
            public void onChanged( Boolean aBoolean )
            {
                if( aBoolean )
                {
                    pDialog.show();
                }
                else
                {
                    pDialog.dismiss();
                }
            }
        } );
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
        Snackbar.make( rootLayout, "Please click BACK again to exit", Snackbar.LENGTH_LONG ).show();
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


