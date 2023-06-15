package middleware;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Date;

import dao.DataManager;
import entity.mysql.beacon_localisation.Record;
import entity.mysql.beacon_localisation.Room;
import entity.mysql.beacon_localisation.User;
import entity.postgresql.sensors.IncomingEvent;

public class Action {

	public static void performActions(Object o) {
		sendDataToMReasonerDB(o);
	}
	
	public static void sendDataToMReasonerDB(Object o) {
		Record record = (Record) o;
		System.out.println("PERFORMING ACTION: " + record.getUser().getName() + "In" + record.getRoom().getName());
		String value = record.getUser().getName() + "In" + record.getRoom().getName();
		IncomingEvent incomingEvent = new IncomingEvent();
		incomingEvent.setState(value);
		incomingEvent.setValue(true);
		incomingEvent.setIteration(new BigInteger("85667"));
		incomingEvent.setDateOld(record.getDateTime());
		incomingEvent.setTimeOld(record.getDateTime());
//		System.out.println("ADDING: " + incomingEvent);
		Main.dataManager.addRow(incomingEvent, "PostgreSQL");
	}
	
	public static void sendDataToDB(Object dstnObject, String db) {

		DataManager dataManager = new DataManager(); //DELETE
		dataManager.addRow(dstnObject, db);
		//Dynamic access into an object
//		Record object = new Record();
//        object.setUser(new User());
//        object.getUser().setName("HARRY");
//
//        try {
//        	Field[] fields = object.getClass().getDeclaredFields();
//	        for (Field field : fields) {
//	            field.setAccessible(true);
//	            System.out.println(field.getName() + " = " + field.get(object) + " type: " + field.getType());
//	        }
//        } catch(IllegalArgumentException | IllegalAccessException e) {
//        	e.printStackTrace();
//        }
        
	}
	
	public static void main(String[] args) {
		Record record = new Record();
		User gines = new User();
		gines.setId(1);
		gines.setName("Gines");
		record.setUser(gines);
		Room r = new Room();
		r.setId(0);
		r.setName("LivingRoom");
		record.setRoom(r);
		record.setMac("C5:39:2D:D9:C1:B8");
		record.setRssi(-76);
		record.setX(530);
		record.setY(70);
		record.setDateTime(new Date());
		Object recordObject = (Object) record;
//		System.out.println(recordObject.getClass());
//		System.out.println(record.getClass());
		sendDataToDB(recordObject, "MySQL");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * //Dynamic access into an object
		Record object = new Record();
        object.setUser(new User());
        object.getUser().setName("HARRY");

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println(field.getName() + " = " + field.get(object) + " type: " + field.getType());
        }
	 */
}
