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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


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
    private Rectangle recB;
    @FXML       
    private ImageView imageB;
    @FXML
    private Label runnerId;
    @FXML
    private Button skip;
    
    private List<Contestant> contestants;   
    
    private MediaPlayer mediaPlayer;
    
    private SequentialTransition masterTransition;
    
    private ParallelTransition lastTransition;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String musicPath = getClass().getResource("/music.mp3").toExternalForm();
            Media media = new Media(musicPath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }catch (Exception e){
            System.out.println("Music file not found");
        }
        
        contestants = loadContestants();

        imageA.setImage(contestants.get(0).image);
        imageA.setOpacity(0);
        
        runnerId.setText("Contestant #" + contestants.get(0).number + ": " + contestants.get(0).name);
        runnerId.setOpacity(0);
        
        paneB.setVisible(false);
        paneB.setDisable(true);
        
        FadeTransition imgIn = new FadeTransition(Duration.millis(1200), imageA);
        imgIn.setFromValue(0);
        imgIn.setToValue(1);
        
        FadeTransition txtIn = new FadeTransition(Duration.millis(1200), runnerId);
        txtIn.setFromValue(0);
        txtIn.setToValue(1);
        
        ParallelTransition initialIn = new ParallelTransition(imgIn, txtIn);
        initialIn.setOnFinished(e -> slideShow());
        initialIn.play();
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
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        
        if (masterTransition != null) {
            masterTransition.stop();
        }
        
        if (lastTransition != null) {
            lastTransition.stop();
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RaceTrack.fxml"));
            Parent raceRoot = loader.load();
            
            Scene scene = skip.getScene();
            scene.setRoot(raceRoot);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void slideShow() {
        double width = root.getPrefWidth();

        SequentialTransition seq = new SequentialTransition();
        
        PauseTransition firstPause = new PauseTransition(Duration.seconds(0.8));
        firstPause.setOnFinished(e -> playSlide(contestants.get(1), width));
        seq.getChildren().add(firstPause);

        for (int i = 2; i < contestants.size(); i++) {
            
            Contestant next = contestants.get(i);
            seq.getChildren().add(createTransition(next, width));
            
        }
        
        masterTransition = seq;
        masterTransition.play();
        
    }
    
    private PauseTransition createTransition(Contestant next, double width) {
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        
        pause.setOnFinished(e -> playSlide(next, width));
        
        return pause;
    }
        
            
    private void playSlide(Contestant next, double width) {
        paneB.setTranslateX(width);
        paneB.setDisable(false);
        paneB.setVisible(true);

        imageB.setImage(next.image);
        imageB.setOpacity(0);

        animateText("Contestant #" + next.number + ": " + next.name);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(2500), paneB);
        slideIn.setFromX(width);
        slideIn.setToX(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(3400), imageB);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        TranslateTransition slideOut = new TranslateTransition(Duration.millis(2500), paneA);
        slideOut.setFromX(0);
        slideOut.setToX(-width);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(2500), imageA);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        ParallelTransition trans = new ParallelTransition(fadeIn, slideIn, slideOut, fadeOut);
        
        lastTransition = trans;

        trans.setOnFinished(ev -> {
            imageA.setImage(next.image);
            imageA.setOpacity(1);
            imageB.setOpacity(0);
            
            paneA.setTranslateX(0);
            paneB.setVisible(false);
            paneB.setDisable(true);
        });

        trans.play();
    }
    
    private void animateText(String newText) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(1900), runnerId);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1900), runnerId);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        fadeOut.setOnFinished(e -> runnerId.setText(newText));
        
        new javafx.animation.SequentialTransition(fadeOut, fadeIn).play();
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
