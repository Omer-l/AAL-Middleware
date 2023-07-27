package gui;

import java.util.ArrayList;

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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class MainMenu extends Application {
	private final int WINDOW_WIDTH = 900;
	private final int WINDOW_HEIGHT = 800;
	public final static String HEADER_1_STYLE = "-fx-font-size: 20px;";
	public final static String HEADER_2_STYLE = "-fx-font-weight:   bold; -fx-font-size: 16px;";
	public final static String MENU_BUTTON_STYLE = "-fx-background-color:   white; -fx-border-color: black;";
	public final static String MENU_ADD_NEW_EVENT_BUTTON_STYLE = "-fx-background-color:   yellow; -fx-border-color: black;";
	public final static String MAIN_CONTENT_STYLE = "-fx-background-color:  #d3d3d3;";
	public final static String GUI_BACKGROUND_STYLE = "-fx-background-color:   #FAF0DC;";
	public final static ArrayList<String> databaseQueries = new ArrayList<String>();
	public static MySqlConnection mainDbManager = new MySqlConnection();
	public static VBox root = new VBox();
	//NAVBAR
	public static VBox menuBarVBox = new VBox();
	public static HBox menuBarHBox = new HBox();
	public static HBox titleHBox = new HBox();
	
	//MAIN
	public static HBox mainHBox = new HBox(10);
	//MAIN
	public static HBox mainVBox = new HBox(10);
	//MAIN
	public static Stage primaryStage;
    public static void main(String[] args) {
    	//ensure connection to middleware is established before allowing configuring of middleware
		mainDbManager.setUrl("jdbc:mysql://localhost:3306/middleware");
		mainDbManager.setUsername("root");
		mainDbManager.setPassword("root");
<<<<<<< HEAD
    	open();
//		AddFileEvent adr = new AddFileEvent();
//    	adr.open();
=======
//    	open();
		AddRule adr = new AddRule();
    	adr.open();
>>>>>>> 7e4bce40bab7fe7716d44ba486bae2013815c8ca
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setTitle("Middleware");
        primaryStage.show();
    	menuBarVBox.getChildren().addAll(menuBarHBox, titleHBox);
    	root.getChildren().addAll(menuBarVBox, mainHBox);
    	root.setStyle(GUI_BACKGROUND_STYLE);
    	titleHBox.setAlignment(Pos.CENTER);
    	menuBarVBox.prefHeightProperty().bind(root.heightProperty().divide(10));
    	menuBarVBox.prefWidthProperty().bind(root.widthProperty());
    	mainHBox.prefWidthProperty().bind(root.widthProperty());
    	mainHBox.prefHeightProperty().bind(root.heightProperty().divide(10).multiply(9));
    	mainHBox.setStyle(MAIN_CONTENT_STYLE);
    }
    
    public static void open() {
    	clearMainBox();
    	changeTitle("Main Menu");
    	//LEFT SIDE MAIN
    	VBox mainVBox1 = new VBox(2);
    	mainVBox1.prefWidthProperty().bind(root.widthProperty().divide(2));
    	//Configurations
    	VBox configVBox = new VBox(2);
    	Text configHeader = new Text("Configuration");
    	configHeader.setStyle(HEADER_1_STYLE);
    	VBox configVBox1 = new VBox();
    	configVBox1.setStyle(MENU_BUTTON_STYLE);
    	Text configVBox1Header = new Text("Database");
    	configVBox1Header.setStyle(HEADER_2_STYLE);
    	Text configVBox1Details = new Text("Read/Write database");
    	configVBox1.getChildren().addAll(configVBox1Header, configVBox1Details);
    	configVBox1.setOnMouseClicked(event -> { goToDatabaseListWindow(null);});
    	VBox configVBox2 = new VBox();
    	configVBox2.setStyle(MENU_BUTTON_STYLE);
    	Text configVBox2Header = new Text("System");
    	configVBox2Header.setStyle(HEADER_2_STYLE);
    	Text configVBox2Details = new Text("Run/Read/Write files");
    	configVBox2.getChildren().addAll(configVBox2Header, configVBox2Details);
    	configVBox2.setOnMouseClicked(event -> { new FileEvents(null).open();});
    	VBox configVBox3 = new VBox();
    	configVBox3.setStyle(MENU_BUTTON_STYLE);
    	Text configVBox3Header = new Text("Schedules");
    	configVBox3Header.setStyle(HEADER_2_STYLE);
    	Text configVBox3Details = new Text("Schedules to keep track of");
    	configVBox3.getChildren().addAll(configVBox3Header, configVBox3Details);
    	VBox configVBox4 = new VBox();
    	configVBox4.setStyle(MENU_BUTTON_STYLE);
    	Text configVBox4Header = new Text("Hardware & Services");
    	configVBox4Header.setStyle(HEADER_2_STYLE);
    	Text configVBox4Details = new Text("Define, connect and use API of hardware and online services");
    	configVBox4.getChildren().addAll(configVBox4Header, configVBox4Details);
    	configVBox.getChildren().addAll(configHeader, configVBox1, configVBox2, configVBox3, configVBox4);
    	//Automation
    	VBox automationVBox = new VBox(2);
    	Text automationHeader = new Text("Automation");
    	automationHeader.setStyle(HEADER_1_STYLE);
    	VBox automationVBox1 = new VBox();
    	automationVBox1.setStyle(MENU_BUTTON_STYLE);
    	Text automationVBox1Header = new Text("Rules");
    	automationVBox1Header.setStyle(HEADER_2_STYLE);
    	Text automationVBox1Details = new Text("Rules in system");
    	automationVBox1.getChildren().addAll(automationVBox1Header, automationVBox1Details);
    	automationVBox1.setOnMouseClicked(event -> { goToRulesListWindow(null); });
    	automationVBox.getChildren().addAll(automationHeader, automationVBox1);
    	mainVBox1.getChildren().addAll(configVBox, automationVBox);
    	//RIGHT SIDE MAIN
    	VBox mainVBox2 = new VBox(2);
    	mainVBox2.prefWidthProperty().bind(root.widthProperty().divide(2));
    	//Settings
    	VBox settingsVBox = new VBox(2);
    	Text settingsHeader = new Text("Settings");
    	settingsHeader.setStyle(HEADER_1_STYLE);
    	VBox settingsVBox1 = new VBox(2);
    	settingsVBox1.setStyle(MENU_BUTTON_STYLE);
    	Text settingsVBox1Header = new Text("Database");
    	settingsVBox1Header.setStyle(HEADER_2_STYLE);
    	Text settingsVBox1Details = new Text("RDBM URL, Username, Password");
    	settingsVBox1.getChildren().addAll(settingsVBox1Header, settingsVBox1Details);
    	settingsVBox.getChildren().addAll(settingsHeader, settingsVBox1);
    	mainVBox2.getChildren().addAll(settingsVBox);

    	addHoverInteraction(new VBox[] {configVBox1, configVBox2, configVBox3, configVBox4, automationVBox1, settingsVBox1}, "white", "darkgray");
    	mainHBox.getChildren().addAll(mainVBox1, mainVBox2);
    }
    
    public static void clearMainBox() {
		menuBarHBox.getChildren().clear();
    	mainHBox.getChildren().clear();
    	mainVBox.getChildren().clear();
    	titleHBox.getChildren().clear();
    }
    
    public static void changeTitle(String title) {
    	Text newTitle = new Text(title);
    	newTitle.setStyle(HEADER_1_STYLE);
    	titleHBox.getChildren().add(newTitle);
    }
    
    public static void goToRulesListWindow(Window prevWindow) {
    	Rules rulesWindow = new Rules(prevWindow);
    	rulesWindow.open();
	}
    
    public static void goToDatabaseListWindow(Window prevWindow) {
    	DatabaseEvents de = new DatabaseEvents(prevWindow);
    	de.open();
	}
    
    public static void goToMainMenu() {
    	menuBarHBox.getChildren().clear();
    	MainMenu.open();
	}

	public static void addHoverInteraction(VBox[] buttons, String fromColor, String toColor) {
    	for(VBox button : buttons) {
	    	button.setStyle("-fx-background-color: " + fromColor +";");
	
	        // Set the initial appearance of the pane
	    	button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + toColor + ";"));
	    	button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + fromColor + ";"));
    	}
    }
	
	public static void setActive(Button button) {
    	button.setStyle("-fx-background-color: darkgray;");
	}
	
	public static void deactivate(Button button) {
    	button.setStyle("");
	}

	public static boolean isActive(Button runFileMethodButton) {
		System.out.println(runFileMethodButton.getStyle());
		return runFileMethodButton.getStyle().contains("darkgray");
	}
}
