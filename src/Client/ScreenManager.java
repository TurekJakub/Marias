package Client;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jakub
 */
public class ScreenManager {

    public Initializable changeScene(String scene, Stage stage) throws IOException {
        FXMLLoader loader =new FXMLLoader(getClass().getResource(scene));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        return loader.getController();

    }
}
