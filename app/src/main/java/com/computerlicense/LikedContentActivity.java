package com.computerlicense;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LikedContentActivity extends AppCompatActivity implements OnBackPressedListener{

    private TextView TextView_main_content, TextView_main_content_info;
    private ImageButton ImageButton_next, ImageButton_like, ImageButton_before, ImageButton_liked_home, ImageButton_setting;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private long backKeyPressedTime;
    private int isLike;
    private String[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_content);

        this.getSupportActionBar().hide();

        TextView_main_content=findViewById(R.id.TextView_liked_content);
        TextView_main_content_info=findViewById(R.id.TextView_liked_content_info);
        ImageButton_next=findViewById(R.id.ImageButton_liked_next);
        ImageButton_before=findViewById(R.id.ImageButton_liked_before);
        ImageButton_like=findViewById(R.id.ImageButton_liked_like);
        ImageButton_liked_home=findViewById(R.id.ImageButton_liked_home);
        ImageButton_setting=findViewById(R.id.ImageButton_liked_setting);

        dbHelper=new DBHelper(this);
        db=dbHelper.getReadableDatabase();

        categories=new String[]{"1. 컴퓨터 일반", "2. 스프레드시트 일반", "3. 데이터베이스 일반"};

        SharedPreferences sf=getSharedPreferences("subject", MODE_PRIVATE);
        boolean isChecked1 = sf.getBoolean("isChecked1", true);
        boolean isChecked2 = sf.getBoolean("isChecked2", true);
        boolean isChecked3 = sf.getBoolean("isChecked3", true);

        final Cursor cursor=dbHelper.loadSQLiteDBCursor_liked(isChecked1, isChecked2, isChecked3);
        try{
            cursor.moveToFirst();
            setContent(cursor.getInt(1), cursor.getString(2), cursor.getInt(3));
        }catch(Exception e){
            e.printStackTrace();
        }

        isLike=cursor.getInt(3);

        ImageButton_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LikedContentActivity.this, LikedContentActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0); //애니메이션 제거
            }
        });
        ImageButton_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LikedContentActivity.super.onBackPressed();
            }
        });

        ImageButton_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("button", "별 버튼이 클릭됨");
                db=dbHelper.getWritableDatabase();
                dbHelper.updateLike(db, cursor.getInt(0), isLike);
                if(isLike==0) {
                    ImageButton_like.setImageResource(R.drawable.full_star);
                    isLike=1;
                }
                else if(isLike==1) {
                    ImageButton_like.setImageResource(R.drawable.empty_star);
                    isLike=0;
                }
            }
        });

        ImageButton_liked_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LikedContentActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0); //애니메이션 제거
            }
        });

        ImageButton_setting.setOnClickListener(new View.OnClickListener(){
            SharedPreferences sf=getSharedPreferences("subject", MODE_PRIVATE);
            boolean isChecked1, isChecked2, isChecked3;
            @Override
            public void onClick(View view) {
                isChecked1 = sf.getBoolean("isChecked1", true);
                isChecked2 = sf.getBoolean("isChecked2", true);
                isChecked3 = sf.getBoolean("isChecked3", true);
                AlertDialog.Builder builder = new AlertDialog.Builder(LikedContentActivity.this);
                LayoutInflater factory = LayoutInflater.from(LikedContentActivity.this);
                final View checkbox_view = factory.inflate(R.layout.subject_checkbox, null);
                final CheckBox checkBox1=checkbox_view.findViewById(R.id.checkbox_subject1);
                final CheckBox checkBox2=checkbox_view.findViewById(R.id.checkbox_subject2);
                final CheckBox checkBox3=checkbox_view.findViewById(R.id.checkbox_subject3);
                if(isChecked1) checkBox1.setChecked(true);
                else checkBox1.setChecked(false);
                if(isChecked2) checkBox2.setChecked(true);
                else checkBox2.setChecked(false);
                if(isChecked3) checkBox3.setChecked(true);
                else checkBox3.setChecked(false);

                checkBox1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        isChecked1=!isChecked1;
//                        if(isChecked1) checkBox1.setChecked(true);
//                        else checkBox1.setChecked(false);
                    }
                });

                checkBox2.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        isChecked2=!isChecked2;
//                        if(isChecked2) checkBox2.setChecked(true);
//                        else checkBox2.setChecked(false);
                    }
                });

                checkBox3.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        isChecked3=!isChecked3;
//                        if(isChecked3) checkBox3.setChecked(true);
//                        else checkBox3.setChecked(false);
                    }
                });

                builder.setTitle("설정")
//                        .setMultiChoiceItems(categories, null, new DialogInterface.OnMultiChoiceClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                                SharedPreferences sf=getSharedPreferences("subject", MODE_PRIVATE);
//
//                            }
//                        })
                        .setView(checkbox_view)
                        .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences.Editor editor=sf.edit();
                                if(!isChecked1&&!isChecked2&&!isChecked3){
                                    Toast.makeText(LikedContentActivity.this, "한 개 이상의 항목을 체크하셔야 합니다.", Toast.LENGTH_SHORT).show();
                                    editor.putBoolean("isChecked1", true);
                                    editor.putBoolean("isChecked2", true);
                                    editor.putBoolean("isChecked3", true);
                                }
                                else{
                                    editor.putBoolean("isChecked1", isChecked1);
                                    editor.putBoolean("isChecked2", isChecked2);
                                    editor.putBoolean("isChecked3", isChecked3);
                                    Toast.makeText(LikedContentActivity.this, "변경 사항이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                                editor.commit();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();

                                checkBox1.setChecked(sf.getBoolean("isChecked1", true));
                                checkBox2.setChecked(sf.getBoolean("isChecked2", true));
                                checkBox3.setChecked(sf.getBoolean("isChecked3", true));
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    //    두 번 누르면 앱 종료
    @Override
    public void onBackPressed() {
        Toast toast=Toast.makeText(LikedContentActivity.this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
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

    public void setContent(int info, String content, int islike){
        String temp="";
        if(info==1) temp="1. 컴퓨터 일반";
        else if(info==2) temp="2. 스프레드시트 일반";
        else if(info==3) temp="3. 데이터베이스 일반";
        TextView_main_content_info.setText(temp);
        TextView_main_content.setText(content);
        if(islike==0) ImageButton_like.setImageResource(R.drawable.empty_star);
        else if(islike==1) ImageButton_like.setImageResource(R.drawable.full_star);
    }
}