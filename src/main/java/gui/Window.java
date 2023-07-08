package gui;

import javafx.scene.control.Button;

public abstract class Window {
	public Window prevWindow;
	
	public Window(Window prevWindow) {
		this.prevWindow = prevWindow;
	}
	
	public Window() {
		
	}

	public Window back() {
    	MainMenu.clearMainBox();
		if(prevWindow == null) {
			new MainMenu().open();
			return null;
		} else {
			prevWindow.open();
			return prevWindow;
		}
	}
	
	public void open() {};
}
