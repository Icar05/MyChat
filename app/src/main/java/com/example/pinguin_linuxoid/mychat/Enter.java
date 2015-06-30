package com.example.pinguin_linuxoid.mychat;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.concurrent.ExecutionException;


public class Enter extends Activity{


    final String NAME = "name";
    final String PARSER = "`";

    TextView label, tvNotif, tempView;

    ListView lv_contacts;
    ListView lv_messages;

    String[] contact = {""};
    String[] message = {""};

    String username ,temp,temp2,temp3,data = null;
    String reciever = null;         //ето имя втбрпного контакта
    LinearLayout LL;

    EditText mBox;
    Send_data sd;

    ArrayAdapter<String> aC;
    MyAdapter myAdapter;


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
        lv_messages = (ListView) findViewById(R.id.Messages);
        lv_messages.setVisibility(View.INVISIBLE);

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

        Update_Adaptor("get_Friends");
    }



    public void onClick(View v)
    {
        switch (v.getId())
        {


            case R.id.Add:               Intent intent = new Intent(this, Find_friends.class);
                                         intent.putExtra(NAME, username);
                                         startActivityForResult(intent, 1);
                                         break;


            case R.id.Send :             String message = mBox.getText().toString();
                                         if(message.equals(""))
                                         {
                                             tempView.setText("Нету сообщения!");
                                             tempView.setTextColor(Color.RED);
                                             break;
                                         }
                                         if(reciever == null)
                                         {
                                              tempView.setText("Сначала выберите контакт!");
                                              tempView.setTextColor(Color.RED);
                                              break;
                                         }

                                         data = "Message" + PARSER + username + PARSER + reciever+PARSER+message;
                                         sd = new Send_data(data);
                                         sd.execute();
                                         Update_Message_Adaptor();
                                         tempView.setText("");
                                         break;


            case R.id.btnYes:            data = "Add_Yes" + PARSER + username + PARSER + temp;
                                         sd = new Send_data(data);
                                         sd.execute();
                                         LL.setVisibility(View.INVISIBLE);
                                         Update_Adaptor("get_Friends");

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

                                          Update_Adaptor("get_Friends");
                                          break;

            case R.id.Exit :               data = "exit" + PARSER + username + PARSER + "@";
                                           sd = new Send_data(data);
                                           sd.execute();

                                           Intent home_intent = new Intent(this, MainActivity.class);
                                           startActivity(home_intent);


                                           break;

            case R.id.chkOnline:           if(((CheckBox) v).isChecked())
                                               Update_Adaptor("get_online_friends");

                                           else
                                               Update_Adaptor("get_Friends");

                                            break;

            default:                        break;
        }
    }


    public void Update_Adaptor(String query)
    {
        temp2 = null;
        data = query+ PARSER + username + PARSER + "@";
        sd = new Send_data(data);
        sd.execute();


        try {


            temp2 = sd.get().toString();
            if(temp2.equals("Unknown host") || temp2.equals("No connecting to internet! "))
            {
                tempView.setText(temp2);
                tempView.setTextColor(Color.RED);
            }

            else {
                contact = temp2.split(PARSER);

                aC = new ArrayAdapter<String>(this, R.layout.contacts_adaptor, contact);
                lv_contacts.setAdapter(aC);
                lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        reciever = contact[position];// имя вибраного

                        for(int i = 0; i< parent.getChildCount(); i++)
                            parent.getChildAt(i).setBackgroundColor(Color.WHITE);

                            view.setBackgroundColor(Color.RED);


                            Update_Message_Adaptor();
                    }
                });
            }

        } catch (InterruptedException e) {
            e.fillInStackTrace();
        } catch (ExecutionException e) {
            e.fillInStackTrace();
        } finally {
            sd.isCancelled();
        }
    }


    public void Update_Message_Adaptor()
    {
        temp3 = null;
        data = "get_Messages" + PARSER + username + PARSER + reciever;
        sd = new Send_data(data);
        sd.execute();


        try {


            temp3 = sd.get().toString();
            if(temp3.equals("Unknown host") || temp3.equals("No connecting to internet! ") || temp3.equals(""))
            {
                tempView.setText(temp3);
                tempView.setTextColor(Color.RED);
            }

            else {
                message = temp3.split(PARSER);

                myAdapter = new MyAdapter(this, message, username);
                lv_messages.setAdapter(myAdapter);
                lv_messages.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        view.setBackgroundColor(Color.YELLOW);

                    }
                });
                lv_messages.setVisibility(View.VISIBLE);
            }

        } catch (InterruptedException e) {
            e.fillInStackTrace();
        } catch (ExecutionException e) {
            e.fillInStackTrace();
        } finally {
            sd.isCancelled();
        }
    }

}
