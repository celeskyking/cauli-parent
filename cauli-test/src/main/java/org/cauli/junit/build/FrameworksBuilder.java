package org.cauli.junit.build;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.util.List;

/**
 * Created by celeskyking on 2014/6/1
 */
public interface FrameworksBuilder {

    public List<FrameworkMethod> build(TestClass testClass);
}
