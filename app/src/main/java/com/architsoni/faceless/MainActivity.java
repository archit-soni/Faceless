package com.architsoni.faceless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.Var;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    User user;
    Button loginBtn;
    EditText userText;
    private String id;
    EditText passText;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        userText = findViewById(R.id.userText);
        passText = findViewById(R.id.passText);
        loginBtn.setOnClickListener(view -> checkProfile());

    }

    private void checkProfile() {
        String name = userText.getText().toString();
        String pass = passText.getText().toString();
        Log.d("look here", "name "+name);
        user = new User(name, pass);
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int flag = 0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.get("username").equals(user.getUsername())) {
                                    flag = flag + 1;
                                    if (document.get("password").equals(user.getPassword())) {
                                        flag = flag + 1;
                                        login();
                                    }
                                }
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    if(flag==0){
                        makeProfile();
                        }
                    else if(flag==1){
                        Toast.makeText(getApplicationContext(),"Username already exists or Incorrect Password.", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
    }

    private void login() {
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("uname", user.getUsername());
        startActivity(intent);
    }

    private void makeProfile() {
        Intent intent = new Intent(this, MakeProfile.class);

// Add a new document with a generated ID
        db.collection("Users").document(user.getUsername()).set(user);
        intent.putExtra("Name", user.getUsername());
        intent.putExtra("pass", user.getPassword());
        startActivity(intent);
    }

}