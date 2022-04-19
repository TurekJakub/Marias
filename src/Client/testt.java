/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import game.GameManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.Card;

/**
 *
 * @author jakub
 */
public class testt extends Thread {

    @Override
    public void run() {
        GameScreen g = new GameScreen("S");
        GameManager ge = new GameManager();
        Card[] f = ge.getPlayersCardsSet(0).toArray(new Card[8]);
        for (Card c : f) {
            System.out.println(c.toString());
        }
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(testt.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.findPlayableCards(f, "L", "L", "8");
    }

}
