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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
    private ObservableList<String> whenItemsList;
    private ObservableList<String> thenItemsList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	//Input rule title etc
        TextField ruleIDField = new TextField();
        ruleIDField.setText("9c49fd1289");
        ruleIDField.setEditable(false);
        ruleIDField.setStyle("-fx-text-fill: gray;");
        TextField ruleTitleField = new TextField();
        TextField ruleDescriptionField = new TextField();

        Label ruleIDLabel = new Label("ID:");
        Label ruleTitleLabel = new Label("Title:");
        Label ruleDescriptionLabel = new Label("Description:");

        VBox ruleDetailsVBox = new VBox();
        ruleDetailsVBox.getChildren().addAll(
        		ruleIDLabel, ruleIDField,
        		ruleTitleLabel, ruleTitleField,
        		ruleDescriptionLabel, ruleDescriptionField);
        //When
        Text whenTitleText = new Text("When");
        whenTitleText.setFont(Font.font("Arial", 24));
        whenTitleText.setStyle("-fx-font-weight: bold;");
        whenItemsList = FXCollections.observableArrayList();

        ListView<String> whenListView = new ListView<String>(whenItemsList);

        TextField whenItemTextField = new TextField();
        Button whenAddButton = new Button("Add");
        whenAddButton.setOnAction(event -> openWhenAddWindow(whenItemTextField.getText()));

        Button whenRemoveButton = new Button("Remove");
        whenRemoveButton.setOnAction(event -> removeItem(whenListView.getSelectionModel().getSelectedItem(), whenItemsList));

        //Then
        Text thenTitleText = new Text("Then");
        thenTitleText.setFont(Font.font("Arial", 24));
        thenTitleText.setStyle("-fx-font-weight: bold;");
        thenItemsList = FXCollections.observableArrayList();

        ListView<String> thenListView = new ListView<>(thenItemsList);

        TextField thenItemTextField = new TextField();
        Button thenAddButton = new Button("Add");
        thenAddButton.setOnAction(event -> addItem(thenItemTextField.getText(), thenItemsList));

        Button thenRemoveButton = new Button("Remove");
        thenRemoveButton.setOnAction(event -> removeItem(thenListView.getSelectionModel().getSelectedItem(), thenItemsList));

        VBox root = new VBox(10, ruleDetailsVBox, 
        		whenTitleText, whenListView, whenItemTextField, whenAddButton, whenRemoveButton,
        		thenTitleText, thenListView, thenItemTextField, thenAddButton, thenRemoveButton);
        root.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(root, 900, 1000));
        primaryStage.setTitle("Add New Rule");
        primaryStage.show();
    }
    
    public static void openWhenAddWindow(String item) {
    	Trigger t = new Trigger();
    	t.openWhenWindow();
//    	addItem(item, whenItemsList)
    }

    private void addItem(String item, ObservableList<String> itemsList) {
    	//open when window
        if (!item.isEmpty()) {
        	itemsList.add(item);
        }
    }

    private void removeItem(String item, ObservableList<String> itemsList) {
        if (item != null) {
        	itemsList.remove(item);
        }
    }
}
