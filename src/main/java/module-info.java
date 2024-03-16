module xyz.zuner.javafxassignment {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens xyz.zuner.javafxassignment to javafx.fxml;
    opens xyz.zuner.javafxassignment.objects to javafx.base, javafx.fxml;
    exports xyz.zuner.javafxassignment;
}