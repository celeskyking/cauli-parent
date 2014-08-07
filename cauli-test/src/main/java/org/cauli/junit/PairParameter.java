package org.cauli.junit;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.cauli.pairwise.core.ParameterValuePair;
import java.util.List;

/**
 * Created by tianqing.wang on 2014/6/17
 */
public class PairParameter {

    private List<ParameterValuePair> params= Lists.newArrayList();

    public List<ParameterValuePair> getPairs(){
        return params;
    }


    public String getValue(String name){
        for(ParameterValuePair parameterValuePair:params){
            if(name.equals(parameterValuePair.getParameterName())){
                return parameterValuePair.getParameterValue();
            }
        }
        return null;
    }

    public boolean isContainsAliasName(String name){
        for(ParameterValuePair parameterValuePair:params){
            if(parameterValuePair.getParameterName().contains(".")&&name.equals(StringUtils.substringBefore(parameterValuePair.getParameterName(),"."))){
                return true;
            }else if(!parameterValuePair.getParameterName().contains(".")&&name.equals(parameterValuePair.getParameterName())){
                return true;
            }

        }
        return false;
    }


    public boolean isContainsKey(String key){
        for(ParameterValuePair parameterValuePair:params){
            if(key.equals(parameterValuePair.getParameterName())){
                return true;
            }
        }
        return false;
    }

    public void addParam(ParameterValuePair pair)  {
        this.params.add(pair);

    }

    public void addParams(List<ParameterValuePair> pairs){
        this.params.addAll(pairs);
    }




}
