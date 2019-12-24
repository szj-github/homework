package com.example.homework;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class add_question extends AppCompatActivity implements View.OnClickListener {
    CheckBox tf_check;
    EditText edt;
    Button btn;
    Spinner spinner;
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
        setContentView(R.layout.activity_add_question);
        tf_check = findViewById(R.id.checkBox);
        btn = findViewById(R.id.post_btn);
        edt = findViewById(R.id.add_question);
        spinner = findViewById(R.id.add_spin);

        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.post_btn) {
            DBhelper dbhelp = new DBhelper(this, null,null, 1);
            edt.getText();
            ContentValues content = new ContentValues();
            content.put("content", String.valueOf(edt.getText()));
            content.put("class",spinner.getSelectedItem().toString());
            if (tf_check.isChecked()) {
                content.put("TorF", "1");
                dbhelp.insert("question_tab", content);
                Toast.makeText(this,"添加成功",Toast.LENGTH_LONG).show();
                Intent it = new Intent(this,home.class);
                startActivity(it);
            } else {
                content.put("TorF", "0");
                dbhelp.insert("question_tab", content);
                Toast.makeText(this, "添加成功", Toast.LENGTH_LONG).show();
                Intent it = new Intent(this,home.class);
                startActivity(it);
            }
        }
    }
}
