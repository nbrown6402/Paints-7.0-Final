package com.example.paints;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;

/**
 * this class was created by Nicole Brown
 * this class includes open, edit menu, and calls to all other classes
 * Everything rotates around this class
 */
public class PaintController {

    Toolkit toolkit;
    Timer timer;
    @FXML
    private RadioMenuItem copy;
    @FXML
    private RadioMenuItem paste;
    private Image clipboard;
    @FXML
    private TextField brushSize;

    @FXML
    private RadioButton col;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private RadioButton cork;
    @FXML
    private RadioButton dash;
    @FXML
    private RadioButton eraser;
    @FXML
    private RadioMenuItem cutting;
    @FXML
    private RadioMenuItem srotate;
    @FXML
    private RadioButton lin;
    @FXML
    private RadioButton obo;
    @FXML
    private RadioButton pencil;

    @FXML
    private RadioButton rect;
    @FXML
    private RadioButton sqar;
    @FXML
    private RadioButton npoly;
    @FXML
    private GraphicsContext graph;
    public String direct;
    private PixelReader read;
    public static boolean smart = false;
    @FXML
    private CheckBox AutoSaveChecker;
    double[] xNpoly, yNpoly;
    int pointCt;
    boolean complete;
    private double x, y;
    @FXML
    private Canvas canv;
    @FXML
    private RadioButton rr;
    Stack<javafx.scene.image.Image> undoHistory = new Stack();
    Stack<javafx.scene.image.Image> redoHistory = new Stack();


    /**
     * opens an image on the current canvas
     * @throws IOException
     */
    @FXML
    void OpeningImage() throws IOException {
        graph = canv.getGraphicsContext2D();
        new Threading().Thread("Image opened");
        graph.clearRect(0, 0, canv.getWidth(), canv.getHeight());
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setInitialDirectory(new File("C:\\Users\\nicol\\Pictures\\images"));
        File pic = fileChooser.showOpenDialog(null);

        direct = pic.toString();

        if(direct == null) {
            smart = false;
        }else{smart = true;}


        if (pic != null)
            try {
                InputStream im = new FileInputStream(pic);
                Image open = new Image(im);
                read = open.getPixelReader();
                double oh = open.getHeight();
                double ow = open.getWidth();
                canv.setHeight(oh);
                canv.setWidth(ow);


                graph.drawImage(open, 0, 0);
            } catch (IOException e) {
                System.out.println("NO IMAGE FOR YOU!");
            }

    }


    /**
     * calls to the Save As function in the FileMenu class
     * @throws IOException
     */
    @FXML
    void SavingNewImage() throws IOException {
        smart = true;
        new HelpMenu().savingNewFileType(canv);
    }

    //**********************    Save   **************************************\\

    /**
     * calls to the Save function in the FileMenu class
     * @param event
     * @throws IOException
     */
    @FXML
    void save(ActionEvent event) throws IOException {
        smart = true;
        new Threading().Thread("Save");
        new FileMenu().save(direct, canv);

    }

    /**
     * initializes all drawing tools, rotates, copy, cut, paste, color picker, and changing the width of a line
     */
    public void initialize() {
        GraphicsContext graph = canv.getGraphicsContext2D();
        colorPicker.setValue(Color.BLACK);
        brushSize.setText("10");

        Rectangle rec = new Rectangle();
        Circle circ = new Circle();
        Ellipse elip = new Ellipse();
        Line line = new Line();
        Line lime = new Line();
        Rectangle rore = new Rectangle();

        xNpoly = new double[500];  // create arrays to hold the polygon's points
        yNpoly = new double[500];
        pointCt = 0;


//***************************    Mouse Clicked  **************************************\\
        canv.setOnMouseClicked(e -> {
            smart = false;
            double x = e.getX();
            double y = e.getY();

            if (col.isSelected()) {
                Color color = read.getColor((int) x, (int) y);
                colorPicker.setValue(color);
                new drawing().savingDrawnImage(canv, graph);
            }

        });

//***************************    Mouse Pressed  **************************************\\
        canv.setOnMousePressed(e -> {
            x = e.getX();
            y = e.getY();

            if (rect.isSelected()) {
                new drawing().coloring(graph, colorPicker, brushSize);
                rec.setLocation((int) e.getX(), (int) e.getY());

            } else if (sqar.isSelected()) {
                new drawing().coloring(graph, colorPicker, brushSize);
                rec.setLocation((int) e.getX(), (int) e.getY());

            } else if (cork.isSelected()) {
                new drawing().coloring(graph, colorPicker, brushSize);
                circ.setCenterX(e.getX());
                circ.setCenterY(e.getY());

            } else if (obo.isSelected()) {
                new drawing().coloring(graph, colorPicker, brushSize);
                elip.setCenterX(e.getX());
                elip.setCenterY(e.getY());

            } else if (lin.isSelected()) {
                new drawing().coloring(graph, colorPicker, brushSize);
                line.setStartX(e.getX());
                line.setStartY(e.getY());

            } else if (dash.isSelected()) {
                new drawing().coloring(graph, colorPicker, brushSize);
                lime.setStartX(e.getX());
                lime.setStartY(e.getY());

            }else if (rr.isSelected()){
                    new drawing().coloring(graph, colorPicker, brushSize);
                    rore.setLocation((int) e.getX(), (int) e.getY());

            } else if (copy.isSelected()) {
                rec.setLocation((int) e.getX(), (int) e.getY());
            } else if (cutting.isSelected()) {
                rec.setLocation((int) e.getX(), (int) e.getY());
            } else if (srotate.isSelected()) {
                rec.setLocation((int) e.getX(), (int) e.getY());
            } else if (rr.isSelected()) {
                rore.setLocation((int) e.getX(), (int) e.getY());

            } else if (npoly.isSelected()) {
                graph.setLineDashes(0);
                if (complete) {
                    // Start a new polygon at the point that was clicked.
                    complete = false;
                    xNpoly[0] = e.getX();
                    yNpoly[0] = e.getY();
                    pointCt = 1;
                } else if (pointCt > 0 && pointCt > 0 && (Math.abs(xNpoly[0] - e.getX()) <= 3)
                        && (Math.abs(yNpoly[0] - e.getY()) <= 3)) {
                    // User has clicked near the starting point.
                    // The polygon is complete.
                    complete = true;
                } else if (e.getButton() == MouseButton.SECONDARY || pointCt == 500) {
                    // The polygon is complete.
                    complete = true;
                    Image ime = getRegion(0, 0, canv.getWidth(), canv.getHeight());
                    graph.drawImage(ime, 0, 0, ime.getWidth(), ime.getHeight(), ime.getWidth(), 0, ime.getWidth(), ime.getHeight());

                } else {
                    // Add the point where the user clicked to the list of
                    // points in the polygon, and draw a line between the
                    // previous point and the current point.  A line can
                    // only be drawn if there are at least two points.
                    xNpoly[pointCt] = e.getX();
                    yNpoly[pointCt] = e.getY();
                    pointCt++;
                }
                new drawing().savingDrawnImage(canv, graph);
                new drawing().draw(canv, pointCt, complete, colorPicker, xNpoly, yNpoly, brushSize);
            }
        });

//***************************    Mouse Dragged   **************************************\\
        canv.setOnMouseDragged(e -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            if (eraser.isSelected()) {
                graph.clearRect(x, y, size, size);
                new drawing().savingDrawnImage(canv, graph);
                add2undo();

            } else if (pencil.isSelected()) {
                graph.setFill(colorPicker.getValue());
                graph.fillOval(x, y, size, size);
                new drawing().savingDrawnImage(canv, graph);
                add2undo();
            }

        });

//***************************    Mouse Released   **************************************\\
        canv.setOnMouseReleased(e -> {
            if (rect.isSelected()) {
                new drawing().rectangle(graph, rec, canv, e);
                add2undo();

            } else if (sqar.isSelected()) {
                new drawing().square(graph, rec, canv, e);
                add2undo();

            } else if (cork.isSelected()) {
                new drawing().circle(graph, circ, canv, e);
                add2undo();

            } else if (obo.isSelected()) {
                new drawing().ellipse(graph, elip, canv, e);
                add2undo();

            } else if (lin.isSelected()) {
                new drawing().line(graph, line, canv, e);
                add2undo();


            } else if (dash.isSelected()) {
                new drawing().dashedLine(graph, lime, canv, e);
                add2undo();

            } else if (copy.isSelected()) {
                try {
                    new Threading().Thread("copy");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                rec.setSize((int) Math.abs((e.getX() - rec.getX())), (int) Math.abs((e.getY() - rec.getY())));
                if (rec.getX() > e.getX() || rec.getY() > e.getY()) {
                    rec.setLocation((int) rec.getX(), (int) rec.getY());
                }

                clipboard = getRegion(rec.getX(), rec.getY(), e.getX(), e.getY());
                add2undo();
            } else if (cutting.isSelected()) {
                try {
                    new Threading().Thread("cut");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                graph.setLineDashes(0);
                rec.setSize((int) Math.abs((e.getX() - rec.getX())), (int) Math.abs((e.getY() - rec.getY())));
                if (rec.getX() > e.getX() || rec.getY() > e.getY()) {
                    rec.setLocation((int) rec.getX(), (int) rec.getY());
                }

                clipboard = getRegion(rec.getX(), rec.getY(), e.getX(), e.getY());
                graph.clearRect(rec.getX(), rec.getY(), e.getX(), e.getY());
                add2undo();
            } else if (srotate.isSelected()) {
                try {
                    new Threading().Thread("sectional rotate");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                rec.setSize((int) Math.abs((e.getX() - rec.getX())), (int) Math.abs((e.getY() - rec.getY())));
                if (rec.getX() > e.getX() || rec.getY() > e.getY()) {
                    rec.setLocation((int) rec.getX(), (int) rec.getY());
                }
                Image ime = getRegion(rec.getX(), rec.getY(), e.getX(), e.getY());
                graph.drawImage(ime, 0, 0, ime.getWidth(), ime.getHeight(), rec.getX() + ime.getWidth(), rec.getY() + ime.getHeight(), -ime.getHeight(), -ime.getWidth());
                add2undo();
            } else if (paste.isSelected()) {
                if (clipboard != null) {
                    try {
                        new Threading().Thread("paste");
                        new FileMenu().pasteImage(graph, canv, clipboard, x, y);
                        add2undo();
                    } catch (Exception f) {
                        System.out.println("AHHHH");
                    }
                } else System.out.println("no clipboard");
            } else if (rr.isSelected()) {
                new drawing().roundedRectangle(graph, rore, canv, e);
                add2undo();
            }

        });

    }

    /**
     * undoes the current action
     * @param event
     * @throws IOException
     */
    @FXML
    void undo(ActionEvent event) throws IOException {
        new Threading().Thread("undo");
        Image im = undoHistory.pop();
        if (!undoHistory.empty()) {
            redoHistory.push(im);
            new FileMenu().undoImage(event, graph, canv, read, undoHistory.peek(), 0, 0);
        } else {   //puts image back because in this case it's the base/only one in stack
            new FileMenu().undoImage(event, graph, canv, read, im, 0, 0);
            undoHistory.push(im);
        }
    }

    /**
     * adds the current region to the undo stack so the action can be undone
     */
    public void add2undo() {
        Image im = getRegion(0, 0, canv.getWidth(), canv.getHeight());
        undoHistory.push(im);
    }

    /**
     * redoes the last action and adds it back to the canvas
     * @param event
     * @throws IOException
     */
    @FXML
    void redo(ActionEvent event) throws IOException {
        new Threading().Thread("redo");
        if (!redoHistory.empty()) {
            Image im = redoHistory.pop();
            undoHistory.push(im);
            new FileMenu().undoImage(event, graph, canv, read, im, 0, 0);
        }

    }

    /**
     * calls the about menu in the fileMenu class
     * @param event
     */
    @FXML
    void About(ActionEvent event) {
        new HelpMenu().About(event);
    }


    /**
     * calls the help menu in the fileMenu class
     * @param event
     */
    @FXML
    void HELP(ActionEvent event) {
        new HelpMenu().HELP(event);
    }

    /**
     * calls the new function in the fileMenu class
     * @param event
     * @throws IOException
     */

    @FXML
    void New(ActionEvent event) throws IOException {
        new FileMenu().New();
    }

    /**
     * calls the exit warning message in the HelpMenu class if the art piece has not recently been saved
     * @param w
     * @param stage
     * @throws IOException
     */
    @FXML
    void bye(WindowEvent w, Stage stage) throws IOException {
        if (smart == true) {
            stage.close();
        } else {
            new HelpMenu().ExitWithoutSave(w, direct, canv, stage);
        }
    }


    /**
     * gives a warning message before clearing the canvas and clears the canvas
     * @param event
     * @throws IOException
     */
    @FXML
    void clearcanv(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to clear the application?");
        ButtonType okButton = new ButtonType("Yes", OK_DONE);
        ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                graph = canv.getGraphicsContext2D();
                graph.clearRect(0, 0, canv.getWidth(), canv.getHeight());
            }
        });

    }
//----------------------------------------------------------------------------------------------

    /**
     * Takes the current canvas and makes it an image
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public Image getRegion(double x1, double y1, double x2, double y2) {
        SnapshotParameters snap = new SnapshotParameters();
        WritableImage write = new WritableImage((int) Math.abs(x1 - x2), (int) Math.abs(y1 - y2));

        snap.setViewport(new Rectangle2D(x1, y1, x2, y2));

        canv.snapshot(snap, write);
        return write;
    }

    /**
     * Creates the timer for the Auto Save
     * Would have moved to the FileMenu class, but there were issues when moved
     * so it must stay
     */
    public class AnnoyingBeep {
        public void AnnoyingBeep() {
            toolkit = Toolkit.getDefaultToolkit();
            timer = new Timer();
            timer.schedule(new RemindTask(), 0, 30 * 1000);  //subsequent rate
        }

        class RemindTask extends TimerTask {

            public void run() {
                toolkit.beep();
                System.out.println("beep");
                Platform.runLater(() -> {
                    new FileMenu().save(direct, canv);
                });
            }

        }
    }


    /**
     * If the Auto save button is checked then the Smart save timer will be activated, if it is not checked
     * then the timer is cancelled and there will be no auto save
     */
    @FXML
    void autoSave(ActionEvent event) {
        if (AutoSaveChecker.isSelected()) {
            new AnnoyingBeep().AnnoyingBeep();
        } else timer.cancel();

    }

    /**
     * Rotates the canvs 180 degrees
     * @param event
     * @throws IOException
     */
    @FXML
    void r180(ActionEvent event) throws IOException {
        canv.setRotate(canv.getRotate() + 180);
        new Threading().Thread("rotate 180");
    }

    /**
     * rotates the canvas 270 degrees
     * @param event
     * @throws IOException
     */
    @FXML
    void r270(ActionEvent event) throws IOException {
        canv.setRotate(canv.getRotate() + 270);
        new Threading().Thread("rotate 270");
    }

    /**
     * rotates the canvas 90 degrees
     * @param event
     * @throws IOException
     */
    @FXML
    void r90(ActionEvent event) throws IOException {
        canv.setRotate(canv.getRotate() + 90);
        new Threading().Thread("rotate 90");
    }

    /**
     * flips the canvas vertically
     * @param event
     * @throws IOException
     */
    @FXML
    void vert(ActionEvent event) throws IOException {
            canv.setRotate(canv.getRotate() + 180);
            new Threading().Thread("vertical flip");
    }

    /**
     * flips the canvas horizontally
     * @param event
     */
    @FXML
    void horizontal(ActionEvent event) {
       if(canv.getScaleY()==1) {
           canv.setTranslateX(0);
           canv.setScaleX(1);
           canv.setScaleY(-1);
       }else if(canv.getScaleY()==-1){
           canv.setTranslateX(1);
           canv.setScaleX(1);
           canv.setScaleY(1);

       }
}

    /**
     * adds the pencil to the threading file when clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void pen(MouseEvent event) throws IOException {
        new Threading().Thread("pencil");
    }

    /**
     * adds the eraser to the threading file when clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void erase(MouseEvent event) throws IOException {
        new Threading().Thread("eraser");
    }

    /**
     * adds the eye dropper to the threading file when clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void dropper(MouseEvent event) throws IOException {
        new Threading().Thread("eye dropper");
    }

    /**
     * adds the rectangle to the threading file when clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void rectangle(MouseEvent event) throws IOException {
        new Threading().Thread("rectangle");
    }

    /**
     * adds the circle to the threading file when clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void circle(MouseEvent event) throws IOException {
        new Threading().Thread("circle");
    }

    /**
     * adds the line to the threading file when clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void line(MouseEvent event) throws IOException {
        new Threading().Thread("line");
    }

    /**
     * adds the oval/ellipse to the threading file when clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void oval(MouseEvent event) throws IOException {
        new Threading().Thread("oval");
    }

    /**
     * adds the square to the threading file when clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void square(MouseEvent event) throws IOException {
        new Threading().Thread("square");
    }

    /**
     * adds the dashed line to the threading file when clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void dashedLine(MouseEvent event) throws IOException {
        new Threading().Thread("dashed line");
    }

    /**
     * adds the npolygon to the threading file when clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void npoly(MouseEvent event) throws IOException {
        new Threading().Thread("npoly");
    }

    /**
     * adds the rounded rectanlge to the threading file when clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void roundedRect(MouseEvent event) throws IOException {
        new Threading().Thread("rounded rectangle");
    }





}



