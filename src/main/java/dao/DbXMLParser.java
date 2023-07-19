package dao;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

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
            NodeList nodeList = rootElement.getElementsByTagName("property");
            if (nodeList.getLength() > 0) {
        		for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) node;
                        String nodeName = childElement.getNodeName();
                        String nodeValue = childElement.getTextContent();
                        dbDetails[0] = childElement.getAttribute("url");
                        dbDetails[1] = childElement.getAttribute("username");
                        dbDetails[2] = childElement.getAttribute("password");
//                        if(nodeName.equals("property"))
//                    		if(nodeAttribute.contains("url"))
//                    			dbDetails[0] = nodeValue;
//                        	else if(nodeAttribute.contains("username"))
//                        		dbDetails[1] = nodeValue;
//                        	else if(nodeAttribute.contains("password"))
//                        		dbDetails[2] = nodeValue;
                    }
        		}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return dbDetails;
	}
	
//    private static String[] traverseElements(Element element) {
//        NodeList nodeList = element.getChildNodes();
//		String[] dbDetails = new String[3]; //0 is url, 1 is user, 2 is password
//		for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            if (node.getNodeType() == Node.ELEMENT_NODE) {
//                Element childElement = (Element) node;
//                String nodeName = childElement.getNodeName();
//                String nodeValue = childElement.getTextContent();
//                String nodeAttribute = childElement.getAttribute("name");
//                if(nodeName.equals("property"))
//            		if(nodeAttribute.contains("url"))
//            			dbDetails[0] = nodeValue;
//                	else if(nodeAttribute.contains("username"))
//                		dbDetails[1] = nodeValue;
//                	else if(nodeAttribute.contains("password"))
//                		dbDetails[2] = nodeValue;
////                System.out.println("VAL: " + nodeValue);
////                traverseElements(childElement);
//            }
//        }
//		return dbDetails;
//    }
}
