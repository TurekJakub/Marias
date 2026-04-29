/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.marias.shared;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @brief Utility class providing constants for card values and suits in Mariáš.
 *
 *        The CardManager class contains static definitions of the valid card
 *        values and
 *        card colors (suits) used in the Mariáš card game. These constants are
 *        used throughout
 *        the application to standardize card representation and validation.
 *
 *        This class is not intended to be instantiated.
 * 
 * @author jakub
 */
public class CardManager {

    /**
     * @brief List of possible card values in the Mariáš game.
     *
     *        The values are ordered as {"7", "8", "9", "1", "B", "T", "K", "A"},
     *        where representation is following:
     *        "1" - 10
     *        "B" - Spodek
     *        "T" - Svršek
     *        "K" - Král
     *        "A" - Eso
     *
     *        Example usage:
     * @code
     *       for (String value : CardManager.VALUES_OF_CARDS) {
     *       // ...
     *       }
     *       @endcode
     */
    public static final LinkedList<String> VALUES_OF_CARDS = new LinkedList<>(
            Arrays.asList("7", "8", "9", "1", "B", "T", "K", "A"));

    /**
     * @brief Possible colors (suits) of cards in the Mariáš game.
     *
     *        The values are {"L", "Z", "K", "S"},where representation of suits is following:
     *        - "L": Listy 
     *        - "Z": Žaludy 
     *        - "K": Kule
     *        - "S": Srdce
     */
    public static final String[] COLORS_OF_CARDS = new String[] { "L", "Z", "K", "S" };

}
