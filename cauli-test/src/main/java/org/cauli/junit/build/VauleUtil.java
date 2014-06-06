package org.cauli.junit.build;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class VauleUtil {


    public static String valueTransfer(String rowValue){
        if("<null>".equalsIgnoreCase(rowValue)){
            return null;
        }else if("<empty>".equalsIgnoreCase(rowValue)){
            return "";
        }
        return rowValue;
    }
}
