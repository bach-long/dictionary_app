package sample;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Scene2Controller implements Initializable {
    TextAnimation textAnimation;

    @FXML
    private Text text;

    @FXML
    private AnchorPane parentContro;

    @FXML
    private Circle c1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRotate(c1, true, 1000, 10);
        TextOutput textOutput = new TextOutput() {
            @Override
            public void writeText(String textOut) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(textOut);
                    }
                });
            }
        };

        textAnimation = new TextAnimation("Hello!!",
                150, textOutput);
        Thread thread = new Thread(textAnimation);
        thread.start();
    }
    int rotate = 0;
    private void setRotate(Circle c, boolean reverse, int angle, int duration) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), c);

        rotateTransition.setAutoReverse(reverse);

        rotateTransition.setByAngle(angle);
        rotateTransition.setDelay(Duration.seconds(0));
        rotateTransition.setRate(5);
        rotateTransition.setCycleCount(2);
        rotateTransition.play();;

        rotateTransition.setOnFinished((e)->{
            try {
                AnchorPane parentContent = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                parentContro.getChildren().setAll(parentContent);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }
}
