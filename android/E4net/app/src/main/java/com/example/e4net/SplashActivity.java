package com.example.e4net;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    TextView tv_splash;
    ImageView iv_splash;    // 안씀
    ImageView iv_e4netStartLogo;

    List<Drawable> drawableList = new ArrayList<>();

    Animation anim_fadeIn;
    Animation anim_bounce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        // 3초 후에 SplashHandler가 작동하도록
        handler.postDelayed(new SplashHandler(), 2500);

        constraintLayout = findViewById(R.id.constraintLayout);
        tv_splash = findViewById(R.id.tv_splash);
//        iv_splash = findViewById(R.id.iv_splash);
        iv_e4netStartLogo = findViewById(R.id.iv_e4netStartLogo);

        anim_fadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_splash_fadein);
        anim_bounce = AnimationUtils.loadAnimation(this, R.anim.anim_splash_bounce);

        anim_fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("[splash]", "onAnimationStart!");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("[splash]", "onAnimationEnd!");
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d("[splash]", "onAnimationRepeat!");
            }
        });
//        tv_splash.startAnimation(anim_fadeIn);
//        iv_e4netStartLogo.startAnimation(anim_bounce);
        iv_e4netStartLogo.setAnimation(anim_bounce);
        tv_splash.setAnimation(anim_fadeIn);


        // --------- 미르 ----------
        drawableList.add(getResources().getDrawable(R.drawable.grinning3));
        drawableList.add(getResources().getDrawable(R.drawable.expressionless));

//        getAnimation().start();
    }

    private class SplashHandler implements Runnable {
        @Override
        public void run() {
            startActivity(new Intent(getApplication(), LoginActivity.class));
            SplashActivity.this.finish();
        }
    }
//    private Thread getAnimation(){
//        return new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i <= 4; i++){
//                    Drawable drawable = drawableList.get(i%2);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            iv_splash.setImageDrawable(drawable);
//                        }
//                    });
//                    try {
//                        Thread.sleep(1000);
//                    }catch (Exception e){
//
//                    }
//                }
//                startActivity(
//                        new Intent(SplashActivity.this,
//                                LoginActivity.class));
//                SplashActivity.this.finish();
//            }
//        });
//    }

}