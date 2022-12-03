module edu.farmingdale.csc311_finalproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens edu.farmingdale.csc311_finalproj to javafx.fxml;
    exports edu.farmingdale.csc311_finalproj;
}
