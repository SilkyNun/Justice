package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;


public class GunController  implements EventHandler<MouseEvent> {
    private ImageView gun;

    public GunController(ImageView gun) {
        this.gun = gun;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        gun.setTranslateX(mouseEvent.getSceneX() - gun.getLayoutX());
        gun.setTranslateY(mouseEvent.getSceneY() - gun.getLayoutY());
    }
}
