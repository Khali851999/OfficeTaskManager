package com.example.officetaskmanager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.officetaskmanager.modal.data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
//toolbar
    android.support.v7.widget.Toolbar toolbar;
//firebase
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;
//updatedata()
    private String note;
    private String title;
    private String post_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//     setting custon title bar if not this then default one is also there just cnge manifest file
         toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.hometoolbar);
         setSupportActionBar(toolbar);

//         setting title
         getSupportActionBar().setTitle("");


//firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String Uid = mUser.getUid();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("TaskNote").child(Uid);
        mdatabase.keepSynced(true);

//recycler
        recyclerView = (RecyclerView) findViewById(R.id.recycler1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating floating input fiels
                AlertDialog.Builder myDialog = new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater myInflater = LayoutInflater.from(HomeActivity.this);
                //adding custumized custominputlayout.xml to the dialog box
                View myview = myInflater.inflate(R.layout.custominputlayout, null);
                myDialog.setView(myview);
                final AlertDialog dialog = myDialog.create();
                dialog.show();

                final EditText ettitle = myview.findViewById(R.id.ettitle);
                final EditText etnote = myview.findViewById(R.id.etnote);
                final Button btnsave = myview.findViewById(R.id.btnsave);
                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mtitle = ettitle.getText().toString().trim();
                        String mnote = etnote.getText().toString().trim();
                        if (mtitle.isEmpty() || mnote.isEmpty()) {
                            Toast.makeText(HomeActivity.this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
                        } else {
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


            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<data, myviewholder> adapter = new FirebaseRecyclerAdapter<data, myviewholder>(
                data.class,
                R.layout.dataitem,
                myviewholder.class,
                mdatabase
        ) {
            @Override
            protected void populateViewHolder(final myviewholder viewHolder, final data model, final int position) {
                viewHolder.settitle(model.getTitle());
                viewHolder.setnote(model.getNote());
                viewHolder.setdate(model.getDate());
                viewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                      //getting stored data at position and storing it into string
                        post_key=getRef(position).getKey();
                        title=model.getTitle();
                        note=model.getNote();

                        updatedata();
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);

    }

    public void onBackPressed(View view) {
        Intent mainActivity = new Intent(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
        finish();
    }

    public static class myviewholder extends RecyclerView.ViewHolder {

        View myview;

        public myviewholder(View itemView) {
            super(itemView);
            myview = itemView;
        }

        public void  settitle(String title) {
            TextView mytitle = myview.findViewById(R.id.title);
            mytitle.setText(title);
        }

        public void setnote(String note) {
            TextView mynote = myview.findViewById(R.id.note);
            mynote.setText(note);

        }

        public void setdate(String date) {
            TextView mydate = myview.findViewById(R.id.date);
            mydate.setText(date);
        }
    }
    public void updatedata()
    {
        //creating floating input fiels
        final AlertDialog.Builder myDialog = new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater myInflater = LayoutInflater.from(HomeActivity.this);

        //adding custumized layout updatefieldlayout.xml to the dialog box
        View myview = myInflater.inflate(R.layout.updatefieldlayout, null);
        myDialog.setView(myview);
        final AlertDialog dialog = myDialog.create();
        dialog.show();

        final EditText ettitleupd = myview.findViewById(R.id.ettitleupd);
        final EditText etnoteupd = myview.findViewById(R.id.etnoteupd);
        final Button update=myview.findViewById(R.id.update);
        final Button delete=myview.findViewById(R.id.delete);

//setting data on the update dialog box to update
        ettitleupd.setText(title);
        ettitleupd.setSelection(title.length());
        etnoteupd.setText(note);
        etnoteupd.setSelection(note.length());


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title=ettitleupd.getText().toString().trim();
                note=etnoteupd.getText().toString().trim();
                String Date=DateFormat.getDateInstance().format(new Date());

// creating object of modal class and transferring values to it
                data data1= new data(title,note,Date,post_key);
//updating firebase corresponding to id=post_key
                mdatabase.child(post_key).setValue(data1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(HomeActivity.this,"Updated",Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.dismiss();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

       // deleting data
                mdatabase.child(post_key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(HomeActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.refresh:

//             default   snackbar
                Snackbar snack=Snackbar.make(findViewById(android.R.id.content),"Refreshing",Snackbar.LENGTH_SHORT)
                        .setAction("VIEW", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snack.setActionTextColor(Color.WHITE);
                snack.getView().setBackgroundResource(R.color.colorPrimaryDark);
                TextView textView = (TextView)snack.getView().findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snack.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

