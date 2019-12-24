package com.example.homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Error extends AppCompatActivity {
    private Intent it;
    ListView lvdata;
    ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        lvdata = findViewById(R.id.error_list);
        it = getIntent();
        Bundle bundle = getIntent().getExtras();
        ArrayList list = bundle.getParcelableArrayList("error");
//从List中将参数转回 List<Map<String, Object>>
        List<Map<String, Object>> lists= (List<Map<String, Object>>)list.get(0);
        String sResult = "";
        for (Map<String, Object> m : lists)
        {
            for (String k : m.keySet())
            {
                sResult += "/r/n"+k + " : " + m.get(k);
            }
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.item, new String[]{"id", "类别", "内容", "tf"},
                new int[]{R.id.item1, R.id.item2, R.id.item4, R.id.item3});
//        list.setAdapter(adapter);
        lvdata.setAdapter(adapter);

    }
}
