package suic._2021.days.day09;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import suic.util.MathUtils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Day09Visualization {

   /* private final int WIDTH = 600;
    private final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {

        AtomicBoolean started = new AtomicBoolean();
        Group root = new Group();
        Day09 day = new Day09();
        day.parse();
        int maxX = day.maxX;
        int maxY = day.maxY;
        AtomicInteger curX = new AtomicInteger(1);
        AtomicInteger curY = new AtomicInteger(1);

        int[][] input = day.input;
        int scale = WIDTH / maxX;
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                int value = input[x][y];
                Rectangle rect = new Rectangle(x * scale, y * scale, scale, scale);
                float hue = MathUtils.map(value, 0, 9, 355, 5);
                Color color = Color.hsb(hue, 1, 1f);
                rect.setFill(color);
                rect.setStroke(Color.BLACK);
                root.getChildren().add(rect);
            }
        }

        Timeline timeline;
        timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {

            if (started.get()) {
                for (int x = 0; x < curX.get(); x++) {
                    for (int y = 0; y < curY.get(); y++) {
                        day.computeBasinSize(x, y);
                        int value = input[x][y];
                        float hue = MathUtils.map(value, 0, 9, 355, 5);
                        Color color = Color.hsb(hue, 1, 1f);
                        ((Rectangle) root.getChildren().get(x + y * 100)).setFill(color);
                    }
                }

                if(curX.get() < 100) {
                    curX.getAndIncrement();
                    curY.getAndIncrement();
                }

            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);

        scene.setOnMouseClicked(event -> {
            started.set(true);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }*/
}
