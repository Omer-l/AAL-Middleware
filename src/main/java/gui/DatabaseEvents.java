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
        MainMenu.menuBarHBox.setAlignment(Pos.TOP_LEFT); // button on the left
        MainMenu.menuBarHBox.getChildren().addAll(button1);
        //LEFT SIDE MAIN
        VBox mainVBox1 = new VBox(2);
        mainVBox1.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        //Configurations
        VBox configVBox = new VBox(2);
        Text configHeader = new Text("Read Events");
        configHeader.setStyle(MainMenu.HEADER_1_STYLE);

        VBox configVBox1 = new VBox();
        configVBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text configVBox1Header = new Text("Read Record");
        configVBox1Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text configVBox1Details = new Text("Read BLE database");
        configVBox1.getChildren().addAll(configVBox1Header, configVBox1Details);

        VBox configVBox2 = new VBox();
        configVBox2.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text configVBox2Header = new Text("File");
        configVBox2Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text configVBox2Details = new Text("What to read/write from files");
        configVBox2.getChildren().addAll(configVBox2Header, configVBox2Details);

        VBox configVBox3 = new VBox();
        configVBox3.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text configVBox3Header = new Text("Schedules");
        configVBox3Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text configVBox3Details = new Text("Schedules to keep track of");
        configVBox3.getChildren().addAll(configVBox3Header, configVBox3Details);

        VBox configVBox4 = new VBox();
        configVBox4.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text configVBox4Header = new Text("Hardware & Services");
        configVBox4Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text configVBox4Details = new Text("Define, connect and use API of hardware and online services");
        configVBox4.getChildren().addAll(configVBox4Header, configVBox4Details);
        configVBox.getChildren().addAll(configHeader, configVBox1, configVBox2, configVBox3, configVBox4);


        //Automation
       /** VBox automationVBox = new VBox(2);
        Text automationHeader = new Text("Automation");
        automationHeader.setStyle(Main.HEADER_1_STYLE);
        VBox automationVBox1 = new VBox();
        automationVBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text automationVBox1Header = new Text("Rules");
        automationVBox1Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text automationVBox1Details = new Text("Rules in system");
        automationVBox1.getChildren().addAll(automationVBox1Header, automationVBox1Details);
        automationVBox.getChildren().addAll(automationHeader, automationVBox1);**/

        mainVBox1.getChildren().addAll(configVBox);
        //RIGHT SIDE MAIN
        VBox mainVBox2 = new VBox(2);
        mainVBox2.prefWidthProperty().bind(MainMenu.root.widthProperty().divide(2));
        //write events
        VBox settingsVBox = new VBox(2);
        Text settingsHeader = new Text("Write Events");
        settingsHeader.setStyle(MainMenu.HEADER_1_STYLE);

        VBox settingsVBox1 = new VBox(2);
        settingsVBox1.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text settingsVBox1Header = new Text("Write incoming BLE");
        settingsVBox1Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text settingsVBox1Details = new Text("Writes to MReasoner DB about BLE Event");
        settingsVBox1.getChildren().addAll(settingsVBox1Header, settingsVBox1Details);

        VBox settingsVBox2 = new VBox();
        settingsVBox2.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text settingsVBox2Header = new Text("File");
        settingsVBox2Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text settingsVBox2Details = new Text("What to read/write from files");
        settingsVBox2.getChildren().addAll(settingsVBox2Header, settingsVBox2Details);


        VBox settingsVBox3 = new VBox();
        settingsVBox3.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text settingsVBox3Header = new Text("Schedules");
        settingsVBox3Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text settingsVBox3Details = new Text("Schedules to keep track of");
        settingsVBox3.getChildren().addAll(settingsVBox3Header, settingsVBox3Details);

        VBox settingsVBox4 = new VBox();
        settingsVBox4.setStyle(MainMenu.MENU_BUTTON_STYLE);
        Text settingsVBox4Header = new Text("Hardware and Services");
        settingsVBox4Header.setStyle(MainMenu.HEADER_2_STYLE);
        Text settingsVBox4Details = new Text("Define, connect and use API of hardware and online services");
        settingsVBox4.getChildren().addAll(settingsVBox4Header, settingsVBox4Details);

        settingsVBox.getChildren().addAll(settingsHeader, settingsVBox1,settingsVBox2,settingsVBox3,settingsVBox4);
        mainVBox2.getChildren().addAll(settingsVBox);

        MainMenu.mainHBox.getChildren().addAll(mainVBox1, mainVBox2);
	}
}
