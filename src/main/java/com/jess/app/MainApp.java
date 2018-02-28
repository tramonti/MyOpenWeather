package com.jess.app;

import com.jess.app.view.IdEditDialogController;
import com.jess.app.view.WeatherSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage;
    private Stage idEditStage;
    private WeatherSceneController weatherSceneController;
    private IdEditDialogController idEditDialogController;

    public MainApp() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        buildPrimaryStage();
        this.primaryStage.show();
    }

    private void buildPrimaryStage() {
        setUpPrimaryStage();
        showWeatherOverview();
    }

    private void setUpPrimaryStage() {
//        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setTitle("My Open Weather");
        makeNotResizable(primaryStage);
        primaryStage.setOnCloseRequest(event -> fullProgramExit());
    }

    private void makeNotResizable(Stage stage) {
        stage.resizableProperty().setValue(Boolean.FALSE);
    }

    private void fullProgramExit() {
        Platform.exit();
        System.exit(0);
    }

    private void showWeatherOverview() {
        try {
            FXMLLoader loader = getLoaderFromResource("view/weatherScene.fxml");
            weatherSceneController = loader.getController();
            weatherSceneController.setMainApp(this);
            primaryStage.setScene(retrieveScene(loader));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FXMLLoader getLoaderFromResource(String resourcePath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getClassLoader().getResource(resourcePath));
        loader.load();
        return loader;
    }

    private Scene retrieveScene(FXMLLoader loader) throws IOException {
        AnchorPane loaderPane = loader.getRoot();
        return new Scene(loaderPane);
    }

    public boolean showIdEditDialog() {
        try {
            setUpIdEditDialogStage();
            setUpIdEditController();
            idEditStage.showAndWait();
            return idEditDialogController.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setUpIdEditDialogStage() {
        idEditStage = new Stage();
        idEditStage.setTitle("City Change");
        idEditStage.initModality(Modality.WINDOW_MODAL);
        idEditStage.initOwner(primaryStage);
        makeNotResizable(idEditStage);
    }

    private void setUpIdEditController() throws IOException {
        FXMLLoader loader = getLoaderFromResource("view/idChangeLayout.fxml");
        idEditStage.setScene(retrieveScene(loader));
        idEditDialogController = loader.getController();
        idEditDialogController.setWeatherController(weatherSceneController);
        idEditDialogController.setDialogStage(idEditStage);
    }
}