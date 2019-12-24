package com.example.homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_home;
    Button btn_CSDN,login_btn;
    private String username;
    private Appdata appdata;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
       if (username!=null) {
           getMenuInflater().inflate(R.menu.usernullmenu, menu);
       }else {
           getMenuInflater().inflate(R.menu.login, menu);
       }
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            //以下隐藏了
            case R.id.author: //对应的ID就是在add方法中所设定的Id
                Toast.makeText(this,"关于作者",Toast.LENGTH_SHORT).show();
                Intent it =  new Intent(this,about.class);
                startActivity(it);
                break;
            case R.id.quit:
                Toast.makeText(this,"退出",Toast.LENGTH_SHORT).show();
                Intent it2 =  new Intent(this,MainActivity.class);
                startActivity(it2);
                break;
            case R.id.add_question:
                Toast.makeText(this,"添加题目",Toast.LENGTH_SHORT).show();
                Intent it3 =  new Intent(this,add_question.class);
                startActivity(it3);
                break;
                //
            case R.id.login:
                Intent it4 = new Intent(this,Login.class);
                startActivity(it4);
            case R.id.change:
                Intent it5 = new Intent(this,Login.class);
                startActivity(it5);
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appdata = (Appdata)getApplication();
        username = appdata.getUsername();
        btn_home = findViewById(R.id.question_center);
        btn_home.setOnClickListener(this);
        btn_CSDN = findViewById(R.id.CSDN);
        btn_CSDN.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.CSDN){
            Intent it2 = new Intent(this,CSDN.class);
            startActivity(it2);
        }
        else if (view.getId()==R.id.question_center){
        appdata  = (Appdata)getApplication();
        username = appdata.getUsername();
        if (username!=null){
            Intent it5 = new Intent(MainActivity.this,home.class);
            startActivity(it5);
        }else {
            Intent it6 = new Intent(MainActivity.this,Login.class);
            startActivity(it6);
        }
        }


    }
}
