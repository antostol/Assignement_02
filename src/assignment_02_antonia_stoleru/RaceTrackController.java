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

public class RaceTrackController implements Initializable {

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
    
    @FXML private Label cat1Name, cat2Name, cat3Name, cat4Name, cat5Name;
    
    private boolean raceOver = false;
    
    private Random random = new Random();
    
    private List<Contestant> contestants;
    
    private List<TranslateTransition> transitions;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contestants = loadContestants();

        setContestant(cat1, cat1Name, contestants.get(0));
        setContestant(cat2, cat2Name, contestants.get(1));
        setContestant(cat3, cat3Name, contestants.get(2));
        setContestant(cat4, cat4Name, contestants.get(3));
        setContestant(cat5, cat5Name, contestants.get(4));
    }
    
    private void setContestant(ImageView img, Label nameLabel, Contestant c) {
    nameLabel.setText(c.name);
    img.setImage(c.runningImage);
    }
    
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

    @FXML
    private void handleStart(ActionEvent event) {
        messageArea.setText("Welcome to cats' race!");
        if (raceOver) 
        resetRace();
        startRaceWithTransitions();
    }

    @FXML
    private void handlePause(ActionEvent event) {
        if (transitions != null && !transitions.isEmpty()) {
            for (TranslateTransition t : transitions) {
                t.pause();
            }
            messageArea.setText("Race paused!");
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }
    
    private void resetRace() {
        raceOver = false;
        cat1.setTranslateX(0);
        cat2.setTranslateX(0);
        cat3.setTranslateX(0);
        cat4.setTranslateX(0);
        cat5.setTranslateX(0);
        messageArea.clear();
        
        cat1.setImage(contestants.get(0).runningImage);
        cat2.setImage(contestants.get(1).runningImage);
        cat3.setImage(contestants.get(2).runningImage);
        cat4.setImage(contestants.get(3).runningImage);
        cat5.setImage(contestants.get(4).runningImage);
    }
    
    private void startRaceWithTransitions() {
        raceOver = false;
        double finishX = racepane.getWidth() - 50;
        
        ImageView[] cats = {cat1, cat2, cat3, cat4, cat5};
        transitions = new java.util.ArrayList<>();
        
        for (int i = 0; i < cats.length; i++) {
         ImageView cat = cats[i];
        
        double duration = 4 + random.nextDouble() * 3;
        
        TranslateTransition translate = new TranslateTransition(Duration.seconds(duration), cat);
        translate.setFromX(0);
        translate.setToX(finishX - cat.getLayoutX());
        
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

    
    private void declareWinner(int catIndex) {
        raceOver = true;
        
        Contestant winner = contestants.get(catIndex);
        showWinnerPopup(winner);
    }
    
    private void showWinnerPopup(Contestant winner) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: white;");
        
        ImageView winnerImg = new ImageView(winner.winnerImage);
        winnerImg.setFitWidth(150);
        winnerImg.setFitHeight(150);
        winnerImg.setPreserveRatio(true);
        
 
        Label message = new Label("Contestant " + winner.name + " Has won the race!");
        message.setStyle("-fx-font-weight: bold; -fx-text-fill: green; -fx-font-size: 16px;");
        
        root.getChildren().addAll(winnerImg, message);
        
        Stage stage = new Stage();
        stage.setTitle("Winner!");
        stage.setScene(new Scene(root, 400, 350));
        stage.show();
    }
    
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
