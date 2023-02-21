package GUI;

import java.util.ArrayList;

import database.CourseSQL;
import domain.Course;
import domain.Module;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//Class that creates the Course Modify Modules scene
public class CourseModifyModules {
    
    //Method that creates the Course Modify scene with the information from the given attribute
    public Parent CourseModifyModulesScene(Stage window, Course course){
        CourseDetailPage courseDetailPage = new CourseDetailPage();
        CourseSQL sql = new CourseSQL();

        //Adds the Background image
        Image image = new Image("resources/backgroundImage.jpg");
        ImageView imageView = new ImageView(image);
        Group root = new Group();
        root.getChildren().addAll(imageView);
 
        //Adds the Button to go back to the courseDetailScene
        Button backButton = new Button("Back");
        backButton.setPrefSize(80, 37);
        backButton.setOnAction((event) -> {
            window.getScene().setRoot(courseDetailPage.CourseDetailScene(window, course));
        });
        
        //Creates the GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40, 0, 0, 0));
		grid.setHgap(5);
		grid.setVgap(5);

        //Creates an ArrayList for CheckBoxes
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();

        //Loops through the Modules that aren't added to a Course yet
        int i = 0;
        for(String module : sql.getModules()){
            CheckBox checkbox = new CheckBox(module);
            checkBoxes.add(checkbox);
            
            grid.add(checkbox, 0, i, 1, 1);
            i++;
        }

        //Loops through the Modules that are added to the selected Course
        for(Module module : sql.getSpecificModules(course)){
            CheckBox checkBox = new CheckBox(module.getTitle());
            checkBoxes.add(checkBox);
            checkBox.setSelected(true);

            grid.add(checkBox, 0, i , 1, 1);
            i++;
        }

        //Creates a button for submitting changes
        Button submitButton = new Button("Update modules");
        submitButton.setPrefSize(180, 40);

        //Adds an setOnAction event to the created Button that loops over all the checkboxes and submits the changes to the connected Database
        submitButton.setOnAction((event) -> {
            for(CheckBox checkBox : checkBoxes){
                boolean isSelected = checkBox.isSelected();
                if(isSelected){
                    sql.setCourseName(checkBox.getText(), course.getName());
                } else {
                    sql.setNull(checkBox.getText());
                }
            }
            window.getScene().setRoot(courseDetailPage.CourseDetailScene(window, course));
        });

        //Adds the Button to the GridPane
        grid.add(submitButton, 0, i+2, 1, 1);
        
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
