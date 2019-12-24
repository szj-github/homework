package com.example.homework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "data.db";
    private static final String TBNAME = "questionTb";
    private SQLiteDatabase student_db;

    public DBhelper( Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
    }
    //构造


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.student_db = db;
        String create_question = "create table question_tab(_id integer primary key autoincrement,"+"content text,class text,TorF integer)";
        String create_tb = "create table Stutent_tab(_id integer primary key autoincrement,"+" Date text,Score text,username text)";
        String error_tb = "create table error_tb(_id integer ,"+"content text,cs text,tf integer)";
        student_db.execSQL(create_question);
        student_db.execSQL(error_tb);
        System.out.println("创建ing");
        student_db.execSQL(create_tb);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insert(String tb,ContentValues value){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(db);
        db.insert(tb,null,value);
    }
    public Cursor query(String tb,String username){
        student_db = getReadableDatabase();
        if (tb.equals("question_tab")){
        Cursor cur = student_db.query(tb,new String[]{"_id","content","class","TorF"}  ,null,null,null,null,null);
        return cur;
        }else if (tb.equals("Stutent_tab")){
            System.out.println("即将查询"+username);
            Cursor cur = student_db.query(tb, new String[]{"_id", "Date","Score","username"},"username=?",new String[]{username},null,null,null);
            return cur;
        }else{
            Cursor cur = student_db.query(tb,new String[]{"_id","content","cs","tf"}  ,null,null,null,null,null);
            return cur;

        }

    }
    //精确查找,同名不同参
    public Cursor query2(String tb,String cs){
        student_db = getReadableDatabase();
        //根据cs来查找，
        Cursor cur = student_db.query(tb,null,"cs=?",new String[]{cs},null,null,null);
        return cur;
    }

    public void delete(String tb,int id){
        if (student_db==null) student_db=getWritableDatabase();
        student_db.delete(tb, "_id=?", new String[]{String.valueOf(id)});

        }
    public void close(){
        if (student_db!=null){
            student_db.close();
        }
    }

}
