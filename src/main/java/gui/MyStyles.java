package gui;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MyStyles {
	Map<String, Object> removeData = new HashMap<String, Object>();
	public MyStyles() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void createLogField(TextField logField, VBox mainVBox1, HBox column1HBox) {
	     Text logText = new Text("LOG: ");
	     logField.prefWidthProperty().bind(mainVBox1.widthProperty().multiply(0.9));
	     logField.prefHeightProperty().bind(mainVBox1.heightProperty().multiply(0.1));
	     logField.setEditable(false);
	     column1HBox.getChildren().addAll(logText,logField);
	}
	
	public static void addHover(VBox column1VBox) {
		MainMenu.addHoverInteraction(new VBox[] {column1VBox}, "white", "darkgray");
	}
	
	public static void createAddNewEvent( VBox column1VBox2,VBox eventsVBox) {
		    column1VBox2 = new VBox();
	        column1VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
	        Text column1VBox2Header = new Text("Add New Event");
	        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);        
	        column1VBox2.getChildren().addAll(column1VBox2Header);
	        eventsVBox.getChildren().add(column1VBox2);
	}
	
	public static ArrayList<Map<String, Object>> getEvents(String query, VBox eventsVBox, Window window) {
//		int divider = eventsVBox.getParent().getChildrenUnmodifiable().size();
		ArrayList<Map<String, Object>> events = MainMenu.mainDbManager.queryDB(query, "select");
		if(eventsVBox == null) return events;
		
		for(Map<String, Object> readEvent : events) {
			HBox column1HBox = new HBox();
			VBox column1VBox = new VBox();
			column1HBox.minWidthProperty().bind(eventsVBox.widthProperty().subtract(140));
			column1VBox.minWidthProperty().bind(eventsVBox.widthProperty().subtract(140));
			column1VBox.setStyle(MainMenu.CARD_STYLE);
	        Text column1VBoxHeader = new Text((String) readEvent.get("name"));
	        column1VBoxHeader.setStyle(MainMenu.HEADER_2_STYLE);
	        Text column1VBoxUniqueId = new Text((String) readEvent.get("unique_id"));
	        Text column1VBoxDescription = new Text((String) readEvent.get("description"));
	        
	        Button editButton = new Button("Edit");
	        editButton.setStyle("-fx-font: 15 arial;-fx-base: #FFE4E1");
	        editButton.setOnAction(event -> {
	        	if(window instanceof DatabaseEvents) {
	        		AddDatabaseEvent ade = new AddDatabaseEvent(window);
	        		ade.loadData((String) readEvent.get("unique_id"));
	        		ade.open();
	        	} else if(window instanceof FileEvents) {
	        		String header = ((Text) eventsVBox.getChildren().get(0)).getText();
	        		AddFileEvent afe = new AddFileEvent(window);
	        		if(header.equals("Run Events")) {
	        			afe.loadData((String) readEvent.get("unique_id"), "run");
	        		} else if (header.equals("Read Events")) {
	        			afe.loadData((String) readEvent.get("unique_id"), "read");
	        		}
	        		afe.open();
	        	} else if(window instanceof Rules) {
        			AddRule addRule = new AddRule(new Rules());
        			addRule.editRule.put("unique_id", (String) readEvent.get("unique_id"));
        			addRule.open();
	        	} else if(window instanceof Schedules) {
	        		AddSchedule addSchedule = new AddSchedule(window);
	        		addSchedule.editData = MainMenu.mainDbManager.queryDB("SELECT * FROM schedule JOIN event ON schedule.unique_id = event.unique_id WHERE event.unique_id = \"" + ((String) readEvent.get("unique_id")) + "\"", "select").get(0);
	        		addSchedule.open();
	        	}
	        			
	        });
	        
	        Button removeButton = new Button("Remove");
	        removeButton.setStyle("-fx-font: 15 arial ; -fx-base: #FFE4E1");
	        removeButton.setOnAction(event -> {
	        	if(window instanceof DatabaseEvents) {
	        		AddDatabaseEvent.removeEventFromDatabaseReadEvent((String) readEvent.get("unique_id"));
	        		eventsVBox.getChildren().remove(column1HBox);
	        	}else if(window instanceof FileEvents) {
	        		String header = ((Text) eventsVBox.getChildren().get(0)).getText();
	        		if(header.equals("Read Events")) {
	        			AddFileEvent.removeEventFromFileEvent((String) readEvent.get("unique_id"),"read");
		        		eventsVBox.getChildren().remove(column1HBox);
	        		} else if (header.equals("Run Events")) {
	        			AddFileEvent.removeEventFromFileEvent((String) readEvent.get("unique_id"), "run");
		        		eventsVBox.getChildren().remove(column1HBox);
	        		} else if (header.equals("Write Events")) {
	        			AddFileEvent.removeEventFromFileEvent((String) readEvent.get("unique_id"),"write");
		        		eventsVBox.getChildren().remove(column1HBox);
	        		} 
	            }else if (window instanceof Rules) {
        			Rules.removeRule((String) readEvent.get("unique_id"));
	        		eventsVBox.getChildren().remove(column1HBox);
        		}else if(window instanceof Schedules) {
        			Schedules.removeSchedule((String) readEvent.get("unique_id"));
        			eventsVBox.getChildren().remove(column1HBox);
        			
        		}
	        });
	        column1VBox.getChildren().addAll(column1VBoxUniqueId, column1VBoxHeader, column1VBoxDescription);
	        column1VBoxUniqueId.managedProperty().bind(column1VBoxUniqueId.visibleProperty());
	        column1VBoxUniqueId.setVisible(false);
	        column1VBox.setOnMouseClicked(event -> {
	        	if(window.prevWindow instanceof AddWhen) { //add uniqueId
	        		ArrayList<String> whenEvent = new ArrayList<String>();
	        		whenEvent.add(column1VBoxUniqueId.getText());
	        		whenEvent.add(column1VBoxHeader.getText());
	        		whenEvent.add(column1VBoxDescription.getText());
	        		((AddRule) window.prevWindow.prevWindow).whenData.add(whenEvent);
	        		window.back().back();
//	        		window.back().back();
	        	} if(window.prevWindow instanceof AddThen) {//add uniqueID
	        		ArrayList<String> thenEvent = new ArrayList<String>();
	        		thenEvent.add(column1VBoxHeader.getText());
	        		thenEvent.add(column1VBoxUniqueId.getText());
	        		thenEvent.add(column1VBoxDescription.getText());
	        		((AddRule) window.prevWindow.prevWindow).thenData.add(thenEvent);
	        		window.back().back();
//	        		window.back().back();
	        	}
	        });
	    	//MainMenu.addHoverInteraction(new VBox[] {column1VBox}, "white", "darkgray");
	        column1HBox.getChildren().addAll(column1VBox, editButton, removeButton);
	        eventsVBox.getChildren().add(column1HBox);
	        MyStyles.addHover(column1VBox);
		}
		VBox column1VBox2 = new VBox();
		MyStyles.createAddNewEvent(column1VBox2,eventsVBox);
        return events;
       }

	
	


	public static void thenAndWhen(Window window) {
		Button button1 = new Button("Back");
        button1.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9");

        button1.setOnAction(event -> { window.back(); });
        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty());
        //column1urations
        VBox column1VBox1 = new VBox(2);
        Text column1Header = new Text("Description");
        column1Header.setStyle(MainMenu.HEADER_1_STYLE);

        HBox column1HBox1 = new HBox();
        VBox column1HBox1VBox1 = new VBox();
        column1HBox1VBox1.setStyle(MainMenu.CARD_STYLE);
        column1HBox1VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox1VBox1Header = new Text("UniqueID");
        VBox column1HBox1VBox2 = new VBox();
        column1HBox1VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox1VBox2TextField = new TextField();
        column1HBox1VBox1.getChildren().addAll(column1HBox1VBox1Header);
        column1HBox1VBox2.getChildren().addAll(column1HBox1VBox2TextField);
        column1HBox1.getChildren().addAll(column1HBox1VBox1, column1HBox1VBox2);

        HBox column1HBox2 = new HBox();
        VBox column1HBox2VBox1 = new VBox();
        column1HBox2VBox1.setStyle(MainMenu.CARD_STYLE);
        column1HBox2VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox2VBox1Header = new Text("Name");
        VBox column1HBox2VBox2 = new VBox();
        column1HBox2VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox2VBox2TextField = new TextField();
        column1HBox2VBox1.getChildren().addAll(column1HBox2VBox1Header);
        column1HBox2VBox2.getChildren().addAll(column1HBox2VBox2TextField);
        column1HBox2.getChildren().addAll(column1HBox2VBox1, column1HBox2VBox2);

        HBox column1HBox3 = new HBox();
        VBox column1HBox3VBox1 = new VBox();
        column1HBox3VBox1.setStyle(MainMenu.CARD_STYLE);
        column1HBox3VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox3VBox1Header = new Text("Description");
        VBox column1HBox3VBox2 = new VBox();
        column1HBox3VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox3VBox2TextField = new TextField();
        column1HBox3VBox1.getChildren().addAll(column1HBox3VBox1Header);
        column1HBox3VBox2.getChildren().addAll(column1HBox3VBox2TextField);
        column1HBox3.getChildren().addAll(column1HBox3VBox1, column1HBox3VBox2);

        //column1urations
        VBox column1VBox2 = new VBox(2);
        Text column1VBox2Header = new Text("Event");
        column1VBox2Header.setStyle(MainMenu.HEADER_1_STYLE);
        Label text1 = new Label("Database");
        VBox text1VBox = new VBox(text1);
        Label text2 = new Label("System");
        VBox text2VBox = new VBox(text2);
        Label text3 = new Label("Time");
        VBox text3VBox = new VBox(text3);
        Label text4 = new Label("Device");
        VBox text4VBox = new VBox(text4);
        GridPane gridPane = new GridPane();
        gridPane.setConstraints(text1VBox, 0, 0);
        gridPane.setConstraints(text2VBox, 1, 0);
        gridPane.setConstraints(text3VBox, 0, 1);
        gridPane.setConstraints(text4VBox, 1, 1);
        gridPane.getChildren().addAll(text1VBox, text2VBox, text3VBox, text4VBox);
        gridPane.setStyle("-fx-grid-lines-visible: true; -fx-background-color: white;");
        gridPane.setHgrow(text1VBox, Priority.ALWAYS);
        gridPane.setVgrow(text1VBox, Priority.ALWAYS);
        gridPane.setHgrow(text2VBox, Priority.ALWAYS);
        gridPane.setVgrow(text2VBox, Priority.ALWAYS);
        gridPane.setHgrow(text3VBox, Priority.ALWAYS);
        gridPane.setVgrow(text3VBox, Priority.ALWAYS);
        gridPane.setHgrow(text4VBox, Priority.ALWAYS);
        gridPane.setVgrow(text4VBox, Priority.ALWAYS);
        text1VBox.setAlignment(Pos.CENTER);
        text2VBox.setAlignment(Pos.CENTER);
        text3VBox.setAlignment(Pos.CENTER);
        text4VBox.setAlignment(Pos.CENTER);
        gridPane.prefWidthProperty().bind(mainVBox1.widthProperty().divide(2));
        gridPane.prefHeightProperty().bind(mainVBox1.heightProperty().divide(3));
        
        text1VBox.setOnMouseClicked(event -> { 
        	VBox label = (VBox) event.getSource(); 
        	DatabaseEvents de = new DatabaseEvents(window);
        	de.open();
        } );
        
        text2VBox.setOnMouseClicked(event -> { 
        	VBox label = (VBox) event.getSource(); 
        	FileEvents de = new FileEvents(window);
        	de.open();
        } );

        
        text3VBox.setOnMouseClicked(event -> { 
        	new Schedules(window).open();
        } );
        
        text4VBox.setOnMouseClicked(event -> { VBox label = (VBox) event.getSource(); System.out.println("CLICKED: " + label.getChildren().get(0));} );
        
        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header);
        
    	MainMenu.addHoverInteraction(new VBox[] {text1VBox, text2VBox, text3VBox, text4VBox}, "white", "darkgray");
    	mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, gridPane);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);
	}
	
	
}
