package com.computerlicense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;

public class DBHelper extends SQLiteOpenHelper {

    private final String ID="id";
    private static final String DATABASE_NAME="Content.db";
    private final String TABLE_NAME="Table_content";
    private final String INFO="info";
    private final String CONTENT="content";
    private final String ISLIKE="islike";
    private final String KEYWORD="keyword";
    public static final int DATABASE_VERSION=1;

    private final String createQuery="CREATE TABLE IF NOT EXISTS "+TABLE_NAME
            +"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +INFO+" INTEGER, "
            +CONTENT+" TEXT, "
            +ISLIKE+" INTEGER, "
            +KEYWORD+" TEXT)";

//생성자
    public DBHelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    데이터 베이스 생성
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createQuery);
    }

//    데이터베이스 업데이트
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor loadSQLiteDBCursor(boolean is1, boolean is2, boolean is3){
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        String selectQuery="";

        if(is1==true){
            if(is2==true){
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+" ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=3 ORDER BY RANDOM() LIMIT 1";
            }
            else{
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=2 ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =1 ORDER BY RANDOM() LIMIT 1";
            }
        }
        else{
            if(is2==true){
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=1 ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =2 ORDER BY RANDOM() LIMIT 1";
            }
            else{
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =3 ORDER BY RANDOM() LIMIT 1";
//                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+" ORDER BY RANDOM() LIMIT 1";
            }
        }

        Cursor cursor=null;

        try{
            cursor=db.rawQuery(selectQuery, null);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }

    public Cursor loadSQLiteDBCursor_liked(boolean is1, boolean is2, boolean is3){
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        String selectQuery="";

        if(is1==true){
            if(is2==true){
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+" WHERE "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=3 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
            }
            else{
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=2 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =1 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
            }
        }
        else{
            if(is2==true){
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" !=1 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =2 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
            }
            else{
                if(is3==true) selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+ " WHERE "+INFO +" =3 AND "+ISLIKE+" =1 ORDER BY RANDOM() LIMIT 1";
//                else selectQuery="SELECT "+ID+", "+INFO+", "+CONTENT+", "+ISLIKE+", "+KEYWORD+" FROM "+TABLE_NAME+" WHERE "+ISLIKE+ " =1 ORDER BY RANDOM() LIMIT 1";
            }
        }

        Cursor cursor=null;

        try{
            cursor=db.rawQuery(selectQuery, null);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }

    public Cursor getCountLikeCursor(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        String countQuery="SELECT * FROM "+ TABLE_NAME+" WHERE ISLIKE=1";
        Cursor cursor=null;
        try{
            cursor=db.rawQuery(countQuery, null);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }

//데이터베이스에 값 삽입
    public void insertContent(int info, String content, int islike, String keyword){
        ContentValues contentValues=new ContentValues();
        SQLiteDatabase db=getWritableDatabase(); // 데이터베이스를 수정할 때는 getWritableDatabase(), 데이터를 읽을 떄는 getReadableDatabase()를 씁니다
        contentValues.put(INFO, info);
        contentValues.put(CONTENT, content);
        contentValues.put(ISLIKE, islike);
        contentValues.put(KEYWORD, keyword);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void loadContent(SQLiteDatabase db){
        insertContent(1, "플러그 앤 플레이: 한글 Windows에 하드웨어 장치를 추가할 때 운영체제가 이를 자동으로 인식하여 설치 및 환경설정을 용이하게 해주는 기능", 0, "플러그 앤 플레이");
        insertContent(1, "단축키 F3: 탐색기의 '검색 상자' 선택하기", 0, "F3");
        insertContent(1, "단축키 Alt+Esc: 실행 중인 프로그램 사이에 작업 전환을 한다.\n한 번씩 누를 떄마다 열려 있는 프로그램의 창이 바로 바뀐다.", 0, "Alt+Esc");
        insertContent(2, "현재 워크시트의 왼쪽에 새로운 시트를 삽입할 때에는 Shift+F11을 누른다.", 0, "Shift+F11");
        insertContent(3, "데이터베이스 방식은 데이터의 중복을 최소화할 수 있다.", 0, null);
    }

    public void updateLike(SQLiteDatabase db, int id, int islike){
        ContentValues contentValues=new ContentValues();
        if(islike==0) contentValues.put(ISLIKE, 1);
        else if(islike==1) contentValues.put(ISLIKE, 0);
        db.update(TABLE_NAME, contentValues, ID+"="+id, null);
    }

    public SpannableString highlightKeyWord(String content, String keyword){
        if(keyword==null){
            return SpannableString.valueOf(content);
        }
        else{
            SpannableString str = new SpannableString(content);
            str.setSpan(new BackgroundColorSpan(Color.YELLOW), content.indexOf(keyword), content.indexOf(keyword)+keyword.length(), 0);
            return str;
        }
    }

}
