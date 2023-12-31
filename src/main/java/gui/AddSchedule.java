package gui;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import dao.DbXMLParser;
import dao.MySqlConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class AddSchedule extends Window {
	private Button testButton = new Button("Test");
    private Button saveButton = new Button("Save");
    private static final Pattern VALID_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");
    Map<String, Object> editData = new HashMap<String, Object>();
    DatePicker startDatePicker = new DatePicker();
    DatePicker endDatePicker = new DatePicker();
    TextField startTimeField = new TextField();
    TextField endTimeField = new TextField();
    Menu menu1 =  new Menu();
	private int id;
    
	public AddSchedule(Window prevWindow) {
		super(prevWindow);
	}

    
	public AddSchedule() {
		
	}
	
	public void loadData(int uniqueId, TextField uniqueIdField, TextField nameField, TextField descriptionField, 
			String startTime, String endTime, String repeat) {
		System.out.println(editData);
		this.id = uniqueId;
		uniqueIdField.setText(uniqueId + "");
		nameField.setText((String) editData.get("name"));
		descriptionField.setText((String) editData.get("description"));
		menu1.setText((String) editData.get("repetition"));
		startDatePicker.setValue(((LocalDateTime)editData.get("start_date_time")).toLocalDate());
	    endDatePicker.setValue(((LocalDateTime)editData.get("end_date_time")).toLocalDate());
	    startTimeField.setText(((LocalDateTime)editData.get("start_date_time")).toLocalTime().toString());
	    endTimeField.setText(((LocalDateTime)editData.get("end_date_time")).toLocalTime().toString());

		
		
	}

	public void open() {
		MainMenu.clearMainBox();
		MainMenu.changeTitle("Add Schedule Event");
		
	        
        Button button1 = new Button("Back");
        button1.setStyle("-fx-font: 15 arial; -fx-base: #b6e7c9");
        saveButton.setStyle("-fx-font: 15 arial ; -fx-base: #FFE4E1");
        testButton.setStyle("-fx-font: 15 arial ; -fx-base: #FFE4E1");

        button1.setOnAction(event -> { back(); });
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
        column1HBox1VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1HBox1VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1HBox1VBox1Header = new Text("UniqueID"); //TODO: delete this unique id field and make it so that the db auto increments this
        VBox column1HBox1VBox2 = new VBox();
        column1HBox1VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        TextField column1HBox1VBox2TextField = new TextField();
        column1HBox1VBox1.getChildren().addAll(column1HBox1VBox1Header);
        column1HBox1VBox2.getChildren().addAll(column1HBox1VBox2TextField);
        column1HBox1.getChildren().addAll(column1HBox1VBox1, column1HBox1VBox2);

        HBox column1HBox2 = new HBox();
        VBox column1HBox2VBox1 = new VBox();
        column1HBox2VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
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
        column1HBox3VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
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
        Text column1VBox2Header = new Text("Method");
        column1VBox2Header.setStyle(MainMenu.HEADER_1_STYLE);
        
        HBox column1Hbox4 = new HBox();
        VBox column1Hbox4VBox1 = new VBox();
        column1Hbox4VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1Hbox4VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1Hbox4VBox1Header = new Text("Start date and time");
        HBox column1Hbox4HBox2 = new HBox();
        column1Hbox4HBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(4));
        // Create a MenuItem
        MenuBar column1HBox4VBox2MenuBar = new MenuBar();
        //DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Select a date");
        column1Hbox4VBox1.getChildren().addAll(column1Hbox4VBox1Header);
        column1Hbox4HBox2.getChildren().addAll(startDatePicker, startTimeField);
        column1Hbox4.getChildren().addAll(column1Hbox4VBox1, column1Hbox4HBox2);
        

        HBox column1Hbox5 = new HBox();
        VBox column1Hbox5VBox1 = new VBox();
        column1Hbox5VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1Hbox5VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1Hbox5VBox1Header = new Text("End date and time");
        HBox column1Hbox5HBox2 = new HBox();
        column1Hbox5HBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(4));
        // Create a MenuItem
        MenuBar column1Hbox5VBox2MenuBar = new MenuBar();
       // DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("Select a date");
        column1Hbox5VBox1.getChildren().addAll(column1Hbox5VBox1Header);
        column1Hbox5HBox2.getChildren().addAll(endDatePicker, endTimeField);
        column1Hbox5.getChildren().addAll(column1Hbox5VBox1, column1Hbox5HBox2);
        
        HBox column1Hbox6 = new HBox();
        VBox column1Hbox6VBox1 = new VBox();
        column1Hbox6VBox1.setStyle(MainMenu.USER_INPUT_STYLE);
        column1Hbox6VBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        Text column1Hbox6VBox1Header = new Text("Repeat");
        VBox column1Hbox6VBox2 = new VBox();
        column1Hbox6VBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        // Create a MenuItem
        MenuBar column1Hbox6VBox2MenuBar = new MenuBar();
        // Create MenuItems for the dropdown menu
        MenuItem option1 = new MenuItem("Second");
        MenuItem option2 = new MenuItem("Minute");
        MenuItem option3 = new MenuItem("Hour");
        MenuItem option4 = new MenuItem("Day");
        MenuItem option5 = new MenuItem("Week");
        MenuItem option6 = new MenuItem("Month");
        menu1.getItems().addAll(option1, option2, option3, option4, option5, option6);
        column1Hbox6VBox2MenuBar.getMenus().add(menu1);
        column1Hbox6VBox1.getChildren().addAll(column1Hbox6VBox1Header);
        column1Hbox6VBox2.getChildren().addAll(column1Hbox6VBox2MenuBar);
        column1Hbox6.getChildren().addAll(column1Hbox6VBox1, column1Hbox6VBox2);
        for(MenuItem menuItem : menu1.getItems())
	        menuItem.setOnAction(event -> {
				menu1.setText(menuItem.getText());		
			});
		
        VBox column1VBox3 = new VBox(10);
        
        ButtonBar column1ButtonBar = new ButtonBar(); 
        //test the query
        testButton.setOnAction(event -> {
        	 
        	   // processTestQuery(column1HBox1VBox2TextField.getText(),column1HBox2VBox2TextField.getText(), column1HBox3VBox2TextField.getText(),
        		// (startDatePicker.getValue() + " " +  startTimeField.getText()),  (endDatePicker.getValue() + " " + endTimeField.getText()),menu1.getText()); 
        	    boolean isValid = validateInput(column1HBox1VBox2TextField.getText(),column1HBox2VBox2TextField.getText(), column1HBox3VBox2TextField.getText(),
                  		 (startDatePicker.getValue() + " " +  startTimeField.getText()),  (endDatePicker.getValue() + " " + endTimeField.getText()),menu1.getText());
        	    if (isValid) {
                    showAlert("Success", "Details are valid.");
                    saveButton.setDisable(false);
                } else {
                    showAlert("Error", "Invalid details. Please check your input.");
                    saveButton.setDisable(true);
                }
        	   
        });
        saveButton.setDisable(true);
        saveButton.setOnAction(event -> { processSaveButton(column1HBox1VBox2TextField.getText(),column1HBox2VBox2TextField.getText(), column1HBox3VBox2TextField.getText(),
       		 (startDatePicker.getValue() + " " +  startTimeField.getText()),  (endDatePicker.getValue() + " " + endTimeField.getText()),menu1.getText());});
        column1ButtonBar.getButtons().addAll(testButton, saveButton);
        HBox column1HBox9 = new HBox();
        
        
        column1VBox1.getChildren().addAll(column1Header, column1HBox1, column1HBox2, column1HBox3);
        column1VBox2.getChildren().addAll(column1VBox2Header, column1Hbox4, column1Hbox5, column1Hbox6);
        column1VBox3.getChildren().addAll(column1ButtonBar, column1HBox9);
        
        mainVBox1.getChildren().addAll(column1VBox1, column1VBox2, column1VBox3);
        MainMenu.mainHBox.getChildren().addAll(mainVBox1);
        
        if(!editData.isEmpty()) {
        	loadData((int) editData.get("id"),column1HBox1VBox2TextField, column1HBox2VBox2TextField,column1HBox3VBox2TextField,
        			(startDatePicker.getValue() + " " +  startTimeField.getText()),  (endDatePicker.getValue() + " " + endTimeField.getText())
        			,menu1.toString());
        }
        
	}


	


	private boolean validateInput(String uniqueId, String name, String description, String startDate, String endDate,
			String repeat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		LocalDateTime startDateString = LocalDateTime.parse(startDate,formatter);
        LocalDateTime endDateString = LocalDateTime.parse(endDate,formatter);
        
		if (uniqueId.isEmpty() || name.isEmpty() || description.isEmpty() || startDate == null || endDate == null || repeat.isEmpty()) {
            return false;
           }
		if (!VALID_ID_PATTERN.matcher(uniqueId).matches()) {
            return false; // Unique ID doesn't match the expected pattern
        }
		if(endDateString.isBefore(startDateString)) {
            showAlert("Error.","End date cannot be before start date. Please try again.");
			return false;
		}
		return true;
	}





	private void processSaveButton(String uniqueId, String name, String description, String startDate, String endDate, String repeat) {
		boolean emptyField = uniqueId.isEmpty() || name.isEmpty() || description.isEmpty();
			if(!emptyField) {
				if(editData.isEmpty()) {
					MainMenu.mainDbManager.queryDB("INSERT INTO event VALUES (null, '" + name + "', '" + description + "');", "");
		        	id = (int) MainMenu.mainDbManager.queryDB("SELECT * FROM event ORDER BY id  DESC LIMIT 1;", "select").get(0).get("id");
		        	MainMenu.mainDbManager.queryDB("INSERT INTO schedule VALUES (" + id + ", '" + startDate + "', '" + endDate + "', '" + repeat + "', '" + LocalDateTime.now() + "');", ""); 
				} else {
		        	MainMenu.mainDbManager.queryDB("UPDATE event SET"
		        			+ " id = '" + uniqueId + "', name = '" + name + "', "
		        			+ "description = '" + description + "' WHERE id = '" + id + "';", "");
		        	MainMenu.mainDbManager.queryDB("UPDATE schedule SET"
		        			+ " id = '" + uniqueId + "', start_date_time = '" + startDate + "', "
		        			+ "end_date_time = '" + endDate + "' WHERE id = '" + id + "';", "");
				}
			}
			back();
	}
	
	private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
