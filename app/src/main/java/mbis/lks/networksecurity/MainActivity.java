package mbis.lks.networksecurity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

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
            textView.setText(String.format("Key : %s Length : %d", base64EncodedKey, keyBytes.length));

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
        }

    }
}
