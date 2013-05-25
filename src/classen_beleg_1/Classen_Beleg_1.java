/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classen_beleg_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chris
 */
public class Classen_Beleg_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
//        // TODO code application logic here
//        File lagerDatei = new File("crawler.txt");
//        
//        if (lagerDatei.exists()){
//            lagerDatei.delete();
//        }
//        
//        Controller controller = new Controller();
//        try {
//            controller.start();
//        } catch (Exception ex) {
//            Logger.getLogger(Classen_Beleg_1.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        


        List<Node> nodeList = new ArrayList<Node>();




        List<Node> nodeList2 = PageRank.readNodesFromFile("crawler.txt");
        // Initialize all Nodes with Beginning PR
        PageRank.setInitialPageRank(nodeList2);
        PageRank.getIncomingLinksFromNodeList(nodeList2);

        // JUST DO THIS 1x
        PageRank.calcTransitionProhability(nodeList2);
        ///
        // DEBUG PURPOSE
        for (Node singleNode : nodeList2) {
            singleNode.printLinks();
        }


        System.out.println("\n\n\tPAGERANKS\n\n");

        System.out.println("d01\td02\td03\td04\td05\td06\td07\td08\t\n");
        for (Node singleNode : nodeList2) {
            singleNode.printPageRank();
            System.out.print("\t");
        }


        System.out.println("\n");
        for (Node singleNode : nodeList2) {
            singleNode.printWSK();
            System.out.print("\t");
        }
        System.out.println("\n");
        double oldPR = 0;
        
        PageRank.calcPageRank(nodeList2);

        for (Node singleNode : nodeList2) {
            singleNode.printPageRank();
            System.out.print("\t");
        }
    }
}
