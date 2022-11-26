package com.example.paints;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;

/**
 * This class was created by Nicole Brown
 * This class includes all the Warning messages and text menus
 */
public class HelpMenu {
    /**
     * Brings up a text menu that tells the user about Paint
     * @param event
     */
    void About(ActionEvent event) {

        File file = new File("C:\\Users\\nicol\\Desktop\\Cs250\\Paints\\src\\main\\assets\\About.txt");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists())
            try {
                desktop.open(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    //*******************************    Help menu   **************************************\\

    /**
     * Brings up a text menu that tells the user some things that may help them
     * @param event
     */
    void HELP(ActionEvent event) {
        File file = new File("C:\\Users\\nicol\\Desktop\\Cs250\\Paints\\src\\main\\assets\\HELP.txt");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists())
            try {
                desktop.open(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * Brings a Warning message before Save As in case the user saves a different file format
     * @param canv
     * @throws IOException
     */
    @FXML
    void savingNewFileType(Canvas canv) throws IOException {
    Alert alert = new Alert(Alert.AlertType.WARNING);
//Set text in conveinently pre-defined layout
            alert.setTitle("Warning");
            alert.setHeaderText("In case you save a different file type from the original some content may be lost!");
            alert.setContentText("");
    //Set custom buttons
    ButtonType okButton = new ButtonType("Okay", OK_DONE);
   // ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton);
//Prevent all interaction with application until resolved.
            alert.initModality(Modality.APPLICATION_MODAL);
//Launch
            alert.showAndWait().ifPresent(response -> {
        if (response == okButton) {
           new FileMenu().SaveAs(canv);
        }

    });
}

    /**
     * Brings up a Warning message that warns the user that they have not saved their artwork, and they are trying to leave
     * @param event
     * @param direct
     * @param canv
     * @param stage
     * @throws IOException
     */
    void ExitWithoutSave(WindowEvent event, String direct, Canvas canv, Stage stage) throws IOException {
        //Create an Alert with predefined warning image
        Alert alert = new Alert(Alert.AlertType.WARNING);
//Set text in conveinently pre-defined layout
        alert.setTitle("Warning");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to close the application?");
//Set custom buttons
        ButtonType okButton = new ButtonType("Yes, exit", OK_DONE);
        ButtonType cancelButton = new ButtonType("No, go back to Paint", OK_DONE);
        ButtonType sButton = new ButtonType("Save", OK_DONE);
        ButtonType SAButton = new ButtonType("Save As", OK_DONE);

        alert.getButtonTypes().setAll(okButton, cancelButton, sButton, SAButton);
//Prevent all interaction with application until resolved.
        alert.initModality(Modality.APPLICATION_MODAL);
//Launch
        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                stage.close();
            } else if (response == sButton) {
                new FileMenu().save(direct, canv);
                Platform.exit();
            } else if (response == SAButton) {
               new FileMenu().SaveAs(canv);
                Platform.exit();
            }

        });
    }



}
