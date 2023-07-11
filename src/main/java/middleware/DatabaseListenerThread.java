package middleware;

import java.util.ArrayList;
import java.util.Map;
import java.lang.reflect.Field;

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
            	ArrayList<Map<String, Object>> mostRecentResult = mySqlConnection.queryDB("SELECT * FROM record ORDER BY dateTime DESC LIMIT 1", "select");
            	Object obj = mostRecentResult.get(0).values().toArray()[0];
            	System.out.println("HERE: " + obj);
            	Class<?> objClass = obj.getClass();
                Field[] fields = objClass.getDeclaredFields();

                for (Field field : fields) {
                    field.setAccessible(true); // Make the private field accessible

                    try {
                        Object attributeValue = field.get(obj);
                        String attributeName = field.getName();

                        System.out.println("NAME: " + attributeName + ": VALE: " + attributeValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            	
//            	if(mostRecentResult == null)
//            		continue;
//            	if(Main.DEBUG)
//            		System.out.println("LATEST ROW MATCHES PREV ROW: " + mostRecentResult.equals(latestResult) + " : \n" + latestResult + "\n" + mostRecentResult);
//
//            	if(latestResult != null && !mostRecentResult.equals(latestResult)) {
//            		latestResult = mostRecentResult;
//            		Action.performActions(latestResult);
//            	} else {
//            		latestResult = mostRecentResult;
//            	}
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
