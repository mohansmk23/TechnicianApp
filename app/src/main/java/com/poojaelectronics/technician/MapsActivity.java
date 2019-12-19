/*
 * *
 *  * Developed by Saravana  on 8/16/19 1:22 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/16/19 1:22 PM
 *  *
 */

package com.poojaelectronics.technician;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.poojaelectronics.technician.common.BaseActivity;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback
{
    private static final int PERMISSIONS_REQUEST = 1;
    private GoogleMap mMap;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps );
        SupportMapFragment mapFragment = ( SupportMapFragment ) getSupportFragmentManager().findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
        getSupportActionBar().setTitle( "Client Location" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady( GoogleMap googleMap )
    {
        mMap = googleMap;
        mMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );
        LatLng position = new LatLng( 13.055674, 80.225882 );
        mMap.addMarker( new MarkerOptions().position( position ).title( "" ).icon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)) );
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( position, 15f ) );
    }
}
