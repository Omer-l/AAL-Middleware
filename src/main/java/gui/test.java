package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class test {
    public static void main(String[] args) {
    	System.out.println("Running BLE DB to MReasoner DB Script...");
        HashMap<String, String> nameAndLocationArr = new HashMap<String, String>();
        try {
        	String latestName = "";
        	String latestLocation = "";
//        	read latest BLE row from db get user's name and location
        	String dbUrl = "jdbc:mysql://localhost:3306/beacon_localisation";
            String username = "root";
            String password = "root";
    		Connection connection = DriverManager.getConnection(dbUrl, username, password);
            String sqlQuery = "SELECT record.dateTime, beacon.location, user.name"
    				+ " FROM"
    				+ 	" record"
    				+ " JOIN"
    				+ 	" beacon on beacon.MAC = record.MAC"
    				+ " JOIN"
    				+ 	" user on user.idUser = record.idUser"
    				+ " ORDER BY 1 DESC LIMIT 1";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                // Retrieve data from the latest row
                latestName = resultSet.getString("name");
                latestLocation = resultSet.getString("location");
            } else {
                System.out.println("Table is empty.");
            }
            String fileName = "./lastAccessedBLE.txt";

//            	get the user and location
//				read file if exists get map of userAndRoom
            	//READ FILE
                File f = new File ("./lastAccessedBLE.txt");
            	if(!f.exists())
            		f.createNewFile();
            	BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] lineSplit = line.split(",");
                    if(lineSplit.length == 2)
                    	nameAndLocationArr.put(lineSplit[0], lineSplit[1]);
                }
                bufferedReader.close();
//            	if dbRoom and fileUserRoom matches
//            		then do nothing to file
//            	else
//            		edit userAndRoom map
//            	connect to postgresql jdbc
        		connection.close();
            	dbUrl = "jdbc:postgresql://localhost:5432/sensors";
                username = "postgres";
                password = "123456";
                connection = DriverManager.getConnection(dbUrl, username, password);
//            	send relevant data to incoming_events postgresql
                
                //write to file of the new location of user
                // Data to save
                StringBuilder dataToSave = new StringBuilder();
                for(String key : nameAndLocationArr.keySet()) {
                	dataToSave.append(key);
                	dataToSave.append(",");
                	dataToSave.append(nameAndLocationArr.get(key));
                	dataToSave.append("\n");
                }
                System.out.println(dataToSave);
            	FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write(dataToSave.toString());
                System.out.println("Data saved successfully.");
                fileWriter.close();

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
    }
}
