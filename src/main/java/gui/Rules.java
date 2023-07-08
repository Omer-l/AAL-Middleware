package gui;

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

        VBox configVBox1 = new VBox();
        configVBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text configVBox1Header = new Text("Transform BLE Data");
        configVBox1Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text configVBox1Details = new Text("When user enters a room (BLE DB), transform and send the data to MReasoner DB");
        configVBox1.getChildren().addAll(configVBox1Header, configVBox1Details);

        VBox configVBox2 = new VBox();
        configVBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text configVBox2Header = new Text("Add New Rule");
        configVBox2Header.setStyle(MainMenu.HEADER_2_STYLE);
        configVBox2.getChildren().addAll(configVBox2Header);
        configVBox2.setOnMouseClicked(event -> { new AddRule(this).open(); });
        
        configVBox.getChildren().addAll(configHeader, configVBox1, configVBox2);

        mainVBox1.getChildren().addAll(configVBox);

    	MainMenu.addHoverInteraction(new VBox[] {configVBox1}, "white", "darkgray");
    	MainMenu.addHoverInteraction(new VBox[] {configVBox2}, "yellow", "darkgray");
    	MainMenu.mainHBox.getChildren().addAll(mainVBox1);
    }
}
