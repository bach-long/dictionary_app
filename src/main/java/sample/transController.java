package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class transController implements Initializable {
    private static String language = "Anh-Việt";
    public static String[] lang = {"Anh-Việt", "Việt-Anh"};
    public static String nameTable = "av";
    public static String from = "en";
    public static String to = "vi";

    @FXML
    private TextArea input_prg;

    @FXML
    private WebView prg_view;

    public WebEngine engine = null;

    @FXML
    public Button prg;

    @FXML
    private Button prev;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Text text;

    @FXML
    protected void prevhController(MouseEvent event) throws IOException {
        Stage stage = (Stage)prev.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage.setScene(new Scene(root));
    }

    public void trans_prg(ActionEvent event) throws IOException {
        String text = input_prg.getText();
        engine = prg_view.getEngine();
        String s = translator.translate(from, to, text);
        engine.loadContent(s);
    }

    public void audioAction (ActionEvent event) throws SQLException {
        try {
            TextToSpeech speech = new TextToSpeech();
            speech.toSpeech(input_prg.getText());
        } catch(Exception e) {
            engine.loadContent("<h1>This word doesn't exist</h1>");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**chon ngon ngu.*/
        choiceBox.getItems().addAll(lang);
        text.setText(language);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                language = t1;
                if (language == "Việt-Anh") {
                    from = "vi";
                    to = "en";
                    text.setText(language);
                } else {
                    from = "en";
                    to = "vi";
                    text.setText(language);
                }
            }
        });
    }
}
