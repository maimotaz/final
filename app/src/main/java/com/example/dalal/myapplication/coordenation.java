package com.example.dalal.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class coordenation extends AppCompatActivity {

    public static String selectedFromList;
    ListView lv ;
    InputStream is ;
    String line = null;
    String result = null;
    String [] arr ;
    static double lati;
    static double loni;
    Button map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordenation);


        lv = (ListView) findViewById(R.id.list);
        map = (Button) findViewById(R.id.button2);


        StrictMode.ThreadPolicy policy = new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);




        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://zwarh.net/zwarhapp/Mai/location.php?ID="+invitations.ID);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();


            is = entity.getContent();


        }   catch (Exception e){
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
            String sreOne =result.substring(1,length-2);
            //use toString() to get the data result
            result=sb.toString();
            // check the data


            System.out.println(sreOne);
            arr= sreOne.split(",");
            int arrLength = arr.length ;

            String input=arr[0];
            lati=Double.parseDouble(input.replaceAll("\\s+",""));

            String input2=arr[1];
            loni=Double.parseDouble(input2.replaceAll("\\s+",""));



            lv.setAdapter(new ArrayAdapter<String>(coordenation.this,android.R.layout.simple_list_item_1,arr));

        }  catch (IOException e) {
            e.printStackTrace();
        }



    }
    public void map (View view)
    {

        Intent intent = new Intent(coordenation.this, MapsActivity.class);
        startActivity(intent);

    }
}
