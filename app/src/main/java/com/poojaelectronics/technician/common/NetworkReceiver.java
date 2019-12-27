package com.poojaelectronics.technician.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.poojaelectronics.technician.view.LoginActivity;

public class NetworkReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive( Context context, Intent intent )
    {
        LoginActivity.networkChange( NetworkUtils.IsNetworkConnected( context ) );
    }
}
