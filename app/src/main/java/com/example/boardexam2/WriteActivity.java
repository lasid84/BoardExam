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
import com.google.firebase.firestore.SetOptions;

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

    private String mId;

    public String mTest = "";

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
        try {
            Intent intent = getIntent(); //이전 액티비티에서 수신할 데이터를 받기위해 선언
            String id = intent.getStringExtra(FirebaseID.documentId);
            mId = id;
            String title = intent.getStringExtra(FirebaseID.title);
            String type = NullCheck(intent.getStringExtra(FirebaseID.type));
            String contents = intent.getStringExtra(FirebaseID.contents);
            String name = intent.getStringExtra(FirebaseID.name);
            String email = intent.getStringExtra(FirebaseID.email);

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
                if (type != " " && !type.equals("null"))
                    mWriteType.setSelection(Integer.parseInt(type));

                if (email.equals(FirebaseID.useremail)) {
                    mWriteBtnUpload.setVisibility(View.INVISIBLE);
                    mWriteBtnUpdate.setVisibility(View.VISIBLE);
                    mWriteBtnDelete.setVisibility(View.VISIBLE);
                } else {
                    mWriteBtnUpload.setVisibility(View.INVISIBLE);
                    mWriteBtnUpdate.setVisibility(View.INVISIBLE);
                    mWriteBtnDelete.setVisibility(View.INVISIBLE);
                }
            } else {
                mWriteBtnUpload.setVisibility(View.VISIBLE);
                mWriteBtnUpdate.setVisibility(View.INVISIBLE);
                mWriteBtnDelete.setVisibility(View.INVISIBLE);
                mWriteType.setSelection(0);
            }
        }
        catch (Exception e) {
            Toast.makeText(WriteActivity.this, "type : " + mTest + "//////////////////////"  + e.toString(), Toast.LENGTH_SHORT).show();
        }

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
        mId = mStore.collection(FirebaseID.board).document().getId();

//        mId = mStore.collection(FirebaseID.board).document()

        Map<String, Object> post = new HashMap<>();
        post.put(FirebaseID.documentId, mId);

        post.put(FirebaseID.title, mWriteTitleText.getText().toString());

        CommonCode cc = new CommonCode(FirebaseID.board, FirebaseID.admin);
        Object typekey = getKeyFromValue(cc.typeMap, mWriteType.getSelectedItem().toString());
        post.put(FirebaseID.type, typekey.toString());
        post.put(FirebaseID.contents, mWriteContentsText.getText().toString());
        post.put(FirebaseID.name, mWriteNameText.getText().toString());
        post.put(FirebaseID.email, FirebaseID.useremail);
//        FirebaseID.last_createdate = new Timestamp(new Date().getTime());
        post.put(FirebaseID.createdate, FieldValue.serverTimestamp());
        post.put(FirebaseID.useflag, "Y");

        mStore.collection(FirebaseID.board).document(mId).set(post)
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
        mStore.collection(FirebaseID.board).document(mId).update(FirebaseID.useflag, "N")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(com.example.boardexam2.WriteActivity.this, "수정 성공!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.example.boardexam2.WriteActivity.this, "수젇 실패!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UpdatePost() {

        Map<String, Object> post = new HashMap<>();
        post.put(FirebaseID.title, mWriteTitleText.getText().toString());

        CommonCode cc = new CommonCode(FirebaseID.board, FirebaseID.admin);
        Object key = getKeyFromValue(cc.typeMap, mWriteType.getSelectedItem().toString());
        post.put(FirebaseID.type, key.toString());
        post.put(FirebaseID.contents, mWriteContentsText.getText().toString());
        post.put(FirebaseID.name, mWriteNameText.getText().toString());
        post.put(FirebaseID.email, FirebaseID.useremail);
//        FirebaseID.last_createdate = new Timestamp(new Date().getTime());
        post.put(FirebaseID.createdate, FieldValue.serverTimestamp());

        mStore.collection(FirebaseID.board).document(mId).update(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(com.example.boardexam2.WriteActivity.this, "수정 성공!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.example.boardexam2.WriteActivity.this, "수젇 실패!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String NullCheck (String val) {
        if (val == null || val.equals("null"))
            return " ";
        else
            return val;
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

}