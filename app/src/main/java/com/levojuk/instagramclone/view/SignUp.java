package com.levojuk.instagramclone.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.levojuk.instagramclone.R;
import com.levojuk.instagramclone.databinding.ActivitySignUpBinding;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    String email,username, password;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view =  binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user =mAuth.getCurrentUser();

    }
    public void signUp(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        username = binding.usernameSignUpText.getText().toString();
        email = binding.emailSignUpText.getText().toString();
        password = binding.passwordSignUpText.getText().toString();

        HashMap<String,Object> postData= new HashMap<>();
        postData.put("useremail",email);
        postData.put("username",username);
        postData.put("date", FieldValue.serverTimestamp());

        firebaseFirestore.collection("/Username").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Intent intent = new Intent(SignUp.this,FeedActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
        if (email.equals("")||password.equals("")){
            Toast.makeText(this,"Enter email and password",Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
                Intent intent = new Intent(SignUp.this,FeedActivity.class);
                startActivity(intent);
                finish();

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUp.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
