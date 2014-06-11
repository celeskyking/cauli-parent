package org.cauli.junit;

import com.google.common.collect.Lists;
import org.cauli.CauliListener;
import org.cauli.junit.build.FrameworksBuilder;
import org.cauli.junit.build.FrameworksBuilderFactory;
import org.cauli.junit.statement.InterceptorStatement;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.notification.RunListener;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class TestPlan {

    private int runLevel=2;
    private Filter filter;
    private int retryTimes=0;
    private int timeout;
    private String runFeature="default";
    private String runRelease="";
    private String reportPath;
    private FrameworksBuilder frameworksBuilder= FrameworksBuilderFactory.getInstance().getFrameworkBuilder();
    private List<RunListener> runListeners=new ArrayList<RunListener>();
    private InterceptorStatement runStatement;
    private List<CauliListener> listeners= Lists.newArrayList();

    public int getRunLevel() {
        return runLevel;
    }

    public void setRunLevel(int runLevel) {
        this.runLevel = runLevel;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public FrameworksBuilder getFrameworksBuilder() {
        return frameworksBuilder;
    }

    public void setFrameworksBuilder(FrameworksBuilder frameworksBuilder) {
        this.frameworksBuilder = frameworksBuilder;
    }

    public List<RunListener> getRunListeners() {
        return runListeners;
    }

    public void setRunListeners(List<RunListener> runListeners) {
        this.runListeners = runListeners;
    }

    public Statement getRunStatement() {
        return runStatement;
    }

    public void setRunStatement(InterceptorStatement runStatement) {
        this.runStatement = runStatement;
    }

    public List<CauliListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<CauliListener> listeners) {
        this.listeners = listeners;
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

    public void setRunRelease(String runRelease) {
        this.runRelease = runRelease;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
