/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.marias.shared;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author jakub
 */
public class Sender {
    private static final Logger logger = LogManager.getLogger(Sender.class);

    public Sender() {

    }

    public void MultiSendData(Object data, Socket sender, Socket[] clientSockets) throws IOException {
        for (int i = 0; i < clientSockets.length; i++) {

            if (!clientSockets[i].equals(sender)) {

                ObjectOutputStream out = new ObjectOutputStream(clientSockets[i].getOutputStream());
                out.writeObject(data);
                out.flush();
                logger.info("Data successfully send.");
            }

        }

    }

    public void SingelsendData(Object data, Socket reciver) throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(reciver.getOutputStream());
        out.writeObject(data);
        out.flush();

    }

}
