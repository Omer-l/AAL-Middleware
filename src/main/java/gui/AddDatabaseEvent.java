package gui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.DbXMLParser;
import dao.MySqlConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
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
    private int id;
    private String operation = "read";
	private VBox dbWriteVBox = new VBox();
	Map<String, Object> editData = new HashMap<String, Object>();
	Map<String, Object> removeData = new HashMap<String, Object>();

	public void loadData(int id, String operation) {
		this.id = id;
		this.operation = operation;
		editData.put("id", id);
	}
	
	public void loadData( TextField nameField, TextField descriptionField,
			Menu rdbmMenu, Menu databaseMenu, Menu tableMenu, Menu columnMenu, TextField valueField, Menu sortByMenu) {
		if(operation == "read")	
			editData = MainMenu.mainDbManager.queryDB("SELECT * FROM database_read_event JOIN event ON database_read_event.id = event.id", "select").get(0);
		else
			editData = MainMenu.mainDbManager.queryDB("SELECT * FROM database_write_event JOIN event ON database_write_event.id = event.id", "select").get(0);
		if(((String) editData.get("rdbm")).equals("MySQL"))
			dbManager.setDetails(DbXMLParser.dbDetailsMySql);
		else if(((String) editData.get("rdbm")).equals("PostgreSQL"))
			dbManager.setDetails(DbXMLParser.dbDetailsPostgresql);
		nameField.setText((String) editData.get("name"));
		descriptionField.setText((String) editData.get("description"));
		rdbmMenu.setText((String) editData.get("rdbm"));
		databaseMenu.setText((String) editData.get("database"));
		tableMenu.setText((String) editData.get("table"));
		loadTablesMenu(tableMenu, columnMenu, (String) editData.get("database"));
		if(operation == "read") {
			columnMenu.setText((String) editData.get("column"));
			loadColumnsMenu(tableMenu, columnMenu, (String) editData.get("table"));
			valueField.setText((String) editData.get("value"));
			sortByMenu.setText((String) editData.get("sortby"));
		} else {
			loadInputFields((String) editData.get("table"));
			String[] values = parseInsertValues((String) editData.get("query"));
			int i = 0;
			for(Node node : dbWriteVBox.getChildren()) {
				String value = values[i];
		        if (value.startsWith("'") && value.endsWith("'"))
		        	value = value.substring(1, value.length() - 1);
		        
				((TextField)((VBox) node).getChildren().get(1)).setText(value);
				i++;
			}
		}
		//		this.editData = data;
	}
	

	public static void removeEventFromDatabaseReadEvent(int uniqueID) {
	  MainMenu.mainDbManager.queryDB("DELETE FROM database_read_event WHERE database_read_event.id = " + uniqueID,"");
	}
 
    
	public AddDatabaseEvent(Window prevWindow) {
		super(prevWindow);
	}

    
	public AddDatabaseEvent() {
		
	}

	public AddDatabaseEvent(Window prevWindow, String operation) {
		super(prevWindow);
		this.operation = operation;
	}

	public void open() {
		MainMenu.clearMainBox();
		MainMenu.changeTitle("Add Database Event");
		
		HBox mainHbox = new HBox(10);
		mainHbox.setAlignment(Pos.CENTER_RIGHT);
		mainHbox.setStyle(MainMenu.HBOX_STYLE);
		
	        
        Button button1 = new Button("Back");
        button1.setStyle(MainMenu.BACK_BUTTON_STYLE);
        saveButton.setStyle(MainMenu.SAVE_BUTTON_STYLE);
        testButton.setStyle(MainMenu.TEST_BUTTON_STYLE);

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

        VBox column1HBox2 = new VBox();
        VBox column1HBox2VBox1 = new VBox();

        column1HBox2VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        Text column1HBox2VBox1Header = new Text("Name");
        VBox column1HBox2VBox2 = new VBox();

        TextField column1HBox2VBox2TextField = new TextField();
        column1HBox2VBox2TextField.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));
        column1HBox2VBox1.getChildren().addAll(column1HBox2VBox1Header);
        column1HBox2VBox2.getChildren().addAll(column1HBox2VBox2TextField);
        column1HBox2.getChildren().addAll(column1HBox2VBox1, column1HBox2VBox2);

        VBox column1HBox3 = new VBox();
        VBox column1HBox3VBox1 = new VBox();
        column1HBox3VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
//        column1HBox3VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox3VBox1Header = new Text("Description");
        VBox column1HBox3VBox2 = new VBox();
//        column1HBox3VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox3VBox2TextField = new TextField();
        column1HBox3VBox2TextField.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));

        column1HBox3VBox1.getChildren().addAll(column1HBox3VBox1Header);
        column1HBox3VBox2.getChildren().addAll(column1HBox3VBox2TextField);
        column1HBox3.getChildren().addAll(column1HBox3VBox1, column1HBox3VBox2);
//
//        //column1urations
        VBox column1VBox2 = new VBox(2);
        Text column1VBox2Header = new Text("Method");
        column1VBox2Header.setStyle(MainMenu.HEADER_1_STYLE);
        VBox column1Hbox4 = new VBox();
//        
        VBox column1Hbox4VBox1 = new VBox();
        column1Hbox4VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
//        column1Hbox4VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1Hbox4VBox1Header = new Text("RDBM ");
        VBox column1Hbox4VBox2 = new VBox();
//        column1Hbox4VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(7));
//        
//        
//        // Create a MenuItem
        MenuBar column1HBox4VBox2MenuBar = new MenuBar();
        
        column1HBox4VBox2MenuBar.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));
        Menu menu1 =  new Menu();
        // Create MenuItems for the dropdown menu
       //here we need to add databases
        menu1.setText("Choose RDBM");
        MenuItem option1 = new MenuItem("MySQL");
        MenuItem option2 = new MenuItem("PostgreSQL");
       
        menu1.getItems().addAll(option1,option2);
        column1HBox4VBox2MenuBar.getMenus().add(menu1);
        column1Hbox4VBox1.getChildren().addAll(column1Hbox4VBox1Header);
        column1Hbox4VBox2.getChildren().addAll(column1HBox4VBox2MenuBar);
        column1Hbox4.getChildren().addAll(column1Hbox4VBox1, column1Hbox4VBox2);
        
        
        
        
//
        VBox column1HBox5 = new VBox();
        VBox column1HBox5VBox1 = new VBox();
        column1HBox5VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
//        column1HBox5VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox5VBox1Header = new Text("Database");
        VBox column1HBox5VBox2 = new VBox();
//        column1HBox5VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        MenuBar column1HBox5VBox2MenuBar = new MenuBar();
        column1HBox5VBox2MenuBar.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));

        Menu menu2 = new Menu();
        // Create MenuItems for the dropdown menu
        column1HBox5VBox2MenuBar.getMenus().add(menu2);
        
        Text menuBar2Label = new Text("Table");
    	HBox menuBar2HBox = new HBox(10);
    	menuBar2HBox.getChildren().addAll(menuBar2Label, column1HBox5VBox2MenuBar);
        column1HBox5VBox1.getChildren().addAll(column1HBox5VBox1Header);
        column1HBox5VBox2.getChildren().addAll(column1HBox5VBox2MenuBar);
        column1HBox5.getChildren().addAll(column1HBox5VBox1, column1HBox5VBox2);
//
        VBox column1HBox6 = new VBox();
        VBox column1HBox6VBox1 = new VBox();
        column1HBox6VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox6VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox6VBox1Header = new Text("Table");
        VBox column1HBox6VBox2 = new VBox();
        column1HBox6VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        MenuBar column1HBox6VBox2MenuBar = new MenuBar();
        column1HBox6VBox2MenuBar.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));

        Menu menu3 = new Menu();
//        menu3.setText("Choose Table");

        // Create MenuItems for the dropdown menu
        column1HBox6VBox2MenuBar.getMenus().add(menu3);
        Text menuBar3Label = new Text("Table");
    	HBox menuBar3HBox = new HBox(10);
    	menuBar3HBox.getChildren().addAll(menuBar3Label, column1HBox6VBox2MenuBar);
        column1HBox6VBox1.getChildren().addAll(column1HBox6VBox1Header);
        column1HBox6VBox2.getChildren().addAll(column1HBox6VBox2MenuBar);
        column1HBox6.getChildren().addAll(column1HBox6VBox1, column1HBox6VBox2);
//
        VBox column1HBox7 = new VBox();
        VBox column1HBox7VBox1 = new VBox();
        column1HBox7VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox7VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox7VBox1Header = new Text("Column");
        VBox column1HBox7VBox2 = new VBox();
        column1HBox7VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(4));
        MenuBar column1HBox7VBox2MenuBar = new MenuBar();
        column1HBox7VBox2MenuBar.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));
        Menu menu4 = new Menu();
//        menu4.setText("Choose Column");

        // Create MenuItems for the dropdown menu
        column1HBox7VBox2MenuBar.getMenus().add(menu4);
        column1HBox7VBox1.getChildren().addAll(column1HBox7VBox1Header);
        column1HBox7VBox2.getChildren().addAll(valueField);
        valueField.setDisable(true);
        column1HBox7.getChildren().addAll(column1HBox7VBox1, column1HBox7VBox2MenuBar, column1HBox7VBox2);
//        
//
        VBox column1HBox8 = new VBox();
        VBox column1HBox8VBox1 = new VBox();
        column1HBox8VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox8VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox8VBox1Header = new Text("Sort By");
//        menu5.setText("Sort by");

        VBox column1HBox8VBox2 = new VBox();
        column1HBox8VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        MenuBar column1HBox8VBox2MenuBar = new MenuBar();
        column1HBox8VBox2MenuBar.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));

        // Create MenuItems for the dropdown menu
        column1HBox8VBox2MenuBar.getMenus().add(menu5);
        Text menuBar3Label1 = new Text("Table");
    	HBox menuBar3HBox1 = new HBox(10);
    	menuBar3HBox.getChildren().addAll(menuBar3Label1, column1HBox8VBox2MenuBar);
        column1HBox8VBox1.getChildren().addAll(column1HBox8VBox1Header);
        column1HBox8VBox2.getChildren().addAll(column1HBox8VBox2MenuBar);
        column1HBox8.getChildren().addAll(column1HBox8VBox1, column1HBox8VBox2);
        valueField.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));
        VBox column1VBox3 = new VBox(10);
         
        ButtonBar column1ButtonBar = new ButtonBar(); 
        testButton.setOnAction(event -> { processTestQuery(menu1, menu2, menu3, menu4, menu5, valueField); });
        saveButton.setDisable(true);
        saveButton.setOnAction(event -> { processSaveButton(column1HBox2VBox2TextField.getText(), column1HBox3VBox2TextField.getText(), menu1.getText(), menu2.getText(), menu3.getText(), menu4.getText(), valueField.getText(), menu5.getText());});
        column1ButtonBar.getButtons().addAll(testButton, saveButton);
            
        HBox column1HBox9 = new HBox();
        MyStyles.createLogField(logField, mainVBox1, column1HBox9);
//        
//        
        setRDBMSMenuOnAction(menu1, menu2, menu3, menu4);
        column1VBox1.getChildren().addAll(column1Header, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header, column1Hbox4, column1HBox5, column1HBox6);
        if(operation == "read") {
        	column1VBox2.getChildren().addAll(column1HBox7, column1HBox8);
        } else { //write
        	column1VBox2.getChildren().add(dbWriteVBox);
        }
        column1VBox3.getChildren().addAll(column1HBox9,column1ButtonBar);
        
        
        mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, column1VBox3);
        
        mainHbox.getChildren().add(mainVBox1);
        MainMenu.mainHBox.getChildren().addAll(mainHbox);
        mainVBox1.setAlignment(Pos.TOP_CENTER);
      
        
        if(!editData.isEmpty()) {
        	loadData(column1HBox2VBox2TextField, column1HBox3VBox2TextField, menu1, menu2, menu3, menu4, valueField, menu5);
        }
	}

	private void setRDBMSMenuOnAction(Menu rdbmMenu, Menu databaseMenu, Menu tableMenu, Menu columnMenu) {
	    Menu[] menusToReset = {databaseMenu, tableMenu, columnMenu, menu5};
		rdbmMenu.getItems().get(0).setOnAction(event -> {
	        reset(menusToReset);
			loadDatabasesMenu(rdbmMenu, databaseMenu, "mysql.cfg.xml");
			rdbmMenu.setText(rdbmMenu.getItems().get(0).getText());
	        setDatabaseMenuOnAction(databaseMenu, tableMenu, columnMenu);
	        databaseMenu.setText("Choose database");
		
		});
		rdbmMenu.getItems().get(1).setOnAction(event -> {
	        reset(menusToReset);
			loadDatabasesMenu(rdbmMenu, databaseMenu, "postgresql.cfg.xml");
			rdbmMenu.setText(rdbmMenu.getItems().get(1).getText());
	        setDatabaseMenuOnAction(databaseMenu, tableMenu, columnMenu);
	        databaseMenu.setText("Choose database");
			});
	}

	private void reset(Menu[] menus) {
		for(Menu menu : menus) {
			menu.setText("");
			menu.getItems().clear();
		}
		if(operation == "write")
			dbWriteVBox.getChildren().clear();
	}

	private void setDatabaseMenuOnAction(Menu dbMenu, Menu tableMenu, Menu columnMenu) {
	    Menu[] menusToReset = {tableMenu, columnMenu, menu5};
		for(MenuItem menuItem : dbMenu.getItems()) {
			menuItem.setOnAction(event -> {
	    		reset(menusToReset);
				loadTablesMenu(tableMenu, columnMenu, menuItem.getText());
				System.out.println(menuItem.getText());
	    		menuItem.getParentMenu().setText(menuItem.getText());
	    		tableMenu.setText("Choose table");
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
				if(operation == "read") {
					loadColumnsMenu(tableMenu, columnMenu, tableName);
					columnMenu.setText("Choose column");
				} else {
					loadInputFields(tableName);
				}
			});
			tables.add(tableOption);
		}
		
		tableMenu.getItems().addAll(tables);
	}

	private void loadInputFields(String tableName) {
		String[] columnNames = dbManager.getColumnNames(tableName);
		dbWriteVBox.getChildren().clear();
		int i = 0;
		for(String columnName : columnNames) {
	        VBox column1HBox6 = new VBox();
	        Text menuBar3Label = new Text(columnName);
	        TextField tf = new TextField();
//			if(columnName.toLowerCase().contains("id") && i == 0) {
//				menuBar3Label.setVisible(false);
//				tf.setVisible(false);
//				tf.setText(tableName);
//			}
	        column1HBox6.getChildren().addAll(menuBar3Label, tf);
	        dbWriteVBox.getChildren().add(column1HBox6);
	        i++;
		}
	}

	private void loadColumnsMenu(Menu tableMenu, Menu columnMenu, String tableName) {
		columnMenu.getItems().clear();
		ObservableList<MenuItem> columns = FXCollections.observableArrayList();
		ObservableList<MenuItem> sortByColumns = FXCollections.observableArrayList();
		MenuItem wholeRowOption = new MenuItem("Whole Row");
		wholeRowOption.setOnAction(event -> {
			columnMenu.setText(wholeRowOption.getText());
			valueField.setDisable(true);
			menu5.setText("Choose sort by column");
		});
		columns.add(wholeRowOption);
		
		for(String columnName : dbManager.getColumnNames(tableName)) {
			MenuItem columnOption = new MenuItem(columnName);
			MenuItem sortByColumnOption = new MenuItem(columnName);
			columnOption.setOnAction(event -> {
				columnMenu.setText(columnName);
				valueField.setDisable(false);
				menu5.setText("Choose sort by column");
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
	
	private String getValue(String value) {
		if(value.matches("\\d+") || value.toLowerCase().equals("null")) 
			return (value);
		else
			return "'" + value + "'";
	}

	private ArrayList<String> getInputValues() {
		ArrayList<String> values = new ArrayList<>();
		ObservableList<Node> children = dbWriteVBox.getChildren();
		for (Node node : children) {
			String columnName = (String) ((Text) ((VBox) node).getChildren().get(0)).getText();
			String value = (String) ((TextField) ((VBox) node).getChildren().get(1)).getText();
			values.add(getValue(value));
		}
		return values;
	}
	
	public static String[] parseInsertValues(String insertQuery) {
        Pattern pattern = Pattern.compile("\\bnull\\b|\\bNULL\\b|\\d+|'[^']*'");
        Matcher matcher = pattern.matcher(insertQuery);

        StringBuilder valuesBuilder = new StringBuilder();

        while (matcher.find()) {
            String value = matcher.group(0);
            valuesBuilder.append(value).append(",");
        }

        String valuesString = valuesBuilder.toString();
        if (valuesString.endsWith(",")) {
            valuesString = valuesString.substring(0, valuesString.length() - 1);
        }

        return valuesString.split(",");
    }
	
	private void processTestQuery(Menu rdbmMenu, Menu databaseMenu, Menu tableMenu, Menu columnMenu, Menu sortByMenu, TextField valueField) {
		String rdbm = rdbmMenu.getText();
		String db = databaseMenu.getText();
		String table = tableMenu.getText();
		String column = columnMenu.getText();
		if(operation == "read") {
	    	String value = valueField.getText();
	    	String sortBy = menu5.getText();
	    	boolean wholeRow = column.equals("Whole Row");
	    	boolean noBlankFields = !(rdbm.isEmpty() || db.isEmpty() || table.isEmpty() || column.isEmpty() || (value.isEmpty() && !wholeRow));
	    	if(noBlankFields) {
	        	System.out.println(rdbm + ", " + db + ", " + table + ", " + column + ", " + value);
	        	StringBuilder query = new StringBuilder("SELECT * FROM " + table);
	        	if(!wholeRow) {
	        		query.append(" WHERE " + column + " = ");
	        		
	        			query.append(getValue(value));
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
		} else {
	    	ArrayList<String> value = getInputValues();
	    	String sortBy = menu5.getText();
	    	boolean wholeRow = column.equals("Whole Row");
	    	boolean noBlankFields = !(rdbm.isEmpty() || db.isEmpty() || table.isEmpty());
	    	if(noBlankFields) {
	    		ArrayList<String> inputValues = getInputValues();
	    		this.query = "INSERT INTO " + table + " VALUES (";
	    		for(int i = 0; i < inputValues.size(); i++) {
	    			String input = inputValues.get(i);
	    			if(i == inputValues.size() - 1)
	    				this.query += input + ")";
	    			else
	    				this.query += input + ", ";
	    		}
	    		
	        	String result = dbManager.queryDB(query.toString(), "").toString();
	        	if(result.contains("ERROR")) {
	        		saveButton.setDisable(true);
	        	} else {
	        		saveButton.setDisable(false);
		        	System.out.println(result);
	        	}
	    	}
		}
	}

	private void processSaveButton(String nameInput, String descriptionInput, String rdbm, String db, String table, String column, String value, String sortBy) {
		boolean emptyField = nameInput.isEmpty() || descriptionInput.isEmpty();
		
		if(!emptyField) {
			if(operation == "read") {
				if(editData.isEmpty()) {
		        	MainMenu.mainDbManager.queryDB("INSERT INTO event VALUES (null, '" + nameInput + "', '" + descriptionInput + "');", "");
		        	id = (int) MainMenu.mainDbManager.queryDB("SELECT * FROM event ORDER BY id  DESC LIMIT 1;", "select").get(0).get("id");
		        	MainMenu.mainDbManager.queryDB("INSERT INTO database_read_event VALUES ("+ id + ", '" + rdbm + "', '" + db + "', '" + table + "', '" + column + "', '" + sortBy + "', '" + value + "', \"" + this.query + "\");", ""); 
				} else {
		        	MainMenu.mainDbManager.queryDB("UPDATE event SET"
		        			+ " name = '" + nameInput + "', "
		        			+ "description = '" + descriptionInput + "' WHERE id = " + id + ";", "");
		        	MainMenu.mainDbManager.queryDB("UPDATE database_read_event"
		        			+ " SET "
		        			+ "rdbm = '" + rdbm + "', "
		        			+ "`database` = '" + db + "', `table` = '" + table + "',"
		        			+ "`column` = '" + column + "', `value` = '" + value
		        			+ "', sortby = '" + sortBy
		        			+ "', query = \"" + this.query + "\" WHERE id = " + id + ";", "");
		        }
			} else {
				if(editData.isEmpty()) {
		        	MainMenu.mainDbManager.queryDB("INSERT INTO event VALUES (null, '" + nameInput + "', '" + descriptionInput + "');", "");
		        	id = (int) MainMenu.mainDbManager.queryDB("SELECT * FROM event ORDER BY id DESC LIMIT 1;", "select").get(0).get("id");
		        	MainMenu.mainDbManager.queryDB("INSERT INTO database_write_event VALUES ("+ id + ", '" + rdbm + "', '" + db + "', '" + table + "', \"" + this.query + "\");", ""); 
				} else {
		        	MainMenu.mainDbManager.queryDB("UPDATE event SET"
		        			+ " name = '" + nameInput + "', "
		        			+ "description = '" + descriptionInput + "' WHERE id = " + id + ";", "");
		        	MainMenu.mainDbManager.queryDB("UPDATE database_write_event"
		        			+ " SET "
		        			+ "rdbm = '" + rdbm + "', "
		        			+ "`database` = '" + db + "', `table` = '" + table + "',"
		        			+ " query = \"" + this.query + "\" WHERE id = " + id + ";", "");
		        }
			}
			back();
		} else {
			logField.setText("unique id, name or description field is empty");
		}
	}
}
