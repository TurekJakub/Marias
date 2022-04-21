/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jakub
 */
public class MainScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    MainScreen mainScreen = new MainScreen();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void handelClicJoin(ActionEvent event) {
        Button b = (Button) event.getSource();
        mainScreen.changeScene("GameScreen.fxml", (Stage) b.getScene().getWindow());
    }

    @FXML
    public void handelClicNew(ActionEvent event) {
        Button b = (Button) event.getSource();
        mainScreen.changeScene("GameScreen.fxml", (Stage) b.getScene().getWindow());
    }

    @FXML
    public void handelClicHelp(ActionEvent event) {
        Button b = (Button) event.getSource();
        mainScreen.changeScene("GameScreen.fxml", (Stage) b.getScene().getWindow());
    }

}
