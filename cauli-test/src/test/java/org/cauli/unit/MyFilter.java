package org.cauli.unit;

import org.cauli.junit.PairwiseFilter;

import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/17
 */
public class MyFilter implements PairwiseFilter{
    @Override
    public boolean isMatch(Map<String, String> params) {
        if(params.get("地点").equals("上海")&&params.get("食物").equals("冰糕")){
            return false;
        }
        return true;
    }
}
