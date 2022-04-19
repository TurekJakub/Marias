/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author jakub
 */
public class CardManager {

    private final LinkedList<String> VALUES_OF_CARDS;
    private final String[] COLORS_OF_CARDS;
  

    public CardManager() {
        VALUES_OF_CARDS = new LinkedList<>(Arrays.asList("7", "8", "9", "1", "B", "T", "K", "A"));
        COLORS_OF_CARDS = new String[]{"L", "Z", "K", "S"};       

    } 

    

    public String[] getCOLORS_OF_CARDS() {
        return COLORS_OF_CARDS;
    }

    public LinkedList<String> getVALUES_OF_CARDS() {
        return VALUES_OF_CARDS;
    }
    

}
