package org.cauli;

import org.cauli.junit.runner.Reporter;

/**
 * Created by tianqing.wang on 2014/7/22
 */
public class TestConfig {

    private int threadNumber;

    private Reporter reporter;

    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public Reporter getReporter() {
        return reporter;
    }

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }
}
