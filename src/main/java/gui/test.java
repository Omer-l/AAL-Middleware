package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
    public static void main(String[] args) {
    	System.out.println("Running BLE DB to MReasoner DB Script...");
        try {
        	String dbUrl = "jdbc:mysql://localhost:3306/beacon_localisation";
            String username = "root";
            String password = "root";
//        	Class.forName("C:/Users/EIS/AppData/Roaming/DBeaverData/drivers/maven/maven-central/mysql/mysql-connector-java-8.0.29.jar");
//    		Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
//            String insertQuery = "INSERT INTO your_table (name, age, email) VALUES (?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
//            preparedStatement.setString(1, name);
//            preparedStatement.setInt(2, age);
//            preparedStatement.setString(3, email);

//            int rowsInserted = preparedStatement.executeUpdate();
//
//            if (rowsInserted > 0) {
//                System.out.println("Data inserted successfully.");
//            } else {
//                System.out.println("Failed to insert data.");
//            }
            Connection connection = DriverManager.getConnection(dbUrl, username, password);
            String sqlQuery = "SELECT * FROM record ORDER BY dateTime DESC LIMIT 1";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                // Retrieve data from the latest row
                int id = resultSet.getInt("idRecord");
                String name = resultSet.getString("MAC");
                // ... (other columns)

                System.out.println("Latest row: id=" + id + ", name=" + name);
            } else {
                System.out.println("Table is empty.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
