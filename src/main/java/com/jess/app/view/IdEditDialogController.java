package com.jess.app.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.net.WeatherParser;


import java.net.ConnectException;
import java.net.UnknownHostException;

/**
 * Created by olesia on 09.05.16.
 */
public class IdEditDialogController {
    @FXML
    private TextField idUpdate;

    private Stage dialogStage;
    private boolean okClicked = false;
    private WeatherSceneController weatherController;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            long id = Long.parseLong(idUpdate.getText());
            try {
                weatherController.showWeatherDetails(id);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            weatherController.writeIdToFile(id);
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleHyperLink() {
//        Thread myThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Desktop.getDesktop().browse(new URI("http://openweathermap.org/city/703448"));
//                } catch (Exception e) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.initOwner(dialogStage);
//                    alert.setTitle("404 not found");
//                    alert.setHeaderText("ConnectionError");
//                    alert.setContentText("Check your internet connection");
//
//                    alert.showAndWait();
//                }
//            }
//        });
//        myThread.run();


    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (idUpdate.getText() == null || idUpdate.getText().length() == 0) {
            errorMessage = "Not valid input";
        }
        String txt = idUpdate.getText();
        if (!txt.matches("\\d*")) {
            errorMessage = "id should be integer";
        }
        try {
            Long.parseLong(txt);
        } catch (NumberFormatException e) {
            errorMessage = "wrong number format";
        }


        WeatherParser wp = new WeatherParser();
        try {
            wp.getProtectedCity(Long.parseLong(txt));
        } catch (ConnectException e) {
            e.printStackTrace();
            errorMessage = "wrong id or lost internet connection ";
        } catch (NullPointerException e) {
            e.printStackTrace();
            errorMessage = "wrong id or lost internet connection ";
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "wrong id or lost internet connection ";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please correct invalid id");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }

    }

    public WeatherSceneController getWeatherController() {
        return weatherController;
    }

    public void setWeatherController(WeatherSceneController weatherController) {
        this.weatherController = weatherController;
    }
}
