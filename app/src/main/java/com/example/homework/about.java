package com.example.homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class about extends AppCompatActivity {
    private Appdata appdata;
    TextView textView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);
        appdata = (Appdata)getApplication();
        textView = findViewById(R.id.xingming);
        textView.setText(appdata.getUsername());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
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
            case R.id.question_center:
                Toast.makeText(this,"答题中心",Toast.LENGTH_SHORT).show();
                Intent it4 =  new Intent(this,home.class);
                startActivity(it4);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
