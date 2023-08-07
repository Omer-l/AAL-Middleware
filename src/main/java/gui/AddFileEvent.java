package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.BufferedReader;

import javax.swing.plaf.metal.MetalBorders.TextFieldBorder;
import dao.DbXMLParser;
import dao.MySqlConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.geometry.Pos;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class AddFileEvent extends Window {
	private MySqlConnection dbManager = new MySqlConnection();
    private Menu menu5 = new Menu();
    private TextField logField = new TextField();
    private TextField valueField = new TextField();
    private TextField currentWorkingDirectyField = new TextField();
    private Button testButton = new Button("Test");
    private Button saveButton = new Button("Save");

    private Button runFileMethodButton = new Button("Run");
    private Button readFileMethodButton = new Button("Read");
    private Button writeFileMethodButton = new Button("Write");
    private String query;
    private File selectedFile;
    private  String operation; //run, read, or write
	Map<String, Object> editRunData = new HashMap<String, Object>();
	Map<String, Object> editReadData = new HashMap<String, Object>();
	Map<String, Object> editWriteData = new HashMap<String, Object>();

    
	public AddFileEvent(Window prevWindow, String operation) {
		super(prevWindow);
		this.operation = operation;
	}
    
	public AddFileEvent() {
		
	}
	
	public static void removeEventFromFileEvent(String uniqueID, String operation) {
		try {
			if(operation == "run") {
				  MainMenu.mainDbManager.queryDB("DELETE FROM system_file_run_event WHERE system_file_run_event.unique_id = '" + uniqueID + "'","");
			}else if(operation == "read") {
			  MainMenu.mainDbManager.queryDB("DELETE FROM system_file_read_event WHERE system_file_read_event.unique_id = '" + uniqueID + "'","");
			}else if(operation == "write") {
			  MainMenu.mainDbManager.queryDB("DELETE FROM system_file_write_event WHERE system_file_write_event.unique_id = '" + uniqueID + "'","");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		}
	
	public void loadData(String uniqueId, String operation) {
		this.operation = operation;
		if(operation == "run") {
			editRunData = MainMenu.mainDbManager.queryDB("SELECT * FROM system_file_run_event JOIN event ON system_file_run_event.unique_id = event.unique_id WHERE event.unique_id =  \"" + uniqueId + "\"", "select").get(0);
		} else if(operation == "read") {
			editReadData = MainMenu.mainDbManager.queryDB("SELECT * FROM system_file_read_event JOIN event ON system_file_read_event.unique_id = event.unique_id WHERE event.unique_id = \"" + uniqueId + "\"", "select").get(0);
		} else if(operation == "write") {
			editWriteData = MainMenu.mainDbManager.queryDB("SELECT * FROM system_file_write_event JOIN event ON system_file_write_event.unique_id = event.unique_id WHERE event.unique_id = \"" + uniqueId + "\"", "select").get(0);
		}
	}
	
	public void loadData(String uniqueId, TextField uniqueIdField, TextField nameField,
			 TextField descriptionField, VBox column1VBox3) {
		
		try {
		if(operation  == "run") {
			uniqueIdField.setText(uniqueId);
			nameField.setText((String) editRunData.get("name"));
			descriptionField.setText((String) editRunData.get("description"));
			currentWorkingDirectyField.setText((String) editRunData.get("current_working_directory"));
			valueField.setText((String) editRunData.get("command"));
			logField.setText((String) editRunData.get("command"));
			MainMenu.setActive(runFileMethodButton);
        	openRunForm(column1VBox3);
        	processTestButton("run");
        	readFileMethodButton.setDisable(true);
        	writeFileMethodButton.setDisable(true);
	    }else if( operation == "read") {
	    	uniqueIdField.setText(uniqueId);
			nameField.setText((String) editReadData.get("name"));
			descriptionField.setText((String) editReadData.get("description"));
			selectedFile = new File((String) editReadData.get("path"));
			valueField.setText((String) editReadData.get("content"));
			logField.setText((String) editReadData.get("path"));
			MainMenu.setActive(readFileMethodButton);
        	openReadForm(column1VBox3);
        	processTestButton("read");
        	runFileMethodButton.setDisable(true);
        	writeFileMethodButton.setDisable(true);
		}else if(operation == "write") {
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	

	public void open() {
		MainMenu.clearMainBox();
		MainMenu.changeTitle("Add File Event");
		
		
      Button button1 = new Button("Back");
        button1.setOnAction(event -> { back(); });
        button1.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9");
        saveButton.setStyle("-fx-font: 15 arial ; -fx-base: #FFE4E1");
        testButton.setStyle("-fx-font: 15 arial ; -fx-base: #FFE4E1");
        runFileMethodButton.setStyle("-fx-font: 15 arial ; -fx-base: #E9967A");
        readFileMethodButton.setStyle("-fx-font: 15 arial ; -fx-base: #E9967A");
        writeFileMethodButton.setStyle("-fx-font: 15 arial ; -fx-base: #E9967A");


        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty());
        //column1urations
        VBox column1VBox1 = new VBox(2);
        Text column1Header = new Text("Details");
        column1Header.setStyle(MainMenu.HEADER_1_STYLE);

        HBox column1HBox1 = new HBox();
        VBox column1HBox1VBox1 = new VBox();
        column1HBox1VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox1VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox1VBox1Header = new Text("UniqueID");
        VBox column1HBox1VBox2 = new VBox();
        column1HBox1VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox1VBox2TextField = new TextField();
        column1HBox1VBox1.getChildren().addAll(column1HBox1VBox1Header);
        column1HBox1VBox2.getChildren().addAll(column1HBox1VBox2TextField);
        column1HBox1.getChildren().addAll(column1HBox1VBox1, column1HBox1VBox2);

        HBox column1HBox2 = new HBox();
        VBox column1HBox2VBox1 = new VBox();
        column1HBox2VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox2VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox2VBox1Header = new Text("Name");
        VBox column1HBox2VBox2 = new VBox();
        column1HBox2VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox2VBox2TextField = new TextField();
        column1HBox2VBox1.getChildren().addAll(column1HBox2VBox1Header);
        column1HBox2VBox2.getChildren().addAll(column1HBox2VBox2TextField);
        column1HBox2.getChildren().addAll(column1HBox2VBox1, column1HBox2VBox2);

        HBox column1HBox3 = new HBox();
        VBox column1HBox3VBox1 = new VBox();
        column1HBox3VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox3VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox3VBox1Header = new Text("Description");
        VBox column1HBox3VBox2 = new VBox();
        column1HBox3VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox3VBox2TextField = new TextField();
        column1HBox3VBox1.getChildren().addAll(column1HBox3VBox1Header);
        column1HBox3VBox2.getChildren().addAll(column1HBox3VBox2TextField);
        column1HBox3.getChildren().addAll(column1HBox3VBox1, column1HBox3VBox2);

        //column1urations
        VBox column1VBox2 = new VBox(2);
        VBox column1VBox3 = new VBox();
        Text column1VBox2Header = new Text("Method");
        column1VBox2Header.setStyle(MainMenu.HEADER_1_STYLE);
        HBox column1ButtonBar1HBox = new HBox();
        column1ButtonBar1HBox.setAlignment(Pos.CENTER);
        ButtonBar column1ButtonBar1 = new ButtonBar();

        runFileMethodButton.setOnAction(event -> { 
        	MainMenu.setActive(runFileMethodButton);
        	MainMenu.deactivate(readFileMethodButton);
        	MainMenu.deactivate(writeFileMethodButton);
        	saveButton.setDisable(true);
    		column1VBox3.getChildren().clear();
        	openRunForm(column1VBox3);
        });
        readFileMethodButton.setOnAction(event -> { 
        	MainMenu.setActive(readFileMethodButton); 
        	MainMenu.deactivate(runFileMethodButton);
        	MainMenu.deactivate(writeFileMethodButton);
        	saveButton.setDisable(true);
    		column1VBox3.getChildren().clear();
        	openReadForm(column1VBox3);
        });
        writeFileMethodButton.setOnAction(event -> { 
        	MainMenu.setActive(writeFileMethodButton); 
        	MainMenu.deactivate(readFileMethodButton);
        	MainMenu.deactivate(runFileMethodButton);
        	saveButton.setDisable(true);
    		column1VBox3.getChildren().clear();
        	openWriteForm(column1VBox3);
        	});
        
        if(operation == "run") {
         MainMenu.setActive(runFileMethodButton); openRunForm(column1VBox3);
        }else if(operation == "read") {
            MainMenu.setActive(readFileMethodButton); openReadForm(column1VBox3);
         }else if(operation == "write") {
         MainMenu.setActive(writeFileMethodButton); openWriteForm(column1VBox3);

        }
        
//        switch(operation) {
//	        case "run": MainMenu.setActive(runFileMethodButton); openRunForm(column1VBox3); break;
//	        case "read": MainMenu.setActive(readFileMethodButton); openReadForm(column1VBox3); break;
//	        case "write": MainMenu.setActive(writeFileMethodButton); openWriteForm(column1VBox3); break;
//        }
        column1ButtonBar1.getButtons().addAll(runFileMethodButton, readFileMethodButton, writeFileMethodButton);
        column1ButtonBar1HBox.getChildren().add(column1ButtonBar1);
//        column1ButtonBar1
        VBox column1VBox4 = new VBox(10);
        
        ButtonBar column1ButtonBar2 = new ButtonBar();
        testButton.setOnAction(event -> { processTestButton(operation); });
        saveButton.setDisable(true);
    	saveButton.setOnAction(event -> {
        	processSaveButton(column1HBox1VBox2TextField.getText(), column1HBox3VBox2TextField.getText(), column1HBox2VBox2TextField.getText());
    	});
        column1ButtonBar2.getButtons().addAll(testButton, saveButton);
       HBox column1HBox9 = new HBox();
        MyStyles.createLogField(logField, mainVBox1,column1HBox9);
        
        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header, column1ButtonBar1HBox);
        column1VBox4.getChildren().addAll(column1ButtonBar2, column1HBox9);
        
        mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, column1VBox3, column1VBox4);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);
        if(!editRunData.isEmpty()) {
    		column1VBox3.getChildren().clear();
        	operation = "run";
        	loadData((String) editRunData.get("unique_id"), column1HBox1VBox2TextField, column1HBox2VBox2TextField, column1HBox3VBox2TextField, column1VBox3);
        }else if(!editReadData.isEmpty()) {
    		column1VBox3.getChildren().clear();
        	loadData((String) editReadData.get("unique_id"), column1HBox1VBox2TextField, column1HBox2VBox2TextField, column1HBox3VBox2TextField, column1VBox3);

        }
	}


	private void openRunForm(VBox column1VBox3) {

        HBox column1HBox7 = new HBox();
        VBox column1HBox7VBox1 = new VBox();
        column1HBox7VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox7VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox7VBox1Header = new Text("Working Directory of Command");
        VBox column1HBox7VBox2 = new VBox();
        column1HBox7VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        column1HBox7VBox1.getChildren().addAll(column1HBox7VBox1Header);
		column1HBox7VBox2.getChildren().addAll(currentWorkingDirectyField );
        column1HBox7.getChildren().addAll(column1HBox7VBox1, column1HBox7VBox2);

        HBox column1HBox8 = new HBox();
        VBox column1HBox8VBox1 = new VBox();
        column1HBox8VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox8VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox8VBox1Header = new Text("Command (including classpath, args etc)");
        VBox column1HBox8VBox2 = new VBox();
        column1HBox8VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        column1HBox8VBox1.getChildren().addAll(column1HBox8VBox1Header);
        column1HBox8VBox2.getChildren().addAll(valueField);
        column1HBox8.getChildren().addAll(column1HBox8VBox1, column1HBox8VBox2);

        column1VBox3.getChildren().addAll(column1HBox7, column1HBox8);
	}


	private void selectFile() {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();

        // Set an initial directory (optional)
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Show the file dialog
        selectedFile = fileChooser.showOpenDialog(MainMenu.primaryStage);

        if (selectedFile != null) {
            // Display the selected file path in the label
            logField.setText(selectedFile.getAbsolutePath());
        } else {
            // No file selected
            logField.setText("No file selected");
        }
	}


	private void openReadForm(VBox column1VBox2) {
        HBox column1Hbox4 = new HBox();
        VBox column1Hbox4VBox1 = new VBox();
        column1Hbox4VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1Hbox4VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1Hbox4VBox1Header = new Text("File To Read ");
        VBox column1Hbox4VBox2 = new VBox();
        column1Hbox4VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        // Create a File chooser
        Button uploadButton = new Button("Select File");
        uploadButton.setOnAction(event -> selectFile());
        column1Hbox4VBox1.getChildren().addAll(column1Hbox4VBox1Header);
        column1Hbox4VBox2.getChildren().addAll(uploadButton);
        column1Hbox4.getChildren().addAll(column1Hbox4VBox1, column1Hbox4VBox2);

        HBox column1HBox7 = new HBox();
        VBox column1HBox7VBox1 = new VBox();
        column1HBox7VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1HBox7VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox7VBox1Header = new Text("Contains (leave empty for any change in file)");
        VBox column1HBox7VBox2 = new VBox();
        column1HBox7VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Menu menu4 = new Menu();
        column1HBox7VBox1.getChildren().addAll(column1HBox7VBox1Header);
        column1HBox7VBox2.getChildren().addAll(valueField);
        column1HBox7.getChildren().addAll(column1HBox7VBox1, column1HBox7VBox2);
        
        column1VBox2.getChildren().addAll(column1Hbox4, column1HBox7);
	}

	private void openWriteForm(VBox column1VBox2) {
		
	}


	private void processTestButton(String operation) {
		logField.clear();
		switch(operation) {
			case "run":
		        try {
		        	String args = valueField.getText();
//		        	String nameOfFile = selectedFile.getName().substring(0, selectedFile.getName().indexOf("."));
//		        	if(!args.contains(nameOfFile))
//		        		args += selectedFile.getAbsolutePath();
		        	String command = valueField.getText();
		            String currentWorkingDirectory = currentWorkingDirectyField.getText();
	        		ProcessBuilder processBuilder = new ProcessBuilder("cmd" ,"/c", command);
	        		if(!currentWorkingDirectory.isEmpty())
	        			processBuilder.directory(new File(currentWorkingDirectory));
	        		
	        		Process process = processBuilder.start();
		          
		            // Redirect error stream to separate stream
		            processBuilder.redirectErrorStream(true);
		            System.out.println(processBuilder.command());

		            // Get the output stream of the process
		            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		            // Read and print the output
		            String line;
		            logField.clear();
		            while ((line = reader.readLine()) != null) {
		                System.out.println(line);
		                logField.setText(logField.getText() + "\n" + line);
		            }

		            // Wait for the process to finish
		            int exitCode = process.waitFor();
		            System.out.println("Process exited with code: " + exitCode);
		            if(exitCode == 0)
		            	saveButton.setDisable(false);
		            else
  		            	saveButton.setDisable(true);
		        } catch (IOException | InterruptedException e) {
		            e.printStackTrace();
		        }
	            break;
			case "read":
		        try {
		            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(selectedFile.getAbsolutePath()));
	                byte[] buffer = new byte[8192]; // Adjust buffer size as needed
	                int bytesRead;
	                while ((bytesRead = bis.read(buffer)) != -1) {
	                    // Convert the bytes read to a string and print the result
	                    String data = new String(buffer, 0, bytesRead);
	                    if(!valueField.getText().isEmpty() && data.indexOf(valueField.getText()) != -1) {
	                    	data = data.substring(data.indexOf(valueField.getText()));
	                    }
	                    for(String line : data.split("\n")) {
	                    	logField.appendText(line);
	                    }
	                }
	            	saveButton.setDisable(false);
 		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				break;
			case "write":
				break;
		}
	}

	private void processSaveButton(String uniqueIdInput, String nameInput, String descriptionInput) {
		String command = valueField.getText().replaceAll("\\\\", "/");
		String currentWorkingDirectory = currentWorkingDirectyField.getText().replaceAll("\\\\", "/");
//		System.out.println(filePath);
		System.out.println("WRITING");
		if(MainMenu.isActive(runFileMethodButton)) {
	    	String arguments = valueField.getText();
	    	System.out.println("WROTE");
	    	boolean emptyField = uniqueIdInput.isEmpty() || nameInput.isEmpty() || descriptionInput.isEmpty();
	    	if(!emptyField) {
	    		if(editRunData.isEmpty()) {
		    		MainMenu.mainDbManager.queryDB("INSERT INTO event VALUES ('" + uniqueIdInput + "', '" + nameInput + "', '" + descriptionInput + "');", "");
		        	MainMenu.mainDbManager.queryDB("INSERT INTO system_file_run_event VALUES ('" + uniqueIdInput + "', '" + command + "', '" + currentWorkingDirectory + "');", "");
	    		} else {
	    			MainMenu.mainDbManager.queryDB("UPDATE event SET"
		        			+ " unique_id = '" + uniqueIdInput + "', name = '" + nameInput + "', "
		        			+ "description = '" + descriptionInput + "' WHERE unique_id = '" + uniqueIdInput + "';", "");
	    			MainMenu.mainDbManager.queryDB("UPDATE system_file_run_event SET"
		        			+ " unique_id = '" + uniqueIdInput + "', path = '" + command + "', "
		        			+ "current_working_directory = '" + currentWorkingDirectory + "' WHERE unique_id = '" + uniqueIdInput + "';", "");
	    		}
	    	} else {
	    		logField.setText("unique id, name or description field is empty");
	    	} 
		} else if(MainMenu.isActive(readFileMethodButton)) {
			String filePath = selectedFile.getAbsolutePath().replaceAll("\\\\", "/");
			String content = valueField.getText();
			boolean emptyField = uniqueIdInput.isEmpty() || nameInput.isEmpty() || descriptionInput.isEmpty();
	    	if(!emptyField) {
	    		if(editReadData.isEmpty()) {
	    		MainMenu.mainDbManager.queryDB("INSERT INTO event VALUES ('" + uniqueIdInput + "', '" + nameInput + "', '" + descriptionInput + "');", "");
	        	MainMenu.mainDbManager.queryDB("INSERT INTO system_file_read_event VALUES ('" + uniqueIdInput + "', '" + filePath + "', '" + content + "');", "");
		    	} else {
	    			MainMenu.mainDbManager.queryDB("UPDATE event SET"
		        			+ " unique_id = '" + uniqueIdInput + "', name = '" + nameInput + "', "
		        			+ "description = '" + descriptionInput + "' WHERE unique_id = '" + uniqueIdInput + "';", "");
	    			MainMenu.mainDbManager.queryDB("UPDATE system_file_read_event SET"
		        			+ " unique_id = '" + uniqueIdInput + "', path = '" + filePath + "', "
		        			+ "content = '" + content + "' WHERE unique_id = '" + uniqueIdInput + "';", "");
	    		}
	    	} else { 
	    		logField.setText("unique id, name or description field is empty");
	    	} 
		}
		back();
	}
}
