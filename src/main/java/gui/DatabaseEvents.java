package gui;
import java.util.ArrayList;
import java.util.Arrays;
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
        //read events
        VBox column1VBox = new VBox(2);
        Text column1Header = new Text("Read Events");
        column1Header.setStyle(MainMenu.HEADER_1_STYLE);
        column1VBox.getChildren().addAll(column1Header);
        getEvents("SELECT * FROM database_read_event INNER JOIN event ON database_read_event.unique_id = event.unique_id;", column1VBox);
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
        getEvents("SELECT * FROM database_write_event INNER JOIN event ON database_write_event.unique_id = event.unique_id;", column2VBox);
        int writeEventsLastIndex = column2VBox.getChildren().size() - 1;
        column2VBox.getChildren().get(writeEventsLastIndex).setOnMouseClicked(event -> { /** add event */ System.out.println("Write event clicked"); });
        mainVBox2.getChildren().addAll(column2VBox);

    	MainMenu.addHoverInteraction(new VBox[] {(VBox) column1VBox.getChildren().get(readEventsLastIndex), (VBox) column2VBox.getChildren().get(writeEventsLastIndex)}, "yellow", "darkgray");
        MainMenu.mainHBox.getChildren().addAll(mainVBox1, mainVBox2);
	}
	
	public void getEvents(String query, VBox eventsVBox) {
		ArrayList<Map<String, Object>> events = MainMenu.mainDbManager.queryDB(query, "select");
		for(Map<String, Object> readEvent : events) {
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
	        		ArrayList<String> whenEvent = new ArrayList<String>();
	        		whenEvent.add(column1VBoxHeader.getText());
	        		whenEvent.add(column1VBoxUniqueId.getText());
	        		whenEvent.add(column1VBoxDescription.getText());
	        		((AddRule) this.prevWindow.prevWindow).whenData.add(whenEvent);
	        		this.back().back();
	        		this.back().back();
	        	}
	        	
	        	if(this.prevWindow instanceof AddThen) {//add uniqueID
	        		ArrayList<String> thenEvent = new ArrayList<String>();
	        		thenEvent.add(column1VBoxHeader.getText());
	        		thenEvent.add(column1VBoxUniqueId.getText());
	        		thenEvent.add(column1VBoxDescription.getText()); 
	        		((AddRule) this.prevWindow.prevWindow).thenData.add(thenEvent);
	        		this.back().back();
	        		this.back().back();
	        	}
	        });
	    	//MainMenu.addHoverInteraction(new VBox[] {column1VBox}, "white", "darkgray");
	        //eventsVBox.getChildren().add(column1VBox);
	        myStyles.addHover(column1VBox,eventsVBox);
		}
		VBox column1VBox2 = new VBox();
		myStyles.createAddNewEvent(column1VBox2,eventsVBox);

        /**VBox column1VBox2 = new VBox();
        column1VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column1VBox2Header = new Text("Add New Event");
        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);        
        column1VBox2.getChildren().addAll(column1VBox2Header);
        eventsVBox.getChildren().add(column1VBox2);**/
	}
}