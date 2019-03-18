package com.example.officetaskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    TextView tvlogin;
    EditText etemail;
    EditText etpass;
    Button btnlogin;
    Button btnnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvlogin=findViewById(R.id.tvlogin);
        etemail=findViewById(R.id.etemail);
        etpass=findViewById(R.id.etpass);
        btnlogin=findViewById(R.id.btnlogin);
        btnnew=findViewById(R.id.btnnew);

        FirebaseApp.initializeApp(MainActivity.this);


    }
    public void gotoactivityreg(View view){
        Intent intent=new Intent(MainActivity.this,RegistrationActivity.class);
        startActivity(intent);
    }

}
