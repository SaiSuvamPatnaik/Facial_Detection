import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.facialdetection.MainActivity;
import com.example.facialdetection.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    public EditText mail,pass;
    Button signup,back;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth= FirebaseAuth.getInstance();
        mail=findViewById(R.id.mail);
        pass=findViewById(R.id.pass);
        signup=findViewById(R.id.signup);
        back=findViewById(R.id.back);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mail.getText().toString();
                String passw = pass.getText().toString();
                if(email.isEmpty()){
                    mail.setError("Enter Mail-Id");
                    mail.requestFocus();
                    return;
                }
                if (!email.matches(emailPattern)){
                    mail.setError("Enter Valid Mail-Id");
                    mail.requestFocus();
                    return;
                }
                if(passw.isEmpty()){
                    pass.setError("Enter a Password");
                    pass.requestFocus();
                    return;
                }
                if(passw.length()<8){
                    pass.setError("Min 8 Characters Required");
                    pass.requestFocus();
                    return;
                }

                mFirebaseAuth.createUserWithEmailAndPassword(email,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(signup.this,"Failed !!! Connect To Internet",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(signup.this,"Registered",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signup.this,MainActivity.class);
                            startActivity(intent);
                            finish();


                        }
                    }
                });
            }
        });
    }
}