package com.example.boardexam2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    androidx.appcompat.widget.Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_btnBoard).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);

        //myToolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_rightbtn, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.first:
//                Toast.makeText(this, "1111",Toast.LENGTH_SHORT).show();
//                break;
            case R.id.second:
                Toast.makeText(this, "2222",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    public void onPopUpClick(MenuItem item) {
//        Button button;
//        button = findViewById(R.id.menu_main);
//
//                PopupMenu popup = new PopupMenu(this , button);
//                MenuInflater inf = popup.getMenuInflater();
//                inf.inflate(R.layout.popup_logininfo, popup.getMenu()); // 버튼 눌렀을 때 띄울 팝업 형태 정해주기
//                popup.show();

        Toast.makeText(this, "onPopUpClick 여기 호출되네",Toast.LENGTH_SHORT).show();

    }
}