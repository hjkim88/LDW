/*
 * To change this template, choose Tools | Templates
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
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 *
 * @author Hyunjin Kim
 */
public class ExtWebAsscsWithPOS {
    
    private String webPath;
    private String[] diseaseList;
    private String[] drugList;
    private String[] locationList;
    
    private int webLen;
    
    private int diseaseNum;
    private int drugNum;
    private int locationNum;
    
    private MaxentTagger tagger;
    private int dlLen, ddLen, ldLen;
    
    public ExtWebAsscsWithPOS() {
        initVariables();
    }
    
    private void initVariables() {
        webPath = global.Variables.webCrawlDataPath;
        tagger = new MaxentTagger(global.Variables.taggerPath);
        
        dlLen = 0;
        ddLen = 0;
        ldLen = 0;
        
        loadEntities();
    }
    
    private void loadEntities() {
        try {
            File f = new File(global.Variables.diseaseListPath);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            
            diseaseNum = Integer.parseInt(br.readLine());
            diseaseList = new String[diseaseNum];
            for(int i = 0; i < diseaseNum; i++) {
                diseaseList[i] = br.readLine();
            }
            
            f = new File(global.Variables.drugListPath);
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            
            drugNum = Integer.parseInt(br.readLine());
            drugList = new String[drugNum];
            for(int i = 0; i < drugNum; i++) {
                drugList[i] = br.readLine();
            }
            
            f = new File(global.Variables.locationPath);
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            
            locationNum = Integer.parseInt(br.readLine());
            locationList = new String[locationNum];
            for(int i = 0; i < locationNum; i++) {
                locationList[i] = br.readLine();
            }
            
            br.close();
            fr.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private void extract() {
        String line = "";
        
        try {
            File f = new File(webPath);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw1 = new FileWriter(global.Variables.extractedDL);
            FileWriter fw2 = new FileWriter(global.Variables.extractedDD);
            FileWriter fw3 = new FileWriter(global.Variables.extractedLD);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            BufferedWriter bw3 = new BufferedWriter(fw3);
            PrintWriter outFile1 = new PrintWriter(bw1);
            PrintWriter outFile2 = new PrintWriter(bw2);
            PrintWriter outFile3 = new PrintWriter(bw3);
            
            webLen = Integer.parseInt(br.readLine());
            int div = webLen / 10;
            String[] str, nouns;
            String[][] associationTemp;
            int cnt1, cnt2, cnt3, index;
            boolean isReady;
            
            String[] locationTemp = new String[global.Variables.entityTempLen];
            String[] diseaseTemp = new String[global.Variables.entityTempLen];
            String[] drugTemp = new String[global.Variables.entityTempLen];
            
            for(int k = 1; k < webLen+1; k++) {
                if(k % div == 0) {
                    System.out.println(global.Variables.logTag + (k*10/div) + "% done.");
                }
                
                line = br.readLine();
                
                if(line.length() < global.Variables.lineLenLimit) {
                    line = new tag.GetTaggedForm(tagger).getTaggedStr(line);
                    str = line.split("_\\. ");

                    for(int i = 0; i < str.length; i++) {
                        if(str[i].length() > 2) {
                            nouns = new tag.GetNouns(str[i].substring(0, str[i].length()-1)).getNouns();

                            cnt1 = 0;
                            cnt2 = 0;
                            cnt3 = 0;
                            isReady = true;

                            for(int j = 0; j < nouns.length; j++) {
                                index = Arrays.binarySearch(locationList, nouns[j]);
                                if(index >= 0) {
                                    if(cnt1 == global.Variables.entityTempLen) {
                                        isReady = false;
                                        break;
                                    }
                                    locationTemp[cnt1] = locationList[index];
                                    cnt1++;
                                }
                                else {
                                    index = Arrays.binarySearch(diseaseList, nouns[j]);
                                    if(index >= 0) {
                                        if(cnt2 == global.Variables.entityTempLen) {
                                            isReady = false;
                                            break;
                                        }
                                        diseaseTemp[cnt2] = diseaseList[index];
                                        cnt2++;
                                    }
                                    else {
                                        index = Arrays.binarySearch(drugList, nouns[j]);
                                        if(index >= 0) {
                                            if(cnt3 == global.Variables.entityTempLen) {
                                                isReady = false;
                                                break;
                                            }
                                            drugTemp[cnt3] = drugList[index];
                                            cnt3++;
                                        }
                                    }
                                }
                            }

                            if(isReady == true) {
                                associationTemp = makeAssociation(cnt1, locationTemp, cnt2, diseaseTemp, cnt3, drugTemp);
                                for(int j = 0; j < (cnt1 * cnt2); j++) {
                                    outFile1.println(associationTemp[0][j]);
                                    dlLen++;
                                }
                                for(int j = 0; j < (cnt2 * cnt3); j++) {
                                    outFile2.println(associationTemp[1][j]);
                                    ddLen++;
                                }
                                for(int j = 0; j < (cnt1 * cnt3); j++) {
                                    outFile3.println(associationTemp[2][j]);
                                    ldLen++;
                                }
                            }
                        }
                    }
                }
            }
            
            outFile1.close();
            outFile2.close();
            outFile3.close();
            bw1.close();
            bw2.close();
            bw3.close();
            fw1.close();
            fw2.close();
            fw3.close();
            
            br.close();
            fr.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        catch(OutOfMemoryError oome) {
            System.out.println(global.Variables.logTag + "Out of Memory - line: " + line);
        }
        
        System.out.println("|");
    }
    
    private String[][] makeAssociation(int cnt1, String[] str1, int cnt2, String[] str2, int cnt3, String[] str3) {
        String[][] association = new String[3][global.Variables.entityTempLen * global.Variables.entityTempLen];
        int cnt;
        
        if((cnt1 > 0) && (cnt2 > 0)) {
            cnt = 0;
            for(int i = 0; i < cnt1; i++) {
                for(int j = 0; j < cnt2; j++) {
                    association[0][cnt] = str2[j] + "\t" + str1[i];
                    cnt++;
                }
            }
        }
        
        if(cnt2 > 0 && cnt3 > 0) {
            cnt = 0;
            for(int i = 0; i < cnt2; i++) {
                for(int j = 0; j < cnt3; j++) {
                    association[1][cnt] = str2[i] + "\t" + str3[j];
                    cnt++;
                }
            }
        }
        
        if(cnt1 > 0 && cnt3 > 0) {
            cnt = 0;
            for(int i = 0; i < cnt1; i++) {
                for(int j = 0; j < cnt3; j++) {
                    association[2][cnt] = str1[i] + "\t" + str3[j];
                    cnt++;
                }
            }
        }
        
        return association;
    }
    
    public void start() {
        extract();
        System.out.println(global.Variables.logTag + "The # of Disease-Location:" + dlLen);
        System.out.println(global.Variables.logTag + "The # of Disease-Drug:" + ddLen);
        System.out.println(global.Variables.logTag + "The # of Location-Drug:" + ldLen);
        System.out.println(global.Variables.logTag + webPath + " extraction done.");
    }
    
    public int getDlLen() {
        return this.dlLen;
    }
    
    public int getDdLen() {
        return this.ddLen;
    }
    
    public int getLdLen() {
        return this.ldLen;
    }
    
}
