package com.poojaelectronics.technician.common;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.view.LoginActivity;

import java.util.Objects;

import static android.view.View.LAYER_TYPE_HARDWARE;

public class BaseActivity extends AppCompatActivity
{
    Session session;
    BroadcastReceiver networkReceiver;
    static View rootLayout;
    static MaterialTextView materialTextView;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        session = new Session( this );
        networkReceiver = new NetworkReceiver();
        registerReceiver( networkReceiver, new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION ) );
        Objects.requireNonNull( getSupportActionBar() ).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setBackgroundDrawable( ContextCompat.getDrawable( this, R.drawable.app_gradient ) );
        Drawable background = ContextCompat.getDrawable( this, R.drawable.app_gradient );
        Window w = getWindow();
        w.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
        w.setStatusBarColor( ContextCompat.getColor( this, android.R.color.transparent ) );
        w.setBackgroundDrawable( background );
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item )
    {
        if( item.getItemId() == android.R.id.home )
        {
            finish();
        }
        else if( item.getItemId() == R.id.logout )
        {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder( this, R.style.AlertDialogTheme );
            builder.setTitle( "Logout Alert" );
            builder.setMessage( "Are you sure you wanted to logout?" );
            builder.setPositiveButton( "LOGOUT", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which )
                {
                    session.clear();
                    Intent intent = new Intent( BaseActivity.this, LoginActivity.class );
                    startActivity( intent );
                    finishAffinity();
                }
            } );
            builder.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which )
                {
                }
            } );
            builder.show();
        }
        else
        {
            return super.onOptionsItemSelected( item );
        }
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        NotificationManager notificationManager = ( NotificationManager ) getSystemService( Context.NOTIFICATION_SERVICE );
        assert notificationManager != null;
        notificationManager.cancelAll();
        materialTextView = findViewById( R.id.networkStatus );
        rootLayout = findViewById( R.id.rootLay );
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver( networkReceiver );
    }


    public static void networkChange( boolean state )
    {
        ColorMatrix cm = new ColorMatrix();
        Paint greyScalePaint = new Paint();
        if( state )
        {
            materialTextView.setText( R.string.back_online );
            materialTextView.setBackgroundColor( Color.parseColor( "#43a047" ) );
            Handler handler = new Handler();
            handler.postDelayed( new Runnable()
            {
                @Override
                public void run()
                {
                    materialTextView.setVisibility( View.GONE );
                }
            }, 1500 );
        }
        else
        {
            cm.setSaturation( 0 );
            materialTextView.setText( R.string.no_connection );
            materialTextView.setBackgroundColor( Color.parseColor( "#000000" ) );
            materialTextView.setVisibility( View.VISIBLE );
        }
        greyScalePaint.setColorFilter( new ColorMatrixColorFilter( cm ) );
        rootLayout.setLayerType( LAYER_TYPE_HARDWARE, greyScalePaint );
    }
}
