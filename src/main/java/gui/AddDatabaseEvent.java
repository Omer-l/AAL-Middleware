package gui;
import javax.swing.plaf.metal.MetalBorders.TextFieldBorder;

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
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class AddDatabaseEvent {
	private String url = "jdbc:mysql://localhost:3306/beacon_localisation";
	private String user = "root";
	private String password = "root";
	private MySqlConnection dbManager = new MySqlConnection(url, user, password);
    private Menu menu5 = new Menu();

	public void open() {
		MainMenu.changeTitle("Add Database Event");
        Button button1 = new Button("Back");
        button1.setOnAction(event -> {MainMenu.goToMainMenu(); });
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
        column1HBox7VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        MenuBar column1HBox7VBox2MenuBar = new MenuBar();
        Menu menu4 = new Menu();
        // Create MenuItems for the dropdown menu
        column1HBox7VBox2MenuBar.getMenus().add(menu4);
//        Text menuBar3Label = new Text("Table");
//    	HBox menuBar3HBox = new HBox(10);
//    	menuBar3HBox.getChildren().addAll(menuBar3Label, column1HBox7VBox2MenuBar);
        column1HBox7VBox1.getChildren().addAll(column1HBox7VBox1Header);
        column1HBox7VBox2.getChildren().addAll(column1HBox7VBox2MenuBar);
        column1HBox7.getChildren().addAll(column1HBox7VBox1, column1HBox7VBox2);
        

        HBox column1HBox8 = new HBox();
        VBox column1HBox8VBox1 = new VBox();
        column1HBox8VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox8VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox8VBox1Header = new Text("Sort By");
        VBox column1HBox8VBox2 = new VBox();
        column1HBox8VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        MenuBar column1HBox8VBox2MenuBar = new MenuBar();
        // Create MenuItems for the dropdown menu
        column1HBox8VBox2MenuBar.getMenus().add(menu5);
//        Text menuBar3Label = new Text("Table");
//    	HBox menuBar3HBox = new HBox(10);
//    	menuBar3HBox.getChildren().addAll(menuBar3Label, column1HBox8VBox2MenuBar);
        column1HBox8VBox1.getChildren().addAll(column1HBox8VBox1Header);
        column1HBox8VBox2.getChildren().addAll(column1HBox8VBox2MenuBar);
        column1HBox8.getChildren().addAll(column1HBox8VBox1, column1HBox8VBox2);
        
        setRDBMSMenuOnAction(menu1, menu2, menu3, menu4);
        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header, column1Hbox4, column1HBox5, column1HBox6, column1HBox7, column1HBox8);
        mainVBox1.getChildren().addAll(column1VBox1, column1VBox2);
        
        
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);
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
		ObservableList<MenuItem> sortByColumns = FXCollections.observableArrayList();
		MenuItem wholeRowOption = new MenuItem("Whole Row");
		wholeRowOption.setOnAction(event -> {
			columnMenu.setText(wholeRowOption.getText());
		});
		columns.add(wholeRowOption);
		
		for(String columnName : dbManager.getColumnNames(tableName)) {
			MenuItem columnOption = new MenuItem(columnName);
			MenuItem sortByColumnOption = new MenuItem(columnName);
			columnOption.setOnAction(event -> {
				columnMenu.setText(columnName);
			});
			columns.add(columnOption);
			sortByColumns.add(sortByColumnOption);
		}
		
		columnMenu.getItems().addAll(columns);
        menu5.getItems().addAll(sortByColumns);
	}
}
