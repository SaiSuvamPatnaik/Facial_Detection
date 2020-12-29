package com.example.facialdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class delete extends AppCompatActivity {

    EditText name1;
    TextView show;
    Button dlt;
    Editable value;
    String value1;
    String d1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        name1=(EditText)findViewById(R.id.name1);
        show=findViewById(R.id.show);
        dlt=findViewById(R.id.dlt);

        value=name1.getText();
        d1=getIntent().getStringExtra("val");
        value1=String.valueOf(d1);

        dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Details").child(d1);
                DatabaseReference myRef1 = myRef.child(String.valueOf(value));


                myRef1.removeValue();
                Toast.makeText(delete.this,"Data Deleted Successfully",Toast.LENGTH_SHORT).show();

            }
        });
    }
}