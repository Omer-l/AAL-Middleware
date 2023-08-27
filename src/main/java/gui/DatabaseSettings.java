package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.DbXMLParser;
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
    private boolean mysqlExists  = false;
    private boolean postgreqlExists = false;
    
	public DatabaseSettings() {
		// TODO Auto-generated constructor stub
	}
	
	public DatabaseSettings(Window window) {
		super(window);
	}
	
	public void loadData(TextField mysqlUrl, TextField mysqlUser, TextField mysqlPw, TextField mysqldbTbField,
			TextField psqlUrl, TextField psqlUser, TextField psqlPw, TextField psqlDbTbField) {
		String[] mysqlDetails = DbXMLParser.getDBDetails("mysql.cfg.xml");
		Map<String, ArrayList<String>> mysqlDbtb = DbXMLParser.getDbAndTablesMap("mysql.cfg.xml");
		String[] psqlDetails = DbXMLParser.getDBDetails("postgresql.cfg.xml");
		Map<String, ArrayList<String>> psqlDbtb = DbXMLParser.getDbAndTablesMap("postgresql.cfg.xml");
		mysqlUrl.setText(mysqlDetails[0]);
		mysqlUser.setText(mysqlDetails[1]);
		mysqlPw.setText(mysqlDetails[2]);
		loadDbTb(mysqldbTbField, mysqlDbtb);
		psqlUrl.setText(psqlDetails[0]);
		psqlUser.setText(psqlDetails[1]);
		psqlPw.setText(psqlDetails[2]);
		loadDbTb(psqlDbTbField, psqlDbtb);
	}
	
	private void loadDbTb(TextField mysqldbTbField, Map<String, ArrayList<String>> mysqlDbtb) {
		String result = "";
		int i = 0;//db
		for(String db : mysqlDbtb.keySet()) {
			int j = 0;//tb
			ArrayList<String> values = mysqlDbtb.get(db);
			result += db;
			
			for(String value : values) {
				if(j != values.size())
					result += ",";
				result += value;
				j++;
			}

			if(i != mysqlDbtb.keySet().size() - 1)
				result += "&";
			i++;
		}
		mysqldbTbField.setText(result);
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
        column1HBox1VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
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
        column1HBox2VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
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
        column1HBox3VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
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
        column1HBox4VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox4VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox4VBox1Header = new Text("Enter Database name1,Table name1,Table name2&Database name2,"
        		+ "Table name1,Table name2");
        VBox column1HBox4VBox2 = new VBox();
        column1HBox4VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox4VBox2TextField = new TextField();
        column1HBox4VBox1.getChildren().addAll(column1HBox4VBox1Header);
        column1HBox4VBox2.getChildren().addAll(column1HBox4VBox2TextField);
        column1HBox4.getChildren().addAll(column1HBox4VBox1, column1HBox4VBox2);
        

        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3,column1HBox4);
        VBox column1VBox1P = new VBox(2);
        Text column1HeaderP = new Text("PostgreSQL");
        column1HeaderP.setStyle(MainMenu.HEADER_1_STYLE);

    
        HBox column1HBox1P = new HBox();
        VBox column1HBox1VBox1P = new VBox();
        column1HBox1VBox1P.setStyle(MainMenu.USER_INPUT_STYLE);
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
        column1HBox2VBox1P.setStyle(MainMenu.USER_INPUT_STYLE);
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
        column1HBox3VBox1P.setStyle(MainMenu.USER_INPUT_STYLE);
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
        column1HBox4VBox1P.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox4VBox1P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox4VBox1HeaderP = new Text("Enter Database name1,Table name1,Table name2&Database name2,"
        		+ "Table name1,Table name2");
        VBox column1HBox4VBox2P = new VBox();
        column1HBox4VBox2P.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox4VBox2TextFieldP = new TextField();
        column1HBox4VBox1P.getChildren().addAll(column1HBox4VBox1HeaderP);
        column1HBox4VBox2P.getChildren().addAll(column1HBox4VBox2TextFieldP);
        column1HBox4P.getChildren().addAll(column1HBox4VBox1P, column1HBox4VBox2P);
        
        
        
        
        VBox column1VBox3 = new VBox(10);
         
        ButtonBar column1ButtonBar = new ButtonBar(); 
        testButton.setOnAction(event -> {
        	
        	mysqlExists = processTestButton(column1HBox1VBox2TextField.getText(), column1HBox2VBox2TextField.getText(), column1HBox3VBox2TextField.getText(), column1HBox4VBox2TextField.getText());
        	postgreqlExists = processTestButton(column1HBox1VBox2TextFieldP.getText(), column1HBox2VBox2TextFieldP.getText(), column1HBox3VBox2TextFieldP.getText(), column1HBox4VBox2TextFieldP.getText());
        	if(mysqlExists && postgreqlExists) {
		        showAlert("Connection Successful", "Connected to MySQL and PostgreSQL database.", Alert.AlertType.CONFIRMATION);
        		saveButton.setDisable(false);
        	}else if(!mysqlExists && postgreqlExists) {
		        showAlert("Connection Failed", "Connected to PostgreSQL but unable to connect to MySQL.", Alert.AlertType.ERROR);
        		saveButton.setDisable(false);
        	}else if(mysqlExists && !postgreqlExists) {
		        showAlert("Connection Failed", "Connected to MySQL but unable to connect to PostgreSQL.", Alert.AlertType.ERROR);
        		saveButton.setDisable(false);
        	}else {
		        showAlert("Connection Failed", "Unable to connect with MySQL and PostgreSQL.", Alert.AlertType.ERROR);
        		saveButton.setDisable(true);
        	}
        	
        
        });
        saveButton.setDisable(true);

        
        saveButton.setOnAction(event -> {
        	if(mysqlExists)
        		DbXMLParser.save("mysql.cfg.xml", column1HBox1VBox2TextField.getText(), column1HBox2VBox2TextField.getText(), column1HBox3VBox2TextField.getText(), column1HBox4VBox2TextField.getText());

        	if(postgreqlExists)
        		DbXMLParser.save("postgresql.cfg.xml", column1HBox1VBox2TextFieldP.getText(), column1HBox2VBox2TextFieldP.getText(), column1HBox3VBox2TextFieldP.getText(), column1HBox4VBox2TextFieldP.getText());

        	if(mysqlExists && postgreqlExists) {
	             showAlert("Data saved", "MySQL and PostgreSQL data configurations has been saved to xml file..", Alert.AlertType.CONFIRMATION);

        	}
        	 });
        
        
        column1ButtonBar.getButtons().addAll(testButton, saveButton);

        
        column1VBox3.getChildren().addAll(column1ButtonBar);
        column1VBox1P.getChildren().addAll(column1HeaderP, column1HBox1P, column1HBox2P, column1HBox3P,column1HBox4P);
        mainVBox1.getChildren().addAll(column1VBox1,column1VBox1P,column1VBox3);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);


        loadData(column1HBox1VBox2TextField, column1HBox2VBox2TextField, column1HBox3VBox2TextField, column1HBox4VBox2TextField, column1HBox1VBox2TextFieldP, column1HBox2VBox2TextFieldP, column1HBox3VBox2TextFieldP, column1HBox4VBox2TextFieldP);
	}
	
	 private void checkExistence(String url, String username, String password) {
//	        String pairsInput = column1HBox4VBox2TextField.getText();
//	        Map<String, ArrayList<String>> pairsMap = parseDbTb(pairsInput);
//	        StringBuilder missingItems = new StringBuilder();

//	        for (Map.Entry<String, ArrayList<String>> entry : pairsMap.entrySet()) {
//	            String databaseName = entry.getKey();
//	            ArrayList<String> tableName = entry.getValue();

//	            boolean exists = testInputs(url, username, password);

//	        if (exists) {
//	            showAlert("Success","All specified database-table pairs exist.",Alert.AlertType.CONFIRMATION);
//	        } else {
//	            showAlert("Error","Some database-table pairs do not exist: n" + missingItems.toString(),Alert.AlertType.CONFIRMATION);
//	        }
	 }

	private boolean processTestButton(String url, String username, String password, String dbTb) {
		
//		 try {
			 	if(!checkFields(url,username,password) || !testInputs(url, username, password, dbTb))
			 		return false;
//				 saveButton.setDisable(false);
//			        return true;
//			 	
//		        Connection connection = DriverManager.getConnection(url, username, password);
//		        
//		        connection.close();
//		    } catch (SQLException e) {
//		        showAlert("Connection Failed", "Unable to connect to the database.\nError: " + e.getMessage(), Alert.AlertType.ERROR);
//		    }
			 	return true;
	}
	
	
	
	private boolean checkFields(String url, String username, String password) {
		if(url.isEmpty() || username.isEmpty() || password.isEmpty()) {
//	        showAlert("Error.", "Please enter the correct details.", Alert.AlertType.INFORMATION);
			return false;
		}
		return true;
	}
	
	
	
	private void showAlert(String title, String content, Alert.AlertType alertType) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setContentText(content);
	    alert.setHeaderText(null);
	    alert.showAndWait();
	}

	public static HashMap<String, ArrayList<String>> parseDbTb(String dbTb) {
        String[] rows = dbTb.split("&");
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

	public boolean testInputs(String url, String username, String password, String dbTb) {
		
		HashMap<String,ArrayList<String>> dbAndTablesMap =  parseDbTb(dbTb);
		
		
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