package middleware;

import java.math.BigInteger;
import java.util.Date;

public class Action {

	public static void performActions(Object o) {
		
		sendDataToMReasonerDB(o);
	}
	
	public static void sendDataToMReasonerDB(Object o) {
//		Record record = (Record) o;
//		System.out.println("PERFORMING ACTION: " + record.getUser().getName() + "In" + record.getRoom().getName());
//		String value = record.getUser().getName() + "In" + record.getRoom().getName();
//		IncomingEvent incomingEvent = new IncomingEvent();
//		incomingEvent.setState(value);
//		incomingEvent.setValue(true);
//		incomingEvent.setIteration(new BigInteger("85667"));
//		incomingEvent.setDateOld(record.getDateTime());
//		incomingEvent.setTimeOld(record.getDateTime());
////		System.out.println("ADDING: " + incomingEvent);
//		Main.dataManager.addRow(incomingEvent, "PostgreSQL");
	}
	
	public static void sendDataToDB(Object dstnObject, String db) {

//		DataManager dataManager = new DataManager(); //DELETE
//		dataManager.addRow(dstnObject, db);
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
