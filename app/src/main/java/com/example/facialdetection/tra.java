package com.example.facialdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tra extends AppCompatActivity {

    TextView name,age,gender;
    private FirebaseDatabase Database;
    private DatabaseReference Ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra);

        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gender);
        String val1=getIntent().getStringExtra("Val");
        Database=FirebaseDatabase.getInstance();
        Ref=Database.getReference().child("Details");
        Ref.child("lipun3434gmailcom").child(val1).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data=snapshot.getValue(String.class);
                name.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Ref.child("lipun3434gmailcom").child(val1).child("age").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data=snapshot.getValue(String.class);
                age.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Ref.child("lipun3434gmailcom").child(val1).child("gender").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data=snapshot.getValue(String.class);
                gender.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}