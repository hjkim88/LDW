/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package association;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Hyunjin Kim
 */
public class MakeNewWebAssc {
    
    private String[] targetPath;
    private global.AsscScoreList[][] assc;
    private int[] asscLen;
    
    private int newAsscLen;
    
    public MakeNewWebAssc(int dlLen, int ldLen) {
        initVariables(dlLen, ldLen);
    }
    
    private void initVariables(int dlLen, int ldLen) {
        targetPath = new String[2];
        targetPath[0] = global.Variables.extractedDL.substring(0, global.Variables.extractedDL.length()-4) + "_processed.dat";
        targetPath[1] = global.Variables.extractedLD.substring(0, global.Variables.extractedLD.length()-4) + "_processed.dat";
        
        assc = new global.AsscScoreList[targetPath.length][0];
        asscLen = new int[assc.length];
        asscLen[0] = dlLen;
        asscLen[1] = ldLen;
        
        newAsscLen = 0;
        
        loadAssc();
    }
    
    private void loadAssc() {
        for(int i = 0; i < targetPath.length; i++) {
            try {
                File f = new File(targetPath[i]);
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                
                assc[i] = new global.AsscScoreList[asscLen[i]];
                
                String[] str;
                
                if(i == 0) {
                    for(int j = 0; j < asscLen[i]; j++) {
                        str = br.readLine().split("\t");
                        assc[i][j] = new global.AsscScoreList();
                        assc[i][j].setAssc1(str[1]);
                        assc[i][j].setAssc2(str[0]);
                        assc[i][j].setScore(Double.parseDouble(str[2]));
                    }
                }
                else {
                    for(int j = 0; j < asscLen[i]; j++) {
                        str = br.readLine().split("\t");
                        assc[i][j] = new global.AsscScoreList();
                        assc[i][j].setAssc1(str[0]);
                        assc[i][j].setAssc2(str[1]);
                        assc[i][j].setScore(Double.parseDouble(str[2]));
                    }
                }
                
                br.close();
                fr.close();
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
            
            Arrays.sort(assc[i]);
        }
    }
    
    private double EuclideanDistance2D(double d1, double d2) {
        return Math.sqrt(Math.pow((1/d1), 2) + Math.pow((1/d2), 2));        // 해당 count의 역수로 대각선의 길이를 구함
    }
    
    private void makeNew() {
        try {
            FileWriter fw1 = new FileWriter(global.Variables.resultPath + "Disease-Drug(new).dat");
            BufferedWriter bw1 = new BufferedWriter(fw1);
            PrintWriter outFile1 = new PrintWriter(bw1);
            
            int idx, tempIdx;
            
            for(int i = 0; i < asscLen[0]; i++) {
                idx = Arrays.binarySearch(assc[1], assc[0][i]);
                if(idx >= 0) {
                    outFile1.println(assc[0][i].getAssc2() + "\t" + assc[1][idx].getAssc2() + "\t" + EuclideanDistance2D(assc[0][i].getScore(), assc[1][idx].getScore()));
                    newAsscLen++;
                    tempIdx = idx;
                    while((idx < (asscLen[1]-1)) && assc[1][idx].getAssc1().equals(assc[1][idx+1].getAssc1())) {
                        idx = idx + 1;
                        outFile1.println(assc[0][i].getAssc2() + "\t" + assc[1][idx].getAssc2() + "\t" + EuclideanDistance2D(assc[0][i].getScore(), assc[1][idx].getScore()));
                        newAsscLen++;
                    }
                    idx = tempIdx;
                    while((idx > 0) && assc[1][idx].getAssc1().equals(assc[1][idx-1].getAssc1())) {
                        idx = idx - 1;
                        outFile1.println(assc[0][i].getAssc2() + "\t" + assc[1][idx].getAssc2() + "\t" + EuclideanDistance2D(assc[0][i].getScore(), assc[1][idx].getScore()));
                        newAsscLen++;
                    }
                }
            }
            
            outFile1.close();
            bw1.close();
            fw1.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        
    }
    
    public void start() {
        makeNew();
        System.out.println(global.Variables.logTag + "The # of New Associations: " + newAsscLen);
        System.out.println(global.Variables.logTag + "MakeNewWebAssc() done.");
    }
    
    public int getNewAsscLen() {
        return this.newAsscLen;
    }
    
}
