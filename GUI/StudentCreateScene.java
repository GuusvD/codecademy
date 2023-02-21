package GUI;

import database.StudentSQL;
import domain.Student;
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
import javafx.stage.Stage;

//Class that creates the Student Create scene
public class StudentCreateScene extends domain.Validation{
    private StudentSQL sql = new StudentSQL();

	//Method that creates the Student Create scene with the given Stage
    public Parent studentCreateScene(Stage window) {
        StudentOverviewScene studentOverviewScene = new StudentOverviewScene();

		//Adds the Background image
		Image image = new Image("resources/backgroundImage.jpg");
		ImageView imageView = new ImageView(image);
		Group root = new Group();
		root.getChildren().addAll(imageView);

        //Adds the Button to go back to the StudentOverviewScene.
        Button backButton = new Button("Back");
        backButton.setPrefSize(80, 37);
        backButton.setOnAction((event) -> {
            window.getScene().setRoot(studentOverviewScene.studentOverviewScene(window));
        });
		
        //Adds all labels, input fields and error messages
		Label emailLabel = new Label("Email: ");
		TextArea emailTextArea = new TextArea();
		emailTextArea.setPrefHeight(1.0);
		
		Label nameLabel = new Label("Name: ");
		TextArea nameTextArea = new TextArea();
		nameTextArea.setPrefHeight(1.0);
		
		Label birthDayLabel = new Label("Birthday: ");
		TextArea birthDayTextArea = new TextArea();
		birthDayTextArea.setPrefHeight(1.0);

		Label birthMonthLabel = new Label("Birthmonth: ");
		TextArea birthMonthTextArea = new TextArea();
		birthMonthTextArea.setPrefHeight(1.0);

		Label birthYearLabel = new Label("Birthyear: ");
		TextArea birthYearTextArea = new TextArea();
		birthYearTextArea.setPrefHeight(1.0);
		
		Label genderLabel = new Label("Gender: ");
		ComboBox<String> cbxGender = new ComboBox<>();
		cbxGender.getItems().addAll("M", "F");
		cbxGender.setPrefHeight(1.0);
		
		Label streetLabel = new Label("Street: ");
		TextArea streetTextArea = new TextArea();
		streetTextArea.setPrefHeight(1.0);

        Label houseNumberLabel = new Label("House number: ");
		TextArea houseNumberTextArea = new TextArea();
		houseNumberTextArea.setPrefHeight(1.0);

        Label houseNumberAdditionLabel = new Label("House number addition: ");
		TextArea houseNumberAdditionTextArea = new TextArea();
		houseNumberAdditionTextArea.setPrefHeight(1.0);

        Label postalCodeLabel = new Label("Postal code: ");
		TextArea postalCodeTextArea = new TextArea();
		postalCodeTextArea.setPrefHeight(1.0);

		Label residenceLabel = new Label("Residence: ");
		TextArea residenceTextArea = new TextArea();
		residenceTextArea.setPrefHeight(1.0);
		
		Label countryLabel = new Label("Country: ");
		TextArea countryTextArea = new TextArea();
		countryTextArea.setPrefHeight(1.0);

		//Adds a Button to create a Student with all the information in the textareas
		Button createStudentButton = new Button("Add student");
		createStudentButton.setPrefSize(120, 40);
		createStudentButton.setId("createStudentButton");

		//Creates a GridPane
        GridPane grid = new GridPane();
		grid.setPadding(new Insets(40, 0, 0, 0));
		grid.setHgap(5);
		grid.setVgap(2);

		//Sets an setOnAction event for the created Button that gives error messages when wrong input is given in a textarea
		createStudentButton.setOnAction((event) -> {
			boolean validation = true;
			if(!checkEmail(emailTextArea.getText())) { 
				validation = false;
				Label errorText = new Label("email isn't valid");
				errorText.setId("errorLabel");
				grid.add(errorText, 1, 1, 1, 1);
			}
			if(fieldIsEmpty(birthDayTextArea.getText()) || fieldIsEmpty(birthMonthTextArea.getText()) || fieldIsEmpty(birthYearTextArea.getText())){
				validation = false;
				Label errorText = new Label("birthdate isn't valid");
				errorText.setId("errorLabel");	
				grid.add(errorText, 1, 5, 1, 1);
			} else if (!checkDate(Integer.parseInt(birthDayTextArea.getText()), Integer.parseInt(birthMonthTextArea.getText()), Integer.parseInt(birthYearTextArea.getText()))) {
				validation = false;
				Label errorText = new Label("birthdate isn't valid");
				errorText.setId("errorLabel");	
				grid.add(errorText, 1, 5, 1, 1);
			}
			try {
				checkPostalCode(postalCodeTextArea.getText());
			} catch (Exception e) {
				if(e instanceof IllegalArgumentException){
					validation = false;
					Label errorText = new Label("postal code isn't formatted right must be formatted like 4 digits one space 2 letters");
					errorText.setId("errorLabel");
					grid.add(errorText, 1, 19, 1, 1);
				}
			}

			if (cbxGender.getSelectionModel().isEmpty()) {
				validation = false;
				Label errorText = new Label("gender must be 'M' for male or 'F' for female");
				errorText.setId("errorLabel");
				grid.add(errorText, 1, 11, 1, 1);
			}
			if (fieldIsEmpty(nameTextArea.getText())) {
				validation = false;
				Label errorText = new Label("Text field isn't filled in");
				errorText.setId("errorLabel");
				grid.add(errorText, 1, 3, 1, 1);
			}
			if (fieldIsEmpty(streetTextArea.getText())) {
				validation = false;
				Label errorText = new Label("Text field isn't filled in");
				errorText.setId("errorLabel");
				grid.add(errorText, 1, 13, 1, 1);
			} 
			if (fieldIsEmpty(houseNumberTextArea.getText())) {
				validation = false;
				Label errorText = new Label("Text field isn't filled in");
				errorText.setId("errorLabel");
				grid.add(errorText, 1, 15, 1, 1);
			} 
			if (fieldIsEmpty(residenceTextArea.getText())) {
				validation = false;
				Label errorText = new Label("Text field isn't filled in");
				errorText.setId("errorLabel");
				grid.add(errorText, 1, 21, 1, 1);
			}
			if (fieldIsEmpty(countryTextArea.getText())) {
				validation = false;
				Label errorText = new Label("Text field isn't filled in");
				errorText.setId("errorLabel");
				grid.add(errorText, 1, 23, 1, 1);
			}
			
			if (validation) { 
				sql.createStudent(new Student(emailTextArea.getText(), nameTextArea.getText(), Integer.parseInt(birthDayTextArea.getText()), Integer.parseInt(birthMonthTextArea.getText()), Integer.parseInt(birthYearTextArea.getText()), cbxGender.getSelectionModel().getSelectedItem() , streetTextArea.getText(), houseNumberTextArea.getText(), houseNumberAdditionTextArea.getText(), postalCodeTextArea.getText(), residenceTextArea.getText(), countryTextArea.getText()));
				window.getScene().setRoot(studentOverviewScene.studentOverviewScene(window));
			}			
		});

		//Sets the position of the different elements that are added to the GridPane
		grid.add(emailLabel, 0, 0 , 1, 1);
		grid.add(emailTextArea, 1, 0, 1, 1);
		grid.add(nameLabel, 0, 2 , 1, 1);
		grid.add(nameTextArea, 1, 2, 1, 1);
		grid.add(birthDayLabel, 0, 4, 1, 1);
		grid.add(birthDayTextArea, 1, 4, 1, 1);
		grid.add(birthMonthLabel, 0, 6, 1, 1);
		grid.add(birthMonthTextArea, 1, 6, 1, 1);
		grid.add(birthYearLabel, 0, 8, 1, 1);
		grid.add(birthYearTextArea, 1, 8, 1, 1);
		grid.add(genderLabel, 0, 10 , 1, 1);
		grid.add(cbxGender, 1, 10 , 1, 1);
		grid.add(streetLabel, 0, 12, 1, 1);
		grid.add(streetTextArea, 1, 12 , 1, 1);
        grid.add(houseNumberLabel, 0, 14 , 1, 1);
		grid.add(houseNumberTextArea, 1, 14 , 1, 1);
        grid.add(houseNumberAdditionLabel, 0, 16 , 1, 1);
		grid.add(houseNumberAdditionTextArea, 1, 16 , 1, 1);
        grid.add(postalCodeLabel, 0, 18 , 1, 1);
		grid.add(postalCodeTextArea, 1, 18 , 1, 1);
		grid.add(residenceLabel, 0, 20 , 1, 1);
		grid.add(residenceTextArea, 1, 20 , 1, 1);
		grid.add(countryLabel, 0, 22, 1, 1);
		grid.add(countryTextArea, 1, 22, 1, 1);
		grid.add(createStudentButton, 1, 24, 1, 1);

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
