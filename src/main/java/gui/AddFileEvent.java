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
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class AddFileEvent extends Window {
	private MySqlConnection dbManager = new MySqlConnection();
    private Menu menu5 = new Menu();
    private TextField valueField = new TextField();
    private TextField logField = new TextField();
    private Button testButton = new Button("Test");
    private Button saveButton = new Button("Save");
    private String query;
    
	public AddFileEvent(Window prevWindow) {
		super(prevWindow);
	}

    
	public AddFileEvent() {
		
	}

	public void open() {
		MainMenu.clearMainBox();
		MainMenu.changeTitle("Add File Event");
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
        HBox column1ButtonBar1HBox = new HBox();
        column1ButtonBar1HBox.setAlignment(Pos.CENTER);
        ButtonBar column1ButtonBar1 = new ButtonBar();
        Button runFileMethodButton = new Button("Run");
        Button readFileMethodButton = new Button("Read");
        Button writeFileMethodButton = new Button("Write");
        
        runFileMethodButton.setOnAction(event -> { 
        	MainMenu.setActive(runFileMethodButton);
        	MainMenu.deactivate(readFileMethodButton);
        	MainMenu.deactivate(writeFileMethodButton);
        });
        readFileMethodButton.setOnAction(event -> { 
        	MainMenu.setActive(readFileMethodButton); 
        	MainMenu.deactivate(runFileMethodButton);
        	MainMenu.deactivate(writeFileMethodButton);
        });
        writeFileMethodButton.setOnAction(event -> { 
        	MainMenu.setActive(writeFileMethodButton); 
        	MainMenu.deactivate(readFileMethodButton);
        	MainMenu.deactivate(runFileMethodButton);
        	});
        column1ButtonBar1.getButtons().addAll(runFileMethodButton, readFileMethodButton, writeFileMethodButton);
        column1ButtonBar1HBox.getChildren().add(column1ButtonBar1);
//        column1ButtonBar1
        
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

        
        VBox column1VBox3 = new VBox(10);
        
        ButtonBar column1ButtonBar2 = new ButtonBar();
//        testButton.setOnAction(event -> { processTestQuery()});
        saveButton.setDisable(true);
        saveButton.setOnAction(event -> {  });
        column1ButtonBar2.getButtons().addAll(testButton, saveButton);
        HBox column1HBox9 = new HBox();
        Text logText = new Text("LOG: ");
        logField.prefWidthProperty().bind(mainVBox1.widthProperty().multiply(0.9));
        logField.prefHeightProperty().bind(mainVBox1.heightProperty().multiply(0.1));
        logField.setEditable(false);
        column1HBox9.getChildren().addAll(logText, logField);
        
        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header, column1ButtonBar1HBox, column1Hbox4);
        column1VBox3.getChildren().addAll(column1ButtonBar2, column1HBox9);
        
        mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, column1VBox3);
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
        	if(!wholeRow)
        		query.append(" WHERE " + column + " = " + value);
        	
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
