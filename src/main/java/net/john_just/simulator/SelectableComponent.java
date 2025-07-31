package net.john_just.simulator;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class SelectableComponent extends StackPane {
    private boolean selected = false;
    private final Node content;

    public SelectableComponent(Node content) {
        this.content = content;
        this.getChildren().add(content);

        this.setStyle("-fx-border-color: transparent; -fx-border-width: 2;");

        this.setOnMousePressed(e -> {
            SelectionManager.select(this); // выделение
            this.setUserData(new Point2D(e.getSceneX(), e.getSceneY()));
            e.consume();
        });

        this.setOnMouseDragged(e -> {
            Point2D start = (Point2D) this.getUserData();
            if (start == null) return;

            double offsetX = e.getSceneX() - start.getX();
            double offsetY = e.getSceneY() - start.getY();

            this.setLayoutX(this.getLayoutX() + offsetX);
            this.setLayoutY(this.getLayoutY() + offsetY);

            this.setUserData(new Point2D(e.getSceneX(), e.getSceneY()));
        });
    }

    public void setSelected(boolean value) {
        selected = value;
        this.setStyle(selected
                ? "-fx-border-color: blue; -fx-border-width: 2;"
                : "-fx-border-color: transparent; -fx-border-width: 2;");
    }

    public boolean isSelected() {
        return selected;
    }
}
