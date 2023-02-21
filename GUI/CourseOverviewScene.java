package GUI;

import java.util.Arrays;

import database.CourseSQL;
import domain.Course;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

//Class that creates the Course Overview scene
public class CourseOverviewScene {
    private CourseSQL sql = new CourseSQL();
    
    //Method that creates the Course Overview scene with the given Stage
    public Parent courseOverviewScene(Stage window) {
        HomescreenScene homescreenScene = new HomescreenScene();
        CourseOverviewScene courseOverviewScene = new CourseOverviewScene();
        CourseUpdateScene  courseUpdateScene = new CourseUpdateScene();
        CourseCreateScene courseCreateScene = new CourseCreateScene();
        CourseDetailPage courseDetailPage = new CourseDetailPage();

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

        //Adds the Button to go to the Course create scene
        Button createButton = new Button("Create");
        createButton.setPrefSize(80, 37);
        createButton.setOnAction((event) -> {
            window.getScene().setRoot(courseCreateScene.courseCreateScene(window));
        });

        //Creates a HBox and adds created Buttons to it
        HBox menu = new HBox(backButton, createButton);
        menu.setSpacing(10);

        //Creates the table to show all the records of the Course table from the connected Database in the application
        TableView<Course> table = new TableView<Course>();
        TableColumn<Course, String> nameCol = new TableColumn<Course, String>("Course name");
        TableColumn<Course, String> topicCol = new TableColumn<Course, String>("Course topic");
        TableColumn<Course, String> introductionCol = new TableColumn<Course, String>("Course Introduction");
        TableColumn<Course, String> levelCol = new TableColumn<Course, String>("Course Level");
        TableColumn<Course, String> editCol = new TableColumn<Course, String>("Edit");      
        TableColumn<Course, String> deleteCol = new TableColumn<Course, String>("Delete");      
        
        table.getColumns().addAll(Arrays.asList(nameCol, topicCol, introductionCol, levelCol, editCol, deleteCol));

        //Creates a ObservableList to store all the Course information
        ObservableList<Course> list = sql.getCourseList();

        //Fills the created TableColumns with Course information
        nameCol.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        topicCol.setCellValueFactory(new PropertyValueFactory<Course, String>("topic"));
        introductionCol.setCellValueFactory(new PropertyValueFactory<Course, String>("introduction"));
        levelCol.setCellValueFactory(new PropertyValueFactory<Course, String>("level"));
        editCol.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        deleteCol.setCellValueFactory(new PropertyValueFactory<>("Delete"));

        //Adds an setOnMouseClicked event to a created Button
        table.setOnMouseClicked((event) -> {
            Course course = table.getSelectionModel().getSelectedItem();
            window.getScene().setRoot(courseDetailPage.CourseDetailScene(window, course));
        });
        
        //Creates a Callback and adds methods to it that edit given information
        Callback<TableColumn<Course, String>, TableCell<Course, String>> editCellFactory = new Callback<TableColumn<Course, String>, TableCell<Course, String>>() {
            @Override
            public TableCell<Course, String> call(final TableColumn<Course, String> param) {
                final TableCell<Course, String> cell = new TableCell<Course, String>() {

                    final Button editBtn = new Button("Edit");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            editBtn.setOnAction(event -> {
                                Course course = getTableView().getItems().get(getIndex());
                                window.getScene().setRoot(courseUpdateScene.courseUpdateScene(window, course));
                            });
                            setGraphic(editBtn);
                        }
                    }
                };
                return cell;
            }
        };
        
        //Creates a label that tells the user why the delete didn't succeed
        Label deleteFeedback = new Label("");
        deleteFeedback.setId("errorLabel");

        //Creates a Callback and adds methods to it that delete given information
        Callback<TableColumn<Course, String>, TableCell<Course, String>> deletecellFactory = new Callback<TableColumn<Course, String>, TableCell<Course, String>>() {
            @Override
            public TableCell<Course, String> call(final TableColumn<Course, String> param) {
                final TableCell<Course, String> cell = new TableCell<Course, String>() {

                    final Button deleteBtn = new Button("Delete");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            deleteBtn.setOnAction(event -> {
                                Course course = getTableView().getItems().get(getIndex());
                                String feedback =  sql.deleteCourse(course);
                                if(feedback.contains("Can't delete course")){
                                    deleteFeedback.setText(feedback);
                                } else {
                                    window.getScene().setRoot(courseOverviewScene.courseOverviewScene(window));
                                } 
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
        deleteCol.setCellFactory(deletecellFactory);

        //Specifies the size of the editCol and deleteCol
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

        GridPane grid = new GridPane();
        // grid.add(new Label(), 0, 0, 1, 1);
        grid.add(table, 0, 1, 1, 1);
        grid.add(deleteFeedback, 0, 2, 1, 1);

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
