package com.example.officetaskmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    TextView tvreg;
    EditText etemailreg;
    EditText etpassreg;
    Button btnreg;
    Button btnloginrev;

    FirebaseAuth mAuth;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

         tvreg =findViewById(R.id.tvreg);
         etemailreg =findViewById(R.id.etemailreg);
         etpassreg =findViewById(R.id.etpassreg);
         btnreg =findViewById(R.id.btnreg);
         btnloginrev =findViewById(R.id.btnloginrev);

        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailreg=etemailreg.getText().toString().trim();
                String passreg=etpassreg.getText().toString().trim();
                if(emailreg.isEmpty()|| passreg.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Fields Cannot be Empty", Toast.LENGTH_SHORT).show();
                }

                //Registering Username and pass on firebase
                mDialog.setMessage("Processing");
                mDialog.show();

                mAuth.createUserWithEmailAndPassword(emailreg,passreg).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this,"Registration Success",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            Intent intent=new Intent(RegistrationActivity.this,HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegistrationActivity.this,"Problem creating an account",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                }
                );

            }
        });

        btnloginrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegistrationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
