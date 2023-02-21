package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Class that creates the Homescreen scene
public class HomescreenScene{
	StudentOverviewScene studentOverviewScene = new StudentOverviewScene();
	CourseOverviewScene courseOverviewScene = new CourseOverviewScene();
	StatisticOverviewScene statisticOverviewScene = new StatisticOverviewScene();
	
	//Method that creates the Homescreen scene with the given Stage
	public Parent homeScene(Stage window) {
		
		//Adds the Background image
		Image image = new Image("resources/backgroundImage.jpg");
		ImageView imageViewBackground = new ImageView(image);
		Group root = new Group();
		root.getChildren().addAll(imageViewBackground);
		
		//Adds the first button which leads to the studentOverviewScene
		Button studentButton = new Button("Students");
		studentButton.setPrefSize(80, 37);
		studentButton.setOnAction((event) -> {
			window.getScene().setRoot(studentOverviewScene.studentOverviewScene(window));
		});

		//Adds the second button which leads to courseOverviewScene
		Button courseButton = new Button("Courses");
		courseButton.setPrefSize(80, 37);
		courseButton.setOnAction((event) -> {
			window.getScene().setRoot(courseOverviewScene.courseOverviewScene(window));
		});

		//Adds the third button which leads to statisticOverviewScene
		Button statisticButton = new Button("Statistics");
		statisticButton.setPrefSize(80, 37);
		statisticButton.setOnAction((event) -> {
			window.getScene().setRoot(statisticOverviewScene.statisticOverviewScene(window));
		});

		//Creates a Hbox and adds the three buttons created above
		HBox menu = new HBox(studentButton, courseButton, statisticButton);
		menu.setSpacing(10);
			
		//Title in the middle of the homescreen that welcomes the user
		Label firstTitelLabel = new Label("Welcome to the");
		firstTitelLabel.setId("firstTitelLabel");
		Label secondTitelLabel = new Label("Codecademy Application");
		secondTitelLabel.setId("secondTitelLabel");

		//Creates a Vbox with the two title labels
		VBox front = new VBox(firstTitelLabel, secondTitelLabel);
		front.setSpacing(5);
		front.setAlignment(Pos.BASELINE_CENTER);
		front.setPadding(new Insets(50, 0, 0, 0));
		
		//Creates a BorderPane and adds elements to it
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(15, 15, 15, 15));
		pane.getChildren().add(imageViewBackground);
		pane.setTop(menu);
		pane.setCenter(front);

		//Sets the path to the Stylesheet to be used by the BorderPane
		pane.getStylesheets().add("/resources/styleSheet.css");
		
		//Returns the BorderPane
		return pane;
	}
}
