module com.example.labwork {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.labwork to javafx.fxml;
    exports com.example.labwork;
}