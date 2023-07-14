package gui;

import java.io.File;

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
    private String query;
    private File selectedFile;
    private TextField valueField = new TextField();
    
	public AddFileEvent(Window prevWindow) {
		super(prevWindow);
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
        Button runFileMethodButton = new Button("Run");
        Button readFileMethodButton = new Button("Read");
        Button writeFileMethodButton = new Button("Write");
        
        runFileMethodButton.setOnAction(event -> { 
        	MainMenu.setActive(runFileMethodButton);
        	MainMenu.deactivate(readFileMethodButton);
        	MainMenu.deactivate(writeFileMethodButton);
    		column1VBox3.getChildren().clear();
        	openRunForm(column1VBox3);
        });
        readFileMethodButton.setOnAction(event -> { 
        	MainMenu.setActive(readFileMethodButton); 
        	MainMenu.deactivate(runFileMethodButton);
        	MainMenu.deactivate(writeFileMethodButton);
    		column1VBox3.getChildren().clear();
        	openReadForm(column1VBox3);
        });
        writeFileMethodButton.setOnAction(event -> { 
        	MainMenu.setActive(writeFileMethodButton); 
        	MainMenu.deactivate(readFileMethodButton);
        	MainMenu.deactivate(runFileMethodButton);
    		column1VBox3.getChildren().clear();
        	openWriteForm(column1VBox3);
        	});
        column1ButtonBar1.getButtons().addAll(runFileMethodButton, readFileMethodButton, writeFileMethodButton);
        column1ButtonBar1HBox.getChildren().add(column1ButtonBar1);
//        column1ButtonBar1
        VBox column1VBox4 = new VBox(10);
        
        ButtonBar column1ButtonBar2 = new ButtonBar();
        testButton.setOnAction(event -> { processTestQuery("run"); });
        saveButton.setDisable(true);
        saveButton.setOnAction(event -> {  });
        column1ButtonBar2.getButtons().addAll(testButton, saveButton);
        HBox column1HBox9 = new HBox();
        Text logText = new Text("LOG: ");
        logField.prefWidthProperty().bind(mainVBox1.widthProperty().multiply(0.9));
        logField.prefHeightProperty().bind(mainVBox1.heightProperty().multiply(0.1));
        logField.setEditable(false);
        column1HBox9.getChildren().addAll(logText, logField);
        
        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header, column1ButtonBar1HBox);
        openRunForm(column1VBox3);
        column1VBox4.getChildren().addAll(column1ButtonBar2, column1HBox9);
        
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


	private void processTestQuery(String operation) {
		switch(operation) {
			case "run":
				// Set the path to your Java file
		        String filePath = selectedFile.getAbsolutePath();

		        // Create a JavaCompiler object
		        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		        // Compile the Java file
		        int compilationResult = compiler.run(null, null, null, filePath);

		        // Check if compilation was successful
		        if (compilationResult == 0) {
		            try {
		                // Extract the class name from the file path
		                String className = selectedFile.getName().replace(".java", "");

		                // Load the compiled class dynamically
		                Class<?> loadedClass = Class.forName(className);

		                // Execute the main method of the loaded class
		                loadedClass.getDeclaredMethod("main", String[].class)
		                        .invoke(null, (Object) logField.getText());
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
				break;
			case "read":
				break;
			case "write":
				break;
		}
	}
}
