package com.example.homework;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SyncStats;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.se.omapi.Session;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class test extends AppCompatActivity implements DialogInterface.OnClickListener{
    int counter = 0, score = 0;

    TextView txv;
    List<Map<String, Object>> question_list = new ArrayList<Map<String, Object>>();
    Boolean isSelected;
    //
    private Appdata appdata;
    HashSet ranset = new HashSet<Integer>();
    //
    ListView error_list;
    List<Map<String, Object>> random_ques = new ArrayList<Map<String, Object>>();
    int resultcode;
    AlertDialog firstDialog;
    CountDownTimer myCountDownTimer;
    TextView textView;
    int front=0;
    int back=0;
    int database=0;
    int max;
    private String username;
    private CharSequence[] item;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> errorlist = new ArrayList<Map<String,Object>>();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        appdata = (Appdata)getApplication();
        username = appdata.getUsername();
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
            case R.id.question_center:
                Toast.makeText(this, "答题中心", Toast.LENGTH_SHORT).show();
                Intent it4 = new Intent(this, home.class);
                startActivity(it4);
                break;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        DBhelper dbhelper = new DBhelper(this, null, null, 1);
        error_list = findViewById(R.id.error_list);
        textView = findViewById(R.id.text_test);
        Cursor cursor = dbhelper.query("question_tab",username);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                String data = cursor.getString(cursor.getColumnIndex("content"));
                String _id = cursor.getString(cursor.getColumnIndex("_id"));
                String cs = cursor.getString(cursor.getColumnIndex("class"));
                String tf = cursor.getString(cursor.getColumnIndex("TorF"));
                map.put("id", _id);
                map.put("类别", cs);
                map.put("内容", data);
                map.put("tf", tf);
                list.add(map);
            }
            cursor.close();
        }
        //which代表用户上一次选择`

        System.out.println(list.toString());
        System.out.println("__________");
        ranset = Random_Num(13,30);//从0-15题中抽取十个题出来
        System.out.println(ranset.toString());
        for (Object i:ranset){
        random_ques.add(list.get((Integer) i));
    }
    //randomset 为随机抽取的问题
    Dialogshow(0);
        System.out.println("ok");
}
    public void countdown (TextView txv, AlertDialog Dialog){
        final TextView txv_count=txv;
        txv_count.setTextColor(Color.rgb(255, 00, 00));
        final AlertDialog myDialog=Dialog;
        myCountDownTimer=new CountDownTimer(11000,1000) {
            @Override
            public void onTick(long millisUntilFinished){
                txv_count.setText((millisUntilFinished/1000)+"s");
            }
            @Override
            public void onFinish() {
                if (myDialog!=null) {
                    myDialog.dismiss();
                }
            }};
        myCountDownTimer.start();
    }
    public HashSet Random_Num(int num_count, int num_range) {
        Random ran = new Random();
        //num_count随机数 数量   mnum_range为：范围
        for (int i = 0; i < num_count; i++) {
            ranset.add(ran.nextInt(num_range));//add into ranset
        }
        //判断ranset数量是否够
        if (ranset.size() <= num_count) {
            Random_Num(num_count - ranset.size(), num_range);
        }
        return ranset;
    }
    @Override
    public void onClick(DialogInterface dialogInterface, int which) {//处理dialog的点击事件
        isSelected = true;//将isselected设置为true
        resultcode = which;//which为获取的用户选择
//        resultcode = DialogInterface.BUTTON_POSITIVE;//值为-1
//        System.out.println("res的值为"+resultcode);
//        Dialogshow(which);
    }
    private void Dialogshow(int which) {
        if (counter<10) {
            //count!=0：第一次count会是0，跳过此步，count-1是真正的索引
            //which 是：为-1，不是为-2
            System.out.println("which的值为"+which);
            int tf=0;
            if (counter>=1){
                if (random_ques.get(counter-1).get("tf").equals("0")){
                    tf=-2;
                }else {
                    tf=-1;
                }
            }
            if (counter!=0 && (String.valueOf(which).equals(String.valueOf(tf))))
            {
                score+=20;
            }
            else if (counter!=0 &&(String.valueOf(which).equals(String.valueOf(tf)))==false){
                System.out.println("答错题了！");
                System.out.println(random_ques.get(counter-1));
                errorlist.add(random_ques.get(counter-1));

            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            TextView txv = new TextView(this);
            txv.setGravity(Gravity.CENTER);
            txv.setTextSize(40);
            builder.setTitle("快乐答题 gogogo");
            if (counter==9) //判断是否达到最大题目数
            {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                //获得系统时间
                int month = calendar.get(Calendar.MONTH) + 1;
                //日
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                //小时
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                //分钟
                int minute = calendar.get(Calendar.MINUTE);
                String date = year + "年" + month + "月" + day + "日 " + hour + ":" + minute;
                builder.setMessage("你的得分为：" + score + "分");
                ContentValues contentValues = new ContentValues();
                contentValues.put("Score", score);
                contentValues.put("Date", date);
                contentValues.put("username",username);
                System.out.println("用户"+username+"score"+score);
                DBhelper db = new DBhelper(this, null, null, 1);
                db.insert("Stutent_tab", contentValues);
                ContentValues error_content = new ContentValues();
                for (Map<String,Object> map:errorlist){
                    error_content.put("_id",map.get("id").toString());
                    error_content.put("cs",map.get("类别").toString());
                    if ("前端".equals(map.get("类别").toString())){
                        front++;
                    }else if ("后端".equals(map.get("类别").toString())){
                        back++;
                    }else{
                        database++;
                    }
                    int[] arr = {front,back,database};
                    Arrays.sort(arr);
                    max = arr[0];
                    error_content.put("content",map.get("内容").toString());

//                    db.insert("error_tb",error_content);
                }
            }
//                保存得分到Score_tab
            else{
                // 未达到最大题目数 继续下一题
                builder.setMessage(random_ques.get(counter).get("内容").toString());
            }
            builder.setView(txv);
            if (counter<=10) {
                builder.setPositiveButton("是", this);
                builder.setNegativeButton("不是", this);
            }
            firstDialog=builder.create();
            firstDialog.show();
            counter++;
            countdown(txv, firstDialog);
            firstDialog.setOnDismissListener(new Dialog.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog){
                    firstDialog = null;
                    if (myCountDownTimer!=null){
                        // 停止countDownTimer
                        myCountDownTimer.cancel();
                        myCountDownTimer=null;
                    }
                    if (isSelected == true) {
                        Dialogshow(resultcode);//计算得分进入下一个问题展示页面
                        isSelected =false;
                    }else {
                        Dialogshow(0);
                    }
                }
            });
        }else {
            SimpleAdapter adapter = new SimpleAdapter(this, errorlist, R.layout.item, new String[]{"id", "类别", "内容", "tf"},
                    new int[]{R.id.item1, R.id.item2, R.id.item4, R.id.item3});
            error_list.setAdapter(adapter);
            textView.setText("数据库题错了"+database+"个\n"+"后端题错了"+back+"个\n"+"前端题错了"+front+"个\n");
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
            textView.setTextColor(Color.rgb(122,54,48));
        }
    }
}



