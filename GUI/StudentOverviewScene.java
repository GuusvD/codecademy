package GUI;

import java.util.Arrays;

import database.StudentSQL;
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

//Class that creates the Student Overview scene
public class StudentOverviewScene {
    private StudentSQL sql = new StudentSQL();

    //Method that creates the Student Overview scene with the given Stage
    public Parent studentOverviewScene(Stage window) {
        HomescreenScene homescreenScene = new HomescreenScene();
        StudentOverviewScene studentOverviewScene = new StudentOverviewScene();
        StudentCreateScene studentCreateScene = new StudentCreateScene();
        StudentUpdateScene studentUpdateScene = new StudentUpdateScene();
        StudentRegistrationScene studentDetailScene = new StudentRegistrationScene();

        //Adds the Background image
        Image image = new Image("resources/backgroundImage.jpg");
        ImageView imageView = new ImageView(image);
        Group root = new Group();
        root.getChildren().addAll(imageView);

        //Adds the Button to go back to the homeScene
        Button backButton = new Button("Back");
        backButton.setPrefSize(80, 37);
        backButton.setOnAction((event) -> {
            window.getScene().setRoot(homescreenScene.homeScene(window));
        });

        //Adds the Button which leads to the StudentCreateScene
        Button createButton = new Button("Create student");
        createButton.setPrefSize(120, 37);
        createButton.setOnAction((event) -> {
            window.getScene().setRoot(studentCreateScene.studentCreateScene(window));
        });

        //Adds the Hbox with the two buttons above
        HBox menu = new HBox(backButton, createButton);
        menu.setSpacing(10);

        //Creates a Table to show Student information in the application
        TableView<Student> table = new TableView<Student>();
        TableColumn<Student, String> emailCol = new TableColumn<Student, String>("Student email");
        TableColumn<Student, String> nameCol = new TableColumn<Student, String>("Student name");
        TableColumn<Student, String> birthDayCol = new TableColumn<Student, String>("Birthday");
        TableColumn<Student, String> birthMonthCol = new TableColumn<Student, String>("Birthmonth");
        TableColumn<Student, String> birthYearCol = new TableColumn<Student, String>("Birthyear");
        TableColumn<Student, String> genderCol = new TableColumn<Student, String>("Gender");
        TableColumn<Student, String> streetCol = new TableColumn<Student, String>("Street");
        TableColumn<Student, String> houseNumberCol = new TableColumn<Student, String>("House number");
        TableColumn<Student, String> houseNumberAdditionCol = new TableColumn<Student, String>("Addition");
        TableColumn<Student, String> postalCodeCol = new TableColumn<Student, String>("Postal code");
        TableColumn<Student, String> residenceCol = new TableColumn<Student, String>("Residence");
        TableColumn<Student, String> countryCol = new TableColumn<Student, String>("Country");
        TableColumn<Student, String> editCol = new TableColumn<Student, String>("Edit");   
        TableColumn<Student, String> deleteCol = new TableColumn<Student, String>("Delete");      
        
        table.getColumns().addAll(Arrays.asList(emailCol, nameCol, birthDayCol, birthMonthCol, birthYearCol, genderCol, streetCol, houseNumberCol, houseNumberAdditionCol, postalCodeCol, residenceCol, countryCol, editCol, deleteCol));

        //Creates a ObservableList to store Students in it
        ObservableList<Student> list = sql.getStudentList();

        //Fills the created TableColumns with Student information
        emailCol.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        birthDayCol.setCellValueFactory(new PropertyValueFactory<Student, String>("birthDay"));
        birthMonthCol.setCellValueFactory(new PropertyValueFactory<Student, String>("birthMonth"));
        birthYearCol.setCellValueFactory(new PropertyValueFactory<Student, String>("birthYear"));
        genderCol.setCellValueFactory(new PropertyValueFactory<Student, String>("gender"));
        streetCol.setCellValueFactory(new PropertyValueFactory<Student, String>("street"));
        houseNumberCol.setCellValueFactory(new PropertyValueFactory<Student, String>("houseNumber"));
        houseNumberAdditionCol.setCellValueFactory(new PropertyValueFactory<Student, String>("houseNumberAddition"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<Student, String>("postalCode"));
        residenceCol.setCellValueFactory(new PropertyValueFactory<Student, String>("residence"));
        countryCol.setCellValueFactory(new PropertyValueFactory<Student, String>("country"));
        editCol.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        deleteCol.setCellValueFactory(new PropertyValueFactory<>("Delete"));

        //Sets an setOnMouseClicked event to the created Table that takes Student information from the clicked row
        table.setOnMouseClicked((event) -> {
            Student student = table.getSelectionModel().getSelectedItem();
            window.getScene().setRoot(studentDetailScene.studentRegistrationScene(window, student));
        });
        
        //Creates a Callback and adds methods to it that edit given information
        Callback<TableColumn<Student, String>, TableCell<Student, String>> editCellFactory = new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
            @Override
            public TableCell<Student, String> call(final TableColumn<Student, String> param) {
                final TableCell<Student, String> cell = new TableCell<Student, String>() {

                    final Button editBtn = new Button("Edit");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            editBtn.setOnAction(event -> {
                                Student student = getTableView().getItems().get(getIndex());
                                window.getScene().setRoot(studentUpdateScene.studentUpdateScene(window, student));
                            });
                            setGraphic(editBtn);
                        }
                    }
                };
                return cell;
            }
        };

        //Creates a Callback and adds methods to it that delete given information
        Callback<TableColumn<Student, String>, TableCell<Student, String>> deleteCellFactory = new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
            @Override
            public TableCell<Student, String> call(final TableColumn<Student, String> param) {
                final TableCell<Student, String> cell = new TableCell<Student, String>() {

                    final Button deleteBtn = new Button("Delete");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            deleteBtn.setOnAction(event -> {
                                Student student = getTableView().getItems().get(getIndex());
                                sql.deleteStudent(student);
                                window.getScene().setRoot(studentOverviewScene.studentOverviewScene(window));
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

        //Sets the widths of the created TableColumns
        birthDayCol.setMinWidth(70);
        birthDayCol.setMaxWidth(70);
        birthMonthCol.setMinWidth(80);
        birthMonthCol.setMaxWidth(80);
        birthYearCol.setMinWidth(70);
        birthYearCol.setMaxWidth(70);
        genderCol.setMinWidth(70);
        genderCol.setMaxWidth(70);
        houseNumberCol.setMinWidth(110);
        houseNumberCol.setMaxWidth(110);
        houseNumberAdditionCol.setMinWidth(70);
        houseNumberAdditionCol.setMaxWidth(70);
        postalCodeCol.setMinWidth(90);
        postalCodeCol.setMaxWidth(90);
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
