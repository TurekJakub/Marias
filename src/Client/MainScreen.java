/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author jakub
 */
public class MainScreen {
    public void changeScene(String scene,Stage stage) 
    {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(scene));
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba!");
            alert.setHeaderText("Chyba při načítání souboru aplikace");
            alert.setContentText(ex.getMessage());            
            alert.showAndWait();
        }
        stage.setScene(new Scene(root));
    
    }
}
