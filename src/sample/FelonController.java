package sample;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;


public class FelonController implements Runnable {
    private ImageView felon;
    private Point2D currentLocation;

    public FelonController(ImageView felon) {
        this.felon = felon;
        currentLocation = new Point2D(felon.getLayoutX(), felon.getLayoutY());
        felon.setVisible(false);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId());
        try {
            while (true) {
                currentLocation = new Point2D(currentLocation.getX(), Math.random()* 350 + 50);
                Platform.runLater(() -> {
                    felon.setTranslateX(currentLocation.getX() - felon.getLayoutX());
                    felon.setTranslateY(currentLocation.getY() - felon.getLayoutY());
                    felon.setVisible(true);
                });
                Thread.sleep(1500);
            }
        } catch (InterruptedException ignore) {}
    }

}
