package sample;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FireHandler implements EventHandler<KeyEvent> {
    private Controller controller;

    public FireHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            controller.fire();
        }
    }
}
