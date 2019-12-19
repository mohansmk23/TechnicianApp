package com.poojaelectronics.technician.common;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.poojaelectronics.technician.MapsActivity;
import com.poojaelectronics.technician.R;

public class OngoingTask extends BaseActivity
{
    CardView picked;
    ImageView direction;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ongoing_task );
        if( getSupportActionBar() != null ) getSupportActionBar().setTitle( "Television Service" );
        picked = findViewById( R.id.picked_card );
        direction = findViewById( R.id.direction );
        direction.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                startActivity( new Intent( getApplicationContext(), MapsActivity.class ) );
            }
        } );
        Animation startAnimation = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.blink );
        picked.startAnimation( startAnimation );
    }
}
