package com.example.pinguin_linuxoid.mychat;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class Find_friends extends Activity {

    Send_data sd;
    TextView title_result;
    ListView lv_Add_friends;
    ArrayList<Map<String, Object>> all_users;
    String[] contact = {""};
    Button btn_Add;
    int[] to = {R.id.temp_message};
    final String ATTRIBUTE_NAME_TEXT = "text";
    String[] from = {ATTRIBUTE_NAME_TEXT};
    String data, friend, username = null;
    ProgressBar pb;
    final String PARSER = "`";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_friends);


        title_result = (TextView) findViewById(R.id.title_Add_friends_result);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        lv_Add_friends = (ListView) findViewById(R.id.lv_Add_friends);
        btn_Add = (Button)findViewById(R.id.btn_Add_friends);

        username =getIntent().getStringExtra("name");
        data = "names" + PARSER + username + PARSER + "@";
        sd = new Send_data(data);
        sd.execute();


        try {
            String temp = sd.get().toString();


            if(temp.equals("Unknown host") || temp.equals("No connecting to internet! "))
            {
                title_result.setText(temp);
                btn_Add.setEnabled(false);
            }

            else {

                contact = temp.split(PARSER);

                all_users = new ArrayList<>(contact.length);
                Map<String, Object> m_f;

                for (int i = 0; i < contact.length; i++) {
                    m_f = new HashMap<String, Object>();
                    m_f.put(ATTRIBUTE_NAME_TEXT, contact[i]);
                    all_users.add(m_f);
                }

                SimpleAdapter sFriends = new SimpleAdapter(this, all_users, R.layout.temp, from, to);
                lv_Add_friends.setAdapter(sFriends);
                lv_Add_friends.setBackgroundColor(Color.RED);
                lv_Add_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        friend = contact[position];
                        title_result.setText(friend);
                        title_result.setTextColor(Color.WHITE);
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

            case R.id.btn_Add_friends :           title_result.setText("Click Click");
                                                  title_result.setTextColor(Color.RED);
                                                  break;
            default: break;
        }

    }
}
