module xyz.zuner.javafxassignment {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens xyz.zuner.javafxassignment to javafx.fxml;
    opens xyz.zuner.javafxassignment.objects to javafx.base, javafx.fxml, com.google.gson;
    exports xyz.zuner.javafxassignment;
}