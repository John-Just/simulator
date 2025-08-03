package net.john_just.simulator;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SB extends Component {
    private boolean pressed = false;
    private final List<Contact> contacts = new ArrayList<>();
    private final List<ContactType> types = new ArrayList<>();
    private final double spacing = 20;
    private final double boxHeight = 60;

    public SB(List<ContactType> contactTypes) {
        super(contactTypes.size() * 2);
        this.types.addAll(contactTypes);

        for (int i = 0; i < contactTypes.size(); i++) {
            Terminal a = getTerminals().get(i);
            Terminal b = getTerminals().get(i + contactTypes.size());
            Contact contact = new Contact(a, b, contactTypes.get(i));
            contacts.add(contact);
        }
    }

    @Override
    public void update(double time) {}

    @Override
    public Node createView() {
        double boxWidth = spacing * types.size();

        Pane root = new Pane();
        root.setPrefSize(boxWidth, boxHeight);

        Rectangle base = new Rectangle(boxWidth, boxHeight);
        base.setFill(Color.BEIGE);
        base.setStroke(Color.BLACK);
        root.getChildren().add(base);

        Text label = new Text("Кнопка");
        label.setFont(Font.font(10));
        label.setLayoutX(5);
        label.setLayoutY(15);
        root.getChildren().add(label);

        Rectangle indicator = new Rectangle(boxWidth, 10);
        indicator.setFill(pressed ? Color.LIMEGREEN : Color.RED);
        root.getChildren().add(indicator);

        for (int i = 0; i < types.size(); i++) {
            double x = i * spacing + spacing / 2;

            TerminalView top = new TerminalView(getTerminals().get(i));
            top.setLayoutX(x);
            top.setLayoutY(10);

            TerminalView bottom = new TerminalView(getTerminals().get(i + types.size()));
            bottom.setLayoutX(x);
            bottom.setLayoutY(boxHeight - 10);

            root.getChildren().addAll(top, bottom);
        }

        root.setOnMousePressed(e -> {
            pressed = true;
            indicator.setFill(Color.LIMEGREEN);
            updateContacts();
        });

        root.setOnMouseReleased(e -> {
            pressed = false;
            indicator.setFill(Color.RED);
            updateContacts();
        });

        return new Pane(root);
    }

    private void updateContacts() {
        for (Contact contact : contacts) {
            contact.setActivated(pressed); // только это!
        }
    }
}
