/*
 * *
 *  * Developed by Saravana  on 8/13/19 3:36 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/13/19 3:36 PM
 *  *
 */

package com.poojaelectronics.technician.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.poojaelectronics.technician.BaseActivity;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.Retrofit.Session;
import com.poojaelectronics.technician.ServiceListFragment.HistoryFragment;
import com.poojaelectronics.technician.ServiceListFragment.PendingFragment;
import com.poojaelectronics.technician.view.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceList extends BaseActivity implements UpdateBadgeCount
{
    List list = new ArrayList();
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    private int revealX;
    private int revealY;
    PendingFragment pendingFragment = new PendingFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    TabLayout tabLayout;
    LinearLayout rootLay;
    Session session;
    TextView txt0, txt1, tabCount0, tabCount1;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_service_list );
        session = new Session( this );
        rootLay = findViewById( R.id.rootLay );
        if( getSupportActionBar() != null )
        {
            getSupportActionBar().setTitle( "Service List" );
            getSupportActionBar().setHomeAsUpIndicator( R.drawable.pe_logo_small );
        }
        if( getIntent().hasExtra( "form_splash" ) && getIntent().getExtras().get( "form_splash" ).toString().equalsIgnoreCase( "1" ) )
        {
            final Intent intent = getIntent();
            if( savedInstanceState == null && intent.hasExtra( EXTRA_CIRCULAR_REVEAL_X ) && intent.hasExtra( EXTRA_CIRCULAR_REVEAL_Y ) )
            {
                rootLay.setVisibility( View.INVISIBLE );
                revealX = intent.getIntExtra( EXTRA_CIRCULAR_REVEAL_X, 0 );
                revealY = intent.getIntExtra( EXTRA_CIRCULAR_REVEAL_Y, 0 );
                ViewTreeObserver viewTreeObserver = rootLay.getViewTreeObserver();
                if( viewTreeObserver.isAlive() )
                {
                    viewTreeObserver.addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener()
                    {
                        @Override
                        public void onGlobalLayout()
                        {
                            revealActivity( revealX, revealY );
                            rootLay.getViewTreeObserver().removeOnGlobalLayoutListener( this );
                        }
                    } );
                }
            }
            else
            {
                rootLay.setVisibility( View.VISIBLE );
            }
        }
        ViewPager viewPager = findViewById( R.id.view_pager );
        tabLayout = findViewById( R.id.tab_layout );
        tabLayout.setupWithViewPager( viewPager );
        setupViewPager( viewPager );
    }

    protected void revealActivity( int x, int y )
    {
        float finalRadius = ( float ) ( Math.max( rootLay.getWidth(), rootLay.getHeight() ) * 1.1 );
        Animator circularReveal = ViewAnimationUtils.createCircularReveal( rootLay, x, y, 0, finalRadius );
        circularReveal.setDuration( 800 );
        circularReveal.setInterpolator( new AccelerateInterpolator() );
        rootLay.setVisibility( View.VISIBLE );
        circularReveal.start();
    }

    private void setupViewPager( ViewPager viewPager )
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter( getSupportFragmentManager() );
        adapter.addFragment( pendingFragment, "PENDING" );
        adapter.addFragment( historyFragment, "HISTORY" );
        viewPager.setAdapter( adapter );
        TabLayout.Tab tab = Objects.requireNonNull( tabLayout.getTabAt( 0 ) ).setCustomView( R.layout.custom_badge );
        TabLayout.Tab tab1 = Objects.requireNonNull( tabLayout.getTabAt( 1 ) ).setCustomView( R.layout.custom_badge );
        tabCount0 = tab.getCustomView().findViewById(R.id.tabcount);
        tabCount1 = tab1.getCustomView().findViewById(R.id.tabcount);
        txt0 = Objects.requireNonNull( tab.getCustomView() ).findViewById( R.id.tabname );
        ImageView icon = tab.getCustomView().findViewById( R.id.tabicon );
        icon.setImageDrawable( ContextCompat.getDrawable( this, R.drawable.tab_ongoing ) );
        txt0.setText( R.string.ongoing );
        txt1 = Objects.requireNonNull( tab1.getCustomView() ).findViewById( R.id.tabname );
        ImageView icon1 = tab1.getCustomView().findViewById( R.id.tabicon );
        icon1.setImageDrawable( ContextCompat.getDrawable( this, R.drawable.tab_history ) );
        txt1.setText( R.string.history );
    }

    @Override
    public void updatePendingBadgeCount( String count )
    {
        tabCount0.setText( count );
    }

    @Override
    public void updateHistoryBadgeCount( String count )
    {
        tabCount1.setText( count );
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
            session.clear();
            Intent intent = new Intent( ServiceList.this, LoginActivity.class );
            startActivity( intent );
            finishAffinity();
        }
        return true;
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
