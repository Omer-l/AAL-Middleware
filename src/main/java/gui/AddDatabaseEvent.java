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

public class AddDatabaseEvent extends Window {
	private MySqlConnection dbManager = new MySqlConnection();
    private Menu menu5 = new Menu();
    private TextField valueField = new TextField();
    private TextField logField = new TextField();
    private Button testButton = new Button("Test");
    private Button saveButton = new Button("Save");
    private String query;
	Map<String, Object> editData = new HashMap<String, Object>();
	Map<String, Object> removeData = new HashMap<String, Object>();

	public void loadData(String uniqueId) {
		editData = MainMenu.mainDbManager.queryDB("SELECT * FROM database_read_event JOIN event ON database_read_event.unique_id = event.unique_id", "select").get(0);
	}
	
	public void loadData(String uniqueId, TextField uniqueIdField, TextField nameField, TextField descriptionField,
			Menu rdbmMenu, Menu databaseMenu, Menu tableMenu, Menu columnMenu, TextField valueField, Menu sortByMenu) {
		editData = MainMenu.mainDbManager.queryDB("SELECT * FROM database_read_event JOIN event ON database_read_event.unique_id = event.unique_id", "select").get(0);
		System.out.println(editData);
		if(((String) editData.get("rdbm")).equals("MySQL"))
			dbManager.setDetails(DbXMLParser.dbDetailsMySql);
		else if(((String) editData.get("rdbm")).equals("PostgreSQL"))
			dbManager.setDetails(DbXMLParser.dbDetailsPostgresql);
		uniqueIdField.setText(uniqueId);
		nameField.setText((String) editData.get("name"));
		descriptionField.setText((String) editData.get("description"));
		rdbmMenu.setText((String) editData.get("rdbm"));
		databaseMenu.setText((String) editData.get("database"));
		tableMenu.setText((String) editData.get("table"));
		loadTablesMenu(tableMenu, columnMenu, (String) editData.get("database"));
//		if(!((String) editData.get("column").equals("Whole Row"))
		columnMenu.setText((String) editData.get("column"));
		loadColumnsMenu(tableMenu, columnMenu, (String) editData.get("table"));
		valueField.setText((String) editData.get("value"));
		sortByMenu.setText((String) editData.get("sortby"));
		//		this.editData = data;
	}
	

	public static void removeEventFromDatabaseReadEvent(String uniqueID) {
	  MainMenu.mainDbManager.queryDB("DELETE FROM database_read_event WHERE database_read_event.unique_id = '" + uniqueID + "'","");
	}
 
    
	public AddDatabaseEvent(Window prevWindow) {
		super(prevWindow);
	}

    
	public AddDatabaseEvent() {
		
	}

	public void open() {
		MainMenu.clearMainBox();
		MainMenu.changeTitle("Add Database Event");
		
	        
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
        column1HBox7VBox2.getChildren().addAll(valueField);
        valueField.setDisable(true);
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
        column1HBox8VBox2MenuBar.getMenus().add(menu5);
//        Text menuBar3Label = new Text("Table");
//    	HBox menuBar3HBox = new HBox(10);
//    	menuBar3HBox.getChildren().addAll(menuBar3Label, column1HBox8VBox2MenuBar);
        column1HBox8VBox1.getChildren().addAll(column1HBox8VBox1Header);
        column1HBox8VBox2.getChildren().addAll(column1HBox8VBox2MenuBar);
        column1HBox8.getChildren().addAll(column1HBox8VBox1, column1HBox8VBox2);
        
        VBox column1VBox3 = new VBox(10);
        
        ButtonBar column1ButtonBar = new ButtonBar();
        testButton.setOnAction(event -> { processTestQuery(menu1, menu2, menu3, menu4, menu5, valueField); });
        saveButton.setDisable(true);
        saveButton.setOnAction(event -> {
        	String uniqueIdInput = column1HBox1VBox2TextField.getText();
        	String nameInput = column1HBox2VBox2TextField.getText();
        	String descriptionInput = column1HBox3VBox2TextField.getText();    	
        	String rdbm = menu1.getText();
        	String db = menu2.getText();
        	String table = menu3.getText();
        	String column = menu4.getText();
        	String value = valueField.getText();
        	String sortBy = menu5.getText();
        	boolean emptyField = uniqueIdInput.isEmpty() || nameInput.isEmpty() || descriptionInput.isEmpty();
        	if(!emptyField) {
        		if(editData.isEmpty()) {
		        	MainMenu.mainDbManager.queryDB("INSERT INTO event VALUES ('" + uniqueIdInput + "', '" + nameInput + "', '" + descriptionInput + "');", "");
		        	MainMenu.mainDbManager.queryDB("INSERT INTO database_read_event VALUES ('" + uniqueIdInput + "', '" + rdbm + "', '" + db + "', '" + table + "', '" + column + "', '" + sortBy + "', '" + value + "', \"" + this.query + "\");", ""); 
        		} else {
		        	MainMenu.mainDbManager.queryDB("UPDATE ", "");
		        	MainMenu.mainDbManager.queryDB("UPDATE ", "");
		        	}
        		
        		back();
        	} else {
        		logField.setText("unique id, name or description field is empty");
        	}
        });
        column1ButtonBar.getButtons().addAll(testButton, saveButton);
        HBox column1HBox9 = new HBox();
        myStyles.createLogField(logField, mainVBox1, column1HBox9);
        
        
        setRDBMSMenuOnAction(menu1, menu2, menu3, menu4);
        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header, column1Hbox4, column1HBox5, column1HBox6, column1HBox7, column1HBox8);
        column1VBox3.getChildren().addAll(column1ButtonBar, column1HBox9);
        
        mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, column1VBox3);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);
        
        if(!editData.isEmpty()) {
        	loadData((String) editData.get("unique_id"), column1HBox1VBox2TextField, column1HBox2VBox2TextField, column1HBox3VBox2TextField, menu1, menu2, menu3, menu4, valueField, menu5);
        }
	}

	private void setRDBMSMenuOnAction(Menu rdbmMenu, Menu databaseMenu, Menu tableMenu, Menu columnMenu) {
	    Menu[] menusToReset = {databaseMenu, tableMenu, columnMenu};
		rdbmMenu.getItems().get(0).setOnAction(event -> {
	        reset(menusToReset);
			loadDatabasesMenu(rdbmMenu, databaseMenu, "mysql.cfg.xml");
			rdbmMenu.setText(rdbmMenu.getItems().get(0).getText());
	        setDatabaseMenuOnAction(databaseMenu, tableMenu, columnMenu);
		
		});
		rdbmMenu.getItems().get(1).setOnAction(event -> {
	        reset(menusToReset);
			loadDatabasesMenu(rdbmMenu, databaseMenu, "postgresql.cfg.xml");
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

	private void loadDatabasesMenu(Menu menu, Menu databaseMenu, String configFileName) {
		ObservableList<MenuItem> databases = FXCollections.observableArrayList();
		String[] dbDetails = configFileName.contains("mysql") ? DbXMLParser.dbDetailsMySql : DbXMLParser.dbDetailsPostgresql;
		dbManager.setDetails(dbDetails);
		databaseMenu.getItems().clear();
		for(String dbName : dbManager.getDatabaseNames())
			databases.add(new MenuItem(dbName));
		
		databaseMenu.getItems().addAll(databases);
	}

	private void loadTablesMenu(Menu tableMenu, Menu columnMenu, String databaseName) {
	    Menu[] menusToReset = {columnMenu, menu5};
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
			valueField.setDisable(true);
		});
		columns.add(wholeRowOption);
		
		for(String columnName : dbManager.getColumnNames(tableName)) {
			MenuItem columnOption = new MenuItem(columnName);
			MenuItem sortByColumnOption = new MenuItem(columnName);
			columnOption.setOnAction(event -> {
				columnMenu.setText(columnName);
				valueField.setDisable(false);
			});
			sortByColumnOption.setOnAction(event -> {
				menu5.setText(columnName);
			});
			columns.add(columnOption);
			sortByColumns.add(sortByColumnOption);
		}
		
		columnMenu.getItems().addAll(columns);
        menu5.getItems().addAll(sortByColumns);
	}
	
	private void processTestQuery(Menu rdbmMenu, Menu databaseMenu, Menu tableMenu, Menu columnMenu, Menu sortByMenu, TextField valueField) {
    	String rdbm = rdbmMenu.getText();
    	String db = databaseMenu.getText();
    	String table = tableMenu.getText();
    	String column = columnMenu.getText();
    	String value = valueField.getText();
    	String sortBy = menu5.getText();
    	boolean wholeRow = column.equals("Whole Row");
    	boolean noBlankFields = !(rdbm.isEmpty() || db.isEmpty() || table.isEmpty() || column.isEmpty() || (value.isEmpty() && !wholeRow));
    	if(noBlankFields) {
        	System.out.println(rdbm + ", " + db + ", " + table + ", " + column + ", " + value);
        	StringBuilder query = new StringBuilder("SELECT * FROM " + table);
        	if(!wholeRow) {
        		query.append(" WHERE " + column + " = ");
        		if(value.matches("\\d+"))
        			query.append(value);
        		else
        			query.append("'" + value + "'");
        	}
        	
        	query.append(" ORDER BY " + sortBy + " DESC LIMIT 1");
        	System.out.println(query);
        	String result = dbManager.queryDB(query.toString(), "select").toString();
        	logField.setText(result);
        	if(!result.contains("ERROR")) {
        		saveButton.setDisable(false);
        		this.query = query.toString();
        	} else
        		System.out.println("Blank");
    	}
	}
}
