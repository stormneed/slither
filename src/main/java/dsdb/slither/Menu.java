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
        root.setPrefSize(1000,1000);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> {
            SnakeDirection dir = switch (e.getCode()) {
                case UP -> SnakeDirection.UP;
                case DOWN -> SnakeDirection.DOWN;
                case LEFT -> SnakeDirection.LEFT;
                case RIGHT -> SnakeDirection.RIGHT;
                default -> SnakeDirection.NONE;
            };
            game.move(dir);
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