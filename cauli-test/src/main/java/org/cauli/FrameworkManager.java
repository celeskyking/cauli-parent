package org.cauli;

import org.cauli.junit.FrameworkMethodWithParameters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/11
 */
public class FrameworkManager {

    public static Map<String,FrameworkMethodWithParameters> map = new HashMap<String,FrameworkMethodWithParameters>();

    public static FrameworkMethodWithParameters getFramework(String name){
        return map.get(name);
    }
}
