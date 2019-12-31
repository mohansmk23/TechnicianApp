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

    }
}
