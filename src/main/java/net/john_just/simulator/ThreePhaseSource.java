package net.john_just.simulator;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ThreePhaseSource extends Component {
    private final double voltageRMS;
    private final double frequency;

    private final double timeOffsetL1 = 0;
    private final double timeOffsetL2 = -2 * Math.PI / 3;
    private final double timeOffsetL3 = 2 * Math.PI / 3;

    public ThreePhaseSource(double voltageRMS, double frequency) {
        super(5); // 3 фазы + нейтраль + земля
        this.voltageRMS = voltageRMS;
        this.frequency = frequency;
    }

    @Override
    public void update(double time) {
        double w = 2 * Math.PI * frequency;

        double v1 = voltageRMS * Math.sqrt(2) * Math.sin(w * time + timeOffsetL1);
        double v2 = voltageRMS * Math.sqrt(2) * Math.sin(w * time + timeOffsetL2);
        double v3 = voltageRMS * Math.sqrt(2) * Math.sin(w * time + timeOffsetL3);

        terminals.get(0).setVoltage(v1); // L1
        terminals.get(1).setVoltage(v2); // L2
        terminals.get(2).setVoltage(v3); // L3
        terminals.get(3).setVoltage(0.0); // N
        terminals.get(4).setVoltage(0.0); // PE

        // ⚡ распространение напряжения
        VoltagePropagator.propagateFrom(terminals.get(0)); // L1
        VoltagePropagator.propagateFrom(terminals.get(1)); // L2
        VoltagePropagator.propagateFrom(terminals.get(2)); // L3
        VoltagePropagator.propagateFrom(terminals.get(3)); // N
    }



    @Override
    public Node createView() {
        Pane root = new Pane();
        root.setPrefSize(120, 25);

        Rectangle base = new Rectangle(120, 25);
        base.setFill(Color.LIGHTGRAY);
        base.setStroke(Color.BLACK);

        Text label = new Text(name);
        label.setFont(Font.font(12));
        label.setLayoutX(10);
        label.setLayoutY(20);

        root.getChildren().add(base); // Сначала фон, чтобы он был внизу

        String[] labels = {"L1", "L2", "L3", "N", "PE"};
        for (int i = 0; i < terminals.size(); i++) {
            TerminalView terminalView = new TerminalView(terminals.get(i));
            terminalView.setLayoutX(10 + i * 25);
            terminalView.setLayoutY(8);

            Text terminalLabel = new Text(labels[i]);
            terminalLabel.setFont(Font.font(10));
            terminalLabel.setLayoutX(terminalView.getLayoutX() - 5);
            terminalLabel.setLayoutY(30);

            root.getChildren().addAll(terminalView, terminalLabel);
        }


        root.getChildren().add(label); // Текст поверх

        // Оборачиваем компонент, чтобы можно было задать позицию на сцене
        Pane wrapper = new Pane();
        wrapper.getChildren().add(root);
        wrapper.setLayoutX(100);
        wrapper.setLayoutY(100);

        return wrapper;
    }

}
