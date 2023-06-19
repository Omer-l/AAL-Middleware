package middleware;

import java.util.ArrayList;
import java.util.Map;

import dao.MySqlConnection;

public class DatabaseListenerThread extends Thread {
	
    private Object latestResult;
    private String query;

    public DatabaseListenerThread(String query) {
        this.query = query;
    }
    
    @Override
    public void run() {
    	MySqlConnection mySqlConnection = new MySqlConnection("jdbc:mysql://localhost:3306/beacon_localisation", "root", "root");
    	try {
            // Keep the main thread running to continue listening
            while (true) {
            	ArrayList<Map<String, Object>> mostRecentResult = mySqlConnection.queryDB("SELECT * FROM record");
            	if(mostRecentResult == null)
            		continue;
            	System.out.println(mostRecentResult);
//            	if(latestResult != null && mostRecentResult.equals(latestResult)) {
//            		latestResult = mostRecentResult;
//            		Action.performActions(latestResult);
//            	} else {
//            		latestResult = mostRecentResult;
//            	}
//            	if(Main.DEBUG)
//            		System.out.println("LATEST ROW MATCHES PREV ROW: " + mostRecentResult.equals(latestResult) + " : " + mostRecentResult);
    	        Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    	DatabaseListenerThread dbL = new DatabaseListenerThread("");
    	dbL.run();
    }
}
