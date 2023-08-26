package gui;

import java.util.ArrayList;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Schedules extends Window {

    public Schedules(Window prevWindow) {
		super(prevWindow);
	}
    
    public Schedules() {
    	
    }

	public void open() {
		MainMenu.clearMainBox();
    	MainMenu.changeTitle("Schedules");
        Button button1 = new Button("Back");
        button1.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9");
        
        

        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        button1.setOnAction(event -> { back(); });
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty());
        //Configurations
        VBox configVBox = new VBox(2);
        Text configHeader = new Text("Events");
        configHeader.setStyle(MainMenu.HEADER_1_STYLE);
        configVBox.getChildren().addAll(configHeader);
       // Button removeButton = new Button("Remove");
        //removeButton.setStyle("-fx-font: 15 arial ; -fx-base: #FFE4E1");
//        VBox configVBox1 = new VBox();
//        getEvents("SELECT * FROM rule INNER JOIN event ON rule.id = event.id;", configVBox,removeButton);        
        MyStyles.getEvents("SELECT * FROM schedule INNER JOIN event ON schedule.id = event.id;", configVBox, this);
        int schedulesLastIndex = configVBox.getChildren().size() - 1;
       
        configVBox.getChildren().get(schedulesLastIndex).setOnMouseClicked(event -> {new AddSchedule(this).open();});

        mainVBox1.getChildren().addAll(configVBox);
    	MainMenu.mainHBox.getChildren().addAll(mainVBox1);
    }
	
	public void getEvents(String query, VBox eventsVBox) {
		ArrayList<Map<String, Object>> events = MainMenu.mainDbManager.queryDB(query, "select");
		for(Map<String, Object> readEvent : events) {
			VBox column1VBox = new VBox();
	        column1VBox.setStyle(MainMenu.CARD_STYLE);
	        Text column1VBoxHeader = new Text((String) readEvent.get("name"));
	        column1VBoxHeader.setStyle(MainMenu.HEADER_2_STYLE);
	        Text column1VBoxUniqueId = new Text((String) readEvent.get("id"));
	        Text column1VBoxDescription = new Text((String) readEvent.get("description"));
	        column1VBox.getChildren().addAll(column1VBoxUniqueId, column1VBoxHeader, column1VBoxDescription);
	        column1VBoxUniqueId.managedProperty().bind(column1VBoxUniqueId.visibleProperty());
	        column1VBoxUniqueId.setVisible(false);
	    	MainMenu.addHoverInteraction(new VBox[] {column1VBox}, "white", "darkgray");
	        eventsVBox.getChildren().addAll(column1VBox);
		}
		
        VBox column1VBox2 = new VBox();
        column1VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column1VBox2Header = new Text("Add New Schedule");
        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);        
        column1VBox2.getChildren().addAll(column1VBox2Header);
        eventsVBox.getChildren().add(column1VBox2);
	}

	public static void removeSchedule(int uniqueID) {
		  MainMenu.mainDbManager.queryDB("DELETE FROM schedule WHERE schedule.id = " + uniqueID, "");

	}
}
