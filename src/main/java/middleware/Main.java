package middleware;

import java.math.BigInteger;
import java.sql.*;
import java.util.Date;

 
import gui.Trigger;
public class Main {
	public static final boolean DEBUG = true;
//	public static DataManager dataManager = new DataManager();
	
	public static void main(String[] args) {
    	DatabaseListenerThread dbL = new DatabaseListenerThread("");
    	dbL.run();
	}
}
