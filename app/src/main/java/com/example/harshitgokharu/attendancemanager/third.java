package com.example.harshitgokharu.attendancemanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.Collator;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.security.cert.X509Certificate;

public class third extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseFirestore db;
    private DocumentReference noteref;
    private static final String TAG = "MainActivity";
    public String web;
    WebView ourBrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);










       FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
        String p=currentFirebaseUser.getUid();
        db=FirebaseFirestore.getInstance();
        noteref=db.collection("teachers").document(p);
        noteref.get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        web = document.getString("web");
                        Toast.makeText(third.this, web, Toast.LENGTH_SHORT).show();
                        ourBrow=(WebView)findViewById(R.id.webView);
                        ourBrow.setWebViewClient(new MyBrowser());
                        ourBrow.getSettings().setJavaScriptEnabled(true);
                        ourBrow.loadUrl(web);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {

                    Toast.makeText(third.this, "Error getting documents.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Toast.makeText(third.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private class MyBrowser extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request ) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }










    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id= menuItem.getItemId();
        if(id==R.id.home)
        {
            Intent j = new Intent(third.this, SecondActivity.class);
            startActivity(j);
        }
        else if(id==R.id.profile)
        {
            Intent j=new Intent(third.this,third.class);
            startActivity(j);
        }
        else if(id==R.id.retrieve)
        {
            Intent j=new Intent(third.this,retrieve.class);
            startActivity(j);
        }
        return false;

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



}
