package com.thawfeeqstudios.sahamedthawfeeq.mylogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    private EditText userage,userheight,userweight,userbg,name;
    public static final String KEY_EMAIL="email";
    private String mail;
    public static final String SHARED_PREF_NAME="tech";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        userage = findViewById(R.id.userage);
        userheight = findViewById(R.id.userheight);
        userweight=findViewById(R.id.userweight);
        userbg=findViewById(R.id.userbg);
        name=findViewById(R.id.name);
        Button upbutt = findViewById(R.id.upbutt);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String hr = sharedPreferences.getString("email","nothing");
        mail = hr.trim();
        upbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checky();
            }
        });
    }
    private void checky(){
        String url="http://thawfeeqstudios.000webhostapp.com/reguser/update.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equalsIgnoreCase("updated")){
                            Toast.makeText(Update.this, " information is successfully updated!!", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Update.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else if(response.trim().equalsIgnoreCase("error")){
                            Toast.makeText(Update.this, " Oops..! an error occured..!", Toast.LENGTH_LONG).show();
                        }
                        else if(response.trim().equalsIgnoreCase("fill")){
                            Toast.makeText(Update.this, " please fill all fields..!", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update.this, " Oops..! an error occured..!", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> prams = new HashMap<>();
                prams.put(KEY_EMAIL,mail);
                prams.put("bloodgroup",userbg.getText().toString().trim());
                prams.put("username",name.getText().toString().trim());
                prams.put("age",userage.getText().toString().trim());
                prams.put("height",userheight.getText().toString().trim());
                prams.put("weight",userweight.getText().toString().trim());
                return prams;
            }
        };
        Mysingleton.getmysingleton(Update.this).addToRequestQueue(stringRequest);
    }
}
