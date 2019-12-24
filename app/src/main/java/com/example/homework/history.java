package com.example.homework;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class history extends AppCompatActivity {
    ListView lvdata;
    private Appdata appdata;
    private String username;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.question_center: //对应的ID就是在add方法中所设定的Id
                Toast.makeText(this,"答题中心",Toast.LENGTH_SHORT).show();
                Intent it =  new Intent(this,home.class);
                startActivity(it);
                break;
            case R.id.author:
                Toast.makeText(this,"个人中心",Toast.LENGTH_SHORT).show();
                Intent it2 =  new Intent(this,about.class);
                startActivity(it2);
                break;
            case R.id.add_question:
                Toast.makeText(this,"添加问题",Toast.LENGTH_SHORT).show();
                Intent it3 =  new Intent(this,add_question.class);
                startActivity(it3);
                break;
            case R.id.quit:
                Toast.makeText(this,"退出",Toast.LENGTH_SHORT).show();
                Intent it4 =  new Intent(this,MainActivity.class);
                startActivity(it4);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appdata = (Appdata)getApplication();
        username = appdata.getUsername();
        setContentView(R.layout.activity_history);
        lvdata = findViewById(R.id.list_history);
        DBhelper dbhelp = new DBhelper(this, null,null, 1);
//        ArrayList<String> dataList = new ArrayList<>();
        System.out.println(username);
        Cursor cursor = dbhelp.query("Stutent_tab",username);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("_id","id");
        map1.put("日期","                 日期         ");
        map1.put("评分","评分");
        list.add(map1);
        if(cursor != null && cursor.getCount() >0){
            while(cursor.moveToNext()){
                Map<String,Object> map = new HashMap<String,Object>();
                String _id = cursor.getString(cursor.getColumnIndex("_id"));
                String date = cursor.getString(cursor.getColumnIndex("Date"));
                String Score = cursor.getString(cursor.getColumnIndex("Score"));
                map.put("_id",_id);
                map.put("日期",date);
                map.put("评分",Score);
                list.add(map);
            }
            cursor.close();

        }
        System.out.println(list.toString());
        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.item2,new String[]{"_id","日期","评分"},
                new int[]{R.id.item1,R.id.item2,R.id.item3});
        lvdata.setAdapter(adapter);
        lvdata.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, final long l) {
                final DBhelper dBhelper = new DBhelper(history.this, null, null, 1);
                AlertDialog.Builder builder = new AlertDialog.Builder(history.this);
                builder.setMessage("确定删除？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //通过position获取当前的item所有值 hashmap
                        HashMap person = (HashMap)lvdata.getItemAtPosition((int) l);
                        System.out.println("这是"+person.toString());
                        dBhelper.delete("Stutent_tab", Integer.parseInt(person.get("_id").toString()));
                        Intent it = new Intent(history.this, history.class);
                        startActivity(it);
                    }
                })
                        .setNegativeButton("点错了！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create().show();
                return false;
            }
        });
    }
}
