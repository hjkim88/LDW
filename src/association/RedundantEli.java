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
public class RedundantEli {
    
    private final String finalDataPath = global.Variables.resultPath + "Disease-Drug(new).dat";
    private final String outputPath = global.Variables.resultPath + "Disease-Drug(LDW).dat";
    private global.FinalData[] fd;
    private int fdLen;
    
    private int newLen;
    
    public RedundantEli(int dataLen) {
        initVariables(dataLen);
    }
    
    private void initVariables(int dataLen) {
        newLen = 0;
        fdLen = dataLen;
        
        loadFinalData();
        Arrays.sort(fd);
    }
    
    private void loadFinalData() {
        try {
            File f = new File(finalDataPath);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            
            fd= new global.FinalData[fdLen];
            
            String[] str;
            
            for(int i = 0; i < fdLen; i++) {
                fd[i] = new global.FinalData();
                str = br.readLine().split("\t");
                fd[i].setAssc1(str[0]);
                fd[i].setAssc2(str[1]);
                fd[i].setScore(Double.parseDouble(str[2]));
            }
            
            br.close();
            fr.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private void eli() {
        try {
            FileWriter fw = new FileWriter(outputPath);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter outFile = new PrintWriter(bw);
            
            String temp1 = "", temp2 = "";
            
            for(int i = 0; i < fdLen; i++) {
                if(!temp1.equals(fd[i].getAssc1()) || !temp2.equals(fd[i].getAssc2())) {
                    temp1 = fd[i].getAssc1();
                    temp2 = fd[i].getAssc2();
                    outFile.println(fd[i].getAssc1() + "\t" + fd[i].getAssc2() + "\t" + fd[i].getScore());
                    newLen++;
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
        eli();
        System.out.println(global.Variables.logTag + "newLen = " + newLen);
        System.out.println(global.Variables.logTag + "RedundantEli() done.");
    }
    
    public int getNewLen() {
        return this.newLen;
    }
    
}
