package com.example.facialdetection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class details extends AppCompatActivity {

    RecyclerView recview;
    myadapter adapter;
    String d1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recview=findViewById(R.id.recview);

        d1=getIntent().getStringExtra("val");
        recview.setLayoutManager(new LinearLayoutManager(details.this));

        FirebaseRecyclerOptions<dataholder> options = new FirebaseRecyclerOptions
                .Builder<dataholder>().setQuery(FirebaseDatabase.getInstance().getReference().child("Details")
                .child(d1),dataholder.class)
                .build();

        adapter=new myadapter(options);
        recview.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}