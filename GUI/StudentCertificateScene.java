package GUI;

import java.util.Arrays;

import database.CertificateSQL;
import domain.Certificate;
import domain.Student;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

//Class that creates the Student Certificate scene
public class StudentCertificateScene {
    private CertificateSQL sqlC = new CertificateSQL();
    
    //Method that creates the Student Certificate scene with the information from the given attribute
    public Parent studentCertificateScene(Stage window, Student current_student) {
        StudentRegistrationScene studentRegistrationScene = new StudentRegistrationScene();

        //Adds the Background image
        Image image = new Image("resources/backgroundImage.jpg");
        ImageView imageView = new ImageView(image);
        Group root = new Group();
        root.getChildren().addAll(imageView);

        //Adds the Button to go back to the previous scene
        Button backButton = new Button("Back");
        backButton.setPrefSize(80, 37);
        backButton.setOnAction((event) -> {
            window.getScene().setRoot(studentRegistrationScene.studentRegistrationScene(window, current_student));
        });

        //Creates the HBox and adds the above Button
        HBox menu = new HBox(backButton);
        menu.setSpacing(10);

        //Creates the table to show records of different tables from the connected Database in the application
        TableView<Certificate> table = new TableView<Certificate>();
        TableColumn<Certificate, String> studentEmailCol = new TableColumn<Certificate, String>("Student email");
        TableColumn<Certificate, String> courseNameCol = new TableColumn<Certificate, String>("Course name");
        TableColumn<Certificate, String> idCol = new TableColumn<Certificate, String>("Certificate ID");
        TableColumn<Certificate, String> gradeCol = new TableColumn<Certificate, String>("Certificate grade");
        TableColumn<Certificate, String> externalPersonIdCol = new TableColumn<Certificate, String>("Employee name");
        
        table.getColumns().addAll(Arrays.asList(studentEmailCol, courseNameCol, idCol, gradeCol, externalPersonIdCol));

        //Creates a ObservableList to add student information to it
        ObservableList<Certificate> list = sqlC.getCertificateListFromStudent(current_student);

        //Fills the created TableColumns with information
        studentEmailCol.setCellValueFactory(new PropertyValueFactory<Certificate, String>("studentEmail"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<Certificate, String>("courseName"));
        idCol.setCellValueFactory(new PropertyValueFactory<Certificate, String>("certificateID"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<Certificate, String>("certificateGrade"));
        externalPersonIdCol.setCellValueFactory(new PropertyValueFactory<Certificate, String>("externalPersonName"));

        //Adds information from the given list to the Table
        table.setItems(list);

        //Makes changes in the table size
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        table.setMinWidth(Screen.getPrimary().getBounds().getWidth() - 30);
        table.setPadding(new Insets(30, 0, 0, 0));
        table.setFixedCellSize(40);

        //Creates a BorderPane and adds elements to it
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.getChildren().add(imageView);
        pane.setTop(menu);
        pane.setCenter(table);

        //Sets the path to the Stylesheet to be used by the BorderPane
        pane.getStylesheets().add("/resources/styleSheet.css");

        //Returns the BorderPane
        return pane;
    }
}
