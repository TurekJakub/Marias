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
import java.util.ArrayList;
import java.util.List;
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
    private final Player[] players;
    private String trumphColor;
    private Card playWith;
    Sender sender;
    GameManager manager;
    ServerInfoSender infoSender;
    
    public Game(int port, String name) throws UnknownHostException {
        
        String message = name + ":" + String.valueOf(port) + ":" + InetAddress.getLocalHost().getHostAddress().trim();
        infoSender = new ServerInfoSender(message);
        infoSender.start();
        try {
            server = new ServerSocket(port);
            
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        connectPlayers();
        receiver = new Receiver();
        sender = new Sender();
        players = new Player[]{new Player(0, null), new Player(0, null),
            new Player(0, null), new Player(0, null)};
        
        trumphColor = null;
        
    }
    
    private void connectPlayers() {
        
        Socket[] sockets = new Socket[4];
        for (int i = 0; i < sockets.length; i++) {
            try {
                sockets[i] = server.accept();
            } catch (IOException ex) {
                continue;
            }
            
            System.err.println("Join");
            
        }
        infoSender.interrupt();
        playerSockets = sockets;
    }
    
    private void setAndSendNames() throws IOException {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String name = (String) receiver.read(playerSockets[i]);
            players[i].setName(name);
            names.add(name);
            
        }
        for (String s : names) {
            System.out.println(s);
        }
        sender.MultisendData(names, null, playerSockets);
    }
    
    private void sendPlayersCards() {
        for (int i = 0; i < 4; i++) {
            players[i].setCards(manager.getPlayersCardsSet(i));
            sender.SingelsendData(players[i].getCards(), playerSockets[i]);
        }
        
    }
    
    private void initialize() {
        try {
            setAndSendNames();
            sendPlayersCards();
            boolean b = true;
            playWith = (Card) receiver.read(playerSockets[0]);
            sender.SingelsendData(b, playerSockets[0]);
            trumphColor = (String) receiver.read(playerSockets[0]);
            manager = new GameManager(trumphColor);            
            sender.MultisendData(trumphColor, playerSockets[0], playerSockets);
            sender.MultisendData(playWith, playerSockets[0], playerSockets);
            sender.MultisendData(players[0].getName(), playerSockets[0], playerSockets);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private List<Card> playOneRound() {
        List<Card> playedCards = new ArrayList<>();
        for (int i = 0; i < playerSockets.length; i++) {
            playedCards.set(i, (Card) receiver.read(playerSockets[i]));
            players[i].updateCards(playedCards.get(i));
            try {
                sender.MultisendData(playedCards.get(i), playerSockets[i], playerSockets);
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return playedCards;
    }
    
    private void evaluateRoud(List<Card> playedCards) {
        
        int indexOfWin = manager.getWinerOfRound(playedCards, 4);
        int points = manager.getRoundPoints(playedCards);
        players[indexOfWin].updatePoints(points);
        try {
            sender.MultisendData(points, null, playerSockets);
            sender.MultisendData(players[indexOfWin].getName(), null, playerSockets);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void startGameLoop() throws IOException {
        
        for (int i = 0; i < 8; i++) {
            List<Card> cards = playOneRound();
            evaluateRoud(cards);
        }
        sender.MultisendData(players[manager.getGameWinner(players)].toString(), null, playerSockets);
        
    }
    
    @Override
    public void run() {
        initialize();
        try {
            startGameLoop();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Game g = new Game(8081, "8");
        g.start();
        
    }
    
}
