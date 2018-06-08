package mbis.lks.networksecurity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import org.apache.commons.codec.binary.Base64;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

import mbis.lks.networksecurity.socket.ConnectToServer;
import mbis.lks.networksecurity.socket.listener.DataReceiveListener;
import mbis.lks.networksecurity.socket.listener.DataSendListener;

public class MainActivity extends AppCompatActivity {

    String base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        byte[] result = new byte[10];

        try {

            TextView textView = findViewById(R.id.text);

            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256);

            Key key = generator.generateKey();


            byte[] keyBytes = key.getEncoded();
            String base64EncodedKey = android.util.Base64.encodeToString(keyBytes, android.util.Base64.DEFAULT);
            base = new String(base64EncodedKey);
            textView.setText(String.format("Key : %s Length : %d", base64EncodedKey, keyBytes.length));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            ConnectToServer server = new ConnectToServer("192.168.107.116", 9999);
            server.setOnDataSendListener(new DataSendListener() {
                @Override
                public void sendData(boolean sendResult) {
                    Log.e("result", sendResult + "");
                }
            });
            server.setOnDataReceiveListener(new DataReceiveListener() {
                @Override
                public void receiveData(String data) {
                    Log.e("recieved data", data);
                }
            });
            server.send("hello");
            server.receive();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
