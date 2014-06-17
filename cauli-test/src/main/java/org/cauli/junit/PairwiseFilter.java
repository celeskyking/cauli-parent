package org.cauli.junit;


import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/17
 */
public interface PairwiseFilter {

    public boolean isMatch(Map<String, String> params);
}
