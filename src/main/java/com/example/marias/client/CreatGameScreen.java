/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.marias.client;

import com.example.marias.game.Game;
import java.io.IOException;

/**
 *
 * @author jakub
 */
public class CreatGameScreen {

    public void startNewGame(String name, int port) throws IOException {
        Game game = new Game(port, name);
        game.start();

    }

}
