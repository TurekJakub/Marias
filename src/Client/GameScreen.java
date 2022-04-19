/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.util.Arrays;
import shared.Card;
import shared.CardManager;

/**
 *
 * @author jakub
 */
public class GameScreen {

    GameScreenController controller;
    CardManager manager;
    private final String TRUMPHCOLOR;

    public GameScreen(String TRUMPHCOLOR, GameScreenController controller) {
        this.TRUMPHCOLOR = TRUMPHCOLOR;
        this.controller = controller;
        manager = new CardManager();
    }

    public void waitUntilNextTurn() {
        controller.deactivateAllCards();
    }

    public void activatePlayableCards(Card[] cards, String roundColor, String lastColor, String lastValue) {
     
        boolean oneActivate = false;
        if (lastColor.equals(roundColor)) {
            findPlayableCards(cards, roundColor, lastColor, lastValue);
        }
        if (lastColor.equals(TRUMPHCOLOR) || !oneActivate) {
             findPlayableCards(cards, TRUMPHCOLOR, lastColor, lastValue);
        }
        if (!oneActivate) {
            controller.activateAll();
        }
    }

    private boolean findPlayableCards(Card[] cards, String roundColor, String lastColor, String lastValue) {
        boolean[] indexes = new boolean[8];
        boolean oneActivate = false;
        boolean oneBiggerLast = false;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                boolean b = manager.getVALUES_OF_CARDS().indexOf(cards[i].getValue()) > manager.getVALUES_OF_CARDS().indexOf(lastValue);
                if (cards[i].getColor().equals(roundColor) && b) {
                    oneBiggerLast = true;
                    System.out.println(i);
                    controller.activatePlayable(i);
                    oneActivate = true;
                } else if (cards[i].getColor().equals(roundColor) && !oneBiggerLast) {
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

}
