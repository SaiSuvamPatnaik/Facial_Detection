package com.example.facialdetection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class face extends AppCompatActivity {


    private FirebaseStorage storage=FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    StorageReference riversRef = storageReference.child("Images");

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Details");

    TextView acc,name,age,gender,out;
    ImageView img;
    String d1,val,data4,val1;
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
        out=findViewById(R.id.out);

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
                final String Name = data1;
                String data2=age.getText().toString();
                final String Age = data2;
                String data3=gender.getText().toString();
                final String Gender = data3;






                final ProgressDialog pd = new ProgressDialog(face.this);
                pd.setTitle("Uploading Image...");
                pd.show();

                riversRef.putFile(imageuri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                pd.dismiss();
                                Task<Uri> url = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        data4= task.getResult().toString();
                                        String Img = data4;

                                        final dataholder obj = new dataholder(Name,Age,Gender,Img);

                                        myRef.child(val).child(Name).setValue(obj);






                                    }
                                });





                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                                Toast.makeText(face.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progresspercent=(100.00*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        pd.setMessage("Progress: "+(int)progresspercent+"%");
                    }
                });










            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(face.this,details.class);

                intent.putExtra("val",val);
                intent.putExtra("val1",data4);

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