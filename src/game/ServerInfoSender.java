/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jakub
 */
public class ServerInfoSender extends Thread {

    DatagramSocket socket;
    byte[] buffer;
    InetAddress group;
    boolean interupted;
    private final int COLDOWN;

    public ServerInfoSender(String message) {
        try {
            socket = new DatagramSocket();
            group = InetAddress.getByName("239.0.0.0");
        } catch (SocketException ex) {
            Logger.getLogger(ServerInfoSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerInfoSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        buffer = message.getBytes();
        interupted = false;
        COLDOWN = 2000;
    }

    @Override
    public void run() {
        while (!interupted) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, 4446);
            try {
                socket.send(packet);

            } catch (IOException ex) {
                Logger.getLogger(ServerInfoSender.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(COLDOWN);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerInfoSender.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        socket.close();
    }

    @Override
    public void interrupt() {
        interupted = true;
    }

}
