package org.cauli.junit;

import org.cauli.junit.build.FrameworksBuilder;
import org.junit.runner.notification.RunListener;

import java.util.List;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class TestPlan {

    private FileGenerator fileGenerator;
    private int runLevel;
    private int retryTimes;
    private int timeout;
    private String filterRule;
    private String reportPath;
    private FrameworksBuilder frameworksBuilder;
    List<RunListener> runListeners;
    private boolean retryFailed;
}
