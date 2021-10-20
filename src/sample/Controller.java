package sample;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private Label score;
    @FXML
    private ImageView explosion;
    @FXML
    private ImageView gunImageView;
    @FXML
    private ImageView bulletImageView;
    @FXML
    private ImageView felonImageView;
    @FXML
    private ImageView goldChain;
    @FXML
    private ImageView felonGun;
    @FXML
    private ImageView policeman;

    private FireHandler fireHandler;
    private FelonController felonController;
    private GunController gunController;

    private TranslateTransition bulletTransition;
    private ScaleTransition explosionTransition;
    private ParallelTransition felonTransitions;

    private int shotCounter = 0;
    private boolean isHit = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initControllers();
        initGoodGuy();
        initBadGuy();
        initTransitions();

        Thread felonThread = new Thread(felonController);
        felonThread.setDaemon(true);
        felonThread.start();

        score.setText("Score : " + shotCounter);
    }



    public void fire() {
        bulletImageView.translateXProperty().unbind();
        bulletImageView.translateYProperty().unbind();
        bulletTransition.play();
    }

    public GunController getGunController() {
        return gunController;
    }

    public FireHandler getFireHandler() {
        return fireHandler;
    }

    private void initControllers() {
        felonController = new FelonController(felonImageView);
        gunController = new GunController(gunImageView);
        fireHandler = new FireHandler(this);
    }

    private void initGoodGuy() {
        initPoliceman();
        initBullet();
    }

    private void initBadGuy() {
        initExplosion();
        initFelonGun();
        initGoldChain();
    }

    private void initExplosion() {
        explosion.setVisible(false);
        explosion.translateXProperty().bind(felonImageView.translateXProperty());
        explosion.translateYProperty().bind(felonImageView.translateYProperty());
    }

    private void initGoldChain() {
        goldChain.translateXProperty().bind(felonImageView.translateXProperty());
        goldChain.translateYProperty().bind(felonImageView.translateYProperty());
        goldChain.scaleXProperty().bind(felonImageView.scaleXProperty());
        goldChain.scaleYProperty().bind(felonImageView.scaleYProperty());
        goldChain.visibleProperty().bind(felonImageView.visibleProperty());
    }

    private void initFelonGun() {
        felonGun.translateXProperty().bind(felonImageView.translateXProperty());
        felonGun.translateYProperty().bind(felonImageView.translateYProperty());
        felonGun.scaleXProperty().bind(felonImageView.scaleXProperty());
        felonGun.scaleYProperty().bind(felonImageView.scaleYProperty());
        felonGun.visibleProperty().bind(felonImageView.visibleProperty());
    }

    private void initTransitions() {
        explosionTransition = new ScaleTransition(Duration.millis(2000), explosion);
        explosionTransition.setFromX(1d);
        explosionTransition.setFromY(1d);
        explosionTransition.setToX(2d);
        explosionTransition.setToY(2d);

        ScaleTransition felonScale = new ScaleTransition(Duration.millis(500), felonImageView);
        felonScale.setFromX(1d);
        felonScale.setFromY(1d);
        felonScale.setToX(0d);
        felonScale.setToY(0d);
        felonScale.setOnFinished(actionEvent -> {
            felonImageView.setVisible(false);
            felonImageView.setScaleX(1d);
            felonImageView.setScaleY(1d);
        });

        RotateTransition felonRotate = new RotateTransition(Duration.millis(500), felonImageView);
        felonRotate.setByAngle(360);

        felonTransitions = new ParallelTransition(felonScale, felonRotate);

        bulletTransition = new TranslateTransition();
        bulletTransition.setNode(bulletImageView);
        bulletTransition.setByX(1000);
        bulletTransition.autoReverseProperty().setValue(true);
        bulletTransition.setOnFinished(event -> {
            bulletImageView.translateXProperty().bind(gunImageView.translateXProperty());
            bulletImageView.translateYProperty().bind(gunImageView.translateYProperty());
        });
    }

    private void initPoliceman() {
        policeman.translateXProperty().bind(gunImageView.translateXProperty());
        policeman.translateYProperty().bind(gunImageView.translateYProperty());
    }

    private void initBullet() {
        bulletImageView.translateXProperty().bind(gunImageView.translateXProperty());
        bulletImageView.translateYProperty().bind(gunImageView.translateYProperty());
        bulletImageView.boundsInParentProperty().addListener((observableValue, bounds, t1) -> {
            if (felonImageView.getBoundsInParent().intersects(t1) && !isHit) {
                shotCounter++;
                Platform.runLater(() -> {
                    explosion.setVisible(true);
                    score.setText("Score : " + shotCounter);
                });
                explosionTransition.play();
                felonTransitions.play();
                isHit = true;
            } else {
                Platform.runLater(() -> {
                    explosion.setVisible(false);
                });
                isHit = false;
            }
        });
    }
}
