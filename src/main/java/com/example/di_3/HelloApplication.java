package com.example.di_3;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
public class HelloApplication extends Application {
    Rectangle paleta1 = new Rectangle(15, 80);Rectangle paleta2 = new Rectangle(15, 80);Circle balon = new Circle(10, 10, 10);Label pJugadorUno = new Label();Label pJugadorDos = new Label();
    private boolean downRight, downLeft, upRight, upLeft;
    private int velMovY = -5;private int velMovX = -5;private int sizeScreenX, sizeScreenY;private int pl1=0;private int pl2=0;
    @Override
    public void start(Stage stage) throws IOException {
        paleta1.setFill(Color.rgb(200, 255, 100));
        paleta2.setFill(Color.rgb(255, 200, 100));
        balon.setFill(Color.rgb(100, 255, 200));
        pJugadorUno.setTextFill(Color.rgb(200, 255, 100));//PUNTOS J1
        pJugadorDos.setTextFill(Color.rgb(255, 200, 100));//PUNTOS J2
        Scene scene = new Scene(
                new Pane(paleta1,paleta2,balon,pJugadorUno,pJugadorDos)
                {{
                    pJugadorUno.setText(String.valueOf(pl1));pJugadorUno.setFont(new Font("Italic", 100));
                    pJugadorDos.setText(String.valueOf(pl2));pJugadorDos.setFont(new Font("Italic", 100));
                    requestFocus();
                    setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
                    widthProperty().addListener((observableValue, number, t1) -> {
                        paleta1.setLayoutX(10);
                            paleta2.setLayoutX(t1.intValue() - 25);
                            balon.setLayoutX(t1.floatValue() / 2.f - balon.getRadius() / 2);
                            pJugadorUno.setLayoutX(t1.intValue() / 2 + 200);
                            pJugadorDos.setLayoutX(t1.intValue() / 2 - 200);
                            sizeScreenX =t1.intValue();
                    });
                    heightProperty().addListener((observableValue, number, t1) -> {
                        paleta1.setLayoutY(t1.floatValue() / 2.f - paleta1.getHeight() / 2);
                            paleta2.setLayoutY(t1.floatValue() / 2.f - paleta2.getHeight() / 2);
                            balon.setLayoutY(t1.floatValue() / 2.f - balon.getRadius() / 2);
                            sizeScreenY =t1.intValue();
                    });

                }}, 1000, 700) {{
            setOnKeyPressed(keyEvent ->
            {
                if (keyEvent.getCode() == KeyCode.W) {
                    upLeft = !(paleta1.getBoundsInParent().getMinY()+10 <= 20);
                }
                if (keyEvent.getCode() == KeyCode.S) {
                    downLeft = !(paleta1.getBoundsInParent().getMaxY()-10 > getHeight() - 40);
                }
                if (keyEvent.getCode() == KeyCode.UP) {
                    upRight = !(paleta2.getBoundsInParent().getMinY()+10 <= 20);
                }
                if (keyEvent.getCode() == KeyCode.DOWN) {
                    downRight = !(paleta2.getBoundsInParent().getMaxY()-10 > getHeight() - 40);
                }
            });
            setOnKeyReleased(keyEvent ->
            {
                if (keyEvent.getCode() == KeyCode.W)
                    upLeft = false;
                if (keyEvent.getCode() == KeyCode.S )
                    downLeft = false;
                if (keyEvent.getCode() == KeyCode.UP)
                    upRight = false;
                if (keyEvent.getCode() == KeyCode.DOWN )
                    downRight = false;
            });
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(30), actionEvent ->
            {

                paleta1.setY(upLeft ? paleta1.getY() : paleta1.getY() + 10);
                paleta1.setY(downLeft ? paleta1.getY() : paleta1.getY() - 10);
                paleta2.setY(upRight ? paleta2.getY() : paleta2.getY() + 10);
                paleta2.setY(downRight ? paleta2.getY() : paleta2.getY() - 10);
                if (balon.getLayoutY() + velMovY == 0 || balon.getLayoutY() + velMovY == sizeScreenY -10)
                    velMovY *=-1;
                Shape choquePala1=Shape.intersect(paleta1, balon);
                Shape choquePala2=Shape.intersect(paleta2, balon);
                if (!choquePala1.getBoundsInLocal().isEmpty()||!choquePala2.getBoundsInLocal().isEmpty())
                    velMovX *=-1;

                if (balon.getLayoutX()== 0)
                {
                    pl1++;
                    pJugadorUno.setText(String.valueOf(pl1));
                    balon.setLayoutX(sizeScreenX / 2.f - balon.getRadius() / 2);
                    balon.setLayoutY(sizeScreenY / 2.f - balon.getRadius() / 2);
                }
                if (balon.getLayoutX()== sizeScreenX)
                {
                    pl2++;
                    pJugadorDos.setText(String.valueOf(pl2));
                    balon.setLayoutX(sizeScreenX / 2.f - balon.getRadius() / 2);
                    balon.setLayoutY(sizeScreenY / 2.f - balon.getRadius() / 2);
                }
                balon.setLayoutX(balon.getLayoutX() + velMovX);
                balon.setLayoutY(balon.getLayoutY() + velMovY);
            }
            ));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }};

        stage.setTitle("PongFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}