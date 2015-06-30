package com.example.pinguin_linuxoid.mychat;



import android.os.AsyncTask;
import android.widget.TextView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public  class Send_data extends AsyncTask<Void, Void, String> {

    String input, output;
    DataInputStream in;
    DataOutputStream out;
    TextView view = null;

//
    public Send_data(String _input, TextView _view) {
        input = _input;
        view = _view;
    }
    public Send_data(String _input)
    {
        input = _input;
    }

    @Override
    protected void onPreExecute() {
        super .onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {

        Socket client = null;
        try {
            client = new Socket("93.170.80.138", 6666);

            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            out.writeUTF(input); // отсылаем введенную строку текста серверу.
            out.flush(); // заставляем поток закончить передачу данных.

            output = in.readUTF(); // ждем пока сервер отошлет строку текста

        } catch (UnknownHostException e) {
            output = "Unknown host";
        } catch (IOException e) {
            output = "No connecting to internet! ";
        } finally{
            if(client != null){
                try {
                    client.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
        return output;

    }

    @Override
    protected void onPostExecute(String result) {
        if(view != null)
            view.setText(result);

        super.onPostExecute(result);
    }
}


