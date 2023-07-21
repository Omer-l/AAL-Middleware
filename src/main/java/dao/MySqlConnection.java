package dao;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import middleware.Main;

public class MySqlConnection {
	private String url;
	private String username;
	private String password;
	private Connection connection;
	

	public MySqlConnection(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
//		try {
//			connect();
//		} catch(SQLException sqle) {
//			sqle.printStackTrace();
//		}
	}
	
	public MySqlConnection() {
		
	}

	public void connect() throws SQLException {
		connection = DriverManager.getConnection(url, username, password);
	}

	public void disconnect() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public ArrayList<String> getTableNames(String database) {
		ArrayList<String> tableNames = new ArrayList<String>();
		
		try {
			this.url = this.url.substring(0, this.url.lastIndexOf('/') + 1) + database;
			connect();
		    //Creating a Statement object
		    Statement stmt = connection.createStatement();
		    //Retrieving the data
		    ResultSet tables = url.contains("mysql") ? stmt.executeQuery("Show tables") : stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'");
			while (tables.next()) {
				String tableName = url.contains("mysql") ? tables.getString(1) : tables.getString("table_name");
				System.out.println(url + " NAME: " + tableName);
				tableNames.add(tableName);
			}
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		} finally {
			if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
		System.out.println("CUT");

		return tableNames;
	}

	public String[] getColumnNames(String tableName) {
		ArrayList<String> columnNames = new ArrayList<String>();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connect();
//			// Prepare statement to get column names
//			stmt = connection
//					.prepareStatement("SELECT column_name FROM information_schema.columns WHERE table_name = ?");
//			stmt.setString(1, tableName);
//
//			// Execute query and get results
//			rs = stmt.executeQuery();


            // Get the column information
            rs = connection.getMetaData().getColumns(null, null, tableName, null);

			// Print out column names
			boolean firstColumn = true;
			while (rs.next()) {
				if(firstColumn) {
					firstColumn = false;
					continue;
				}
				String columnName = rs.getString("column_name");
//				String columnDataType = rs.getString("type_name");
//				System.out.println("col: " + columnName + "... TYPE: " + columnDataType);
				columnNames.add(columnName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
		return columnNames.toArray(new String[columnNames.size()]);
	}

	public ArrayList<String> getDatabaseNames() {
		ArrayList<String> databaseNames = new ArrayList<String>();
		try {
			connect();
			Statement stmt = connection.createStatement();
			String query = "";
			String databaseVariable = "";
			if (url.contains("postgresql")) {
				query = "SELECT datname FROM pg_database WHERE datistemplate = false";
				databaseVariable = "datname";
			} else {
				query = "SHOW DATABASES";
				databaseVariable = "Database";
			}
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String databaseName = rs.getString(databaseVariable);
				databaseNames.add(databaseName);
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
		return databaseNames;
	}

	public ArrayList<Map<String, Object>> getRows(String tableName, String columnName, Object columnValue) {
		ArrayList<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

		String query = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";
		try {
			connect();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setObject(1, columnValue);
			try {
				ResultSet rs = pstmt.executeQuery();
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
		return rows;
	}

	public ArrayList<Map<String, Object>> queryDB(String query, String type) {
		ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		try {
			connect();
			Statement stmt = connection.createStatement();
			switch(type) {
				case "select":
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
						result.add(row);
					}
					break;
				case "":
					stmt.executeUpdate(query);
				   	break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("ERROR: ", Arrays.toString(e.getStackTrace()));
			result.add(row);
		} finally {
			if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}

		return result;
	}

	public void insertRow(String tableName, Map<String, Object> rowData) {
		try {
			connect();
			String[] columnNames = getColumnNames(tableName);
			String query = generateInsertQuery(tableName, rowData, columnNames);
			PreparedStatement pstmt = connection.prepareStatement(query);
			setPreparedStatementValues(pstmt, rowData, columnNames);
			System.out.println("Q: " + query);
			pstmt.executeUpdate();
			System.out.println("Row inserted successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
	}

	private static void setPreparedStatementValues(PreparedStatement pstmt, Map<String, Object> rowData, String[] columnNames)
			throws SQLException {
		int parameterIndex = 1;
		for (String columnName : columnNames) {
			Object value = rowData.get(columnName);
			pstmt.setObject(parameterIndex, value);
			parameterIndex++;
		}
		System.out.println("PREPARED: " + pstmt);
	}

	private String generateInsertQuery(String tableName, Map<String, Object> rowData, String[] columnNames) {
		StringBuilder query = new StringBuilder("INSERT INTO ");
		query.append(tableName).append(" (");
		int columnCount = rowData.size();
		int counter = 0;
		for (String columnName : columnNames) {
			query.append(columnName);
			counter++;
			if (counter < columnCount) {
				query.append(", ");
			}
		}

		query.append(") VALUES (");
		for (int i = 0; i < columnCount; i++) {
			query.append("?");
			if (i < columnCount - 1) {
				query.append(", ");
			}
		}

		query.append(")");

		return query.toString();
	}
	
	public ArrayList<HashMap<String, String>> getForeignKeys(String tableName) {
		ArrayList<HashMap<String, String>> foreignKeysMap = new ArrayList<HashMap<String, String>>();
		try {
			connect();
            // Retrieve metadata from the result set
            DatabaseMetaData metaData = connection.getMetaData();
            
            // Get the foreign keys information
            ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, tableName);
            
            // Iterate through the result set and print foreign key table details
            while (foreignKeys.next()) {
                String pkTableName = foreignKeys.getString("PKTABLE_NAME");  // Name of the primary key table
                String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");  // Name of the primary key column
                String fkTableName = foreignKeys.getString("FKTABLE_NAME");  // Name of the foreign key table
                String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");  // Name of the foreign key column
                HashMap<String, String> foreignKeyMap = new HashMap<String, String>();
                foreignKeyMap.put("pkTableName", foreignKeys.getString("PKTABLE_NAME"));  // Name of the primary key table
                foreignKeyMap.put("pkColumnName", foreignKeys.getString("PKCOLUMN_NAME"));  // Name of the primary key column
                foreignKeyMap.put("fkTableName", foreignKeys.getString("FKTABLE_NAME"));  // Name of the foreign key table
                foreignKeyMap.put("fkColumnName", foreignKeys.getString("FKCOLUMN_NAME"));  // Name of the foreign key column
                foreignKeysMap.add(foreignKeyMap);
            }

            // Close the resources
            foreignKeys.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
		
		return foreignKeysMap;
	}
	
	/**
	 * 
SELECT * 
FROM record 
	INNER JOIN `user` on (record.idUser = user.idUser)
	INNER join `room` on (record.idRoom = room.id) 
ORDER BY dateTime  DESC LIMIT 1;
	 */
	public String getSelectWithForeignKeyQuery(String tableName, String orderByColumnName, Integer limit) {
		StringBuilder query = new StringBuilder("SELECT * FROM record");
		ArrayList<HashMap<String, String>> foreignKeysMap = getForeignKeys(tableName);
		
		if(foreignKeysMap.size() > 0) {
			for(HashMap<String, String> foreignKeyMap : foreignKeysMap) {
                String pkTableName = foreignKeyMap.get("pkTableName");  // Name of the primary key table
                String pkColumnName = foreignKeyMap.get("pkColumnName");  // Name of the primary key column
                String fkTableName = foreignKeyMap.get("fkTableName");  // Name of the foreign key table
                String fkColumnName = foreignKeyMap.get("fkColumnName");  // Name of the foreign key column
				query.append(" INNER JOIN " + pkTableName + " ON (" + tableName + "." + fkColumnName + " = " + pkTableName + "." + pkColumnName + ")");
			}
		}
		if(orderByColumnName != null)
			query.append(" ORDER BY " + orderByColumnName);
		
		if(limit != null)
			query.append(" DESC LIMIT " + limit);
		
		if(Main.DEBUG)
			System.out.println(query);
		
		return query.toString();
	}
	
//	public static void main(String[] args) {
//        String dbUrl = "jdbc:mysql://localhost:3306/beacon_localisation";
//        String dbUsername = "root";
//        String dbPassword = "root";        	
//        MySqlConnection connection = new MySqlConnection(dbUrl, dbUsername, dbPassword);
//
//        System.out.println(connection.queryDB(connection.getSelectWithForeignKeyQuery("record", "dateTime", 5)));
//	}
	
    public static String getTimeFromMySQLDateTime(String dateTimeString) {
    	return dateTimeString.substring(dateTimeString.indexOf('T')+1);
    }
	
    public static String getDateFromMySQLDateTime(String dateTimeString) {
    	return dateTimeString.substring(0, dateTimeString.indexOf('T'));
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void connectToDb(String db) { 
		this.url = url.substring(0, url.lastIndexOf('/')+1) + db;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
