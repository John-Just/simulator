package net.john_just.simulator;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloApplication extends Application {

    private long startNanoTime;

    @Override
    public void start(Stage stage) throws IOException {
        VBox sidebar = new VBox(10);
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-border-color: gray; -fx-border-width: 1px;");

        Pane root = new Pane(); // основная сцена
        HBox layout = new HBox(sidebar, root); // комбинируем боковое меню и рабочую область

        HBox.setHgrow(root, Priority.ALWAYS);

        Scene scene = new Scene(layout, 1920, 1080);

        Map<String, List<ComponentTemplate>> componentGroups = new HashMap<>();

        componentGroups.put("Источники питания", List.of(
                new ComponentTemplate("3-фазный источник", () -> {
                    ThreePhaseSource src = new ThreePhaseSource(230, 50);
                    return new SelectableComponent(src.createView());
                })
        ));

        componentGroups.put("Коммутация", List.of(
                new ComponentTemplate("Рубильник (1P)", () -> {
                    Switch sw = new Switch(1, RatedCurrent.A63);
                    return new SelectableComponent(sw.createView());
                }),
                new ComponentTemplate("Рубильник (3P)", () -> {
                    Switch sw = new Switch(3, RatedCurrent.A63);
                    return new SelectableComponent(sw.createView());
                })
        ));

        componentGroups.put("Нагрузка", List.of(
                new ComponentTemplate("Лампа ˜230В", () -> {
                    Lamp lamp = new Lamp();
                    return new SelectableComponent(lamp.createView());
                })
        ));

        for (var entry : componentGroups.entrySet()) {
            String groupName = entry.getKey();
            List<ComponentTemplate> templates = entry.getValue();

            Label title = new Label(groupName);
            VBox groupBox = new VBox(5);
            groupBox.getChildren().add(title);

            for (ComponentTemplate template : templates) {
                Button button = new Button(template.getName());
                button.setOnAction(e -> {
                    Node instance = template.createInstance();
                    instance.setLayoutX(300); // Начальная позиция
                    instance.setLayoutY(200);
                    root.getChildren().add(instance);
                });
                groupBox.getChildren().add(button);
            }

            sidebar.getChildren().add(groupBox);
        }

        ThreePhaseSource source = new ThreePhaseSource(230, 50);
        Lamp lamp = new Lamp();
        Switch sw = new Switch(1, RatedCurrent.A6);

        Node sourceNode = new SelectableComponent(source.createView());
        Node lampNode = new SelectableComponent(lamp.createView());
        Node swNode = new SelectableComponent(sw.createView());


        Terminal lampL = lamp.getTerminals().get(0);
        Terminal lampN = lamp.getTerminals().get(1);

        Terminal swIn = sw.getTerminals().get(0);
        Terminal swOut = sw.getTerminals().get(1);

        Terminal sourceL = source.getTerminals().get(0); // L1
        Terminal sourceN = source.getTerminals().get(3); // N

        swIn.connectTo(sourceL);
        lampL.connectTo(swOut);
        lampN.connectTo(sourceN);

        scene.setOnMousePressed(e -> {
            if (e.getTarget() == root) {
                SelectionManager.clear();
            }
        });

        Button toggleButton = new Button("☰"); // иконка меню
        toggleButton.setOnAction(e -> {
            sidebar.setVisible(!sidebar.isVisible());
            sidebar.setManaged(sidebar.isVisible()); // чтобы скрывать и занимание места
        });

        root.getChildren().add(toggleButton);
        root.getChildren().add(sourceNode);
        root.getChildren().add(swNode);
        root.getChildren().add(lampNode);

        stage.setTitle("Electrical Simulator");
        stage.setScene(scene);
        stage.show();

        startNanoTime = System.nanoTime();

        new AnimationTimer() {
            long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate < 16_000_000) return;
                lastUpdate = now;

                double t = (now - startNanoTime) / 1_000_000_000.0;
                source.update(t);
                lamp.update(t);
                sw.update(t);
            }
        }.start();

    }

    public static void main(String[] args) {
        launch();
    }
}
