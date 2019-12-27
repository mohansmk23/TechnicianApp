package com.poojaelectronics.technician.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils
{
    public static boolean IsNetworkConnected( Context context )
    {
        if( context != null )
        {
            ConnectivityManager connMgr = ( ConnectivityManager ) context.getSystemService( Context.CONNECTIVITY_SERVICE );
            assert connMgr != null;
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return ( networkInfo != null && networkInfo.isConnected() );
        }
        else
        {
            return false;
        }
    }
}
