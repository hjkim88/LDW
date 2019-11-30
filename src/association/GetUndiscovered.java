/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package association;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 *
 * @author Hyunjin Kim
 */
public class GetUndiscovered {
    private final String newDataPath = global.Variables.resultPath + "Disease-Drug(LDW).dat";
    private final String exsDataPath = global.Variables.extractedDD.substring(0, global.Variables.extractedDD.length()-4) + "_processed.dat";
    private final String novelDataPath = global.Variables.resultPath + "Disease-Drug(LDW)_Undiscovered.dat";
    
    private String[] exsList;
    private String[] newList;
    private double[] newScore;
    
    private int exsLen;
    private int newLen;
    private int novelLen;
    
    public GetUndiscovered(int exsLen, int newLen) {
        initVariables(exsLen, newLen);
    }
    
    private void initVariables(int exsLen, int newLen) {
        this.exsLen = exsLen;
        this.newLen = newLen;
        novelLen = 0;
        
        loadData();
    }
    
    private void loadData() {
        try {
            File f = new File(exsDataPath);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            
            exsList = new String[exsLen];
            
            String[] str;
            
            for(int i = 0; i < exsLen; i++) {
                str = br.readLine().split("\t");
                exsList[i] = str[0] + "\t" + str[1];
            }
            
            f = new File(newDataPath);
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            
            newList = new String[newLen];
            newScore = new double[newLen];
            
            for(int i = 0; i < newLen; i++) {
                str = br.readLine().split("\t");
                newList[i] = str[0] + "\t" + str[1];
                newScore[i] = Double.parseDouble(str[2]);
            }
            
            br.close();
            fr.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private void makeNovel() {
        try {
            FileWriter fw = new FileWriter(novelDataPath);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter outFile = new PrintWriter(bw);
            
            int idx;
            
            for(int i = 0; i < newLen; i++) {
                idx = Arrays.binarySearch(exsList, newList[i]);
                if(idx < 0) {
                    outFile.println(newList[i] + "\t" + newScore[i]);
                    novelLen++;
                }
            }
            
            outFile.close();
            bw.close();
            fw.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public void start() {
        makeNovel();
        System.out.println(global.Variables.logTag + "The # of Novel Associations: " + novelLen);
        System.out.println(global.Variables.logTag + "GetUndiscovered() done.");
    }
    
    public int getNovelLen() {
        return this.novelLen;
    }
    
}
