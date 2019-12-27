package com.poojaelectronics.technician.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.poojaelectronics.technician.R;


public class LoadingDialog
{
    static Dialog loadingDialog;

    public static void showDialog( Context context )
    {
        if( loadingDialog != null )
        {
            loadingDialog.dismiss();
        }
        loadingDialog = new Dialog( context );
        loadingDialog.setContentView( R.layout.dialog_loading );
        loadingDialog.getWindow().setBackgroundDrawableResource( android.R.color.transparent );
        final ImageView gif = loadingDialog.findViewById( R.id.gifimg );
        loadingDialog.setCancelable( false );
        if( !( ( Activity ) context ).isFinishing() )
        {
            loadingDialog.show();
        }
        Glide.with( context ).load( R.raw.loading ).into( gif );
    }

    public static void dismiss()
    {
        loadingDialog.dismiss();
    }

    public static Boolean isdisplayed()
    {
        return loadingDialog.isShowing();
    }
}
