package com.computerlicense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener{

    private TextView TextView_main_content, TextView_main_content_info;
    private ImageButton ImageButton_next, ImageButton_like, ImageButton_before, ImageButton_liked_content, ImageButton_setting;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private long backKeyPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView_main_content=findViewById(R.id.TextView_main_content);
        TextView_main_content_info=findViewById(R.id.TextView_main_content_info);
        ImageButton_next=findViewById(R.id.ImageButton_next);
        ImageButton_before=findViewById(R.id.ImageButton_before);
        ImageButton_like=findViewById(R.id.ImageButton_like);
        ImageButton_liked_content=findViewById(R.id.ImageButton_liked_contents);
        ImageButton_setting=findViewById(R.id.ImageButton_setting);

        dbHelper=new DBHelper(this);
        db=dbHelper.getReadableDatabase();

        SharedPreferences sharedPreferences=getSharedPreferences("IsFirst", MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean("isFirst", false);
        if(!isFirst){ //최초 실행시 true 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();

            dbHelper.loadContent(db);
        }

        final Cursor cursor=dbHelper.loadSQLiteDBCursor();
        try{
            cursor.moveToFirst();
            setContent(cursor.getInt(1), cursor.getString(2));
        }catch(Exception e){
            e.printStackTrace();
        }

        ImageButton_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ImageButton_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.super.onBackPressed();
            }
        });
    }

    //    두 번 누르면 앱 종료
    @Override
    public void onBackPressed() {
        Toast toast=Toast.makeText(MainActivity.this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast.show();
            return;
        }
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            finish();
            toast.cancel();
        }
    }

    public void setContent(int info, String content){
        String temp="";
        if(info==1) temp="1. 컴퓨터 일반";
        else if(info==2) temp="2. 스프레드시트 일반";
        else if(info==3) temp="3. 데이터베이스 일반";
        TextView_main_content_info.setText(temp);
        TextView_main_content.setText(content);
    }
}