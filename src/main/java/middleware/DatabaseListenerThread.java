package middleware;

public class DatabaseListenerThread extends Thread {
	
    private Object latestResult;
    private String query;

    public DatabaseListenerThread(String query) {
        this.query = query;
    }
    
    @Override
    public void run() {
    	try {
            // Keep the main thread running to continue listening
            while (true) {
            	Object mostRecentResult = Main.dataManager.getLatestRow("Record", "MySQL");
            	if(mostRecentResult == null)
            		continue;
            	
            	if(latestResult != null && mostRecentResult.equals(latestResult)) {
            		latestResult = mostRecentResult;
            		Action.performActions(latestResult);
            	} else {
            		latestResult = mostRecentResult;
            	}
            	if(Main.DEBUG)
            		System.out.println("LATEST ROW MATCHES PREV ROW: " + mostRecentResult.equals(latestResult) + " : " + mostRecentResult);
    	        Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
