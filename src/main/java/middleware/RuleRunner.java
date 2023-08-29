package middleware;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dao.DbXMLParser;
import dao.MySqlConnection;
import gui.MainMenu;

public class RuleRunner extends Thread{
    public static String[] middlewareTableNames = {"database_read_event", "database_write_event", "rule", "system_file_read_event", "system_file_run_event", "system_file_write_event", "schedule"};
	public static MySqlConnection mainDbManager = new MySqlConnection();
	public ArrayList<Map<String, Object>> whens;
	public ArrayList<Map<String, Object>> thens;
	public Map<String, Object> event; // the new event to evaluate

    public RuleRunner(Map<String, Object> rule) {
		mainDbManager.setUrl("jdbc:mysql://localhost:3306/middleware");
		mainDbManager.setUsername("root");
		mainDbManager.setPassword("root");
        getWhensAndThens(rule);
    }
    
    public RuleRunner(ArrayList<Map<String, Object>> whens, ArrayList<Map<String, Object>> thens) {
		mainDbManager.setUrl("jdbc:mysql://localhost:3306/middleware");
		mainDbManager.setUsername("root");
		mainDbManager.setPassword("root");
    	this.whens = whens;
    	this.thens = thens;
    }
    
    private void getWhensAndThens(Map<String, Object> rule) {
	    String[] whenIds = ((String) rule.get("when_event_ids")).split("\\s*,\\s*");
	    String[] thenIds = ((String) rule.get("then_event_ids")).split("\\s*,\\s*");
	    if( !( (whenIds.length > 0 && whenIds[0].length() == 0) && (thenIds.length > 0 && thenIds[0].length() == 0) )) {
		    whens = getEvents(whenIds);
		    thens = getEvents(thenIds);
	    }
	}

	public static ArrayList<Map<String, Object>> getEvents(String[] ids) {
		ArrayList<Map<String, Object>> matchingEvents = new ArrayList<>();
		
		for(String id : ids) {
//			Map<String, Object> when = new HashMap<String, Object>();
			//loop through the tables
			for(String tableName : middlewareTableNames) {
				String query = "SELECT * FROM " + tableName + " WHERE id = " + id;
				ArrayList<Map<String, Object>> resultList = mainDbManager.queryDB(query, "select");
				//is there is a matching id in this table
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
	
	@Override
	public void run() { //make this a @Override and a run() function but maybe not
		//get relevant when to new event
		for(Map<String, Object> when : whens) {
			//depending on the event_type, send in for evaluation with its relevant when
			if(event.get("event_type") != null && ((String) event.get("event_type")).equals("database_read_event")
					&& ((String) when.get("event_type")).equals("database_read_event")
					&& ((String) event.get("database")).equals(((String) when.get("database")))
					&& ((String) event.get("table")).equals(((when.get("table"))))) {
				if( ( when.get("column")).equals("Whole Row") 
						|| ( event.keySet().contains((String) when.get("column")) 
								&& ((String) event.get(when.get("column"))).equals(((String) when.get("value")))))
					when.put("reached", true);
			} else if(event.get("id") != null && (int) event.get("id") == (int) when.get("id") ) {
				when.put("reached", true);
			}
		}
		
		if(areAllTrue()) {
			runThens();
			reset();
		}
	}
	
//	public static void main(String[] args) {
//	        mainDbManager = MainMenu.mainDbManager;
//			ArrayList<Map<String, Object>> rules = mainDbManager.queryDB("SELECT * from rule", "select");
//			ArrayList<RuleRunner> threads = new ArrayList<>();
//			for (Map<String, Object> rule : rules)
//				threads.add(new RuleRunner(rule));
//			Map<String, Object> testEvent = new HashMap<>();
//			testEvent.put("database", "beacon_localisation");
//			testEvent.put("table", "record");
//			testEvent.put("MAC", "C5:39:2D:D9:C1:B8");
//			testEvent.put("sortby", "dateTime");
//			testEvent.put("event_type", "database_read_event");
//			threads.get(0).event = testEvent;
//			threads.get(0).run();
//
//			testEvent.put("path", "C:/Users/ASUS/git/AAL-Middleware/lastAccessedBLE.txt");
//			testEvent.put("previous_result", 1691063796908l);
//			testEvent.put("id", "kewnfkwjen");
//			testEvent.put("event_type", "system_file_read_event");
//			testEvent.put("content", "");
//
//			threads.get(0).event = testEvent;
//			threads.get(0).run();
//	   }
	
	private void runThens() {
		for(Map<String, Object> then : thens) {
			switch((String) then.get("event_type")) {
				case "system_file_run_event": 
					try { //TODO: duplicate code with AddFileEvent.java
						mainDbManager.connectToDb("middleware");
						Map<String, Object> row = mainDbManager.queryDB("SELECT * FROM system_file_run_event WHERE id = " + (int) then.get("id"), "select").get(0);						
						String command = (String) row.get("command");
//						String command = "java --version";
			            String currentWorkingDirectory = (String) row.get("current_working_directory");
		        		ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
		        		if(currentWorkingDirectory != null && !currentWorkingDirectory.isEmpty())
		        			processBuilder.directory(new File(currentWorkingDirectory));
		        		System.out.println(command);
			            Process process = processBuilder.start();

			            // Get the output stream of the process
			            processBuilder.redirectErrorStream(true);
			            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			            // Read and print the output
			            String line;
			            while ((line = reader.readLine()) != null) {
			                System.out.println(line);
			            }

			            // Wait for the process to finish
			            int exitCode = process.waitFor();
			            System.out.println("Process exited with code: " + exitCode);
			        } catch (IOException | InterruptedException e) {
			            e.printStackTrace();
			        } break;
			}
		}
	}

	public boolean areAllTrue() {
        for (Map<String, Object> when : whens)
            if (when.get("reached") == null || ! ((boolean) when.get("reached")))
                return false;
        return true;
    }

	private void reset() {
		for(Map<String, Object> when : whens)
			when.put("reached", false);
	}
	
	public boolean containsWhenId(int id) {
		for(Map<String, Object> when : whens)
			if((int) when.get("id") == id)
				return true;
		
		return false;
	}
}