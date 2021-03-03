package com.example.demo.controllers;

import com.example.demo.JavaFxApplication;
import com.example.demo.models.Person;
import com.example.demo.utils.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("main.fxml")
public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person,String> firstNameColumn;
    @FXML
    private TableColumn<Person,String> lastNameColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label passportLabel;
    @FXML
    private Label liscenceLabel;
    private JavaFxApplication main;
    private Stage startStage;
    private FxWeaver fxWeaver;

    public PersonOverviewController(){}

    @FXML
    private void initialize(){

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());

        showPersonsOverviewDetails(null); //очищаем справа
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> showPersonsOverviewDetails(newValue)
        ); //при изменении слушатель изменит данные на новые из таблицы*/

    }
    public void setMain(JavaFxApplication main){
        this.main = main;
        this.fxWeaver = this.main.getFxWeaver();
        personTable.setItems(main.getPersonData());
    }

    public void setDialogStage(Stage searchStage) {
        this.startStage = searchStage;
    }

    @FXML
    private void handleDeletePerson(){
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            personTable.getItems().remove(selectedIndex);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Person selection");
            alert.setContentText("Please, select person in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = main.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonsOverviewDetails(selectedPerson);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = main.showPersonEditDialog(tempPerson);
        if (okClicked) {
            main.getPersonData().add(tempPerson);
        }
    }

    private void showPersonsOverviewDetails(Person person){
        if(person != null){
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            phoneLabel.setText(person.getPhone());
            passportLabel.setText(person.getPassport());
            liscenceLabel.setText(DateUtil.format(person.getLiscence()));
        }
        else{
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            phoneLabel.setText("");
            passportLabel.setText("");
            liscenceLabel.setText("");
        }
    }

    @FXML
    private  void handleClose(){
        startStage.close();
    }

    @FXML
    private void handleSearch(){
        FxControllerAndView<SearchWindowController, Node> controllerAndView = fxWeaver.load(SearchWindowController.class);
        Stage searchStage = new Stage();
        controllerAndView.getView().ifPresent(parent -> {
            Scene scene = new Scene((Parent) parent, 380, 280);
            searchStage.setScene(scene);
        });
        searchStage.setTitle("Search");
        searchStage.initOwner(main.getPrimaryStage());
        searchStage.initModality(Modality.WINDOW_MODAL);
        controllerAndView.getController().setSearchStage(searchStage);
        controllerAndView.getController().setMain(main);
        controllerAndView.getController().setFxWeaver(this.fxWeaver);
        searchStage.showAndWait();
    }

}
