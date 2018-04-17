package com.manlyminotaurs.core;

import com.manlyminotaurs.databases.DataModelI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {

    static AnchorPane root; //root holds all other screens
    static boolean createTables = true;

    private static DataModelI dataModelI = DataModelI.getInstance();
    public static String pathStrategy = "";

    public int xcoord;
    public int ycoord;
    public int windowWidth;
    public int windowLength;
    public String cssPath;
    public String destNodeID;
    public String originNodeID;

    public void run(int xcoord, int ycoord, int windowWidth, int windowLength, String cssPath, String destNodeID, String originNodeID) throws Exception {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.windowWidth = windowWidth;
        this.windowLength = windowLength;
        this.cssPath = cssPath;
        this.destNodeID = destNodeID;
        this.originNodeID = originNodeID;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
        //root is anchor pane that all other screens will be held in
        root = FXMLLoader.load(getClass().getClassLoader().getResource("FXMLs/roomServiceAPI.fxml"));

        Scene world = new Scene(root, 1920, 1080);
        primaryStage.setTitle("Brigham and Women's Hospital Navigation");
        //add style sheets here

       // world.getStylesheets().add(getClass().getResource("landingStyle.css").toExternalForm());
        primaryStage.setScene(world);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        //set Stage boundaries to visible bounds of the main screen
        //primaryStage.setX(primaryScreenBounds.getMinX());
       // primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());

        primaryStage.show();
    }catch(Exception e){
        e.printStackTrace();
    }
}
    // wait for application to finish,calls Platform exit, save files.
    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }
    @Override
    public void stop(){
        System.out.println("closing Application");

        DataModelI.getInstance().updateAllCSVFiles();

        System.out.println("Files Saved!");
    }

    public static void main(String[] args) throws IOException {
        if(createTables) {
            System.out.println("version 7");
            DataModelI.getInstance().startDB();
        }
        launch(args);
    }
}