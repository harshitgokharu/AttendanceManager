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
import android.widget.EditText;
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

public class show extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private CardView coa;
    private CardView dbms;
    private CardView os;
    private CardView pl;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    String w;
    private FirebaseFirestore db;
    private DocumentReference noteref;
    String date;
    EditText et;
    String course;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        coa=(CardView)findViewById(R.id.coa);
        dbms=(CardView)findViewById(R.id.dbms);
        os=(CardView)findViewById(R.id.os);
        pl=(CardView)findViewById(R.id.pl);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        et=(EditText)findViewById(R.id.et);
        Intent z = getIntent();
        course = z.getStringExtra("course");

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
        String p = currentFirebaseUser.getUid();
        db = FirebaseFirestore.getInstance();





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submit(View v)
    {
        display();
    }


    private void display() {
        date = et.getText().toString();
        w = course+" "+date;
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        noteref = db.collection("attendance").document(w);
        noteref.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String course1 = document.getString("course");
                                tv1.setText("COURSE: " + course1);

                                String totalpresent1 = document.getString("totalpresent");
                                tv2.setText("TOTAL PRESENT : " + totalpresent1);

                                String studentspresent1 = document.getString("studentspresent");
                                tv3.setText("STUDENTS PRESENT: " + studentspresent1);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Toast.makeText(show.this, "Error getting documents.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(show.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id= menuItem.getItemId();
        if(id==R.id.home)
        {
            Intent j = new Intent(show.this, SecondActivity.class);
            startActivity(j);
        }
        else if(id==R.id.profile)
        {
            Intent j=new Intent(show.this,third.class);
            startActivity(j);
        }
        else if(id==R.id.retrieve)
        {
            Intent j=new Intent(show.this,retrieve.class);
            startActivity(j);
        }
        return false;
    }
}
