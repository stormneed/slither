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
        game.executor.scheduleAtFixedRate(() -> {
            try {
                game.moveRunnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 20, TimeUnit.MILLISECONDS);
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