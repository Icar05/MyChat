package com.example.pinguin_linuxoid.mychat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {

    TextView panda, request;
    EditText getName, getPass;
    String name, passwd, data;
    final String PARSER = "`";
    Send_data sd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        panda =   (TextView)findViewById(R.id.Panda);///
        request = (TextView)findViewById(R.id.request);
        getName = (EditText)findViewById(R.id.getName);
        getPass = (EditText)findViewById(R.id.getPass);
        panda.setText("This is nice panda skype!");
        panda.setTextColor(Color.GREEN);

    }




    public void onClick(View view)
    {

        String temp;

        name = getName.getText().toString();
        passwd = getPass.getText().toString();
        if(name.isEmpty() || passwd.isEmpty())
        {
            Toast.makeText(this, "Запопните все поля!", Toast.LENGTH_SHORT).show();
            return;
        }


        switch (view.getId())
        {
            case R.id.Enter :             data = "enter"+PARSER+name+PARSER+passwd;
                                          sd = new Send_data(data, request);
                                          sd.execute();

                                          try {
                                              temp = sd.get().toString();
                                              if (temp.equals("OK")) {
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
                                                         }
                                                    sd.isCancelled();


                                                    break;


            case R.id.Registration :               data = "registration"+PARSER+name+PARSER+passwd;
                                                   sd = new Send_data(data, request);
                                                   sd.execute();
                                                   sd.isCancelled();
                                                   break;
        }
    }

}
