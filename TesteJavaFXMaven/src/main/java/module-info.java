module org.example.testejavafxmaven {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.testejavafxmaven to javafx.fxml;
    exports org.example.testejavafxmaven;

    requires java.sql;
    requires com.h2database;
}