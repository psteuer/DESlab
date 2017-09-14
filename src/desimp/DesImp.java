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
            //byte[] data = freshkey.getEncoded();
            //System.out.println(data);
            //writer.println(data); // how to print key TODO!
            String encodedKey = Base64.getEncoder().encodeToString(freshkey.getEncoded()).toString();
            System.out.println(encodedKey); //how do we want this? in byte form or in ascii - ascii might be harder but reading in an formatted array of bytes is harder
            writer.println(encodedKey);
            writer.println(ciphertext);
            //close file

        } else if ("2".equals(eord)) { //user wants to decrypt file
            System.out.println("File named ciphertext.txt in the project file will be decrypted");
            DES newDES = new DES();
            String ciphertext;
            SecretKey keyin;

            //read in cipher text and keyin
            //now output plaintext to file called plaintext.txt
            //byte[] plaintext = newDES.decrypt(ciphertextin, keyin);
            PrintWriter writer = new PrintWriter("plaintext.txt", "UTF-8");
            // writer.println(plaintext.toString());
        } else {
            System.out.println("Wrong input please Type 1 to encrypt a file or 2 to decrypt file");
        }

        PrintWriter writer = new PrintWriter("ciphertext.txt", "UTF-8");
        writer.println();

    }

}
