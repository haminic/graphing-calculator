package io.github.haminic.graphingcalculator.application;

import io.github.haminic.graphingcalculator.equation.utils.EquationController;
import io.github.haminic.graphingcalculator.equation.utils.EquationManager;
import io.github.haminic.graphingcalculator.expression.utils.SymbolManager;
import io.github.haminic.graphingcalculator.graph.GraphManager;
import io.github.haminic.graphingcalculator.graph.Point;
import io.github.haminic.graphingcalculator.gui.equation.EquationContainer;
import io.github.haminic.graphingcalculator.gui.graph.GraphCanvas;
import io.github.haminic.graphingcalculator.gui.graph.GraphPane;
import io.github.haminic.graphingcalculator.gui.misc.TopBar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GraphingCalculator extends Application {
    @Override
    public void start(Stage primaryStage) {
        EquationManager manager = new EquationManager(
                new SymbolManager(),
                new GraphManager(new Point(0, 0), 10, 9/7.0, 50000)
        );
        EquationContainer container = new EquationContainer();
        EquationController controller = new EquationController(manager, container);
        GraphCanvas canvas = new GraphCanvas(900, manager.getGraphManager());
        manager.addListener(canvas);

        GraphPane graphPane = new GraphPane(canvas, manager);

        HBox body = new HBox();
        body.getChildren().addAll(controller.getEquationContainer(), graphPane);
        TopBar topBar = new TopBar(controller);
        VBox applicationPane = new VBox();
        applicationPane.getChildren().addAll(topBar, body);

        Scene scene = new Scene(applicationPane);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Graphing Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
