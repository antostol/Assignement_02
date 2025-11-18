package assignment_02_antonia_stoleru;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class RaceTrackController implements Initializable {
    //Elements of fxml file
    @FXML
    private Button exitBtn;

    @FXML
    private TextArea messageArea;

    @FXML
    private Button pauseBtn;

    @FXML
    private Pane racepane;

    @FXML
    private Button startBtn;

    @FXML
    private Label welcome;

    @FXML
    private ImageView cat5;

    @FXML
    private ImageView cat4;

    @FXML
    private ImageView cat3;

    @FXML
    private ImageView cat2;

    @FXML
    private ImageView cat1;

    @FXML
    private Label cat1Name, cat2Name, cat3Name, cat4Name, cat5Name;

    private boolean raceOver = false;

    private Random random = new Random();

    private List<Contestant> contestants;

    private List<TranslateTransition> transitions;

    private MediaPlayer raceMusicPlayer;

    private MediaPlayer winnerMusicPlayer;

    /**
     * Initializes the controller class.
     * Loads cat information and sets the correct images.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contestants = loadContestants();
        //Names and images for each cat
        setContestant(cat1, cat1Name, contestants.get(0));
        setContestant(cat2, cat2Name, contestants.get(1));
        setContestant(cat3, cat3Name, contestants.get(2));
        setContestant(cat4, cat4Name, contestants.get(3));
        setContestant(cat5, cat5Name, contestants.get(4));
    }
    
    //Helping method for setting
    private void setContestant(ImageView img, Label nameLabel, Contestant c) {
        nameLabel.setText(c.name);
        img.setImage(c.runningImage);
    }
    
    //List for loading contestants
    private List<Contestant> loadContestants() {
        return List.of(
                new Contestant("Kitty",
                        new Image(getClass().getResource("/assignment_02_antonia_stoleru/Images/catrunning.png").toExternalForm()),
                        new Image(getClass().getResource("/assignment_02_antonia_stoleru/Images/cat1.png").toExternalForm())
                ),
                new Contestant("Meowy",
                        new Image(getClass().getResource("/assignment_02_antonia_stoleru/Images/catrunning.png").toExternalForm()),
                        new Image(getClass().getResource("/assignment_02_antonia_stoleru/Images/cat2.png").toExternalForm())
                ),
                new Contestant("Mister Kitty",
                        new Image(getClass().getResource("/assignment_02_antonia_stoleru/Images/catrunning.png").toExternalForm()),
                        new Image(getClass().getResource("/assignment_02_antonia_stoleru/Images/cat3.png").toExternalForm())
                ),
                new Contestant("Small cat",
                        new Image(getClass().getResource("/assignment_02_antonia_stoleru/Images/catrunning.png").toExternalForm()),
                        new Image(getClass().getResource("/assignment_02_antonia_stoleru/Images/cat4.png").toExternalForm())
                ),
                new Contestant("Big Chongus",
                        new Image(getClass().getResource("/assignment_02_antonia_stoleru/Images/catrunning.png").toExternalForm()),
                        new Image(getClass().getResource("/assignment_02_antonia_stoleru/Images/cat5.png").toExternalForm())
                )
        );
    }
    
    //Method for handling start button (starts and resumes the race) -> plays animations and music
    @FXML
    private void handleStart(ActionEvent event) {
        //Welcome message
        messageArea.setText("Welcome to cats' race!");
        //If race ended start over
        if (raceOver) {
            resetRace();
            playRaceMusic();
            startRaceWithTransitions();
        //If race paused resume it
        } else if (transitions != null && !transitions.isEmpty()) {
            for (TranslateTransition t : transitions) {
                t.play();
            }
            if (raceMusicPlayer != null) {
                raceMusicPlayer.play();
            }
            messageArea.setText("Race resumed!");
        //First time starting the race
        } else {
            messageArea.setText("Welcome to cats' race!");
            playRaceMusic();
            startRaceWithTransitions();
        }
    }
    
    //Handles pausing the race -> stops animations and music
    @FXML
    private void handlePause(ActionEvent event) {
        if (transitions != null && !transitions.isEmpty()) {
            for (TranslateTransition t : transitions) {
                t.pause();
            }
            if (raceMusicPlayer != null) {
                raceMusicPlayer.pause();
            }
            if (winnerMusicPlayer != null) {
                winnerMusicPlayer.pause();
            }

            messageArea.setText("Race paused!");
        }
    }
    
    //Handles existing the program
    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }
    
    //Handles reseting the race -> images, animations, music
    private void resetRace() {
        raceOver = false;
        
        //Move cats back to starting line
        cat1.setTranslateX(0);
        cat2.setTranslateX(0);
        cat3.setTranslateX(0);
        cat4.setTranslateX(0);
        cat5.setTranslateX(0);
        messageArea.clear();

        //Reset cat images
        cat1.setImage(contestants.get(0).runningImage);
        cat2.setImage(contestants.get(1).runningImage);
        cat3.setImage(contestants.get(2).runningImage);
        cat4.setImage(contestants.get(3).runningImage);
        cat5.setImage(contestants.get(4).runningImage);
    }

    //Starts the actual race
    private void startRaceWithTransitions() {
        raceOver = false;
        double finishX = racepane.getWidth() - 50; //End position

        ImageView[] cats = {cat1, cat2, cat3, cat4, cat5};
        transitions = new java.util.ArrayList<>();

        //Create animation for each cat
        for (int i = 0; i < cats.length; i++) {
            ImageView cat = cats[i];

            double duration = 4 + random.nextDouble() * 3;

            TranslateTransition translate = new TranslateTransition(Duration.seconds(duration), cat);
            translate.setFromX(0);
            translate.setToX(finishX - cat.getLayoutX());

            //Declaring winner when race is over
            int finalI = i;
            translate.setOnFinished(e -> {
                if (!raceOver) {
                    declareWinner(finalI);
                }
            });

            transitions.add(translate);
            translate.play();

        }

    }
    
    /**
     * Called when the first cat finishes the race.
     */
    private void declareWinner(int catIndex) {
        raceOver = true;
        
        //Stops music for winner music to come in
        if (raceMusicPlayer != null) raceMusicPlayer.stop();
        
        playWinnerMusic();

        Contestant winner = contestants.get(catIndex);
        showWinnerPopup(winner);
    }
    
    /**
     * Shows a small popup window displaying the winner.
     */
    private void showWinnerPopup(Contestant winner) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: white;");
        
        //Winner's image
        ImageView winnerImg = new ImageView(winner.winnerImage);
        winnerImg.setFitWidth(150);
        winnerImg.setFitHeight(150);
        winnerImg.setPreserveRatio(true);
        
        //Winner's name
        Label message = new Label("Contestant " + winner.name + " Has won the race!");
        message.setStyle("-fx-font-weight: bold; -fx-text-fill: green; -fx-font-size: 16px;");

        root.getChildren().addAll(winnerImg, message);

        Stage stage = new Stage();
        stage.setTitle("Winner!");
        stage.setScene(new Scene(root, 400, 350));
        stage.show();
    }
    
    //Plays background music during race
    public void playRaceMusic() {
        String path = getClass().getResource("/musicrace.mp3").toExternalForm();
        Media media = new Media(path);
        raceMusicPlayer = new MediaPlayer(media);
        raceMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        raceMusicPlayer.play();
    }
    
    //Plays winner celebration music
    private void playWinnerMusic() {
        String path = getClass().getResource("/winningmusic.mp3").toExternalForm();
        Media media = new Media(path);
        winnerMusicPlayer = new MediaPlayer(media);
        winnerMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        winnerMusicPlayer.play();
    }
    
    //Inner class that stores contestants' names and images
    public class Contestant {

        String name;
        Image winnerImage;
        Image runningImage;

        public Contestant(String name, Image runningImage, Image winnerImage) {
            this.name = name;
            this.winnerImage = winnerImage;
            this.runningImage = runningImage;
        }
    }
}
