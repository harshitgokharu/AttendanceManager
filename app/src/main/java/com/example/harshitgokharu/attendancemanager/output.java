package com.example.harshitgokharu.attendancemanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class output extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    TextView tv1;
    TextView tv2;
    TextView tv3;

    private FirebaseFirestore db;
    private DocumentReference noteref;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);





        Intent z = getIntent();
        String course = z.getStringExtra("course");
        String tpresent = z.getStringExtra("tpresent");
        String present = z.getStringExtra("present");
        String date=z.getStringExtra("date");
        tv1.setText("COURSE: " + course);
        tv2.setText("TOTAL PRESENT: " + tpresent);
        tv3.setText("STUDENTS PRESENT: " + present);

       /* b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=1;
            }

        }); */

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
            String p = currentFirebaseUser.getUid();
            db = FirebaseFirestore.getInstance();
            String w = course+ " "+ date;
            noteref = db.collection("attendance").document(w);

            Map<String, Object> sample = new HashMap<>();
            sample.put("course", course);
            sample.put("totalpresent", tpresent);
            sample.put("studentspresent", present);


            noteref
                    .set(sample)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(output.this, "DocumentSnapshot successfully written!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(output.this, "Error writing document", Toast.LENGTH_SHORT).show();

                        }
                    });
        }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id= menuItem.getItemId();
        if(id==R.id.home)
        {
            Intent j = new Intent(output.this, SecondActivity.class);
            startActivity(j);
        }
        else if(id==R.id.profile)
        {
            Intent j=new Intent(output.this,third.class);
            startActivity(j);
        }
        else if(id==R.id.retrieve)
        {
            Intent j=new Intent(output.this,retrieve.class);
            startActivity(j);
        }
        return false;
    }
}

