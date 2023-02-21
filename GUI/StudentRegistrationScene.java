package GUI;

import java.util.Arrays;

import database.RegistrationSQL;
import domain.Course;
import domain.Registration;
import domain.Student;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

//Class that creates the Student Registration scene
public class StudentRegistrationScene extends domain.Validation {
    private RegistrationSQL sqlR = new RegistrationSQL();

    //Method that creates the Student Registration scene with the information from the given attribute
    public Parent studentRegistrationScene(Stage window, Student current_student) {
        StudentOverviewScene studentOverviewScene = new StudentOverviewScene();
        StudentRegistrationScene studentDetailScene = new StudentRegistrationScene();
        StudentCourseScene studentCourseScene = new StudentCourseScene();
        StudentCertificateScene studentCertificateScene = new StudentCertificateScene();
        RegistrationCreateScene registrationCreateScene = new RegistrationCreateScene();
        RegistrationUpdateScene registrationUpdateScene = new RegistrationUpdateScene();

        //Adds the Background image
        Image image = new Image("resources/backgroundImage.jpg");
        ImageView imageView = new ImageView(image);
        Group root = new Group();
        root.getChildren().addAll(imageView);

        //Adds the Button to go back to the homeScene.
        Button backButton = new Button("Back");
        backButton.setPrefSize(80, 37);
        backButton.setOnAction((event) -> {
            window.getScene().setRoot(studentOverviewScene.studentOverviewScene(window));
        });

        //Adds the Button to register a new Student
        Button registerButton = new Button("Register");
        registerButton.setPrefSize(80, 37);
        registerButton.setOnAction((event) -> {
            window.getScene().setRoot(registrationCreateScene.registrationCreateScene(window, current_student));
        });

        //Adds the Button to view Certificates
        Button certificateButton = new Button("View certificates");
        certificateButton.setPrefSize(120, 37);
        certificateButton.setOnAction((event) -> {
            window.getScene().setRoot(studentCertificateScene.studentCertificateScene(window, current_student));
        });

        //Creates a HBox with the above created Buttons
        HBox menu = new HBox(backButton, registerButton, certificateButton);
        menu.setSpacing(10);

        //Creates a Table to show Registration information in the application
        TableView<Registration> table = new TableView<Registration>();
        TableColumn<Registration, String> courseNameCol = new TableColumn<Registration, String>("Course name");
        TableColumn<Registration, String> registrationDateCol = new TableColumn<Registration, String>("Registration date");
        TableColumn<Registration, String> editCol = new TableColumn<Registration, String>("Edit");   
        TableColumn<Registration, String> deleteCol = new TableColumn<Registration, String>("Delete");      
        
        table.getColumns().addAll(Arrays.asList(courseNameCol, registrationDateCol, editCol, deleteCol));

        //Creates a ObservableList to store Registrations
        ObservableList<Registration> list = sqlR.getStudentRegistrationList(current_student);

        //Fills the created TableColumns with Registration information
        courseNameCol.setCellValueFactory(new PropertyValueFactory<Registration, String>("courseName"));
        registrationDateCol.setCellValueFactory(new PropertyValueFactory<Registration, String>("registrationDate"));
        editCol.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        deleteCol.setCellValueFactory(new PropertyValueFactory<>("Delete"));

        //Sets an setOnMouseClicked event to the created Table that takes Registration information from the clicked row
        table.setOnMouseClicked((event) -> {
            Registration registration = table.getSelectionModel().getSelectedItem();
            Course course = sqlR.getStudentRegistrationCourseFromList(registration);
            window.getScene().setRoot(studentCourseScene.studentCourseScene(window, registration, course, current_student));
        });
        
        //Creates a Callback and adds methods to it that edit given information
        Callback<TableColumn<Registration, String>, TableCell<Registration, String>> editCellFactory = new Callback<TableColumn<Registration, String>, TableCell<Registration, String>>() {
            @Override
            public TableCell<Registration, String> call(final TableColumn<Registration, String> param) {
                final TableCell<Registration, String> cell = new TableCell<Registration, String>() {

                    final Button editBtn = new Button("Edit");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            editBtn.setOnAction(event -> {
                                Registration registration = getTableView().getItems().get(getIndex());
                                window.getScene().setRoot(registrationUpdateScene.registrationUpdateScene(window, registration, current_student));
                            });
                            setGraphic(editBtn);
                        }
                    }
                };
                return cell;
            }
        };

        //Creates a Callback and adds methods to it that delete given information
        Callback<TableColumn<Registration, String>, TableCell<Registration, String>> deleteCellFactory = new Callback<TableColumn<Registration, String>, TableCell<Registration, String>>() {
            @Override
            public TableCell<Registration, String> call(final TableColumn<Registration, String> param) {
                final TableCell<Registration, String> cell = new TableCell<Registration, String>() {

                    final Button deleteBtn = new Button("Delete");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            deleteBtn.setOnAction(event -> {
                                Registration registration = getTableView().getItems().get(getIndex());
                                sqlR.deleteRegistration(registration);
                                window.getScene().setRoot(studentDetailScene.studentRegistrationScene(window, current_student));
                            });
                            setGraphic(deleteBtn);
                        }
                    }
                };
                return cell;
            }
        };

        //Fills the editCol and deleteCol with information
        editCol.setCellFactory(editCellFactory);
        deleteCol.setCellFactory(deleteCellFactory);

        //Sets the widths of the editCol and deleteCol
        editCol.setMinWidth(50);
        editCol.setMaxWidth(50);
        deleteCol.setMinWidth(65);
        deleteCol.setMaxWidth(65);

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
