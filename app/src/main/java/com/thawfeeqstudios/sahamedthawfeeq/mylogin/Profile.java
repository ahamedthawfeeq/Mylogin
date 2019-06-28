package com.thawfeeqstudios.sahamedthawfeeq.mylogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private TextView bg,uname,uage,uh,uw,uemail;
    private String mail,un,bgroup,ua,uheight,uweight,umail;
    private ImageView agpng,bpng,hpng,wpng,maily,ppic;
    public static final String KEY_EMAIL="email";
    public static final String SHARED_PREF_NAME="tech";
    conchecker cff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bg = findViewById(R.id.bg);
        uname=findViewById(R.id.uname);
        uage=findViewById(R.id.uage);
        uh=findViewById(R.id.uh);
        uw=findViewById(R.id.uw);
        Button up = findViewById(R.id.up);
        uemail=findViewById(R.id.uemail);
        agpng=findViewById(R.id.agpng);
        bpng=findViewById(R.id.bldpng);
        hpng=findViewById(R.id.hpng);
        wpng=findViewById(R.id.wpng);
        maily=findViewById(R.id.mailpng);
        ppic=findViewById(R.id.ppic);
        cff=new conchecker(Profile.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String hr = sharedPreferences.getString("email","nothing");
                mail = hr.trim();
                getdetails();
            }
        },2000);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cff.isconnected()){
                    Intent intent = new Intent(Profile.this,Update.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Profile.this,"please connect to internet..",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getdetails(){
        String url="http://thawfeeqstudios.000webhostapp.com/reguser/profile.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("users");
                            JSONObject data = jsonArray.getJSONObject(0);
                            bgroup = data.getString("bloodgroup");
                            un = data.getString("username");
                            ua = data.getString("age");
                            uheight = data.getString("height");
                            uweight = data.getString("weight");
                            umail = data.getString("email");
                            uname.setText(un);
                            Glide.with(Profile.this).load(R.drawable.blood).thumbnail(0.1f).into(bpng);
                            bpng.startAnimation(AnimationUtils.loadAnimation(Profile.this,R.anim.slidescale));
                            bpng.setVisibility(View.VISIBLE);
                            bg.setText(bgroup);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(Profile.this).load(R.drawable.ic_person_black_24dp).thumbnail(0.1f).into(maily);
                                    maily.startAnimation(AnimationUtils.loadAnimation(Profile.this,R.anim.slidescale));
                                    maily.setVisibility(View.VISIBLE);
                                    uemail.setText(umail);
                                }
                            },250);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(Profile.this).load(R.drawable.calendar).thumbnail(0.1f).into(agpng);
                                    agpng.startAnimation(AnimationUtils.loadAnimation(Profile.this,R.anim.slidescale));
                                    agpng.setVisibility(View.VISIBLE);
                                    uage.setText(ua);
                                }
                            },750);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(Profile.this).load(R.drawable.images).apply(new RequestOptions().format(DecodeFormat.PREFER_RGB_565)).thumbnail(0.1f).into(hpng);
                                    hpng.startAnimation(AnimationUtils.loadAnimation(Profile.this,R.anim.slidescale));
                                    hpng.setVisibility(View.VISIBLE);
                                    uh.setText(uheight);
                                }
                            },1250);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(Profile.this).load(R.drawable.weight).thumbnail(0.1f).into(wpng);
                                    wpng.startAnimation(AnimationUtils.loadAnimation(Profile.this,R.anim.slidescale));
                                    wpng.setVisibility(View.VISIBLE);
                                    uw.setText(uweight);
                                }
                            },1750);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(Profile.this).load(R.drawable.styleboy).thumbnail(0.1f).into(ppic);
                                    ppic.animate().scaleX(1f).scaleY(1f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(400).start();
                                }
                            },2500);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this,"something went wrong...",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> prams = new HashMap<>();
                prams.put(KEY_EMAIL,mail);
                return prams;
            }
        };
        Mysingleton.getmysingleton(Profile.this).addToRequestQueue(stringRequest);
    }
}
