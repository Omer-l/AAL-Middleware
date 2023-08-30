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
	private int id;

    
	public AddFileEvent(Window prevWindow, String operation) {
		super(prevWindow);
		this.operation = operation;
	}
	
	public AddFileEvent(Window prevWindow) {
		super(prevWindow);
	}
    
	public AddFileEvent() {
		
	}

	public static void removeEventFromFileEvent(int uniqueID, String operation) {
		try {
			if(operation == "run") {
				  MainMenu.mainDbManager.queryDB("DELETE FROM system_file_run_event WHERE system_file_run_event.id = " + uniqueID,"");
			}else if(operation == "read") {
			  MainMenu.mainDbManager.queryDB("DELETE FROM system_file_read_event WHERE system_file_read_event.id = " + uniqueID,"");
			}else if(operation == "write") {
			  MainMenu.mainDbManager.queryDB("DELETE FROM system_file_write_event WHERE system_file_write_event.id = " + uniqueID,"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		}
	
	public void loadData(int id, String operation) {
		this.id = id;
		this.operation = operation;
		if(operation == "run") {
			editRunData = MainMenu.mainDbManager.queryDB("SELECT * FROM system_file_run_event JOIN event ON system_file_run_event.id = event.id WHERE event.id =  " + id + "", "select").get(0);
		} else if(operation == "read") {
			editReadData = MainMenu.mainDbManager.queryDB("SELECT * FROM system_file_read_event JOIN event ON system_file_read_event.id = event.id WHERE event.id = " + id, "select").get(0);
		} else if(operation == "write") {
			editWriteData = MainMenu.mainDbManager.queryDB("SELECT * FROM system_file_write_event JOIN event ON system_file_write_event.id = event.id WHERE event.id = " + id, "select").get(0);
		}
	}
	
	public void loadData( TextField nameField,
			 TextField descriptionField, VBox column1VBox3) {
		try {
		if(operation  == "run") {
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
        button1.setStyle(MainMenu.BACK_BUTTON_STYLE);
        saveButton.setStyle(MainMenu.SAVE_BUTTON_STYLE);
        testButton.setStyle(MainMenu.TEST_BUTTON_STYLE);
        runFileMethodButton.setStyle(MainMenu.RUN_BUTTON_STYLE);
        readFileMethodButton.setStyle(MainMenu.READ_BUTTON_STYLE);
        writeFileMethodButton.setStyle(MainMenu.WRITE_BUTTON_STYLE);


        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty());
        //column1urations
        VBox column1VBox1 = new VBox(2);
        Text column1Header = new Text("Details");
        column1Header.setStyle(MainMenu.HEADER_1_STYLE);


        VBox column1HBox2 = new VBox();
        VBox column1HBox2VBox1 = new VBox();
        column1HBox2VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox2VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox2VBox1Header = new Text("Name");
        VBox column1HBox2VBox2 = new VBox();
        column1HBox2VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox2VBox2TextField = new TextField();
        column1HBox2VBox2TextField.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));

        column1HBox2VBox1.getChildren().addAll(column1HBox2VBox1Header);
        column1HBox2VBox2.getChildren().addAll(column1HBox2VBox2TextField);
        column1HBox2.getChildren().addAll(column1HBox2VBox1, column1HBox2VBox2);

        VBox column1HBox3 = new VBox();
        VBox column1HBox3VBox1 = new VBox();
        column1HBox3VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox3VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox3VBox1Header = new Text("Description");
        VBox column1HBox3VBox2 = new VBox();
        column1HBox3VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox3VBox2TextField = new TextField();
        column1HBox3VBox2TextField.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));

        column1HBox3VBox1.getChildren().addAll(column1HBox3VBox1Header);
        column1HBox3VBox2.getChildren().addAll(column1HBox3VBox2TextField);
        column1HBox3.getChildren().addAll(column1HBox3VBox1, column1HBox3VBox2);

        //column1urations
        VBox column1VBox2 = new VBox(2);
        VBox column1VBox3 = new VBox();
        Text column1VBox2Header = new Text("Method");
        column1VBox2Header.setStyle(MainMenu.HEADER_1_STYLE);
        
        HBox column1ButtonBar1HBox = new HBox();
        column1ButtonBar1HBox.setAlignment(Pos.CENTER_LEFT);
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
        
        column1ButtonBar1.getButtons().addAll(runFileMethodButton, readFileMethodButton, writeFileMethodButton);
        column1ButtonBar1HBox.getChildren().add(column1ButtonBar1);
        VBox column1VBox4 = new VBox(10);
        
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER_LEFT);
        ButtonBar column1ButtonBar2 = new ButtonBar();
        testButton.setOnAction(event -> { processTestButton(operation); });
        saveButton.setDisable(true);
    	saveButton.setOnAction(event -> {
        	processSaveButton(column1HBox2VBox2TextField.getText(), column1HBox3VBox2TextField.getText());
    	});
        column1ButtonBar2.getButtons().addAll(testButton, saveButton);
       HBox column1HBox9 = new HBox();
        MyStyles.createLogField(logField, mainVBox1,column1HBox9);
        
        column1VBox1.getChildren().addAll(column1Header, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header, column1ButtonBar1HBox);
        column1VBox4.getChildren().addAll(column1HBox9);
        buttons.getChildren().add(column1ButtonBar2);
        
        mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, column1VBox3, column1VBox4,buttons);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);
        if(!editRunData.isEmpty()) {
    		column1VBox3.getChildren().clear();
        	operation = "run";
        	loadData(column1HBox2VBox2TextField, column1HBox3VBox2TextField, column1VBox3);
        }else if(!editReadData.isEmpty()) {
    		column1VBox3.getChildren().clear();
        	loadData( column1HBox2VBox2TextField, column1HBox3VBox2TextField, column1VBox3);

        }
	}


	private void openRunForm(VBox column1VBox3) {

        VBox column1HBox7 = new VBox();
        VBox column1HBox7VBox1 = new VBox();
        column1HBox7VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox7VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox7VBox1Header = new Text("Working Directory of Command");
        VBox column1HBox7VBox2 = new VBox();
        currentWorkingDirectyField.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));

        column1HBox7VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        column1HBox7VBox1.getChildren().addAll(column1HBox7VBox1Header);
		column1HBox7VBox2.getChildren().addAll(currentWorkingDirectyField );
        column1HBox7.getChildren().addAll(column1HBox7VBox1, column1HBox7VBox2);

        VBox column1HBox8 = new VBox();
        VBox column1HBox8VBox1 = new VBox();
        column1HBox8VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox8VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox8VBox1Header = new Text("Command (including classpath, args etc)");
        VBox column1HBox8VBox2 = new VBox();
        valueField.maxWidthProperty().bind(MainMenu.root.widthProperty().divide(4));

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
        VBox column1Hbox4 = new VBox();
        VBox column1Hbox4VBox1 = new VBox();
        column1Hbox4VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
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

        VBox column1HBox7 = new VBox();
        VBox column1HBox7VBox1 = new VBox();
        column1HBox7VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
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

	private void processSaveButton(String nameInput, String descriptionInput) {
		String command = valueField.getText().replaceAll("\\\\", "/");
		String currentWorkingDirectory = currentWorkingDirectyField.getText().replaceAll("\\\\", "/");
//		System.out.println(filePath);
		System.out.println("WRITING");
		if(MainMenu.isActive(runFileMethodButton)) {
	    	String arguments = valueField.getText();
	    	System.out.println("WROTE");
	    	boolean emptyField = nameInput.isEmpty() || descriptionInput.isEmpty();
	    	if(!emptyField) {
	    		if(editRunData.isEmpty()) {
		    		MainMenu.mainDbManager.queryDB("INSERT INTO event VALUES (null, '" + nameInput + "', '" + descriptionInput + "');", "");
		        	int id = (int) MainMenu.mainDbManager.queryDB("SELECT * FROM event ORDER BY id  DESC LIMIT 1;", "select").get(0).get("id");
		        	MainMenu.mainDbManager.queryDB("INSERT INTO system_file_run_event VALUES (" + id + ", '" + command + "', '" + currentWorkingDirectory + "');", "");
	    		} else {
	    			MainMenu.mainDbManager.queryDB("UPDATE event SET"
		        			+ " id = " + id + ", name = '" + nameInput + "',"
		        			+ " description = '" + descriptionInput + "' WHERE id = " + id + ";", "");
	    			MainMenu.mainDbManager.queryDB("UPDATE system_file_run_event SET"
		        			+ " id = " + id + ", command = '" + command + "',"
		        			+ " current_working_directory = '" + currentWorkingDirectory + "' WHERE id = " + id + ";", "");
	    		}
	    	} else {
	    		logField.setText("name or description field is empty");
	    	} 
		} else if(MainMenu.isActive(readFileMethodButton)) {
			String filePath = selectedFile.getAbsolutePath().replaceAll("\\\\", "/");
			String content = valueField.getText();
			boolean emptyField = nameInput.isEmpty() || descriptionInput.isEmpty();
	    	if(!emptyField) {
	    		if(editReadData.isEmpty()) {
	    		MainMenu.mainDbManager.queryDB("INSERT INTO event VALUES (null, '" + nameInput + "', '" + descriptionInput + "');", "");
	        	int id = (int) MainMenu.mainDbManager.queryDB("SELECT * FROM event ORDER BY id  DESC LIMIT 1;", "select").get(0).get("id");
	        	MainMenu.mainDbManager.queryDB("INSERT INTO system_file_read_event VALUES (" + id + ", '" + filePath + "', '" + content + "');", "");
		    	} else {
	    			MainMenu.mainDbManager.queryDB("UPDATE event SET"
		        			+ " id = " + id + ", name = '" + nameInput + "', "
		        			+ "description = '" + descriptionInput + "' WHERE id = " + id + ";", "");
	    			MainMenu.mainDbManager.queryDB("UPDATE system_file_read_event SET"
		        			+ " id = " + id + ", path = '" + filePath + "', "
		        			+ "content = '" + content + "' WHERE id = " + id + ";", "");
	    		}
	    	} else { 
	    		logField.setText("name or description field is empty");
	    	} 
		}
		back();
	}
}
