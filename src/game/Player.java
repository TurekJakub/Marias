/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.LinkedList;
import shared.Card;

/**
 *
 * @author jakub
 */
public class Player {
    private int points;
    private LinkedList<Card> cards;
    private String name;

    public Player(int points, String name) {
        this.points = points;        
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Card> getCards() {
        return cards;
    }

    public int getPoints() {
        return points;
    }
   

    public void updatePoints(int points) {
        this.points += points;
    }

    public void updateCards(Card card) {
        cards.remove(card);
    }

    public void setCards(LinkedList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return name + ":"+ points;
    }
    
    
            
    
    
    
  
    
    
}
