package com.example.paints;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is created by Nicole Brown
 * This class contains all the Threading elements
 */
public class Threading {
    /**
     * outputs all threading to a text file
     * @param buttonName
     * @throws IOException
     */
    void Thread(String buttonName) throws IOException {
        // Open the file in append mode.
        File f = new File("C:\\Users\\nicol\\Pictures\\images\\Paint.txt");
       // if (f.delete())                      //returns Boolean value
        //{
            //System.out.println(f.getName() + " deleted");   //getting and printing the file name
        //}
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        FileWriter fw = new FileWriter("C:\\Users\\nicol\\Pictures\\images\\Paint.txt", true);
        PrintWriter out = new PrintWriter(fw);


            out.println(dtf.format(now) + " " + buttonName + " selected");
            //out.println(dtf.format(now));

        // Close the file.
        out.close();
    }



}


