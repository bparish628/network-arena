package ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Start extends Application {

    final ImageView classImage = new ImageView();
    final Label selected = new Label();
    final Label hp = new Label();
    final Label action1 = new Label();
    final Label action2 = new Label();
    final Label action3 = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage start) {
        /*Setting the ui parameters*/
        GridPane container = new GridPane();
        for(int i = 0; i < 3; i++){
            container.getColumnConstraints().add(new ColumnConstraints(100));
        }
        container.setAlignment(Pos.TOP_CENTER);
        container.setHgap(10);
        container.setVgap(10);
        container.setPadding(new Insets(15, 15, 15, 15));

        Scene ui = new Scene(container, 400, 500);
        start.setTitle("The Network Arena");

        /*Setting up the title pane*/
        Label title = new Label("The Network Arena");
        title.setFont(new Font("Cambria", 32));
        title.setUnderline(true);
        container.add(title, 0, 0, 3, 1);

        /*Setting up the username field*/
        HBox usernamePane = new HBox();
        usernamePane.setPadding(new Insets(0, 45, 0, 0));
        usernamePane.setSpacing(10);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Cambria", 16));

        TextField usernameField = new TextField();
        usernameField.setPrefWidth(175);
        usernamePane.getChildren().addAll(usernameLabel, usernameField);
        container.add(usernamePane, 0, 2, 3, 1);

        /*Setting up class selection*/
        HBox classesPane = new HBox();
        classesPane.setPadding(new Insets(15, 45, 15, 45));
        classesPane.setSpacing(10);

        ToggleGroup classes = new ToggleGroup();

        /*Melee*/
        RadioButton melee = new RadioButton("Melee");
        melee.setToggleGroup(classes);
        melee.setUserData("Melee");

        /*Magic*/
        RadioButton magic = new RadioButton("Magic");
        magic.setToggleGroup(classes);
        magic.setUserData("Magic");

        /*Ranged*/
        RadioButton ranged = new RadioButton("Range");
        ranged.setToggleGroup(classes);

        classesPane.getChildren().addAll(melee, magic, ranged);
        container.add(classesPane, 0, 3, 3, 1);
        ranged.setUserData("Range");

        /*Radio Listener*/

        classes.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (classes.getSelectedToggle() != null) {
                    final Image image = new Image("/ui/images/" + classes.getSelectedToggle().getUserData().toString() + ".png", 50, 75, false, false);
                    classImage.setImage(image);
                    selected.setText(classes.getSelectedToggle().getUserData().toString());
                    hp.setText("30");
                    action1.setText("Action1");
                    action2.setText("Action2");
                    action3.setText("Action3");
                }
            }
        });

        /*Setting up the stats labels*/
        Label selectedLabel = new Label("Class:");
        container.add(selectedLabel, 0, 4);
        container.add(selected, 1, 4);

        Label hpLabel = new Label("HP:");
        container.add(hpLabel, 0, 5);
        container.add(hp, 1, 5);

        Label actionLabel = new Label("Actions:");
        container.add(actionLabel, 0, 6);
        container.add(action1, 1, 6);
        container.add(action2, 1, 7);
        container.add(action3, 1, 8);

        container.add(classImage, 2, 5, 1, 3);
        /*Setting up the host field*/
        HBox hostPane = new HBox();
        hostPane.setPadding(new Insets(30, 0, 45, 0));
        hostPane.setSpacing(10);

        Label hostLabel = new Label("Host:");
        hostLabel.setFont(new Font("Cambria", 16));

        TextField hostField = new TextField();
        hostField.setPrefWidth(212);

        hostPane.getChildren().addAll(hostLabel, hostField);
        container.add(hostPane, 0, 9, 3, 1);

        /*Setting up connect button*/
        Button connectBtn = new Button("Connect");
        HBox boxBtn = new HBox(10);
        boxBtn.setAlignment(Pos.BOTTOM_CENTER);
        boxBtn.getChildren().add(connectBtn);
        container.add(boxBtn, 1, 15);

        /*Set the ui*/
        start.setScene(ui);
        start.show();
    }


}
