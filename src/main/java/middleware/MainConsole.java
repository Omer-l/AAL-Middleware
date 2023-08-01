package middleware;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dao.DbXMLParser;
import dao.MySqlConnection;
import gui.MainMenu;

public class MainConsole {
    ArrayList<Map<String, Object>> prevResultsDB = new ArrayList<Map<String, Object>>();
    private MySqlConnection connection;
    private boolean listening;		
    public static ArrayList<RuleRunner> ruleThreads = new ArrayList<>();

    
	public MainConsole(MySqlConnection connection) {
        this.connection = connection;
        this.listening = true;
	}
	
	public static void main(String[] args) {
        MySqlConnection con = new MySqlConnection();
        con.setDetails(DbXMLParser.dbDetailsMySql);
        MainConsole mainConsole = new MainConsole(con);
        mainConsole.listen();
    }

    public void stopListening() {
        this.listening = false;
    }

	public void listen() {
		assignRules();
	    try {
	    	int it = 0;
        	//initialise prevResults
            while (listening) {
                // Record the start time
                long startTime = System.currentTimeMillis();
    			//go through querying each table for the latest row in the table
                ArrayList<Map<String, Object>> latestResultsDB = new ArrayList<Map<String, Object>>();
    	        connection.setDetails(DbXMLParser.dbDetailsMySql);
                latestResultsDB.addAll(latestResults(connection, DbXMLParser.mySqlDbAndTablesMap));
    	        connection.setDetails(DbXMLParser.dbDetailsPostgresql);
    	        latestResultsDB.addAll(latestResults(connection, DbXMLParser.postgresqlDbAndTablesMap));
//        	    System.out.println(latestResults + "\n" + prevResults);
    			//checks whether the latest result isn't actually previous Result
    	        if(it == 0) { //only for first iteration
    	        	prevResultsDB = latestResultsDB;
        	        it++;
    	        }
    	        if(!latestResultsDB.equals(prevResultsDB)) {
    	        	System.out.println("EVENT!\n");
    	        	Map<String, Object> event = getNewEvent(prevResultsDB, latestResultsDB);
    	        	prevResultsDB = latestResultsDB;
    	        	event.put("event_type", "database_write_event");
    	        	runRules(event);
    	        } else {
    	        	System.out.println("no event detected!");
    	        }
    	        // Record the end time
    	        long endTime = System.currentTimeMillis();

    	        // Calculate the elapsed time in milliseconds
    	        long elapsedTime = endTime - startTime;
    	        // Print the result and the execution time
                Thread.sleep(250); // Sleep for 1 second
                //efficiency test
//        	        System.out.println("Elapsed Time (milliseconds): " + elapsedTime);
//	                memoryUsage();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

	private Map<String, Object> getNewEvent(ArrayList<Map<String, Object>> prevResults, ArrayList<Map<String, Object>> latestResults) {
		Map<String, Object> event = new HashMap<String, Object>();
		Set<Map<String, Object>> set1 = new HashSet<>(prevResults);
        Set<Map<String, Object>> set2 = new HashSet<>(latestResults);

        // Find elements in new results that are not in previous results
        set2.removeAll(set1);
		return new ArrayList<>(set2).get(0);
	}

	private void assignRules() {
		RuleRunner.mainDbManager.setUrl("jdbc:mysql://localhost:3306/middleware");
		RuleRunner.mainDbManager.setUsername("root");
		RuleRunner.mainDbManager.setPassword("root");
		ArrayList<Map<String, Object>> rules = RuleRunner.mainDbManager.queryDB("SELECT * from rule", "select");
		for (Map<String, Object> rule : rules)
			ruleThreads.add(new RuleRunner(rule));
	}

	private void runRules(Map<String, Object> event) {
		for (RuleRunner rule : ruleThreads) {
			rule.event = event;
			rule.start();
		}
	}

	private void memoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        System.out.println("Heap Memory Usage:");
        printMemoryUsage(heapMemoryUsage);

        System.out.println("Non-Heap Memory Usage:");
        printMemoryUsage(nonHeapMemoryUsage);
		
	}

	private void printMemoryUsage(MemoryUsage memoryUsage) {
        System.out.println("    Init: " + memoryUsage.getInit() / (1024 * 1024) + " MB");
        System.out.println("    Used: " + memoryUsage.getUsed() / (1024 * 1024) + " MB");
        System.out.println("    Committed: " + memoryUsage.getCommitted() / (1024 * 1024) + " MB");
        System.out.println("    Max: " + memoryUsage.getMax() / (1024 * 1024) + " MB");		
	}

	private Collection<Map<String, Object>> latestResults(MySqlConnection con, Map<String, ArrayList<String>> dbAndTablesMap) {
    	ArrayList<Map<String, Object>> latestResults = new ArrayList<>();
		for(String databaseName : dbAndTablesMap.keySet()) {
    		con.connectToDb(databaseName);
    		for(String tableName : dbAndTablesMap.get(databaseName)) {
    			StringBuilder query = new StringBuilder("SELECT * FROM ");
    			query.append(tableName);
    			query.append(" ORDER BY 1 DESC LIMIT 1");
    			ArrayList<Map<String, Object>> newRow = con.queryDB(query.toString(), "select");
    			if(newRow.size() > 0) 
    				latestResults.add(newRow.get(0));
    			else
    				latestResults.add(null); //placeholder
    		}
    	}
		return latestResults;
	}
}
