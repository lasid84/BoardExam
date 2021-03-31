package com.example.boardexam2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.boardexam2.models.CommonCode;
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
    private Button mWriteBtnUpload;
    private Button mWriteBtnDelete;
    private Button mWriteBtnUpdate;
    private Spinner mWriteType;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWriteTitleText = findViewById(R.id.write_title_text);
        mWriteContentsText = findViewById(R.id.write_contents_text);
        mWriteNameText = findViewById(R.id.write_name_text);
        mWriteBtnUpload = findViewById(R.id.write_write_text);
        mWriteBtnDelete = findViewById(R.id.write_delete_text);
        mWriteBtnUpdate = findViewById(R.id.write_update_text);
        mWriteType = findViewById(R.id.write_type);


        mWriteNameText.setText(FirebaseID.nickname);
        mWriteNameText.setEnabled(false);

        mWriteBtnUpload.setOnClickListener(this);
        mWriteBtnUpdate.setOnClickListener(this);
        mWriteBtnDelete.setOnClickListener(this);

        setContents();

    }

    private void setContents() {
        Intent intent = getIntent(); //이전 액티비티에서 수신할 데이터를 받기위해 선언
        String id= intent.getStringExtra(FirebaseID.documentId);
        String title = intent.getStringExtra(FirebaseID.title);
        String type = intent.getStringExtra(FirebaseID.type);
        String contents= intent.getStringExtra(FirebaseID.contents);
        String name= intent.getStringExtra(FirebaseID.name);
        String email= intent.getStringExtra(FirebaseID.email);

        mWriteBtnUpload.setVisibility(View.VISIBLE);
        mWriteBtnUpdate.setVisibility(View.INVISIBLE);
        mWriteBtnDelete.setVisibility(View.INVISIBLE);

        CommonCode cc = new CommonCode(FirebaseID.board, FirebaseID.admin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(WriteActivity.this, R.layout.spinner_spinner1_normal, cc.typeArr);

        adapter.setDropDownViewResource(R.layout.spinner_spinner1_normal);
        mWriteType.setAdapter(adapter);

        if (id != null) {
            this.mWriteTitleText.setText(title);
            this.mWriteContentsText.setText(contents);
            this.mWriteNameText.setText(name);
            mWriteType.setSelection(Integer.parseInt(type));

            if (email.equals(FirebaseID.useremail)) {
                mWriteBtnUpload.setVisibility(View.INVISIBLE);
                mWriteBtnUpdate.setVisibility(View.VISIBLE);
                mWriteBtnDelete.setVisibility(View.VISIBLE);
            } else {
                mWriteBtnUpload.setVisibility(View.VISIBLE);
                mWriteBtnUpdate.setVisibility(View.INVISIBLE);
                mWriteBtnDelete.setVisibility(View.INVISIBLE);
            }
        }

        mWriteType.setSelection(0);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.write_write_text:
                WritePost();
                break;
            case R.id.write_update_text:
                UpdatePost();
                break;
            case R.id.write_delete_text:
                DeletePost();
                break;
        }
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

    private void WritePost() {

        id = mStore.collection(FirebaseID.board).document().getId();
        Map<String, Object> post = new HashMap<>();
        post.put(FirebaseID.documentId, id);

        post.put(FirebaseID.title, mWriteTitleText.getText().toString());
        post.put(FirebaseID.contents, mWriteContentsText.getText().toString());
        post.put(FirebaseID.name, mWriteNameText.getText().toString());
        post.put(FirebaseID.email, FirebaseID.useremail);
//        FirebaseID.last_createdate = new Timestamp(new Date().getTime());
        post.put(FirebaseID.createdate, FieldValue.serverTimestamp());

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

    public void DeletePost() {

    }

    public void UpdatePost() {

    }

}