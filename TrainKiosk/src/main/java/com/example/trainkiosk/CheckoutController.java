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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable {
    AudioPlayer player = new AudioPlayer();
    ObservableList<ScheduleData> list = FXCollections.observableArrayList();
    Cities selectedCity;

    @FXML
    private Label errorText;
    @FXML
    Label details;
    @FXML
    Label total;
    @FXML
    TextField nameOnCard;
    @FXML
    TextField cardNumber;
    @FXML
    TextField expDate;
    @FXML
    TextField cvv;
    @FXML
    CheckBox authorize;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardNumber.textProperty().addListener(new ChangeListener(cardNumber, 16));
        expDate.textProperty().addListener(new ChangeListener(expDate, 4));
        cvv.textProperty().addListener(new ChangeListener(cvv, 4));
    }

    @FXML
    protected void changeScreenBackButtonPushed(ActionEvent event) throws IOException {
        player.playClick();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Application.class.getResource("schedule-view.fxml"));
        Parent tableViewParent = fxmlLoader.load();
        ScheduleController controller = fxmlLoader.getController();
        controller.initTableView(list, selectedCity);

        Scene scene = new Scene(tableViewParent);
        scene.getStylesheets().add("main.css");

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void changeScreenSubmitButtonPushed(ActionEvent event) throws IOException {
        if(!processPayment()) {
            player.playError();
            errorText.setText("Unsuccessful! Please try a different card!");
            return;
        }

        player.playSuccess();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Application.class.getResource("success-view.fxml"));
        Parent tableViewParent = fxmlLoader.load();
        SuccessController controller = fxmlLoader.getController();

        Scene scene = new Scene(tableViewParent);
        scene.getStylesheets().add("main.css");

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void initDetails(){
        StringBuilder sb = new StringBuilder();
        long totalPrice = 0;

        for(ScheduleData sd : list){
            if(sd.getQty() > 0){
                sb.append(String.format("%s %s %s x%d : $%d %n", sd.getDestination(),
                        sd.getDate(), sd.getDeparture(), sd.getQty(), sd.getPrice()));
                totalPrice+=sd.getPrice();
            }
        }
        details.setText(sb.toString());
        total.setText("Total : $" + totalPrice);
    }

    public boolean processPayment(){
        return nameOnCard.getText().equals("test") && cardNumber.getText().equals("4111111111111111")
                && expDate.getText().equals("1221") && cvv.getText().equals("123") && authorize.isSelected();
    }

    public ObservableList<ScheduleData> getList() {
        return list;
    }

    public void setList(ObservableList<ScheduleData> list) {
        this.list = list;
        initDetails();
    }

    public Label getDetails() {
        return details;
    }

    public void setDetails(Label details) {
        this.details = details;
    }

    public Label getTotal() {
        return total;
    }

    public void setTotal(Label total) {
        this.total = total;
    }

    public Cities getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(Cities selectedCity) {
        this.selectedCity = selectedCity;
    }

    
}
