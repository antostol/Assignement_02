/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package assignment_02_antonia_stoleru;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Raluca
 */
public class SlideShowController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Pane paneA;
    @FXML
    private Rectangle recA;
    @FXML
    private ImageView imageA;
    @FXML
    private Pane paneB;
    @FXML
    private Rectangle recA1;
    @FXML
    private ImageView imageB;
    @FXML
    private Label runnerId;
    @FXML
    private Button skip;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSkip(ActionEvent event) {
    }
    
}
