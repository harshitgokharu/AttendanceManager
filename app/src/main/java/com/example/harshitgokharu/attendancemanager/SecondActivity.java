package com.example.harshitgokharu.attendancemanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SecondActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView c1t;
    private TextView c2t;
    private TextView c3t;
    private TextView c4t;
    private String sub1,sub2,sub3,sub4;
    public FirebaseFirestore db;
    public DocumentReference noteref;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);


        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        c1t=(TextView)findViewById(R.id.c1t);
        c2t=(TextView)findViewById(R.id.c2t);
        c3t=(TextView)findViewById(R.id.c3t);
        c4t=(TextView)findViewById(R.id.c4t);

        db = FirebaseFirestore.getInstance();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String p = currentFirebaseUser.getUid();
        noteref = db.collection("teachers").document(p);
        noteref.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                sub1 = document.getString("sub1");
                                c1t.setText(sub1);

                                sub2 = document.getString("sub2");
                                c2t.setText(sub2);

                                sub3 = document.getString("sub3");
                                c3t.setText(sub3);

                                sub4 = document.getString("sub4");
                                c4t.setText(sub4);


                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Toast.makeText(SecondActivity.this, "Error getting documents.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SecondActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    public void disp1(View v)
    {
        String course= sub1;
        display(course);
    }
    public void disp2(View v)
    {
        String course= sub2;
        display(course);
    }
    public void disp3(View v)
    {
        String course= sub3;
        display(course);
    }
    public void disp4(View v)
    {
        String course= sub4;
        display(course);
    }

        private void display(String course) {
            Intent j = new Intent(SecondActivity.this, attendance.class);
            j.putExtra("course", course);
            startActivity(j);
        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id= menuItem.getItemId();
        if(id==R.id.home)
        {
            Intent j = new Intent(SecondActivity.this, SecondActivity.class);
            startActivity(j);
        }
        else if(id==R.id.profile)
        {
            Intent j=new Intent(SecondActivity.this,third.class);
            startActivity(j);
        }
        else if(id==R.id.retrieve)
        {
            Intent j=new Intent(SecondActivity.this,retrieve.class);
            startActivity(j);
        }
        return false;
    }
}

