/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import shared.Card;

/**
 *
 * @author jakub
 */
public class Receiver extends Thread {

    public Receiver() {

    }

    public Card readCard(Socket playerSocket) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));

        String[] input = in.readLine().split(":");
        return new Card(input[0], input[1]);

    }

    public String readString(Socket playerSocket) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));

        return in.readLine();

    }

}
