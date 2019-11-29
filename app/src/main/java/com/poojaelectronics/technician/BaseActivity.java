package com.poojaelectronics.technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        Objects.requireNonNull( getSupportActionBar() ).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.app_gradient));
//        getWindow().setFlags(  WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
        Drawable background = this.getResources().getDrawable(R.drawable.app_gradient);
        Window w = getWindow();
        w.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
        w.setStatusBarColor(this.getResources().getColor(android.R.color.transparent));
        w.setBackgroundDrawable(background);
//        w.setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS );
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
