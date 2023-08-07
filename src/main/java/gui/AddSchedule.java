package gui;


import java.util.HashMap;
import java.util.Map;

import dao.DbXMLParser;
import dao.MySqlConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class AddSchedule extends Window {
	private Button testButton = new Button("Test");
    private Button saveButton = new Button("Save");
    public static void removeEventFromDatabaseReadEvent(String uniqueID) {
	  MainMenu.mainDbManager.queryDB("DELETE FROM database_read_event WHERE database_read_event.unique_id = '" + uniqueID + "'","");
	}
 
    
	public AddSchedule(Window prevWindow) {
		super(prevWindow);
	}

    
	public AddSchedule() {
		
	}

	public void open() {
		MainMenu.clearMainBox();
		MainMenu.changeTitle("Add Database Event");
		
	        
        Button button1 = new Button("Back");
        button1.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9");
        saveButton.setStyle("-fx-font: 15 arial ; -fx-base: #FFE4E1");
        testButton.setStyle("-fx-font: 15 arial ; -fx-base: #FFE4E1");

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

        HBox column1HBox1 = new HBox();
        VBox column1HBox1VBox1 = new VBox();
        column1HBox1VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox1VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox1VBox1Header = new Text("UniqueID"); //TODO: delete this unique id field and make it so that the db auto increments this
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
        Text column1VBox2Header = new Text("Method");
        column1VBox2Header.setStyle(MainMenu.HEADER_1_STYLE);
        HBox column1Hbox4 = new HBox();
        
        VBox column1Hbox4VBox1 = new VBox();
        column1Hbox4VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1Hbox4VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1Hbox4VBox1Header = new Text("RDBM ");
        VBox column1Hbox4VBox2 = new VBox();
        column1Hbox4VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        // Create a MenuItem
        MenuBar column1HBox4VBox2MenuBar = new MenuBar();
        Menu menu1 =  new Menu();
        // Create MenuItems for the dropdown menu
        MenuItem option1 = new MenuItem("MySQL");
        MenuItem option2 = new MenuItem("PostgreSQL");
        menu1.getItems().addAll(option1, option2);
        column1HBox4VBox2MenuBar.getMenus().add(menu1);
        
        column1Hbox4VBox1.getChildren().addAll(column1Hbox4VBox1Header);
        column1Hbox4VBox2.getChildren().addAll(column1HBox4VBox2MenuBar);
        column1Hbox4.getChildren().addAll(column1Hbox4VBox1, column1Hbox4VBox2);

        HBox column1HBox5 = new HBox();
        VBox column1HBox5VBox1 = new VBox();
        column1HBox5VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox5VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox5VBox1Header = new Text("Database");
        VBox column1HBox5VBox2 = new VBox();
        column1HBox5VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        MenuBar column1HBox5VBox2MenuBar = new MenuBar();
        Menu menu2 = new Menu();
        // Create MenuItems for the dropdown menu
        column1HBox5VBox2MenuBar.getMenus().add(menu2);
//        Text menuBar2Label = new Text("Table");
//    	HBox menuBar2HBox = new HBox(10);
//    	menuBar2HBox.getChildren().addAll(menuBar2Label, column1HBox5VBox2MenuBar);
        column1HBox5VBox1.getChildren().addAll(column1HBox5VBox1Header);
        column1HBox5VBox2.getChildren().addAll(column1HBox5VBox2MenuBar);
        column1HBox5.getChildren().addAll(column1HBox5VBox1, column1HBox5VBox2);

        HBox column1HBox6 = new HBox();
        VBox column1HBox6VBox1 = new VBox();
        column1HBox6VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox6VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox6VBox1Header = new Text("Table");
        VBox column1HBox6VBox2 = new VBox();
        column1HBox6VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        MenuBar column1HBox6VBox2MenuBar = new MenuBar();
        Menu menu3 = new Menu();
        // Create MenuItems for the dropdown menu
        column1HBox6VBox2MenuBar.getMenus().add(menu3);
//        Text menuBar3Label = new Text("Table");
//    	HBox menuBar3HBox = new HBox(10);
//    	menuBar3HBox.getChildren().addAll(menuBar3Label, column1HBox6VBox2MenuBar);
        column1HBox6VBox1.getChildren().addAll(column1HBox6VBox1Header);
        column1HBox6VBox2.getChildren().addAll(column1HBox6VBox2MenuBar);
        column1HBox6.getChildren().addAll(column1HBox6VBox1, column1HBox6VBox2);

        HBox column1HBox7 = new HBox();
        VBox column1HBox7VBox1 = new VBox();
        column1HBox7VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox7VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox7VBox1Header = new Text("Column");
        VBox column1HBox7VBox2 = new VBox();
        column1HBox7VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(4));
        MenuBar column1HBox7VBox2MenuBar = new MenuBar();
        column1HBox7VBox2MenuBar.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(4));
        Menu menu4 = new Menu();
        // Create MenuItems for the dropdown menu
        column1HBox7VBox2MenuBar.getMenus().add(menu4);
        column1HBox7VBox1.getChildren().addAll(column1HBox7VBox1Header);
        column1HBox7.getChildren().addAll(column1HBox7VBox1, column1HBox7VBox2MenuBar, column1HBox7VBox2);
        

        HBox column1HBox8 = new HBox();
        VBox column1HBox8VBox1 = new VBox();
        column1HBox8VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox8VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox8VBox1Header = new Text("Sort By");
        VBox column1HBox8VBox2 = new VBox();
        column1HBox8VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        MenuBar column1HBox8VBox2MenuBar = new MenuBar();
        // Create MenuItems for the dropdown menu
//        Text menuBar3Label = new Text("Table");
//    	HBox menuBar3HBox = new HBox(10);
//    	menuBar3HBox.getChildren().addAll(menuBar3Label, column1HBox8VBox2MenuBar);
        column1HBox8VBox1.getChildren().addAll(column1HBox8VBox1Header);
        column1HBox8VBox2.getChildren().addAll(column1HBox8VBox2MenuBar);
        column1HBox8.getChildren().addAll(column1HBox8VBox1, column1HBox8VBox2);
        
        VBox column1VBox3 = new VBox(10);
        
        ButtonBar column1ButtonBar = new ButtonBar(); 
        testButton.setOnAction(event -> { processTestQuery(); });
        saveButton.setDisable(true);
        saveButton.setOnAction(event -> { processSaveButton(column1HBox1VBox2TextField.getText(), column1HBox2VBox2TextField.getText(), column1HBox3VBox2TextField.getText());});
        column1ButtonBar.getButtons().addAll(testButton, saveButton);
        HBox column1HBox9 = new HBox();
        
        
        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header, column1Hbox4, column1HBox5, column1HBox6, column1HBox7, column1HBox8);
        column1VBox3.getChildren().addAll(column1ButtonBar, column1HBox9);
        
        mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, column1VBox3);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);
        
	}


	private void processTestQuery() {
		// TODO Auto-generated method stub
		
	}


	private void processSaveButton(String text, String text2, String text3) {
		// TODO Auto-generated method stub
		
	}
}
