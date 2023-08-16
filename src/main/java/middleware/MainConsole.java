package middleware;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.time.*;
import java.util.Timer;
import java.util.TimerTask;

import dao.DbXMLParser;
import dao.MySqlConnection;
import gui.MainMenu;

public class MainConsole {
    ArrayList<Map<String, Object>> prevResults = new ArrayList<Map<String, Object>>();
    ArrayList<Map<String, Object>> completedSchedules = new ArrayList<Map<String,Object>>();
    private int prevResultsIndex = 0;
    private MySqlConnection connection;
    private boolean listening;		
    public static ArrayList<RuleRunner> ruleThreads = new ArrayList<>();
    private static long iteration;
    
	public MainConsole(MySqlConnection connection) {
        this.connection = connection;
        this.listening = true;
	}
	
	public static void main(String[] args) {
        MySqlConnection con = new MySqlConnection();
        con.setDetails(DbXMLParser.dbDetailsMySql);
        MainConsole mainConsole = new MainConsole(con);
		RuleRunner.mainDbManager.setUrl("jdbc:mysql://localhost:3306/middleware");
		RuleRunner.mainDbManager.setUsername("root");
		RuleRunner.mainDbManager.setPassword("root");
        mainConsole.initialiseSchedules();
        mainConsole.listen();
}

    public void stopListening() {
        this.listening = false;
    }

	public void listen() {
		assignRules();
	    try {
        	//initialise prevResults
            while (listening) {
                // Record the start time
                long startTime = System.currentTimeMillis();
    			//go through querying each table for the latest row in the table
                ArrayList<Map<String, Object>> latestResults = new ArrayList<Map<String, Object>>();
    	        connection.setDetails(DbXMLParser.dbDetailsMySql);
                latestResults.addAll(latestResultsDB(DbXMLParser.mySqlDbAndTablesMap));
    	        connection.setDetails(DbXMLParser.dbDetailsPostgresql);
    	        latestResults.addAll(latestResultsDB(DbXMLParser.postgresqlDbAndTablesMap));
    	        latestResults.addAll(latestResultsFiles());
    	        latestResults.addAll(completedSchedules);
//        	    System.out.println(latestResults + "\n" + prevResults);
    	        if(iteration == 0) //only for first iteration
    	        	prevResults = latestResults;
    	        if(!latestResults.equals(prevResults)) {
    	        	System.out.println("EVENT!\n");
    	        	Map<String, Object> event = getNewEvent(prevResults, latestResults);
    	        	if(event != null) {
	    	        	prevResults = latestResults;
	    	        	runRules(event);
    	        	}
    	        } else {
//    	        	System.out.println("no event detected!");
    	        }
    	        // Record the end time
    	        long endTime = System.currentTimeMillis();

    	        // Calculate the elapsed time in milliseconds
    	        long elapsedTime = endTime - startTime;
    	        // Print the result and the execution time
                Thread.sleep(50); // Sleep for 1 second
                prevResultsIndex = 0;
                iteration++;
                //efficiency test
//    	        System.out.println("Elapsed Time (milliseconds): " + elapsedTime);
//	                memoryUsage();
                completedSchedules.clear();
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
        if(set2.size() > 0)
        	return new ArrayList<>(set2).get(0);
        else
        	return null;
	}

	private void assignRules() {
		ArrayList<Map<String, Object>> rules = RuleRunner.mainDbManager.queryDB("SELECT * from rule", "select");
		for (Map<String, Object> rule : rules)
			ruleThreads.add(new RuleRunner(rule));
	}

	private void runRules(Map<String, Object> event) {
		for (RuleRunner rule : ruleThreads) {
			//clone first to avoid IllegalThreadStateException
			RuleRunner ruleThread = new RuleRunner(rule.whens, rule.thens); //carry of the whens and thens to the clone
			ruleThread.event = event;
			ruleThread.start();
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

	private Collection<Map<String, Object>> latestResultsDB(Map<String, ArrayList<String>> dbAndTablesMap) {
		ArrayList<Map<String, Object>> latestResults = new ArrayList<>();
		for(String databaseName : dbAndTablesMap.keySet()) {
    		connection.connectToDb(databaseName);
    		for(String tableName : dbAndTablesMap.get(databaseName)) {
    			StringBuilder query = new StringBuilder("SELECT * FROM ");
    			query.append(tableName);
    			query.append(" ORDER BY 1 DESC LIMIT 1");
    			ArrayList<Map<String, Object>> result = connection.queryDB(query.toString(), "select");
    			if(result.size() > 0) {
    				Map<String, Object> newRow = result.get(0);
    				newRow.put("event_type", "database_read_event");
    				newRow.put("database", databaseName);
    				newRow.put("table", tableName);
    				latestResults.add(newRow);
    				prevResultsIndex++;
    			} else
    				latestResults.add(null); //placeholder
    		}
    	}
		return latestResults;
	}

	private ArrayList<Map<String, Object>> latestResultsFiles() {
		ArrayList<Map<String, Object>> latestResults = new ArrayList<>();
		ArrayList<Map<String, Object>> results = RuleRunner.mainDbManager.queryDB("SELECT * FROM system_file_read_event ORDER BY 1 DESC", "select");
		for(Map<String, Object> result : results) {
            try {
            	File file = new File((String) result.get("path"));
	            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
	            byte[] buffer = new byte[8192]; // Adjust buffer size as needed
	            int bytesRead;
				while ((bytesRead = bis.read(buffer)) != -1) {
//					Map<String, Object> fileResult = new HashMap<String, Object>();
				    // Convert the bytes read to a string and print the result
				    String data = new String(buffer, 0, bytesRead);
				    long lastModified = file.lastModified();
//				    !(((String) result.get("content")).isEmpty()) && 
				    if(data.contains((String) result.get("content")) || prevResults.size() < prevResultsIndex)
				    	result.put("previous_result", lastModified);
				    else
				    	result.put("previous_result", (long) prevResults.get(prevResultsIndex).get("previous_result"));

    				result.put("event_type", "system_file_read_event");
				}
				latestResults.add(result);
			    prevResultsIndex++;
			    bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return latestResults;
	}
	
	private ArrayList<Map<String, Object>> initialiseSchedules() {
		ArrayList<Map<String, Object>> latestResults = new ArrayList<>();
		ArrayList<Map<String, Object>> results = RuleRunner.mainDbManager.queryDB("SELECT * FROM schedule ORDER BY 1 DESC", "select");
//		for(Map<String, Object> result : results) {
//            LocalDateTime now = LocalDateTime.now();
//            //do some calculations to solve startDateTime by using the  result.get("start_date_time") and result.get("repetition") attribute
//            LocalDateTime startDateTime = (LocalDateTime) result.get("start_date_time");
//			LocalDateTime endDateTime = (LocalDateTime) result.get("end_date_time");
//        	result.put("event_type", "schedule");
//        	//also check result.get("updated_at") in case code is rerun and event ends up running twice in the repetition period
//            if ( iteration != 0 && (now.isEqual(startDateTime) || now.isAfter(startDateTime)) && now.isBefore(endDateTime))
//            	result.put("achieved", true);
//            else
//            	result.put("unique_id", null); //nullify the result, in case previously it was set to true
//        	latestResults.add(result);
//        	prevResultsIndex++;
////	        System.out.println(result);
//		}
		for(Map<String, Object> result : results) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime startDateTime = (LocalDateTime) result.get("start_date_time");
			LocalDateTime endDateTime = (LocalDateTime) result.get("end_date_time");
			if(now.isAfter(endDateTime)) continue; //schedule won't need to be considered
			ScheduleInterval scheduleInterval = ScheduleInterval.valueOf(((String) result.get("repetition")).toUpperCase());
			Schedule schedule = new Schedule(startDateTime.getDayOfWeek(), startDateTime.toLocalTime(), scheduleInterval);
	        // Calculate the initial delay until the next schedule time
	        Duration initialDelay = calculateInitialDelay(schedule);
	        Timer timer = new Timer();
	        // Schedule the task to run at the specified interval
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                LocalDateTime now = LocalDateTime.now();
	                LocalDateTime nextSchedule = schedule.getNextScheduleTime(now);

	                // Check if the schedule is met
//	                if(scheduleInterval.equals(ScheduleInterval.MINUTE))
	                Map<String, Object> scheduleEvent = new HashMap<String, Object>();
	                scheduleEvent.put("unique_id", schedule.uniqueId);
	                scheduleEvent.put("start_date_time", schedule.dateTime);
//	                    System.out.println("Schedule met at: " + now);
		           completedSchedules.add(result);
	            }
	        }, initialDelay.toMillis(), schedule.getInterval().duration.toMillis());
	    }

		return null;
	}
	
	public static boolean isScheduleMet(LocalDateTime dateTime, Schedule schedule) {
        return schedule.isScheduleTime(dateTime);
    }
	public static boolean isScheduleMet(LocalDateTime dateTime, LocalDateTime schedule) {
        return dateTime.isAfter(schedule) || dateTime.isEqual(schedule);
    }
	
	public static Duration calculateInitialDelay(Schedule schedule) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextScheduleTime = schedule.getNextScheduleTime(now);

        return Duration.between(now, nextScheduleTime);
    }
	
	static class Schedule {
        private final DayOfWeek dayOfWeek;
        private final LocalTime time;
        private final ScheduleInterval interval;
        private String uniqueId;
        private LocalDateTime dateTime;

        public Schedule(DayOfWeek dayOfWeek, LocalTime time, ScheduleInterval interval) {
            this.dayOfWeek = dayOfWeek;
            this.time = time;
            this.interval = interval;
        }

        public LocalDateTime getNextScheduleTime(LocalDateTime baseTime) {
            LocalDateTime nextSchedule = baseTime
                    .with(TemporalAdjusters.nextOrSame(dayOfWeek))
                    .with(time);

            while (nextSchedule.isBefore(baseTime)) {
                nextSchedule = nextSchedule.plus(interval.toDuration());
            }

            return nextSchedule;
        }

        public boolean isScheduleTime(LocalDateTime dateTime) {
            return dayOfWeek == dateTime.getDayOfWeek() && time.equals(dateTime.toLocalTime());
        }

        public ScheduleInterval getInterval() {
            return interval;
        }
    }
	
    enum ScheduleInterval {
    	SECOND(Duration.ofSeconds(1)),
        MINUTE(Duration.ofMinutes(1)),
        HOUR(Duration.ofHours(1)),
        DAY(Duration.ofDays(1)),
        WEEK(Duration.ofDays(7)),
        MONTH(Duration.ofDays(30));

        private final Duration duration;

        ScheduleInterval(Duration duration) {
            this.duration = duration;
        }

        public Duration toDuration() {
            return duration;
        }
    }
}

