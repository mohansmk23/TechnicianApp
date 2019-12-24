package com.poojaelectronics.technician.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session
{
    private SharedPreferences prefs;

    public Session( Context context )
    {
        prefs = PreferenceManager.getDefaultSharedPreferences( context );
    }

    public void clear()
    {
        prefs.edit().clear().apply();
    }

    public String getTechId()
    {
        return prefs.getString( "techId", "" );
    }

    public void setTechId( String techId )
    {
        prefs.edit().putString( "techId", techId ).apply();
    }

    public String getpicked()
    {
        return prefs.getString( "service_id", "" );
    }

    public void setpicked( String serviceID )
    {
        prefs.edit().putString( "service_id", serviceID ).apply();
    }

    public String getServiceList()
    {
        return prefs.getString( "service_list", "" );
    }

    public void setServiceList( String serviceList )
    {
        prefs.edit().putString( "service_list", serviceList ).apply();
    }


}
