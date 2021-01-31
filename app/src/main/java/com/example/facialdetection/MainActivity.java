package com.example.facialdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    View view,view2;
    ImageView imageview2;
    TextView pass,frgt;
    EditText mail;

    Button login,signup;

    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth=FirebaseAuth.getInstance();
        pass=(TextView)findViewById(R.id.pass);
        mail=findViewById(R.id.mail);
        frgt=(TextView)findViewById(R.id.frgt);

        login=(Button)findViewById(R.id.login);
        signup=(Button)findViewById(R.id.signup);

        frgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,frgtpass.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mail.getText().toString();
                String passw = pass.getText().toString();


                    if (email.isEmpty()){
                        mail.setError("Enter Mail !!");
                        mail.requestFocus();
                        return;
                    }
                    if (passw.isEmpty()){
                        pass.setError("Enter Password !!");
                        pass.requestFocus();
                        return;
                    }
                    mFirebaseAuth.signInWithEmailAndPassword(email,passw)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    String accdetails = mail.getText().toString();
                                    Intent intent = new Intent(MainActivity.this,face.class);
                                    intent.putExtra("accdetails",accdetails);
                                    startActivity(intent);

                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"Incorrect Credential !!! ",Toast.LENGTH_SHORT).show();
                        }
                    });



            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,signup.class);
                startActivity(intent);
                finish();
            }
        });


    }







}




