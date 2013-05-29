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
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

/**
 *
 * @author chris
 */
public class Classen_Beleg_1 {

    IndexSearcher searcher;

    public ScoreDoc[] search(String querystr, Directory d, Analyzer analyzer) {
        try {

            Query q = new QueryParser(Version.LUCENE_43, "text", analyzer).parse(querystr);
            int hitsPerPage = 10;
            IndexReader reader = IndexReader.open(d);
            searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
            searcher.search(q, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            return hits;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        // TODO code application logic here
        File lagerDatei = new File("crawler.txt");
        
        if (lagerDatei.exists()){
            lagerDatei.delete();
        }
        
        Controller controller = new Controller();
        try {
            controller.start();
        } catch (Exception ex) {
            Logger.getLogger(Classen_Beleg_1.class.getName()).log(Level.SEVERE, null, ex);
        }
        


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

        Crawler crawl = new Crawler();

        String querystr = args.length > 0 ? args[0] : "d02";
        Query q = new QueryParser(Version.LUCENE_43, "text", crawl.getAnalyzer()).parse(querystr);

        int hitsPerPage = 10;
        IndexReader reader = IndexReader.open(crawl.getIndex());

        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        
        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("text") );
        }



    }
}
