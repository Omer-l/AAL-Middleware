package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dao.MySqlConnection;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Settings extends Window{
	
	public Settings(Window prevWindow) {
		super(prevWindow);
	}

	public Settings() {

	}
	
	

	public void open() {
		MainMenu.clearMainBox();
    	MainMenu.changeTitle("Database Settings");
        Button button1 = new Button("Back");
        button1.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9");
        
        

        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        button1.setOnAction(event -> { back(); });
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty());
        //Configurations
        VBox configVBox = new VBox(2);
        Text configHeader = new Text("Events");
        configHeader.setStyle(MainMenu.HEADER_1_STYLE);
        configVBox.getChildren().addAll(configHeader);
              
        int schedulesLastIndex = configVBox.getChildren().size() - 1;
       
        configVBox.getChildren().get(schedulesLastIndex).setOnMouseClicked(event -> {new DatabaseSettings(this).open();});

        mainVBox1.getChildren().addAll(configVBox);
    	MainMenu.mainHBox.getChildren().addAll(mainVBox1);
    }
	
	public void getEvents(String query, VBox eventsVBox) {
		ArrayList<Map<String, Object>> events = MainMenu.mainDbManager.queryDB(query, "select");
		for(Map<String, Object> readEvent : events) {
			VBox column1VBox = new VBox();
	        column1VBox.setStyle(MainMenu.MENU_BUTTON_STYLE);
	        Text column1VBoxHeader = new Text((String) readEvent.get("name"));
	        column1VBoxHeader.setStyle(MainMenu.HEADER_2_STYLE);
	        Text column1VBoxUniqueId = new Text((String) readEvent.get("unique_id"));
	        Text column1VBoxDescription = new Text((String) readEvent.get("description"));
	        column1VBox.getChildren().addAll(column1VBoxUniqueId, column1VBoxHeader, column1VBoxDescription);
	        column1VBoxUniqueId.managedProperty().bind(column1VBoxUniqueId.visibleProperty());
	        column1VBoxUniqueId.setVisible(false);
	    	MainMenu.addHoverInteraction(new VBox[] {column1VBox}, "white", "darkgray");
	        eventsVBox.getChildren().addAll(column1VBox);
		}
		
        VBox column1VBox2 = new VBox();
        column1VBox2.setStyle(MainMenu.MENU_ADD_NEW_EVENT_BUTTON_STYLE);
        Text column1VBox2Header = new Text("Add New Schedule");
        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);        
        column1VBox2.getChildren().addAll(column1VBox2Header);
        eventsVBox.getChildren().add(column1VBox2);
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

	public boolean testRdbm(String url, String username, String password, HashMap<String, ArrayList<String>> dbAndTablesMap) {
		MySqlConnection dbManager = new MySqlConnection(url, username, password);
		
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
	
    public static void main(String[] args) {
    	Settings s = new Settings();
		String dbTb = "beacon_localisation,record\nmiddleware,schedule";
		HashMap<String, ArrayList<String>> map = s.parseDbTb(dbTb);
		s.testRdbm("jdbc:mysql://localhost:3306/beacon_localisation", "root", "root", map);
		
    }

}
