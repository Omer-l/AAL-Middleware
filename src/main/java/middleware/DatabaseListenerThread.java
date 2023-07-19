package middleware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Field;

import dao.MySqlConnection;

public class DatabaseListenerThread extends Thread {
	
//    private Object latestResult;
    private String queries;
    public static String[] referencedTableNames = {"database_read_event", "database_write_event", "rule", "system_file_read_event", "system_file_run_event", "system_file_write_event"};
	public static MySqlConnection mainDbManager = new MySqlConnection();
	public ArrayList<Map<String, Object>> whens;
	public ArrayList<Map<String, Object>> thens;
	//has new row been added
	//has the searched value been matched?

    public DatabaseListenerThread(Map<String, Object> rule) {
        getWhensAndThens(rule);
    }
    
    private void getWhensAndThens(Map<String, Object> rule) {
	    String[] whenIds = ((String) rule.get("when_event_ids")).split("\\s*,\\s*");
	    String[] thenIds = ((String) rule.get("then_event_ids")).split("\\s*,\\s*");
	    whens = getEvents(whenIds);
	    thens = getEvents(thenIds);
	}

	private ArrayList<Map<String, Object>> getEvents(String[] ids) {
		ArrayList<Map<String, Object>> matchingEvents = new ArrayList<>();
		for(String id : ids) {
//			Map<String, Object> when = new HashMap<String, Object>();
			//loop through the tables
			for(String tableName : referencedTableNames) {
				String query = "SELECT * FROM " + tableName + " WHERE unique_id = \"" + id + "\"";
				ArrayList<Map<String, Object>> resultList = mainDbManager.queryDB(query, "select");
				//is there is amatching id in this table
				if(resultList.size() > 0) {
					//add a new "event_type" element to map
					Map<String, Object> event = resultList.get(0);
					event.put("event_type", tableName);
					//add found result row to the whens/thens
					matchingEvents.add(event);
					//break since event has been found
					break;
				}
			}
		}
		return matchingEvents;
	}

	public static void main(String[] args) {
		mainDbManager.setUrl("jdbc:mysql://localhost:3306/middleware");
		mainDbManager.setUsername("root");
		mainDbManager.setPassword("root");
		ArrayList<Map<String, Object>> rules = mainDbManager.queryDB("SELECT * from rule", "select");
		ArrayList<DatabaseListenerThread> threads = new ArrayList<>();
		for (Map<String, Object> rule : rules)
			threads.add(new DatabaseListenerThread(rule));
		
		threads.get(1).run();
    }
    
	@Override
    public void run() {
//    	if(query.contains("WHERE")) { //Then it's not a whole row query
//    		
//    	} else {
//    		
//    	}
		//go through whens
		for(Map<String, Object> when : whens) {
			String eventType = (String) when.get("event_type");
			System.out.println("TYPE: " + eventType);
		}
    }
    
    public static void loadRules() {
//		ArrayList<Map<String, Object>> whenEventIds = mainDbManager.queryDB("SELECT when_event_ids from rule", "select");
//		ArrayList<Map<String, Object>> thenEventIds = mainDbManager.queryDB("SELECT then_event_ids from rule", "select");
//		ArrayList<String> whenDbReadEvents = getDbEvents("database_read_event", ((String) whenEventIds.get(0).get("when_event_ids")).split("\\s*,\\s*"));
//		ArrayList<String> thenDbReadEvents = getDbEvents("database_read_event", ((String) thenEventIds.get(0).get("when_event_ids")).split("\\s*,\\s*"));
//		ArrayList<String> whenFileWriteEvents = getDbEvents("system_file_run_event", ((String) whenEventIds.get(0).get("then_event_ids")).split("\\s*,\\s*"));
//		ArrayList<String> thenFileWriteEvents = getDbEvents("system_file_run_event", ((String) thenEventIds.get(0).get("then_event_ids")).split("\\s*,\\s*"));
		//a list of events, where are these events from?
    }
    
    private static ArrayList<String> getDbEvents(String tableName, String[] ids) {
		ArrayList<Map<String, Object>> events = new ArrayList<Map<String, Object>>();

		for(String id : ids) { //which ID is this table
			String query = "SELECT * FROM " + tableName + " WHERE unique_id = \"" + id + "\"";
			ArrayList<Map<String, Object>> resultList = mainDbManager.queryDB(query, "select");
			if(resultList.size() > 0)
				events = resultList;
		}
		
		return null;
	}

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
