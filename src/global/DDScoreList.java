/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

/**
 *
 * @author Hyunjin Kim
 */
public class DDScoreList implements Comparable<DDScoreList> {
    
    private String disease1;        // Disease1 name
    private String disease2;        // Disease2 name
    private double sim;       // Disease-Disease similarity
    
    public DDScoreList() {}
    
    @Override
    public int compareTo(DDScoreList sl) {  // 점수가 작은게 앞으로 오는 정렬
        if(this.sim > sl.sim) {
            return 1;
        }
        else if(this.sim == sl.sim) {
            return 0;
        }
        else {
            return -1;
        }
    }
    
    public void setDisease1(String d1) {
        this.disease1 = d1;
    }
    
    public void setDisease2(String d2) {
        this.disease2 = d2;
    }
    
    public void setScore(double s) {
        this.sim = s;
    }
    
    public String getDisease1() {
        return disease1;
    }
    
    public String getDisease2() {
        return disease2;
    }
    
    public double getSimilarity() {
        return sim;
    }
    
}
