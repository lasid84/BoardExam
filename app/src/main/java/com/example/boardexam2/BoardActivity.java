package com.example.boardexam2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.boardexam2.models.Board;
import com.example.boardexam2.adapters.BoardAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener, BoardAdapter.OnListItemLongSelectedInterface, BoardAdapter.OnListItemSelectedInterface {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private RecyclerView mMainRecyclerView;
    private BoardAdapter mAdapter;
    private List<Board> mBoardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMainRecyclerView = findViewById(R.id.main_recycler_view);
        findViewById(R.id.main_write_button).setOnClickListener(this);



    }

    @Override
    protected void onStart() {
        super.onStart();

        mBoardList = new ArrayList<>();
        mStore.collection(FirebaseID.board)
                .whereEqualTo(FirebaseID.useflag, "Y")
                .orderBy(FirebaseID.createdate, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            mBoardList.clear();
                            //for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                            for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = snap.getData();
                                String key = snap.getId();
                                String documentid = key;
                                String title = String.valueOf(shot.get(FirebaseID.title));
                                String type = String.valueOf(shot.get(FirebaseID.type));
                                String contents = String.valueOf(shot.get(FirebaseID.contents));
                                String name = String.valueOf(shot.get(FirebaseID.name));
                                String email = String.valueOf(shot.get(FirebaseID.email));
//                                Date createdate = ((Timestamp)shot.get(FirebaseID.createdate)).toDate();
//                                Date updatedate = ((Timestamp)shot.get(FirebaseID.updatedate)).toDate();
                                Timestamp createtime = (Timestamp)shot.get(FirebaseID.createdate);
                                Timestamp updatetime = (Timestamp)shot.get(FirebaseID.updatedate);
                                Date createdate, updatedate = null;
                                if (createtime == null) {
                                    Timestamp ts = new Timestamp(new Date());
                                    createdate = ts.toDate();
                                } else {
                                    createdate = createtime.toDate();
                                    if (updatetime != null) {
                                        updatedate = updatetime.toDate();
                                    }
                                }
                                Board data = new Board(key, documentid, title, type, contents, name, email, createdate, updatedate, FirebaseID.admin);

                                mBoardList.add(data);
                            }
                        }
                        mAdapter = new BoardAdapter(mBoardList,BoardActivity.this , BoardActivity.this);
                        mMainRecyclerView.setAdapter(mAdapter);

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_write_button :
                startActivity(new Intent(this, WriteActivity.class));
                break;
//            case R.id.item_btn_delete:
//                break;
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


    @Override
    public void onItemLongSelected(View v, int position) {
        Toast.makeText(this, position + " long clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(View v, int position) {
        //BoardAdapter.BoardViewHolder viewHolder = (BoardAdapter.BoardViewHolder)mMainRecyclerView.findViewHolderForAdapterPosition(position);
        try {
            Board data = mBoardList.get(position);

            Intent intent = new Intent(this, WriteActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseID.documentId, data.getId());
            bundle.putString(FirebaseID.title, data.getTitle());
            bundle.putString(FirebaseID.type, data.getType());
            bundle.putString(FirebaseID.contents, data.getContents());
            bundle.putString(FirebaseID.name, data.getName());
            bundle.putString(FirebaseID.email, data.getEmail());
            ;
            bundle.putString(FirebaseID.createdate, data.getCreatedate().toString());
            if (data.getUpdatedate() != null)
                bundle.putString(FirebaseID.updatedate, data.getUpdatedate().toString());

            intent.putExtras(bundle);

            startActivity(intent);
        }
        catch (Exception e) {
            Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
        }

//        Toast.makeText(this,position + "/" + data.getName() +"/" + data.getContents(), Toast.LENGTH_SHORT).show();
    }
}