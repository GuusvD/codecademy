package GUI;

import database.CourseSQL;
import domain.Course;
import domain.Level;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//Class that creates the Course Update scene
public class CourseUpdateScene extends domain.Validation{

    private CourseSQL sql = new CourseSQL();

	//Method that creates the Course Update scene with the information from the given attribute
    public Parent courseUpdateScene(Stage window, Course old_course) {
        CourseOverviewScene courseOverviewScene = new CourseOverviewScene();

        //Adds the Background image
        Image image = new Image("resources/backgroundImage.jpg");
        ImageView imageView = new ImageView(image);
        Group root = new Group();
        root.getChildren().addAll(imageView);

        //Adds the Button to go back to the homeScene.
        Button backButton = new Button("Back");
        backButton.setPrefSize(80, 37);
        backButton.setOnAction((event) -> {
            window.getScene().setRoot(courseOverviewScene.courseOverviewScene(window));
        });

        //Creates the labels and textareas through which the user can input information
        Label nameLabel = new Label("CourseName: ");
		TextArea nameTextArea = new TextArea();
        nameTextArea.setText(old_course.getName());
		nameTextArea.setPrefHeight(1.0);
		
		Label topicLabel = new Label("Topic: ");
		TextArea topicTextArea = new TextArea();
        topicTextArea.setText(old_course.getTopic());
		topicTextArea.setPrefHeight(1.0);
		
		Label introductionLabel = new Label("Introduction: ");
		TextArea introductionTextArea = new TextArea();
        introductionTextArea.setText(String.valueOf(old_course.getIntroduction()));
		introductionTextArea.setPrefHeight(1.0);

        Label levelLabel = new Label("Level: ");
		ComboBox<Level>cbxLevel = new ComboBox<>();
        cbxLevel.getItems().setAll(Level.values());
        Level level = Level.valueOf(old_course.getLevel());
        cbxLevel.getSelectionModel().select(level);
		cbxLevel.setPrefHeight(1.0);

		//Adds a Button to update a Course
        Button updateCourse = new Button("Update course");
		updateCourse.setPrefSize(120, 40);

		//Creates a HBox and adds the created Button to it
		HBox buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(updateCourse);

		//Creates a GridPane
        GridPane grid = new GridPane();
		grid.setPadding(new Insets(40, 0, 0, 0));
		grid.setHgap(5);
		grid.setVgap(2);

		//Adds an setOnAction event and a style to the created Button
		updateCourse.setOnAction((event) -> {
			boolean validation = true;
			if (fieldIsEmpty(nameTextArea.getText())) {
				validation = false;
				Label errorText = new Label("Text field isn't filled in");
				errorText.setId("errorLabel");
				grid.add(errorText, 1, 1, 1, 1);
			}
			if (fieldIsEmpty(topicTextArea.getText())) {
				validation = false;
				Label errorText = new Label("Text field isn't filled in");
				errorText.setId("errorLabel");
				grid.add(errorText, 1, 3, 1, 1);
			} 
			if (fieldIsEmpty(introductionTextArea.getText())) {
				validation = false;
				Label errorText = new Label("Text field isn't filled in");
				errorText.setId("errorLabel");
				grid.add(errorText, 1, 5, 1, 1);
			}

			if(validation){
				Course course = new Course(nameTextArea.getText(), topicTextArea.getText(), introductionTextArea.getText(), cbxLevel.getSelectionModel().getSelectedItem().name());
				sql.updateCourse(course, old_course);
				window.getScene().setRoot(courseOverviewScene.courseOverviewScene(window));
			}
		});

		//Sets the position of the different elements that are added to the GridPane
		grid.add(nameLabel, 0, 0 , 1, 1);
		grid.add(nameTextArea, 1, 0, 1, 1);
		grid.add(topicLabel, 0, 2 , 1, 1);
		grid.add(topicTextArea, 1, 2, 1, 1);
		grid.add(introductionLabel, 0, 4, 1, 1);
		grid.add(introductionTextArea, 1, 4, 1, 1);
		grid.add(levelLabel, 0, 6, 1, 1);
		grid.add(cbxLevel, 1, 6, 1, 1);
		grid.add(buttonHBox, 0, 8, 1, 1);

		//Creates a BorderPane and adds elements to it
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.getChildren().add(imageView);
        pane.setTop(backButton);
        pane.setCenter(grid);

		//Sets the path to the Stylesheet to be used by the BorderPane
		pane.getStylesheets().add("/resources/styleSheet.css");

		//Returns the BorderPane
        return pane;
    }
}
