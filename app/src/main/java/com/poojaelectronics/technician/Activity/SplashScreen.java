package com.poojaelectronics.technician.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    CardView card ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getSupportActionBar().hide();

        final ImageView btnOvershoot =  findViewById(R.id.pelogo);

        card = findViewById(R.id.center_card);

        final Animation animOvershoot = AnimationUtils.loadAnimation(this, R.anim.overshoot);


        btnOvershoot.startAnimation(animOvershoot);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
               presentActivity(card);
            }
        }, 2000);




    }



    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(LoginActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(LoginActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);


        ActivityCompat.startActivity(this, intent, options.toBundle());


    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
