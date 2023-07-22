package dao;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DbXMLParser {
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