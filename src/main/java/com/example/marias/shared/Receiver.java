/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.marias.shared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.marias.game.Game;

/**
 *
 * @author jakub
 */
public class Receiver  {
    private static final Logger logger = LogManager.getLogger(Receiver.class);
    public Receiver() {

    }

    public Object read(Socket playerSocket) {

        ObjectInputStream in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(playerSocket.getInputStream());

            try {
                o = in.readObject();
            } catch (ClassNotFoundException ex) {
                logger.fatal("Failed to serialize received data. Err: " + ex.getMessage());
            }
            return o;
        } catch (IOException ex) {
            logger.fatal("Connection error occurred while receiving data. Err: " + ex.getMessage());
        }
        return o;
    }

}
