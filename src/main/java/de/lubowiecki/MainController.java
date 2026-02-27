package de.lubowiecki;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainController implements Initializable {

    @FXML
    private TextField eingabe;

    @FXML
    private ListView<Task> tasks;

    private Predicate<Task> filter;

    @FXML
    private void eingabeVerarbeiten(KeyEvent event) throws SQLException {
        if(event.getCode() == KeyCode.ENTER) {
            Task task = new Task();
            task.setName(eingabe.getText());
            TaskRepository.insert(task);
            updateAusgabe();
            eingabe.clear();
        }
    }

    private void updateAusgabe() throws SQLException {

        ObservableList<Task> tasks = FXCollections.observableList(TaskRepository.find());

        if(filter == null) {
            this.tasks.setItems(tasks);
        }
        else {
            this.tasks.setItems(new FilteredList<>(tasks, filter));
        }
    }

    @FXML
    private void filter(ActionEvent event) throws SQLException {

        String auswahl = ((Button)event.getSource()).getText();
        filter = switch(auswahl) {
            case "offen" -> t -> !t.isDone();
            case "erledigt" -> t -> t.isDone();
            default -> null;
        };
        updateAusgabe();
    }

    @FXML
    private void statusAendern(KeyEvent event) throws SQLException {
        if(event.getCode() == KeyCode.SPACE) {
            Task task = tasks.getSelectionModel().getSelectedItem();
            task.toggleDone();
            TaskRepository.update(task);
            updateAusgabe();
        }
        else if(event.getCode() == KeyCode.BACK_SPACE) {
            Task task = tasks.getSelectionModel().getSelectedItem();
            TaskRepository.delete(task);
            updateAusgabe();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            TaskRepository.createTable();
            updateAusgabe();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
