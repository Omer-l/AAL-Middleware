package gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.BufferedReader;

import javax.swing.plaf.metal.MetalBorders.TextFieldBorder;
import dao.DbXMLParser;
import dao.MySqlConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
    private Button testButton = new Button("Test");
    private Button saveButton = new Button("Save");
    private Button runFileMethodButton = new Button("Run");
    private Button readFileMethodButton = new Button("Read");
    private Button writeFileMethodButton = new Button("Write");
    private String query;
    private File selectedFile;
    private TextField valueField = new TextField();
    private String operation = "run"; //run, read, or write
    
	public AddFileEvent(Window prevWindow, String operation) {
		super(prevWindow);
		this.operation = operation;
	}
    
	public AddFileEvent() {
		
	}

	public void open() {
		MainMenu.clearMainBox();
		MainMenu.changeTitle("Add File Event");
        Button button1 = new Button("Back");
        button1.setOnAction(event -> { back(); });
        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty());
        //column1urations
        VBox column1VBox1 = new VBox(2);
        Text column1Header = new Text("Description");
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
        
        switch(operation) {
	        case "run": MainMenu.setActive(runFileMethodButton); break;
	        case "read": MainMenu.setActive(runFileMethodButton); break;
	        case "write": MainMenu.setActive(runFileMethodButton); break;
        }
        column1ButtonBar1.getButtons().addAll(runFileMethodButton, readFileMethodButton, writeFileMethodButton);
        column1ButtonBar1HBox.getChildren().add(column1ButtonBar1);
//        column1ButtonBar1
        VBox column1VBox4 = new VBox(10);
        
        ButtonBar column1ButtonBar2 = new ButtonBar();
        testButton.setOnAction(event -> { processTestButton("run"); });
        saveButton.setDisable(true);
    	saveButton.setOnAction(event -> {
        	processSaveButton(column1HBox1VBox2TextField.getText(), column1HBox3VBox2TextField.getText(), column1HBox2VBox2TextField.getText());
    	});
        column1ButtonBar2.getButtons().addAll(testButton, saveButton);
       
        myStyles.createLogField(logField, mainVBox1);
        
        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header, column1ButtonBar1HBox);
        openRunForm(column1VBox3);
        column1VBox4.getChildren().addAll(column1ButtonBar2, myStyles.column1HBox9);
        
        mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, column1VBox3, column1VBox4);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);
	}


	private void openRunForm(VBox column1VBox3) {
        HBox column1Hbox4 = new HBox();
        VBox column1Hbox4VBox1 = new VBox();
        column1Hbox4VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        column1Hbox4VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1Hbox4VBox1Header = new Text("Java File To Run ");
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
        Text column1HBox7VBox1Header = new Text("Arguments");
        VBox column1HBox7VBox2 = new VBox();
        column1HBox7VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Menu menu4 = new Menu();
        column1HBox7VBox1.getChildren().addAll(column1HBox7VBox1Header);
        column1HBox7VBox2.getChildren().addAll(valueField);
        column1HBox7.getChildren().addAll(column1HBox7VBox1, column1HBox7VBox2);

        column1VBox3.getChildren().addAll(column1Hbox4, column1HBox7);
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


	private void openReadForm(VBox column1vBox2) {
		
	}

	private void openWriteForm(VBox column1vBox2) {
		
	}


	private void processTestButton(String operation) {
		switch(operation) {
			case "run":

		        try {
		        	String args = valueField.getText();
		            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "java " + args, selectedFile.getAbsolutePath());
		            Process process = processBuilder.start();

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
				break;
			case "write":
				break;
		}
	}

	private void processSaveButton(String uniqueIdInput, String nameInput, String descriptionInput) {
		String filePath = selectedFile.getAbsolutePath().replaceAll("\\\\", "/");
		System.out.println(filePath);
		System.out.println("WRITING");
		if(MainMenu.isActive(runFileMethodButton)) {
	    	String arguments = valueField.getText();
	    	System.out.println("WROTE");
	    	boolean emptyField = uniqueIdInput.isEmpty() || nameInput.isEmpty() || descriptionInput.isEmpty();
	    	if(!emptyField) {
	    		MainMenu.mainDbManager.queryDB("INSERT INTO event VALUES ('" + uniqueIdInput + "', '" + nameInput + "', '" + descriptionInput + "');", "");
	        	MainMenu.mainDbManager.queryDB("INSERT INTO system_file_run_event VALUES ('" + uniqueIdInput + "', '" + filePath + "', '" + arguments + "');", "");
	    	} else {
	    		logField.setText("unique id, name or description field is empty");
	    	}
		}
		back();
	}
}
