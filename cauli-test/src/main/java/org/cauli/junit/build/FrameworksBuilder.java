package org.cauli.junit.build;

import org.cauli.exception.FrameworkBuildException;
import org.cauli.junit.FrameworkMethodWithParameters;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.util.List;

/**
 * Created by celeskyking on 2014/6/1
 */
public interface FrameworksBuilder {

    public List<FrameworkMethodWithParameters> build(TestClass testClass) throws FrameworkBuildException;
}
