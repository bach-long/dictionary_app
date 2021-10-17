package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.sql.SQLException;
import java.util.*;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.awt.*;
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class HelloController implements Initializable {
    public String namePerson = "Person";
    String globe = "";
    public static String[] listWord = null;

    public static void getListWord() throws SQLException {
        listWord = database_manage.list_word();
    }

    @FXML
    public TextField tfinput;
    private AutoCompletionBinding<String> autoCompletionBinding;
    Set<String> psWord = new HashSet<>();

    @FXML
    public Button bt;

    @FXML
    private Label name;

    @FXML
    private Label wordIn;

    @FXML
    public AnchorPane anchor;

    @FXML
    private AnchorPane slider;

    @FXML
    private AnchorPane web;

    @FXML
    private JFXHamburger h1;

    @FXML
    private JFXButton acpt;

    @FXML
    private JFXButton dec;

    @FXML
    private JFXDialog dialogLogin;

    @FXML
    private JFXButton login;

    @FXML
    public WebView html_view;

    @FXML
    private JFXButton next;

    @FXML
    private JFXButton memo;

    @FXML
    private JFXButton ListMemo;

    @FXML
    private TextField inName;

    @FXML
    private JFXButton searchOnline;


    @FXML
    private StackPane root;

    public WebEngine engine = null;

    @FXML
    void gopY(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.facebook.com/anh.tranthe.98622"));
    }

    @FXML
    void searchOnGG(ActionEvent event) throws URISyntaxException, IOException {
       Desktop.getDesktop().browse(new URI("https://translate.google.com"));
    }

    @FXML
    void switchToScene1(MouseEvent event) throws IOException {
        Stage stage = (Stage)next.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("trans.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    void addWord(MouseEvent event) throws IOException {
        Stage stage = (Stage)next.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddWord.fxml"));
        stage.setScene(new Scene(root));
    }


    public void submit(ActionEvent event) throws SQLException {
        String input = tfinput.getText();
        engine = html_view.getEngine();
        wordIn.setText(input);
        try {
            String s = database_manage.search(input);
            if(s == "<h1 style=\"color:Tomato;\"> This word doesn't exist </h1>") {
                s = database_manage.user_search(input);
            }
            engine.loadContent(s);
        } catch(Exception e) {
            engine.loadContent("<h1>This word doesn't exist</h1>");
        }
    }

    public void audioAction (ActionEvent event) throws SQLException {
        try {
            TextToSpeech speech = new TextToSpeech();
            speech.toSpeech(tfinput.getText());
        } catch(Exception e) {
            engine.loadContent("<h1>This word doesn't exist</h1>");
        }
    }

    @FXML
    void memo(MouseEvent event) throws IOException {
        Stage stage = (Stage)memo.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("listGroup.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    void openList(MouseEvent event) throws IOException {
        Stage stage = (Stage)ListMemo.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("listGroup.fxml"));
        stage.setScene(new Scene(root));
    }


    @Override
    public void initialize(URL url, ResourceBundle rsc) {
        dialogLogin.setTransitionType(JFXDialog.DialogTransition.BOTTOM);
        dialogLogin.setDialogContainer(root);
        dec.setOnMouseClicked(MouseEvent ->{
            dialogLogin.close();
        });

        acpt.setOnMouseClicked(MouseEvent ->{
            namePerson = inName.getText();
            dialogLogin.close();
        });
        login.setOnMouseClicked(MouseEvent->{
            dialogLogin.show();
        });

        name.setText(namePerson);

        try {
            getListWord();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.addAll(psWord, listWord);
        autoCompletionBinding = TextFields.bindAutoCompletion(tfinput,psWord);
        tfinput.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()) {
                case ENTER:
                    learnWord(tfinput.getText());
                    String input = tfinput.getText();
                    engine = html_view.getEngine();
                    wordIn.setText(input);
                    try {
                        String s = database_manage.search(input);
                        if(s == "<h1 style=\"color:Tomato;\"> This word doesn't exist </h1>") {
                            s = database_manage.user_search(input);
                        }
                        engine.loadContent(s);
                    } catch(Exception e1) {
                        engine.loadContent("<h1>This word doesn't exist</h1>");
                    }
                    break;
                default:
                    break;
            }
        });

        HamburgerNextArrowBasicTransition burgerTask = new HamburgerNextArrowBasicTransition(h1);
        burgerTask.setRate(-1);
        double leng = slider.getHeight();
        slider.setTranslateX(-200);
        web.setTranslateX(-100);
        h1.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
            burgerTask.setRate(burgerTask.getRate()*-1);
            burgerTask.play();

            if(burgerTask.getRate() == -1) {
                /**mo.*/
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(-200);
            slide.play();
            slider.setTranslateX(0);

            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setNode(web);
            slide2.setToX(-100);
            slide2.play();
            web.setTranslateX(0);
            } else {

                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(slider);
                slide.setToX(0);
                slide.play();
                slider.setTranslateX(-200);

                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(0.4));
                slide2.setNode(web);
                slide2.setToX(0);
                slide2.play();
                web.setTranslateX(-100);
                /** dong */

            }
        });


    }

    private void learnWord(String text) {
        psWord.add(text);
        if(autoCompletionBinding != null){
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(tfinput, psWord);
    }

}