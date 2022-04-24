/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import shared.Sender;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import shared.Card;
import shared.CardManager;
import shared.Receiver;

/**
 *
 * @author jakub
 */
public class Client extends Thread {

    private final Socket clientSocket;
    private ArrayList<String> playersNames;
    private final String name;
    private final Sender sender;
    private final Receiver receiver;
    private final GameScreen gameScreen;
    private Card lastCard;
    private final CardManager manager;

    public Client(String address, int port, String name, GameScreenController cont) throws IOException {
        this.clientSocket = new Socket(address, port);
        this.name = name;
        receiver = new Receiver();
        sender = new Sender();
        manager = new CardManager();
        gameScreen = new GameScreen(null, cont);
        playersNames = new ArrayList<>();
        lastCard = null;

    }

    private void initialize() {
        sender.SingelsendData(name, clientSocket);
        playersNames = (ArrayList<String>) receiver.read(clientSocket);
        gameScreen.setCards((List<Card>) receiver.read(clientSocket));
        for (String s : playersNames) {
            System.err.println(s);
        }
        Platform.runLater(
                () -> {
                    gameScreen.initializePlayersInfo(playersNames, playersNames.indexOf(name));
                    gameScreen.dealCards();
                }
        );

    }

    @Override
    public void run() {
        initialize();
        String roundColor = null;
        int playedRound = 0;
        while (playedRound < 8) {
            try {
                Object data = receiver.read(clientSocket);
                if (data.getClass() == Class.forName("java.lang.Boolean")) {
                    if (gameScreen.gettrumphColor() == null) {
                        Platform.runLater(
                                () -> {
                                    gameScreen.showDialog(gameScreen, "trumphDialog.fxml");
                                    gameScreen.showDialog(gameScreen, "playWithDialog.fxml");
                                }
                        );

                        sender.SingelsendData(gameScreen.gettrumphColor(), clientSocket);
                        sender.SingelsendData(gameScreen.playWith, clientSocket);
                    }
                    if (roundColor == null) {
                        Platform.runLater(
                                () -> {
                                    gameScreen.activatePlayableCards("0", "0", "0");
                                }
                        );

                    } else {

                        Platform.runLater(
                                () -> {

                                    gameScreen.activatePlayableCards(roundColor, lastCard.getColor(), lastCard.getValue());
                                }
                        );

                    }
                }
                if (data.getClass() == Class.forName("shared.Card")) {
                    Card c = (Card) data;
                    if (Arrays.asList(manager.getCOLORS_OF_CARDS()).contains(c.getColor())) {
                        lastCard = c;
                        Platform.runLater(
                                () -> {
                                    gameScreen.showPlayedCard(c);
                                }
                        );

                    } else {
                        Platform.runLater(
                                () -> {
                                    gameScreen.setPlayWith(c, (String) receiver.read(clientSocket));
                                });
                    }

                }
                if (data.getClass() == Class.forName("java.lang.Integer")) {
                    int points = (int) data;
                    Platform.runLater(
                            () -> {
                                gameScreen.updatePoints((String) receiver.read(clientSocket), points);
                            }
                    );

                    playedRound++;
                }
                if (data.getClass() == Class.forName("java.lang.String")) {
                    Platform.runLater(
                            () -> {
                                gameScreen.setTrumphColor((String) receiver.read(clientSocket));
                            }
                    );

                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            gameScreen.waitUntilNextTurn();

        }
    }

}
