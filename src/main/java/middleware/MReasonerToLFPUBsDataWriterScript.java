package middleware;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MReasonerToLFPUBsDataWriterScript {

	public static void main(String[] args) {

		try {
	//    	read latest MReasoner rows from db get user's name and location
			ArrayList<String> rows = getRows("public.resultS");
			//write to file of the new location of user
            // Data to save
			
            StringBuilder dataToSave = new StringBuilder();
            for(String row : rows) {
            	dataToSave.append(row);
            	dataToSave.append("\n");
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:/MREASONER/lfpubs/files/middleware.txt"));
            byte[] data = dataToSave.toString().getBytes(); // Replace with your data
            bos.write(data);
            bos.close();
		} catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static ArrayList<String> getRows(String tableName) {
		ArrayList<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
    	String dbUrl = "jdbc:postgresql://localhost:5432/sensors";
        String username = "postgres";
        String password = "123456";
        Connection connection = null;
		String query = "SELECT * FROM " + tableName + " ORDER BY 1 ASC";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
			connection = DriverManager.getConnection(dbUrl, username, password);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> row = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					String colName = rsmd.getColumnName(i);
					Object colValue = rs.getObject(i);
					row.put(colName, colValue);
				}
				rows.add(row);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
		ArrayList<String> parsedRows = new ArrayList<>();
		Map<String, Object> prevRow = null;
		int it = 0; //skip first row
		for(Map<String, Object> row : rows) {
			long time = (long) row.get("system_time_millis");
			row.remove("system_time_millis");
			row.remove("iteration");
			boolean updated = false;
			
			for(String key : row.keySet()) {
				
				if(prevRow == null || prevRow.get(key) == null || !prevRow.get(key).equals(row.get(key))) {
					Instant instant = Instant.ofEpochMilli(time);
					StringBuilder sb = new StringBuilder((instant.atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter).toString()));
					sb.append(",");
					sb.append(key);
					if((boolean) row.get(key) == true)
						sb.append(",ON,100");
					else if ((boolean) row.get(key) == false)
						sb.append(",OFF,0");
					parsedRows.add(sb.toString());
					updated = true;
				}
			}
			if(updated)
				prevRow = row;
		}
		
		return parsedRows;
	}

}
