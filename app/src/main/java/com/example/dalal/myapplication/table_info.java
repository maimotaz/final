package com.example.dalal.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class table_info extends AppCompatActivity {

    ImageButton ignor, insert;
    ListView lv ;
    InputStream is ;
    String line = null;
    String result = null;
    String [] arr ;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_info);


        lv = (ListView) findViewById(R.id.list);
        ignor= (ImageButton)findViewById(R.id.imageButton15);
        insert= (ImageButton)findViewById(R.id.imageButton14);

        StrictMode.ThreadPolicy policy = new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // The button to update the state
            insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectedFromList = (lv.getItemAtPosition(0).toString());
                List<NameValuePair> nameValuePair =new ArrayList<NameValuePair>(1);
                nameValuePair.add(new BasicNameValuePair("selectedFromList",selectedFromList));


                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://zwarh.net/zwarhapp/Mai/update_table_status.php?selectedFromList="+unreserved_table.selectedFromList);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is=entity.getContent();
                    String msg = "You reserved succefully";
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                nameValuePair.add(new BasicNameValuePair("selectedFromList",selectedFromList));

            }
        });


        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://zwarh.net/zwarhapp/Mai/Table_info.php?selectedFromList="+unreserved_table.selectedFromList);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();


        }catch (Exception e){
            System.out.print("exception 1 caught");
            //exception handel code
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);


            StringBuilder sb = new StringBuilder();
            while ((line=reader.readLine())!=null)
            sb.append(line+"\n");

            result=sb.toString();
            result=result.replace('"',' ');
            int length =result.length();
            String sreOne =result.substring(2,length-1);

            //use toString() to get the data result
            result=sb.toString();
            // check the data
            System.out.println(sreOne);
            arr= sreOne.split(",");
            lv.setAdapter(new ArrayAdapter<String>(table_info.this,android.R.layout.simple_list_item_1,arr));




        }  catch (IOException e) {
            e.printStackTrace();
        }}

    public void ignor (View view)
    {
        Intent intent = new Intent(table_info.this, unreserved_table.class);
        startActivity(intent);
    }


    }

