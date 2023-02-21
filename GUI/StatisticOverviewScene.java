package GUI;

import database.StatisticSQL;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//Class that creates the Statistic Overview scene
public class StatisticOverviewScene {
    StatisticSQL sqlS = new StatisticSQL();

    //Method that creates the Statistic Overview scene with the given Stage
    public Parent statisticOverviewScene(Stage window) {
        HomescreenScene homescreenScene = new HomescreenScene();

        //Adds the Background image
        Image image = new Image("resources/backgroundImage.jpg");
        ImageView imageView = new ImageView(image);
        Group root = new Group();
        root.getChildren().addAll(imageView);

        //Adds the Button to go back to the homeScene.
        Button backButton = new Button("Back");
        backButton.setPrefSize(80, 37);
        backButton.setOnAction((event) -> {
            window.getScene().setRoot(homescreenScene.homeScene(window));
        });

        //Creates a HBox with the above Button
        HBox menu = new HBox(backButton);
        menu.setSpacing(10);

        //Creates a Label and a ComboBox which the user can use to choose a gender from a list
        Label genderLabel = new Label("Choose gender: ");
        ComboBox<String>cbxGender = new ComboBox<>();
        String[] genders = {"M", "F"};
        cbxGender.getItems().setAll(genders);

        //Adds an setOnAction event to the result Label
        Label resultGender = new Label();
        cbxGender.setOnAction((event) -> {
            if(cbxGender.getSelectionModel().getSelectedItem().equals("M")) {
                int result = sqlS.calculateGenderCertificates("M");
                resultGender.setText(result + "% of the males graduated");
            } else if(cbxGender.getSelectionModel().getSelectedItem().equals("F")) {
                int result = sqlS.calculateGenderCertificates("F");
                resultGender.setText(result + "% of the females graduated");
            }
        });

        //Creates a GridPane
        GridPane grid = new GridPane();

        //Gets and shows the names of the top 3 most viewed webcasts
        Label infoWebcastLabel = new Label("Top 3 most viewed webcasts: ");
        int h = 1;
        int i = 2;
        grid.add(infoWebcastLabel, 0, i, 1, 1) ;
        for(String webcast : sqlS.getTop3MostViewedWebcasts()){
            Label label = new Label(h + ". " + webcast);
            grid.add(label, 1, i, 1, 1);
            i++;
            h++;
        }

        //Gets and shows the names of the top 3 Courses with the most certificates
        Label infoCourseLabel = new Label("Top 3 most certificates per course: ");
        int k = 1;
        int j = 5;
        grid.add(infoCourseLabel, 0, j, 1, 1) ;
        for(String course : sqlS.getTop3MostCertificateCourses()){
            Label label = new Label(k + ". " + course);
            grid.add(label, 1, j, 1, 1);
            j++;
            k++;
        }

        //Sets the position of the different elements that are added to the GridPane
		grid.setPadding(new Insets(40, 0, 0, 0));
		grid.setHgap(5);
		grid.setVgap(2);
        grid.add(genderLabel, 0, 0 , 1, 1);
		grid.add(cbxGender, 1, 0 , 1, 1);
        grid.add(resultGender, 0, 1, 1, 1);

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
