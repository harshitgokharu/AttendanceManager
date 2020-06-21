package com.example.harshitgokharu.attendancemanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText user;
    EditText pass;
    CardView card;
    FirebaseAuth fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user=(EditText)findViewById(R.id.user);
        pass=(EditText)findViewById(R.id.pass);
        card=(CardView)findViewById(R.id.card);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    String user1= user.getText().toString();
                    if(user1.endsWith("@gmail.com")) {
                        // do nothing
                    }
                    else
                    {
                        user1 = user1 + "@gmail.com";
                    }
                    String pass1= pass.getText().toString();
                    fire.signInWithEmailAndPassword(user1,pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                };
            }
        });

        fire=FirebaseAuth.getInstance();
        FirebaseUser user=fire.getCurrentUser();




    }
    private Boolean validate()
    {
        Boolean result=false;

        String user1= user.getText().toString();
        String pass1= pass.getText().toString();

        if(user1.isEmpty() || pass1.isEmpty()) {
            Toast.makeText(this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }
        else
        {
            result=true;
        }

        return result;

    }
}

