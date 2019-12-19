/*
 * *
 *  * Developer Name: Saravana  on
 *    Creaated : 8/14/19 12:48 PM
        Functionality: Read operation
* Description: This module is to perform view operation for cutomer details and using map funality for map function.
*
 *  *
 */

package com.poojaelectronics.technician.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.TrackerService;
import com.poojaelectronics.technician.common.BaseActivity;
import com.poojaelectronics.technician.common.Session;
import com.poojaelectronics.technician.databinding.ActivityStartTaskBinding;
import com.poojaelectronics.technician.model.StartTaskModel;
import com.poojaelectronics.technician.model.StartTaskResponse;
import com.poojaelectronics.technician.viewModel.StartTaskViewModel;

public class StartTask extends BaseActivity
{
    TextView startTask, tvStatus;
    ImageView direction;
    CardView cvPicked;
    RelativeLayout rootLay;
    StartTaskModel startTaskModel;
    StartTaskViewModel startTaskViewModel;
    Session session;
    boolean isAgree = false;
    int status = 0;
    FloatingActionButton floatingActionButton;
    Animation startAnimation;
    String tittle, serviceId;
    private static final int PERMISSIONS_REQUEST = 1;
    StartTaskActivityClickHandler startTaskActivityClickHandler;
    ActivityStartTaskBinding activityStartTaskBinding;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        startTaskViewModel = ViewModelProviders.of( this ).get( StartTaskViewModel.class );
        serviceId = getIntent().getExtras().get( "service_id" ).toString();
        startTaskViewModel.init( serviceId );
        startTaskActivityClickHandler = new StartTaskActivityClickHandler( this );
        setupObservers();
        activityStartTaskBinding = DataBindingUtil.setContentView( this, R.layout.activity_start_task );
        session = new Session( this );
        rootLay = findViewById( R.id.rootLay );
        cvPicked = findViewById( R.id.picked_card );
        tvStatus = findViewById( R.id.status );
        startTask = findViewById( R.id.startTask );
        direction = findViewById( R.id.direction );
        floatingActionButton = findViewById( R.id.fabAdminCall );
        startAnimation = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.blink );
        direction.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                if( !startTaskModel.getLat().isEmpty() && !startTaskModel.getLng().isEmpty() )
                {
                    Uri navigation = Uri.parse( "google.navigation:q=" + "13.012101" + "," + "80.229734" );
                    Intent navigationIntent = new Intent( Intent.ACTION_VIEW, navigation );
                    navigationIntent.setPackage( "com.google.android.apps.maps" );
                    startActivity( navigationIntent );
                }
            }
        } );
        floatingActionButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                if( startTaskModel.getAdmin_mobile_number().isEmpty() )
                {
                    floatingActionButton.setVisibility( View.GONE );
                }
                else
                {
                    Intent intent = new Intent( Intent.ACTION_DIAL, Uri.fromParts( "tel", startTaskModel.getAdmin_mobile_number(), null ) );
                    startActivity( intent );
                }
            }
        } );
        startTask.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                String[] multiChoiceItems = getResources().getStringArray( R.array.dialog_multi_choice_array );
                final boolean[] checkedItems = { false };
                final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder( StartTask.this, R.style.AlertDialogTheme );
                builder.setTitle( tittle );
                builder.setMultiChoiceItems( multiChoiceItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick( DialogInterface dialog, int which, boolean isChecked )
                    {
                        isAgree = isChecked;
                    }
                } );
                builder.setPositiveButton( "AGREE", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick( DialogInterface dialog, int which )
                    {
                        if( isAgree )
                        {
                            switch( status )
                            {
                                case 0:
                                    startTaskViewModel.picked( serviceId, "picked" );
                                    break;
                                case 1:
                                    startTaskViewModel.picked( serviceId, "reached" );
                                    break;
                                case 2:
                                    startTaskViewModel.picked( serviceId, "start" );
                                    break;
                                case 3:
                                    startActivity( new Intent( StartTask.this, CompleteTask.class ).putExtra( "serviceId", serviceId ) );
                                    break;
                            }
                        }
                        else
                        {
                            Snackbar.make( rootLay, "Please agree to continue", Snackbar.LENGTH_LONG ).show();
                            builder.show();
                        }
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
        } );
    }

    private void startTrackerService()
    {
        startService( new Intent( StartTask.this, TrackerService.class ) );
    }

    private void stopTrackerService()
    {
        this.stopService( new Intent( StartTask.this, TrackerService.class ) );
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults )
    {
        if( requestCode == PERMISSIONS_REQUEST && grantResults.length == 1 && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED )
        {
            startTrackerService();
        }
    }

    public class StartTaskActivityClickHandler
    {
        Context context;

        private StartTaskActivityClickHandler( Context context )
        {
            this.context = context;
        }

        public void onCustomerCallClicked( View view )
        {
            if( startTaskModel.getPhone().isEmpty() )
            {
                Snackbar.make( view, "Customer Mobile Number Not Available", Snackbar.LENGTH_LONG ).show();
            }
            else
            {
                Intent intent = new Intent( Intent.ACTION_DIAL, Uri.fromParts( "tel", startTaskModel.getPhone(), null ) );
                startActivity( intent );
            }
        }
    }

    //Note: Observer the response form server to view customer details
    public void setupObservers()
    {
        startTaskViewModel.startTaskRepository.customerDetailsResponse.observe( this, new Observer<StartTaskResponse>()
        {
            @Override
            public void onChanged( StartTaskResponse startTaskResponse )
            {
                if( startTaskResponse != null )
                {
                    if( getSupportActionBar() != null )
                    {
                        getSupportActionBar().setTitle( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getServiceType() );
                    }
                    startTaskModel = new StartTaskModel();
                    startTaskModel.setCustomer_name( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getCustomerName() );
                    startTaskModel.setAddress( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getAddress() );
                    startTaskModel.setPhone( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getPhone() );
                    startTaskModel.setEmail( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getEmail() );
                    startTaskModel.setAdmin_mobile_number( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getAdminMobileNumber() );
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
                    if( !startTaskModel.getAdmin_mobile_number().isEmpty() )
                    {
                        floatingActionButton.setVisibility( View.VISIBLE );
                    }
                    if( startTaskModel.getStatus().equalsIgnoreCase( "Assigned" ) )
                    {
                        startTask.setText( R.string.pickup_task );
                        cvPicked.setVisibility( View.GONE );
                        tittle = getString( R.string.pickup_tittle );
                        status = 0;
                    }
                    else if( startTaskModel.getStatus().equalsIgnoreCase( "Picked" ) )
                    {
                        startTask.setText( R.string.reached );
                        cvPicked.setVisibility( View.VISIBLE );
                        tvStatus.setText( R.string.job_picked_up );
                        getSupportActionBar().hide();
                        cvPicked.startAnimation( startAnimation );
                        status = 1;
                        LocationManager lm = ( LocationManager ) getSystemService( LOCATION_SERVICE );
                        if( !lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) )
                        {
                            Snackbar.make( rootLay, "Please enable location services", Snackbar.LENGTH_SHORT ).show();
                        }
                        int permission = ContextCompat.checkSelfPermission( StartTask.this, Manifest.permission.ACCESS_FINE_LOCATION );
                        if( permission == PackageManager.PERMISSION_GRANTED )
                        {
                            startTrackerService();
                        }
                        else
                        {
                            ActivityCompat.requestPermissions( StartTask.this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSIONS_REQUEST );
                        }
                        tittle = getString( R.string.reached_tittle );
                    }
                    else if( startTaskModel.getStatus().equalsIgnoreCase( "Reached" ) )
                    {
                        startTask.setText( R.string.start_task );
                        cvPicked.setVisibility( View.VISIBLE );
                        tvStatus.setText( R.string.location_reached );
                        tittle = getString( R.string.start_tittle );
                        status = 2;
                        cvPicked.startAnimation( startAnimation );
                        stopTrackerService();
                    }
                    else if( startTaskModel.getStatus().equalsIgnoreCase( "Start" ) )
                    {
                        startTask.setText( R.string.complete_task );
                        cvPicked.setVisibility( View.VISIBLE );
                        tvStatus.setText( R.string.task_started );
                        cvPicked.startAnimation( startAnimation );
                        status = 3;
                        tittle = getString( R.string.complete_tittle );
                    }
                    if( status > 0 )
                    {
                        getSupportActionBar().setDisplayHomeAsUpEnabled( false );
                        session.setpicked( serviceId );
                    }
                    activityStartTaskBinding.setCustomerDetails( startTaskModel );
                    activityStartTaskBinding.setOnClickHandler( startTaskActivityClickHandler );
                }
                else
                {
                    startTaskViewModel.startTaskRepository.errorResponse.observe( StartTask.this, new Observer<String>()
                    {
                        @Override
                        public void onChanged( String s )
                        {
                            Snackbar.make( rootLay, s, Snackbar.LENGTH_LONG ).show();
                        }
                    } );
                }
            }
        } );
        startTaskViewModel.startTaskRepository.isLoading.observe( this, new Observer<Boolean>()
        {
            @Override
            public void onChanged( Boolean aBoolean )
            {
            }
        } );
        startTaskViewModel.technicianServiceStatusRepository.technicianServiceResponse.observe( this, new Observer<StartTaskResponse>()
        {
            @Override
            public void onChanged( StartTaskResponse startTaskResponse )
            {
                if( startTaskResponse != null )
                {
                    if( getSupportActionBar() != null )
                    {
                        getSupportActionBar().setTitle( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getServiceType() );
                    }
                    startTaskModel = new StartTaskModel();
                    startTaskModel.setCustomer_name( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getCustomerName() );
                    startTaskModel.setAddress( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getAddress() );
                    startTaskModel.setPhone( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getPhone() );
                    startTaskModel.setEmail( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getEmail() );
                    startTaskModel.setAdmin_mobile_number( startTaskResponse.getOutput().get( 0 ).getProfile().get( 0 ).getAdminMobileNumber() );
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
                    if( !startTaskModel.getAdmin_mobile_number().isEmpty() )
                    {
                        floatingActionButton.setVisibility( View.VISIBLE );
                    }
                    if( startTaskModel.getStatus().equalsIgnoreCase( "Assigned" ) )
                    {
                        startTask.setText( R.string.pickup_task );
                        cvPicked.setVisibility( View.GONE );
                        tittle = getString( R.string.pickup_tittle );
                        status = 0;
                    }
                    else if( startTaskModel.getStatus().equalsIgnoreCase( "Picked" ) )
                    {
                        startTask.setText( R.string.reached );
                        cvPicked.setVisibility( View.VISIBLE );
                        tvStatus.setText( R.string.job_picked_up );
                        cvPicked.startAnimation( startAnimation );
                        status = 1;
                        LocationManager lm = ( LocationManager ) getSystemService( LOCATION_SERVICE );
                        if( !lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) )
                        {
                            Snackbar.make( rootLay, "Please enable location services", Snackbar.LENGTH_SHORT ).show();
                        }
                        int permission = ContextCompat.checkSelfPermission( StartTask.this, Manifest.permission.ACCESS_FINE_LOCATION );
                        if( permission == PackageManager.PERMISSION_GRANTED )
                        {
                            startTrackerService();
                        }
                        else
                        {
                            ActivityCompat.requestPermissions( StartTask.this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSIONS_REQUEST );
                        }
                        tittle = getString( R.string.reached_tittle );
                    }
                    else if( startTaskModel.getStatus().equalsIgnoreCase( "Reached" ) )
                    {
                        startTask.setText( R.string.start_task );
                        cvPicked.setVisibility( View.VISIBLE );
                        tvStatus.setText( R.string.location_reached );
                        tittle = getString( R.string.start_tittle );
                        status = 2;
                        stopTrackerService();
                        cvPicked.startAnimation( startAnimation );
                    }
                    else if( startTaskModel.getStatus().equalsIgnoreCase( "Start" ) )
                    {
                        startTask.setText( R.string.complete_task );
                        cvPicked.setVisibility( View.VISIBLE );
                        tvStatus.setText( R.string.task_started );
                        cvPicked.startAnimation( startAnimation );
                        status = 3;
                        tittle = getString( R.string.complete_tittle );
                    }
                    if( status > 0 )
                    {
                        getSupportActionBar().setDisplayHomeAsUpEnabled( false );
                        session.setpicked( serviceId );
                    }
                    activityStartTaskBinding.setCustomerDetails( startTaskModel );
                    activityStartTaskBinding.setOnClickHandler( startTaskActivityClickHandler );
                }
                else
                {
                    startTaskViewModel.technicianServiceStatusRepository.errorResponse.observe( StartTask.this, new Observer<String>()
                    {
                        @Override
                        public void onChanged( String s )
                        {
                            Snackbar.make( rootLay, s, Snackbar.LENGTH_LONG ).show();
                        }
                    } );
                }
            }
        } );
        startTaskViewModel.technicianServiceStatusRepository.isLoading.observe( this, new Observer<Boolean>()
        {
            @Override
            public void onChanged( Boolean aBoolean )
            {
            }
        } );
    }

    @Override
    public void onBackPressed()
    {
        if( status < 1 ) super.onBackPressed();
    }
}
