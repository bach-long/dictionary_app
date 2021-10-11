module sample {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires com.jfoenix;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens sample to javafx.fxml;
    exports sample;
}