/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package global;

/**
 *
 * @author Hyunjin Kim
 */
public class Variables {
    
    public final static String logTag = "LOG: ";
    
    public final static String webCrawlDataPath = System.getProperty("user.dir") + "/data/Common_Crawl/WebCrawlData(Example).dat";
    public final static String diseaseListPath = System.getProperty("user.dir") + "/data/Entities/diseaseList.txt";
    public final static String drugListPath = System.getProperty("user.dir") + "/data/Entities/drugList.txt";
    public final static String locationPath = System.getProperty("user.dir") + "/data/Entities/locationList.txt";
    public final static String web_association_path = System.getProperty("user.dir") + "/data/Associations/";
    public final static String resultPath = System.getProperty("user.dir") + "/data/Results/";
    
    public final static String taggerPath = System.getProperty("user.dir") + "/lib/english-left3words-distsim.tagger";
    
    public final static String extractedDL = web_association_path + "Disease-Location(web).dat";
    public final static String extractedDD = web_association_path + "Disease-Drug(web).dat";
    public final static String extractedLD = web_association_path + "Location-Drug(web).dat";
    
    public final static int lineLenLimit = 100000;
    public final static int entityTempLen = 100;
    public final static int nounTempLen = 5000;
    public final static int wordLenLimit = 5000;
    
}