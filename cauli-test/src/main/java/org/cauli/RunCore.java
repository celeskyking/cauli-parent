package org.cauli;

import org.cauli.junit.FileGenerator;
import org.cauli.junit.build.FrameworksBuilder;
import org.junit.runner.notification.RunListener;

import java.util.List;

/**
 * Created by celeskyking on 2014/6/1
 */
public class RunCore {

    private FileGenerator fileGenerator;
    private int runLevel;
    private int retryTimes;
    private int timeout;
    private String resourcePath;
    private String filterRule;
    private String sortRule;
    private String reportPath;
    private FrameworksBuilder frameworksBuilder;
    List<RunListener> runListeners;
}
