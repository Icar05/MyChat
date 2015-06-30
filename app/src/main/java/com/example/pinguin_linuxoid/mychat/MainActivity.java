package com.example.pinguin_linuxoid.mychat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {

    TextView  request;
    EditText getName, getPass;
    String name, passwd, data;
    final String PARSER = "`";
    Send_data sd;
    ProgressBar PB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        request = (TextView)findViewById(R.id.request);
        getName = (EditText)findViewById(R.id.getName);
        getPass = (EditText)findViewById(R.id.getPass);
        PB = (ProgressBar)findViewById(R.id.PB);
        PB.setVisibility(View.INVISIBLE);
    }




    public void onClick(View view)
    {

        String temp;

        name = getName.getText().toString();
        passwd = getPass.getText().toString();
        if(name.isEmpty() || passwd.isEmpty())
        {
            request.setText("Заполните все поля!");
            return;
        }


        switch (view.getId())
        {
            case R.id.Enter :             PB.setVisibility(View.VISIBLE);
                                          data = "enter"+PARSER+name+PARSER+passwd;
                                          sd = new Send_data(data, request);
                                          sd.execute();

                                          try {
                                              temp = sd.get().toString();
                                              if (temp.equals("Wellcome!")) {
                                                  Intent intent = new Intent(this, Enter.class);
                                                  intent.putExtra("name", name);
                                                  startActivity(intent);
                                              }
                                          }
                                                  catch(InterruptedException e) {
                                                       e.fillInStackTrace();
                                                     }
                                                  catch (ExecutionException e) {
                                                        e.fillInStackTrace();
                                                         }finally {
                                              sd.isCancelled();
                                          }



                                                    break;


            case R.id.Registration :               data = "registration"+PARSER+name+PARSER+passwd;
                                                   sd = new Send_data(data, request);
                                                   sd.execute();
                                                   sd.isCancelled();
                                                   break;
        }
    }

}
