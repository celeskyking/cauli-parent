package org.cauli;

import com.google.common.collect.Lists;
import org.cauli.junit.build.FrameworksBuilder;
import org.cauli.ui.selenium.browser.Browser;
import org.junit.rules.TestRule;

import java.util.List;

/**
 * Created by tianqing.wang on 2014/9/11
 */
public class CauliUIConfig {

    private Browser[] browser;

    private String runFeature;

    private String runRelease;

    private int threads;

    private String reportPath;

    private int reteyTimes;

    private List<TestRule> rules= Lists.newArrayList();

    private FrameworksBuilder frameworksBuilder;

    public Browser[] getBrowser() {
        return browser;
    }

    public void setBrowser(Browser[] browser) {
        this.browser = browser;
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

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public int getReteyTimes() {
        return reteyTimes;
    }

    public void setReteyTimes(int reteyTimes) {
        this.reteyTimes = reteyTimes;
    }

    public List<TestRule> getRules() {
        return rules;
    }

    public void setRules(List<TestRule> rules) {
        this.rules = rules;
    }

    public FrameworksBuilder getFrameworksBuilder() {
        return frameworksBuilder;
    }

    public void setFrameworksBuilder(FrameworksBuilder frameworksBuilder) {
        this.frameworksBuilder = frameworksBuilder;
    }
}
