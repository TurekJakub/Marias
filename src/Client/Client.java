/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import game.GameManager;
import shared.Sender;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import shared.Card;
import shared.Receiver;

/**
 *
 * @author jakub
 */
public class Client extends Thread {

    private final Socket clientSocket;
    private HashMap<String, Integer> playersInfo;
    private final String name;
    private final Sender sender;
    private final Receiver receiver;
    private Card[] cards;
    GameScreen gameScreen;

    public Client(String address, int port, String name, GameScreenController cont) throws IOException {
        this.clientSocket = new Socket(address, port);
        this.name = name;
        receiver = new Receiver();
        sender = new Sender();
        gameScreen = new GameScreen(null, cont);
        GameManager m = new GameManager();
        cards = m.getPlayersCardsSet(0).toArray(new Card[0]);

    }

    private void initialize() throws IOException {
        sender.SingelsendData(name, clientSocket);
        playersInfo = (HashMap<String, Integer>) receiver.read(clientSocket);
        //cards = (Card[]) receiver.read(clientSocket);
    }   

    @Override
    public void run() {
        Card lastCard = null;
        String roundColor = null;
        int playedRound = 0;
        while (playedRound < 1) {
            try {
                Object data = receiver.read(clientSocket);
                if (data.getClass() == Class.forName("java.lang.Boolean")) {
                    if (gameScreen.gettrumphColor() == null) {
                        gameScreen.setTrumphColor("S");
                    }
                    if (roundColor == null) {
                        gameScreen.activatePlayableCards(cards, "0", "0", "0");
                    } else {
                        gameScreen.activatePlayableCards(cards, roundColor, lastCard.getColor(), lastCard.getValue());
                    }
                }
                if (data.getClass() == Class.forName("shared.Card")) {
                    lastCard = (Card) data;
                }
                if (data.getClass() == Class.forName("java.lang.Integer")) {
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
