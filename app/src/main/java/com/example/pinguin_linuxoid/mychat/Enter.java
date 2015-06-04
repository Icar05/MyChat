package com.example.pinguin_linuxoid.mychat;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Enter extends Activity{

    ArrayList<Map<String, Object>> data_messages;
    ArrayList<Map<String, Object>> data_contacts;


    final String ATTRIBUTE_NAME_TEXT = "text";
    TextView reciever_name;
    TextView label;

    Button send;
    ListView contacts;
    ListView messages;

    String[] contact = {"Panda", "Chapaye", "JJ", "Icar"};
    String[] message = {"Привет", "бла бла бла", "something else"};
    String reciever = "";
    BroadcastReceiver br;


    int[] to = {R.id.temp_message};
    int[] to2 = {R.id.Cont};

    String[] from = {ATTRIBUTE_NAME_TEXT};

    EditText mBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter);


        label = (TextView)findViewById(R.id.Label);

        String temp =getIntent().getStringExtra("name");
        label.setText("Hello, "+temp+ " !");

        contacts = (ListView) findViewById(R.id.Contacts);
        messages = (ListView) findViewById(R.id.Messages);
        contacts.setBackgroundColor(Color.MAGENTA);
        messages.setBackgroundColor(Color.GREEN);
        send = (Button) findViewById(R.id.Send);
        mBox = (EditText) findViewById(R.id.MesBox);
        reciever_name = (TextView)findViewById(R.id.reciever_name);

        data_messages = new ArrayList<>(message.length);
        data_contacts = new ArrayList<>(contact.length);



        Map<String, Object> m_c;
        Map<String, Object> m_m;

        for (int i = 0; i < message.length; i++) {
            m_c = new HashMap<String, Object>();
            m_c.put(ATTRIBUTE_NAME_TEXT, message[i]);
            data_messages.add(m_c);
        }

        for (int i = 0; i < contact.length; i++)
        {
            m_m = new HashMap<String, Object>();
            m_m.put(ATTRIBUTE_NAME_TEXT, contact[i]);
            data_contacts.add(m_m);
        }


        SimpleAdapter sMessages = new SimpleAdapter(this, data_messages, R.layout.temp, from, to);
        SimpleAdapter sContacts = new SimpleAdapter(this, data_contacts, R.layout.temp, from, to);

        messages.setAdapter(sMessages);
        contacts.setAdapter(sContacts);

        contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                reciever = contact[position];
                view.setBackgroundColor(Color.MAGENTA);
                reciever_name.setText("Message from " + reciever);
                reciever_name.setBackgroundColor(Color.GREEN);
            }
        });



    }


    @Override
    protected void onDestroy() {
        super .onDestroy();

        unregisterReceiver(br);
    }


    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.Send :            //Сделать базу которая получает и
                //разворачивает putExtra
                //Добавляет сообщение и отравляет обратно
                break;


            case R.id.find_user3:          //Искать по таблицам бд
                break;
            default:
                break;
        }
    }

}
