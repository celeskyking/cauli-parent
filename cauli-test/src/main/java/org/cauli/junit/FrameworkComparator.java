package org.cauli.junit;

import java.util.Comparator;

/**
 * Created by tianqing.wang on 2014/6/16
 */
public class FrameworkComparator implements Comparator<FrameworkMethodWithParameters> {


    @Override
    public int compare(FrameworkMethodWithParameters o1, FrameworkMethodWithParameters o2) {
        if(o1.getLevel()<o2.getLevel()){
            return -1;
        }else if(o1.getLevel()==o2.getLevel()){
            return 0;
        }else{
            return 1;
        }
    }
}
