package sample;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FlashCard implements Initializable {
    private List<String> listWord = new ArrayList<>();
    int show = 0;
    int size = 0;
    boolean check = true;
    @FXML
    private AnchorPane page1;

    @FXML
    private AnchorPane page2;

    @FXML
    private JFXButton prev;

    @FXML
    private TextArea word1;

    @FXML
    private TextArea mean1;

    @FXML
    private TextArea word2;

    @FXML
    private TextArea mean2;

    @FXML
    private Label number;

    @FXML
    private Label text2;


    @FXML
    void later(ActionEvent event) throws IOException {
        if(check) {
            show--;
            if(show <= 0) {
                show = listWord.size() -1;
            }
            number.setText((show + 1) + " / " + size);
            text2.setText((show + 1) + " / " + size);
            String s = translator.translate("en", "vi", listWord.get(show));
            mean2.setText(s);
            word2.setText(listWord.get(show));
            page2.setTranslateX(800);
            translateAnimation(0.25, page2, -800);
            translateAnimation(0.25,page1, -800);
            check = false;

        } else {
            show--;
            if(show <= 0) {
                show = listWord.size() -1;
            }
            number.setText((show + 1) + " / " + size);
            text2.setText((show + 1) + " / " + size);
            String s = translator.translate("en", "vi", listWord.get(show));
            mean1.setText(s);
            word1.setText(listWord.get(show));
            page1.setTranslateX(800);
            translateAnimation(0.25, page1, -800);
            translateAnimation(0.25,page2, -800);
            check = true;
        }

    }

    @FXML
    void prev(ActionEvent event) throws IOException {
        Stage stage = (Stage)prev.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("listMemoView.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    void next(ActionEvent event) throws IOException {
        if(check) {
            show++;
            if(show >= listWord.size()) {
                show = 0;
            }
            number.setText((show + 1) + " / " + size);
            text2.setText((show + 1) + " / " + size);
            word2.setText(listWord.get(show));
            String s = translator.translate("en", "vi", listWord.get(show));
            mean2.setText(s);
            page2.setTranslateX(-800);
            translateAnimation(0.5, page2, 800);
            translateAnimation(0.5, page1,800);
            check = false;


        } else {
            show++;
            if(show >= listWord.size()) {
                show = 0;
            }
            number.setText((show + 1) + " / " + size);
            text2.setText((show + 1) + " / " + size);
            word1.setText(listWord.get(show));
            String s = translator.translate("en", "vi", listWord.get(show));
            mean1.setText(s);
            page1.setTranslateX(-800);
            translateAnimation(0.5, page1, 800);
            translateAnimation(0.5, page2,800);
            check = true;

        }
    }

    public void translateAnimation(double duration, Node node, double width) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
        translateTransition.setByX(width);
        translateTransition.play();
    }

    @FXML
    void audio(ActionEvent event) {
            TextToSpeech speech = new TextToSpeech();
            speech.toSpeech(listWord.get(show));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            listWord = database_manage.get_list(ListGroupOut.group);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String s = null;
        try {
            s = translator.translate("en", "vi", listWord.get(show));
        } catch (IOException e) {
            e.printStackTrace();
        }
        size = listWord.size();
        number.setText((show + 1) + " / " + size);
        text2.setText((show + 1) + " / " + size);
        mean1.setText(s);
        word1.setText(listWord.get(show));
        translateAnimation(0.1,page2,-800);
        //translateAnimation(0.1,page1,-800);
    }
}
