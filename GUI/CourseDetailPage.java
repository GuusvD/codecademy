package GUI;

import database.CourseSQL;
import domain.Course;
import domain.Module;
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

//Class that creates the Course Detail scene
public class CourseDetailPage {
    private CourseSQL sql = new CourseSQL();

    //Method that creates the Course Detail scene with the information from the given attribute
    public Parent CourseDetailScene(Stage window, Course course) {

        CourseOverviewScene courseOverviewScene = new CourseOverviewScene();
        CourseModifyModules courseModifyModules = new CourseModifyModules();
        CourseModuleScene courseModuleScene = new CourseModuleScene();
        CourseDetailPage courseDetailPage = new CourseDetailPage();

        //Adds the Background image
        Image image = new Image("resources/backgroundImage.jpg");
        ImageView imageView = new ImageView(image);
        Group root = new Group();
        root.getChildren().addAll(imageView);

        //Adds the Button to go back to the StudentOverviewScene
        Button backButton = new Button("Back");
        backButton.setPrefSize(80, 37);
        backButton.setOnAction((event) -> {
            window.getScene().setRoot(courseOverviewScene.courseOverviewScene(window));
        });

        //Adds the Button to modify Modules
        Button modifyModules = new Button("Modify modules");
        modifyModules.setPrefSize(120, 37);
        modifyModules.setOnAction((event) -> {
            window.getScene().setRoot(courseModifyModules.CourseModifyModulesScene(window, course));
        });    

        //Adds a HBox and adds elements to it
        HBox menu = new HBox(backButton, modifyModules);
        menu.setSpacing(15);

        //Sets the labels with Course information
        Label infoNameLabel = new Label("Course name: ");
        Label nameLabel = new Label();
        nameLabel.setText(course.getName());

        Label infoTopicLabel = new Label("Course topic: ");
        Label topicLabel = new Label();
        topicLabel.setText(course.getTopic());

        Label infoIntroLabel = new Label("Course introduction: ");
        Label introLabel = new Label();
        introLabel.setText(course.getIntroduction());

        Label infoLevelLabel = new Label("Course level: ");
        Label levelLabel = new Label();
        levelLabel.setText(course.getLevel());

        Label infoCertificateLabel = new Label("Certificates obtained : ");
        Label certificateLabel = new Label(String.valueOf(sql.obtainedCertificates(course)));

        //Creates a GridPane 
        GridPane grid = new GridPane();

        //Adds an setOnAction event and a style to a created Button
        Label infoModulesLabel = new Label("Modules: ");
        int j = 6;
        for(Module module : sql.getSpecificModules(course)){
            Button button = new Button(module.getTitle());
            button.setOnAction((event) -> {
                window.getScene().setRoot(courseModuleScene.courseModuleScene(window, module, course));
            });
            button.setStyle("-fx-background-color: #0a9ec2; -fx-text-fill: #FFFFFF; -fx-font-size: 13");
            grid.add(button, 1, j, 1, 1);
            j++;
        }

        //Adds an setOnAction event to a created Button
        Label hasRelevantLabel = new Label("Relevant courses: ");
        j+=2;
        grid.add(hasRelevantLabel, 0, j, 1, 1);
        for(Course relCourse : sql.relevantCourses(course)){
            Button button = new Button(relCourse.getName());
            button.setOnAction((event) -> {
                window.getScene().setRoot(courseDetailPage.CourseDetailScene(window, relCourse));
            });
            grid.add(button, 1, j, 1, 1);
            j++;
        }
        
        //Sets the position of the different elements in the GridPane
		grid.setPadding(new Insets(40, 0, 0, 0));
		grid.setHgap(5);
		grid.setVgap(5);
        grid.add(infoNameLabel, 0, 0 , 1, 1);
        grid.add(nameLabel, 1, 0 , 1, 1);
        grid.add(infoTopicLabel, 0, 1 , 1, 1);
        grid.add(topicLabel, 1, 1 , 1, 1);
        grid.add(infoIntroLabel, 0, 2 , 1, 1);
        grid.add(introLabel, 1, 2 , 1, 1);
        grid.add(infoLevelLabel, 0, 3 , 1, 1);
        grid.add(levelLabel, 1, 3 , 1, 1);
        grid.add(certificateLabel, 1, 4 , 1, 1);
        grid.add(infoCertificateLabel, 0, 4 , 1, 1);
        grid.add(infoModulesLabel, 0, 6 , 1, 1);
        
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