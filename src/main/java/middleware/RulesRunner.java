package middleware;

import java.util.ArrayList;
import java.util.Map;

import dao.MySqlConnection;

public class RulesRunner {
    public static String[] referencedTableNames = {"database_read_event", "database_write_event", "rule", "system_file_read_event", "system_file_run_event", "system_file_write_event"};
	public static MySqlConnection mainDbManager = new MySqlConnection();
	public ArrayList<Map<String, Object>> whens;
	public ArrayList<Map<String, Object>> thens;

    public RulesRunner(Map<String, Object> rule) {
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
	
	public void update() { //make this a @Override and a run() function but maybe not
		//go through conditions
		//boolean array to see if all WHENs are true
		//loop through whens
		for(Map<String, Object> when : whens) {
			switch((String) when.get("event_type")) {
				case "database_read_event": 
					//then go to that database table and read the latest row
					
					; break;
				case "read_file_event" : ; break; //TODO: IMPLEMENT THIS
			}
		}
		//if all when booleans are true
			//then run thens
	}

	public static void main(String[] args) {
		mainDbManager.setUrl("jdbc:mysql://localhost:3306/middleware");
		mainDbManager.setUsername("root");
		mainDbManager.setPassword("root");
		ArrayList<Map<String, Object>> rules = mainDbManager.queryDB("SELECT * from rule", "select");
		ArrayList<RulesRunner> threads = new ArrayList<>();
		for (Map<String, Object> rule : rules)
			threads.add(new RulesRunner(rule));
		
		threads.get(0).update();
    }
}
