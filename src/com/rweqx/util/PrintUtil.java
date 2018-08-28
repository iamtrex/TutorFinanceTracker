package com.rweqx.util;

import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PrintUtil {

    public static void print(Node node, Window parentWindow){
        /*
        PrinterJob job = PrinterJob.createPrinterJob();
        if(job != null){
            boolean proceed = job.showPageSetupDialog(node.getScene().getWindow());
            if(proceed){

                JobSettings jobSettings = job.getJobSettings();
                System.out.println(jobSettings.toString());

                boolean printed = job.printPage(node);
                if(printed){
                    job.endJob();
                    System.out.println("Print success");
                }else{
                    System.out.println("Print failed");
                }
            }
        }*/

        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.getDefaultPageLayout();
        System.out.println("Page Layout " + pageLayout);

        double pWidth = pageLayout.getPrintableWidth();
        double pHeight = pageLayout.getPrintableHeight();
        System.out.println("Printable area is " + pWidth + " width and " + pHeight + " height.");
        double nWidth = node.getBoundsInParent().getWidth();
        double nHeight = node.getBoundsInParent().getHeight();
        System.out.println("Node's dimensions are " + nWidth + " width and " + nHeight + " height");
        double widthLeft = pWidth - nWidth;
        double heightLeft = pHeight - nHeight;
        System.out.println("Width left: " + widthLeft
                + " height left: " + heightLeft);
        double scale = 0;
        if (widthLeft < heightLeft) {
            scale = pWidth / nWidth;
        } else {
            scale = pHeight / nHeight;
        }
        // preserve ratio (both values are the same)
        node.getTransforms().add(new Scale(scale, scale));

        // after scale you can check the size fit in the printable area
        double newWidth = node.getBoundsInParent().getWidth();
        double newHeight = node.getBoundsInParent().getHeight();
        System.out.println("New Node's dimensions: " + newWidth
                + " width " + newHeight + " height");

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean cont = job.showPrintDialog(parentWindow);
            if(cont) {
                boolean success = job.printPage(node);
                if (success) {
                    job.endJob();
                    System.exit(0);
                }
            }
        }

    }
}
