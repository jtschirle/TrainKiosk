package com.example.trainkiosk;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SuccessController {
    AudioPlayer player = new AudioPlayer();

    @FXML
    protected void changeScreenBackButtonPushed(ActionEvent event) throws IOException {
        player.playClick();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 550);
        scene.getStylesheets().add("main.css");

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
