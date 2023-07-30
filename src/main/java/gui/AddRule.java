package gui;

import java.util.ArrayList;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AddRule extends Window {
	ArrayList<ArrayList<String>> whenData = new ArrayList<>();
	ArrayList<ArrayList<String>> thenData = new ArrayList<>();
	
	
	public AddRule(Window prevWindow) {
		super(prevWindow);
	}
	
	public AddRule() {
		
	}

	public void open() {
		MainMenu.clearMainBox();
		MainMenu.changeTitle("Add Rule");
		
      Button button1 = new Button("Back");
        button1.setOnAction(event -> { back(); });
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
        column1HBox1VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
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
        column1HBox2VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
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
        column1HBox3VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
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
        Text column1VBox2Header = new Text("When");
        column1VBox2Header.setStyle(MainMenu.HEADER_1_STYLE);
        column1VBox2.getChildren().addAll(column1VBox2Header);
        getEvents(column1VBox2, whenData);
        int whenEventsLastIndex = column1VBox2.getChildren().size() - 1;
        column1VBox2.getChildren().get(whenEventsLastIndex).setOnMouseClicked(event -> {new AddWhen(this).open();});

        //column1urations
        VBox column1VBox3 = new VBox(2);
        Text column1VBox3Header = new Text("Then");
        column1VBox3Header.setStyle(MainMenu.HEADER_1_STYLE);
        column1VBox2.getChildren().addAll(column1VBox3Header);
        getEvents(column1VBox3, thenData);
        int thenEventsLastIndex = column1VBox3.getChildren().size() - 1;
        column1VBox3.getChildren().get(thenEventsLastIndex).setOnMouseClicked(event -> {  new AddThen(this).open();});

        VBox column1ButtonsVBox = new VBox(2);
        
        ButtonBar column1ButtonBar = new ButtonBar();
        Button saveButton = new Button("Save");
        column1ButtonBar.getButtons().addAll(saveButton);
        saveButton.setOnAction( event -> { processSaveButton(column1HBox1VBox2TextField.getText(), column1HBox2VBox2TextField.getText(), column1HBox3VBox2TextField.getText()); });

        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1ButtonsVBox.getChildren().addAll(column1ButtonBar);
        
    	mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, column1VBox3, column1ButtonsVBox);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);
	}
	
	public void getEvents(VBox eventsVBox, ArrayList<ArrayList<String>> events) {
		for(ArrayList<String> event : events) {
			VBox column1VBox = new VBox();
	        column1VBox.setStyle(MainMenu.MENU_BUTTON_STYLE);
	        Text column1VBoxHeader = new Text((String) event.get(1));
	        column1VBoxHeader.setStyle(MainMenu.HEADER_2_STYLE);
	        Text column1VBoxUniqueId = new Text((String) event.get(0));
	        Text column1VBoxDescription = new Text((String) event.get(2));
	        column1VBox.getChildren().addAll(column1VBoxUniqueId, column1VBoxHeader, column1VBoxDescription);
	        column1VBoxUniqueId.managedProperty().bind(column1VBoxUniqueId.visibleProperty());
	        column1VBoxUniqueId.setVisible(false);
	        column1VBox.setOnMouseClicked(e -> {
	        	//remove from list or edit?
	        }); //HERE
	    	//MainMenu.addHoverInteraction(new VBox[] {column1VBox}, "white", "darkgray");
	        //eventsVBox.getChildren().add(column1VBox);
	        MyStyles.addHover(column1VBox);
	        eventsVBox.getChildren().add(column1VBox);
		}
		

        VBox column1VBox2 = new VBox();
        MyStyles.createAddNewEvent(column1VBox2,eventsVBox);
        /**column1VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column1VBox2Header = new Text("Add New Event");
        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);        
        column1VBox2.getChildren().addAll(column1VBox2Header);**/ //HERE
    	MainMenu.addHoverInteraction(new VBox[] {column1VBox2}, "yellow", "darkgray");
       // eventsVBox.getChildren().add(column1VBox2);
	}

	private void processSaveButton(String uniqueIdInput, String nameInput, String descriptionInput) {

    	boolean emptyField = uniqueIdInput.isEmpty() || nameInput.isEmpty() || descriptionInput.isEmpty();
    	String whenUniqueIds = getUniqueIds(whenData);
    	String thenUniqueIds = getUniqueIds(thenData);
    	if(!emptyField) {
        	MainMenu.mainDbManager.queryDB("INSERT INTO rule VALUES ('" + uniqueIdInput + "', '" + whenUniqueIds + "', '" + thenUniqueIds + "');", "");
        	MainMenu.mainDbManager.queryDB("INSERT INTO event VALUES ('" + uniqueIdInput + "', '" + nameInput + "', '" + descriptionInput + "');", "");
    	} else {
    		System.out.println("unique id, name or description field is empty");
    	}
		back();
	}

	private String getUniqueIds(ArrayList<ArrayList<String>> data) {
		StringBuilder uniqueIds = new StringBuilder();
		for(int i = 0; i < data.size(); i++) {
			ArrayList<String> event = data.get(i);
			String id = event.get(1);
			if(i != data.size() - 1)
				uniqueIds.append(id + ", ");
			else
				uniqueIds.append(id);
		}
		return uniqueIds.toString();
	}
}
