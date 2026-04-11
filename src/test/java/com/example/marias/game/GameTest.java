package com.example.marias.game;

import com.example.marias.game.Game;
import com.example.marias.shared.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

class GameTest {

    private Game game;

    @BeforeEach
    void init() {
        try {
            game = new Game(0,"");
        }catch(IOException ex){
            fail("Failed to init mock game instance");
        }
    }

    @Test
    void testStringToColor() {
        assertEquals("S", game.stringToColor("Srdce"), "Srdce should be convert to 'S'");
        assertEquals("K", game.stringToColor("Kule"), "Kule should be convert to 'K'");
        assertEquals("Ž", game.stringToColor("Žaludy"), "Žaludy should be convert to 'Ž'");
        assertEquals("L", game.stringToColor("Listy"), "Listy should be convert to 'L'");

    }

    @Test
    void testCardPlayWith() {
        System.out.println("Testing cratPlayWith method...");
        Card c2 = new Card("Spodek", "Srdce");
        Card exp2 = new Card("B", "S");
        assertEquals(exp2.compareTo(game.cardPlayWith(c2)), 0, "Result should by equal to Card(B, S)");

        Card c1 = new Card("Svršek", "Žaludy");
        Card exp1 = new Card("T", "Ž");
        assertEquals(exp1.compareTo(game.cardPlayWith(c1)), 0, "Result should by equal to Card(T, Ž)");

        Card c3 = new Card("Desítka", "Kule");
        Card exp3 = new Card("D", "K");
        assertEquals(exp3.compareTo(game.cardPlayWith(c3)) , 0, "Result should by equal to Card(D, K)");
    }
}