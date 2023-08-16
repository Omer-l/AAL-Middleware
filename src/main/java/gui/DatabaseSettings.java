package gui;

import java.util.ArrayList;
import java.util.HashMap;

import dao.MySqlConnection;

import java.awt.Label;
import java.awt.TextArea;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DatabaseSettings extends Window {
	private Button testButton = new Button("Test");
    private Button saveButton = new Button("Save");

	public DatabaseSettings() {
		// TODO Auto-generated constructor stub
	}
	
	public DatabaseSettings(Window window) {
		super(window);
	}
	
	public void open() {
		MainMenu.clearMainBox();
		MainMenu.changeTitle("Add Database Configurations");
		
	        
        Button button1 = new Button("Back");
        button1.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9");
        saveButton.setStyle("-fx-font: 15 arial ; -fx-base: #FFE4E1");
        testButton.setStyle("-fx-font: 15 arial ; -fx-base: #FFE4E1");

        button1.setOnAction(event -> { 
        	back();
        });
        
        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty());
        VBox column1VBox1 = new VBox(2);
        Text column1Header = new Text("MySQL");
        column1Header.setStyle(MainMenu.HEADER_1_STYLE);

    
        HBox column1HBox1 = new HBox();
        VBox column1HBox1VBox1 = new VBox();
        column1HBox1VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox1VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox1VBox1Header = new Text("Enter URL (with database)"); //TODO: delete this unique id field and make it so that the db auto increments this
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
        Text column1HBox2VBox1Header = new Text("Enter Username");
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
        Text column1HBox3VBox1Header = new Text("Enter Password");
        VBox column1HBox3VBox2 = new VBox();
        column1HBox3VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox3VBox2TextField = new TextField();
        column1HBox3VBox1.getChildren().addAll(column1HBox3VBox1Header);
        column1HBox3VBox2.getChildren().addAll(column1HBox3VBox2TextField);
        column1HBox3.getChildren().addAll(column1HBox3VBox1, column1HBox3VBox2);
                
        HBox column1HBox4 = new HBox();
        VBox column1HBox4VBox1 = new VBox();
        column1HBox4VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox4VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox4VBox1Header = new Text("Enter Database Name");
        VBox column1HBox4VBox2 = new VBox();
        column1HBox4VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox4VBox2TextField = new TextField();
        column1HBox4VBox1.getChildren().addAll(column1HBox4VBox1Header);
        column1HBox4VBox2.getChildren().addAll(column1HBox4VBox2TextField);
        column1HBox4.getChildren().addAll(column1HBox4VBox1, column1HBox4VBox2);
        
        HBox column1HBox5 = new HBox();
        VBox column1HBox5VBox1 = new VBox();
        column1HBox5VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox5VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox5VBox1Header = new Text("Enter Table Name");
        VBox column1HBox5VBox2 = new VBox();
        column1HBox5VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox5VBox2TextField = new TextField();
        column1HBox5VBox1.getChildren().addAll(column1HBox5VBox1Header);
        column1HBox5VBox2.getChildren().addAll(column1HBox5VBox2TextField);
        column1HBox5.getChildren().addAll(column1HBox5VBox1, column1HBox5VBox2);
       
        
        

        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3,column1HBox4,column1HBox5);
        VBox column1VBox1P = new VBox(2);
        Text column1HeaderP = new Text("PostgreSQL");
        column1HeaderP.setStyle(MainMenu.HEADER_1_STYLE);

    
        HBox column1HBox1P = new HBox();
        VBox column1HBox1VBox1P = new VBox();
        column1HBox1VBox1P.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox1VBox1P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox1VBox1HeaderP = new Text("Enter URL (with database)"); //TODO: delete this unique id field and make it so that the db auto increments this
        VBox column1HBox1VBox2P = new VBox();
        column1HBox1VBox2P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox1VBox2TextFieldP = new TextField();
        column1HBox1VBox1P.getChildren().addAll(column1HBox1VBox1HeaderP);
        column1HBox1VBox2P.getChildren().addAll(column1HBox1VBox2TextFieldP);
        column1HBox1P.getChildren().addAll(column1HBox1VBox1P, column1HBox1VBox2P);

        HBox column1HBox2P = new HBox();
        VBox column1HBox2VBox1P = new VBox();
        column1HBox2VBox1P.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox2VBox1P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox2VBox1HeaderP = new Text("Enter Username");
        VBox column1HBox2VBox2P = new VBox();
        column1HBox2VBox2P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox2VBox2TextFieldP = new TextField();
        column1HBox2VBox1P.getChildren().addAll(column1HBox2VBox1HeaderP);
        column1HBox2VBox2P.getChildren().addAll(column1HBox2VBox2TextFieldP);
        column1HBox2P.getChildren().addAll(column1HBox2VBox1P, column1HBox2VBox2P);

        HBox column1HBox3P = new HBox();
        VBox column1HBox3VBox1P = new VBox();
        column1HBox3VBox1P.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox3VBox1P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox3VBox1HeaderP = new Text("Enter Password");
        VBox column1HBox3VBox2P = new VBox();
        column1HBox3VBox2P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox3VBox2TextFieldP = new TextField();
        column1HBox3VBox1P.getChildren().addAll(column1HBox3VBox1HeaderP);
        column1HBox3VBox2P.getChildren().addAll(column1HBox3VBox2TextFieldP);
        column1HBox3P.getChildren().addAll(column1HBox3VBox1P, column1HBox3VBox2P);
        
        HBox column1HBox4P = new HBox();
        VBox column1HBox4VBox1P = new VBox();
        column1HBox4VBox1P.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox4VBox1P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox4VBox1HeaderP = new Text("Enter Database Name");
        VBox column1HBox4VBox2P = new VBox();
        column1HBox4VBox2P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox4VBox2TextFieldP = new TextField();
        column1HBox4VBox1P.getChildren().addAll(column1HBox4VBox1HeaderP);
        column1HBox4VBox2P.getChildren().addAll(column1HBox4VBox2TextFieldP);
        column1HBox4P.getChildren().addAll(column1HBox4VBox1P, column1HBox4VBox2P);
        
        HBox column1HBox5P = new HBox();
        VBox column1HBox5VBox1P = new VBox();
        column1HBox5VBox1P.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox5VBox1P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox5VBox1HeaderP = new Text("Enter Schema Name");
        VBox column1HBox5VBox2P = new VBox();
        column1HBox5VBox2P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox5VBox2TextFieldP = new TextField();
        column1HBox5VBox1P.getChildren().addAll(column1HBox5VBox1HeaderP);
        column1HBox5VBox2P.getChildren().addAll(column1HBox5VBox2TextFieldP);
        column1HBox5P.getChildren().addAll(column1HBox5VBox1P, column1HBox5VBox2P);
        
        HBox column1HBox6P = new HBox();
        VBox column1HBox6VBox1P = new VBox();
        column1HBox6VBox1P.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox6VBox1P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox6VBox1HeaderP = new Text("Enter Table Name");
        VBox column1HBox6VBox2P = new VBox();
        column1HBox6VBox2P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox6VBox2TextFieldP = new TextField();
        column1HBox6VBox1P.getChildren().addAll(column1HBox6VBox1HeaderP);
        column1HBox6VBox2P.getChildren().addAll(column1HBox6VBox2TextFieldP);
        column1HBox6P.getChildren().addAll(column1HBox6VBox1P, column1HBox6VBox2P);
        
        
        VBox column1VBox3 = new VBox(10);
        
        ButtonBar column1ButtonBar = new ButtonBar(); 

        
        testButton.setOnAction(event -> {
        	
        	boolean sql = processTestButton(column1HBox1VBox2TextField.getText(),column1HBox2VBox2TextField.getText(),
        			column1HBox3VBox2TextField.getText());
        	
        	
        	boolean postgresql = processTestButton(column1HBox1VBox2TextFieldP.getText(),column1HBox2VBox2TextFieldP.getText(),
        			column1HBox3VBox2TextFieldP.getText());
        	
        	if(sql && postgresql) {
		        showAlert("Connection Successful", "Database connection is successful!", Alert.AlertType.INFORMATION);

        	}else if(sql && !postgresql) {
		        showAlert("Connection Unsuccessful", "PostgreSQL does not found!", Alert.AlertType.INFORMATION);

        	}else if(!sql && postgresql) {
		        showAlert("Connection Unsuccessful", "MySQL connection is successful!", Alert.AlertType.INFORMATION);

        	}else {
		        showAlert("Connection Unsuccessful", "Unable to connect to MySQL and PostgreSQL!", Alert.AlertType.INFORMATION);

        	}
        	
        	
        
        });
        saveButton.setDisable(true);

        
        saveButton.setOnAction(event -> {
        	boolean existsMysql = checkDatabaseExistence(column1HBox1VBox2TextField.getText(),column1HBox2VBox2TextField.getText(),
        			column1HBox3VBox2TextField.getText(), column1HBox4VBox2TextField.getText(), column1HBox5VBox2TextField.getText());
        	
        	boolean existsPostgres =  checkExistence(column1HBox1VBox2TextFieldP.getText(),column1HBox2VBox2TextFieldP.getText(),
        	column1HBox3VBox2TextFieldP.getText(), column1HBox4VBox2TextFieldP.getText(), column1HBox5VBox2TextFieldP.getText(),column1HBox6VBox2TextFieldP.getText());
        	
        	
        	if(existsMysql && existsPostgres) {
        		showAlert("Success", "MySQL and PostgreSQL database and table exists.", Alert.AlertType.INFORMATION);
        	}else if(existsMysql && !existsPostgres){ 
        		showAlert("Error", "MySQL database and table exists but PostgreSQL database and table does not exists.", Alert.AlertType.INFORMATION);
        	}else if(!existsMysql && existsPostgres) {
        		showAlert("Error", "MySQL database and table does not exists but PostgreSQL database and table d exists.", Alert.AlertType.INFORMATION);

        	}else {
        		showAlert("Error", "MySQL database and PostgreSQL database and table does not exists.", Alert.AlertType.INFORMATION);

        	}
        	
        
        	 });
        
        
        column1ButtonBar.getButtons().addAll(testButton, saveButton);

        
        column1VBox3.getChildren().addAll(column1ButtonBar);
        column1VBox1P.getChildren().addAll(column1HeaderP, column1HBox1P, column1HBox2P, column1HBox3P,column1HBox4P,column1HBox5P,column1HBox6P);
        mainVBox1.getChildren().addAll(column1VBox1,column1VBox1P,column1VBox3);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);


        
	}
	
	private boolean checkDatabaseExistence(String url, String username, String password,
			String databaseName, String tableName) {
        try {

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            // Check if the database exists
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES");
            while (resultSet.next()) {
                String dbName = resultSet.getString(1);
                if (dbName.equals(databaseName)) {
                    // Database exists, now check if the table exists
                    resultSet = statement.executeQuery("SHOW TABLES IN " + databaseName);
                    while (resultSet.next()) {
                        String tblName = resultSet.getString(1);
                        if (tblName.equals(tableName)) {
                            // Table exists
                            connection.close();
                            return true;
                        }
                    }
                }
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
	
	private boolean checkExistence(String url, String username, String password, String dbName, String schemaName, String tableName) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            ResultSet dbResultSet = statement.executeQuery("SELECT datname FROM pg_database WHERE datname='" + dbName + "'");
            if (dbResultSet.next()) {
                ResultSet schemaResultSet = statement.executeQuery("SELECT schema_name FROM information_schema.schemata WHERE catalog_name='" + dbName + "' AND schema_name='" + schemaName + "'");
                if (schemaResultSet.next()) {
                    ResultSet tableResultSet = statement.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_catalog='" + dbName + "' AND table_schema='" + schemaName + "' AND table_name='" + tableName + "'");
                    if (tableResultSet.next()) {
                        connection.close();
                        return true;
                    }
                }
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
	
	private boolean  processTestButton(String url, String username, String password) {
		
		 try {
			 	checkFields(url,username,password);
		        Connection connection = DriverManager.getConnection(url, username, password);

		        connection.close();
		    } catch (SQLException e) {
		        showAlert("Connection Failed", "Unable to connect to the database.\nError: " + e.getMessage(), Alert.AlertType.ERROR);
		    }
		 saveButton.setDisable(false);
	        return true;

	}
	
	
	
	private void checkFields(String url, String username, String password) {
		if(url.isEmpty() || username.isEmpty() || password.isEmpty()) {
	        showAlert("Error.", "Please enter the correct details.", Alert.AlertType.INFORMATION);
		}
	}
	
	
	
	private void showAlert(String title, String content, Alert.AlertType alertType) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setContentText(content);
	    alert.setHeaderText(null);
	    alert.showAndWait();
	}

	public static HashMap<String, ArrayList<String>> parseDbTb(String dbTb) {
        String[] rows = dbTb.split("\n");
        int rowCount = rows.length;
        int colCount = 100;
        
        HashMap<String, ArrayList<String>> array =  new HashMap<>(rowCount,colCount);		        
        for (int i = 0; i < rowCount; i++) {
            String[] cols = rows[i].split(",");
            ArrayList<String> values = new ArrayList<>();
            
            for(int j = 1; j < cols.length; j++) {
            	String col = cols[j];
            	values.add(col);
            }
            
            array.put(cols[0], values);
        }

        return array;
}

	public boolean testRdbm(String url, String username, String password) {
		
		HashMap<String,ArrayList<String>> dbAndTablesMap = new HashMap();
		
		dao.MySqlConnection dbManager = new dao.MySqlConnection(url, username, password);

				ArrayList<String> databases = dbManager.getDatabaseNames();

				if(!databases.containsAll(dbAndTablesMap.keySet()))
					return false;
				for(String db : dbAndTablesMap.keySet()) {
					ArrayList<String> tables = dbAndTablesMap.get(db);
					ArrayList<String> allTables = dbManager.getTableNames(db);
					if(!allTables.containsAll(tables))
						return false;
				}
				return true;
	}
	
}