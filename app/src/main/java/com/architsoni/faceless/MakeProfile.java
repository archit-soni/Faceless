package com.architsoni.faceless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

public class MakeProfile extends AppCompatActivity {

    Button submit;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User user;
    EditText fullNameet;
    EditText ageet;
    EditText bioet;
    EditText uniet;
    EditText hobbyet;
    EditText drinket;
    EditText foodet;
    EditText relnet;
    EditText watchet;
    EditText listenet;
    EditText genderet;
    EditText locationet;
    EditText foreplayet;
    EditText kisset;
    EditText fantasyet;
    CheckBox wilder;
    String fullName;
    String location;
    String gender;
    String foreplay;
    String kiss;
    String fantasy;
    String age;
    String bio;
    String uni;
    String hobby;
    String drink;
    String food;
    String reln;
    String watch;
    String listen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_profile);
        String pass = getIntent().getStringExtra("pass");
        ArrayList<String> liked = getIntent().getStringArrayListExtra("liked");
        ArrayList<String> likedBy = getIntent().getStringArrayListExtra("likedBy");
        ArrayList<String> dislikedBy = getIntent().getStringArrayListExtra("dislikedBy");
        ArrayList<String> disliked = getIntent().getStringArrayListExtra("disliked");
        String name = getIntent().getStringExtra("Name");
        user = new User(name, pass);
        submit = findViewById(R.id.submitBtn);
        fullNameet = findViewById(R.id.name);
        ageet = findViewById(R.id.age);
        bioet = findViewById(R.id.bio);
        uniet = findViewById(R.id.uni);
        hobbyet = findViewById(R.id.hobby);
        drinket = findViewById(R.id.drink);
        foodet = findViewById(R.id.food);
        relnet = findViewById(R.id.reln);
        watchet = findViewById(R.id.watch);
        wilder = findViewById(R.id.checkBox);
        listenet = findViewById(R.id.listen);
        genderet = findViewById(R.id.gender);
        locationet = findViewById(R.id.location);
        fantasyet = findViewById(R.id.fantasy);
        kisset = findViewById(R.id.firstKiss);
        foreplayet = findViewById(R.id.foreplay);
        wilder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                fantasyet.setVisibility(View.VISIBLE);
                kisset.setVisibility(View.VISIBLE);
                foreplayet.setVisibility(View.VISIBLE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomePage.class);
                fullName = fullNameet.getText().toString();
                age = ageet.getText().toString();
                bio = bioet.getText().toString();
                uni = uniet.getText().toString();
                hobby = hobbyet.getText().toString();
                drink = drinket.getText().toString();
                food = foodet.getText().toString();
                reln = relnet.getText().toString();
                watch = watchet.getText().toString();
                listen = listenet.getText().toString();
                gender = genderet.getText().toString();
                kiss = kisset.getText().toString();
                location = locationet.getText().toString();
                foreplay = foreplayet.getText().toString();
                fantasy = fantasyet.getText().toString();
                int flag = verify();
                if(flag == 0) {
                    user.setBio(bio);
                    user.setDrink(drink);
                    user.setReln(reln);
                    user.setFullName(fullName);
                    user.setHobby(hobby);
                    user.setListen(listen);
                    user.setFood(food);
                    user.setWatch(watch);
                    user.setUni(uni);
                    user.setGender(gender);
                    user.setLocation(location);
                    user.setFantasy(fantasy);
                    user.setFirstKiss(kiss);
                    user.setForeplay(foreplay);
                    user.setLiked(liked);
                    user.setLikedBy(likedBy);
                    user.setDisliked(disliked);
                    user.setDislikedBy(dislikedBy);
                    db.collection("Users").document(name)
                            .set(user, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error writing document", e);
                                }
                            });
                    intent.putExtra("uname", user.getUsername());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"All fields must have atleast 4 characters",Toast.LENGTH_SHORT).show();
                }

            }

            private int verify() {
                int flag = 0;
                if(fullName.length()<4){
                    flag = flag + 1;
                    if(bio.length()<4){
                        flag = flag + 1;
                        if(gender.length()<4){
                            flag = flag + 1;
                            if(uni.length()<4){
                                flag = flag + 1;
                                if(food.length()<4){
                                    flag = flag + 1;
                                    if(hobby.length()<4){
                                        flag = flag + 1;
                                        if(drink.length()<4){
                                            flag = flag + 1;
                                            if(reln.length()<4){
                                                flag = flag + 1;
                                                if(listen.length()<4){
                                                    flag = flag + 1;
                                                    if(watch.length()<4){
                                                        flag = flag + 1;
                                                        if(location.length()<4){
                                                            flag = flag + 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return flag;
            }
        });
    }
}