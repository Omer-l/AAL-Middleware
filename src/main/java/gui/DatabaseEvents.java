package gui;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
public class DatabaseEvents {

	public void open() {
        Button button1 = new Button("Back");
        button1.setOnAction(event -> {MainMenu.goToMainMenu(); });
        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        //column1urations
        VBox column1VBox = new VBox(2);
        Text column1Header = new Text("Read Events");
        column1Header.setStyle(MainMenu.HEADER_1_STYLE);

        VBox column1VBox1 = new VBox();
        column1VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column1VBox1Header = new Text("Read Record");
        column1VBox1Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column1VBox1Details = new Text("Read BLE database");
        column1VBox1.getChildren().addAll(column1VBox1Header, column1VBox1Details);

        VBox column1VBox2 = new VBox();
        column1VBox2.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column1VBox2Header = new Text("File");
        column1VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column1VBox2Details = new Text("What to read/write from files");
        column1VBox2.getChildren().addAll(column1VBox2Header, column1VBox2Details);

        VBox column1VBox3 = new VBox();
        column1VBox3.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column1VBox3Header = new Text("Schedules");
        column1VBox3Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column1VBox3Details = new Text("Schedules to keep track of");
        column1VBox3.getChildren().addAll(column1VBox3Header, column1VBox3Details);

        VBox column1VBox4 = new VBox();
        column1VBox4.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column1VBox4Header = new Text("Hardware & Services");
        column1VBox4Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column1VBox4Details = new Text("Define, connect and use API of hardware and online services");
        column1VBox4.getChildren().addAll(column1VBox4Header, column1VBox4Details);
        column1VBox.getChildren().addAll(column1Header, column1VBox1, column1VBox2, column1VBox3, column1VBox4);

        mainVBox1.getChildren().addAll(column1VBox);
        //RIGHT SIDE MAIN
        VBox mainVBox2 = new VBox(2);
        mainVBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        //write events
        VBox column2VBox = new VBox(2);
        Text column2Header = new Text("Write Events");
        column2Header.setStyle(MainMenu.HEADER_1_STYLE);

        VBox column2VBox1 = new VBox(2);
        column2VBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column2VBox1Header = new Text("Write incoming BLE");
        column2VBox1Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column2VBox1Details = new Text("Writes to MReasoner DB about BLE Event");
        column2VBox1.getChildren().addAll(column2VBox1Header, column2VBox1Details);

        VBox column2VBox2 = new VBox();
        column2VBox2.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column2VBox2Header = new Text("File");
        column2VBox2Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column2VBox2Details = new Text("What to read/write from files");
        column2VBox2.getChildren().addAll(column2VBox2Header, column2VBox2Details);


        VBox column2VBox3 = new VBox();
        column2VBox3.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column2VBox3Header = new Text("Schedules");
        column2VBox3Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column2VBox3Details = new Text("Schedules to keep track of");
        column2VBox3.getChildren().addAll(column2VBox3Header, column2VBox3Details);

        VBox column2VBox4 = new VBox();
        column2VBox4.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text column2VBox4Header = new Text("Hardware and Services");
        column2VBox4Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text column2VBox4Details = new Text("Define, connect and use API of hardware and online services");
        column2VBox4.getChildren().addAll(column2VBox4Header, column2VBox4Details);

        column2VBox.getChildren().addAll(column2Header, column2VBox1,column2VBox2,column2VBox3,column2VBox4);
        mainVBox2.getChildren().addAll(column2VBox);

    	MainMenu.addHoverInteraction(new VBox[] {column1VBox1, column1VBox2, column1VBox3, column1VBox4, column2VBox1, column2VBox2, column2VBox3, column2VBox4}, "white", "darkgray");
        MainMenu.mainHBox.getChildren().addAll(mainVBox1, mainVBox2);
	}
}
