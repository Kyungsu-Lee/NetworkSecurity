package mbis.lks.networksecurity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;

import javax.crypto.SecretKey;

import mbis.lks.networksecurity.jce.Example;
import mbis.lks.networksecurity.jce.GenerateTimeStamp;
import mbis.lks.networksecurity.jce.aes.AESAlgorithm;
import mbis.lks.networksecurity.jce.aes.AESSecretKey;
import mbis.lks.networksecurity.jce.des.DESAlgorithm;
import mbis.lks.networksecurity.jce.des.DESSecretKey;
import mbis.lks.networksecurity.jce.rsa.RSAAlgorithm;
import mbis.lks.networksecurity.jce.rsa.RSASecretKey;
import mbis.lks.networksecurity.json.JsonParser;
import mbis.lks.networksecurity.socket.ConnectToServer;
import mbis.lks.networksecurity.socket.listener.DataReceiveListener;
import mbis.lks.networksecurity.socket.listener.DataSendListener;
import mbis.lks.networksecurity.util.ListViewAdaptor;
import mbis.lks.networksecurity.util.UserIDItem;

public class MainActivity extends AppCompatActivity {

    String base;
    TextView textView;
    EditText editTextMessage;

    Button sendRSAButton;
    Button sendDESButton;
    Button sendAESButton;

    ConnectToServer server;

    private boolean getUUIDs = false;
    private boolean init_UUID = true;

    private String myUUID = "";

    private AESSecretKey aesSecretKey;
    private DESSecretKey desSecretKey;
    private RSASecretKey rsaSecretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMessage = findViewById(R.id.edit_text_message);
        textView = findViewById(R.id.text);

        sendRSAButton = findViewById(R.id.sendButtonwithRSA);
        sendDESButton = findViewById(R.id.sendButtonwithDES);
        sendAESButton = findViewById(R.id.sendButtonwithAES);

        Log.e("time", GenerateTimeStamp.generate());

        //for encryption
        desSecretKey = new DESSecretKey();
        aesSecretKey = new AESSecretKey();
        rsaSecretKey = new RSASecretKey();

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
                    server.receive();

                    receiveDataWithJson(data);
                }
            });

            sendRSAButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = editTextMessage.getText().toString();
                    JsonParser jsonParser = new JsonParser();
                    String encryptedText = RSAAlgorithm.encrpytedAsBase64(message, rsaSecretKey.getPublicKey());

                    jsonParser.add("command", "Send To")
                            .add("FROM", myUUID)
                            .add("ID", ((TextView)findViewById(R.id.sendUUID)).getText().toString())
                            .add("message", encryptedText)
                            .add("encrypt", "RSA")
                            ;
                    server.send(jsonParser.toString());


                    editTextMessage.setText("");
                }
            });

            sendAESButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = editTextMessage.getText().toString();
                    JsonParser jsonParser = new JsonParser();
                    String encryptedText = AESAlgorithm.encrpytedAsBase64(message, aesSecretKey.getKey());

                    jsonParser.add("command", "Send To")
                            .add("FROM", myUUID)
                            .add("ID", ((TextView)findViewById(R.id.sendUUID)).getText().toString())
                            .add("message", encryptedText)
                            .add("encrypt", "AES")
                    ;
                    server.send(jsonParser.toString());


                    editTextMessage.setText("");
                }
            });

            sendDESButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = editTextMessage.getText().toString();
                    JsonParser jsonParser = new JsonParser();
                    String encryptedText = DESAlgorithm.encrpytedAsBase64(message, desSecretKey.getKey());

                    jsonParser.add("command", "Send To")
                            .add("FROM", myUUID)
                            .add("ID", ((TextView)findViewById(R.id.sendUUID)).getText().toString())
                            .add("message", encryptedText)
                            .add("encrypt", "DES")
                    ;
                    server.send(jsonParser.toString());


                    editTextMessage.setText("");
                }
            });


//            findViewById(R.id.requestButton).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    JsonParser jsonParser = new JsonParser();
//                    jsonParser.add("command", "Request ID");
//                    server.send(jsonParser.toString());
//                    jsonParser.add("command", "Request My ID");
//                    server.send(jsonParser.toString());
//                }
//            });


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


            //Request ID Command
            if(parser.get("command").equals("Request ID"))
            {

                Spinner spinner = findViewById(R.id.spinner);
                Log.e("Request ID", parser.get("UUIDs"));

                String[] ids = parser.get("UUIDs").split(",");


                //set spinner adapter
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_spinner_item,
                        new ArrayList<String>());

                //add id
                for(String id : ids)
                    if(!id.equals(""))
                        stringArrayAdapter.add(id);

                //set style of spinner
                stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(stringArrayAdapter);

                //item selection event
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("clicked", position +"");
                        ((TextView)findViewById(R.id.sendUUID)).setText(parent.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            //Request My ID Command
            if(parser.get("command").equals("Request My ID"))
            {
                myUUID = parser.get("UUID");
                ((TextView)findViewById(R.id.myUUID)).setText(parser.get("UUID"));
                Log.e("Request My ID", parser.get("UUID"));
            }

            //Send To Command
            if(parser.get("command").equals("Send To"))
            {
                JsonParser jsonParser = JsonParser.parse(message);

                String encrpytionMethod = jsonParser.get("encrypt");

                String decryptedText = "";

                if(encrpytionMethod.equals("RSA"))
                {
                    String encryptedText = jsonParser.get("message");
                    decryptedText = RSAAlgorithm.decryptBase64AsString(encryptedText, rsaSecretKey.getPrivateKey());
                }

                else if(encrpytionMethod.equals("AES"))
                {
                    String encryptedText = jsonParser.get("message");
                    decryptedText = AESAlgorithm.decryptBase64AsString(encryptedText, aesSecretKey.getKey());
                }

                else if(encrpytionMethod.equals("DES"))
                {
                    String encryptedText = jsonParser.get("message");
                    decryptedText = DESAlgorithm.decryptBase64AsString(encryptedText, desSecretKey.getKey());
                }

                textView.setText(decryptedText);
            }

            //public key distribution
            if(parser.get("command").equals("request public key"))
            {
                server.send(new JsonParser()
                        .add("command", "Request Key")
                        .add("ID", myUUID)
                        .add("key", desSecretKey.toString())
                        .toString()
                );
                Log.e("send key", desSecretKey.toString());
            }
        }
        catch (Exception e)
        {
            textView.setText(message);
            Log.e("message", "not a json");
        }
    }


}
