package gui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
public class DatabaseEvents extends Window {
	MyStyles styles = new MyStyles();
	public DatabaseEvents(Window prevWindow) {
		super(prevWindow);
	}
	
	public DatabaseEvents() {
		
	}

	public void open() {
		MainMenu.clearMainBox();
    	MainMenu.changeTitle("Database Events");
        Button button1 = new Button("Back");
        button1.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9");

        button1.setOnAction(event -> { back(); });
        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        //read events
        VBox column1VBox = new VBox(2);
        Text column1Header = new Text("Read Events");
        column1Header.setStyle(MainMenu.HEADER_1_STYLE);
        column1VBox.getChildren().addAll(column1Header);
        styles.getEvents("SELECT * FROM database_read_event INNER JOIN event ON database_read_event.unique_id = event.unique_id;", column1VBox, this);
        int readEventsLastIndex = column1VBox.getChildren().size() - 1;
        column1VBox.getChildren().get(readEventsLastIndex).setOnMouseClicked(event -> {new AddDatabaseEvent(this).open();});
        mainVBox1.getChildren().addAll(column1VBox);
        //RIGHT SIDE MAIN
        VBox mainVBox2 = new VBox(2);
        mainVBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        //write events
        VBox column2VBox = new VBox(2);
        Text column2Header = new Text("Write Events");
        column2Header.setStyle(MainMenu.HEADER_1_STYLE);
        column2VBox.getChildren().add(column2Header);
        styles.getEvents("SELECT * FROM database_write_event INNER JOIN event ON database_write_event.unique_id = event.unique_id;", column2VBox, this);
        int writeEventsLastIndex = column2VBox.getChildren().size() - 1;
        column2VBox.getChildren().get(writeEventsLastIndex).setOnMouseClicked(event -> { /** add event */ System.out.println("Write event clicked"); });
        mainVBox2.getChildren().addAll(column2VBox);
//START
    	MainMenu.addHoverInteraction(new VBox[] {(VBox) column1VBox.getChildren().get(readEventsLastIndex), (VBox) column2VBox.getChildren().get(writeEventsLastIndex)}, "yellow", "darkgray");
        MainMenu.mainHBox.getChildren().addAll(mainVBox1, mainVBox2);
	}
	
	
}