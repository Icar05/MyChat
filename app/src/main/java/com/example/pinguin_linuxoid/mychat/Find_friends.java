package com.example.pinguin_linuxoid.mychat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.concurrent.ExecutionException;


public class Find_friends extends Activity {

    Send_data sd;
    TextView title_result;
    ListView lv_Add_friends;
    String[] contact = {""};
    Button btn_Add, btn_Home;
    String data, friend, username, temp = null;
    ProgressBar pb;
    final String PARSER = "`";
    final String NAME = "name";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_friends);


        title_result = (TextView) findViewById(R.id.title_Add_friends_result);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        lv_Add_friends = (ListView) findViewById(R.id.lv_Add_friends);
        btn_Add = (Button)findViewById(R.id.btn_Add_friends);
        btn_Home = (Button)findViewById(R.id.btn_Home);

        username =getIntent().getStringExtra(NAME);

        data = "names" + PARSER + username + PARSER + "@";
        sd = new Send_data(data);
        sd.execute();


        try {
            temp = sd.get().toString();

            if(temp.equals("Unknown host") || temp.equals("No connecting to internet! "))
            {
                title_result.setText(temp);
                title_result.setTextColor(Color.RED);
                btn_Add.setEnabled(false);
            }

            else {

                contact = temp.split(PARSER);

                ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.add_friends_item, contact);
                lv_Add_friends.setAdapter(aa);
                lv_Add_friends.setBackgroundColor(Color.WHITE);


                lv_Add_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        friend = contact[position];

                        for(int i = 0; i< parent.getChildCount(); i++)
                            parent.getChildAt(i).setBackgroundColor(Color.WHITE);

                             view.setBackgroundColor(Color.RED);
                    }
                });

            }

        } catch (InterruptedException e) {
          e.fillInStackTrace();
        } catch (ExecutionException e) {
           e.fillInStackTrace();
        } finally {
            sd.isCancelled();
            pb.setVisibility(View.GONE);
        }


    }


    public void onClick_F(View v) {
        switch (v.getId())
        {

            case R.id.btn_Add_friends :           if(friend == null)
                                                     title_result.setText("Сначала выберите пользователя из списка!");

                                                else {
                                                   data = "add_Friend" + PARSER + username + PARSER + friend + PARSER;
                                                   sd = new Send_data(data);
                                                    sd.execute();

                                                   try {
                                                           temp = sd.get().toString();
                                                           title_result.setText(temp);
                                                           title_result.setTextColor(Color.WHITE);
                                                        } catch (InterruptedException e) {
                                                             e.fillInStackTrace();
                                                        } catch (ExecutionException e) {
                                                             e.fillInStackTrace();
                                                        } finally {
                                                        sd.isCancelled();
                                                          }
                                                  }
                                                  break;


            case R.id.btn_Home        :           Intent intent = new Intent();
                                                  setResult(RESULT_OK, intent);
                                                  finish();

                                                  break;
            default: break;
        }

    }
}
