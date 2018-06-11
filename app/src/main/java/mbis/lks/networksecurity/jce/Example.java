package mbis.lks.networksecurity.jce;

import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import mbis.lks.networksecurity.jce.des.DESAlgorithm;
import mbis.lks.networksecurity.jce.des.DESSecretKey;
import mbis.lks.networksecurity.jce.rsa.RSAAlgorithm;
import mbis.lks.networksecurity.jce.rsa.RSASecretKey;

/**
 * Created by lmasi on 2018. 6. 10..
 */

public class Example {

    public Example()
    {
//        try{
//
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//            keyPairGenerator.initialize(1024);
//            KeyPair keyPair = keyPairGenerator.generateKeyPair();
//
//            PublicKey publicKey = keyPair.getPublic();
//            PrivateKey privateKey = keyPair.getPrivate();
//
//
//            Cipher desCipher;
//
//            // Create the cipher
//            desCipher = Cipher.getInstance("RSA");
//
//            // Initialize the cipher for encryption
//            desCipher.init(Cipher.ENCRYPT_MODE, publicKey);
//
//            //sensitive information
//            byte[] text = "helmjjjjklo World".getBytes();
//
//            Log.e("original text to byte", "Text [Byte Format] : " + text);
//            Log.e("original text", "Text : " + new String(text));
//
//            // Encrypt the text
//            byte[] textEncrypted = desCipher.doFinal(text);
//
//            Log.e("encrypted byte", "Text Encryted : " + textEncrypted);
//            Log.e("encrypted text", "Text : " + new String(textEncrypted));
//
//            String base = Base64.encodeToString(textEncrypted, Base64.NO_WRAP);
//            Log.e("base", base);
//
//            byte[] b = Base64.decode(base, Base64.NO_WRAP);
//
//            // Initialize the same cipher for decryption
//            desCipher.init(Cipher.DECRYPT_MODE, privateKey);
//
//            // Decrypt the text
//            byte[] textDecrypted = desCipher.doFinal(b);
//
//            Log.e("decrypted text", "Text Decryted : " + new String(textDecrypted));
//
//        }
//        catch (NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        }
//
//        DESSecretKey secretKey = new DESSecretKey();
//        String message = "hello world";
//        String encryptedText = DESAlgorithm.encrpytedAsBase64(message, secretKey.getKey());
//        Log.e("encrypted text", encryptedText);
//        String decryptedText = DESAlgorithm.decryptBase64AsString(encryptedText, secretKey.getKey());
//        Log.e("decrypted text", decryptedText);

//        RSASecretKey rsaSecretKey = new RSASecretKey();
//        String message = "hello";
//        String encryptedText = RSAAlgorithm.encrpytedAsBase64(message, rsaSecretKey.getPublicKey());
//        Log.e("encrypted", encryptedText);
//        String decryptedText = RSAAlgorithm.decryptBase64AsString(encryptedText, rsaSecretKey.getPrivateKey());
//        Log.e("decrypted", decryptedText);


    }
}
