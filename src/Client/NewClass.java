/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import game.GameManager;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shared.Card;

/**
 *
 * @author jakub
 */
public class NewClass extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GameScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Kalkulátor");
        primaryStage.setScene(scene);
        primaryStage.show();
        GameScreen g = new GameScreen("S", loader.getController());
        GameManager ge = new GameManager();

        Card[] f = ge.getPlayersCardsSet(0).toArray(new Card[8]);
        for (int i = 0; i < 8; i++) {
            System.out.println(f[i].toString());
           if (f[i].getColor().equals("L") ||f[i].getColor().equals("S") ) {
                f[i] = null;
            }
        }

        g.activatePlayableCards(f, "L", "L", "8");

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
