package com.manlyminotaursAPI.core;

import com.manlyminotaursAPI.databases.DataModelIAPI;
import com.manlyminotaursAPI.databases.TableInitializer;
import com.manlyminotaursAPI.viewControllers.roomServiceAPIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class RoomService{

    private Stage primaryStage;
    private static DataModelIAPI dataModelIAPI;
    private static String pathStrategy = "";
    private static AnchorPane root;
    private static boolean createTables = true;

    public RoomService() {
        dataModelIAPI = DataModelIAPI.getInstance();
    }


    public void thing(int xcoord, int ycoord, int windowWidth, int windowLength, String cssPath) {
        Stage primaryStage = new Stage();
        new FXMLLoader(this.getClass().getResource("FXMLs/roomServiceAPI.fxml"));

        DataModelIAPI.getInstance().startDB();

        Parent root;
        try {
            root = (Parent)FXMLLoader.load(this.getClass().getClassLoader().getResource("FXMLs/roomServiceAPI.fxml"));
        } catch (Exception var10) {
            System.out.println("failed to load the file");
            var10.printStackTrace();
            return;
        }

        Scene scene = new Scene(root, (double)windowWidth, (double)windowLength);
        primaryStage.setX((double)xcoord);
        primaryStage.setY((double)ycoord);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(listener -> save());
    }

    public void run(int xcoord, int ycoord, int windowWidth, int windowLength, String cssPath, String destNodeID, String originNodeID) throws Exception {
        String cssFile = cssPath;
        if (xcoord >= 0 && ycoord >= 0 && windowWidth >= 0 && windowLength >= 0 && xcoord <= windowWidth && ycoord <= windowLength) {
            if (cssPath == null) {
                cssFile = "/Boundary2/APIStyle.css";
            }

            this.thing(xcoord, ycoord, windowWidth, windowLength, cssFile);
        }

    }

    public void save(){
        System.out.println("closing Application");
        roomServiceAPIController apiController = new roomServiceAPIController();

        TableInitializer tableInitializer = new TableInitializer();
        tableInitializer.initTables();
        DataModelIAPI.getInstance().updateAllDatabase(apiController.getInventoryList(), apiController.getOpenList(), apiController.getClosedList(), apiController.getEmployeeList());
        DataModelIAPI.getInstance().updateAllCSVFiles();

        System.out.println("Files Saved!");
    }

}