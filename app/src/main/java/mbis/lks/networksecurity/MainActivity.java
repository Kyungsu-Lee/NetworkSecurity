package mbis.lks.networksecurity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

import mbis.lks.networksecurity.json.JsonParser;
import mbis.lks.networksecurity.socket.ConnectToServer;
import mbis.lks.networksecurity.socket.listener.DataReceiveListener;
import mbis.lks.networksecurity.socket.listener.DataSendListener;
import mbis.lks.networksecurity.util.ListViewAdaptor;
import mbis.lks.networksecurity.util.UserIDItem;

public class MainActivity extends AppCompatActivity {

    String base;
    TextView textView;
    Button sendButton;
    EditText editTextMessage;

    ConnectToServer server;

    private boolean getUUIDs = false;
    private boolean init_UUID = true;

    private String myUUID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMessage = findViewById(R.id.edit_text_message);
        sendButton = findViewById(R.id.sendButton);

        byte[] result = new byte[10];

        try {

            textView = findViewById(R.id.text);

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

            server = new ConnectToServer(this, "192.168.107.116", 9999);
            server.setOnDataSendListener(new DataSendListener() {
                @Override
                public void sendData(boolean sendResult) {
                    Log.e("result", sendResult + "");

                    if(!sendResult && myUUID.equals(""))
                        server.send(new JsonParser().add("command", "Request My ID").toString());

                    if(sendResult && init_UUID)
                    {
                        init_UUID = false;
                        Log.e("aa", "aa");
                        server.send(new JsonParser().add("command", "Request ID").toString());
                    }

                }
            });
            server.setOnDataReceiveListener(new DataReceiveListener() {
                @Override
                public void receiveData(String data) {
//                    Log.e("dat", data);
//                    textView.setText(data);
                    server.receive();

                    receiveDataWithJson(data);
                }
            });

            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = editTextMessage.getText().toString();
                    JsonParser jsonParser = new JsonParser();
//                    jsonParser.add("command", "To Server");
//                    jsonParser.add("message", message);
//                    server.send(jsonParser.toString());
//                    Log.e("message", jsonParser.toString());

                    jsonParser.add("command", "Send To")
                            .add("ID", ((TextView)findViewById(R.id.sendUUID)).getText().toString())
                            .add("message", message)
                            ;

                    server.send(jsonParser.toString());

                    editTextMessage.setText("");
                }
            });

            findViewById(R.id.requestButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonParser jsonParser = new JsonParser();
                    jsonParser.add("command", "Request ID");
                    server.send(jsonParser.toString());
                    jsonParser.add("command", "Request My ID");
                    server.send(jsonParser.toString());
                }
            });


        }catch (Exception e)
        {

            e.printStackTrace();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("start", "start");
        server.send(new JsonParser().add("command", "Request My ID").toString());
    }

    public void receiveDataWithJson(String message)
    {
        try{

            JsonParser parser = JsonParser.parse(message);

            if(parser.get("command").equals("Request ID"))
            {
                Log.e("Request ID", parser.get("UUIDs"));

                ListView listView = findViewById(R.id.listView);

                String[] ids = parser.get("UUIDs").split(",");

                final ArrayList<UserIDItem> items = new ArrayList<>();

                for(String id : ids)
                    if(!id.equals(""))
                        items.add(new UserIDItem(id));

                ListViewAdaptor listViewAdaptor = new ListViewAdaptor(getApplicationContext(), R.layout.item, items);
                listView.setAdapter(listViewAdaptor);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("clicked", position +"");
                        ((TextView)findViewById(R.id.sendUUID)).setText(items.get(position).getName());
                    }
                });

            }
            if(parser.get("command").equals("Request My ID"))
            {
                ((TextView)findViewById(R.id.myUUID)).setText(parser.get("UUID"));
                Log.e("Request My ID", parser.get("UUID"));
            }
        }
        catch (Exception e)
        {
            textView.setText(message);
            Log.e("message", "not a json");
        }
    }


}
