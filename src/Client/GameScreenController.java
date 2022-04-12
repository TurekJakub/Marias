/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author jakub
 */
public class GameScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    AnchorPane backPane;
    @FXML
    Label targetAreaLabel;
    @FXML
    GridPane backGrid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        backGrid.setStyle("-fx-background-color:#074212");
        targetAreaLabel.setStyle("-fx-background-color:#cfd1cf");
        backPane.setStyle("-fx-background-color:#074212");
        addCardsImages(initializeCards());

    }

    private void handleOnMousePressed(MouseEvent event) {
        ImageViewWithCoordinates i = (ImageViewWithCoordinates) event.getSource();
        i.setCursorX(i.getLayoutX() - event.getSceneX());
        i.setCursorX(i.getLayoutY() - event.getSceneY());
    }

    private void handleOnMouseDragged(MouseEvent event) {
        ImageViewWithCoordinates i = (ImageViewWithCoordinates) event.getSource();
        i.setLayoutX(event.getSceneX() + i.getCursorX());
        i.setLayoutY(event.getSceneY() + i.getCursorY());
    }

    private void handleOnMouseReleased(MouseEvent event) {
        ImageViewWithCoordinates i = (ImageViewWithCoordinates) event.getSource();
        i.setLayoutX(i.getDefaultX());
        i.setLayoutY(i.getDefaultY());
    }

    @FXML
    private ImageViewWithCoordinates[] initializeCards() {
        ImageViewWithCoordinates[] imageViews = new ImageViewWithCoordinates[8];
        Image image = new Image(getClass().getResourceAsStream("card.png"));

        for (int i = 0; i < 8; i++) {
            imageViews[i] = new ImageViewWithCoordinates(image);
            imageViews[i].setImage(image);
            imageViews[i].setFitHeight(225);
            imageViews[i].setFitWidth(150);
            imageViews[i].setDefaultX(i * 150);
            imageViews[i].setDefaultY(525);
            imageViews[i].setOnMousePressed(event -> handleOnMousePressed(event));
            imageViews[i].setOnMouseDragged(event -> handleOnMouseDragged(event));
            imageViews[i].setOnMouseReleased(event -> handleOnMouseReleased(event));

        }
        return imageViews;
    }

    @FXML
    private void addCardsImages(ImageViewWithCoordinates[] images) {

        for (int i = 0; i < 8; i++) {
            backPane.getChildren().add(images[i]);

            images[i].setLayoutX(images[i].getDefaultX());
            images[i].setLayoutY(images[i].getDefaultY());
        }

    }

}
