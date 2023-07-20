package middleware;

import java.util.ArrayList;

import gui.MainMenu;

public class MyDatabaseTrigger extends Thread {
	private ArrayList<String> MySqlDbNames;
	private ArrayList<String> MySqlTableNames;
	private ArrayList<String> postgresqlDbNames;
	private ArrayList<String> postgresqlTableNames;
	public MyDatabaseTrigger() {
		MainMenu.mainDbManager.getDatabaseNames();
	}

	@Override
	public void run() {
		
	}
}
