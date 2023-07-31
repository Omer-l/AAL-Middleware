package middleware;

import dao.MySqlConnection;
import gui.MainMenu;

public class EventSource extends Thread{
	private EventDispatcher eventDispatcher = new EventDispatcher();
    private boolean listening;		
    private MySqlConnection connection;

	public EventSource(MySqlConnection connection) {
		// TODO Auto-generated constructor stub
	}

    public void stopListening() {
        this.listening = false;
    }

	@Override
	public void run() {
    	//initialise prevResults
        while (listening) {
        	
        }
	}
	
	public static void main(String[] args) {
        EventSource eventSource = new EventSource(MainMenu.mainDbManager);
	}
}
