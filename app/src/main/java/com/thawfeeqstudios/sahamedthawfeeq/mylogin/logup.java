package com.thawfeeqstudios.sahamedthawfeeq.mylogin;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.VISIBLE;

public class logup extends AppCompatActivity {


    public static final String LOGIN_URL= "http://thawfeeqstudios.000webhostapp.com/reguser/login.php";
    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";
    public static final String LOGIN_SUCCESS="success";
    public static final String SHARED_PREF_NAME="tech";
    public static final String EMAIL_SHARED_PREF="email";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button BtnLogin;
    private View view;
    private ProgressBar pp;
    private int cv,cbx,cby;
    private Intent intent;
    conchecker cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);
        editTextEmail=findViewById(R.id.editText_email);
        editTextPassword=findViewById(R.id.editText_password);
        BtnLogin=findViewById(R.id.btn_login);
        cbx = (this.getResources().getDisplayMetrics().widthPixels)/2;
        cby = ((this.getResources().getDisplayMetrics().heightPixels)/2) + ( (int)getResources().getDimension(R.dimen.bheight) );
        pp =findViewById(R.id.ppp);
        view =findViewById(R.id.vv);
        cd = new conchecker(logup.this);
        cv = (int)getResources().getDimension(R.dimen.btnwid);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        if(cd.isconnected()){
            final String email = editTextEmail.getText().toString().trim();
            final String password = editTextPassword.getText().toString().trim();
            animate();
            fadeshow();
            showprogress();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equalsIgnoreCase(LOGIN_SUCCESS)){

                                SharedPreferences sharedPreferences = logup.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                                editor.putString(EMAIL_SHARED_PREF, email);
                                editor.commit();
                                intent = new Intent(logup.this, MainActivity.class);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        reveal();
                                    }
                                },2000);

                            }else{
                                Toast.makeText(logup.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                                pp.setVisibility(View.INVISIBLE);
                                ValueAnimator bcc = ValueAnimator.ofInt(getfabwidth(),cv);
                                bcc.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        int y = (int) valueAnimator.getAnimatedValue();
                                        ViewGroup.LayoutParams layoutParams = BtnLogin.getLayoutParams();
                                        layoutParams.width = y;
                                        BtnLogin.requestLayout();
                                    }
                                });
                                bcc.setDuration(250);
                                bcc.start();
                                BtnLogin.setText(R.string.aaa);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> prams = new HashMap<>();
                    prams.put(KEY_EMAIL, email);
                    prams.put(KEY_PASSWORD, password);

                    return prams;
                }
            };
            Mysingleton.getmysingleton(logup.this).addToRequestQueue(stringRequest);
        }
        else{
            Toast.makeText(logup.this,"please connect to internet..",Toast.LENGTH_SHORT).show();
        }
    }
    private void reveal(){
        int cx =cbx;
        int cy =cby;
        int s =0;
        int f = (int)Math.hypot(view.getWidth(),view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view,cx,cy,s,f);
        view.setVisibility(VISIBLE);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                nexty();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.setDuration(350);
        animator.start();
    }
    private void nexty(){
        startActivity(intent);
    }

    private void fadeshow() {
        BtnLogin.setText(null);

    }
    private void showprogress() {
        pp.setAlpha(1f);
        pp.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        pp.setVisibility(View.VISIBLE);
    }
    private void animate() {
        ValueAnimator anim = ValueAnimator.ofInt(BtnLogin.getMeasuredWidth(),getfabwidth());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = BtnLogin.getLayoutParams();
                layoutParams.width = val;
                BtnLogin.requestLayout();
            }
        });
        anim.setDuration(250);
        anim.start();
    }

    private int getfabwidth() {
        return (int) getResources().getDimension(R.dimen.fab_size);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
        if(loggedIn){
            Intent intent = new Intent(logup.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        view.setVisibility(View.INVISIBLE);
    }
}
