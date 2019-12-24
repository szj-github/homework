package com.example.homework;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Regisit extends AppCompatActivity implements View.OnClickListener {
    Button regisit_btn;
    ProgressDialog progressDialog;
    TextView username,pwd,again_pwd;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisit);
        regisit_btn = findViewById(R.id.regisit_btn);
        username = findViewById(R.id.regisit_username);
        pwd = findViewById(R.id.pwd);
        again_pwd = findViewById(R.id.again_pwd);
        username.setOnClickListener(this);
        regisit_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.regisit_btn){
                String username1 = username.getText().toString();
                String password = pwd.getText().toString();
                regisit(username1, password);
        }
    }
    private void regisit(final String username, final String password){
        progressDialog = ProgressDialog.show(Regisit.this, "注册中",
                "用户："+username, true);

        new Thread() {
            public void run() {
                try {
                    /* 在这里写上要背景运行的程序片段 */
                    /* 为了明显看见效果，以暂停3秒作为示范 */
                    String url = "http://10.0.2.2:8080/ServletRegisit";
//                String queryString = "username" + username + "&password" + password;
                    HttpPost request = new HttpPost(url);
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("username",username));
                    params.add(new BasicNameValuePair("password",password));
                    try{
                        sleep(1000);
                        request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpResponse response = httpClient.execute(request);
                        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                            String str = EntityUtils.toString(response.getEntity());
                            System.out.println(str);
                            if (str.equals("注册成功")){
                                System.out.println("ok");
                                Intent it =new Intent(Regisit.this,Login.class);
                                startActivity(it);
                            }else {
                                Looper.prepare();
                                Toast.makeText(Regisit.this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
                                Intent it2 = new Intent(Regisit.this,Regisit.class);
                                startActivity(it2);
                                Looper.loop();
                            }
                        }else {
                            System.out.println("fail");
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 卸载所创建的myDialog对象。
                    progressDialog.dismiss();
                }
            }
        }.start(); /* 开始运行运行线程 */
    }
}
