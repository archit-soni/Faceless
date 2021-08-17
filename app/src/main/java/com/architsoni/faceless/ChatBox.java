package com.architsoni.faceless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Nullable;

public class ChatBox extends AppCompatActivity {

    TextView receivertv;
    ImageButton sendBtn;
    ArrayList<String> sender = new ArrayList<>();
    ArrayList<String> body = new ArrayList<>();
    ListView list;
    EditText textMessageet;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        MessageAdapter adapter=new MessageAdapter(this, sender, body);
        list=(ListView)findViewById(R.id.texts);
        list.setAdapter((ListAdapter) adapter);
        receivertv = findViewById(R.id.receiver);
        sendBtn = findViewById(R.id.sendBtn);
        textMessageet = findViewById(R.id.textMessage);
        String rname = getIntent().getStringExtra("rname");
        String uname = getIntent().getStringExtra("uname");
        DocumentReference docRef = db.collection("Chats").document(uname+"+"+rname);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Chat chat = document.toObject(Chat.class);
                        ArrayList<Message> msges = chat.getMsges();
                        for(Message i:msges){
                            sender.add(i.getSender());
                            body.add(i.getText());
                            adapter.notifyDataSetChanged();
                        }
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
        receivertv.setText(rname);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = textMessageet.    getText().toString();
                Date date = new Date();
                textMessageet.setText("");
                Message msg = new Message(text, uname, rname, date.getTime());
                db.collection("Chats")
                        .document(uname+"+"+rname)
                        .update("msges", FieldValue.arrayUnion(msg));
                db.collection("Chats")
                        .document(rname+"+"+uname)
                        .update("msges", FieldValue.arrayUnion(msg));
            }
        });
        final DocumentReference dRef = db.collection("Chats").document(uname+"+"+rname);
        dRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("TAG", "Current data: " + snapshot.get("msges"));
                    Chat chat = snapshot.toObject(Chat.class);
                    ArrayList<Message> msges = chat.getMsges();
                    try {
                        sender.add(msges.get(msges.size() - 1).getSender());
                        body.add(msges.get(msges.size() - 1).getText());
                        adapter.notifyDataSetChanged();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    Log.d("TAG", "Current data: null");
                }
            }
        });

    }
}


