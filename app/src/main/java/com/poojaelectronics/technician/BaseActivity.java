package com.poojaelectronics.technician;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        Objects.requireNonNull( getSupportActionBar() ).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setBackgroundDrawable( ContextCompat.getDrawable( this,R.drawable.app_gradient )) ;
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
        else
        {
            return super.onOptionsItemSelected( item );
        }
        return true;
    }
}
