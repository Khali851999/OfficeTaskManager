package com.example.officetaskmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView tvlogin;
    EditText etemail;
    EditText etpass;
    Button btnlogin;
    Button btnnew;

    private FirebaseAuth mAuth;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvlogin=findViewById(R.id.tvlogin);
        etemail=findViewById(R.id.etemail);
        etpass=findViewById(R.id.etpass);
        btnlogin=findViewById(R.id.btnlogin);
        btnnew=findViewById(R.id.btnnew);

        mAuth = FirebaseAuth.getInstance();
        //checking for current user
        if(mAuth.getCurrentUser()!=null)
        {Intent intent=new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);}


        mDialog=new ProgressDialog(this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  emaillogin;
                String  passlogin;
                emaillogin=etemail.getText().toString().trim();
                passlogin=etpass.getText().toString().trim();
                if(emaillogin.isEmpty() || passlogin.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Fields cannot be empty",Toast.LENGTH_SHORT).show();}
                else{
                    mDialog.setMessage("Processing");
                    mDialog.show();
                    mAuth.signInWithEmailAndPassword(emaillogin,passlogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                               Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                               Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                               startActivity(intent);
                                mDialog.dismiss();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this," Authentication failed.",Toast.LENGTH_LONG).show();
                                Log.w("error","signInWithEmail:failure", task.getException());
                                mDialog.dismiss();
                            }

                        }
                    });
                }
            }
        });




    }
    public void gotoactivityreg(View view){
        Intent intent=new Intent(MainActivity.this,RegistrationActivity.class);
        startActivity(intent);
    }

}
