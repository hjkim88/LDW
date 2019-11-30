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
public class NewSortedAssc {
    
    private String path1 = global.Variables.resultPath + "Disease-Drug(LDW)_Undiscovered.dat";
    private String outPath1 = global.Variables.resultPath + "Disease-Drug(LDW)_Undiscovered_Sorted.dat";
    
    private global.DDScoreList[] sl1;
    private int sl1Len;
    
    public NewSortedAssc(int len) {
        initVariables(len);
    }
    
    private void initVariables(int len) {
        sl1Len = len;
        
        loadScores();
    }
    
    private void loadScores() {
        try {
            File f = new File(path1);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            
            String[] str;
            
            sl1 = new global.DDScoreList[sl1Len];
            
            for(int i = 0; i < sl1Len; i++) {
                str = br.readLine().split("\t");
                sl1[i] = new global.DDScoreList();
                sl1[i].setDisease1(str[0]);
                sl1[i].setDisease2(str[1]);
                sl1[i].setScore(Double.parseDouble(str[2]));
            }
            
            Arrays.sort(sl1);
            System.out.println("Disease-Drug sorting done");
            
            br.close();
            fr.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private void write() {
        try {
            FileWriter fw1 = new FileWriter(outPath1);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            PrintWriter outFile1 = new PrintWriter(bw1);
            
            for(int i = 0; i < sl1Len; i++) {
                outFile1.println(sl1[i].getDisease1() + "\t" + sl1[i].getDisease2() + "\t" + sl1[i].getSimilarity());
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
        write();
        System.out.println(global.Variables.logTag + "NewSortedAssc() done.");
    }
    
}
