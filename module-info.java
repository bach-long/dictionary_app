module group1.learn_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens group1.learn_fx to javafx.fxml;
    exports group1.learn_fx;
}