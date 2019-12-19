package com.poojaelectronics.technician.common;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.view.LoginActivity;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity
{
    Session session;
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        session = new Session(this);
        Objects.requireNonNull( getSupportActionBar() ).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setBackgroundDrawable( ContextCompat.getDrawable( this, R.drawable.app_gradient )) ;
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
}
