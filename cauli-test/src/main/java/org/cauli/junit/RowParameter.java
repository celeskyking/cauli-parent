package org.cauli.junit;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by celeskyking on 14-3-2
 */
public class RowParameter {

    public RowParameter(){
        this.params= Lists.newArrayList();
    }
    public List<String> params;

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public void addParam(String value){
        this.params.add(value);
    }
}
