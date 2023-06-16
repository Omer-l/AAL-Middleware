package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextField;

import java.io.IOException;

import dao.DataManager;
import dao.DbXMLParser;
import dao.MySqlConnection;

public class Trigger extends Application{
//	DataManager dm = n8`ew DataManager();
	private String url = "jdbc:mysql://localhost:3306/beacon_localisation";
	private String user = "root";
	private String password = "root";
	private MySqlConnection dbManager = new MySqlConnection(url, user, password);
	
	public static void main(String[] args) {
		launch(args);
	}


    @Override
    public void start(Stage primaryStage) {
		openWhenWindow();
	}
	
    public void openWhenWindow() {
    	//Input rule title etc
        TextField ruleTitleField = new TextField();
        TextField ruleDescriptionField = new TextField();

        Label ruleIDLabel = new Label("ID:");
        Label ruleTitleLabel = new Label("Title:");
        Label ruleDescriptionLabel = new Label("Description:");

        VBox ruleDetailsVBox = new VBox();
        ruleDetailsVBox.getChildren().addAll(
        		ruleTitleLabel, ruleTitleField,
        		ruleDescriptionLabel, ruleDescriptionField);
    	Stage stage = new Stage();
        // Create a MenuItem
        MenuBar menuBar1 = new MenuBar();
        Menu menu1 =  new Menu();
        // Create MenuItems for the dropdown menu
        MenuItem option1 = new MenuItem("MySQL");
        MenuItem option2 = new MenuItem("PostgreSQL");
        menu1.getItems().addAll(option1, option2);
        menuBar1.getMenus().add(menu1);
        // Create a button on the right
        Button configureButton = new Button("Configure");
        // Set an action when the button is clicked
        configureButton.setOnAction(event -> {
            openNewGUI();
        });
        // Create a layout for the buttons
        VBox buttonLayout = new VBox(10);
        buttonLayout.getChildren().addAll(configureButton);
        Text menuBar1Label = new Text("Database Model");
    	HBox menuBar1HBox = new HBox(10);
    	menuBar1HBox.getChildren().addAll(menuBar1Label, menuBar1, buttonLayout);

        MenuBar menuBar2 = new MenuBar();
        Menu menu2 = new Menu();

        // Create MenuItems for the dropdown menu
        menuBar2.getMenus().add(menu2);
        Text menuBar2Label = new Text("Database");
    	HBox menuBar2HBox = new HBox(10);
    	menuBar2HBox.getChildren().addAll(menuBar2Label, menuBar2);

        MenuBar menuBar3 = new MenuBar();
        Menu menu3 = new Menu();

        // Create MenuItems for the dropdown menu
        // Add the MenuItems 
        menuBar3.getMenus().add(menu3);
        Text menuBar3Label = new Text("Table");
    	HBox menuBar3HBox = new HBox(10);
    	menuBar3HBox.getChildren().addAll(menuBar3Label, menuBar3);

        MenuBar menuBar4 = new MenuBar();
        Menu menu4 = new Menu();
        menuBar4.getMenus().add(menu4);
        Text menuBar4Label = new Text("Column");
    	HBox menuBar4HBox = new HBox(10);
    	menuBar4HBox.getChildren().addAll(menuBar4Label, menuBar4);
        setRDBMSMenuOnAction(menu1, menu2, menu3, menu4);

        
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #89cff0");

        //Label for name
        Text text = new Text("Value");
        //Text field for name
        TextField valueField = new TextField();

        addButton.setOnAction(event -> {
        	processAddQuery(menu1, menu2, menu3, menu4, valueField);
        });
        
        // Create a layout for the text and bottom button
        HBox bottomLayout = new HBox(10);
        bottomLayout.setAlignment(Pos.BOTTOM_LEFT);
        bottomLayout.getChildren().addAll(text, valueField);

        HBox bottombutton = new HBox(10);
        bottomLayout.setAlignment(Pos.BOTTOM_LEFT);
        bottomLayout.getChildren().add(addButton);

        VBox root = new VBox(10);
        root.getChildren().addAll(ruleDetailsVBox, menuBar1HBox, menuBar2HBox, menuBar3HBox, menuBar4HBox, bottomLayout,bottombutton);


        // Create a border pane as the root layout
        BorderPane root1 = new BorderPane();
        root1.setPadding(new Insets(10));
        root1.setCenter(root);
//        root1.setRight(buttonLayout);
        root1.setBottom(bottomLayout);

        // Create a scene and set the layout
        Scene scene = new Scene(root1, 500, 400);
        // Create a layout and add the MenuItem to it


        stage.setTitle("Trigger");
        stage.setScene(scene);
        stage.show();
    }
    
    private EventHandler<ActionEvent> processAddQuery(Menu rdbmMenu, Menu databaseMenu, Menu tableMenu, Menu columnMenu, TextField valueField) {
    	String rdbm = rdbmMenu.getText();
    	String db = databaseMenu.getText();
    	String table = tableMenu.getText();
    	String column = columnMenu.getText();
    	String value = valueField.getText();
    	
    	System.out.println(rdbm + ", " + db + ", " + table + ", " + column + ", " + value);
		return null;
	}


	private void setRDBMSMenuOnAction(Menu rdbmMenu, Menu databaseMenu, Menu tableMenu, Menu columnMenu) {
        Menu[] menusToReset = {databaseMenu, tableMenu, columnMenu};
    	rdbmMenu.getItems().get(0).setOnAction(event -> {
            reset(menusToReset);
    		loadDatabasesMenu(rdbmMenu, databaseMenu, "hibernate-mysql.cfg.xml");
    		rdbmMenu.setText(rdbmMenu.getItems().get(0).getText());
            setDatabaseMenuOnAction(databaseMenu, tableMenu, columnMenu);
    	
    	});
    	rdbmMenu.getItems().get(1).setOnAction(event -> {
            reset(menusToReset);
    		loadDatabasesMenu(rdbmMenu, databaseMenu, "hibernate-postgresql.cfg.xml");
    		rdbmMenu.setText(rdbmMenu.getItems().get(1).getText());
            setDatabaseMenuOnAction(databaseMenu, tableMenu, columnMenu);
    		});
	}
    
    private void reset(Menu[] menus) {
		for(Menu menu : menus) {
			menu.setText("");
			menu.getItems().clear();
		}
	}


	private void setDatabaseMenuOnAction(Menu dbMenu, Menu tableMenu, Menu columnMenu) {
        Menu[] menusToReset = {tableMenu, columnMenu};
    	for(MenuItem menuItem : dbMenu.getItems()) {
    		menuItem.setOnAction(event -> {
        		reset(menusToReset);
    			loadTablesMenu(tableMenu, columnMenu, menuItem.getText());
    			System.out.println(menuItem.getText());
        		menuItem.getParentMenu().setText(menuItem.getText());
    		});
    	}
	}
    
    private void loadDatabasesMenu(Menu menu, Menu databaseMenu, String hibernateConfigFileName) {
    	ObservableList<MenuItem> databases = FXCollections.observableArrayList();
    	String[] dbDetails = DbXMLParser.getDBDetailsSQL(hibernateConfigFileName);
    	dbManager.setUrl(dbDetails[0]);
    	dbManager.setUsername(dbDetails[1]);
    	dbManager.setPassword(dbDetails[2]);
    	databaseMenu.getItems().clear();
    	for(String dbName : dbManager.getDatabaseNames())
    		databases.add(new MenuItem(dbName));
    	
    	databaseMenu.getItems().addAll(databases);
    }
    
    private void loadTablesMenu(Menu tableMenu, Menu columnMenu, String databaseName) {
        Menu[] menusToReset = {columnMenu};
    	tableMenu.getItems().clear();
    	System.out.println("LOADING");
    	ObservableList<MenuItem> tables = FXCollections.observableArrayList();
    	for(String tableName : dbManager.getTableNames(databaseName)) {
    		System.out.println(tableName);
    		MenuItem tableOption = new MenuItem(tableName);
    		tableOption.setOnAction(event -> {
    			reset(menusToReset);
    			tableMenu.setText(tableName);
    			loadColumnsMenu(tableMenu, columnMenu, tableName);
    		});
    		tables.add(tableOption);
    	}
    	
    	tableMenu.getItems().addAll(tables);
    }
    
    private void loadColumnsMenu(Menu tableMenu, Menu columnMenu, String tableName) {
    	columnMenu.getItems().clear();
    	ObservableList<MenuItem> columns = FXCollections.observableArrayList();
    	for(String columnName : dbManager.getColumnNames(tableName)) {
    		MenuItem columnOption = new MenuItem(columnName);
    		columnOption.setOnAction(event -> {
    			columnMenu.setText(columnName);
    		});
    		columns.add(columnOption);
    	}
    	
    	columnMenu.getItems().addAll(columns);
    }
    

	private void openNewGUI() {
        Stage newStage = new Stage();
        //Label for name
        Text text = new Text("URL");
        //Text field for name
        TextField nameText = new TextField();
        Text text1 = new Text("Username");
        //Text field for name
        TextField nameText1 = new TextField();
        Text text2 = new Text("Password");
        //Text field for name
        TextField nameText2 = new TextField();

        Button savebutton = new Button("Save");
        savebutton.setStyle("-fx-background-color: #89cff0");


        // Create a layout for the text and bottom button
        HBox h1 = new HBox(10);
        h1.setAlignment(Pos.BASELINE_CENTER);
        h1.getChildren().addAll(text,nameText);

        HBox h2 = new HBox(10);
        h2.setAlignment(Pos.BASELINE_CENTER);
        h2.getChildren().addAll(text1,nameText1);

        HBox h3 = new HBox(10);
        h3.setAlignment(Pos.BASELINE_CENTER);
        h3.getChildren().addAll(text2,nameText2);

        HBox h4 = new HBox(10);
        h4.setAlignment(Pos.BASELINE_CENTER);
        h4.getChildren().add(savebutton);

        VBox h = new VBox(10);
        h.getChildren().addAll(h1,h2,h3,h4);

        Scene scene = new Scene(h, 300, 150);

        newStage.setScene(scene);
        newStage.setTitle("Data Configuration");
        newStage.show();
    }

}