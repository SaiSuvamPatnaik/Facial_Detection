package com.example.facialdetection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class face extends AppCompatActivity {

    TextView acc,name,age,gender;
    ImageView img;
    String d1,val;
    Button add,show,dlt;
    static final int PICK_IMAGE=1;
    Uri imageuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);


        img= findViewById(R.id.img);
        name= findViewById(R.id.name);
        age= findViewById(R.id.age);
        gender= findViewById(R.id.gender);
        acc= findViewById(R.id.acc);
        add= findViewById(R.id.add);
        show= findViewById(R.id.show);
        dlt=findViewById(R.id.dlt);

        d1=getIntent().getStringExtra("accdetails");
        acc.setText(d1);
        val = acc.getText().toString().replaceAll("[@.]","");

        dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(face.this,delete.class);
                intent.putExtra("val",val);
                startActivity(intent);
            }
        });




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String data1=name.getText().toString();
                String Name = data1;
                String data2=age.getText().toString();
                String Age = data2;
                String data3=gender.getText().toString();
                String Gender = data3;

                dataholder obj = new dataholder(Name,Age,Gender);


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Details");

                myRef.child((val)).child(Name).setValue(obj);
                Toast.makeText(face.this,"Data Inserted Successfully",Toast.LENGTH_SHORT).show();


            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(face.this,details.class);
                intent.putExtra("val",val);
                startActivity(intent);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"Select a Picture"),PICK_IMAGE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageuri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}