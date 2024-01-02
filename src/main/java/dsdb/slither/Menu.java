package dsdb.slither;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;

import java.io.IOException;

public class Menu extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SnakeGame controller = new SnakeGame();
        VBox root= new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(50);

        Button b=new Button("Snake!");
        b.setOnAction(e ->{startGame();stage.close();});
        root.getChildren().add(b);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public void startGame() {
        Game game= new Game();
        GridPane root= game.getGrid();
        root.setAlignment(Pos.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        root.setPrefSize(screenSize.getWidth(), screenSize.getHeight());
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                    game.move(SnakeDirection.UP);
                    game.update();
                    break;
                case DOWN:
                    game.move(SnakeDirection.DOWN);
                    game.update();
                    break;
                case LEFT:
                    game.move(SnakeDirection.LEFT);
                    game.update();
                    break;
                case RIGHT:
                    game.move(SnakeDirection.RIGHT);
                    game.update();
                    break;
            }
        });



        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}