module com.example.paints {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires junit;
    requires testfx.core;
    requires testfx.junit;


    opens com.example.paints to javafx.fxml;
    exports com.example.paints;
}