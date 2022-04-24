/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shared.Card;
import shared.CardManager;

/**
 *
 * @author jakub
 */
public class GameScreen {
    
    GameScreenController controller;
    CardManager manager;
    String trumphColor;
    Card playWith;
    List<Card> cards;
    
    public GameScreen(String trumphColor, GameScreenController controller) {
        this.trumphColor = trumphColor;
        this.controller = controller;
        manager = new CardManager();
        cards = new ArrayList<>();
    }
    
    public void waitUntilNextTurn() {
        controller.deactivateAllCards();
    }
    
    public void activatePlayableCards(String roundColor, String lastColor, String lastValue) {
        
        boolean oneActivate = false;
        if (lastColor.equals(roundColor)) {
            findPlayableCards(roundColor, lastColor, lastValue);
        }
        if (lastColor.equals(trumphColor) || !oneActivate) {
            findPlayableCards(trumphColor, lastColor, lastValue);
        }
        if (!oneActivate) {
            controller.activateAll();
        }
    }
    
    private boolean findPlayableCards(String roundColor, String lastColor, String lastValue) {
        boolean[] indexes = new boolean[8];
        boolean oneActivate = false;
        boolean oneBiggerLast = false;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) != null) {
                boolean b = manager.getVALUES_OF_CARDS().indexOf(cards.get(i).getValue()) > manager.getVALUES_OF_CARDS().indexOf(lastValue);
                if (cards.get(i).getColor().equals(roundColor) && b) {
                    oneBiggerLast = true;
                    controller.activatePlayable(i);
                    oneActivate = true;
                } else if (cards.get(i).getColor().equals(roundColor) && !oneBiggerLast) {
                    indexes[i] = true;
                    oneActivate = true;
                }
                
                if (!oneBiggerLast) {
                    for (int j = 0; j < indexes.length; j++) {
                        if (indexes[j]) {
                            controller.activatePlayable(j);
                            oneActivate = true;
                        }
                    }
                }
            }
        }
        
        return oneActivate;
        
    }
    
    public String gettrumphColor() {
        return trumphColor;
    }
    
    public void dealCards() {
        String[] imagesURL = new String[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            imagesURL[i] = "file:src/pictures/" + cards.get(i).toString() + ".png";
            
        }
        controller.dealCards(imagesURL);
    }
    
    private String stringToColor(String string) {
        String color = string.substring(0, 1);
        if (color.equals("Ž")) {
            color = "Z";
        }
        return color;
    }
    
    public void setTrumphColor(String trumphColor) {
        if (this.trumphColor == null) {
            
            this.trumphColor = stringToColor(trumphColor);
        }
    }
    
    public void setCards(List<Card> cards) {
        if (this.cards.isEmpty()) {
            this.cards = cards;
        }
    }
    
    public void showDialog(GameScreen gameScreen, String dialog) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(dialog));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException ex) {
            Alert aler = new Alert(Alert.AlertType.ERROR);
            aler.setTitle("Chyba");
            aler.setContentText("Cyhyba při načítání souboru aplikace");
            aler.showAndWait();
        }
        if (dialog.equals("trumphDialog.fxml")) {
            TrumphDialogController c = loader.getController();
            c.setGameScreen(gameScreen);
        }
        if (dialog.equals("playWithDialog.fxml")) {
            PlayWithDialogController c = loader.getController();
            c.setGameScreen(gameScreen);
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        
        stage.initModality(Modality.WINDOW_MODAL);
        
        stage.setScene(scene);
        
        stage.showAndWait();
        
    }
    
    public void setPlayWith(String value, String color) {
        playWith = new Card(value, stringToColor(color));
    }
    
    public Card getPlayWith() {
        return playWith;
    }
    
    public void showPlayedCard(Card playedCard) {
        controller.showPlayedCard("file:src/pictures/" + playedCard.toString() + ".png");
        
    }
    
    private void showAlert(DialogEvent event, String s) {
        if (s.equals(" ")) {
            event.consume();
            
        }
        
    }
    public void initializePlayersInfo(ArrayList<String>names, int thisIndex){
        
        controller.initializePlayersInfo(names, thisIndex);
    
    }
    public void updatePoints(String playerName, int points){
        controller.updatePoints(playerName, points);
    }
    public void setPlayWith(Card playWith, String playerName){
        controller.setPlayWith(playWith, playerName);
    }
    public void setTrumph(String trumphColor){
        controller.setTrumphColor(trumphColor);
    }
}
