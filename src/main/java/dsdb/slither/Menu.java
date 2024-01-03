package dsdb.slither;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;

import static java.lang.Thread.sleep;


public class Menu extends Application {
    @Override
    public void start(Stage stage) {
        VBox root= new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(50);

        Button b=new Button("Snake!");
        b.setOnAction(e ->{startGame();stage.close();});
        root.getChildren().add(b);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Slither/Snake!");
        stage.setScene(scene);
        stage.show();
    }



    public void startGame() {
        Game game= new Game();
        AnchorPane root= game.getGrid();
        root.setPrefSize(800,800);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> {
            SnakeDirection dir = switch (e.getCode()) {
                case UP -> SnakeDirection.UP;
                case DOWN -> SnakeDirection.DOWN;
                case LEFT -> SnakeDirection.LEFT;
                case RIGHT -> SnakeDirection.RIGHT;
                default -> SnakeDirection.NONE;
            };
            game.move(dir,game.players.get(0));
            try {
                sleep(2);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            game.move(game.directionIA(),game.players.get(1));

        });
        Stage stage = new Stage();
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}