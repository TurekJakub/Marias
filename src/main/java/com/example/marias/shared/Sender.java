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
 * @brief Handles sending data objects to one or multiple clients over sockets.
 * 
 *        The Sender class provides utility methods for transmitting serialized
 *        objects. Either to a single recipient or to multiple clients at once
 * 
 * @author jakub
 */
public class Sender {

    private static final Logger logger = LogManager.getLogger(Sender.class);

    /**
     * @brief Default constructor for Sender.
     *
     *        Initializes a new instance of the Sender utility.
     */
    public Sender() {

    }

    /**
     * @brief Sends a data object to all clients except the sender.
     *
     *        Iterates through the array of client sockets and sends the provided
     *        serializable data object
     *        to every socket other than the sender's socket. Used for broadcasting
     *        game events or updates.
     *
     * @param data          The object to be sent to clients (must be serializable).
     * @param sender        The socket of the client who is sending the data
     *                      (excluded from broadcast).
     * @param clientSockets The array of sockets representing all connected clients.
     * @throws IOException if sending data to any socket fails.
     */
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

    /**
     * @brief Sends a data object to a single recipient over a socket.
     *
     *        Serializes and transmits the specified data object to the provided
     *        recipient socket.
     * 
     * @param data    The object to send (must be serializable).
     * @param reciver The receiving socket.
     * @throws IOException if sending data fails.
     */
    public void SingelsendData(Object data, Socket reciver) throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(reciver.getOutputStream());
        out.writeObject(data);
        out.flush();

    }

}
