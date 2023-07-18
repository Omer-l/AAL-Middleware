package gui;

import java.util.ArrayList;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Rules extends Window {

    public Rules(Window prevWindow) {
		super(prevWindow);
	}
    
    public Rules() {
    	
    }

	public void open() {
		MainMenu.clearMainBox();
    	MainMenu.changeTitle("Rules");
        Button button1 = new Button("Back");
        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        button1.setOnAction(event -> { back(); });
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty());
        //Configurations
        VBox configVBox = new VBox(2);
        Text configHeader = new Text("Read Events");
        configHeader.setStyle(MainMenu.HEADER_1_STYLE);
        configVBox.getChildren().addAll(configHeader);

//        VBox configVBox1 = new VBox();
        getEvents("SELECT * FROM rule INNER JOIN event ON rule.unique_id = event.unique_id;", configVBox);        
        int rulesLastIndex = configVBox.getChildren().size() - 1;
        configVBox.getChildren().get(rulesLastIndex).setOnMouseClicked(event -> {new AddRule(this).open();});

        mainVBox1.getChildren().addAll(configVBox);
    	MainMenu.mainHBox.getChildren().addAll(mainVBox1);
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
	    	MainMenu.addHoverInteraction(new VBox[] {column1VBox}, "white", "darkgray");
	        eventsVBox.getChildren().add(column1VBox);
		}
		
        VBox column1VBox2 = new VBox();
        column1VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column1VBox2Header = new Text("Add New Rule");
        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);        
        column1VBox2.getChildren().addAll(column1VBox2Header);
        eventsVBox.getChildren().add(column1VBox2);
	}
}
