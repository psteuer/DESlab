/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desimp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Paul
 */
public class DesImp {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, UnsupportedEncodingException, IOException {

        System.out.println("Type 1 to encrypt a file or 2 to decrypt file");
        Scanner eordin = new Scanner(System.in);
        String eord = eordin.nextLine();
        if ("1".equals(eord)) { //user wants to encrypt file
            System.out.println("file named plaintext.txt in the project file will be encrypted");
            DES newDES = new DES();
            String plaintext = "";
            //create key
            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey freshkey = keygenerator.generateKey();

            //Read in file
            String line;
            try {
                String filename = "plaintext.txt";
                // FileReader reads text files in the default encoding.
                FileReader fileReader
                        = new FileReader(filename);

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader
                        = new BufferedReader(fileReader);

                while ((line = bufferedReader.readLine()) != null) {
                    //System.out.println(line);
                    plaintext = line;
                }

                // Always close files.
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file '"
                        + "'");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file '"
                        + "'");
            }
            //Now do encryption and then output to file called ciphertext.txt
            byte[] ciphertext = newDES.encrypt(plaintext, freshkey);
            PrintWriter writer = new PrintWriter("ciphertext.txt", "UTF-8");
            String encodedKey = Base64.getEncoder().encodeToString(freshkey.getEncoded()).toString();

            byte[] encodedCiphertext = Base64.getEncoder().encode(ciphertext);
            System.out.println(new String(encodedCiphertext));

            System.out.println(encodedKey);
            writer.println(encodedKey);
            writer.println(new String(encodedCiphertext));
            writer.close();
        } else if ("2".equals(eord)) { //user wants to decrypt file
            System.out.println("File named ciphertext.txt in the project file will be decrypted");
            DES newDES = new DES();
            byte[] ciphertext;
            SecretKey key_formatted;
            String ciphertextin;
            String keyin;
            //read in cipher text and keyin
            try (BufferedReader br = new BufferedReader(new FileReader("ciphertext.txt"))) {
                keyin = br.readLine();
                ciphertextin = br.readLine();
            }
            byte[] decodedKey = Base64.getDecoder().decode(keyin);
            key_formatted = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES/CBC/PKCS5Padding");
            ciphertext = Base64.getDecoder().decode(ciphertextin);
            //now output plaintext to file called plaintext.txt
            String plaintext = newDES.decrypt(ciphertext, key_formatted);
            try (PrintWriter writer = new PrintWriter("plaintext.txt", "UTF-8")) {
                writer.println(plaintext);
            }
        } else {
            System.out.println("Wrong input please Type 1 to encrypt a file or 2 to decrypt file");
        }

    }

}
