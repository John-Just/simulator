package net.john_just.simulator;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class Сontactor extends ElectromagneticDevice {

    public Сontactor(List<ContactType> types) {
        super(types.size(), types);
    }

    @Override
    public Node createView() {
        double width = 100;
        double height = 90;

        Pane root = new Pane();
        root.setPrefSize(width, height);

        Rectangle base = new Rectangle(width, height);
        base.setFill(Color.LIGHTGRAY);
        base.setStroke(Color.BLACK);
        root.getChildren().add(base);

        Text label = new Text("Контактор");
        label.setFont(Font.font(14));
        label.setX(10);
        label.setY(20);
        root.getChildren().add(label);

        // Отрисовка контактов
        double spacing = width / (types.size() + 1);
        for (int i = 0; i < types.size(); i++) {
            double x = spacing * (i + 1);

            // Верхний контакт
            TerminalView top = new TerminalView(getTerminals().get(i));
            top.setLayoutX(x);
            top.setLayoutY(30);
            root.getChildren().add(top);

            // Нижний контакт
            TerminalView bottom = new TerminalView(getTerminals().get(i + types.size()));
            bottom.setLayoutX(x);
            bottom.setLayoutY(height - 30);
            root.getChildren().add(bottom);

            // Тип контакта (НО / НЗ)
            Text typeText = new Text(types.get(i).name());
            typeText.setFont(Font.font(10));
            typeText.setX(x - 10);
            typeText.setY(height / 2);
            root.getChildren().add(typeText);
        }

        // Катушка слева
        TerminalView coilAView = new TerminalView(coilA);
        coilAView.setLayoutX(10);
        coilAView.setLayoutY(height / 2);
        root.getChildren().add(coilAView);

        TerminalView coilBView = new TerminalView(coilB);
        coilBView.setLayoutX(10);
        coilBView.setLayoutY(height / 2 + 20);
        root.getChildren().add(coilBView);

        Text coilLabel = new Text("Катушка");
        coilLabel.setFont(Font.font(10));
        coilLabel.setX(5);
        coilLabel.setY(height - 4);
        root.getChildren().add(coilLabel);

        return root;
    }
}
