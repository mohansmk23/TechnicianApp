/*
 * *
 *  * Developed by Saravana  on 8/14/19 12:48 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/14/19 12:48 PM
 *  *
 */

package com.poojaelectronics.technician.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.poojaelectronics.technician.BaseActivity;
import com.poojaelectronics.technician.MapsActivity;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.activity.OngoingTask;
import com.poojaelectronics.technician.databinding.ActivityStartTaskBinding;
import com.poojaelectronics.technician.model.StartTaskModel;
import com.poojaelectronics.technician.model.StartTaskResponse;
import com.poojaelectronics.technician.viewmodel.StartTaskViewModel;

public class StartTask extends BaseActivity
{
    TextView startTask;
    ImageView direction;
    StartTaskViewModel startTaskViewModel;
    ActivityStartTaskBinding activityStartTaskBinding;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
//        setContentView( R.layout.activity_start_task );
        startTaskViewModel = ViewModelProviders.of( this ).get( StartTaskViewModel.class );
        startTaskViewModel.init();
        setupObservers();
        activityStartTaskBinding = DataBindingUtil.setContentView(this, R.layout.activity_start_task );
        startTask = findViewById( R.id.startTask );
        direction = findViewById( R.id.direction );
        direction.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                startActivity( new Intent( getApplicationContext(), MapsActivity.class ) );
            }
        } );
        startTask.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                startActivity( new Intent( getApplicationContext(), OngoingTask.class ) );
            }
        } );
    }

    public void setupObservers()
    {
        startTaskViewModel.getStartTaskResponse().observe( this, new Observer<StartTaskResponse>()
                {
                    @Override
                    public void onChanged( StartTaskResponse startTaskResponse )
                    {
                        if( getSupportActionBar() != null )
                            getSupportActionBar().setTitle( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getServiceType() );
                        StartTaskModel startTaskModel = new StartTaskModel();
                        startTaskModel.setCustomer_name( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getCustomerName() );
                        startTaskModel.setAddress( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getAddress() );
                        startTaskModel.setPhone( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getPhone() );
                        startTaskModel.setEmail( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getEmail() );
                        startTaskModel.setCategory_name( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getCategoryName() );
                        startTaskModel.setService_type( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getServiceType() );
                        startTaskModel.setBrand_name( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getBrandName() );
                        startTaskModel.setTechnician_name( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getTechnicianName() );
                        startTaskModel.setDate( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getDate() );
                        startTaskModel.setTime( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getTime() );
                        startTaskModel.setRemarks( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getRemarks() );
                        startTaskModel.setStatus( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getStatus() );
                        startTaskModel.setCancel_remarks( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getCancelRemarks() );
                        startTaskModel.setCreated_at( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getCreatedAt() );
                        startTaskModel.setRe_schedule( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getReSchedule() );
                        startTaskModel.setRe_date( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getReDate() );
                        startTaskModel.setRe_time( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getReTime() );
                        startTaskModel.setLat( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getLat() );
                        startTaskModel.setLng( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getLng() );
                        activityStartTaskBinding.setCustomerDetails( startTaskModel );
                    }
                });
    }
}
