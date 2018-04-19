package com.manlyminotaursAPI.core;

import com.manlyminotaursAPI.databases.DataModelIAPI;
import com.manlyminotaursAPI.databases.TableInitializer;
import com.manlyminotaursAPI.viewControllers.roomServiceAPIController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class Runner extends Application{

    public Runner() {
    }

    public static void main(String[] args) {
            launch(new String[0]);
    }

    public void start(Stage primaryStage) throws Exception {
        RoomService roomService = new RoomService();
        roomService.run(0, 0, 1920, 1080, (String)null, (String)null, (String)null);
    }

    // wait for application to finish,calls Platform exit, save files.
    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void stop(){
        System.out.println("closing Application");
        roomServiceAPIController apiController = new roomServiceAPIController();

        TableInitializer tableInitializer = new TableInitializer();
        tableInitializer.initTables();
        DataModelIAPI.getInstance().updateAllDatabase(apiController.getInventoryList(), apiController.getOpenList(), apiController.getClosedList(), apiController.getEmployeeList());
        DataModelIAPI.getInstance().updateAllCSVFiles();

        System.out.println("Files Saved!");
    }

}
