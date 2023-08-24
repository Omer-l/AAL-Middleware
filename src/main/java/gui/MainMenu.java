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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private final int WINDOW_WIDTH = 700;
	private final int WINDOW_HEIGHT = 800;
	private final int VBOX_HEIGHT = 50;
	private final int VBOX_WIDTH = 600;
	private final int HBOX_HEIGHT = 80;
	private final int HBOX_WIDTH = 1000;

	public final static String HEADER = "-fx-font-size: 24px;"
			+ "    -fx-font-weight: bold;"
			+ "    -fx-fill: #D81B60;";
	public final static String HEADER_1_STYLE = "-fx-font-size: 21px;"
			+ "    -fx-font-weight: bold;"
			+ "    -fx-fill: #EC407A;";
	public final static String HEADER_2_STYLE = "-fx-font-size: 20px;"
			+ "    -fx-font-weight: bold;"
			+ "    -fx-fill: #F48FB1;";
	
	public final static String CARD_STYLE = "-fx-background-color:  white ; ";
	
	public final static String HBOX_LIST_STYLE = "-fx-background-color: #FFEBEE ;"
			+ "-fx-background-radius: 10; ";
	
	public final static double ICON_WIDTH = 75;
	public final static double ICON_HEIGHT = 70;
	
	public static String DATABASE_SVG = "M448 80v48c0 44.2-100.3 80-224 80S0 172.2 0 128V80C0 35.8 100.3 0 224 0S448 35.8 448 80zM393.2 214.7c20.8-7.4 39.9-16.9 54.8-28.6V288c0 44.2-100.3 80-224 80S0 332.2 0 288V186.1c14.9 11.8 34 21.2 54.8 28.6C99.7 230.7 159.5 240 224 240s124.3-9.3 169.2-25.3zM0 346.1c14.9 11.8 34 21.2 54.8 28.6C99.7 390.7 159.5 400 224 400s124.3-9.3 169.2-25.3c20.8-7.4 39.9-16.9 54.8-28.6V432c0 44.2-100.3 80-224 80S0 476.2 0 432V346.1z";
	public static String SYSTEM_SVG = "M308.5 135.3c7.1-6.3 9.9-16.2 6.2-25c-2.3-5.3-4.8-10.5-7.6-15.5L304 89.4c-3-5-6.3-9.9-9.8-14.6c-5.7-7.6-15.7-10.1-24.7-7.1l-28.2 9.3c-10.7-8.8-23-16-36.2-20.9L199 27.1c-1.9-9.3-9.1-16.7-18.5-17.8C173.9 8.4 167.2 8 160.4 8h-.7c-6.8 0-13.5 .4-20.1 1.2c-9.4 1.1-16.6 8.6-18.5 17.8L115 56.1c-13.3 5-25.5 12.1-36.2 20.9L50.5 67.8c-9-3-19-.5-24.7 7.1c-3.5 4.7-6.8 9.6-9.9 14.6l-3 5.3c-2.8 5-5.3 10.2-7.6 15.6c-3.7 8.7-.9 18.6 6.2 25l22.2 19.8C32.6 161.9 32 168.9 32 176s.6 14.1 1.7 20.9L11.5 216.7c-7.1 6.3-9.9 16.2-6.2 25c2.3 5.3 4.8 10.5 7.6 15.6l3 5.2c3 5.1 6.3 9.9 9.9 14.6c5.7 7.6 15.7 10.1 24.7 7.1l28.2-9.3c10.7 8.8 23 16 36.2 20.9l6.1 29.1c1.9 9.3 9.1 16.7 18.5 17.8c6.7 .8 13.5 1.2 20.4 1.2s13.7-.4 20.4-1.2c9.4-1.1 16.6-8.6 18.5-17.8l6.1-29.1c13.3-5 25.5-12.1 36.2-20.9l28.2 9.3c9 3 19 .5 24.7-7.1c3.5-4.7 6.8-9.5 9.8-14.6l3.1-5.4c2.8-5 5.3-10.2 7.6-15.5c3.7-8.7 .9-18.6-6.2-25l-22.2-19.8c1.1-6.8 1.7-13.8 1.7-20.9s-.6-14.1-1.7-20.9l22.2-19.8zM112 176a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zM504.7 500.5c6.3 7.1 16.2 9.9 25 6.2c5.3-2.3 10.5-4.8 15.5-7.6l5.4-3.1c5-3 9.9-6.3 14.6-9.8c7.6-5.7 10.1-15.7 7.1-24.7l-9.3-28.2c8.8-10.7 16-23 20.9-36.2l29.1-6.1c9.3-1.9 16.7-9.1 17.8-18.5c.8-6.7 1.2-13.5 1.2-20.4s-.4-13.7-1.2-20.4c-1.1-9.4-8.6-16.6-17.8-18.5L583.9 307c-5-13.3-12.1-25.5-20.9-36.2l9.3-28.2c3-9 .5-19-7.1-24.7c-4.7-3.5-9.6-6.8-14.6-9.9l-5.3-3c-5-2.8-10.2-5.3-15.6-7.6c-8.7-3.7-18.6-.9-25 6.2l-19.8 22.2c-6.8-1.1-13.8-1.7-20.9-1.7s-14.1 .6-20.9 1.7l-19.8-22.2c-6.3-7.1-16.2-9.9-25-6.2c-5.3 2.3-10.5 4.8-15.6 7.6l-5.2 3c-5.1 3-9.9 6.3-14.6 9.9c-7.6 5.7-10.1 15.7-7.1 24.7l9.3 28.2c-8.8 10.7-16 23-20.9 36.2L315.1 313c-9.3 1.9-16.7 9.1-17.8 18.5c-.8 6.7-1.2 13.5-1.2 20.4s.4 13.7 1.2 20.4c1.1 9.4 8.6 16.6 17.8 18.5l29.1 6.1c5 13.3 12.1 25.5 20.9 36.2l-9.3 28.2c-3 9-.5 19 7.1 24.7c4.7 3.5 9.5 6.8 14.6 9.8l5.4 3.1c5 2.8 10.2 5.3 15.5 7.6c8.7 3.7 18.6 .9 25-6.2l19.8-22.2c6.8 1.1 13.8 1.7 20.9 1.7s14.1-.6 20.9-1.7l19.8 22.2zM464 304a48 48 0 1 1 0 96 48 48 0 1 1 0-96z";
	public static String SCHEDULE_SVG = "M160 0c13.3 0 24 10.7 24 24V64H328V24c0-13.3 10.7-24 24-24s24 10.7 24 24V64h40c35.3 0 64 28.7 64 64v16 48V448c0 35.3-28.7 64-64 64H96c-35.3 0-64-28.7-64-64V192 144 128c0-35.3 28.7-64 64-64h40V24c0-13.3 10.7-24 24-24zM432 192H80V448c0 8.8 7.2 16 16 16H416c8.8 0 16-7.2 16-16V192zm-95 89l-47 47 47 47c9.4 9.4 9.4 24.6 0 33.9s-24.6 9.4-33.9 0l-47-47-47 47c-9.4 9.4-24.6 9.4-33.9 0s-9.4-24.6 0-33.9l47-47-47-47c-9.4-9.4-9.4-24.6 0-33.9s24.6-9.4 33.9 0l47 47 47-47c9.4-9.4 24.6-9.4 33.9 0s9.4 24.6 0 33.9z";
	public static String HARDWARE_SVG = "M218.3 8.5c12.3-11.3 31.2-11.3 43.4 0l208 192c6.7 6.2 10.3 14.8 10.3 23.5H336c-19.1 0-36.3 8.4-48 21.7V208c0-8.8-7.2-16-16-16H208c-8.8 0-16 7.2-16 16v64c0 8.8 7.2 16 16 16h64V416H112c-26.5 0-48-21.5-48-48V256H32c-13.2 0-25-8.1-29.8-20.3s-1.6-26.2 8.1-35.2l208-192zM352 304V448H544V304H352zm-48-16c0-17.7 14.3-32 32-32H560c17.7 0 32 14.3 32 32V448h32c8.8 0 16 7.2 16 16c0 26.5-21.5 48-48 48H544 352 304c-26.5 0-48-21.5-48-48c0-8.8 7.2-16 16-16h32V288z";
	public static String RULE_SVG = "M0 80v48c0 17.7 14.3 32 32 32H48 96V80c0-26.5-21.5-48-48-48S0 53.5 0 80zM112 32c10 13.4 16 30 16 48V384c0 35.3 28.7 64 64 64s64-28.7 64-64v-5.3c0-32.4 26.3-58.7 58.7-58.7H480V128c0-53-43-96-96-96H112zM464 480c61.9 0 112-50.1 112-112c0-8.8-7.2-16-16-16H314.7c-14.7 0-26.7 11.9-26.7 26.7V384c0 53-43 96-96 96H368h96z";
	public static String DB_SETTINGS_SVG = "M495.9 166.6c3.2 8.7 .5 18.4-6.4 24.6l-43.3 39.4c1.1 8.3 1.7 16.8 1.7 25.4s-.6 17.1-1.7 25.4l43.3 39.4c6.9 6.2 9.6 15.9 6.4 24.6c-4.4 11.9-9.7 23.3-15.8 34.3l-4.7 8.1c-6.6 11-14 21.4-22.1 31.2c-5.9 7.2-15.7 9.6-24.5 6.8l-55.7-17.7c-13.4 10.3-28.2 18.9-44 25.4l-12.5 57.1c-2 9.1-9 16.3-18.2 17.8c-13.8 2.3-28 3.5-42.5 3.5s-28.7-1.2-42.5-3.5c-9.2-1.5-16.2-8.7-18.2-17.8l-12.5-57.1c-15.8-6.5-30.6-15.1-44-25.4L83.1 425.9c-8.8 2.8-18.6 .3-24.5-6.8c-8.1-9.8-15.5-20.2-22.1-31.2l-4.7-8.1c-6.1-11-11.4-22.4-15.8-34.3c-3.2-8.7-.5-18.4 6.4-24.6l43.3-39.4C64.6 273.1 64 264.6 64 256s.6-17.1 1.7-25.4L22.4 191.2c-6.9-6.2-9.6-15.9-6.4-24.6c4.4-11.9 9.7-23.3 15.8-34.3l4.7-8.1c6.6-11 14-21.4 22.1-31.2c5.9-7.2 15.7-9.6 24.5-6.8l55.7 17.7c13.4-10.3 28.2-18.9 44-25.4l12.5-57.1c2-9.1 9-16.3 18.2-17.8C227.3 1.2 241.5 0 256 0s28.7 1.2 42.5 3.5c9.2 1.5 16.2 8.7 18.2 17.8l12.5 57.1c15.8 6.5 30.6 15.1 44 25.4l55.7-17.7c8.8-2.8 18.6-.3 24.5 6.8c8.1 9.8 15.5 20.2 22.1 31.2l4.7 8.1c6.1 11 11.4 22.4 15.8 34.3zM256 336a80 80 0 1 0 0-160 80 80 0 1 0 0 160z";
	
	

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
    	
    	
    	//Configurations
    	VBox configVBox = new VBox(2);
    	Text configHeader = new Text("Configuration");
    	configHeader.setStyle(HEADER_1_STYLE);
    	
    	HBox configHBox1 = new HBox();

    	configHBox1.setPrefHeight(HBOX_HEIGHT);
    	configHBox1.setPrefWidth(HBOX_WIDTH);
    	configHBox1.setStyle(HBOX_LIST_STYLE);
    	
    	SVGPath svg = new SVGPath();
    	svg.setContent(DATABASE_SVG);
    	final Region svgShape = new Region();
    	svgShape.setShape(svg);
    	svgShape.setMinSize(ICON_WIDTH, ICON_HEIGHT);
        svgShape.setPrefSize(ICON_WIDTH, ICON_HEIGHT);
        svgShape.setMaxSize(ICON_WIDTH, ICON_HEIGHT);
        svgShape.setStyle("-fx-background-color: black;");
    	
    	VBox configVBox1 = new VBox();
    	//configVBox1.setStyle(CARD_STYLE);
    	Text configVBox1Header = new Text("Database");
    	configVBox1Header.setStyle(HEADER_2_STYLE);
    	Text configVBox1Details = new Text("Read and Write database");
    	configVBox1.getChildren().addAll(configVBox1Header, configVBox1Details);
    	configHBox1.getChildren().addAll(new StackPane(svgShape), configVBox1);
    	configHBox1.setOnMouseClicked(event -> { goToDatabaseListWindow(null);});
    	
    	//second 
    	

    	HBox configHBox2 = new HBox();

    	configHBox2.setPrefHeight(HBOX_HEIGHT);
    	configHBox2.setPrefWidth(HBOX_WIDTH);
    	configHBox2.setStyle(HBOX_LIST_STYLE);
    	
    	SVGPath svg2 = new SVGPath();
    	svg2.setContent(SYSTEM_SVG);
    	final Region svgShape2 = new Region();
    	svgShape2.setShape(svg2);
    	svgShape2.setMinSize(ICON_WIDTH, ICON_HEIGHT);
        svgShape2.setPrefSize(ICON_WIDTH, ICON_HEIGHT);
        svgShape2.setMaxSize(ICON_WIDTH, ICON_HEIGHT);
        svgShape2.setStyle("-fx-background-color: black;");
    	
    	VBox configVBox2 = new VBox();
//    	configVBox2.setStyle(CARD_STYLE);
    	Text configVBox2Header = new Text("System");
    	configVBox2Header.setStyle(HEADER_2_STYLE);
    	Text configVBox2Details = new Text("Run,Read and Write files");
    	configVBox2.getChildren().addAll(configVBox2Header, configVBox2Details);
    	configHBox2.getChildren().addAll(new StackPane(svgShape2), configVBox2);
    	configHBox2.setOnMouseClicked(event -> { goToDatabaseListWindow(null);});
    	
    	
    	 HBox configHBox3 = new HBox();

     	configHBox3.setPrefHeight(HBOX_HEIGHT);
     	configHBox3.setPrefWidth(HBOX_WIDTH);
     	configHBox3.setStyle(HBOX_LIST_STYLE);
     	
     	SVGPath svg3 = new SVGPath();
     	svg3.setContent(SCHEDULE_SVG);
     	final Region svgShape3 = new Region();
     	svgShape3.setShape(svg3);
     	svgShape3.setMinSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape3.setPrefSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape3.setMaxSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape3.setStyle("-fx-background-color: black;");
    
    	VBox configVBox3 = new VBox();
//    	configVBox3.setStyle(CARD_STYLE);
    	Text configVBox3Header = new Text("Schedules");
    	configVBox3Header.setStyle(HEADER_2_STYLE);
    	Text configVBox3Details = new Text("Schedules to keep track of");
    	configVBox3.getChildren().addAll(configVBox3Header, configVBox3Details);
    	configHBox3.getChildren().addAll(new StackPane(svgShape3), configVBox3);
    	configHBox3.setOnMouseClicked(event -> { new Schedules(null).open();});
    	
    	HBox configHBox4 = new HBox();

     	configHBox4.setPrefHeight(HBOX_HEIGHT);
     	configHBox4.setPrefWidth(HBOX_WIDTH);
     	configHBox4.setStyle(HBOX_LIST_STYLE);
     	
     	SVGPath svg4 = new SVGPath();
     	svg4.setContent(HARDWARE_SVG);
     	final Region svgShape4 = new Region();
     	svgShape4.setShape(svg4);
     	svgShape4.setMinSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape4.setPrefSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape4.setMaxSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape4.setStyle("-fx-background-color: black;");
    	
    	
    	
    	VBox configVBox4 = new VBox();
//    	configVBox4.setStyle(CARD_STYLE);
    	Text configVBox4Header = new Text("Hardware & Services");
    	configVBox4Header.setStyle(HEADER_2_STYLE);
    	Text configVBox4Details = new Text("Define, connect and use API of hardware and online services");
    	configVBox4.getChildren().addAll(configVBox4Header, configVBox4Details);
    	configHBox4.getChildren().addAll(new StackPane(svgShape4), configVBox4);
    	
    	configVBox.getChildren().addAll(configHeader, configHBox1, configHBox2, configHBox3, configHBox4);
    	
    	
    	
    	//Automation
    	VBox automationVBox = new VBox(2);
    	Text automationHeader = new Text("Automation");
    	automationHeader.setStyle(HEADER_1_STYLE);
 
    	HBox configHBox5 = new HBox();

     	configHBox5.setPrefHeight(HBOX_HEIGHT);
     	configHBox5.setPrefWidth(HBOX_WIDTH);
     	configHBox5.setStyle(HBOX_LIST_STYLE);
     	
     	SVGPath svg5 = new SVGPath();
     	svg5.setContent(RULE_SVG);
     	final Region svgShape5 = new Region();
     	svgShape5.setShape(svg5);
     	svgShape5.setMinSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape5.setPrefSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape5.setMaxSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape5.setStyle("-fx-background-color: black;");
    	
    	
    	
    	VBox automationVBox1 = new VBox();
    	automationVBox1.setStyle(CARD_STYLE);
    	Text automationVBox1Header = new Text("Rules");
    	automationVBox1Header.setStyle(HEADER_2_STYLE);
    	Text automationVBox1Details = new Text("Rules in system");
    	automationVBox1.getChildren().addAll(automationVBox1Header, automationVBox1Details);
    	configHBox5.getChildren().addAll(new StackPane(svgShape5),automationVBox1);
    	automationVBox1.setOnMouseClicked(event -> { goToRulesListWindow(null); });
    	automationVBox.getChildren().addAll(automationHeader, configHBox5);
    	
    	mainVBox1.getChildren().addAll(configVBox, automationVBox);
    	// Set alignment to center within the VBox
    	mainVBox1.setAlignment(Pos.TOP_CENTER);
    	
    	//RIGHT SIDE MAIN
//    	VBox mainVBox2 = new VBox(2);
//    	mainVBox2.prefWidthProperty().bind(root.widthProperty().divide(2));
    	
    	
    	HBox configHBox6 = new HBox();

     	configHBox6.setPrefHeight(HBOX_HEIGHT);
     	configHBox6.setPrefWidth(HBOX_WIDTH);
     	configHBox6.setStyle(HBOX_LIST_STYLE);
     	
     	SVGPath svg6 = new SVGPath();
     	svg6.setContent(DB_SETTINGS_SVG);
     	final Region svgShape6 = new Region();
     	svgShape6.setShape(svg6);
     	svgShape6.setMinSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape6.setPrefSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape6.setMaxSize(ICON_WIDTH, ICON_HEIGHT);
         svgShape6.setStyle("-fx-background-color: black;");
    	
    	
    	
    	//Settings
    	VBox settingsVBox = new VBox(2);
    	Text settingsHeader = new Text("Settings");
    	settingsHeader.setStyle(HEADER_1_STYLE);
    	settingsVBox.setOnMouseClicked(event -> { new DatabaseSettings(null).open();});
    	VBox settingsVBox1 = new VBox(2);
    	//settingsVBox1.setStyle(CARD_STYLE);
    	Text settingsVBox1Header = new Text("Database");
    	settingsVBox1Header.setStyle(HEADER_2_STYLE);
    	Text settingsVBox1Details = new Text("RDBM URL, Username, Password");
    	settingsVBox1.getChildren().addAll(settingsVBox1Header, settingsVBox1Details);
    	settingsVBox.getChildren().addAll(settingsHeader, configHBox6);
    	configHBox6.getChildren().addAll(new StackPane(svgShape6),settingsVBox1);

    	mainVBox1.getChildren().add(settingsVBox);

    	
    	//mainVBox2.getChildren().addAll(settingsVBox);
    	addHoverInteraction(new Pane[] {configHBox1, configHBox2, configHBox3, configHBox4, automationVBox1,settingsVBox}, "white", "darkgray");
    	mainHBox.getChildren().addAll(mainVBox1);
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
//	    	button.setStyle("-fx-background-color: " + fromColor +";");
	
	        // Set the initial appearance of the pane
//	    	button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + toColor + ";"));
//	    	button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + fromColor + ";"));
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
