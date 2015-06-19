package com.example.pinguin_linuxoid.mychat;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;


public class Enter extends Activity{


    final String NAME = "name";
    final String PARSER = "`";

    TextView label, tvNotif, tempView;

    ListView lv_contacts;
    ListView lv_messages;

    String[] contact = {""};
    String[] message = {"Message1", "Message2", "Message3", "Message4"};

    String username ,temp,temp2,data = null;
    String reciever = null;         //ето имя втбрпного контакта
    LinearLayout LL;

    EditText mBox;
    Send_data sd;

    ArrayAdapter<String> aC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter);


        username =getIntent().getStringExtra(NAME);

        tvNotif = (TextView)findViewById(R.id.tvNotif);
        tempView = (TextView)findViewById(R.id.tempView);
        label = (TextView)findViewById(R.id.Label);      /// Приветствие поциента
        label.setText("Wellcome, " + username + " !");

        LL = (LinearLayout)findViewById(R.id.LL);
        LL.setVisibility(View.INVISIBLE);

        mBox = (EditText) findViewById(R.id.MesBox);

        lv_contacts = (ListView) findViewById(R.id.Contacts);



        data = "notification" + PARSER + username + PARSER + "@";
        sd = new Send_data(data);
        sd.execute();


        try {
            temp = sd.get().toString();

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

        Update_Adaptor();


        lv_messages = (ListView) findViewById(R.id.Messages);
        ArrayAdapter<String> aM = new ArrayAdapter<String>(this, R.layout.test, message);
        lv_messages.setAdapter(aM);

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


            case R.id.btnYes:            data = "Add_Yes" + PARSER + username + PARSER + temp;
                                         sd = new Send_data(data);
                                         sd.execute();
                                         LL.setVisibility(View.INVISIBLE);
                                         Update_Adaptor();

                                         break;


            case R.id.btnNo :             data = "Add_No" + PARSER + username + PARSER + temp;
                                          sd = new Send_data(data);
                                          sd.execute();
                                          LL.setVisibility(View.INVISIBLE);
                                          break;

            case R.id.Drop :              if(reciever == null)
                                          {
                                              tempView.setText("Сначала выберите контакт!");
                                              break;
                                          }

                                          data = "Drop_Contact" + PARSER + username + PARSER + reciever;
                                          sd = new Send_data(data);
                                          sd.execute();

                                          Update_Adaptor();
                                          break;

            default:
                                         break;
        }
    }


    public void Update_Adaptor()
    {
        temp2 = null;
        data = "get_Friends" + PARSER + username + PARSER + "@";
        sd = new Send_data(data);
        sd.execute();


        try {
            temp2 = sd.get().toString();
            contact = temp2.split(PARSER);

            aC = new ArrayAdapter<String>(this, R.layout.test, contact);
            lv_contacts.setAdapter(aC);
            lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    reciever = contact[position];// имя вибраного
                    tempView.setText("Выбран "+reciever);
                }
            });

        } catch (InterruptedException e) {
            e.fillInStackTrace();
        } catch (ExecutionException e) {
            e.fillInStackTrace();
        } finally {
            sd.isCancelled();
        }
    }

}
