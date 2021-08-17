package com.architsoni.faceless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Messaging extends AppCompatActivity {

    ListView list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> maintitle = new ArrayList<>();
    ArrayList<String> subtitle = new ArrayList<>();
    ArrayList<String> liked = new ArrayList<>();
    ArrayList<String> likedBy = new ArrayList<>();
    Set<String> set1 = new HashSet<>();
    Set<String> set2 = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        maintitle.add("Faceless App");
        subtitle.add("Welcome to Faceless! The app is in very early stages right now, for any queries contact archit2@ualberta.ca.");
        MyListAdapter adapter=new MyListAdapter(this, maintitle, subtitle);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter((ListAdapter) adapter);
        String uname = getIntent().getStringExtra("uname");
        DocumentReference doc = db.collection("Users")
                .document(uname);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        liked = (ArrayList<String>) document.get("liked");
                        likedBy = (ArrayList<String>) document.get("likedBy");
                        try {
                            for (String i : liked) {
                                set1.add(i);
                            }
                            for (String i : likedBy) {
                                set2.add(i);
                            }
                            set1.retainAll(set2);
                            for (String i : set1) {
                                maintitle.add(i);
                                subtitle.add(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        Log.d("TAG", "DocumentSnapshot data: " + set1);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
        Log.d("Tag", "msg"+liked+likedBy+maintitle+subtitle);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ChatBox.class);
                intent.putExtra("uname", uname);
                intent.putExtra("rname", maintitle.get(position));
                startActivity(intent);
            }
        });
    }
}
