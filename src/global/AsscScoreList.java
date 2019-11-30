/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

/**
 *
 * @author Hyunjin Kim
 */
public class AsscScoreList implements Comparable<AsscScoreList> {
    
    private String assc1;        // Association1 name
    private String assc2;        // Association2 name       
    private double score;       // Association score
    
    public AsscScoreList() {}
    
    @Override
    public int compareTo(AsscScoreList sl) {
        if(this.assc1.compareTo(sl.assc1) > 0) {
            return 1;
        }
        else if(this.assc1.compareTo(sl.assc1) < 0) {
            return -1;
        }
        else {
            return 0;
        }
    }
    
    public void setAssc1(String a1) {
        this.assc1 = a1;
    }
    
    public void setAssc2(String a2) {
        this.assc2 = a2;
    }
    
    public void setScore(double s) {
        this.score = s;
    }
    
    public String getAssc1() {
        return assc1;
    }
    
    public String getAssc2() {
        return assc2;
    }
    
    public double getScore() {
        return score;
    }
    
}
