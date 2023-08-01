package middleware;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dao.DbXMLParser;
import dao.MySqlConnection;
import gui.MainMenu;

public class RuleRunner extends Thread{
    public static String[] middlewareTableNames = {"database_read_event", "database_write_event", "rule", "system_file_read_event", "system_file_run_event", "system_file_write_event"};
	public static MySqlConnection mainDbManager = new MySqlConnection();
	public ArrayList<Map<String, Object>> whens;
	public ArrayList<Map<String, Object>> thens;
	public Map<String, Object> event; // the new event to evaluate

    public RuleRunner(Map<String, Object> rule) {
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
			for(String tableName : middlewareTableNames) {
				String query = "SELECT * FROM " + tableName + " WHERE unique_id = \"" + id + "\"";
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
			if(((String) event.get("event_type")).equals("database_read_event")
					&& ((String) when.get("event_type")).equals("database_read_event")
					&& ((String) event.get("database")).equals(((String) when.get("database")))
					&& ((String) event.get("table")).equals(((when.get("table")))))
				if( ((String) when.get("column")).equals("Whole Row") || event.get(when.get("column")).toString().equals(when.get("value").toString()))
					when.put("reached", true);
		}
	}
	
	private void runThens() {
		for(Map<String, Object> then : thens) {
			switch((String) then.get("event_type")) {
				case "system_file_run_event": 
					try { //TODO: duplicate code with AddFileEvent.java
						mainDbManager.connectToDb("middleware");
						Map<String, Object> row = mainDbManager.queryDB("SELECT * FROM system_file_run_event WHERE unique_id = '" + (String) then.get("unique_id") + "'", "select").get(0);
						String filePath = (String) row.get("path");
						String args = (String) row.get("arguments");
						System.out.println("java " + args);
			            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "java " + args + " " + filePath);
			            Process process = processBuilder.start();

			            // Get the output stream of the process
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

	public static boolean areAllTrue(boolean[] array) {
        for (boolean value : array) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

	public static void main(String[] args) {
        mainDbManager = MainMenu.mainDbManager;
		ArrayList<Map<String, Object>> rules = mainDbManager.queryDB("SELECT * from rule", "select");
		ArrayList<RuleRunner> threads = new ArrayList<>();
		for (Map<String, Object> rule : rules)
			threads.add(new RuleRunner(rule));
		Map<String, Object> testEvent = new HashMap<>();
		testEvent.put("database", "beacon_localisation");
		testEvent.put("unique_id", "emfwerkwlem");
		testEvent.put("query", "SELECT * FROM record ORDER BY dateTime DESC LIMIT 1");
		testEvent.put("rdbm", "MySQL");
		testEvent.put("table", "record");
		testEvent.put("column", "Whole Row");
		testEvent.put("value", "");
		testEvent.put("sortby", "dateTime");
		threads.get(0).event = testEvent;
		threads.get(0).run();
   }
}