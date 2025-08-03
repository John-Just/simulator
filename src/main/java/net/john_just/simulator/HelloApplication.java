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
import java.util.ArrayList;
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
        List<Component> allComponents = new ArrayList<>();


        componentGroups.put("Источники питания", List.of(
                new ComponentTemplate("3-фазный источник", () -> {
                    ThreePhaseSource src = new ThreePhaseSource(230, 50);
                    allComponents.add(src); // ⬅ добавляем компонент в список
                    return new SelectableComponent(src.createView());
                })
        ));

        componentGroups.put("Коммутация", List.of(
                new ComponentTemplate("Рубильник (1P)", () -> {
                    Switch sw = new Switch(1, RatedCurrent.A63);
                    allComponents.add(sw); // ⬅ добавляем компонент в список
                    return new SelectableComponent(sw.createView());
                }),
                new ComponentTemplate("Рубильник (3P)", () -> {
                    Switch sw = new Switch(3, RatedCurrent.A63);
                    allComponents.add(sw); // ⬅ добавляем компонент в список
                    return new SelectableComponent(sw.createView());
                }),
                new ComponentTemplate(" Кнопка НО", () -> {
                    SB sb = new SB(List.of(ContactType.NO));
                    allComponents.add(sb); // ⬅ добавляем компонент в список
                    return new SelectableComponent(sb.createView());
                }),
                new ComponentTemplate(" Кнопка НЗ", () -> {
                    SB sb = new SB(List.of(ContactType.NC));
                    allComponents.add(sb); // ⬅ добавляем компонент в список
                    return new SelectableComponent(sb.createView());
                })
        ));

        componentGroups.put("Нагрузка", List.of(
                new ComponentTemplate("Лампа ˜230В", () -> {
                    Lamp lamp = new Lamp();
                    allComponents.add(lamp); // ⬅ добавляем компонент в список
                    return new SelectableComponent(lamp.createView());
                }),
                new ComponentTemplate("Лампа индикаторная белая", () -> {
                    IndicatorLamp lamp = new IndicatorLamp(IndicatorColor.WHITE);
                    allComponents.add(lamp);
                    return new SelectableComponent(lamp.createView());
                }),
                new ComponentTemplate("Лампа индикаторная красная", () -> {
                    IndicatorLamp lamp = new IndicatorLamp(IndicatorColor.RED);
                    allComponents.add(lamp);
                    return new SelectableComponent(lamp.createView());
                }),
                new ComponentTemplate("Лампа индикаторная зеленая", () -> {
                    IndicatorLamp lamp = new IndicatorLamp(IndicatorColor.GREEN);
                    allComponents.add(lamp);
                    return new SelectableComponent(lamp.createView());
                }),
                new ComponentTemplate("Лампа индикаторная синяя", () -> {
                    IndicatorLamp lamp = new IndicatorLamp(IndicatorColor.BLUE);
                    allComponents.add(lamp);
                    return new SelectableComponent(lamp.createView());
                }),
                new ComponentTemplate("Лампа индикаторная желтая", () -> {
                    IndicatorLamp lamp = new IndicatorLamp(IndicatorColor.YELLOW);
                    allComponents.add(lamp);
                    return new SelectableComponent(lamp.createView());
                }),
                new ComponentTemplate("Реле", () -> {
                    Relay relay = new Relay(List.of(ContactType.NO, ContactType.NC));
                    allComponents.add(relay);
                    return new SelectableComponent(relay.createView());
                }),
                new ComponentTemplate("Контактор", () -> {
                    Сontactor contactor = new Сontactor(List.of(ContactType.NO, ContactType.NO, ContactType.NO, ContactType.NO));
                    allComponents.add(contactor);
                    return new SelectableComponent(contactor.createView());
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
        Switch sw_2 = new Switch(1, RatedCurrent.A6);

        Node sourceNode = new SelectableComponent(source.createView());
        Node lampNode = new SelectableComponent(lamp.createView());
        Node swNode = new SelectableComponent(sw.createView());
        Node sw_2_Node = new SelectableComponent(sw_2.createView());


        Terminal lampL = lamp.getTerminals().get(0);
        Terminal lampN = lamp.getTerminals().get(1);

        Terminal swIn = sw.getTerminals().get(0);
        Terminal swOut = sw.getTerminals().get(1);

        Terminal sw_2In = sw_2.getTerminals().get(0);
        Terminal sw_2Out = sw_2.getTerminals().get(1);

        Terminal sourceL = source.getTerminals().get(0); // L1
        Terminal sourceN = source.getTerminals().get(3); // N

        swIn.connectTo(sourceL);
        sw_2In.connectTo(swOut);
        lampL.connectTo(sw_2Out);
        lampN.connectTo(sourceN);

        allComponents.addAll(List.of(source, lamp, sw, sw_2));

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
        root.getChildren().add(sw_2_Node);
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

                resetAllVoltages(allComponents);

                // Шаг 1: обновляем состояния (например, замкнуты ли контакты)
                for (Component c : allComponents) {
                    c.update(t);
                }

                // Шаг 2: распространяем напряжение заново
                for (Component c : allComponents) {
                    for (Terminal tl : c.getTerminals()) {
                        if (Math.abs(tl.getVoltage()) > 1e-3) { // если это источник
                            VoltagePropagator.propagateFrom(tl);
                        }
                    }
                }
            }
        }.start();

    }

    public static void main(String[] args) {
        launch();
    }

    private void resetAllVoltages(List<Component> components) {
        for (Component c : components) {
            for (Terminal t : c.getTerminals()) {
                t.setVoltage(0.0);
            }
        }
    }
}
