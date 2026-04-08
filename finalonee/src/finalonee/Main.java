package finalonee;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        DatabaseManager.getInstance().initializeDatabase();
        
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showLoginScene();
        
        primaryStage.setTitle("Modern Student Management");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}