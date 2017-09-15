package desimp;

/**
 *
 * @author Paul
 */
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class DES {

    public SecretKey key;
    public String plaintext;
    public byte[] cipherText;
    public Cipher myDES;

    public DES() throws NoSuchAlgorithmException, NoSuchPaddingException {
        myDES = Cipher.getInstance("DES");
    }

    public byte[] encrypt(String plaintextin, SecretKey keyin) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("entered the encrypt algorithm");
        plaintext = plaintextin;
        key = keyin;
        myDES.init(Cipher.ENCRYPT_MODE, key);
        // byte[] text = "This is a test message".getBytes();
        cipherText = myDES.doFinal(plaintext.getBytes());
        
        return cipherText;
    }

    public byte[] decrypt(byte[] ciphertextin, SecretKey keyin) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("entered the decrpt algorithm");
       cipherText = ciphertextin;
       key = keyin;
        
        System.out.println(key);
        myDES.init(Cipher.DECRYPT_MODE, key); //crashes here
        byte[] decryptedPlaintext = myDES.doFinal(cipherText);
        return decryptedPlaintext;
    }
}
