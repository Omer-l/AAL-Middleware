package middleware;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import dao.DbXMLParser;
import dao.MySqlConnection;
import gui.MainMenu;

public class MainConsole extends Thread {
	private ArrayList<String> mySqlDbNames = new ArrayList<String> ();
	private ArrayList<String> mySqlTableNames = new ArrayList<String> ();
	private ArrayList<String> postgresqlDbNames = new ArrayList<String> ();
	private ArrayList<String> postgresqlTableNames = new ArrayList<String> ();
    ArrayList<Map<String, Object>> prevResults = new ArrayList<Map<String, Object>>();
    private MySqlConnection connection;
    private boolean listening;		
    public static ArrayList<RuleRunner> ruleThreads = new ArrayList<>();

    
	public MainConsole(MySqlConnection connection) {
        this.connection = connection;
        this.listening = true;
        mySqlDbNames.add("beacon_localisation");
        mySqlTableNames.add("record");
//        postgresqlDbNames.add("sensors");
//        postgresqlTableNames.add("incoming_events");
	}
	
	public static void main(String[] args) {
        MySqlConnection con = new MySqlConnection();
        con.setDetails(DbXMLParser.dbDetailsMySql);
        MainConsole myDatabaseTrigger = new MainConsole(con);
        myDatabaseTrigger.assignRules();
        myDatabaseTrigger.run();
        
    }

    public void stopListening() {
        this.listening = false;
    }
	 @Override
	    public void run() {
		    try {
		    	int it = 0;
	        	//initialise prevResults
	            while (listening) {

	                // Record the start time
	                long startTime = System.currentTimeMillis();
        			//go through querying each table for the latest row in the table
	                ArrayList<Map<String, Object>> latestResults = new ArrayList<Map<String, Object>>();
        	        connection.setDetails(DbXMLParser.dbDetailsMySql);
	                latestResults.addAll(latestResults(connection, mySqlDbNames, mySqlTableNames));
        	        connection.setDetails(DbXMLParser.dbDetailsPostgresql);
        	        latestResults.addAll(latestResults(connection, postgresqlDbNames, postgresqlTableNames));
//        	        System.out.println(latestResults + "\n" + prevResults);
        			//checks whether the latest result isn't actually previous Result
        	        if(it == 0) {
        	        	prevResults = latestResults;
            	        it++;
        	        }
        	        if(!latestResults.equals(prevResults)) {
        	        	System.out.println("EVENT!\n");
        	        	prevResults = latestResults;
        	        	runRules();
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

	private void assignRules() {

		RuleRunner.mainDbManager.setUrl("jdbc:mysql://localhost:3306/middleware");
		RuleRunner.mainDbManager.setUsername("root");
		RuleRunner.mainDbManager.setPassword("root");
		ArrayList<Map<String, Object>> rules = RuleRunner.mainDbManager.queryDB("SELECT * from rule", "select");
		for (Map<String, Object> rule : rules) {
			ruleThreads.add(new RuleRunner(rule));
		}
	}

	private void runRules() {
		for (RuleRunner rule : ruleThreads) {
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

	private Collection<Map<String, Object>> latestResults(MySqlConnection con, ArrayList<String> dbNames, ArrayList<String> tbNames) {
    	ArrayList<Map<String, Object>> latestResults = new ArrayList<>();
		for(String databaseName : dbNames) {
    		con.connectToDb(databaseName);
    		for(String tableName : tbNames) {
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
