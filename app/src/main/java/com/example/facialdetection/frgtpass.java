package com.example.facialdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class frgtpass extends AppCompatActivity {

    EditText rstmail;
    Button rst,bck;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frgtpass);

        rstmail=findViewById(R.id.rstmail);
        rst=findViewById(R.id.rst);
        bck=findViewById(R.id.bck);
        mAuth=FirebaseAuth.getInstance();
        rst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = rstmail.getText().toString();
                if (email.isEmpty()){
                    rstmail.setError("Enter A Mail !!!");
                    rstmail.requestFocus();
                    return;
                }

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(frgtpass.this,"Check Your Mail And Reset Password... ",Toast.LENGTH_LONG).show();

                        }
                        else{
                            String message = task.getException().getMessage();
                            Toast.makeText(frgtpass.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(frgtpass.this,MainActivity.class));
            }
        });
    }
}