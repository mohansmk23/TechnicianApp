/*
 * *
 *  * Developed by Saravana  on 8/13/19 3:36 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/13/19 3:36 PM
 *  *
 */

package com.poojaelectronics.technician.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.poojaelectronics.technician.BaseActivity;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.ServiceListFragment.HistoryFragment;
import com.poojaelectronics.technician.ServiceListFragment.PendingFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceList extends BaseActivity
{
    PendingFragment pendingFragment = new PendingFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    TabLayout tabLayout;
    LinearLayout rootLay;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_service_list );
        rootLay = findViewById( R.id.rootLay );
        if( getSupportActionBar() != null )
        {
            getSupportActionBar().setTitle( "Service List" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( false );
        }
        ViewPager viewPager = findViewById( R.id.view_pager );
        tabLayout = findViewById( R.id.tab_layout );
        tabLayout.setupWithViewPager( viewPager );
        setupViewPager( viewPager );
    }

    private void setupViewPager( ViewPager viewPager )
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter( getSupportFragmentManager() );
        adapter.addFragment( pendingFragment, "PENDING" );
        adapter.addFragment( historyFragment, "HISTORY" );
        viewPager.setAdapter( adapter );
        TabLayout.Tab tab = Objects.requireNonNull( tabLayout.getTabAt( 0 ) ).setCustomView( R.layout.custom_badge );
        TabLayout.Tab tab1 = Objects.requireNonNull( tabLayout.getTabAt( 1 ) ).setCustomView( R.layout.custom_badge );
        TextView txt0 = Objects.requireNonNull( tab.getCustomView() ).findViewById( R.id.tabname );
        ImageView icon = tab.getCustomView().findViewById( R.id.tabicon );
        icon.setImageDrawable( ContextCompat.getDrawable( this, R.drawable.tab_ongoing ) );
        txt0.setText( R.string.ongoing );
        TextView txt1 = Objects.requireNonNull( tab1.getCustomView() ).findViewById( R.id.tabname );
        ImageView icon1 = tab1.getCustomView().findViewById( R.id.tabicon );
        icon1.setImageDrawable( ContextCompat.getDrawable( this, R.drawable.tab_history ) );
        txt1.setText( R.string.history );
    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter( FragmentManager manager )
        {
            super( manager );
        }

        @NonNull
        @Override
        public Fragment getItem( int position ) { return mFragmentList.get( position ); }

        @Override
        public int getCount() { return mFragmentList.size(); }

        void addFragment( Fragment fragment, String title )
        {
            mFragmentList.add( fragment );
            mFragmentTitleList.add( title );
        }

        @Override
        public CharSequence getPageTitle( int position ) { return mFragmentTitleList.get( position ); }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.navigation, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item )
    {
        if( item.getItemId() == R.id.logout )
        {
            finish();
            return ( true );
        }
        return ( super.onOptionsItemSelected( item ) );
    }

    @Override
    public void onBackPressed()
    {
        if( doubleBackToExitPressedOnce )
        {
            super.onBackPressed();
            moveTaskToBack( true );
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Snackbar.make( rootLay, "Please click BACK again to exit", Snackbar.LENGTH_SHORT ).show();
        new Handler().postDelayed( new Runnable()
        {
            @Override
            public void run()
            {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000 );
    }
}
