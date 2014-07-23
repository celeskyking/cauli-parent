package org.cauli.junit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.cauli.Constant;
import org.cauli.junit.build.FrameworksBuilder;
import org.cauli.junit.build.FrameworksBuilderFactory;
import org.junit.rules.TestRule;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.notification.RunListener;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class TestPlan {

    private int runLevel= Constant.DEFAULT_RUN_LEVEL;
    private int threads=Constant.DEFAULT_THREAD_NUM;
    private Filter filter;
    private int retryTimes=Constant.DEFAULT_RETRY_TIMES;
    private long timeout=Constant.DEFAULT_TIME_OUT;
    private String runFeature=Constant.DEFAULT_RUN_FEATURE;
    private String runRelease=Constant.DEFAULT_RUN_RELEASE;
    private String reportPath;
    private FrameworksBuilder frameworksBuilder= FrameworksBuilderFactory.getInstance().getFrameworkBuilder();
    private List<RunListener> runListeners=new ArrayList<RunListener>();
    private List<TestRule> listeners= Lists.newArrayList();
    private Map<String,Object> templateSources = Maps.newHashMap();

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

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
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

    public List<TestRule> getListeners() {
        return listeners;
    }

    public void setListeners(List<TestRule> listeners) {
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

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public Map<String, Object> getTemplateSources() {
        return templateSources;
    }

    public void setTemplateSources(Map<String, Object> templateSources) {
        this.templateSources = templateSources;
    }
}
