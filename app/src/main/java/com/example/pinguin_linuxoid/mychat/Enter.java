package com.example.pinguin_linuxoid.mychat;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class Enter extends Activity{


    final String NAME = "name";
    final String PARSER = "`";

    TextView label, tvNotif;

    ListView lv_contacts;
    ListView lv_messages;

    String[] contact = {"Panda", "Chapaye", "JJ", "Icar", "Ivan", "Leska", "Someone else"};
    String[] message = {"Привет", "бла бла бла", "something else"};

    String username , data = null;
    String reciever = "";         //ето имя втбрпного контакта
    LinearLayout LL;

    EditText mBox;
    Send_data sd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter);


        username =getIntent().getStringExtra(NAME);

        tvNotif = (TextView)findViewById(R.id.tvNotif);
        label = (TextView)findViewById(R.id.Label);      /// Приветствие поциента
        label.setText("Wellcome, " + username + " !");

        LL = (LinearLayout)findViewById(R.id.LL);
        LL.setVisibility(View.INVISIBLE);


        data = "notification" + PARSER + username + PARSER + "@";
        sd = new Send_data(data);
        sd.execute();


        try {
            String temp = sd.get().toString();

            if(!temp.equals(""))
            {
                LL.setVisibility(View.VISIBLE);
                tvNotif.setText("Вас хочет добавить "+temp);
            }


        } catch (InterruptedException e) {
          e.fillInStackTrace();
        } catch (ExecutionException e) {
           e.fillInStackTrace();
        } finally {
            sd.isCancelled();
        }


        lv_contacts = (ListView) findViewById(R.id.Contacts);
        lv_messages = (ListView) findViewById(R.id.Messages);
        lv_contacts.setBackgroundColor(Color.WHITE);
        lv_messages.setBackgroundColor(Color.GREEN);


        mBox = (EditText) findViewById(R.id.MesBox);


        ArrayAdapter<String> aM = new ArrayAdapter<String>(this, R.layout.test, message);
        ArrayAdapter<String> aC = new ArrayAdapter<String>(this, R.layout.test, contact);


        lv_contacts.setAdapter(aC);
        lv_messages.setAdapter(aM);



        lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                reciever = contact[position];// имя вибраного
            }
        });



    }



    public void onClick(View v)
    {
        switch (v.getId())
        {


            case R.id.find_user3:        Intent intent = new Intent(this, Find_friends.class);
                                         intent.putExtra(NAME, username);
                                         startActivityForResult(intent, 1);
                                         break;

            case R.id.Send :             Toast.makeText(this, "i sending message)", Toast.LENGTH_SHORT).show();
                                         break;


            case R.id.btnYes:            Toast.makeText(this, "You say yes!", Toast.LENGTH_SHORT).show();
                                         /*
                                         data = "Add_Yes" + PARSER + username + PARSER + "@";
                                         sd = new Send_data(data);
                                         sd.execute();
                                         LL.setVisibility(View.INVISIBLE);
                                         */
                                         break;

            case R.id.btnNo :            Toast.makeText(this,"I say no!", Toast.LENGTH_SHORT).show();
                                         /*
                                         data = "Add_No" + PARSER + username + PARSER + "@";
                                         sd = new Send_data(data);
                                         sd.execute();
                                         LL.setVisibility(View.INVISIBLE);
                                         */
                                         break;

            default:
                                         break;
        }



    }

}
