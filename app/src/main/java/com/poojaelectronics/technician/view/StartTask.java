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
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.common.BaseActivity;
import com.poojaelectronics.technician.common.LoadingDialog;
import com.poojaelectronics.technician.common.Session;
import com.poojaelectronics.technician.common.TrackerService;
import com.poojaelectronics.technician.databinding.ActivityStartTaskBinding;
import com.poojaelectronics.technician.model.CompleteResponse;
import com.poojaelectronics.technician.model.StartTaskModel;
import com.poojaelectronics.technician.model.StartTaskResponse;
import com.poojaelectronics.technician.viewModel.StartTaskViewModel;
import com.shuhart.stepview.StepView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class StartTask extends BaseActivity
{
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222;
    TextView startTask;
    ImageView direction;
    FrameLayout serviceDetails, completeDetails;
    RelativeLayout rootLay;
    StartTaskModel startTaskModel;
    StartTaskViewModel startTaskViewModel;
    Session session;
    boolean isAgree = false, visibleState = false;
    int status = 0;
    FloatingActionButton floatingActionButton;
    Animation startAnimation;
    String tittle, serviceId = "";
    StepView stepView;
    AppCompatTextView clear;
    SignaturePad signaturePad;
    boolean isSigned = false;
    OutputStream os;
    File filesDir;
    File imageFile;
    RatingBar ratingBar;
    MaterialAlertDialogBuilder builder;
    private static final int PERMISSIONS_REQUEST = 1;
    StartTaskActivityClickHandler startTaskActivityClickHandler;
    ActivityStartTaskBinding activityStartTaskBinding;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        startTaskViewModel = ViewModelProviders.of( this ).get( StartTaskViewModel.class );
        serviceId = Objects.requireNonNull( Objects.requireNonNull( getIntent().getExtras() ).get( "service_id" ) ).toString();
        startTaskViewModel.init( serviceId );
        startTaskActivityClickHandler = new StartTaskActivityClickHandler( this );
        setupObservers();
        activityStartTaskBinding = DataBindingUtil.setContentView( this, R.layout.activity_start_task );
        session = new Session( this );
        rootLay = findViewById( R.id.rootLay );
        stepView = findViewById( R.id.step_view );
        stepView.getState().animationType( StepView.ANIMATION_ALL ).stepsNumber( 4 ).nextStepCircleEnabled( true ).nextTextColor( Color.GRAY ).nextStepCircleColor( Color.BLACK ).steps( new ArrayList<String>()
        {{
            add( "Picked" );
            add( "Reached" );
            add( "Started" );
            add( "Completed" );
        }} ).animationDuration( getResources().getInteger( android.R.integer.config_shortAnimTime ) ).commit();
        startTask = findViewById( R.id.startTask );
        direction = findViewById( R.id.direction );
        serviceDetails = findViewById( R.id.serviceDetails );
        completeDetails = findViewById( R.id.completeDetails );
        floatingActionButton = findViewById( R.id.fabAdminCall );
        filesDir = getApplicationContext().getFilesDir();
        imageFile = new File( filesDir, "customerSign" + ".jpg" );
        clear = findViewById( R.id.btnClear );
        signaturePad = findViewById( R.id.signPad );
        ratingBar = findViewById( R.id.rating );
        clear.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                signaturePad.clear();
            }
        } );
        signaturePad.setOnSignedListener( new SignaturePad.OnSignedListener()
        {
            @Override
            public void onStartSigning()
            {
            }

            @Override
            public void onSigned()
            {
                clear.setEnabled( true );
                isSigned = true;
            }

            @Override
            public void onClear()
            {
                isSigned = false;
            }
        } );
        startAnimation = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.blink );
        direction.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                if( !startTaskModel.getLat().isEmpty() && !startTaskModel.getLng().isEmpty() )
                {
                    Uri navigation = Uri.parse( "google.navigation:q=" + startTaskModel.getLat() + "," + startTaskModel.getLng() );
                    Intent navigationIntent = new Intent( Intent.ACTION_VIEW, navigation );
                    navigationIntent.setPackage( "com.google.android.apps.maps" );
                    startActivity( navigationIntent );
                    /*if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays( StartTask.this ) )
                    {
                        Intent intent = new Intent( Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse( "package:" + getPackageName() ) );
                        startActivityForResult( intent, DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE );
                    }
                    else
                    {
                        startService(new Intent(StartTask.this, FloatingWidgetService.class));
                    }*/
                }
                else
                {
                    Snackbar.make( rootLay, "Unable to get the Address", Snackbar.LENGTH_LONG ).show();
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
                builder = new MaterialAlertDialogBuilder( StartTask.this, R.style.AlertDialogTheme );
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
                                    LocationManager lm = ( LocationManager ) getSystemService( LOCATION_SERVICE );
                                    if( lm != null && !lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) )
                                    {
                                        GoogleApiClient googleApiClient = new GoogleApiClient.Builder( StartTask.this ).addApi( LocationServices.API ).build();
                                        googleApiClient.connect();
                                        LocationRequest locationRequest = LocationRequest.create();
                                        locationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
                                        locationRequest.setInterval( 10000 );
                                        locationRequest.setFastestInterval( 10000 / 2 );
                                        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest( locationRequest );
                                        builder.setAlwaysShow( true );
                                        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings( googleApiClient, builder.build() );
                                        result.setResultCallback( new ResultCallback<LocationSettingsResult>()
                                        {
                                            @Override
                                            public void onResult( @NonNull LocationSettingsResult result )
                                            {
                                                Log.d( "s2s", "testing Test" );
                                                final Status status = result.getStatus();
                                                switch( status.getStatusCode() )
                                                {
                                                    case LocationSettingsStatusCodes.SUCCESS:
                                                        break;
                                                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                                        try
                                                        {
                                                            status.startResolutionForResult( StartTask.this, REQUEST_CHECK_SETTINGS );
                                                        }
                                                        catch( IntentSender.SendIntentException ignore )
                                                        {
                                                        }
                                                        break;
                                                }
                                            }
                                        } );
                                    }
                                    else
                                    {
                                        int permission = ContextCompat.checkSelfPermission( StartTask.this, Manifest.permission.ACCESS_FINE_LOCATION );
                                        if( permission == PackageManager.PERMISSION_GRANTED )
                                        {
                                            startTrackerService();
                                            startTaskViewModel.picked( serviceId, "picked" );
                                        }
                                        else
                                        {
                                            ActivityCompat.requestPermissions( StartTask.this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSIONS_REQUEST );
                                        }
                                    }
                                    isAgree = false;
                                    break;
                                case 1:
                                    startTaskViewModel.picked( serviceId, "reached" );
                                    isAgree = false;
                                    break;
                                case 2:
                                    startTaskViewModel.picked( serviceId, "start" );
                                    isAgree = false;
                                    break;
                                case 3:
                                    if( !isSigned || startTaskViewModel.completeTaskModel.getAmount() == null || startTaskViewModel.completeTaskModel.getAmount().isEmpty() )
                                    {
                                        if( startTaskViewModel.completeTaskModel.getAmount() == null || startTaskViewModel.completeTaskModel.getAmount().isEmpty() )
                                        {
                                            Snackbar.make( rootLay, "Amount is Mandatory", Snackbar.LENGTH_LONG ).show();
                                        }
                                        else
                                        {
                                            Snackbar.make( rootLay, "Customer Signature is Mandatory", Snackbar.LENGTH_LONG ).show();
                                        }
                                    }
                                    else
                                    {
                                        Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
                                        try
                                        {
                                            os = new FileOutputStream( imageFile );
                                            signatureBitmap.compress( Bitmap.CompressFormat.JPEG, 100, os );
                                            os.flush();
                                            os.close();
                                        }
                                        catch( Exception e )
                                        {
                                            Log.e( getClass().getSimpleName(), "Error writing bitmap", e );
                                        }
                                        startTaskViewModel.onComplete( startTaskViewModel.completeTaskModel.getAmount(), startTaskViewModel.completeTaskModel.getRemarks(), serviceId, ratingBar.getRating(), imageFile );
                                        startTaskViewModel.completeTaskRepository.getCompleteTaskResponse().observe( StartTask.this, new Observer<CompleteResponse>()
                                        {
                                            @Override
                                            public void onChanged( CompleteResponse completeResponse )
                                            {
                                                Snackbar.make( rootLay, completeResponse.getOutput().get( 0 ).getMessage(), Snackbar.LENGTH_LONG ).show();
                                                if( completeResponse.getOutput().get( 0 ).getStatus().equalsIgnoreCase( "success" ) )
                                                {
                                                    session.setpicked( "" );
                                                    startActivity( new Intent( StartTask.this, ServiceList.class ).setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK ) );
                                                    finish();
                                                }
                                            }
                                        } );
                                        startTaskViewModel.completeTaskRepository.getIsLoading().observe( StartTask.this, new Observer<Boolean>()
                                        {
                                            @Override
                                            public void onChanged( Boolean aBoolean )
                                            {
                                                if( aBoolean )
                                                {
                                                    LoadingDialog.showDialog( StartTask.this );
                                                }
                                                else
                                                {
                                                    LoadingDialog.dismiss();
                                                }
                                            }
                                        } );
                                    }
                                    isAgree = false;
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

    /*@Override
    public void onUserLeaveHint()
    {
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
        {
            PictureInPictureParams params = new PictureInPictureParams.Builder().build();
            enterPictureInPictureMode( params );
        }
    }*/

    private void startTrackerService()
    {
        ContextCompat.startForegroundService( this, new Intent( StartTask.this, TrackerService.class ) );
    }

    @Override
    public void onPictureInPictureModeChanged( boolean isInPictureInPictureMode, Configuration newConfig )
    {
        if( isInPictureInPictureMode )
        {
            Objects.requireNonNull( getSupportActionBar() ).hide();
            visibleState = serviceDetails.getVisibility() == View.VISIBLE;
            floatingActionButton.setVisibility( View.GONE );
            serviceDetails.setVisibility( View.GONE );
            completeDetails.setVisibility( View.GONE );
        }
        else
        {
            Objects.requireNonNull( getSupportActionBar() ).show();
            floatingActionButton.setVisibility( View.GONE );
            if( visibleState )
            {
                serviceDetails.setVisibility( View.VISIBLE );
            }
            else
            {
                completeDetails.setVisibility( View.VISIBLE );
            }
        }
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
            startTaskViewModel.picked( serviceId, "picked" );
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
                    if( !startTaskModel.getRemarks().isEmpty() )
                    {
                        startTaskModel.setRemarksVisibility( View.VISIBLE );
                    }
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
                        tittle = getString( R.string.pickup_tittle );
                        status = 0;
                    }
                    else if( startTaskModel.getStatus().equalsIgnoreCase( "Picked" ) )
                    {
                        startTask.setText( R.string.reached );
                        status = 1;
                        tittle = getString( R.string.reached_tittle );
                    }
                    else if( startTaskModel.getStatus().equalsIgnoreCase( "Reached" ) )
                    {
                        startTask.setText( R.string.start_task );
                        tittle = getString( R.string.start_tittle );
                        status = 2;
                        stopTrackerService();
                    }
                    else if( startTaskModel.getStatus().equalsIgnoreCase( "Start" ) )
                    {
                        serviceDetails.setVisibility( View.GONE );
                        completeDetails.setVisibility( View.VISIBLE );
                        startTask.setText( R.string.complete_task );
                        status = 3;
                        tittle = getString( R.string.complete_tittle );
                    }
                    if( status > 0 )
                    {
                        getSupportActionBar().setDisplayHomeAsUpEnabled( false );
                        session.setpicked( serviceId );
                    }
                    stepView.go( status, true );
                    activityStartTaskBinding.setCustomerDetails( startTaskModel );
                    activityStartTaskBinding.setViewModel( startTaskViewModel.completeTaskModel );
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
                if( aBoolean )
                {
                    LoadingDialog.showDialog( StartTask.this );
                }
                else
                {
                    LoadingDialog.dismiss();
                }
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
                        tittle = getString( R.string.pickup_tittle );
                        status = 0;
                    }
                    else if( startTaskModel.getStatus().equalsIgnoreCase( "Picked" ) )
                    {
                        startTask.setText( R.string.reached );
                        status = 1;
                        LocationManager lm = ( LocationManager ) getSystemService( LOCATION_SERVICE );
                        if( lm != null && !lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) )
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
                        tittle = getString( R.string.start_tittle );
                        status = 2;
                        stopTrackerService();
                    }
                    else if( startTaskModel.getStatus().equalsIgnoreCase( "Start" ) )
                    {
                        serviceDetails.setVisibility( View.GONE );
                        completeDetails.setVisibility( View.VISIBLE );
                        startTask.setText( R.string.complete_task );
                        status = 3;
                        tittle = getString( R.string.complete_tittle );
                    }
                    if( status > 0 )
                    {
                        getSupportActionBar().setDisplayHomeAsUpEnabled( false );
                        session.setpicked( serviceId );
                    }
                    stepView.go( status, true );
                    activityStartTaskBinding.setCustomerDetails( startTaskModel );
                    activityStartTaskBinding.setViewModel( startTaskViewModel.completeTaskModel );
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
                if( aBoolean )
                {
                    LoadingDialog.showDialog( StartTask.this );
                }
                else
                {
                    LoadingDialog.dismiss();
                }
            }
        } );
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item )
    {
        startActivity( new Intent( this, ServiceList.class ) );
        finish();
        return true;
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data )
    {
        if( requestCode == REQUEST_CHECK_SETTINGS )
        {
            startTrackerService();
            startTaskViewModel.picked( serviceId, "picked" );
        }
        else if( requestCode == DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE )
        {
            if( resultCode == RESULT_OK )
            {
//                startService(new Intent(StartTask.this, FloatingWidgetService.class));
            }
            else
            {
                Toast.makeText( this, "Draw over other app permission not available. App won\'t work without permission. Please try again.", Toast.LENGTH_SHORT ).show();
            }
        }
        super.onActivityResult( requestCode, resultCode, data );
    }

    @Override
    public void onBackPressed()
    {
        if( status < 1 )
        {
            startActivity( new Intent( this, ServiceList.class ) );
            finish();
        }
    }
}
