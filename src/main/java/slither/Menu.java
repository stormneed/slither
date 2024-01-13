package slither;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

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
        System.out.println("Starting game...");
        Game game= new Game();
        AnchorPane root= game.getGrid();
        root.setPrefSize(800,800);
        Scene scene = new Scene(root);
        game.executor.scheduleAtFixedRate(game.moveRunnable, 0,20, TimeUnit.MILLISECONDS);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP -> {game.getPlayers().get(0).setSnakeDirection(SnakeDirection.UP);}
                case DOWN -> {game.getPlayers().get(0).setSnakeDirection(SnakeDirection.DOWN);}
                case LEFT -> {game.getPlayers().get(0).setSnakeDirection(SnakeDirection.LEFT);}
                case RIGHT -> {game.getPlayers().get(0).setSnakeDirection(SnakeDirection.RIGHT);}
                case Z -> {game.getPlayers().get(2).setSnakeDirection(SnakeDirection.UP);}
                case S -> {game.getPlayers().get(2).setSnakeDirection(SnakeDirection.DOWN);}
                case Q -> {game.getPlayers().get(2).setSnakeDirection(SnakeDirection.LEFT);}
                case D -> {game.getPlayers().get(2).setSnakeDirection(SnakeDirection.RIGHT);}
            };






        });
        Stage stage = new Stage();
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.onCloseRequestProperty().setValue(e -> {
            game.executor.shutdown();
            System.exit(0);
        });
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}