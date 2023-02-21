package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//Class that shows the given Stage and sets a icon and title for all windows
public class BaseGUI extends Application{
	HomescreenScene homeScreen = new HomescreenScene();
	
	//Method that starts the given Stage
	@Override
	public void start(Stage window) throws Exception {
		
		//Adds the icon for the given Stage
		try {
			window.getIcons().add(new Image(getClass().getResourceAsStream("/resources/icon.png")));
		} catch(Exception e) {
			e.getMessage();
		}

		//Creates the Scene object and sets the title for the given Stage
		Scene scene = new Scene(homeScreen.homeScene(window));
		window.setTitle("Calvin Kleijn (2186254), Guus van Damme (2182402), Matthijs Feringa (2185220), Timothy Borghouts (2182610)");
		
		//Shows the created Scene and sets it to fullscreen
		window.setScene(scene);
		window.setMaximized(true);
		window.show();
	}
}
