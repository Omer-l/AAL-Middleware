package gui;

import javafx.geometry.Pos;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;

public class AddThen extends Window {
	
	public AddThen(Window prevWindow) {
		super(prevWindow);
	}

	public AddThen() {

	}

	public void open() {
		MainMenu.clearMainBox();
		MainMenu.changeTitle("Add Then");
		MyStyles.thenAndWhen(this);
        /**Button button1 = new Button("Back");
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
        	DatabaseEvents de = new DatabaseEvents(this);
        	de.open();
        } );
        
        text2VBox.setOnMouseClicked(event -> { 
        	VBox label = (VBox) event.getSource(); 
        	FileEvents de = new FileEvents(this);
        	de.open();
        } );
        
        //DELETE THESE TWO
        text3VBox.setOnMouseClicked(event -> { VBox label = (VBox) event.getSource(); System.out.println("CLICKED: " + label.getChildren().get(0));} );
        text4VBox.setOnMouseClicked(event -> { VBox label = (VBox) event.getSource(); System.out.println("CLICKED: " + label.getChildren().get(0));} );
        
        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header);
        
    	MainMenu.addHoverInteraction(new VBox[] {text1VBox, text2VBox, text3VBox, text4VBox}, "white", "darkgray");
    	mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, gridPane);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);**/
	}
}
