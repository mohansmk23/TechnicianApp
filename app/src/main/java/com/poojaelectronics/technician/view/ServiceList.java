/*
 * *
 *  * Developed by Saravana  on 8/13/19 3:36 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/13/19 3:36 PM
 *  *
 */

package com.poojaelectronics.technician.view;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.common.BaseActivity;
import com.poojaelectronics.technician.common.MyFirebaseMessagingService;
import com.poojaelectronics.technician.common.Session;
import com.poojaelectronics.technician.common.UpdateBadgeCount;
import com.poojaelectronics.technician.view.ServiceListFragment.HistoryFragment;
import com.poojaelectronics.technician.view.ServiceListFragment.PendingFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceList extends BaseActivity implements UpdateBadgeCount
{
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
        registerReceiver( pendingFragment.myReceiver, new IntentFilter( MyFirebaseMessagingService.INTENT_FILTER ) );
        session = new Session( this );
        if( !session.getpicked().isEmpty() )
        {
            Intent intent = new Intent( this, StartTask.class );
            intent.putExtra( "service_id", session.getpicked() );
            startActivity(  intent );
            finish();
        }
        rootLay = findViewById( R.id.rootLay );
        if( getSupportActionBar() != null )
        {
            getSupportActionBar().setTitle( "Service List" );
            getSupportActionBar().setHomeAsUpIndicator( R.drawable.pe_logo_small );
        }
        if( getIntent().hasExtra( "form_splash" ) && getIntent().getExtras().get( "form_splash" ).toString().equalsIgnoreCase( "1" ) )
        {
            final Intent intent = getIntent();
            if( savedInstanceState == null && intent.hasExtra( LoginActivity.EXTRA_CIRCULAR_REVEAL_X ) && intent.hasExtra( LoginActivity.EXTRA_CIRCULAR_REVEAL_Y ) )
            {
                rootLay.setVisibility( View.INVISIBLE );
                revealX = intent.getIntExtra( LoginActivity.EXTRA_CIRCULAR_REVEAL_X, 0 );
                revealY = intent.getIntExtra( LoginActivity.EXTRA_CIRCULAR_REVEAL_Y, 0 );
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
        tabCount0 = tab.getCustomView().findViewById( R.id.tabCount );
        tabCount1 = tab1.getCustomView().findViewById( R.id.tabCount );
        txt0 = Objects.requireNonNull( tab.getCustomView() ).findViewById( R.id.tabName );
        ImageView icon = tab.getCustomView().findViewById( R.id.tabIcon );
        icon.setImageDrawable( ContextCompat.getDrawable( this, R.drawable.tab_ongoing ) );
        txt0.setText( R.string.ongoing );
        txt1 = Objects.requireNonNull( tab1.getCustomView() ).findViewById( R.id.tabName );
        ImageView icon1 = tab1.getCustomView().findViewById( R.id.tabIcon );
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
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder( this, R.style.AlertDialogTheme );
            builder.setTitle( "Logout Alert" );
            builder.setMessage( "Are you sure you wanted to logout?" );
            builder.setPositiveButton( "LOGOUT", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which )
                {
                    session.clear();
                    Intent intent = new Intent( ServiceList.this, LoginActivity.class );
                    startActivity( intent );
                    finishAffinity();
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
        return true;
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        session.setServiceList( "1" );
    }


    @Override
    protected void onStop()
    {
        super.onStop();
        session.setServiceList( "" );
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver( pendingFragment.myReceiver );
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
