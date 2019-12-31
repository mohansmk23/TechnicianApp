package com.poojaelectronics.technician.common;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.view.StartTask;

public class FloatingWidgetService extends Service implements View.OnClickListener
{
    private WindowManager mWindowManager;
    private View mFloatingWidgetView, collapsedView;
    private ImageView remove_image_view;
    private Point szWindow = new Point();
    private View removeFloatingWidgetView;
    private int x_init_cord, y_init_cord, x_init_margin, y_init_margin;
    int LAYOUT_FLAG;
    private boolean isLeft = true;

    public FloatingWidgetService()
    {
    }

    @Override
    public IBinder onBind( Intent intent )
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mWindowManager = ( WindowManager ) getSystemService( WINDOW_SERVICE );
        getWindowManagerDefaultDisplay();
        LayoutInflater inflater = ( LayoutInflater ) getSystemService( LAYOUT_INFLATER_SERVICE );
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
        {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        else
        {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        addRemoveView( inflater );
        addFloatingWidgetView( inflater );
        implementClickListeners();
        implementTouchListenerToFloatingWidgetView();
    }

    private View addRemoveView( LayoutInflater inflater )
    {
        removeFloatingWidgetView = inflater.inflate( R.layout.remove_floating_widget_layout, null );
        WindowManager.LayoutParams paramRemove = new WindowManager.LayoutParams( WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, LAYOUT_FLAG, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, PixelFormat.TRANSLUCENT );
        paramRemove.gravity = Gravity.TOP | Gravity.START;
        removeFloatingWidgetView.setVisibility( View.GONE );
        remove_image_view = removeFloatingWidgetView.findViewById( R.id.remove_img );
        mWindowManager.addView( removeFloatingWidgetView, paramRemove );
        return remove_image_view;
    }

    private void addFloatingWidgetView( LayoutInflater inflater )
    {
        mFloatingWidgetView = inflater.inflate( R.layout.floating_widget_layout, null );
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams( WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, LAYOUT_FLAG, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT );
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 100;
        mWindowManager.addView( mFloatingWidgetView, params );
        collapsedView = mFloatingWidgetView.findViewById( R.id.collapse_view );
    }

    private void getWindowManagerDefaultDisplay()
    {
        mWindowManager.getDefaultDisplay().getSize( szWindow );
    }

    private void implementTouchListenerToFloatingWidgetView()
    {
        mFloatingWidgetView.findViewById( R.id.root_container ).setOnTouchListener( new View.OnTouchListener()
        {
            long time_start = 0, time_end = 0;
            boolean isLongClick = false;
            boolean inBounded = false;
            int remove_img_width = 0, remove_img_height = 0;
            Handler handler_longClick = new Handler();
            Runnable runnable_longClick = new Runnable()
            {
                @Override
                public void run()
                {
                    isLongClick = true;
                    removeFloatingWidgetView.setVisibility( View.VISIBLE );
                    onFloatingWidgetLongClick();
                }
            };

            @Override
            public boolean onTouch( View v, MotionEvent event )
            {
                WindowManager.LayoutParams layoutParams = ( WindowManager.LayoutParams ) mFloatingWidgetView.getLayoutParams();
                int x_cord = ( int ) event.getRawX();
                int y_cord = ( int ) event.getRawY();
                int x_cord_Destination, y_cord_Destination;
                switch( event.getAction() )
                {
                    case MotionEvent.ACTION_DOWN:
                        time_start = System.currentTimeMillis();
                        handler_longClick.postDelayed( runnable_longClick, 600 );
                        remove_img_width = remove_image_view.getLayoutParams().width;
                        remove_img_height = remove_image_view.getLayoutParams().height;
                        x_init_cord = x_cord;
                        y_init_cord = y_cord;
                        x_init_margin = layoutParams.x;
                        y_init_margin = layoutParams.y;
                        return true;
                    case MotionEvent.ACTION_UP:
                        isLongClick = false;
                        removeFloatingWidgetView.setVisibility( View.GONE );
                        remove_image_view.getLayoutParams().height = remove_img_height;
                        remove_image_view.getLayoutParams().width = remove_img_width;
                        handler_longClick.removeCallbacks( runnable_longClick );
                        if( inBounded )
                        {
                            stopSelf();
                            inBounded = false;
                            break;
                        }
                        int x_diff = x_cord - x_init_cord;
                        int y_diff = y_cord - y_init_cord;
                        if( Math.abs( x_diff ) < 5 && Math.abs( y_diff ) < 5 )
                        {
                            time_end = System.currentTimeMillis();
                            if( ( time_end - time_start ) < 300 ) onFloatingWidgetClick();
                        }
                        y_cord_Destination = y_init_margin + y_diff;
                        int barHeight = getStatusBarHeight();
                        if( y_cord_Destination < 0 )
                        {
                            y_cord_Destination = 0;
                        }
                        else if( y_cord_Destination + ( mFloatingWidgetView.getHeight() + barHeight ) > szWindow.y )
                        {
                            y_cord_Destination = szWindow.y - ( mFloatingWidgetView.getHeight() + barHeight );
                        }
                        layoutParams.y = y_cord_Destination;
                        inBounded = false;
                        resetPosition( x_cord );
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        int x_diff_move = x_cord - x_init_cord;
                        int y_diff_move = y_cord - y_init_cord;
                        x_cord_Destination = x_init_margin + x_diff_move;
                        y_cord_Destination = y_init_margin + y_diff_move;
                        if( isLongClick )
                        {
                            int x_bound_left = szWindow.x / 2 - ( int ) ( remove_img_width * 1.5 );
                            int x_bound_right = szWindow.x / 2 + ( int ) ( remove_img_width * 1.5 );
                            int y_bound_top = szWindow.y - ( int ) ( remove_img_height * 1.5 );
                            if( ( x_cord >= x_bound_left && x_cord <= x_bound_right ) && y_cord >= y_bound_top )
                            {
                                inBounded = true;
                                int x_cord_remove = ( int ) ( ( szWindow.x - ( remove_img_height * 1.5 ) ) / 2 );
                                int y_cord_remove = ( int ) ( szWindow.y - ( ( remove_img_width * 1.5 ) + getStatusBarHeight() ) );
                                if( remove_image_view.getLayoutParams().height == remove_img_height )
                                {
                                    remove_image_view.getLayoutParams().height = ( int ) ( remove_img_height * 1.5 );
                                    remove_image_view.getLayoutParams().width = ( int ) ( remove_img_width * 1.5 );
                                    WindowManager.LayoutParams param_remove = ( WindowManager.LayoutParams ) removeFloatingWidgetView.getLayoutParams();
                                    param_remove.x = x_cord_remove;
                                    param_remove.y = y_cord_remove;
                                    mWindowManager.updateViewLayout( removeFloatingWidgetView, param_remove );
                                }
                                layoutParams.x = x_cord_remove + ( Math.abs( removeFloatingWidgetView.getWidth() - mFloatingWidgetView.getWidth() ) ) / 2;
                                layoutParams.y = y_cord_remove + ( Math.abs( removeFloatingWidgetView.getHeight() - mFloatingWidgetView.getHeight() ) ) / 2;
                                mWindowManager.updateViewLayout( mFloatingWidgetView, layoutParams );
                                break;
                            }
                            else
                            {
                                inBounded = false;
                                remove_image_view.getLayoutParams().height = remove_img_height;
                                remove_image_view.getLayoutParams().width = remove_img_width;
                                onFloatingWidgetClick();
                            }
                        }
                        layoutParams.x = x_cord_Destination;
                        layoutParams.y = y_cord_Destination;
                        mWindowManager.updateViewLayout( mFloatingWidgetView, layoutParams );
                        return true;
                }
                return false;
            }
        } );
    }

    private void implementClickListeners()
    {
        mFloatingWidgetView.findViewById( R.id.close_floating_view ).setOnClickListener( this );
        mFloatingWidgetView.findViewById( R.id.close_expanded_view ).setOnClickListener( this );
        mFloatingWidgetView.findViewById( R.id.collapse_view ).setOnClickListener( this );
        mFloatingWidgetView.findViewById( R.id.open_activity_button ).setOnClickListener( this );
    }

    @Override
    public void onClick( View v )
    {
        switch( v.getId() )
        {
            case R.id.collapse_view:
                Log.d( "s2s", "testing problem" );
                Intent intent = new Intent("com.twidroid.SendTweet");
                startActivity(intent);
                Intent homeIntent = new Intent( this, StartTask .class );
                homeIntent.addFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
                startActivity( homeIntent );
                break;
        }
    }

    private void onFloatingWidgetLongClick()
    {
        WindowManager.LayoutParams removeParams = ( WindowManager.LayoutParams ) removeFloatingWidgetView.getLayoutParams();
        int x_cord = ( szWindow.x - removeFloatingWidgetView.getWidth() ) / 2;
        int y_cord = szWindow.y - ( removeFloatingWidgetView.getHeight() + getStatusBarHeight() );
        removeParams.x = x_cord;
        removeParams.y = y_cord;
        mWindowManager.updateViewLayout( removeFloatingWidgetView, removeParams );
    }

    private void resetPosition( int x_cord_now )
    {
        if( x_cord_now <= szWindow.x / 2 )
        {
            isLeft = true;
            moveToLeft( x_cord_now );
        }
        else
        {
            isLeft = false;
            moveToRight( x_cord_now );
        }
    }

    private void moveToLeft( final int current_x_cord )
    {
        final int x = szWindow.x - current_x_cord;
        new CountDownTimer( 500, 5 )
        {
            WindowManager.LayoutParams mParams = ( WindowManager.LayoutParams ) mFloatingWidgetView.getLayoutParams();

            public void onTick( long t )
            {
                long step = ( 500 - t ) / 5;
                mParams.x = 0 - ( int ) ( current_x_cord * current_x_cord * step );
                mWindowManager.updateViewLayout( mFloatingWidgetView, mParams );
            }

            public void onFinish()
            {
                mParams.x = 0;
                mWindowManager.updateViewLayout( mFloatingWidgetView, mParams );
            }
        }.start();
    }

    private void moveToRight( final int current_x_cord )
    {
        new CountDownTimer( 500, 5 )
        {
            WindowManager.LayoutParams mParams = ( WindowManager.LayoutParams ) mFloatingWidgetView.getLayoutParams();

            public void onTick( long t )
            {
                long step = ( 500 - t ) / 5;
                mParams.x = ( int ) ( szWindow.x + ( current_x_cord * current_x_cord * step ) - mFloatingWidgetView.getWidth() );
                mWindowManager.updateViewLayout( mFloatingWidgetView, mParams );
            }

            public void onFinish()
            {
                mParams.x = szWindow.x - mFloatingWidgetView.getWidth();
                mWindowManager.updateViewLayout( mFloatingWidgetView, mParams );
            }
        }.start();
    }

    private double bounceValue( long step, long scale )
    {
        double value = scale * java.lang.Math.exp( -0.055 * step ) * java.lang.Math.cos( 0.08 * step );
        return value;
    }

    private boolean isViewCollapsed()
    {
        return mFloatingWidgetView == null || mFloatingWidgetView.findViewById( R.id.collapse_view ).getVisibility() == View.VISIBLE;
    }

    private int getStatusBarHeight()
    {
        return ( int ) Math.ceil( 25 * getApplicationContext().getResources().getDisplayMetrics().density );
    }

    @Override
    public void onConfigurationChanged( Configuration newConfig )
    {
        super.onConfigurationChanged( newConfig );
        getWindowManagerDefaultDisplay();
        WindowManager.LayoutParams layoutParams = ( WindowManager.LayoutParams ) mFloatingWidgetView.getLayoutParams();
        if( newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE )
        {
            if( layoutParams.y + ( mFloatingWidgetView.getHeight() + getStatusBarHeight() ) > szWindow.y )
            {
                layoutParams.y = szWindow.y - ( mFloatingWidgetView.getHeight() + getStatusBarHeight() );
                mWindowManager.updateViewLayout( mFloatingWidgetView, layoutParams );
            }
            if( layoutParams.x != 0 && layoutParams.x < szWindow.x )
            {
                resetPosition( szWindow.x );
            }
        }
        else if( newConfig.orientation == Configuration.ORIENTATION_PORTRAIT )
        {
            if( layoutParams.x > szWindow.x )
            {
                resetPosition( szWindow.x );
            }
        }
    }

    private void onFloatingWidgetClick()
    {
        if( isViewCollapsed() )
        {
            collapsedView.setVisibility( View.GONE );
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if( mFloatingWidgetView != null ) mWindowManager.removeView( mFloatingWidgetView );
        if( removeFloatingWidgetView != null )
        {
            mWindowManager.removeView( removeFloatingWidgetView );
        }
    }
}
