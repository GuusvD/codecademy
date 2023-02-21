package GUI;

import database.ExternalPersonSQL;
import database.RegistrationSQL;
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

//Class that creates the Course Module scene
public class CourseModuleScene {
    ExternalPersonSQL sqlE = new ExternalPersonSQL();
    RegistrationSQL sqlR = new RegistrationSQL();

    //Method that creates the Course Module scene with the information from the given attributes
    public Parent courseModuleScene(Stage window, Module module, Course course) {
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
            window.getScene().setRoot(courseDetailPage.CourseDetailScene(window, course));
        });

        //Creates a HBox and adds a Button to it
        HBox menu = new HBox(backButton);
        menu.setSpacing(10);

        //Sets the labels with Module information
        Label infoIDLabel = new Label("Module ID: ");
        Label moduleIDLabel = new Label();
        moduleIDLabel.setText(String.valueOf(module.getItemId()));

        Label infoSerNrLabel = new Label("Module serial number: ");
        Label moduleSerNrLabel = new Label();
        moduleSerNrLabel.setText(String.valueOf(module.getSerialNumber()));

        Label infoVersionLabel = new Label("Module version: ");
        Label moduleVersionLabel = new Label();
        moduleVersionLabel.setText(module.getVersion());

        Label infoTitleLabel = new Label("Module title: ");
        Label moduleTitleLabel = new Label();
        moduleTitleLabel.setText(module.getTitle());

        Label infoDescLabel = new Label("Module description: ");
        Label moduleDescLabel = new Label();
        moduleDescLabel.setText(module.getDescription());

        Label infoDateLabel = new Label("Module publication date: ");
        Label moduleDateLabel = new Label();
        moduleDateLabel.setText(String.valueOf(module.getPublicationDate()));

        Label infoStatusLabel = new Label("Module status: ");
        Label moduleStatusLabel = new Label();
        moduleStatusLabel.setText(module.getStatus());

        Label infoExPerNameLabel = new Label("Module contactperson name: ");
        Label moduleExPerNameLabel = new Label();
        moduleExPerNameLabel.setText(sqlE.getExternalPersonById(module).getName());

        Label infoExPerEmailLabel = new Label("Module contactperson email: ");
        Label moduleExPerEmailLabel = new Label();
        moduleExPerEmailLabel.setText(sqlE.getExternalPersonById(module).getEmail());

        Label infoViewsLabel = new Label("Module average views: ");
        Label moduleViewsLabel = new Label();
        moduleViewsLabel.setText(String.valueOf(sqlR.getAverageViews(module)));

        Label infoProgressLabel = new Label("Module average progress: ");
        Label moduleProgressLabel = new Label();
        moduleProgressLabel.setText(String.valueOf(sqlR.getAverageProgress(module) + "%"));

        //Creates a GridPane and sets the position of the different elements that are added to it
        GridPane grid = new GridPane();
        
		grid.setPadding(new Insets(40, 0, 0, 0));
		grid.setHgap(5);
		grid.setVgap(5);
        grid.add(infoIDLabel, 0, 0 , 1, 1);
        grid.add(moduleIDLabel, 1, 0 , 1, 1);
        grid.add(infoSerNrLabel, 0, 1 , 1, 1);
        grid.add(moduleSerNrLabel, 1, 1 , 1, 1);
        grid.add(infoVersionLabel, 0, 2 , 1, 1);
        grid.add(moduleVersionLabel, 1, 2 , 1, 1);
        grid.add(infoTitleLabel, 0, 3 , 1, 1);
        grid.add(moduleTitleLabel, 1, 3 , 1, 1);
        grid.add(infoDescLabel, 0, 4 , 1, 1);
        grid.add(moduleDescLabel, 1, 4 , 1, 1);
        grid.add(infoDateLabel, 0, 5 , 1, 1);
        grid.add(moduleDateLabel, 1, 5 , 1, 1);
        grid.add(infoStatusLabel, 0, 6 , 1, 1);
        grid.add(moduleStatusLabel, 1, 6 , 1, 1);
        grid.add(infoExPerNameLabel, 0, 7 , 1, 1);
        grid.add(moduleExPerNameLabel, 1, 7 , 1, 1);
        grid.add(infoExPerEmailLabel, 0, 8 , 1, 1);
        grid.add(moduleExPerEmailLabel, 1, 8 , 1, 1);
        grid.add(infoViewsLabel, 0, 9 , 1, 1);
        grid.add(moduleViewsLabel, 1, 9 , 1, 1);
        grid.add(infoProgressLabel, 0, 10 , 1, 1);
        grid.add(moduleProgressLabel, 1, 10 , 1, 1);

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
