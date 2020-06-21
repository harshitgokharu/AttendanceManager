package com.example.harshitgokharu.attendancemanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class attendance extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private CheckBox r1;
    private CheckBox r2;
    private CheckBox r3;
    private CheckBox r4;
    private CheckBox r5;
    EditText et;
    int count =0;
    String present="";
    String course;
    String date;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        r1 = (CheckBox) findViewById(R.id.r1);
        r2 = (CheckBox) findViewById(R.id.r2);
        r3 = (CheckBox) findViewById(R.id.r3);
        r4 = (CheckBox) findViewById(R.id.r4);
        r5 = (CheckBox) findViewById(R.id.r5);
        et = (EditText) findViewById(R.id.et);



        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        Intent j = getIntent();
        course = j.getStringExtra("course");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void reset(View v)
    {
        r1.setChecked(false);
        r2.setChecked(false);
        r3.setChecked(false);
        r4.setChecked(false);
        r5.setChecked(false);
        count=0;
        present="";
    }
    public void submit(View v)
    {
        date = et.getText().toString();
        Toast.makeText(attendance.this, date, Toast.LENGTH_SHORT).show();

        if(r1.isChecked())
        {
            count++ ;
            present=present+"1,";
        }
        if(r2.isChecked())
        {
            count++ ;
            present=present+"2,";
        }
        if(r3.isChecked())
        {
            count++ ;
            present=present+"3,";
        }
        if(r4.isChecked())
        {
            count++ ;
            present=present+"4,";
        }
        if(r5.isChecked())
        {
            count++ ;
            present=present+"5,";
        }

        r1.setChecked(false);
        r2.setChecked(false);
        r3.setChecked(false);
        r4.setChecked(false);
        r5.setChecked(false);


        Intent z = new Intent(attendance.this, output.class);
        z.putExtra("course",course);
        z.putExtra("tpresent",count+"/5");
        z.putExtra("present",present);
        z.putExtra("date",date);
        startActivity(z);
        count=0;
        present="";


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id= menuItem.getItemId();
        if(id==R.id.home)
        {
            Intent j = new Intent(attendance.this, SecondActivity.class);
            startActivity(j);
        }
        else if(id==R.id.profile)
        {
            Intent j=new Intent(attendance.this,third.class);
            startActivity(j);
        }
        else if(id==R.id.retrieve)
        {
            Intent j=new Intent(attendance.this,retrieve.class);
            startActivity(j);
        }
        return false;
    }
}

