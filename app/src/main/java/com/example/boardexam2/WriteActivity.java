package com.example.boardexam2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private EditText mWriteTitleText;
    private EditText mWriteContentsText;
    private EditText mWriteNameText;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWriteTitleText = findViewById(R.id.write_title_text);
        mWriteContentsText = findViewById(R.id.write_contents_text);
        mWriteNameText = findViewById(R.id.write_name_text);

        mWriteNameText.setText(FirebaseID.nickname);
        mWriteNameText.setEnabled(false);

        findViewById(R.id.write_upload_text).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        id = mStore.collection(FirebaseID.board).document().getId();
        Map<String, Object> post = new HashMap<>();
        post.put(FirebaseID.documentId, id);
        post.put(FirebaseID.title, mWriteTitleText.getText().toString());
        post.put(FirebaseID.contents, mWriteContentsText.getText().toString());
        post.put(FirebaseID.name, mWriteNameText.getText().toString());
        post.put(FirebaseID.timestamp, FieldValue.serverTimestamp());

//        mStore.collection("board").add(post)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(WriteActivity.this, "업로드 성공!", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(WriteActivity.this, "업로드 실패!", Toast.LENGTH_SHORT).show();
//            }
//        });

                mStore.collection(FirebaseID.board).document().set(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(com.example.boardexam2.WriteActivity.this, "업로드 성공!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(com.example.boardexam2.WriteActivity.this, "업로드 실패!", Toast.LENGTH_SHORT).show();
                            }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}