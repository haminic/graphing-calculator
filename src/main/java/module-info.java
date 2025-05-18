module io.github.haminic.graphingcalculator {
    requires javafx.controls;
    requires javafx.fxml;


    exports io.github.haminic.graphingcalculator;
    opens io.github.haminic.graphingcalculator to javafx.fxml;
}