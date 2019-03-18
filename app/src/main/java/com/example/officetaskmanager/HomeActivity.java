package com.example.officetaskmanager;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.officetaskmanager.modal.data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity {
FloatingActionButton floatingActionButton;
RecyclerView recyclerView;

private DatabaseReference mdatabase;
private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//firebase
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String Uid=mUser.getUid();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("TaskNote");

//recycler
        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);




        floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating floating input fiels
                AlertDialog.Builder myDialog=new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater myInflater=LayoutInflater.from(HomeActivity.this);
                //adding custized layout to the dialog box
                View myview= myInflater.inflate(R.layout.custominputlayout,null);
                myDialog.setView(myview);
                final AlertDialog dialog=myDialog.create();
                final EditText ettitle=myview.findViewById(R.id.ettitle);
                final EditText etnote=myview.findViewById(R.id.etnote);
                final Button btnsave=myview.findViewById(R.id.btnsave);
                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mtitle=ettitle.getText().toString().trim();
                        String mnote=etnote.getText().toString().trim();
                        if(mtitle.isEmpty() || mnote.isEmpty())
                        {
                            Toast.makeText(HomeActivity.this,"Fields Cannot Be Empty",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //database
                            String id = mdatabase.push().getKey();
                            String date = DateFormat.getDateInstance().format(new Date());

                            data data1 = new data(mtitle, mnote, date, id);
                            mdatabase.child(id).setValue(data1);
                            Toast.makeText(HomeActivity.this, "DATA INSERTED", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                });
                dialog.show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    public static class myviewholder extends RecyclerView.ViewHolder {

        View myview;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            myview = itemView;
        }

        public void settitle(String title) {
            TextView mytitle = myview.findViewById(R.id.title);
            mytitle.setText("title");

        }

        public void setnote(String note) {
            TextView mynote = myview.findViewById(R.id.note);
            mynote.setText("note");

        }

        public void setdate(String date) {
            TextView mydate = myview.findViewById(R.id.date);
            mydate.setText("date");
        }
    }
}
