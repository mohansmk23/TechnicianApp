package com.poojaelectronics.technician.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Session
{
    private SharedPreferences prefs;

    public Session( Context context )
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }



    public void clear( ) {
        prefs.edit().clear().apply();
    }

    public void setTechId(String techId) {
        prefs.edit().putString("techId", techId).apply();
    }

    public String getTechId() {
        return prefs.getString("techId","");
    }
}
