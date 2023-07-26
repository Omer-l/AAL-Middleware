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
    
	public MainConsole(MySqlConnection connection) {
        this.connection = connection;
        this.listening = true;
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
        MainConsole myDatabaseTrigger = new MainConsole(con);
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
        			//go through querying each table for the latest row in the table
	                ArrayList<Map<String, Object>> latestResults = new ArrayList<Map<String, Object>>();
        	        connection.setDetails(DbXMLParser.dbDetailsMySql);
	                latestResults.addAll(latestResults(connection, mySqlDbNames, mySqlTableNames));
        	        connection.setDetails(DbXMLParser.dbDetailsPostgresql);
        	        latestResults.addAll(latestResults(connection, postgresqlDbNames, postgresqlTableNames));
        	        System.out.println(latestResults + "\n" + prevResults);
        			//checks whether the latest result isn't actually previous Result
        	        if(it == 0) {
        	        	prevResults = latestResults;
            	        it++;
        	        }
        	        if(!latestResults.equals(prevResults)) {
        	        	System.out.println("NOT EQS!\n");
        	        	prevResults = latestResults;
        	        	runRules();
        	        } else {
        	        	System.out.println("EQS!\n");
        	        }
	                Thread.sleep(1000); // Sleep for 1 second
	                //efficiency test
	                memoryUsage();
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	private void runRules() {

		RuleRunner.mainDbManager.setUrl("jdbc:mysql://localhost:3306/middleware");
		RuleRunner.mainDbManager.setUsername("root");
		RuleRunner.mainDbManager.setPassword("root");
		ArrayList<Map<String, Object>> rules = RuleRunner.mainDbManager.queryDB("SELECT * from rule", "select");
		ArrayList<RuleRunner> threads = new ArrayList<>();
		for (Map<String, Object> rule : rules)
			threads.add(new RuleRunner(rule));
		
		threads.get(0).update();
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
