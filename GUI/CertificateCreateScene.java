package GUI;

import database.CertificateSQL;
import database.ExternalPersonSQL;
import domain.Certificate;
import domain.Course;
import domain.Registration;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//Class that creates the Certificate Create scene
public class CertificateCreateScene extends domain.Validation{
    private CertificateSQL sqlC = new CertificateSQL();
    private ExternalPersonSQL sqlE = new ExternalPersonSQL();

    //Method that creates the Certificate Create scene with the information from the given attributes
    public Parent certificateCreateScene(Stage window, Registration registration, Course course, Student current_student) {
        StudentCourseScene studentCourseScene = new StudentCourseScene();
		
		//Adds the background image
		Image image = new Image("resources/backgroundImage.jpg");
		ImageView imageView = new ImageView(image);
		Group root = new Group();
		root.getChildren().addAll(imageView);

        //Adds the Button to go back to the StudentOverviewScene.
        Button backButton = new Button("Back");
        backButton.setPrefSize(80, 37);
        backButton.setOnAction((event) -> {
            window.getScene().setRoot(studentCourseScene.studentCourseScene(window, registration, course, current_student));
        });

        //Creates the labels and textareas through which the user can input information
        Label gradeLabel = new Label("Grade: ");
		TextArea gradeTextArea = new TextArea();
        gradeTextArea.setPrefHeight(1.0);

        Label externalPersonLabel = new Label("External person: ");
        ComboBox<String>cbxExternalPerson = new ComboBox<>();
        cbxExternalPerson.getItems().setAll(sqlE.getEmployeeExternalPersons());
        cbxExternalPerson.setPrefHeight(1.0);

        //Adds the Button to create a certificate
        Button createCertificate = new Button("Create certificate");
		createCertificate.setPrefSize(120, 40);

        //Adds HBox and GridPane
        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(createCertificate);

        GridPane grid = new GridPane();
		grid.setPadding(new Insets(40, 0, 0, 0));
		grid.setHgap(5);
		grid.setVgap(2);

        //Adds an setOnAction event to the created Button
		createCertificate.setOnAction((event) -> {
            boolean validation = true;
            if (fieldIsEmpty(gradeTextArea.getText())) {
				validation = false;
				Label errorText = new Label("Text field isn't filled in");
                errorText.setId("errorLabel");
				grid.add(errorText, 1, 1, 1, 1);
			} else if (!checkGrade(Integer.parseInt(gradeTextArea.getText()))) {
                validation = false;
				Label errorText = new Label("The grade isn't valid, must be between the 1 and the 10");
                errorText.setId("errorLabel");
				grid.add(errorText, 1, 1, 1, 1);
            }

            if (cbxExternalPerson.getSelectionModel().isEmpty()) {
				validation = false;
				Label errorText = new Label("dropdown menu isn't filled in");
                errorText.setId("errorLabel");
				grid.add(errorText, 1, 3, 1, 1);
			}

            

            if(validation){
                //Making a label, for a error message that could occur if this  student already has a certificate for this course
                Label feedback = new Label("");
                feedback.setId("errorLabel");
                grid.add(feedback, 1, 4, 1, 1);

                Certificate certificate = new Certificate(Integer.valueOf(gradeTextArea.getText()), sqlE.findExternalPersonID(cbxExternalPerson.getSelectionModel().getSelectedItem()), current_student.getEmail(), course.getName());
                String outputCreate = sqlC.createCertificate(certificate);

                if(outputCreate.contains("You've already created a certificate")){
                    feedback.setText("Certificate can't be created, this student already has a certificate for this course");
                } else {
                    window.getScene().setRoot(studentCourseScene.studentCourseScene(window, registration, course, current_student));
                }
            }
		});

        //Sets the position of the different elements in the GridPane
        grid.add(gradeLabel, 0, 0 , 1, 1);
		grid.add(gradeTextArea, 1, 0 , 1, 1);
        grid.add(externalPersonLabel, 0, 2 , 1, 1);
		grid.add(cbxExternalPerson, 1, 2 , 1, 1);
        grid.add(buttonHBox, 0, 5 , 1, 1);
		
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
