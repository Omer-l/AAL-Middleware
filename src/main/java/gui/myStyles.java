package gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class myStyles{
	
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

}
