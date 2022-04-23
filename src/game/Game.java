/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import shared.Sender;
import shared.Receiver;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.Card;

/**
 *
 * @author jakub
 */
public class Game extends Thread {

    private ServerSocket server;
    private Socket[] playerSockets;
    private final Receiver receiver;
    private Player[] players;

    Sender sender;
    GameManager manager;
    ServerInfoSender infoSender;

    public Game(int port, String name) throws UnknownHostException, IOException {

        String message = name + ":" + String.valueOf(port) + ":" + InetAddress.getLocalHost().getHostAddress().trim();
        infoSender = new ServerInfoSender(message);
        infoSender.start();
        try {
            server = new ServerSocket(port);
            connectPlayers();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        receiver = new Receiver();
        sender = new Sender();
        players = new Player[]{new Player(0, null), new Player(0, null),
            new Player(0, null), new Player(0, null)};
        manager = new GameManager();
        //  System.out.println(receiver.read(playerSockets[0]));
        boolean b = true;
        sender.SingelsendData(b, playerSockets[0]);
        
    }

    private void connectPlayers() throws IOException {

        Socket[] sockets = new Socket[1];
        for (int i = 0; i < sockets.length; i++) {
            sockets[i] = server.accept();

            System.err.println("Join");

        }
        infoSender.interrupt();
        playerSockets = sockets;
    }

    public void startGameLoop() throws IOException {

        Card[] playedCards = new Card[4];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < playerSockets.length; j++) {
                playedCards[j] = receiver.readCard(playerSockets[j]);
                //  players[i].updateCards(playedCards[i]);
                sender.sendData(playedCards[j].toString(), playerSockets[j]);
            }
            int indexOfWin = manager.getWinerOfRound(playedCards, "L");
            players[indexOfWin].updatePoints(manager.getRoundPoints(playedCards));
            sender.sendData(players[indexOfWin].toString(), null);
            System.out.print(players[indexOfWin].toString());

        }
        sender.sendData(players[manager.getGameWinner(players)].toString(), null);
        System.out.println(players[manager.getGameWinner(players)].toString());

    }

    @Override
    public void run() {
        try {
            startGameLoop();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Game g = new Game(8081, "8");

    }

}
