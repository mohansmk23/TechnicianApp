/*
 * *
 *  * Developed by Saravana  on 8/14/19 12:48 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/14/19 12:48 PM
 *  *
 */

package com.poojaelectronics.technician.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.poojaelectronics.technician.BaseActivity;
import com.poojaelectronics.technician.MapsActivity;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.TrackerService;

import java.util.Objects;

public class StartTask extends BaseActivity
{

    TextView starttask;
    ImageView direction;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_start_task );

        getSupportActionBar().setTitle("Television Service");
        starttask = findViewById(R.id.starttask);



        direction = findViewById(R.id.direction);

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });


        starttask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),OngoingTask.class));


            }
        });



    }




}
