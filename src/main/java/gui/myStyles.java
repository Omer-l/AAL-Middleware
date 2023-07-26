package gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class myStyles extends Window{
	
	public myStyles(Window prevWindow) {
		super(prevWindow);
	}
	
	public myStyles() {
		// TODO Auto-generated constructor stub
	}
	
	public static void createLogField(TextField logField, VBox mainVBox1, HBox column1HBox) {
	 
     Text logText = new Text("LOG: ");
     logField.prefWidthProperty().bind(mainVBox1.widthProperty().multiply(0.9));
     logField.prefHeightProperty().bind(mainVBox1.heightProperty().multiply(0.1));
     logField.setEditable(false);
     column1HBox.getChildren().addAll(logText,logField);
	}
	
	public static void addHover(VBox column1VBox,VBox eventsVBox) {
		MainMenu.addHoverInteraction(new VBox[] {column1VBox}, "white", "darkgray");
        eventsVBox.getChildren().add(column1VBox);
	}
	
	public static void createAddNewEvent( VBox column1VBox2,VBox eventsVBox) {
		    column1VBox2 = new VBox();
	        column1VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
	        Text column1VBox2Header = new Text("Add New Event");
	        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);        
	        column1VBox2.getChildren().addAll(column1VBox2Header);
	        eventsVBox.getChildren().add(column1VBox2);
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
	        	if(this.prevWindow instanceof AddWhen) { //add uniqueID//add uniqueID
	        		ArrayList<String> whenEvent = new ArrayList<String>();
	        		whenEvent.add(column1VBoxHeader.getText());
	        		whenEvent.add(column1VBoxUniqueId.getText());
	        		whenEvent.add(column1VBoxDescription.getText());
	        		((AddRule) this.prevWindow.prevWindow).whenData.add(whenEvent);
	        		this.back().back();
	        		this.back().back();
	        	} if(this.prevWindow instanceof AddThen) {//add uniqueID
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
        //TILL HERE
       /** column1VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column1VBox2Header = new Text("Add New Event");
        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);        
        column1VBox2.getChildren().addAll(column1VBox2Header);
        eventsVBox.getChildren().add(column1VBox2);**/
	}

}
