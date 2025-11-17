/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package assignment_02_antonia_stoleru;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Antonia Stoleru
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
    
    private List<Contestant> contestants;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contestants = loadContestants();

        imageA.setImage(contestants.get(0).image);
        runnerId.setText("Contestant #" + contestants.get(0).number + ": " + contestants.get(0).name);
        
        paneB.setVisible(false);

        slideShow();
    }

    private List<Contestant> loadContestants() {
        return List.of(
             new Contestant("Kitty", 1, new Image("/assignment_02_antonia_stoleru/Images/cat1.png")),
             new Contestant("Meowy", 2, new Image("/assignment_02_antonia_stoleru/Images/cat2.png")),
             new Contestant("Mister Kitty", 3, new Image("/assignment_02_antonia_stoleru/Images/cat3.png")),
             new Contestant("Small cat", 4, new Image("/assignment_02_antonia_stoleru/Images/cat4.png")),
             new Contestant("Big Chongus", 5, new Image("/assignment_02_antonia_stoleru/Images/cat5.png"))     
        );
    }
    
    private List<Image> loadImages() {
        return List.of(
                new Image("/assignment_02_antonia_stoleru/Images/cat1.png"),
                new Image("/assignment_02_antonia_stoleru/Images/cat2.png"),
                new Image("/assignment_02_antonia_stoleru/Images/cat3.png"),
                new Image("/assignment_02_antonia_stoleru/Images/cat4.png"),
                new Image("/assignment_02_antonia_stoleru/Images/cat5.png")
        );
    }

    @FXML
    private void handleSkip(ActionEvent event) {
    }

    private void slideShow() {
        double width = root.getPrefWidth();

        SequentialTransition seq = new SequentialTransition();

        for (int i = 1; i < contestants.size(); i++) {
            
            Contestant next = contestants.get(i);

            PauseTransition pause = new PauseTransition(Duration.seconds(1.7));

            pause.setOnFinished(e -> {
                
                paneB.setTranslateX(width);
                paneB.setOpacity(0);
                paneB.setVisible(true);
                
                imageB.setImage(next.image);
                
                runnerId.setText("Contestant #" + next.number + ": " + next.name);

                TranslateTransition slideIn = new TranslateTransition(Duration.millis(1000), paneB);
                slideIn.setFromX(width);
                slideIn.setToX(0);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), paneB);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);

                TranslateTransition slideOut = new TranslateTransition(Duration.millis(1000), paneA);
                slideOut.setFromX(0);
                slideOut.setToX(-width);

                FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), paneA);
                fadeOut.setFromValue(1);
                fadeOut.setToValue(0);

                ParallelTransition trans = new ParallelTransition(slideIn, fadeIn, slideOut, fadeOut);

                trans.setOnFinished(ev -> {
                    imageA.setImage(next.image);
                    paneA.setTranslateX(0);
                    paneA.setOpacity(1);
                    paneB.setVisible(false);
                });

                trans.play();

            });

            seq.getChildren().add(pause);
        }

        seq.play();
    }

    public class Contestant {

        String name;
        int number;
        Image image;

        public Contestant(String name, int number, Image image) {
            this.name = name;
            this.number = number;
            this.image = image;
        }

    }

}
