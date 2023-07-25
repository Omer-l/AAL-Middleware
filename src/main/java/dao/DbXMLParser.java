package dao;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbXMLParser {
	
	public static Map<String, ArrayList<String>> mySqlDbAndTablesMap = getDbAndTablesMap("mysql.cfg.xml");
	public static Map<String, ArrayList<String>> postgresqlDbAndTablesMap = getDbAndTablesMap("postgresql.cfg.xml");
	
	public static void main(String[] args) {
		 
	}
	
	public static Map<String, ArrayList<String>> getDbAndTablesMap(String fileName) {
		HashMap<String, ArrayList<String>> dbAndTablesMap = new HashMap<>();
		
		try {
			// Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Create a DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Get the current directory
            String currentDirectory = System.getProperty("user.dir");
            // Parse the XML file
            Document document = builder.parse(new File(currentDirectory + "/src/main/resources/" + fileName));
            // Get the root element of the XML document
            Element rootElement =  document.getDocumentElement();
            
            NodeList databases = document.getElementsByTagName("database");
         
            for (int i = 0; i < databases.getLength(); i++) {
                Element databaseElement = (Element) databases.item(i);
                String databaseName = databaseElement.getAttribute("name");

                NodeList tableNodes = databaseElement.getElementsByTagName("table");
                ArrayList<String> tableList = new ArrayList<>();

                for (int j = 0; j < tableNodes.getLength(); j++) {
                    Element tableElement = (Element) tableNodes.item(j);
                    tableList.add(tableElement.getTextContent());
                }

                dbAndTablesMap.put(databaseName, tableList);
            }
           	
		}catch(Exception exception) {
			exception.printStackTrace();
		}
		return dbAndTablesMap;
		
		
		// dbAndTablesMap.get("beacon_localisation") should get me the tables mentioned in the XML file, like 'record'
	}
	
	 // Helper method to convert a Node to a String
    private static String nodeToString(Node node) {
        // Implement this method based on your requirements, depending on how you want to represent the XML element as a string.
        // For example, you might use node.getTextContent() or other methods to extract the desired information from the XML node.
        return node.getNodeName() + ": " + node.getTextContent();
    }
	
	
	public static String[] getDBDetailsSQL(String fileName) {
		String[] dbDetails = new String[3]; //0 is url, 1 is user, 2 is password
		try {
            // Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Create a DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Get the current directory
            String currentDirectory = System.getProperty("user.dir");
            // Parse the XML file
            Document document = builder.parse(new File(currentDirectory + "/src/main/resources/" + fileName));
            // Get the root element of the XML document
            Element rootElement = document.getDocumentElement();
            // Perform operations on the XML data
            // Example: Get the value of a specific element
            dbDetails[0] = rootElement.getElementsByTagName("url").item(0).getTextContent();
            dbDetails[1] = rootElement.getElementsByTagName("username").item(0).getTextContent();
            dbDetails[2] = rootElement.getElementsByTagName("password").item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return dbDetails;
	}
	
}