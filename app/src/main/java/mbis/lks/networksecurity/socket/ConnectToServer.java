package mbis.lks.networksecurity.socket;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import mbis.lks.networksecurity.socket.listener.DataReceiveListener;
import mbis.lks.networksecurity.socket.listener.DataSendListener;

/**
 * Created by lmasi on 2018. 6. 8..
 */

public class ConnectToServer {

    private Activity mainActivity;

    private String  serverHost = "";
    private int     serverPort = 0;
    private Socket  serverSocket;

    private int     bufferSize = 1000;

    private InputStream inputStream;
    private OutputStream outputStream;

    //for listener
    private DataSendListener    dataSendListener;
    private DataReceiveListener dataReceiveListener;

    public ConnectToServer(Activity activity, String serverHost, int serverPort) throws IOException
    {
        this.mainActivity = activity;
        this.serverHost = serverHost;
        this.serverPort = serverPort;

//      new ConnectServer().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new ConnectServer().execute();
    }


    public void send(String message)
    {
        new SendDataToServer().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, message);
    }

    public void receive()
    {
        new ReceiveFromServer().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void setOnDataSendListener(DataSendListener dataSendListener)
    {
        this.dataSendListener = dataSendListener;
    }

    public void setOnDataReceiveListener(DataReceiveListener dataReceiveListener)
    {
        this.dataReceiveListener = dataReceiveListener;
    }


    //server connect part
    private class ConnectServer extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {

            try
            {
                serverSocket = new Socket(serverHost, serverPort);

                inputStream = serverSocket.getInputStream();
                outputStream = serverSocket.getOutputStream();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(serverSocket == null || inputStream == null || outputStream == null)
            {
                Toast.makeText(mainActivity.getApplicationContext(), "server connection error", Toast.LENGTH_SHORT).show();
                mainActivity.finish();
            }

            receive();
        }
    }

    //data send part
    private class SendDataToServer extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... strings)
        {
            try {
                byte[] data = strings[0].getBytes();
                outputStream.write(data, 0, data.length);

                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(dataSendListener != null)
                dataSendListener.sendData(aBoolean);
        }
    }


    //data receive part
    private class ReceiveFromServer extends AsyncTask<Void, Void, String>
    {
        private byte[] trimByte(byte[] data, int length)
        {
            byte[] returnValue = new byte[length];

            for(int i=0; i<length; i++)
                returnValue[i] = data[i];

            return returnValue;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                byte[] data = new byte[bufferSize];

                int length = inputStream.read(data, 0, bufferSize);
                data = trimByte(data, length);

                return new String(data);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {

            if(s == null)
                return;

            if(dataReceiveListener != null)
                dataReceiveListener.receiveData(s);
        }
    }

    public void setBufferSize(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }
}
