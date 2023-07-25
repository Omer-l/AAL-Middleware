package gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class myStyles{
	public static HBox column1HBox9 = new HBox();
	
	public myStyles() {
		// TODO Auto-generated constructor stub
	}
	
	public static void createLogField(TextField logField, VBox mainVBox1) {
	 
     Text logText = new Text("LOG: ");
     logField.prefWidthProperty().bind(mainVBox1.widthProperty().multiply(0.9));
     logField.prefHeightProperty().bind(mainVBox1.heightProperty().multiply(0.1));
     logField.setEditable(false);
     column1HBox9.getChildren().addAll(logText,logField);
	}

}
