/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import shared.Card;

/**
 *
 * @author jakub
 */
public class Sender {

    private Socket[] clientSockets;

    public Sender(Socket[] clientSockets) {
        this.clientSockets = clientSockets;
    }

    public void sendData(String data, Socket sender) throws IOException {
        for (int i = 0; i < clientSockets.length; i++) {

            if (!clientSockets[i].equals(sender)) {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSockets[i].getOutputStream()));

                out.write(data);
                out.newLine();
                out.flush();
            }

        }

    }

}
