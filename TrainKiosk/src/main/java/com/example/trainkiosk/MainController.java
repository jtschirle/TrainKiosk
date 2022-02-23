package com.example.trainkiosk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    AudioPlayer player = new AudioPlayer();
    ObservableList<String> list = FXCollections.observableArrayList();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Calendar c = Calendar.getInstance();
    boolean style;

    @FXML
    private Label errorText;
    @FXML
    private ComboBox<String> cityBox;

    @FXML
    protected void changeScreenScheduleButtonPushed(ActionEvent event) throws IOException {
        if(cityBox.getValue() == null) {
            errorText.setText("Please Select a Destination!!!");
            player.playError();
            return;
        }
        player.playClick();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Application.class.getResource("schedule-view.fxml"));
        Parent tableViewParent = fxmlLoader.load();
        ScheduleController controller = fxmlLoader.getController();
        controller.setSelectedCity(cityBox.getValue());

        Scene scene = new Scene(tableViewParent);
        scene.getStylesheets().add("main.css");

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void changeScreenPremiumButtonPushed(ActionEvent event) throws IOException{
        player.playClick();
        errorText.setText("Coming soon!");
    }

    @FXML
    protected void setStyleSheet(ActionEvent event) throws IOException {
        player.playClick();
        setStyle(!style);
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 550);
        scene.getStylesheets().add(style ? "main.css" : "dark.css");
        setStyle(!style);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setStyle(!style);
        setData();
    }

    public void setStyle(boolean style) {
        this.style = style;
    }

    public void setData(){
        for(Cities city : Cities.values()){
            if(city.getName() != Cities.SALT_LAKE_CITY.getName())
                 list.add(city.getName());
        }
        cityBox.setItems(list);
    }
}