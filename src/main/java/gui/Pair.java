package gui;

public class Pair {
	 private String databaseName;
     private String tableName;
     private String url;
     private String username;
     private String password;

     public Pair(String databaseName, String tableName) {
         this.databaseName = databaseName;
         this.tableName = tableName;
     }

     public String getDatabaseName() {
         return databaseName;
     }

     public String getTableName() {
         return tableName;
     }

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
