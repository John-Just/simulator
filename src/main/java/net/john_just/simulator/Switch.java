package net.john_just.simulator;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Switch extends Component {
    private final int polyCount;
    protected RatedCurrent ratedCurrent;
    private boolean enabled = false; // начально включён
    private final double pinRadius = 5;
    private final double spacing = 17.5;
    private final double boxHeight = 90;

    public Switch(int count, RatedCurrent ratedCurrent) {
        super(2 * count);
        this.polyCount = count;
        this.ratedCurrent = ratedCurrent;
    }

    @Override
    public void update(double time) {
        for (int i = 0; i < polyCount; i++) {
            Terminal top = terminals.get(i);
            Terminal bottom = terminals.get(i + polyCount);

            if (enabled) {
                top.connectTo(bottom);
            } else {
                top.disconnectFrom(bottom);
            }
        }
    }


    @Override
    public Node createView() {
        double boxWidth = spacing * polyCount;

        Pane root = new Pane();
        root.setPrefSize(boxWidth, boxHeight);

        Rectangle base = new Rectangle(boxWidth, boxHeight);
        base.setFill(Color.LIGHTGRAY);
        base.setStroke(Color.BLACK);
        root.getChildren().add(base);

        Text ratedText = new Text(ratedCurrent.toString());
        ratedText.setFont(Font.font(10));
        ratedText.setX(5);
        ratedText.setY(50);
        root.getChildren().add(ratedText);

        // Кнопка визуального состояния
        Rectangle indicator = new Rectangle(boxWidth, 10);
        indicator.setFill(enabled ? Color.LIMEGREEN : Color.RED);
        root.getChildren().add(indicator);

        for (int i = 0; i < polyCount; i++) {
            double baseX = i * spacing + spacing / 2;

            Circle topPin = new Circle(baseX, 10, pinRadius, Color.DARKGRAY);
            topPin.setStroke(Color.BLACK);

            Circle bottomPin = new Circle(baseX, boxHeight - 10, pinRadius, Color.DARKGRAY);
            bottomPin.setStroke(Color.BLACK);

            root.getChildren().addAll(topPin, bottomPin);
        }

        // Клик для переключения
        root.setOnMouseClicked(e -> {
            toggle();
            indicator.setFill(enabled ? Color.LIMEGREEN : Color.RED);
        });

        Pane wrapper = new Pane(root);
        wrapper.setLayoutX(400);
        wrapper.setLayoutY(100);
        return wrapper;
    }

    public void toggle() {
        enabled = !enabled;
        update(0);  // сразу обновляем связи при переключении
    }
    public boolean isEnabled() {
        return enabled;
    }
}
