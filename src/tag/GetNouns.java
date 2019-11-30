/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tag;

import java.util.Arrays;

/**
 *
 * @author Hyunjin Kim
 */
public class GetNouns {
    
    private String[] nouns;
    private String[] temp;
    private String[] buf1;
    private int cnt;
    private int tempLen;
    private int bufLen;
    
    public GetNouns(String str) {
        initVariables(str);
        if(bufLen <= global.Variables.wordLenLimit) {
            extract();
            redundantEli();
        }
    }
    
    private void initVariables(String str) {
        tempLen = global.Variables.nounTempLen;
        temp = new String[tempLen];
        nouns = new String[0];
        cnt = 0;
        buf1 = str.split(" ");
        bufLen = buf1.length;
    }
    
    private void extract() {
        String[] buf2;
        String previous = "";
        
        for(int i = 0; i < bufLen; i++) {
            buf2 = buf1[i].split("_");
            if(buf2[1].equals("NN") || buf2[1].equals("NNS")) {
                if(previous.equals("NN") || previous.equals("NNS")) {
                    temp[cnt-1] = temp[cnt-1] + " " + buf2[0];
                }
                else {
                    temp[cnt] = buf2[0];
                    previous = buf2[1];
                    cnt++;
                }
            }
            else if(buf2[1].equals("NNP") || buf2[1].equals("NNPS")) {
                if(previous.equals("NNP") || previous.equals("NNPS")) {
                    temp[cnt-1] = temp[cnt-1] + " " + buf2[0];
                }
                else {
                    temp[cnt] = buf2[0];
                    previous = buf2[1];
                    cnt++;
                }
            }
            else {
                previous = buf2[1];
            }
        }
    }
    
    private void redundantEli() {
        Arrays.sort(temp, 0, cnt);
        
        String buf = "";
        int nounLen = 0;
        
        for(int i = 0; i < cnt; i++) {
            if(!buf.equals(temp[i])) {
                buf = temp[i];
                nounLen++;
            }
        }
        
        nouns = new String[nounLen];
        buf = "";
        nounLen = 0;
        
        for(int i = 0; i < cnt; i++) {
            if(!buf.equals(temp[i])) {
                buf = temp[i];
                nouns[nounLen] = buf;
                nounLen++;
            }
        }
    }
    
    public String[] getNouns() {
        return nouns;
    }
    
}
