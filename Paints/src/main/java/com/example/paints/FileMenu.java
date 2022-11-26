package com.example.paints;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;

/**
 * This class was created by Nicole Brown
 * This class includes everything in the File Menu besides open due to a
 * pixelreader issue with the colorpicker
 */
public class FileMenu {
    /**
     * Opens a new window that you can draw on
     */
    void New(){
        Stage stage = new Stage();
        try {
            new Threading().Thread("new canvas");
            new Paint().start(stage);
        } catch (IOException e) {
            System.out.println("NOPE");
        }
    }

    /**
     * Save As can save a as three different image types plus as a pdf
     * @param canv
     */
    void SaveAs(Canvas canv) {
        Stage stage = new Stage();
        FileChooser savefile = new FileChooser();
        savefile.setTitle("Save File");
        savefile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG ", "*.png"), new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"), new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        File file = savefile.showSaveDialog(stage);
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) canv.getWidth(), (int) canv.getHeight());
                canv.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }


    }


    /**
     * Save function (if the canvas hasn't been saved before then Save as will be called
     * and if the image does not have an initial directory Save As will be called)
     * @param direct
     * @param canv
     */
    void save(String direct, Canvas canv) {
        if (direct == null){
            SaveAs(canv);
        }
        else{
            File file = new File(direct);


            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage((int) canv.getWidth(), (int) canv.getHeight());
                    canv.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        }

    }


    /**
     * pastes an image (clip) on the canvas (canv) at the specified coordinates
     * @param graph
     * @param canv
     * @param clip
     * @param x
     * @param y
     * @throws FileNotFoundException
     */
    void pasteImage(GraphicsContext graph, Canvas canv, Image clip, double x, double y) throws FileNotFoundException {
        graph = canv.getGraphicsContext2D();
        if (clip != null) {
            graph.drawImage(clip, x, y);
        }

    }

    /**
     * Takes the last image on the undo stack and adds it onto the current canvas.
     * @param event
     * @param graph
     * @param canv
     * @param read
     * @param clip
     * @param x
     * @param y
     * @throws FileNotFoundException
     */
    void undoImage(ActionEvent event, GraphicsContext graph, Canvas canv, PixelReader read, Image clip, double x, double y) throws FileNotFoundException {

        graph = canv.getGraphicsContext2D();
        if (clip != null) {
            graph.drawImage(clip, x, y);
        }

    }



 }