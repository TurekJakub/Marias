/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author jakub
 */
public class tesst {

    public static void main(String[] args) throws IOException {
        
        Socket s = new Socket("localhost", 8080);
         BufferedWriter w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        w.write("Tony");
        Scanner ss = new Scanner(System.in);       
      
        while (ss.hasNextLine()) {

            String st = ss.nextLine();
            w.write(st);
            w.newLine();
            w.flush();
        }

    }
    

}
