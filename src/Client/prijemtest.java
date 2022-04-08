/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jakub
 */
public class prijemtest extends Thread {

    private Socket playerSocket;

    public prijemtest(Socket playerSocket) {
        this.playerSocket = playerSocket;
    }

    @Override
    public void run() {
        String[] p = new String[]{null, null, null, null};
        int i = 0;
        while (p[3] != null) {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
                p[i] = in.readLine();
                i++;
            } catch (IOException ex) {
                Logger.getLogger(prijemtest.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(prijemtest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        for (String s : p) {
            System.out.println(s);
        }
    }

}
