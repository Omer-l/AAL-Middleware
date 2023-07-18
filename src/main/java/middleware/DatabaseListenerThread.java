package middleware;

import java.util.ArrayList;
import java.util.Map;
import java.lang.reflect.Field;

import dao.MySqlConnection;

public class DatabaseListenerThread extends Thread {
	
//    private Object latestResult;
    private String queries;
    public static String[] referencedTableNames = {"database_read_event", "database_write_event", "rule", "system_file_read_event", "system_file_run_event", "system_file_write_event"};
	public static MySqlConnection mainDbManager = new MySqlConnection();
	//has new row been added
	//has the searched value been matched?

    public DatabaseListenerThread() {
//        getQueries();
    }
    
    @Override
    public void run() {
//    	if(query.contains("WHERE")) { //Then it's not a whole row query
//    		
//    	} else {
//    		
//    	}
    }
    
    public static void loadRules() {
		ArrayList<Map<String, Object>> whenEventIds = mainDbManager.queryDB("SELECT when_event_ids from rule", "select");
		ArrayList<Map<String, Object>> thenEventIds = mainDbManager.queryDB("SELECT then_event_ids from rule", "select");
		ArrayList<String> whenDbReadEvents = getDbEvents("database_read_event", ((String) whenEventIds.get(0).get("when_event_ids")).split("\\s*,\\s*"));
//		ArrayList<String> thenDbReadEvents = getDbEvents("database_read_event", ((String) thenEventIds.get(0).get("when_event_ids")).split("\\s*,\\s*"));
//		ArrayList<String> whenFileWriteEvents = getDbEvents("system_file_run_event", ((String) whenEventIds.get(0).get("then_event_ids")).split("\\s*,\\s*"));
//		ArrayList<String> thenFileWriteEvents = getDbEvents("system_file_run_event", ((String) thenEventIds.get(0).get("then_event_ids")).split("\\s*,\\s*"));
		//a list of events, where are these events from?
    }
    
private static ArrayList<String> getDbEvents(String string, String[] ids) {
		ArrayList<String> events = new ArrayList<String>();

		for(String id : ids) {
			for(String tableName : referencedTableNames) {
				ArrayList<Map<String, Object>> resultList = mainDbManager.queryDB("SELECT * FROM ", "select");
			}
		}
		
		return null;
	}

//    public void ArrayList<Map>
    
    public static void main(String[] args) {
    	mainDbManager.setUrl("jdbc:mysql://localhost:3306/middleware");
    	mainDbManager.setUsername("root");
    	mainDbManager.setPassword("root");
    	
    }
    	//get the rows

//    @Override
//    public void run() {
//    	MySqlConnection mySqlConnection = new MySqlConnection("jdbc:mysql://localhost:3306/beacon_localisation", "root", "root");
//    	try {
//            // Keep the main thread running to continue listening
//            while (true) {
//            	ArrayList<Map<String, Object>> mostRecentResult = mySqlConnection.queryDB("SELECT * FROM record ORDER BY dateTime DESC LIMIT 1", "select");
//            	Object obj = mostRecentResult.get(0).values().toArray()[0];
//            	System.out.println("HERE: " + obj);
//            	Class<?> objClass = obj.getClass();
//                Field[] fields = objClass.getDeclaredFields();
//
//                for (Field field : fields) {
//                    field.setAccessible(true); // Make the private field accessible
//
//                    try {
//                        Object attributeValue = field.get(obj);
//                        String attributeName = field.getName();
//
//                        System.out.println("NAME: " + attributeName + ": VALE: " + attributeValue);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            	
////            	if(mostRecentResult == null)
////            		continue;
////            	if(Main.DEBUG)
////            		System.out.println("LATEST ROW MATCHES PREV ROW: " + mostRecentResult.equals(latestResult) + " : \n" + latestResult + "\n" + mostRecentResult);
////
////            	if(latestResult != null && !mostRecentResult.equals(latestResult)) {
////            		latestResult = mostRecentResult;
////            		Action.performActions(latestResult);
////            	} else {
////            		latestResult = mostRecentResult;
////            	}
//    	        Thread.sleep(1000);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
