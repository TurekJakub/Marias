/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author jakub
 */
public class ExistingGamesScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ListView gamePickerView;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    public void createViewContent(ArrayList<ServerPrametr> param){
        gamePickerView.getItems().removeAll();
        for(ServerPrametr sp : param){
            
            gamePickerView.getItems().add(sp.getName());
        
        }
        
    }
}
