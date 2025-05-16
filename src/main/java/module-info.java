module io.github.haminic.graphingcalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.github.haminic.graphingcalculator to javafx.fxml;
    exports io.github.haminic.graphingcalculator;
    exports io.github.haminic.graphingcalculator.application;
    opens io.github.haminic.graphingcalculator.application to javafx.fxml;
}