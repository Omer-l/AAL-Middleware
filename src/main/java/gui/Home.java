package gui;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Home extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create sidebar buttons
        Button homeButton = new Button("Home");
        Button profileButton = new Button("Profile");
        Button settingsButton = new Button("Settings");

        // Set preferred width for the sidebar buttons
        homeButton.setPrefWidth(120);
        profileButton.setPrefWidth(120);
        settingsButton.setPrefWidth(120);

        // Create a VBox to hold the sidebar buttons
        VBox sidebar = new VBox(homeButton, profileButton, settingsButton);
        sidebar.setSpacing(10);
        sidebar.setPadding(new Insets(10));

        // Set styling for the sidebar
        sidebar.setStyle("-fx-background-color: #ffffff");

        // Set the preferred width for the sidebar
        sidebar.setPrefWidth(150);
        VBox nextBar = new VBox();
        
        HBox hbox = new HBox(sidebar, nextBar);
        // Create the main scene
        Scene scene = new Scene(hbox, 800, 600);

        // Set the scene on the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Sidebar Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
