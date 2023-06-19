package middleware;

import java.math.BigInteger;
import java.sql.*;
import java.util.Date;

 
import gui.Trigger;
public class Main {
	public static final boolean DEBUG = true;
//	public static DataManager dataManager = new DataManager();
	
	public static void main(String[] args) {
//		Record record = new Record();
//		User gines = new User();
//		gines.setId(1);
//		gines.setName("Gines");
//		record.setUser(gines);
//		Room r = new Room();
//		r.setId(0);
//		r.setName("LivingRoom");
//		record.setRoom(r);
//		record.setMac("C5:39:2D:D9:C1:B8");
//		record.setRssi(-76);
//		record.setX(530);
//		record.setY(70);
//		record.setDateTime(new Date());
//		Object recordObject = (Object) record;
//
//		IncomingEvent ie = new IncomingEvent();
//		ie.setIteration(null);
//		ie.setState("OmerInLivingRoom");
//		ie.setTimeOld(record.getDateTime());
//		ie.setDateOld(record.getDateTime());
//		dataManager.addRow(record, "MySQL");
//		System.out.println(recordObject);
//        String query = "";
//        DatabaseListenerThread listenerThread = new DatabaseListenerThread(query);
//        
//        listenerThread.start();
		Trigger.launch(args);
	}
}
