package com.example.dalal.myapplication;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class invitation_info extends AppCompatActivity {

    public Button sug,ignor,accept ;
    ListView lv ;
    String line = null;
    String result = null;
    String [] arr ;
    public InputStream is ;
    static double lati;
    static double loni;
    Button map;
    //String selectedFromList = (lv.getItemAtPosition(1).toString());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        lv = (ListView) findViewById(R.id.list);
        map = (Button) findViewById(R.id.button2);
        sug= (Button)findViewById(R.id.sug);
        ignor= (Button)findViewById(R.id.ignor);
        accept= (Button)findViewById(R.id.accept);


        StrictMode.ThreadPolicy policy = new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        StrictMode.ThreadPolicy policy2 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy2);


        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://zwarh.net/zwarhapp/Mai/invitation_info.php?selectedFromList="+invitations.ID);
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
            String sreOne =result.substring(1,length-2);

            //use toString() to get the data result
            result=sb.toString();

            // check the data
            System.out.println(sreOne);
            arr= sreOne.split(",");
            int arrLength = arr.length ;

            lv.setAdapter(new ArrayAdapter<String>(invitation_info.this,android.R.layout.simple_list_item_1,arr));
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override


                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    List<NameValuePair> nameValuePair =new ArrayList<NameValuePair>(1);
                    AlertDialog.Builder builder = new AlertDialog.Builder(invitation_info.this);

                    String input= (getIntent().getExtras().getString("latitude"));
                  //  String input=(String)parent.getItemAtPosition(position);
                    lati=Double.parseDouble(input.replaceAll(" ",""));

                   // String input2=(String)parent.getItemAtPosition(position);
                    String input2= (getIntent().getExtras().getString("longitude"));
                    loni=Double.parseDouble(input2.replaceAll(" ",""));


                    AlertDialog alert = builder.create();
                    alert.show();



                }
            });
        }  catch (IOException e) {
            e.printStackTrace();
        }






        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


           List<NameValuePair> nameValuePair =new ArrayList<NameValuePair>(1);



                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://zwarh.net/zwarhapp/Mai/come.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is=entity.getContent();
                    String msg = "Acepptance sent succefully";
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    public void sugg (View view)
    {
        Intent intent = new Intent(invitation_info.this, sugg.class);
        startActivity(intent);
    }
    public void ignor (View view)
    {
        Intent intent = new Intent(invitation_info.this, apologiz.class);
        startActivity(intent);
    }


    public void map (View view)
    {
        Intent intent = new Intent(invitation_info.this,coordenation.class);
        startActivity(intent);
    }


}
