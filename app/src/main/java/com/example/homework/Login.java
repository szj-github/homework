package com.example.homework;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Entity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import javax.net.ssl.HttpsURLConnection;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextView name,pwd,code;
    ImageView img_code;
    Button login;
    TextView btn_regisit;
    ProgressDialog progressDialog;
    private Appdata appdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.username);
        login = findViewById(R.id.btn_login);
        btn_regisit = findViewById(R.id.btn_regisit);
        pwd = findViewById(R.id.password);
        btn_regisit.setOnClickListener(this);
        login.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login){
            String username  = name.getText().toString();
            String password = pwd.getText().toString();
            login(username,password);
        }else if (view.getId()==R.id.btn_regisit){
            Intent it = new Intent(this,Regisit.class);
            startActivity(it);
        }
    }
    private void login(final String username, final String password){
        progressDialog = ProgressDialog.show(Login.this, "登陆中",
                "用户："+username, true);

        new Thread() {
            public void run() {
                try {
                    /* 在这里写上要背景运行的程序片段 */
                    /* 为了明显看见效果，以暂停3秒作为示范 */
                    String url = "http://10.0.2.2:8080/ServletAndLogin";
//                String queryString = "username" + username + "&password" + password;
                    HttpPost request = new HttpPost(url);
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("username",username));
                    params.add(new BasicNameValuePair("password",password));
                    try{
                        sleep(1000);

                        request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        HttpClient httpClient = new DefaultHttpClient();
                        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,2000);//连接时间
                        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,2000);//数据传输时间
                        HttpResponse response = null;
                        try {

                            response = httpClient.execute(request);

                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("网络获取错误");
                            if ((username.equals("test"))&&(password.equals("test"))) {
                                appdata = (Appdata) getApplication();
                                appdata.setUsername(username);
                                Intent it = new Intent(Login.this, about.class);
                                startActivity(it);
                            }
                        }
                        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                            String str = EntityUtils.toString(response.getEntity());
                            System.out.println(str=="登陆成功");
                            if (str.equals("登陆成功")){
                                System.out.println("ok");
                                appdata = (Appdata) getApplication();
                                appdata.setUsername(username);
                                Intent it =new Intent(Login.this,about.class);
                                startActivity(it);
                            }else if ((username.equals("test"))&&(password.equals("test"))){
                                appdata = (Appdata) getApplication();
                                appdata.setUsername(username);
                                Intent it =new Intent(Login.this,about.class);
                                startActivity(it);
                            }
                            else {
                                Looper.prepare();
                                Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                                Intent it2 = new Intent(Login.this,Login.class);
                                startActivity(it2);
                                Looper.loop();
                            }
                        }
                        else {
                            Looper.prepare();
                            Toast.makeText(Login.this, "网络错误", Toast.LENGTH_LONG).show();
                            Intent it5 = new Intent(Login.this,Login.class);
                            startActivity(it5);
                            Looper.loop();
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
