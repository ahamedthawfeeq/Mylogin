package com.thawfeeqstudios.sahamedthawfeeq.mylogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class signup extends AppCompatActivity {
    EditText edit_username;
    EditText edit_email;
    EditText edit_bloodgroup;
    EditText edit_age;
    EditText edit_height;
    EditText edit_weight;
    EditText edit_pass;
    Button btn_sign;
    Button btn_login;
    public static final String REGISTER_URL = "http://thawfeeqstudios.000webhostapp.com/reguser/register.php";
    conchecker cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edit_username = findViewById(R.id.id_username);
        edit_email =  findViewById(R.id.id_email);
        edit_pass = findViewById(R.id.id_pass);
        edit_bloodgroup =findViewById(R.id.blood);
        edit_age = findViewById(R.id.agge);
        edit_height=findViewById(R.id.hhh);
        edit_weight = findViewById(R.id.www);
        btn_sign =  findViewById(R.id.btn_signup);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        btn_login=findViewById(R.id.btn_login);
        cc=new conchecker(signup.this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin=new Intent(signup.this,logup.class);
                startActivity(intentLogin);
            }
        });
    }

    private void registerUser() {
        if(cc.isconnected()){
            String username = edit_username.getText().toString().trim().toLowerCase();
            String email = edit_email.getText().toString().trim().toLowerCase();
            String password = edit_pass.getText().toString().trim().toLowerCase();
            String bloodgroup = edit_bloodgroup.getText().toString().trim().toLowerCase();
            String age = edit_age.getText().toString().trim().toLowerCase();
            String height = edit_height.getText().toString().trim().toLowerCase();
            String weight = edit_weight.getText().toString().trim().toLowerCase();
            register(username, password, email,bloodgroup,age,height,weight);
        }
        else{
            Toast.makeText(signup.this,"please connect to internet..",Toast.LENGTH_SHORT).show();
        }
    }

    private void register(String username, String password, String email,String bloodgroup,String age,String height,String weight){
        String urlSuffix = "?username=" + username + "&password=" + password + "&email=" + email + "&bloodgroup=" + bloodgroup + "&age=" + age + "&height=" + height + "&weight=" + weight;
        class RegisterUser extends AsyncTask<String, Void, String> {

            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(signup.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                try {
                    URL url=new URL(REGISTER_URL+s);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    BufferedReader bufferReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result=bufferReader.readLine();
                    return  result;

                }catch (Exception e){
                    return null;
                }
            }

        }
        RegisterUser ur=new RegisterUser();
        ur.execute(urlSuffix);
    }
}

