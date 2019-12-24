package com.example.homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static android.view.KeyEvent.KEYCODE_BACK;

public class CSDN extends AppCompatActivity {
    private WebView mMv;
    private String mUrl = "https://wenku.baidu.com/view/254ee095b7360b4c2f3f6440.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csdn);
        mMv = findViewById(R.id.mv);
        WebSettings settings = mMv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mMv.loadUrl(mUrl);
        mMv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mMv.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.cancel,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.cancel: //对应的ID就是在add方法中所设定的Id
                Toast.makeText(this,"退出",Toast.LENGTH_SHORT).show();
                Intent it =  new Intent(this,MainActivity.class);
                startActivity(it);
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode ==KEYCODE_BACK)&&mMv.canGoBack())
        {
            mMv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
