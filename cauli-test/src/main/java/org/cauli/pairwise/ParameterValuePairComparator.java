package org.cauli.pairwise;

import org.cauli.junit.PairParameter;
import org.cauli.pairwise.core.ParameterValuePair;

import java.util.Comparator;

/**
 * Created by tianqing.wang on 2014/8/6
 */
public class ParameterValuePairComparator implements Comparator<ParameterValuePair> {
    private PairParameter pairParameter;


    public ParameterValuePairComparator(PairParameter pairParameter){
        this.pairParameter=pairParameter;
    }
    @Override
    public int compare(ParameterValuePair o1, ParameterValuePair o2) {
        return 0;
    }


    public PairParameter getPairParameter() {
        return pairParameter;
    }

    public void setPairParameter(PairParameter pairParameter) {
        this.pairParameter = pairParameter;
    }


    private void initPriority(PairParameter pairParameter){

    }
}
