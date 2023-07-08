package gui;

import java.util.ArrayList;

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
	ArrayList<String> whenData = new ArrayList<>();
	ArrayList<String> thenData = new ArrayList<>();
	
	public AddRule(Window prevWindow) {
		super(prevWindow);
	}
	
	public AddRule() {
		
	}

	@Override
	public void open(Window prevWindow) {
    	prevWindow = null; //ensures built windows are nullified and garbage collected. 
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

        VBox column1VBox4 = new VBox();
        column1VBox4.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column1VBox4Header = new Text("When Omer is in the living room");
        column1VBox4Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column1VBox4Details = new Text("Read BLE database for Omer is in the living room");
        column1VBox4.getChildren().addAll(column1VBox4Header, column1VBox4Details);

        VBox column1VBox5 = new VBox();
        column1VBox5.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column1VBox5Header = new Text("Add When");
        column1VBox5Header.setStyle(MainMenu.HEADER_2_STYLE);
        column1VBox5.getChildren().addAll(column1VBox5Header);
        column1VBox5.setOnMouseClicked(event -> { new AddWhen(this).open(this); });

        //column1urations
        VBox column1VBox3 = new VBox(2);
        Text column1VBox3Header = new Text("Then");
        column1VBox3Header.setStyle(MainMenu.HEADER_1_STYLE);

        VBox column1VBox6 = new VBox();
        column1VBox6.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column1VBox6Header = new Text("Write User is in the room");
        column1VBox6Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column1VBox6Details = new Text("Writes to MReasoner DB about BLE Event");
        column1VBox6.getChildren().addAll(column1VBox6Header, column1VBox6Details);

        VBox column1VBox7 = new VBox();
        column1VBox7.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column1VBox7Header = new Text("Add Then");
        column1VBox7Header.setStyle(MainMenu.HEADER_2_STYLE);
        column1VBox7.getChildren().addAll(column1VBox7Header);
        column1VBox7.setOnMouseClicked(event -> { new AddThen(this).open(this); });

        VBox column1ButtonsVBox = new VBox(2);
        
        ButtonBar column1ButtonBar = new ButtonBar();
        Button saveButton = new Button("Save");
        column1ButtonBar.getButtons().addAll(saveButton);

        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header, column1VBox4, column1VBox5);
        column1VBox2.getChildren().addAll(column1VBox3Header, column1VBox6, column1VBox7);
        column1ButtonsVBox.getChildren().addAll(column1ButtonBar);
        
    	MainMenu.addHoverInteraction(new VBox[] {column1VBox4, column1VBox6}, "white", "darkgray");
    	MainMenu.addHoverInteraction(new VBox[] {column1VBox5, column1VBox7}, "yellow", "darkgray");
    	mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, column1VBox3, column1ButtonsVBox);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);
	}
}
