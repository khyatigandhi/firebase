package com.knoxpo.khyati.firebasedemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textTV = findViewById(R.id.tv_text);

        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        final CollectionReference todoCollectionRef = database.collection("todoitems");

        todoCollectionRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        StringBuilder builder = new StringBuilder();
                        for(int i=0;i<documents.size();i++){
                            String text = documents.get(i).getString("text");
                            Log.d(TAG, "Text = "+ text);
                            builder.append(text + "\n");
                        }
                        textTV.setText(builder.toString());
                    }
                });

        Button addBtn = findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("text","Study Android");
                map.put("isCompleted", false);
                todoCollectionRef
                        .add(map);

            }
        });

        Button updateButton = findViewById(R.id.btn_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> map = new HashMap<>();
                map.put("isCompleted", true);

                todoCollectionRef
                        .document("Tzhp1CKqpJKxWGzAeTGp")
                        .update(map); //<---- updates only the specific field
                //.set(map) //<---- set the entire document
            }
        });

        Button deleteButton = findViewById(R.id.btn_delete);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                todoCollectionRef
                        .document("zF8nKB9In0S741KMGkIu")
                        .delete();
            }
        });

    }
}
