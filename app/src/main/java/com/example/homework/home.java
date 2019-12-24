package com.example.homework;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class home extends AppCompatActivity implements View.OnClickListener  {
    ListView lvdata;
    Button btn_test, btn_history,btn_search;
    Spinner spinner;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private Appdata appdata;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.author: //对应的ID就是在add方法中所设定的Id
                Toast.makeText(this, "关于作者", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(this, about.class);
                startActivity(it);
                break;
            case R.id.quit:
                Toast.makeText(this, "退出", Toast.LENGTH_SHORT).show();
                Intent it2 = new Intent(this, MainActivity.class);
                startActivity(it2);
                break;
            case R.id.add_question:
                Toast.makeText(this, "添加题目", Toast.LENGTH_SHORT).show();
                Intent it3 = new Intent(this, add_question.class);
                startActivity(it3);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        appdata = (Appdata)getApplication();
        System.out.println(appdata.getUsername());
        //list适配器
        lvdata = findViewById(R.id.list_data);
        btn_test = findViewById(R.id.test_btn);
        btn_history = findViewById(R.id.history_btn);
        btn_search = findViewById(R.id.btn_search);
        spinner = findViewById(R.id.spinner);
        btn_search.setOnClickListener(this);
        btn_history.setOnClickListener(this);
        btn_test.setOnClickListener(this);
        lvdata.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, final long l) {
                final DBhelper dBhelper = new DBhelper(home.this, null, null, 1);
                AlertDialog.Builder builder = new AlertDialog.Builder(home.this);
                builder.setMessage("确定删除？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //通过position获取当前的item所有值 hashmap
                        HashMap person = (HashMap)lvdata.getItemAtPosition((int) l);
                        System.out.println("这是"+person.toString());
                        dBhelper.delete("question_tab", Integer.parseInt(person.get("id").toString()));
                        Intent it = new Intent(home.this, home.class);
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
        DBhelper dbhelp = new DBhelper(this, null, null, 1);
//        ArrayList<String> dataList = new ArrayList<>();
        Cursor cursor = dbhelp.query("question_tab",appdata.getUsername().toString());

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                Map<String, Object> map = new HashMap<String, Object>();
                String data = cursor.getString(cursor.getColumnIndex("content"));
                String _id = cursor.getString(cursor.getColumnIndex("_id"));
                String cs = cursor.getString(cursor.getColumnIndex("class"));
                String tf = cursor.getString(cursor.getColumnIndex("TorF"));
                if (tf.equals("1")) {
                    tf = "正确";
                    System.out.println("正确");
                } else if (tf.equals("0")) {
                    tf = "错误";
                }
                map.put("id", _id);
                map.put("类别", cs);
                map.put("内容", data);
                map.put("tf", tf);
                list.add(map);
            }
            cursor.close();
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.item, new String[]{"id", "类别", "内容", "tf"},
                new int[]{R.id.item1, R.id.item2, R.id.item4, R.id.item3});
        lvdata.setAdapter(adapter);
        //get 组件
    }

    public static String getType(Object o) { //获取变量类型方法
        return o.getClass().toString(); //使用int类型的getClass()方法
    }
    public void refresh() {

        onCreate(null);

    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.history_btn) {
            Intent it = new Intent(this, history.class);
            startActivity(it);
        } else if (view.getId() == R.id.test_btn) {
            Intent it2 = new Intent(this, test.class);
            startActivity(it2);
        }else  if (view.getId() == R.id.btn_search){
            List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
            Object spinvalue = spinner.getSelectedItem();
            for (Map map: list ){
                if (map.get("类别").equals(spinvalue)){
                    list2.add(map);
                }
            }
            SimpleAdapter adapter = new SimpleAdapter(this, list2, R.layout.item, new String[]{"id", "类别", "内容", "tf"},
                    new int[]{R.id.item1, R.id.item2, R.id.item4, R.id.item3});
            lvdata.setAdapter(adapter);
        }
    }

}