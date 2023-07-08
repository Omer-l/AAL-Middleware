package gui;
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

	@Override
	public void open(Window prevWindow) {
    	prevWindow = null; //ensures built windows are nullified and garbage collected. 
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

        VBox column1VBox1 = new VBox();
        column1VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column1VBox1Header = new Text("When Omer is in the living room");
        column1VBox1Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column1VBox1Details = new Text("I3D4X " + "Read BLE database for Omer is in the living room");
        column1VBox1.getChildren().addAll(column1VBox1Header, column1VBox1Details);
        column1VBox1.setOnMouseClicked(event -> {
        	if(this.prevWindow instanceof AddWhen) {
        		((AddRule) this.prevWindow.prevWindow).whenData.add(column1VBox1Details.getText().substring(0, column1VBox1Details.getText().indexOf(" ")));
        		this.back().back();
        		this.back().back();
        	}
        	
        	if(this.prevWindow instanceof AddThen) {
        		((AddRule) this.prevWindow.prevWindow).thenData.add(column1VBox1Details.getText().substring(0, column1VBox1Details.getText().indexOf(" ")));
        		this.back().back();
        		this.back().back();
        	}
        });

        VBox column1VBox2 = new VBox();
        column1VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column1VBox2Header = new Text("Add New Event");
        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);
        column1VBox2.setOnMouseClicked(event -> {new AddDatabaseEvent(this).open(this);});
        
        column1VBox2.getChildren().addAll(column1VBox2Header);
        
        column1VBox.getChildren().addAll(column1Header, column1VBox1, column1VBox2);
        mainVBox1.getChildren().addAll(column1VBox);
        //RIGHT SIDE MAIN
        VBox mainVBox2 = new VBox(2);
        mainVBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        //write events
        VBox column2VBox = new VBox(2);
        Text column2Header = new Text("Write Events");
        column2Header.setStyle(MainMenu.HEADER_1_STYLE);

        VBox column2VBox1 = new VBox(2);
        column2VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column2VBox1Header = new Text("Write User is in the room");
        column2VBox1Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column2VBox1Details = new Text("Writes to MReasoner DB about BLE Event");
        column2VBox1.getChildren().addAll(column2VBox1Header, column2VBox1Details);
        column2VBox1.setOnMouseClicked(event -> {
        	if(this.prevWindow instanceof AddWhen) {
        		((AddRule) this.prevWindow.prevWindow).whenData.add(column2VBox1Details.getText().substring(0, column2VBox1Details.getText().indexOf(" ")));
        		this.back().back();
        		this.back().back();
        	}
        	
        	if(this.prevWindow instanceof AddThen) {
        		((AddRule) this.prevWindow.prevWindow).thenData.add(column2VBox1Details.getText().substring(0, column2VBox1Details.getText().indexOf(" ")));
        		this.back().back();
        		this.back().back();
        	}
        });

        VBox column2VBox2 = new VBox();
        column2VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column2VBox2Header = new Text("Add New Event");
        column2VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);
        column2VBox2.getChildren().addAll(column2VBox2Header);

        column2VBox.getChildren().addAll(column2Header, column2VBox1,column2VBox2);
        mainVBox2.getChildren().addAll(column2VBox);

    	MainMenu.addHoverInteraction(new VBox[] {column1VBox1, column2VBox1}, "white", "darkgray");
    	MainMenu.addHoverInteraction(new VBox[] {column1VBox2, column2VBox2}, "yellow", "darkgray");
        MainMenu.mainHBox.getChildren().addAll(mainVBox1, mainVBox2);
	}
}
