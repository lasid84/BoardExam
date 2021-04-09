package com.example.boardexam2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String mEmail, mNickname;
    androidx.appcompat.widget.Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_btnBoard).setOnClickListener(this);

        mEmail = FirebaseID.useremail;
        mNickname = FirebaseID.nickname;
        Toast.makeText(this, mNickname + "님 반갑습니다.(" + mEmail + ")" , Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btnBoard :
                Intent intent = new Intent(MainActivity.this, BoardActivity.class);
                startActivity(intent);
                break;
//            case R.id.item_btn_delete:
//                break;
        }
    }

    @Override
    public void onBackPressed() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        LoginActivity.signOut();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_rightbtn, menu);

        int positionOfMenuItem = 0; // or whatever...
        MenuItem item = menu.getItem(positionOfMenuItem).getSubMenu().getItem(0);
        SpannableString s = new SpannableString(mNickname + "(" + mEmail + ")");
        s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
        item.setTitle(s);

        //menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.), getResources().getString(R.string.action_profile)));

        return true;
    }

//    private CharSequence menuIconWithText(Drawable r, String title) {
//
//        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
//        SpannableString sb = new SpannableString("    " + title);
//        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        return sb;
//    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
////            case R.id.first:
////                Toast.makeText(this, "1111",Toast.LENGTH_SHORT).show();
////                break;
            case R.id.toolbar_item_logout:
                LoginActivity.signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
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