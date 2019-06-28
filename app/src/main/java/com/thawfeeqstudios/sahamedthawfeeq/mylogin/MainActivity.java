package com.thawfeeqstudios.sahamedthawfeeq.mylogin;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button bbb;
    private ProgressBar pp;
    public static final String SHARED_PREF_NAME="tech";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bbb = findViewById(R.id.bbb);
        Button bbn = findViewById(R.id.bbn);
        Button thaw = findViewById(R.id.thaw);
        pp = findViewById(R.id.ppp);
        thaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Developer:THAWFEEQ",Toast.LENGTH_LONG).show();
            }
        });
        bbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate();
                fady();
                showy();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        finish();
                        Intent kk = new Intent(MainActivity.this,signup.class);
                        startActivity(kk);
                    }
                },3000);

            }
        });
        bbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Profile.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private  void animate(){
        ValueAnimator bv = ValueAnimator.ofInt(bbb.getMeasuredWidth(),getfabby());
        bv.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int r = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = bbb.getLayoutParams();
                layoutParams.width = r;
                bbb.requestLayout();
            }
        });
        bv.setDuration(250);
        bv.start();
    }
    private void fady(){
        bbb.setText(null);
    }
    private void showy(){
        pp.setAlpha(1f);
        pp.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        pp.setVisibility(View.VISIBLE);
    }
    private int getfabby(){
        return (int) getResources().getDimension(R.dimen.fab_size);
    }
}
