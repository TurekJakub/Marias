/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
    private Receiver receiver;
    private Player[] players;
    Sender sender;
    GameManager manager;

    public Game(int port) {

        try {
            server = new ServerSocket(port);
            playerSockets = connectPlayers();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        receiver = new Receiver();
        sender = new Sender(playerSockets);
        players = new Player[]{new Player(0, "Tony"), new Player(0, "Tony1"), new Player(0, "Tony2"), new Player(0, "Tony3")};
        manager = new GameManager();

    }

    private Socket[] connectPlayers() throws IOException {
        Socket[] sockets = new Socket[4];
        for (int i = 0; i < sockets.length; i++) {
            sockets[i] = server.accept();

        }
        return sockets;
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

    public static void main(String[] args) {
        Game g = new Game(8080);
        g.start();
    }

}
