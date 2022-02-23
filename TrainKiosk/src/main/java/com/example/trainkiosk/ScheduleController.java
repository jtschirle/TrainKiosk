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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {
    AudioPlayer player = new AudioPlayer();
    ObservableList<ScheduleData> list = FXCollections.observableArrayList();
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
    Calendar currentTime = Calendar.getInstance();
    Date initialDate = currentTime.getTime();

    Cities selectedCity;

    @FXML
    TableView<ScheduleData> tableView = new TableView<>();
    @FXML
    TableColumn<ScheduleData, String> destination = new TableColumn<>();
    @FXML
    TableColumn<ScheduleData, String> date = new TableColumn<>();
    @FXML
    TableColumn<ScheduleData, String> departure = new TableColumn<>();
    @FXML
    TableColumn<ScheduleData, Integer> qty = new TableColumn<>();
    @FXML
    TableColumn<ScheduleData, Long> price = new TableColumn<>();

    @FXML
    Label errorText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        departure.setCellValueFactory(new PropertyValueFactory<>("departure"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

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

    @FXML
    protected void changeScreenCheckoutButtonPushed(ActionEvent event) throws IOException {
        if(!checkQty()){
            player.playError();
            errorText.setText("Nothing Selected! Please select a destination!");
            return;
        }
        player.playClick();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Application.class.getResource("checkout-view.fxml"));
        Parent checkoutViewParent = fxmlLoader.load();
        CheckoutController controller = fxmlLoader.getController();
        controller.setSelectedCity(selectedCity);
        controller.setList(tableView.getItems());

        Scene scene = new Scene(checkoutViewParent);
        scene.getStylesheets().add("main.css");

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void setSelectedCity(String city) {
        for(Cities c : Cities.values()){
            if(c.getName().equals(city))
                selectedCity = c;
        }
        initTableView();
    }

    public String getSelectedCity(){
        return String.format("%s → %s", Cities.SALT_LAKE_CITY.getName(), selectedCity.getName());
    }

    public void initTableView(){
        String d, t;
        for(int i = 0; i < 15; i++){
            currentTime.add(Calendar.HOUR, 6);
            currentTime.add(Calendar.MINUTE, 15);
            d = dateFormat.format(currentTime.getTime());
            t = timeFormat.format(currentTime.getTime());

            list.add(new ScheduleData(getSelectedCity(), d, t, 0, 0));
        }
        tableView.setItems(list);
    }

    public void initTableView(ObservableList<ScheduleData> checkoutList, Cities city){
        list = checkoutList;
        selectedCity = city;
        tableView.setItems(list);
    }

    @FXML
    public void increaseSelectedQty(){
        ScheduleData sd = tableView.getSelectionModel().getSelectedItem();
        if(sd == null){
            player.playError();
            errorText.setText("Please select an option above!");
            return;
        }
        else
            errorText.setText("");

        sd.setQty(sd.getQty()+1);
        sd.setPrice(sd.getPrice()+selectedCity.getPrice());
        tableView.getItems().add(tableView.getSelectionModel().getSelectedIndex(),
                new ScheduleData(sd.getDestination(),sd.getDate(),sd.getDeparture(),
                        sd.getQty(),sd.getPrice()));
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
        player.playClick();
    }

    public void decreaseSelectedQty(){
        ScheduleData sd = tableView.getSelectionModel().getSelectedItem();
        if(sd == null){
            player.playError();
            errorText.setText("Please select an option above!");
            return;
        }
        else
            errorText.setText("");

        if(sd.getQty() != 0){
            sd.setQty(sd.getQty()-1);
            sd.setPrice(sd.getPrice()-selectedCity.getPrice());
        }
        else{
            sd.setQty(0);
            sd.setPrice(0);
        }
        tableView.getItems().add(tableView.getSelectionModel().getSelectedIndex(),
                new ScheduleData(sd.getDestination(),sd.getDate(),sd.getDeparture(),
                        sd.getQty(),sd.getPrice()));
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
        player.playClick();
    }

    public void resetQty(){
        player.playClick();
        currentTime.setTime(initialDate);
        tableView.getItems().clear();
        initTableView();
    }

    public boolean checkQty(){
        for(ScheduleData sd : tableView.getItems()){
            if(sd.getQty() > 0)
                return true;
        }
        return false;
    }
}
