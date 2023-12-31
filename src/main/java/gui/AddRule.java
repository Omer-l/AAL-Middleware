package gui;

import java.util.ArrayList;
import java.util.HashMap;
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
import middleware.RuleRunner;

public class AddRule extends Window {
	ArrayList<ArrayList<String>> whenData = new ArrayList<>();
	ArrayList<ArrayList<String>> thenData = new ArrayList<>();
	Map<String, Object> editRule = new HashMap<String, Object>();
	int id = 0;
	String name = "";
	String description = "";
	
	public AddRule(Window prevWindow) {
		super(prevWindow);
	}
	
	public AddRule() {
		
	}
	
	public void loadData() {
		editRule = MainMenu.mainDbManager.queryDB("SELECT * FROM rule JOIN event ON rule.id = event.id WHERE event.id =  " + ((int)editRule.get("id")), "select").get(0);
		try {
	    	id = (int) editRule.get("id");
			name = (String) editRule.get("name");
			description = (String) editRule.get("description");
			String whenIds = "";
			String[] splitWhenIds = ((String) editRule.get("when_event_ids")).split("\\s*,\\s*");
			if(!(splitWhenIds.length == 1 && splitWhenIds[0].length() == 0)) {
				for(int i = 0; i < splitWhenIds.length; i++) {
					int whenId = Integer.parseInt(splitWhenIds[i]);
					if(!(i == splitWhenIds.length - 1))
							whenIds += "\"" + whenId + "\",";
					else
						whenIds += "\"" + whenId + "\"";
				}
				for(Map<String, Object> whenEvent : MyStyles.getEvents("SELECT * FROM event WHERE id IN (" + whenIds + ")", null, this)) {
					ArrayList<String> whenDetails = new ArrayList<>();
					whenDetails.add((int) whenEvent.get("id") + "");
					whenDetails.add((String) whenEvent.get("name"));
					whenDetails.add((String) whenEvent.get("description"));
					if(!whenData.contains(whenDetails))
						whenData.add(whenDetails);
				}
			}
			String thenIds = "";
			String[] splitThenIds = ((String) editRule.get("then_event_ids")).split("\\s*,\\s*");
			if(!(splitThenIds.length == 1 && splitThenIds[0].length() == 0)) {
				for(int i = 0; i < splitThenIds.length; i++) {
					int thenId = Integer.parseInt(splitThenIds[i]);
					if(!(i == splitThenIds.length - 1))
							thenIds += "\"" + thenId + "\",";
					else
						thenIds += "\"" + thenId + "\"";
				}
				for(Map<String, Object> thenEvent : MyStyles.getEvents("SELECT * FROM event WHERE id IN (" + thenIds + ")", null, this)) {
					ArrayList<String> thenDetails = new ArrayList<>();
					thenDetails.add((int) thenEvent.get("id") + "");
					thenDetails.add((String) thenEvent.get("name"));
					thenDetails.add((String) thenEvent.get("description"));
					if(!thenData.contains(thenDetails))
						thenData.add(thenDetails);
				}		
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public void open() {
        if(!editRule.isEmpty())
        	loadData();
        
		MainMenu.clearMainBox();
		MainMenu.changeTitle("Add Rule");
		
		Button button1 = new Button("Back");
		button1.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9");

        button1.setOnAction(event -> { back(); });
        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty());
        //column1urations
        VBox column1VBox1 = new VBox(2);
        Text column1Header = new Text("Details");
        column1Header.setStyle(MainMenu.HEADER_1_STYLE);

        HBox column1HBox2 = new HBox();
        VBox column1HBox2VBox1 = new VBox();
        column1HBox2VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox2VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox2VBox1Header = new Text("Name");
        VBox column1HBox2VBox2 = new VBox();
        column1HBox2VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox2VBox2TextField = new TextField(name);
        column1HBox2VBox1.getChildren().addAll(column1HBox2VBox1Header);
        column1HBox2VBox2.getChildren().addAll(column1HBox2VBox2TextField);
        column1HBox2.getChildren().addAll(column1HBox2VBox1, column1HBox2VBox2);

        HBox column1HBox3 = new HBox();
        VBox column1HBox3VBox1 = new VBox();
        column1HBox3VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox3VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox3VBox1Header = new Text("Description");
        VBox column1HBox3VBox2 = new VBox();
        column1HBox3VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox3VBox2TextField = new TextField(description);
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
        column1VBox2.getChildren().get(whenEventsLastIndex).setOnMouseClicked(event -> {
        	name = column1HBox2VBox2TextField.getText();
        	description = column1HBox3VBox2TextField.getText();
        	new AddWhen(this).open();
        });

        //column1urations
        VBox column1VBox3 = new VBox(2);
        Text column1VBox3Header = new Text("Then");
        column1VBox3Header.setStyle(MainMenu.HEADER_1_STYLE);
        column1VBox3.getChildren().addAll(column1VBox3Header);
    	getEvents(column1VBox3, thenData);
        int thenEventsLastIndex = column1VBox3.getChildren().size() - 1;
        column1VBox3.getChildren().get(thenEventsLastIndex).setOnMouseClicked(event -> {
        	name = column1HBox2VBox2TextField.getText();
        	description = column1HBox3VBox2TextField.getText();
        	new AddThen(this).open();
        });
        

        VBox column1ButtonsVBox = new VBox(2);
        
        ButtonBar column1ButtonBar = new ButtonBar();
        Button saveButton = new Button("Save");
        column1ButtonBar.getButtons().addAll(saveButton);
        saveButton.setOnAction( event -> { processSaveButton(column1HBox2VBox2TextField.getText(), column1HBox3VBox2TextField.getText()); });

        column1VBox1.getChildren().addAll(column1Header, column1HBox2, column1HBox3);
        column1ButtonsVBox.getChildren().addAll(column1ButtonBar);
        
    	mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, column1VBox3, column1ButtonsVBox);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);
	}
	
	public void getEvents(VBox eventsVBox, ArrayList<ArrayList<String>> events) {
		
		for(ArrayList<String> event : events) {
			HBox column1HBox = new HBox();
			VBox column1VBox = new VBox();
			column1HBox.minWidthProperty().bind(eventsVBox.widthProperty().subtract(140));
			column1VBox.minWidthProperty().bind(eventsVBox.widthProperty().subtract(140));
	        column1VBox.setStyle(MainMenu.USER_INPUT_STYLE);
	        Text column1VBoxUniqueId = new Text((String) event.get(0) + "");
	        Text column1VBoxHeader = new Text((String) event.get(1));
	        column1VBoxHeader.setStyle(MainMenu.HEADER_2_STYLE);
	        Text column1VBoxDescription = new Text((String) event.get(2));
	        column1VBox.getChildren().addAll(column1VBoxUniqueId, column1VBoxHeader, column1VBoxDescription);
	        column1VBoxUniqueId.managedProperty().bind(column1VBoxUniqueId.visibleProperty());
	        column1VBoxUniqueId.setVisible(false);
	  
	    	//MainMenu.addHoverInteraction(new VBox[] {column1VBox}, "white", "darkgray");
	        //eventsVBox.getChildren().add(column1VBox);
	       // MyStyles.addHover(column1VBox);
			Button removeButton = new Button("-");
			removeButton.setOnMouseClicked(e -> {
				eventsVBox.getChildren().remove(column1HBox);
				if( ((Text)eventsVBox.getChildren().get(0)).getText().equals("When") )
					whenData.remove(event);
				else
					thenData.remove(event);
				
	        }); //HERE
			column1HBox.getChildren().addAll(column1VBox, removeButton);
	        eventsVBox.getChildren().addAll(column1HBox);
		}
		

        VBox column1VBox2 = new VBox();
        MyStyles.createAddNewEvent(column1VBox2,eventsVBox);
        /**column1VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column1VBox2Header = new Text("Add New Event");
        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);        
        column1VBox2.getChildren().addAll(column1VBox2Header);**/ //HERE
//    	MainMenu.addHoverInteraction(new VBox[] {column1VBox2}, "yellow", "darkgray");
       // eventsVBox.getChildren().add(column1VBox2);
	}

	private void processSaveButton(String nameInput, String descriptionInput) {

    	boolean emptyField = nameInput.isEmpty() || descriptionInput.isEmpty();
    	String whenUniqueIds = getUniqueIds(whenData);
    	String thenUniqueIds = getUniqueIds(thenData);
    	if(!emptyField) {
    		if(editRule.isEmpty()) {
	        	MainMenu.mainDbManager.queryDB("INSERT INTO event VALUES (null, '" + nameInput + "', '" + descriptionInput + "');", "");
	        	int id = (int) MainMenu.mainDbManager.queryDB("SELECT * FROM event ORDER BY id  DESC LIMIT 1;", "select").get(0).get("id");
	        	MainMenu.mainDbManager.queryDB("INSERT INTO rule VALUES (" + id + ", '" + whenUniqueIds + "', '" + thenUniqueIds + "');", "");
    		} else {
    			MainMenu.mainDbManager.queryDB("UPDATE event SET"
	        			+ " name = '" + nameInput + "', "
	        			+ "description = '" + descriptionInput + "' WHERE id = '" + id + "';", "");
    			MainMenu.mainDbManager.queryDB("UPDATE rule SET"
	        			+ " when_event_ids = '" + whenUniqueIds + "', "
	        			+ "then_event_ids = '" + thenUniqueIds + "' WHERE id = '" + id + "';", "");
    		}
    	} else {
    		System.out.println("unique id, name or description field is empty");
    	}
		back();
	}

	private String getUniqueIds(ArrayList<ArrayList<String>> data) {
		StringBuilder uniqueIds = new StringBuilder();
		for(int i = 0; i < data.size(); i++) {
			ArrayList<String> event = data.get(i);
			String id = event.get(0);
			if(i != data.size() - 1)
				uniqueIds.append(id + ", ");
			else
				uniqueIds.append(id);
		}
		return uniqueIds.toString();
	}

	
}
