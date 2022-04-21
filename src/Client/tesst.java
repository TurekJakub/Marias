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
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jakub
 */
public class tesst {

    public static void main(String[] args) throws IOException, InterruptedException {
        ExistingGameFinder f = new ExistingGameFinder();
        f.start();
        Thread.sleep(4000);
        f.interrupt();
        List<String[]> p = f.getExistingGames();
        String[] i =  p.get(0);
     
        System.out.print(i.length);
        int ii = Integer.getInteger(i[1]);
        Socket s = new Socket("localhost",ii);
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        
        Scanner ss = new Scanner(System.in);       
      
        while (ss.hasNextLine()) {

            String st = ss.nextLine();
            w.write(st);
            w.newLine();
            w.flush();
        }

    }
    

}
