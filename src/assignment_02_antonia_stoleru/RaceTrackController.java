
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  

}
