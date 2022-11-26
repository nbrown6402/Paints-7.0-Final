package com.example.paints;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.awt.*;

/**
 * This class was created by Nicole Brown
 * This class includes things that would aid in drawing
 */
public class drawing {

    /**
     * Sets the color of the line that is being drawn, sets the line width of the line being drawn, and sets the fill
     * of the shape being drawn (most are transparent)
     *
     * @param graph
     * @param colorPicker
     * @param brushSize
     */
    public void coloring(GraphicsContext graph, ColorPicker colorPicker, TextField brushSize) {
        graph.setStroke(colorPicker.getValue());
        graph.setFill(colorPicker.getValue());
        graph.setLineWidth(Double.parseDouble(brushSize.getText()));

    }


    /**
     * Starts to draw an n sided polygon when the mouse is pressed
     *
     * @param canvas
     * @param pointCt
     * @param complete
     * @param colorPicker
     * @param xCoord
     * @param yCoord
     */

    void draw(Canvas canvas, int pointCt, boolean complete, ColorPicker colorPicker, double[] xCoord, double[] yCoord, TextField brushSize) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        if (pointCt == 0)
            return;
        g.setLineWidth(Double.parseDouble(brushSize.getText()));
        g.setStroke(colorPicker.getValue());
        if (complete) { // draw a polygon
            g.fillPolygon(xCoord, yCoord, pointCt);
            g.strokePolygon(xCoord, yCoord, pointCt);

        } else { // show the lines the user has drawn so far
            //g.setFill();
            g.fillRect(xCoord[0] - 2, yCoord[0] - 2, 4, 4);  // small square marks first point
            for (int i = 0; i < pointCt - 1; i++) {
                g.strokeLine(xCoord[i], yCoord[i], xCoord[i + 1], yCoord[i + 1]);
            }
        }
    }

    /**
     * Saves the drawn image that has been drawn so any of the other tools don't remove what you've done
     * @param canv
     * @param graph
     */
    void savingDrawnImage(Canvas canv, GraphicsContext graph) {
        Image ime = getRegion(0, 0, canv.getWidth(), canv.getHeight(), canv);
        graph.drawImage(ime, 0, 0, ime.getWidth(), ime.getHeight(), ime.getWidth(), 0, ime.getWidth(), ime.getHeight());
    }

    /**
     * Saves the canvas as an image that is used to be redrawn in the canvas so that other tools don't remove what you've done
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param canv
     * @return
     */
    public Image getRegion(double x1, double y1, double x2, double y2, Canvas canv) {
        SnapshotParameters snap = new SnapshotParameters();
        WritableImage write = new WritableImage((int) Math.abs(x1 - x2), (int) Math.abs(y1 - y2));

        snap.setViewport(new Rectangle2D(x1, y1, x2, y2));

        canv.snapshot(snap, write);
        return write;
    }

    /**
     * Assists with drawing a rectangle when the mouse is released
     * @param graph
     * @param rec
     * @param canv
     * @param e
     */
    void rectangle(GraphicsContext graph, Rectangle rec, Canvas canv, MouseEvent e) {
        graph.setLineDashes(0);
        rec.setSize((int) Math.abs((e.getX() - rec.getX())), (int) Math.abs((e.getY() - rec.getY())));
        if (rec.getX() > e.getX() || rec.getY() > e.getY()) {
            rec.setLocation((int) rec.getX(), (int) rec.getY());
        }
        graph.setFill(Color.TRANSPARENT);
        graph.fillRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight());
        graph.strokeRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight());
        new drawing().savingDrawnImage(canv, graph);
    }

    /**
     * Assists with drawing a square when the mouse is released
     * @param graph
     * @param rec
     * @param canv
     * @param e
     */
    void square(GraphicsContext graph, Rectangle rec, Canvas canv, MouseEvent e) {
        graph.setLineDashes(0);
        rec.setSize((int) Math.abs((e.getX() - rec.getX())), (int) Math.abs((e.getY() - rec.getY())));
        if (rec.getX() > e.getX() || rec.getY() > e.getY()) {
            rec.setLocation((int) rec.getX(), (int) rec.getY());
        }
        graph.setFill(Color.TRANSPARENT);
        graph.fillRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getWidth());
        graph.strokeRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getWidth());
        new drawing().savingDrawnImage(canv, graph);
    }

    /**
     * Assists with drawing a circle when the mouse is released
     * @param graph
     * @param circ
     * @param canv
     * @param e
     */
    void circle(GraphicsContext graph, Circle circ, Canvas canv, MouseEvent e) {
        circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY())) / 2);
        graph.setLineDashes(0);
        if (circ.getCenterX() > e.getX()) {
            circ.setCenterX(e.getX());
        }
        if (circ.getCenterY() > e.getY()) {
            circ.setCenterY(e.getY());
        }
        graph.setFill(Color.TRANSPARENT);
        graph.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
        graph.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
        new drawing().savingDrawnImage(canv, graph);
    }

    /**
     * Assists with drawing a ellipse when the mouse is released
     * @param graph
     * @param elip
     * @param canv
     * @param e
     */
    void ellipse(GraphicsContext graph, Ellipse elip, Canvas canv, MouseEvent e) {
        elip.setRadiusX((Math.abs(e.getX() - elip.getCenterX())));
        elip.setRadiusY((Math.abs(e.getY() - elip.getCenterY())));
        graph.setLineDashes(0);

        if (elip.getCenterX() > e.getX()) {
            elip.setCenterX(e.getX());
        }
        if (elip.getCenterY() > e.getY()) {
            elip.setCenterY(e.getY());
        }
        graph.setFill(Color.TRANSPARENT);
        graph.strokeOval(elip.getCenterX(), elip.getCenterY(), elip.getRadiusX(), elip.getRadiusY());
        graph.fillOval(elip.getCenterX(), elip.getCenterY(), elip.getRadiusX(), elip.getRadiusY());
        new drawing().savingDrawnImage(canv, graph);
    }

    /**
     * Assists with drawing a line when the mouse is released
     * @param graph
     * @param line
     * @param canv
     * @param e
     */
    void line(GraphicsContext graph, Line line, Canvas canv, MouseEvent e) {
        line.setEndX(e.getX());
        line.setEndY(e.getY());
        graph.setLineDashes(0);
        graph.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        new drawing().savingDrawnImage(canv, graph);
    }

    /**
     * Assists with drawing a dashed line when the mouse is released
     * @param graph
     * @param lime
     * @param canv
     * @param e
     */
    void dashedLine(GraphicsContext graph, Line lime, Canvas canv, MouseEvent e) {
        graph.setLineDashes(20);
        lime.setEndX(e.getX());
        lime.setEndY(e.getY());
        graph.strokeLine(lime.getStartX(), lime.getStartY(), lime.getEndX(), lime.getEndY());
        new drawing().savingDrawnImage(canv, graph);
    }

    /**
     * Assists with drawing a rounded rectangle when the mouse is released
     * @param graph
     * @param rore
     * @param canv
     * @param e
     */
    void roundedRectangle(GraphicsContext graph, Rectangle rore, Canvas canv, MouseEvent e) {
        rore.setSize((int) Math.abs((e.getX() - rore.getX())), (int) Math.abs((e.getY() - rore.getY())));

        if((rore.getX() > e.getX()) || (rore.getY() > e.getY())){
            rore.setLocation((int) rore.getX(), (int) rore.getY());

        }
        graph.setLineDashes(0);
        graph.setFill(Color.TRANSPARENT);
        graph.fillRect(rore.getX(), rore.getY(), rore.getWidth(), rore.getHeight());
        graph.strokeRoundRect(rore.getX(), rore.getY(), rore.getWidth(), rore.getHeight(), 20, 30);

        new drawing().savingDrawnImage(canv, graph);

    }
}