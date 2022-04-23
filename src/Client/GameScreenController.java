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
import javafx.scene.Cursor;
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
    AnchorPane frontPane; // odkaz na AnchorPane definovaný pomocí Scene Builderu v .fxml souboru
    @FXML
    Label targetAreaLabel;
    @FXML
    GridPane backGrid;
    ImageViewWithCoordinates[] cards;

    @Override
    // Zavolá se při otevření daného okna, takže zde volám metody, které mají přidat do okny prvky před jeho zobrazením
    public void initialize(URL url, ResourceBundle rb) {
        cards = initializeCards();
        addCards(cards);
        deactivateAllCards();
       

    }

    private void handelOnMouseEntered(MouseEvent event) {
        ImageViewWithCoordinates i = (ImageViewWithCoordinates) event.getSource();

        if (!i.isInactive()) {
            i.setCursor(Cursor.HAND);
        }

    }

    private void handleOnMousePressed(MouseEvent event) {
        ImageViewWithCoordinates i = (ImageViewWithCoordinates) event.getSource();
        if (!i.isInactive()) {
            i.setCursorX(i.getLayoutX() - event.getSceneX());
            i.setCursorY(i.getLayoutY() - event.getSceneY());
            i.setCursor(Cursor.CLOSED_HAND);
        }
    }

    private void handleOnMouseDragged(MouseEvent event) {
        ImageViewWithCoordinates i = (ImageViewWithCoordinates) event.getSource();
        if (!i.isInactive()) {
            i.setLayoutX(event.getSceneX() + i.getCursorX());
            i.setLayoutY(event.getSceneY() + i.getCursorY());
        }
    }

    private void handleOnMouseReleased(MouseEvent event) {
        ImageViewWithCoordinates i = (ImageViewWithCoordinates) event.getSource();
        boolean b = i.getLayoutX() > 300 && i.getLayoutX() < 900;
        boolean a = i.getLayoutY() > 275 && i.getLayoutY() < 500;
        if (b && a) {
            System.err.println("Je to tam!");
            i.setLayoutX(300);
            i.setLayoutY(275);

        } else {
            i.setLayoutX(i.getDefaultX());
            i.setLayoutY(i.getDefaultY());
        }

    }

    @FXML
    private ImageViewWithCoordinates[] initializeCards() { // Nepříliš duležitá metoda, která vrátí pole ImageView s nastavenými parametry
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
            imageViews[i].setOnMouseEntered(event -> handelOnMouseEntered(event));
            imageViews[i].getStyleClass().add("gamescreen.css");
            imageViews[i].applyCss();

        }
        return imageViews;
    }

    @FXML
    private void addCards(ImageViewWithCoordinates[] images) { // metoda, která přidá prvky z pole vráceného předchozí metodou do AnchorPanu.

        for (ImageViewWithCoordinates image : images) {
            frontPane.getChildren().add(image); // přidání ImageView do AnchorPanu
            // Nastavení jejich umístění na požadované souřadnice
            image.setLayoutX(image.getDefaultX());
            image.setLayoutY(image.getDefaultY());
        }

    }

    @FXML
    public void deactivateAllCards() {
        for (ImageViewWithCoordinates card : cards) {
            card.setInactive(true);
        }

    }

    @FXML
    public void activatePlayable(int index) {
        cards[index].setInactive(false);
    }

    @FXML
    public void activateAll() {
        for (ImageViewWithCoordinates i : cards) {
            i.setInactive(false);
        }
    }
   

}
