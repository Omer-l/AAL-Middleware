package gui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
	
	public DatabaseEvents(Window prevWindow) {
		super(prevWindow);
	}

	public void open() {
		MainMenu.clearMainBox();
    	MainMenu.changeTitle("Database Events");
        Button button1 = new Button("Back");
        button1.setOnAction(event -> { back(); });
        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        //column1urations
        VBox column1VBox = new VBox(2);
        Text column1Header = new Text("Read Events");
        column1Header.setStyle(MainMenu.HEADER_1_STYLE);
        column1VBox.getChildren().addAll(column1Header);
        getReadEvents(column1VBox);
        
//        column1VBox2.getChildren().addAll(column1VBox2Header);
        
//        column1VBox.getChildren().addAll(column1Header, column1VBox1, column1VBox2);
        mainVBox1.getChildren().addAll(column1VBox);
        //RIGHT SIDE MAIN
        VBox mainVBox2 = new VBox(2);
        mainVBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        //write events
        VBox column2VBox = new VBox(2);
        Text column2Header = new Text("Write Events");
        column2Header.setStyle(MainMenu.HEADER_1_STYLE);

//        column2VBox.getChildren().addAll(column2Header, column2VBoxes,column2VBox2);
        mainVBox2.getChildren().addAll(column2VBox);

//    	MainMenu.addHoverInteraction(new VBox[] {column1VBox1, column2VBox1}, "white", "darkgray");
//    	MainMenu.addHoverInteraction(new VBox[] {column1VBoxes[column1VBoxes.length - 1], column2VBoxes[column2VBoxes.length - 1]}, "yellow", "darkgray");
        MainMenu.mainHBox.getChildren().addAll(mainVBox1, mainVBox2);
	}
	
	public void getReadEvents(VBox readEventsVBox) {
		String query = "SELECT *\n"
				+ "FROM database_read_event\n"
				+ "INNER JOIN event ON database_read_event.unique_id = event.unique_id;";
		ArrayList<Map<String, Object>> readEvents = MainMenu.mainDbManager.queryDB(query, "select");
		for(Map<String, Object> readEvent : readEvents) {
			VBox column1VBox = new VBox();
	        column1VBox.setStyle(MainMenu.MENU_BUTTON_STYLE);
	        Text column1VBoxHeader = new Text((String) readEvent.get("name"));
	        column1VBoxHeader.setStyle(MainMenu.HEADER_2_STYLE);
	        Text column1VBoxUniqueId = new Text((String) readEvent.get("unique_id"));
	        Text column1VBoxDescription = new Text((String) readEvent.get("description"));
	        column1VBox.getChildren().addAll(column1VBoxUniqueId, column1VBoxHeader, column1VBoxDescription);
	        column1VBoxUniqueId.managedProperty().bind(column1VBoxUniqueId.visibleProperty());
	        column1VBoxUniqueId.setVisible(false);
	        column1VBox.setOnMouseClicked(event -> {
	        	if(this.prevWindow instanceof AddWhen) { //add uniqueID
	        		((AddRule) this.prevWindow.prevWindow).whenData.add(column1VBoxDescription.getText().substring(0, column1VBoxDescription.getText().indexOf(" ")));
	        		this.back().back();
	        		this.back().back();
	        	}
	        	
	        	if(this.prevWindow instanceof AddThen) {//add uniqueID
	        		((AddRule) this.prevWindow.prevWindow).thenData.add(column1VBoxDescription.getText().substring(0, column1VBoxDescription.getText().indexOf(" ")));
	        		this.back().back();
	        		this.back().back();
	        	}
	        });
	        readEventsVBox.getChildren().add(column1VBox);
		}
		

        VBox column1VBox2 = new VBox();
        column1VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column1VBox2Header = new Text("Add New Event");
        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);
        column1VBox2.setOnMouseClicked(event -> {new AddDatabaseEvent(this).open();});
        readEventsVBox.getChildren().add(column1VBox2);
	}
}