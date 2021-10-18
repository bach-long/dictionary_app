package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class WordMemo implements Initializable {
    boolean open = false;

    @FXML
    private JFXButton prev;

    @FXML
    private JFXHamburger h1;

    @FXML
    private AnchorPane slider;

    @FXML
    private AnchorPane web;

    @FXML
    void prevController(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        HamburgerNextArrowBasicTransition burgerTask = new HamburgerNextArrowBasicTransition(h1);
        burgerTask.setRate(-1);
        h1.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
            burgerTask.setRate(burgerTask.getRate()*-1);
            burgerTask.play();
            double leng = slider.getHeight();

            if(burgerTask.getRate() == -1) {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(slider);
                slide.setToX(0);
                slide.play();
                slider.setTranslateX(-leng);

                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(0.4));
                slide2.setNode(web);
                slide2.setToX(0);
                slide2.play();

                //web.setTranslateX(-leng/2);
            } else {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(slider);
                slide.setToX(-leng);
                slide.play();

                slider.setTranslateX(0);

                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(0.4));
                slide2.setNode(web);
                slide2.setToX(-100);
                slide2.play();

                //web.setTranslateX(5);
            }
        });


    }
}
