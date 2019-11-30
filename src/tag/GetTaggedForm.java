/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tag;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 *
 * @author Hyunjin Kim
 */
public class GetTaggedForm {

    private MaxentTagger tagger;
    
    public GetTaggedForm(MaxentTagger tagger) {
        initVariables(tagger);
    }
    
    private void initVariables(MaxentTagger tagger) {
        this.tagger = tagger;
    }
    
    public String getTaggedStr(String args) {
        return tagger.tagString(args);
    }
    
}
