package gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

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
import javafx.scene.shape.SVGPath;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class MainMenu extends Application {
	private final int WINDOW_WIDTH = 900;
	private final int WINDOW_HEIGHT = 800;
	private final int VBOX_HEIGHT = 50;
	private final int VBOX_WIDTH = 600;
	private final int HBOX_HEIGHT = 50;
	private final int HBOX_WIDTH = 500;

	public final static String HEADER = "-fx-font-size: 24px;"
			+ "    -fx-font-weight: bold;"
			+ "    -fx-fill: #512DA8;";
	public final static String HEADER_1_STYLE = "-fx-font-size: 21px;"
			+ "    -fx-font-weight: bold;"
			+ "    -fx-fill: #8E24AA;";
	public final static String HEADER_2_STYLE = "-fx-font-size: 20px;"
			+ "    -fx-font-weight: bold;"
			+ "    -fx-fill: #EF6C00;";
	
	public final static String CARD_STYLE = "-fx-background-color:  white ; "
		
			+ "-fx-background-radius: 10;";
	
	public final static String DATABASE_ICON_PATH = "M448 80v48c0 44.2-100.3 80-224 80S0 172.2 0 128V80C0 35.8 100.3 0 224 0S448 35.8 448 80zM393.2 214.7c20.8-7.4 39.9-16.9 54.8-28.6V288c0 44.2-100.3 80-224 80S0 332.2 0 288V186.1c14.9 11.8 34 21.2 54.8 28.6C99.7 230.7 159.5 240 224 240s124.3-9.3 169.2-25.3zM0 346.1c14.9 11.8 34 21.2 54.8 28.6C99.7 390.7 159.5 400 224 400s124.3-9.3 169.2-25.3c20.8-7.4 39.9-16.9 54.8-28.6V432c0 44.2-100.3 80-224 80S0 476.2 0 432V346.1z";
	public final static double ICON_WIDTH = 40;
	public final static double ICON_HEIGHT = 30;
	public final static String ICON_STYLE = "-fx-background-color: black;";
	

	public final static String MENU_ADD_NEW_EVENT_BUTTON_STYLE = "-fx-background-color:   yellow; -fx-border-color: black;";
	public final static String MAIN_CONTENT_STYLE = "-fx-background-color:  #d3d3d3;";
	public final static String GUI_BACKGROUND_STYLE = "-fx-background-color:   #FAF0DC;";
	public final static ArrayList<String> databaseQueries = new ArrayList<String>();
	//ensure connection to middleware is established before allowing configuring of middleware
	public static MySqlConnection mainDbManager = new MySqlConnection("jdbc:mysql://localhost:3306/middleware", "root", "root");
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
    	
    		MainMenu mm = new MainMenu();
    		mm.open();
    		
//    		DatabaseSettings s = new DatabaseSettings();
//    		s.open();
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
    
    public void open() {
    	clearMainBox();
    	changeTitle("Main Menu");
    	//LEFT SIDE MAIN
    	VBox mainVBox1 = new VBox(2);
    	mainVBox1.prefWidthProperty().bind(root.widthProperty().divide(2));
    	
    	//SVG PATH 
    	// Create SVGPath with the icon path data
        SVGPath databaseIcon = new SVGPath();
        databaseIcon.setContent(DATABASE_ICON_PATH);
        databaseIcon.setStyle("-fx-fill: #007ACC;");
        databaseIcon.setScaleX(ICON_WIDTH / 500); // Scale based on width
        databaseIcon.setScaleY(ICON_HEIGHT / 500);
        
        final Region svgshape = new Region();
        svgshape.setShape(databaseIcon);
        svgshape.setMinSize(ICON_WIDTH, ICON_HEIGHT);
        svgshape.setPrefSize(ICON_WIDTH, ICON_HEIGHT);
        svgshape.setMaxSize(ICON_WIDTH, ICON_HEIGHT);
        svgshape.setStyle(ICON_STYLE);
        
        
    	//Configurations
    	VBox configVBox = new VBox(2);
    	Text configHeader = new Text("Configuration");
    	configHeader.setStyle(HEADER_1_STYLE);
    	HBox configHBox1 = new HBox();
    	configHBox1.setPrefHeight(HBOX_HEIGHT);
    	configHBox1.setPrefWidth(HBOX_WIDTH);
    	VBox configVBox1 = new VBox();
//    	configVBox1.setPrefHeight(VBOX_HEIGHT);
//    	configVBox1.setPrefWidth(VBOX_WIDTH);
    	configVBox1.setStyle(CARD_STYLE);
    	Text configVBox1Header = new Text("Database");
    	configVBox1Header.setStyle(HEADER_2_STYLE);
    	Text configVBox1Details = new Text("Read and Write database");
    	configVBox1.getChildren().addAll(configVBox1Header, configVBox1Details);
    	configHBox1.getChildren().addAll(new StackPane(svgshape), configVBox1);
    	configVBox1.setOnMouseClicked(event -> { goToDatabaseListWindow(null);});
    	VBox configVBox2 = new VBox();
    	configVBox2.setStyle(CARD_STYLE);
    	Text configVBox2Header = new Text("System");
    	configVBox2Header.setStyle(HEADER_2_STYLE);
    	Text configVBox2Details = new Text("Run/Read/Write files");
    	configVBox2.getChildren().addAll(configVBox2Header, configVBox2Details);
    	configVBox2.setOnMouseClicked(event -> { new FileEvents(null).open();});
    	VBox configVBox3 = new VBox();
    	configVBox3.setStyle(CARD_STYLE);
    	Text configVBox3Header = new Text("Schedules");
    	configVBox3Header.setStyle(HEADER_2_STYLE);
    	Text configVBox3Details = new Text("Schedules to keep track of");
    	configVBox3.getChildren().addAll(configVBox3Header, configVBox3Details);
    	configVBox3.setOnMouseClicked(event -> { new Schedules(null).open();});
    	VBox configVBox4 = new VBox();
    	configVBox4.setStyle(CARD_STYLE);
    	Text configVBox4Header = new Text("Hardware & Services");
    	configVBox4Header.setStyle(HEADER_2_STYLE);
    	Text configVBox4Details = new Text("Define, connect and use API of hardware and online services");
    	configVBox4.getChildren().addAll(configVBox4Header, configVBox4Details);
    	configVBox.getChildren().addAll(configHeader, configHBox1, configVBox2, configVBox3, configVBox4);
    	//Automation
    	VBox automationVBox = new VBox(2);
    	Text automationHeader = new Text("Automation");
    	automationHeader.setStyle(HEADER_1_STYLE);
    	VBox automationVBox1 = new VBox();
    	automationVBox1.setStyle(CARD_STYLE);
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
    	settingsVBox.setOnMouseClicked(event -> { new DatabaseSettings(null).open();});
    	VBox settingsVBox1 = new VBox(2);
    	settingsVBox1.setStyle(CARD_STYLE);
    	Text settingsVBox1Header = new Text("Database");
    	settingsVBox1Header.setStyle(HEADER_2_STYLE);
    	Text settingsVBox1Details = new Text("RDBM URL, Username, Password");
    	settingsVBox1.getChildren().addAll(settingsVBox1Header, settingsVBox1Details);
    	settingsVBox.getChildren().addAll(settingsHeader, settingsVBox1);
//		String cssFile = getClass().getResource("styles.css").toExternalForm();
//    	root.getStylesheets().add(cssFile);
    	
    	
    	mainVBox2.getChildren().addAll(settingsVBox);

    	addHoverInteraction(new Pane[] {configHBox1, configVBox2, configVBox3, configVBox4, automationVBox1, settingsVBox1}, "white", "darkgray");
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
    	newTitle.setStyle(HEADER);
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
    	MainMenu mm = new MainMenu();
    	mm.open();
	}

	public static void addHoverInteraction(Pane[] buttons, String fromColor, String toColor) {
    	for(Pane button : buttons) {
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
