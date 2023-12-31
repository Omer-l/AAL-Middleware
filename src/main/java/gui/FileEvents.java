package gui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
public class FileEvents extends Window {
	Map<String, Object> editData = new HashMap<String, Object>();

	
	public FileEvents(Window prevWindow) {
		super(prevWindow);
	}
	
	public FileEvents() {
		
	}
	

	public void open() {
		MainMenu.clearMainBox();
    	MainMenu.changeTitle("File Events");
        Button button1 = new Button("Back");
        button1.setStyle(MainMenu.BACK_BUTTON_STYLE);

        button1.setOnAction(event -> { back(); });
        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(3));
        //run events
        VBox column1VBox = new VBox(2);
        Text column1Header = new Text("Run Events");
        column1Header.setStyle(MainMenu.HEADER_1_STYLE);
        column1VBox.getChildren().addAll(column1Header);
        MyStyles.getEvents("SELECT * FROM system_file_run_event INNER JOIN event ON system_file_run_event.id = event.id;", column1VBox, this);
        int runEventsLastIndex = column1VBox.getChildren().size() - 1;
        column1VBox.getChildren().get(runEventsLastIndex).setOnMouseClicked(event -> {new AddFileEvent(this, "run").open();});
        mainVBox1.getChildren().addAll(column1VBox);
        //MIDDLE SIDE MAIN
        VBox mainVBox2 = new VBox(2);
        mainVBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(3));
        //read events
        VBox column2VBox = new VBox(2);
        Text column2Header = new Text("Read Events");
        column2Header.setStyle(MainMenu.HEADER_1_STYLE);
        column2VBox.getChildren().addAll(column2Header);
        MyStyles.getEvents("SELECT * FROM system_file_read_event INNER JOIN event ON system_file_read_event.id = event.id;", column2VBox, this);
        int readEventsLastIndex = column2VBox.getChildren().size() - 1;
        column2VBox.getChildren().get(readEventsLastIndex).setOnMouseClicked(event -> {new AddFileEvent(this, "read").open();});
        mainVBox2.getChildren().addAll(column2VBox);
        //RIGHT SIDE MAIN
        VBox mainVBox3 = new VBox(2);
        mainVBox3.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(3));
        //write events
        VBox column3VBox = new VBox(2);
        Text column3Header = new Text("Write Events");
        column3Header.setStyle(MainMenu.HEADER_1_STYLE);
        column3VBox.getChildren().add(column3Header);
        MyStyles.getEvents("SELECT * FROM system_file_write_event INNER JOIN event ON system_file_write_event.id = event.id;", column3VBox, this);
        int writeEventsLastIndex = column3VBox.getChildren().size() - 1;
        column3VBox.getChildren().get(writeEventsLastIndex).setOnMouseClicked(event -> { new AddFileEvent(this, "write").open(); });
        mainVBox3.getChildren().addAll(column3VBox);
    	//MainMenu.addHoverInteraction(new VBox[] {(VBox) column1VBox.getChildren().get(runEventsLastIndex), (VBox) column2VBox.getChildren().get(readEventsLastIndex), (VBox) column3VBox.getChildren().get(writeEventsLastIndex)}, "yellow", "darkgray");
        MainMenu.mainHBox.getChildren().addAll(mainVBox1, mainVBox2, mainVBox3);
	}
	
	public void loadData(String uniqueId) {
		editData = MainMenu.mainDbManager.queryDB("SELECT * FROM system_file_run_event JOIN event ON database_read_event.id = event.id", "select").get(0);
	}
	
	
	
}