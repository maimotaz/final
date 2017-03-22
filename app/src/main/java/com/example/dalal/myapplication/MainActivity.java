package com.example.dalal.myapplication;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public EditText username, name, passwored, phoneNumber;

    public Button insert ;

    public InputStream is ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        passwored = (EditText) findViewById(R.id.pass);
        phoneNumber = (EditText) findViewById(R.id.phone);

        insert= (Button)findViewById(R.id.button);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un = username.getText().toString();
                String n = name.getText().toString();
                String p = passwored.getText().toString();
                String pn= phoneNumber.getText().toString();

                List<NameValuePair>  nameValuePair =new ArrayList<NameValuePair>(1);

                nameValuePair.add(new BasicNameValuePair("uname",un));
                nameValuePair.add(new BasicNameValuePair("name",n));
                nameValuePair.add(new BasicNameValuePair("psw",p));
                nameValuePair.add(new BasicNameValuePair("phone",pn));

                try{
                      HttpClient httpClient = new DefaultHttpClient();
                      HttpPost httpPost = new HttpPost("http://zwarh.net/zwarhapp/Dalal/Add.php");
                      httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                      HttpResponse response = httpClient.execute(httpPost);
                      HttpEntity entity = response.getEntity();
                      is=entity.getContent();
                      String msg = "Data Entered succefully";
                      Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
