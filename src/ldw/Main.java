/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldw;

import java.sql.Timestamp;

/**
 *
 * @author Hyunjin Kim
 */
public class Main {

    public Main() {
        initVariables();
    }
    
    private void initVariables() {
        System.out.println(global.Variables.logTag + "LDW.ldw.Main() just started.");
    }
    
    private void start() {
        association.ExtWebAsscsWithPOS extWeb = new association.ExtWebAsscsWithPOS();   // Extract A-B associations and B-C asociations from web crawl data
        extWeb.start();
        association.CooccurFreq cf = new association.CooccurFreq(extWeb.getDlLen(), extWeb.getDdLen(), extWeb.getLdLen());  // Giving scores to the associations with co-occurrence frequency
        cf.start();
        association.MakeNewWebAssc mnwa = new association.MakeNewWebAssc(cf.getLens()[0], cf.getLens()[2]); // Making new A-C associations by using ABC model on the A-B associations and the B-C associations
        mnwa.start();
        association.RedundantEli re = new association.RedundantEli(mnwa.getNewAsscLen());   // Removing redundant associations 
        re.start();
        association.GetUndiscovered gu = new association.GetUndiscovered(cf.getLens()[1], re.getNewLen());  // Discovering undiscovered A-C associations by removing the pre-existing A-C associations
        gu.start();
        new association.NewSortedAssc(gu.getNovelLen()).start();    // Sorting by distance (low distance = high rank)
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(global.Variables.logTag + "Start Time: " + new Timestamp(System.currentTimeMillis()));
        new ldw.Main().start();
        System.out.println(global.Variables.logTag + "End Time: " + new Timestamp(System.currentTimeMillis()));
    }
    
}
