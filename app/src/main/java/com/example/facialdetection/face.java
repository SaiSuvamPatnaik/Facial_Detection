package com.example.facialdetection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
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
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

public class face extends AppCompatActivity {


    private FirebaseStorage storage=FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();

    FirebaseAuth mFirebaseAuth;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Details");

    TextView acc,name,age,gender,out;
    ImageView img;
    String d1,val,data4,val1;
    Button add,show,dlt;
    static final int PICK_IMAGE=1;
    Uri imageuri,uri;
    private Object Tag;
    int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);

        mFirebaseAuth=FirebaseAuth.getInstance();
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

                StorageReference riversRef = storageReference.child("Images").child(mFirebaseAuth.getCurrentUser().getUid());
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
                CropImage.startPickImageActivity(face.this);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                uri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                startcrop(imageuri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageuri=result.getUri();
                img.setImageURI(imageuri);
                try {
                    getBitmapFromUri(imageuri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    private void startcrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getBitmapFromUri(Uri imageuri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(imageuri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        showImage(fileDescriptor);


    }

    private void showImage(FileDescriptor fileDescriptor) {
        ImageView imageView = findViewById(R.id.img);
        BitmapFactory.Options options = new BitmapFactory.Options();


        Bitmap myBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        Paint myRectPaint = new Paint();
        myRectPaint.setStrokeWidth(15);
        myRectPaint.setColor(Color.RED);
        myRectPaint.setStyle(Paint.Style.STROKE);

        Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(),myBitmap.getHeight(),Bitmap.Config.RGB_565);
        Canvas tempcanvas = new Canvas(tempBitmap);
        tempcanvas.drawBitmap(myBitmap,0,0,null);


        FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).build();
        if (!faceDetector.isOperational()){
            c=1;
        }



        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        SparseArray<Face> faces = faceDetector.detect(frame);

        for (int i=0;i<faces.size();i++){
            Face thisface = faces.valueAt(i);
            float x1 = thisface.getPosition().x;
            float y1 = thisface.getPosition().y;
            float x2 = x1 + thisface.getWidth();
            float y2 = y1 + thisface.getHeight();
            tempcanvas.drawRoundRect(new RectF(x1,y1,x2,y2),2,2,myRectPaint);




        }
        imageView.setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));
    }
}