package com.example.marias.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.marias.shared.Card;
import java.util.Arrays;
import java.util.List;

class GameScreenTest {

    private GameScreen gameScreen;

    @BeforeEach
    void init() {
        gameScreen = new GameScreen();
    }

    @Test
    void testFindPlayableCards2() {
        String color = "S";
        String value = "10";
        List<Card> hand = Arrays.asList(
            new Card("K", "S"),
            new Card("S", "K"),  
            new Card("10", "Ž"),  
            new Card("K", "Ž") 
        );

        assertArrayEquals(new boolean[]{true, false, false, false, false,false, false, false}, gameScreen.findPlayableCards(color, value, hand));
    }

    @Test
    void testFindPlayableCard2() {
        String color = "L";
        String value = "K";
        List<Card> hand = Arrays.asList(
            new Card("K", "S"),
            new Card("S", "S"),  
            new Card("10", "S"),  
            new Card("K", "S") ,
            new Card("7", "L"), 
            new Card("8", "L"),  
            new Card("9", "K"), 
            new Card("10", "K")
        );
        
        assertArrayEquals(new boolean[]{false, false, false, false, true, true, false, false}, gameScreen.findPlayableCards(color, value, hand));
    }
}