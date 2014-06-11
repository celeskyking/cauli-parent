
package org.cauli.junit;

import com.google.common.collect.Lists;
import jodd.util.StringUtil;
import org.cauli.exception.FrameworkBuildException;
import org.cauli.junit.anno.Filter;
import org.cauli.junit.build.FrameworksBuilderFactory;
import org.cauli.junit.statement.InterceptorStatement;
import org.junit.Test;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 */
public class Feeder extends BlockJUnit4ClassRunner {

    private Logger logger = LoggerFactory.getLogger(JUnitBaseRunner.class);

    private TestPlan testPlan=new TestPlan();

	private List<FrameworkMethod> children= Lists.newArrayList();
	
	public Feeder(Class<?> testClass) throws InitializationError {
		super(testClass);
        //initFilter(testClass);
	}

    @Override
    protected void collectInitializationErrors(List<Throwable> errors) {
        this.testPlan=new TestPlan();
        this.children=Lists.newArrayList();
        super.collectInitializationErrors(errors);
    }

	@Override
	protected String testName(FrameworkMethod method) {
		return (method instanceof FrameworkMethodWithParameters ? method.toString() : super.testName(method));
	}

    protected void validateTestMethods(List<Throwable> errors) {
        validatePublicVoidMethods(Test.class, false, errors);
    }

    private void validatePublicVoidMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors) {
        List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);
        for (FrameworkMethod eachTestMethod : methods)
            eachTestMethod.validatePublicVoid(isStatic, errors);
    }

	
	@Override
	protected List<FrameworkMethod> computeTestMethods() {
        CauliFilter cauliFilter=new CauliFilter();
		if (children == null||children.size()==0) {
			TestClass testClass = getTestClass();
            Filter filter = testClass.getJavaClass().getAnnotation(Filter.class);
            if(null!=filter){
                cauliFilter.setRunLevel(testPlan.getRunLevel()!=0?testPlan.getRunLevel():filter.runLevel());
                cauliFilter.setRunFeature(StringUtil.isNotEmpty(testPlan.getRunFeature())?testPlan.getRunFeature():filter.feature());
                cauliFilter.setRunRealease(StringUtil.isNotEmpty(testPlan.getRunRelease())?testPlan.getRunRelease():filter.release());
            }else{
                cauliFilter.setRunLevel(testPlan.getRunLevel());
                cauliFilter.setRunFeature(testPlan.getRunFeature());
                cauliFilter.setRunRealease(testPlan.getRunRelease());
            }
            List<FrameworkMethodWithParameters> list;
            try {
                list = FrameworksBuilderFactory.getInstance().getFrameworkBuilder().build(testClass);
            } catch (FrameworkBuildException e) {
                throw new RuntimeException(e);
            }
            for(FrameworkMethodWithParameters frameworkMethodWithParameters : list){
                if(cauliFilter.isMatch(frameworkMethodWithParameters)){
                    children.add(frameworkMethodWithParameters);
                }
            }
            MethodManager.load(list);
		}
		return children;
	}

    protected void initFilter(Class<?> testClass){
        logger.info("加载过滤器..");
        try {
            filter(testPlan.getFilter());
        } catch (NoTestsRemainException e) {
            logger.warn("过滤器加载失败,将会执行所有的case..");
            e.printStackTrace();
        }
    }

    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        if(method instanceof FrameworkMethodWithParameters){
            InterceptorStatement statement = new InterceptorStatement((FrameworkMethodWithParameters) method, test);
            statement.setRunLevel(testPlan.getRunLevel());
            statement.setRetryTimes(testPlan.getRetryTimes());
            return statement;
        }else{
            return new InvokeMethod(method,test);
        }

    }


    public TestPlan getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(TestPlan testPlan) {
        this.testPlan = testPlan;
    }



    public List<FrameworkMethod> getChildren() {
        return children;
    }

    public void setChildren(List<FrameworkMethod> children) {
        this.children = children;
    }
}
