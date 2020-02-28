package com.example.traileroffirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText n, q, p;
    Button b, r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n = findViewById(R.id.name);
        q = findViewById(R.id.qty);
        p = findViewById(R.id.price);
        b = findViewById(R.id.add);
        r = findViewById(R.id.retreive);
        b.setOnClickListener(this);
        r.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retreive:
                retrieveData();
                break;
            case R.id.add:
                addDataFirestore();
                break;
        }
    }

    public void retrieveData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "ykgvhtfchgftyg", Toast.LENGTH_SHORT).show();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Toast.makeText(MainActivity.this, "HELLOOOO", Toast.LENGTH_SHORT).show();
                        System.out.println(documentSnapshot.getData().toString());
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }

    public void addDataFirestore() {
        String name = n.getText().toString();
        String qty = q.getText().toString();
        String price = p.getText().toString();
        HashMap<String, String> order = new HashMap<>();
        order.put("name", name);
        order.put("qty", qty);
        order.put("price", price);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Orders").add(order)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
        });
    }
}
