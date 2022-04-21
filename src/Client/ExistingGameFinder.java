/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jakub
 */
public class ExistingGameFinder extends Thread {
    
    private final byte[] buffer;
    private final List<String[]> existingGames;
    MulticastSocket socket;
    InetAddress group;
    boolean interupted;
    
    public ExistingGameFinder() {
        try {
            socket = new MulticastSocket(9999);
            group = InetAddress.getByName("230.0.0.0");
        } catch (IOException ex) {
            
        }
        existingGames = new ArrayList<>();
        buffer = new byte[1024];
        interupted = false;
    }
    
    @Override
    public void run() {
        
        try {
            socket.joinGroup(group);
        } catch (IOException ex) {
            Logger.getLogger(ExistingGameFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (!interupted) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                Logger.getLogger(ExistingGameFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = new String(packet.getData(), 0, packet.getLength());
            System.err.println(s);
            String[] parametrs = s.split(":");
            if (!existingGames.contains(parametrs)) {
                existingGames.add(parametrs);
            }
            System.out.print(existingGames.size());
        }
        socket.close();
    }
    
    public synchronized List<String[]> getExistingGames() {
        return existingGames;
    }
    
    @Override
    public synchronized void interrupt() {
        interupted = true;
    }

    public static void main(String[] args) {
        ExistingGameFinder f = new ExistingGameFinder();
        f.start();
    }
    
}
