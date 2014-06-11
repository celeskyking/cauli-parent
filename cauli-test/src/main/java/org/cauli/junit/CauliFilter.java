package org.cauli.junit;


import jodd.util.StringUtil;

/**
 * Created by tianqing.wang on 2014/6/9
 */
public class CauliFilter {

    private int runLevel;
    private String runFeature;
    private String runRelease;


    public int getRunLevel() {
        return runLevel;
    }

    protected boolean isMatch(FrameworkMethodWithParameters frameworkMethodWithParameters){
        if(frameworkMethodWithParameters.getLevel()<=runLevel){
            return isMatchFeatureAndRelease(frameworkMethodWithParameters);
        }else{
            return false;
        }
    }

    public void setRunLevel(int runLevel) {
        this.runLevel = runLevel;
    }

    public String getRunFeature() {
        return runFeature;
    }

    public void setRunFeature(String runFeature) {
        this.runFeature = runFeature;
    }

    public String getRunRelease() {
        return runRelease;
    }

    public void setRunRealease(String runRealease) {
        this.runRelease = runRealease;
    }


    private boolean isMatchFeatureAndRelease(FrameworkMethodWithParameters method){
        if("default".equals(runFeature)){
            return true;
        }else{
            if(runFeature.equals(method.getFeature())){
                if(StringUtil.isEmpty(runRelease)||StringUtil.isEmpty(method.getRelease())){
                    return true;
                }else if(StringUtil.equals(runRelease,method.getRelease())){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }

    }
}
