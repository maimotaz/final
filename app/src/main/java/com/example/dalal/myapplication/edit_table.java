package com.example.dalal.myapplication;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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

public class edit_table extends AppCompatActivity {

    public ImageButton insert ;
    public EditText name;
    public EditText tableNumber;
    public EditText Tablestate;
    public EditText numseats;
    public InputStream is ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_table);

        insert= (ImageButton)findViewById(R.id.imageButton15);
        tableNumber=(EditText)findViewById(R.id.tableNumber);
        name=(EditText)findViewById(R.id.restname);
        Tablestate=(EditText)findViewById(R.id.Tablestate);
        numseats=(EditText)findViewById(R.id.numseats);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rest=name.getText().toString();
                String number=tableNumber.getText().toString();
                String state=Tablestate.getText().toString();
                String num=numseats.getText().toString();


                if      (number.length() == 0) {tableNumber.setError("please enter table number ");}
                else if (state.length() == 0) {Tablestate.setError("please enter the table status ");}
                else if (num.length() == 0) {numseats.setError("please enter the number of seats ");}
                else {

                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);

                    nameValuePair.add(new BasicNameValuePair("ResName", rest));
                    nameValuePair.add(new BasicNameValuePair("tableNumber", number));
                    nameValuePair.add(new BasicNameValuePair("Tablestate", state));
                    nameValuePair.add(new BasicNameValuePair("numseats", num));


                    try {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://zwarh.net/zwarhapp/Mai/Edit_Table.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                        HttpResponse response = httpClient.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        String msg = "Updated succefully";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
