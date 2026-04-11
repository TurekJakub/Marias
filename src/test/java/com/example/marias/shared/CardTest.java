package com.example.marias.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CardManagerTest {

    @Test 
    void testCardComparison() {
        Card c1 = new Card("10", "Z");
        Card c2 = new Card("K", "Z");
        Card c3 = new Card("7", "K");
        Card c4 = new Card("7", "K");

        assertEquals(c2.compareTo(c1), 1);
        assertEquals(c1.compareTo(c3), -1);
        assertEquals(c3.compareTo(c4), 0);
    }
}
