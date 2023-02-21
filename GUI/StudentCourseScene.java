package GUI;

import database.CertificateSQL;
import database.ExternalPersonSQL;
import database.RegistrationSQL;
import domain.Module;
import domain.Registration;
import domain.Certificate;
import domain.Course;
import domain.Student;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//Class that creates the Student Course scene
public class StudentCourseScene {
    RegistrationSQL sqlR = new RegistrationSQL();
    CertificateSQL sqlC = new CertificateSQL();
    ExternalPersonSQL sqlE = new ExternalPersonSQL();

    //Method that creates the Student Course scene with the information from the given attributes
    public Parent studentCourseScene(Stage window, Registration registration, Course course, Student current_student) {
        StudentRegistrationScene studentRegistrationScene = new StudentRegistrationScene();
        StudentCourseModuleScene studentCourseModuleScene = new StudentCourseModuleScene();
        CertificateCreateScene certificateCreateScene = new CertificateCreateScene();
        CertificateUpdateScene certificateUpdateScene = new CertificateUpdateScene();

        //Adds the Background image
        Image image = new Image("resources/backgroundImage.jpg");
        ImageView imageView = new ImageView(image);
        Group root = new Group();
        root.getChildren().addAll(imageView);

        //Adds the Button to go back to the homeScene
        Button backButton = new Button("Back");
        backButton.setPrefSize(80, 37);
        backButton.setOnAction((event) -> {
            window.getScene().setRoot(studentRegistrationScene.studentRegistrationScene(window, current_student));
        });

        //Adds the Button to create a Certificate
        Button createButton = new Button("Create certificate");
        createButton.setPrefSize(120, 37);
        createButton.setOnAction((event) -> {
            window.getScene().setRoot(certificateCreateScene.certificateCreateScene(window, registration, course, current_student));

        });

        //Adds the Button to check a Certificate
        Label message = new Label("");
        message.setId("errorLabel");
        Button checkButton = new Button("Check certificates");
        checkButton.setPrefSize(120, 37);
        checkButton.setOnAction((event) -> {
            String feedback = sqlC.checkIfStudentReceiveCertificate(sqlC.getSingleCertificateFromStudentForSpecificCourse(course, current_student), registration);
            if(feedback.contains("Certificate cannot be linked")){
                message.setText(feedback);
            } else {
                window.getScene().setRoot(studentCourseScene(window, registration, course, current_student));
            }
        });

        //Creates a HBox and adds the above created Buttons to it
        HBox menu = new HBox(backButton, createButton, checkButton);
        menu.setSpacing(10);

        //Creates Labels to show information of Courses
        Label infoNameLabel = new Label("Course name: ");
        Label nameLabel = new Label();
        nameLabel.setText(course.getName());

        Label infoTopicLabel = new Label("Course topic: ");
        Label topicLabel = new Label();
        topicLabel.setText(course.getTopic());

        Label infoIntroductionLabel = new Label("Course introduction: ");
        Label introLabel = new Label();
        introLabel.setText(course.getIntroduction());

        Label infoLevelLabel = new Label("Course level: ");
        Label levelLabel = new Label();
        levelLabel.setText(course.getLevel());

        //Creates a GridPane
        GridPane grid = new GridPane();

        //Creates a Label
        Label infoModuleLabel = new Label("Modules: ");

        //Adds a setOnAction event to a Button that shows Module information
        int i = 4;
        for (Module module : sqlR.getSpecificModules(registration)) {
            Button button = new Button(module.getTitle());
            button.setOnAction((event) -> {
                window.getScene().setRoot(studentCourseModuleScene.studentCourseModuleScene(window, module, registration, course, current_student));
            });
            grid.add(button, 1, i, 1, 1);
            i++;
        }

        //Adds setOnAction events to the edit and delete buttons and adds information to new Labels for each Certificate
        int k = 4;
        for (Certificate certificate : sqlC.getCertificatesFromStudentForSpecificCourse(course, current_student)) {
            Label infoCertificateLabel = new Label("Certificate: ");
            Label certificateGradeLabel = new Label("Certificate grade: " + String.valueOf(certificate.getCertificateGrade()));
            Label certificateExternalPersonLabel = new Label("Certificate employee: " + sqlE.getEmployeeNameByIdWithCertificateParameter(certificate));
            Button editButton = new Button("Edit");
            editButton.setOnAction((event) -> {
                window.getScene().setRoot(certificateUpdateScene.certificateUpdateScene(window, certificate, registration, course, current_student));
            });
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction((event) -> {
                sqlC.deleteCertificate(certificate);
                window.getScene().setRoot(studentCourseScene(window, registration, course, current_student));
            });
            grid.add(infoCertificateLabel, 4, 4 , 1, 1);
            grid.add(certificateGradeLabel, 5, k, 1, 1);
            grid.add(certificateExternalPersonLabel, 6, k, 1, 1);
            grid.add(editButton, 7, k, 1, 1);
            grid.add(deleteButton, 8, k, 1, 1);
            k++;
        }

        //Sets the position of the different elements that are added to the GridPane
		grid.setPadding(new Insets(40, 0, 0, 0));
		grid.setHgap(5);
		grid.setVgap(5);
        grid.add(infoNameLabel, 0, 0 , 1, 1);
        grid.add(nameLabel, 1, 0 , 1, 1);
        grid.add(infoTopicLabel, 0, 1 , 1, 1);
        grid.add(topicLabel, 1, 1 , 1, 1);
        grid.add(infoIntroductionLabel, 0, 2 , 1, 1);
        grid.add(introLabel, 1, 2 , 1, 1);
        grid.add(infoLevelLabel, 0, 3 , 1, 1);
        grid.add(levelLabel, 1, 3 , 1, 1);
        grid.add(infoModuleLabel, 0, 4 , 1, 1);
        grid.add(message, 1, i, 1, 1);

        //Creates a BorderPane and adds elements to it
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.getChildren().add(imageView);
        pane.setTop(menu);
        pane.setCenter(grid);

        //Sets the path to the Stylesheet to be used by the BorderPane
        pane.getStylesheets().add("/resources/styleSheet.css");

        //Returns the BorderPane
        return pane;
    }
}