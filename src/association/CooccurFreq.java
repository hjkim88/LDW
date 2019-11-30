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
public class CooccurFreq {
    
    private String[] intgrPath;
    private String[][] file;
    private int[] fileLen;
    private int[] newFileLen;
    
    public CooccurFreq(int dl, int dd, int ld) {
        initVariables(dl, dd, ld);
    }
    
    private void initVariables(int dl, int dd, int ld) {
        intgrPath = new String[3];
        intgrPath[0] = global.Variables.extractedDL;
        intgrPath[1] = global.Variables.extractedDD;
        intgrPath[2] = global.Variables.extractedLD;
        
        fileLen = new int[3];
        fileLen[0] = dl;
        fileLen[1] = dd;
        fileLen[2] = ld;
        
        newFileLen = new int[3];
        for(int i = 0; i < newFileLen.length; i++) {
            newFileLen[i] = 0;
        }
        
        file = new String[3][0];
    }
    
    private void loadFile(String targetFilePath, int fileNum) {
        try {
            File f = new File(targetFilePath);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            
            for(int i = 0; i < fileLen[fileNum]; i++) {
                file[fileNum][i] = br.readLine();
            }
            
            br.close();
            fr.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private void sort() {
        for(int i = 0; i < file.length; i++) {
            file[i] = new String[fileLen[i]];
            loadFile(intgrPath[i], i);
            Arrays.sort(file[i]);
        }
    }
    
    private void printOut() {
        for(int i = 0; i < file.length; i++) {
            try {
                FileWriter fw = new FileWriter(intgrPath[i].substring(0, intgrPath[i].length() - 4) + "_processed.dat");
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter outFile = new PrintWriter(bw);
                
                String temp = file[i][0];
                int cnt = 1, tCnt = 0;
                outFile.print(temp);
                newFileLen[i]++;
                
                for(int j = 1; j < fileLen[i]-1; j++) {
                    if(!temp.equals(file[i][j])) {
                        outFile.println("\t" + cnt);
                        tCnt = tCnt + cnt;
                        temp = file[i][j];
                        outFile.print(temp);
                        newFileLen[i]++;
                        cnt = 1;
                    }
                    else {
                        cnt++;
                    }
                }
                
                if(fileLen[i] == 1) {
                    outFile.println("\t" + cnt);
                    tCnt = tCnt + cnt;
                }
                else if(!temp.equals(file[i][fileLen[i]-1])) {
                    outFile.println("\t" + cnt);
                    tCnt = tCnt + cnt;
                    outFile.println(file[i][fileLen[i]-1] + "\t" + "1");
                    newFileLen[i]++;
                    tCnt = tCnt + 1;
                }
                else {
                    cnt++;
                    outFile.println("\t" + cnt);
                    tCnt = tCnt + cnt;
                }
                
                if(tCnt != fileLen[i]) {
                    System.out.println(global.Variables.logTag + "ERROR : association.CooccurFreq.printOut() : " + "tCnt = " + tCnt + ", fileLen[" + i + "] = "+ fileLen[i]);
                }
                
                outFile.close();
                bw.close();
                fw.close();
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    
    public void start() {
        sort();
        printOut();
        System.out.println(global.Variables.logTag + "The New # of Disease-Location: " + newFileLen[0]);
        System.out.println(global.Variables.logTag + "The New # of Disease-Drug: " + newFileLen[1]);
        System.out.println(global.Variables.logTag + "The New # of Location-Drug: " + newFileLen[2]);
        System.out.println(global.Variables.logTag + "CooccurFreq() done.");
    }
    
    public int[] getLens() {
        return this.newFileLen;
    }
    
}
