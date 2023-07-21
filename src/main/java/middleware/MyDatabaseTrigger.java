package middleware;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import dao.MySqlConnection;
import gui.MainMenu;

public class MyDatabaseTrigger extends Thread {
	private ArrayList<String> mySqlDbNames = new ArrayList<String> ();
	private ArrayList<String> mySqlTableNames = new ArrayList<String> ();
	private ArrayList<String> postgresqlDbNames = new ArrayList<String> ();
	private ArrayList<String> postgresqlTableNames = new ArrayList<String> ();
    ArrayList<Map<String, Object>> prevResults = new ArrayList<Map<String, Object>>();
    private MySqlConnection connection;
    private boolean listening;
    
	public MyDatabaseTrigger(MySqlConnection connection) {
        this.connection = connection;
        this.listening = true;
//		MainMenu.mainDbManager.getDatabaseNames();
        mySqlDbNames.add("beacon_localisation");
        mySqlTableNames.add("record");
        postgresqlDbNames.add("sensors");
        postgresqlTableNames.add("incoming_events");
	}
	
	public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/beacon_localisation";
        String username = "root";
        String password = "root";
        MySqlConnection con = new MySqlConnection(url, username, password);
        MyDatabaseTrigger myDatabaseTrigger = new MyDatabaseTrigger(con);
        myDatabaseTrigger.run();
        
    }

    public void stopListening() {
        this.listening = false;
    }
	 @Override
	    public void run() {
		    prevResults.addAll(latestResults(connection, mySqlDbNames, mySqlTableNames));
	        connection.setUrl("jdbc:postgresql://localhost:5432/sensors");
	        connection.setUsername("postgres");
	        connection.setPassword("123456");
	        prevResults.addAll(latestResults(connection, postgresqlDbNames, postgresqlTableNames));
		    System.out.println(prevResults);
		    try {
	        	//initialise prevResults
	            while (listening) {
	            	for(String databaseName : mySqlDbNames) {
	            		for(String tableName : mySqlTableNames) {
	            			//go through querying each table for newResult
	            			//check whether newResult isn't actually prevResult
	            				//then run all rules
	            				//else continue
	            		}
	                // Execute query
//	                String query = "SELECT * FROM ";
	                
	                // Process the results
	                
	            	}
	                // Sleep for some time before checking again
	                Thread.sleep(1000); // Sleep for 1 second
	            	break;//delete
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	private Collection<Map<String, Object>> latestResults(MySqlConnection con, ArrayList<String> dbNames, ArrayList<String> tbNames) {
    	ArrayList<Map<String, Object>> latestResults = new ArrayList<>();
		for(String databaseName : dbNames) {
    		con.connectToDb(databaseName);
    		for(String tableName : tbNames) {
    			StringBuilder query = new StringBuilder("SELECT * FROM ");
    			query.append(tableName);
    			query.append(" ORDER BY 1 DESC LIMIT 1");
    			ArrayList<Map<String, Object>> newRow = con.queryDB(query.toString(), "select");
    			if(newRow.size() > 0) {
    				latestResults.add(newRow.get(0));
	        		System.out.println(newRow.get(0));
    			} else
    				latestResults.add(null); //placeholder
    		}
    	}
		return latestResults;
	}
}
