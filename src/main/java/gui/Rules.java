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
    
    public  static void removeRule(String uniqueId) {
        MainMenu.mainDbManager.queryDB("DELETE FROM rule WHERE rule.unique_id = '" + uniqueId + "'", "");		
   	}
    
	public void open() {
		MainMenu.clearMainBox();
    	MainMenu.changeTitle("Rules");
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
        Text configHeader = new Text("Read Rule Events");
        configHeader.setStyle(MainMenu.HEADER_1_STYLE);
        configVBox.getChildren().addAll(configHeader);
        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-font: 15 arial ; -fx-base: #FFE4E1");
//        VBox configVBox1 = new VBox();
//        getEvents("SELECT * FROM rule INNER JOIN event ON rule.unique_id = event.unique_id;", configVBox,removeButton);        
        MyStyles.getEvents("SELECT * FROM rule INNER JOIN event ON rule.unique_id = event.unique_id;", configVBox, this);
        int rulesLastIndex = configVBox.getChildren().size() - 1;
       
        configVBox.getChildren().get(rulesLastIndex).setOnMouseClicked(event -> {new AddRule(this).open();});

        mainVBox1.getChildren().addAll(configVBox);
    	MainMenu.mainHBox.getChildren().addAll(mainVBox1);
    }

	public void editData(String uniqueId) {
	 MainMenu.mainDbManager.queryDB("SELECT * FROM rule JOIN event ON rule.unique_id = event.unique_id WHERE event.unique_id =  \"" + uniqueId + "\"", "select").get(0);
		
	}
}
