/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.marias.game;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author jakub
 */
public class ServerInfoSender extends Thread {
    private static final Logger logger = LogManager.getLogger(ServerInfoSender.class);

    private DatagramSocket socket;
    private byte[] buffer;
    private InetAddress address;
    private boolean interupted;
    private final int COOLDOWN;
    private final int port;

    public ServerInfoSender(String message) {
        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
            address = InetAddress.getByName("255.255.255.255");
        } catch (SocketException | UnknownHostException ex) {
            logger.fatal("Failed to initialize the lobby discovery job. Err: " + ex.getMessage());
        }

        buffer = message.getBytes();
        interupted = false;
        COOLDOWN = 2000;
        port = 49152;
    }

    @Override
    public void run() {
        while (!interupted) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            try {
                socket.send(packet);

            } catch (IOException ex) {
                logger.fatal("Failed to broadcast game lobby discovery packet. Err: " + ex.getMessage());
            }
            try {
                Thread.sleep(COOLDOWN);
            } catch (InterruptedException ex) {
                logger.fatal("Failed to cooldown the lobby discovery job after failing. Err: " + ex.getMessage());
            }

        }
        socket.close();
    }

    @Override
    public void interrupt() {
        interupted = true;
    }

}
