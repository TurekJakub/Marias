/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.marias.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.marias.shared.Card;
import com.example.marias.shared.CardManager;
import com.example.marias.shared.Receiver;
import com.example.marias.shared.Sender;

import javafx.application.Platform;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @brief Represents the client-side controller for a player in the Mariáš game.
 *
 *        The Client class is responsible for managing the player's network
 *        connection to the server,
 *        handling communication, GUI updates, and overall orchestration of the
 *        client's game state and actions.
 * 
 *        This class follows the singleton pattern, ensuring only one client
 *        instance exists per process.
 * 
 * @author jakub
 * 
 * @see GameScreen
 * @see GameScreenController
 * @see com.example.marias.shared.Sender
 * @see com.example.marias.shared.Receiver
 */
public class Client extends Thread {
     /**
     * @brief The logger instance for client-side logging.
     */
    private static final Logger logger = LogManager.getLogger(Client.class);

    /**
     * @brief The singleton client instance.
     */
    private static Client client;

    /**
     * @brief Socket used for communication with the game server.
     */
    private Socket clientSocket;

    /**
     * @brief List of player names currently connected to the game.
     */
    private List<String> playersNames;

    /**
     * @brief This player's name.
     */
    private final String name;

    /**
     * @brief Responsible for sending data to the server.
     *
     * @see Sender
     */
    private final Sender sender;

    /**
     * @brief Responsible for receiving data from the server.
     *
     * @see Receiver
     */
    private final Receiver receiver;

    /**
     * @brief GUI screen for the game interface.
     */
    private final GameScreen gameScreen;

    /**
     * @brief Manages various screens/dialogs in the UI.
     */
    private final ScreenManager screenManager;

    /**
     * @brief The last card played in the current round.
     */
    private Card lastCard;

    /**
     * @brief Card that the player has chosen to play with.
     */
    private Card playWith;

    /**
     * @brief List of cards currently held by the player.
     */
    private List<Card> cards;

    /**
     * @brief String representing the trump card color for the game
     * 
     * @see CardManger
     */
    private String trumphColor;

    /**
     * @brief Controller for the main game screen.
     */
    GameScreenController controller;

    /**
     * @brief Private constructor for Client.
     *
     *        Connects to the server and initializes the client-side networking
     *        components.
     *
     * @param address The server's address.
     * @param port    The port number on which the server is accepting connections.
     * @param name    The player's name.
     * @param cont    Controller for the game screen.
     */
    private Client(String address, int port, String name, GameScreenController cont) {
        try {
            this.clientSocket = new Socket(address, port);
        } catch (IOException ex) {
            logger.warn("Attempted an action with a null player.");
        }
        this.name = name;
        receiver = new Receiver();
        sender = new Sender();
        controller = cont;
        playersNames = new ArrayList<>();
        lastCard = null;
        gameScreen = new GameScreen();
        screenManager = new ScreenManager();

    }

    /**
     * @brief Returns (or creates if needed) the singleton Client instance.
     *
     * @param address Server address.
     * @param port    Server port.
     * @param name    Player's name.
     * @param cont    Game screen controller.
     * @return The singleton Client instance.
     */
    public static Client getClientInstance(String address, int port, String name, GameScreenController cont) {
        if (client == null) {
            client = new Client(address, port, name, cont);
        }
        return client;

    }

    /**
     * @brief Returns the existing client singleton instance.
     *
     * @return The singleton Client, or null if not yet initialized.
     */
    public static Client getClientInstance() {
        return client;
    }

    /**
     * @brief Sends an object to the server using the Sender.
     *
     * @param Data The object to send (may be a command, game state, etc).
     * @throws IOException if sending fails.
     */
    public void sendData(Object Data) throws IOException {

        sender.SingelsendData(Data, clientSocket);

    }

    /**
     * @brief Initializes the client after connecting to the server.
     *
     *        Sends the player's name, receives current players, and initializes the
     *        hand.
     *
     * @throws IOException if communication fails.
     */
    private void initialize() throws IOException {
        sender.SingelsendData(name, clientSocket);
        playersNames = (List<String>) receiver.read(clientSocket);
        logger.info("There are currently " + playersNames.size() + " player in the lobby.");
        cards = (List<Card>) receiver.read(clientSocket);

        Platform.runLater(
                () -> {
                    controller.initializePlayersInfo(playersNames, playersNames.indexOf(name));
                    controller.dealCards(gameScreen.getImagesStreams(cards), cards);
                });

    }

    /**
     * @brief Main thread game session thread logic.
     * 
     *        Handles interaction between server and player via GUI
     */
    @Override
    public void run() {
        try {
            initialize();
        } catch (IOException ex) {
            logger.warn(ex.getMessage());
        }

        int playedRound = 0;
        String roundColor = null;
        while (playedRound < 8) {

            try {
                Object data = receiver.read(clientSocket);
                if (data.getClass() == Class.forName("java.lang.Boolean")) {
                    if (trumphColor == null) {
                        Platform.runLater(
                                () -> {
                                    try {
                                        screenManager.showDialog("playWithDialog.fxml");
                                        screenManager.showDialog("trumphDialog.fxml");
                                    } catch (IOException ex) {
                                        screenManager.showExceptio(ex);
                                    }
                                });
                    } else {
                        controller.activateAll();
                    }

                }
                if (data.getClass() == Class.forName("com.example.marias.shared.Card")) {
                    Card c = (Card) data;
                    if (Arrays.asList(CardManager.COLORS_OF_CARDS).contains(c.getColor())) {
                        lastCard = c;
                        if (roundColor == null) {
                            roundColor = c.getColor();
                        }
                        Platform.runLater(
                                () -> {
                                    showPlayedCard(c);

                                });

                    } else {
                        Platform.runLater(
                                () -> {
                                    setPlayWith(c, playersNames.get(4));

                                });
                    }

                }
                if (data.getClass() == Class.forName("java.lang.Integer")) {
                    int points = (int) data;
                    String name = (String) receiver.read(clientSocket);
                    Platform.runLater(
                            () -> {
                                updatePoints(name, points);
                                controller.reset();

                            });
                    roundColor = null;
                    playedRound++;
                }
                if (data.getClass() == Class.forName("java.lang.String")) {
                    if (trumphColor != null) {
                        Platform.runLater(
                                () -> {
                                    controller.updateSateLabel((String) data);
                                });
                    } else {
                        trumphColor = (String) data;
                        Platform.runLater(
                                () -> {
                                    controller.setTrumphColor(trumphColor);
                                });

                    }
                }

            } catch (ClassNotFoundException ex) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        screenManager.showExceptio(ex);
                    }
                });

            }

        }

    }

    /**
     * @brief Determines and activates playable cards based on the current game
     *        state.
     *
     * @param roundColor The color/suit for the current round.
     * @param lastColor  The color/suit of the last card played.
     * @param lastValue  The value/rank of the last card played.
     * @param cards      The list of cards in the player's hand.
     *
     * @see GameScreen#findPlayableCards(String, String, List)
     * @see GameScreenController#activatePlayable(boolean[])
     */
    public void activatePlayableCards(String roundColor, String lastColor, String lastValue, List<Card> cards) {
        boolean[] indexes = null;
        boolean oneActivate = false;

        if (lastColor.equals(roundColor)) {
            indexes = gameScreen.findPlayableCards(roundColor, lastValue, cards);
        }
        if (Arrays.asList(indexes).indexOf(true) != -1) {
            if (lastColor.equals(trumphColor)) {
                indexes = gameScreen.findPlayableCards(trumphColor, lastValue, cards);
            } else {
                indexes = gameScreen.findPlayableCards(trumphColor, "O", cards);
            }
        }
        if (!oneActivate) {
            Arrays.fill(indexes, true);
        }
        controller.activatePlayable(indexes);
    }

    /**
     * @brief Make all cards in the GUI unplayable until the player's next turn.
     *
     * @see GameScreenController#deactivateAllCards()
     */
    public void waitUntilNextTurn() {
        controller.deactivateAllCards();
    }

    /**
     * @brief Shows the specified card as played in the GUI.
     *
     * @param playedCard The card to be shown as played.
     * @see GameScreenController#showPlayedCard(java.io.InputStream)
     */
    public void showPlayedCard(Card playedCard) {
        controller.showPlayedCard(gameScreen.getImageInputStream(playedCard));

    }

    /**
     * @brief Updates the displayed points for a player.
     *
     * @param playerName Name of the player whose score is updated.
     * @param points     Number of points to be set.
     * @see GameScreenController#updatePoints(String, int)
     */
    public void updatePoints(String playerName, int points) {
        controller.updatePoints(playerName, points);
    }

    /**
     * @brief Updates the UI to reflect the "play with" card selection.
     *
     * @param playWith   The card chosen to play with.
     * @param playerName The name of the player making the selection.
     * @see GameScreenController#setPlayWith(Card, String)
     */
    public void setPlayWith(Card playWith, String playerName) {
        controller.setPlayWith(playWith, playerName);
    }

}
