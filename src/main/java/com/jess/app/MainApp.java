package com.jess.app;


import com.jess.app.view.IdEditDialogController;
import com.jess.app.view.WeatherSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


/**
 * Created by olesia on 07.05.16.
 */
public class MainApp extends Application {


    private Stage primaryStage;

    public MainApp() {

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
//        this.primaryStage.initStyle(StageStyle.UTILITY);
        this.primaryStage.resizableProperty().setValue(Boolean.FALSE);
        this.primaryStage.setTitle("My Open Weather");
        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
        showWeatherOverview();

    }

    public void showWeatherOverview() {
        try {

            FXMLLoader loader = new FXMLLoader();


//            loader.setLocation(MainApp.class.getResource("view/weatherScene.fxml"));
            loader.setLocation(MainApp.class.getClassLoader().getResource("view/weatherScene.fxml"));
            AnchorPane weatherOverview = (AnchorPane) loader.load();

            WeatherSceneController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(weatherOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean showIdEditDialog(WeatherSceneController weatherSceneController){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getClassLoader().getResource("view/idChangeLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("City Change");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.resizableProperty().setValue(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            IdEditDialogController controller = loader.getController();
            controller.setWeatherController(weatherSceneController);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
