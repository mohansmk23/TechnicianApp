package com.poojaelectronics.technician.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.common.BaseActivity;
import com.poojaelectronics.technician.common.LoadingDialog;
import com.poojaelectronics.technician.common.Session;
import com.poojaelectronics.technician.databinding.ActivityCompleteTaskBinding;
import com.poojaelectronics.technician.model.CompleteResponse;
import com.poojaelectronics.technician.viewModel.CompleteTaskViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

public class CompleteTask extends BaseActivity
{
    CompleteTaskViewModel completeTaskViewModel;
    ActivityCompleteTaskBinding activityCompleteTaskBinding;
    Button completeTask;
    AppCompatTextView clear;
    Session session;
    SignaturePad signaturePad;
    boolean isSigned = false;
    OutputStream os;
    File filesDir;
    File imageFile;
    RatingBar ratingBar;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        completeTaskViewModel = ViewModelProviders.of( this ).get( CompleteTaskViewModel.class );
        activityCompleteTaskBinding = DataBindingUtil.setContentView( this, R.layout.activity_complete_task );
        session = new Session( this );
        activityCompleteTaskBinding.setViewModel( completeTaskViewModel );
        filesDir = getApplicationContext().getFilesDir();
        imageFile = new File( filesDir, "customerSign" + ".jpg" );
        completeTask = findViewById( R.id.btnCompleteTask );
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
        completeTask.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( final View v )
            {
                if( !isSigned || completeTaskViewModel.completeTaskModel.getAmount().isEmpty() )
                {
                    if( completeTaskViewModel.completeTaskModel.getAmount().isEmpty() )
                    {
                        Snackbar.make( v, "Amount is Mandatory", Snackbar.LENGTH_LONG ).show();
                    }
                    else
                    {
                        Snackbar.make( v, "Customer Signature is Mandatory", Snackbar.LENGTH_LONG ).show();
                    }
                }
                else
                {
                    final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder( CompleteTask.this, R.style.AlertDialogTheme );
                    builder.setTitle( "Are you sure to Complete the Task?" );
                    builder.setPositiveButton( "AGREE", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick( DialogInterface dialog, int which )
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
                            completeTaskViewModel.onComplete( completeTaskViewModel.completeTaskModel.getAmount(), completeTaskViewModel.completeTaskModel.getRemarks(), Objects.requireNonNull( Objects.requireNonNull( getIntent().getExtras() ).get( "serviceId" ) ).toString(), ratingBar.getRating(), imageFile );
                            completeTaskViewModel.completeTaskRepository.getCompleteTaskResponse().observe( CompleteTask.this, new Observer<CompleteResponse>()
                            {
                                @Override
                                public void onChanged( CompleteResponse completeResponse )
                                {
                                    Snackbar.make( v, completeResponse.getOutput().get( 0 ).getMessage(), Snackbar.LENGTH_LONG ).show();
                                    if( completeResponse.getOutput().get( 0 ).getStatus().equalsIgnoreCase( "success" ) )
                                    {
                                        session.setpicked( "" );
                                        startActivity( new Intent( CompleteTask.this, ServiceList.class ).setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK ));
                                        finish();
                                    }
                                }
                            } );
                            completeTaskViewModel.completeTaskRepository.getIsLoading().observe( CompleteTask.this, new Observer<Boolean>() {
                                @Override
                                public void onChanged( Boolean aBoolean )
                                {
                                    if( aBoolean )
                                    {
                                        LoadingDialog.showDialog( CompleteTask.this );
                                    }
                                    else
                                    {
                                        LoadingDialog.dismiss();
                                    }
                                }
                            } );
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
            }
        } );
    }
}
