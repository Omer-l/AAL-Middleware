//package trash;
//
//import java.util.List;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import org.hibernate.tool.schema.internal.AbstractSchemaValidator;
//import org.hibernate.tool.schema.spi.SchemaValidator;
//import org.hibernate.validator.HibernateValidator;
//
//public class DataManager {
//	private HibernateUtil hibernateUtilMySql = new HibernateUtil("MySQL");
//	private HibernateUtil hibernateUtilPostgresql = new HibernateUtil("PostgreSQL");
//	private Session session;
//    
//    private Session session(String db) {
//    	switch(db) {
//	    	case "MySQL": return hibernateUtilMySql.getSessionFactory().openSession();
//	    	case "PostgreSQL": return hibernateUtilPostgresql.getSessionFactory().openSession(); 
//    	}
//		return null;
//	}
//    
//    public List<Object> getSpecificRows(String entityName, String[] columnNames, Object[] values, String db) {
//    	session = session(db);
//        Transaction transaction = session.beginTransaction();
//
//        try {
//        	String hql = "FROM " + entityName + " e WHERE ";
//        	for(int colIndex = 0; colIndex < columnNames.length; colIndex++) {
//	            hql += "e." + columnNames[colIndex] + " = :value" + colIndex;
//	            if(colIndex < columnNames.length - 1)
//	            	hql += " AND ";
//        	}
//            Query query = session.createQuery(hql);
//        	for(int colIndex = 0; colIndex < columnNames.length; colIndex++)
//        		query.setParameter("value" + colIndex, values[colIndex]);
//
//            List<Object> resultList = query.getResultList();
//            transaction.commit();
//            return resultList;
//        } catch (Exception e) {
//            transaction.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return null;
//    }
//    
//
////    public static void main(String[] args) {
////    	DataManager dm = new DataManager();
////    	String[] columnNames = {"implementation", "device"};
////    	Object[] values = {"reed", "8"};
////    	System.out.println(dm.getSpecificRows("Sensors", columnNames, values, "PostgreSQL"));
////    }
//    
//    public Object getLatestRow(String entity, String db) {
//    	session = session(db);
//    	
//    	try {
//            // Create an HQL query to select all rows from the table
//            String hql = "FROM " + entity; // Replace YourEntity with the name of your entity class
//            Query query = session.createQuery(hql);
//
//            // Execute the query and get the list of rows
//            List<Object> rows = query.getResultList();
//            if(rows.isEmpty())
//            	return null;
//            else
//            	return rows.get(rows.size() - 1);
//        } finally { 
//            // Close the Hibernate session
//        	session.close();
//        }
//    }
//
//	public void addRow(Object dstnObject, String db) {
//
//        session = session(db);
//      	
//        // Begin a transaction
//        Transaction transaction = session.beginTransaction();
//        
//        try {
//            // Save the new entity to the database
//        	session.persist(dstnObject);
//            
//            // Commit the transaction
//            transaction.commit();
//            
//            System.out.println("New row inserted successfully.");
//        } catch (Exception e) {
//            // Rollback the transaction if an error occurs
//            transaction.rollback();
//            e.printStackTrace();
//        } finally {
//            // Close the Hibernate session
//        	session.close();
//        }
//    }
//	
////	public static boolean isEntity(String rdbm, String table, String entityName) {
////		if()
//	public static void main(String[] args) {
//
////		return false;
//	}
//}
