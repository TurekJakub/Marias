/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @mainpage Mariáš
 * 
 *  Following documentation describes simple game project. This project is multiplayer game, that allows
 *  four players to play popular card game Mariáš over local network via simple graphical interface
 * 
 *  This documentation describes core components of the project, game logic classes, MVC design, interfaces
 *  of individual modules and their dependencies.     
 * 
 *  
*/
package com.example.marias.client;


import com.example.marias.client.AppMain;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author jakub
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        AppMain.main(args);
        logger.info("App stared successfully");
    }
}
