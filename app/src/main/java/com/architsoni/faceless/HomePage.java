package com.architsoni.faceless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private User userData[];
    private MyArrayAdapter arrayAdapter;
    private int i;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageButton message;
    ImageButton profile;
    ListView listView;
    List rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        rowItems = new ArrayList<User>();
        String uname = getIntent().getStringExtra("uname");
        message = findViewById(R.id.messageBtn);
        profile = findViewById(R.id.profileBtn);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Messaging.class);
                intent.putExtra("uname", uname);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task<QuerySnapshot> docs = db.collection("Users")
                        .whereEqualTo("username", uname)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Intent intent = new Intent(view.getContext(), MakeProfile.class);
                                    intent.putExtra("Name", document.get("username").toString());
                                    intent.putStringArrayListExtra("liked", (ArrayList<String>) document.get("liked"));
                                    intent.putStringArrayListExtra("likedBy", (ArrayList<String>) document.get("likedBy"));
                                    intent.putStringArrayListExtra("dislikedBy", (ArrayList<String>) document.get("dislikedBy"));
                                    intent.putStringArrayListExtra("disliked", (ArrayList<String>) document.get("disliked"));
                                    intent.putExtra("pass", document.get("password").toString());
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });

        Task<QuerySnapshot> docs = db.collection("Users")
                .whereNotEqualTo("username", uname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
                            User user = document.toObject(User.class);
                            rowItems.add(user);
                        }
                    }
                });

        User user = new User("whatever", "password");
        user.setFullName("Swipe Right to like!");
        rowItems.add(user);
        user = new User("whatever2", "pass");
        user.setFullName("Swipe Left to dislike.");
        rowItems.add(user);

        arrayAdapter = new MyArrayAdapter(this, R.layout.item, rowItems );

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView)findViewById(R.id.frame);


        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            String lastRemoved;
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                User last = (User) rowItems.remove(0);
                lastRemoved = last.getUsername();
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

                DocumentReference docRef = db.collection("Users").document(lastRemoved);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                DocumentReference doc = db.collection("Users").document(uname);
                                doc.update("disliked", FieldValue.arrayUnion(lastRemoved));
                                DocumentReference doc2 = db.collection("Users").document(lastRemoved);
                                doc2.update("dislikedBy", FieldValue.arrayUnion(uname));
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
                Log.d("right here", "Left on");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Log.d("right here", "Right on");
                DocumentReference docRef = db.collection("Users").document(lastRemoved);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                DocumentReference doc = db.collection("Users").document(uname);
                                doc.update("liked", FieldValue.arrayUnion(lastRemoved));
                                DocumentReference doc2 = db.collection("Users").document(lastRemoved);
                                doc2.update("likedBy", FieldValue.arrayUnion(uname));
                                doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Chat chat = new Chat(uname, lastRemoved, new ArrayList<Message>());
                                                Chat chat2 = new Chat(lastRemoved, uname, new ArrayList<Message>());
                                                ArrayList<String> match = (ArrayList<String>) document.get("likedBy");
                                                Log.d("tag", "msg"+match);
                                                try{
                                                    for(String i: match){
                                                        if(i.equals(lastRemoved)) {
                                                            Toast.makeText(getApplicationContext(),"New Match! Check Inbox!",Toast.LENGTH_SHORT).show();
                                                            db.collection("Chats")
                                                                    .document(uname + "+" + lastRemoved)
                                                                    .set(chat);
                                                            db.collection("Chats")
                                                                    .document(lastRemoved + "+" + uname)
                                                                    .set(chat2);
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    Log.d("Catch", "No likedBy"+e.toString());
                                                }
                                            } else {
                                                Log.d("TAG", "No such document");
                                            }
                                        } else {
                                            Log.d("TAG", "get failed with ", task.getException());
                                        }
                                    }
                                });

                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(HomePage.this, "Click!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}