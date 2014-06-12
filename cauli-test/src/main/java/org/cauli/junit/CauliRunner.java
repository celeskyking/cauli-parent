package org.cauli.junit;

import com.google.common.collect.Lists;
import org.cauli.exception.FrameworkBuildException;
import org.cauli.instrument.ClassPool;
import org.cauli.instrument.ClassUtils;
import org.cauli.junit.anno.CauliRule;
import org.cauli.junit.anno.Filter;
import org.cauli.junit.anno.Listener;
import org.cauli.junit.anno.ThreadRunner;
import org.cauli.junit.build.FrameworksBuilderFactory;
import org.cauli.junit.statement.InterceptorStatement;
import org.junit.*;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.*;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static org.junit.internal.runners.rules.RuleFieldValidator.RULE_METHOD_VALIDATOR;
import static org.junit.internal.runners.rules.RuleFieldValidator.RULE_VALIDATOR;

/**
 * Created by tianqing.wang on 2014/6/10
 */
public class CauliRunner  extends ParentRunner<FrameworkMethodWithParameters>{

    private TestPlan testPlan;

    private Logger logger;

    private List<FrameworkMethodWithParameters> children;


    public CauliRunner(Class<?> testClass) throws InitializationError {
        super(testClass);

    }


    protected void init(){
        this.testPlan=new TestPlan();
        this.children=Lists.newArrayList();
        this.logger = LoggerFactory.getLogger(CauliRunner.class);
        int threads= getTestClass().getJavaClass().isAnnotationPresent(ThreadRunner.class)?getTestClass().getJavaClass().getAnnotation(ThreadRunner.class).threads():1;
        testPlan.setThreads(threads);
        if(getClass().isAnnotationPresent(Filter.class)){
            Filter filter = getClass().getAnnotation(Filter.class);
            testPlan.setRunLevel(filter.runLevel());
            testPlan.setRunFeature(filter.feature());
            testPlan.setRunRelease(filter.release());
        }
        testPlan.setListeners(getListeners());
        setScheduler(new ExcuteScheduler(testPlan.getThreads()));
    }

    protected List<TestRule> getListeners(){
        List<TestRule> testRules = Lists.newArrayList();
        Set<Class<?>> classes = ClassPool.getClassPool();
        for(Class<?> clazz :classes){
            if(!ClassUtils.isAssignableFromSubClass(TestRule.class,clazz)){
                continue;
            }
            if(clazz.isAnnotationPresent(CauliRule.class)||clazz.isAnnotationPresent(Listener.class)){
                try {
                    testRules.add((TestRule) clazz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return testRules;
    }

    @Override
    protected List<FrameworkMethodWithParameters> getChildren() {
        return computeTestMethods();
    }

    @Override
    protected Description describeChild(FrameworkMethodWithParameters method) {
        return Description.createTestDescription(getTestClass().getJavaClass(),
                testName(method), method.getAnnotations());
    }

    @Override
    protected void runChild(FrameworkMethodWithParameters method, RunNotifier notifier) {
        Description description = describeChild(method);
        if (method.getAnnotation(Ignore.class) != null) {
            notifier.fireTestIgnored(description);
        } else {
            runLeaf(methodBlock(method), description, notifier);
        }
    }

    protected Object createTest() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance();
    }

    protected Statement methodBlock(FrameworkMethodWithParameters method) {
        Object test;
        try {
            test = new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return createTest();
                }
            }.run();
        } catch (Throwable e) {
            return new Fail(e);
        }

        Statement statement = methodInvoker(method, test);
        statement = possiblyExpectingExceptions(method, test, statement);
        statement = withPotentialTimeout(method, test, statement);
        statement = withBefores(method, test, statement);
        statement = withAfters(method, test, statement);
        statement = withRules(method, test, statement);
        return statement;
    }


    protected Statement methodInvoker(FrameworkMethodWithParameters method, Object test) {
        InterceptorStatement statement = new InterceptorStatement(method, test);
        statement.setRunLevel(testPlan.getRunLevel());
        statement.setRetryTimes(testPlan.getRetryTimes());
        return statement;
    }

    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> result = getTestClass().getAnnotatedMethodValues(target,
                Rule.class, TestRule.class);

        result.addAll(getTestClass().getAnnotatedFieldValues(target,
                Rule.class, TestRule.class));
        result.addAll(getRules());
        return result;
    }

    private Class<? extends Throwable> getExpectedException(Test annotation) {
        if (annotation == null || annotation.expected() == Test.None.class) {
            return null;
        } else {
            return annotation.expected();
        }
    }

    private boolean expectsException(Test annotation) {
        return getExpectedException(annotation) != null;
    }

    private long getTimeout(Test annotation) {
        if (annotation == null) {
            return testPlan.getTimeout();
        }
        return annotation.timeout();
    }


    protected void validateTestMethods(List<Throwable> errors) {
        validatePublicVoidMethods(Test.class, false, errors);
    }

    private void validatePublicVoidMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors) {
        List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);
        for (FrameworkMethod eachTestMethod : methods)
            eachTestMethod.validatePublicVoid(isStatic, errors);
    }






    protected List<FrameworkMethodWithParameters> computeTestMethods() {
        CauliFilter cauliFilter=new CauliFilter();
        if (children == null||children.size()==0) {
            TestClass testClass = getTestClass();
            cauliFilter.setRunLevel(testPlan.getRunLevel());
            cauliFilter.setRunFeature(testPlan.getRunFeature());
            cauliFilter.setRunRealease(testPlan.getRunRelease());
            List<FrameworkMethodWithParameters> list;
            try {
                list = FrameworksBuilderFactory.getInstance(testPlan.getFrameworksBuilder()).getFrameworkBuilder().build(testClass);
            } catch (FrameworkBuildException e) {
                throw new RuntimeException(e);
            }
            for(FrameworkMethodWithParameters frameworkMethodWithParameters : list){
                if(cauliFilter.isMatch(frameworkMethodWithParameters)){
                    children.add(frameworkMethodWithParameters);
                }else{
                    logger.info("[{}]被忽略,可能过滤器不匹配或者优先级不匹配",frameworkMethodWithParameters.getName());
                }
            }
            MethodManager.load(list);
        }
        return children;
    }

    public List<TestRule> getRules()  {
        return this.testPlan.getListeners();
    }


    public TestPlan getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(TestPlan testPlan) {
        this.testPlan = testPlan;
    }


    @Deprecated
    protected Statement possiblyExpectingExceptions(FrameworkMethodWithParameters method,
                                                    Object test, Statement next) {
        Test annotation = method.getAnnotation(Test.class);
        return expectsException(annotation) ? new ExpectException(next,
                getExpectedException(annotation)) : next;
    }

    /**
     * Returns a {@link Statement}: if {@code method}'s {@code @Test} annotation
     * has the {@code timeout} attribute, throw an exception if {@code next}
     * takes more than the specified number of milliseconds.
     *
     * @deprecated Will be private soon: use Rules instead
     */
    @Deprecated
    protected Statement withPotentialTimeout(FrameworkMethodWithParameters method,
                                             Object test, Statement next) {
        long timeout = method.getTimeout();
        return timeout > 0 ? new FailOnTimeout(next, timeout) : next;
    }

    /**
     * Returns a {@link Statement}: run all non-overridden {@code @Before}
     * methods on this class and superclasses before running {@code next}; if
     * any throws an Exception, stop execution and pass the exception on.
     *
     * @deprecated Will be private soon: use Rules instead
     */
    @Deprecated
    protected Statement withBefores(FrameworkMethodWithParameters method, Object target,
                                    Statement statement) {
        List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(
                Before.class);
        return befores.isEmpty() ? statement : new RunBefores(statement,
                befores, target);
    }

    /**
     * Returns a {@link Statement}: run all non-overridden {@code @After}
     * methods on this class and superclasses before running {@code next}; all
     * After methods are always executed: exceptions thrown by previous steps
     * are combined, if necessary, with exceptions from After methods into a
     * {@link org.junit.runners.model.MultipleFailureException}.
     *
     * @deprecated Will be private soon: use Rules instead
     */
    @Deprecated
    protected Statement withAfters(FrameworkMethodWithParameters method, Object target,
                                   Statement statement) {
        List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(
                After.class);
        return afters.isEmpty() ? statement : new RunAfters(statement, afters,
                target);
    }

    private Statement withRules(FrameworkMethodWithParameters method, Object target,
                                Statement statement) {
        List<TestRule> testRules = getTestRules(target);
        Statement result = statement;
        result = withMethodRules(method, testRules, target, result);
        result = withTestRules(method, testRules, result);

        return result;
    }

    private Statement withMethodRules(FrameworkMethodWithParameters method, List<TestRule> testRules,
                                      Object target, Statement result) {
        for (org.junit.rules.MethodRule each : getMethodRules(target)) {
            if (!testRules.contains(each)) {
                result = each.apply(result, method, target);
            }
        }
        return result;
    }

    private List<org.junit.rules.MethodRule> getMethodRules(Object target) {
        return rules(target);
    }

    /**
     * @param target the test case instance
     * @return a list of MethodRules that should be applied when executing this
     *         test
     */
    protected List<org.junit.rules.MethodRule> rules(Object target) {
        return getTestClass().getAnnotatedFieldValues(target, Rule.class,
                org.junit.rules.MethodRule.class);
    }

    private Statement withTestRules(FrameworkMethodWithParameters method, List<TestRule> testRules,
                                    Statement statement) {
        return testRules.isEmpty() ? statement :
                new RunRules(statement, testRules, describeChild(method));
    }


    @Override
    protected void collectInitializationErrors(List<Throwable> errors) {
        init();
        super.collectInitializationErrors(errors);

        validateNoNonStaticInnerClass(errors);
        validateConstructor(errors);
        validateInstanceMethods(errors);
        validateFields(errors);
        validateMethods(errors);
    }

    protected void validateNoNonStaticInnerClass(List<Throwable> errors) {
        if (getTestClass().isANonStaticInnerClass()) {
            String gripe = "The inner class " + getTestClass().getName()
                    + " is not static.";
            errors.add(new Exception(gripe));
        }
    }

    /**
     * Adds to {@code errors} if the test class has more than one constructor,
     * or if the constructor takes parameters. Override if a subclass requires
     * different validation rules.
     */
    protected void validateConstructor(List<Throwable> errors) {
        validateOnlyOneConstructor(errors);
        validateZeroArgConstructor(errors);
    }

    /**
     * Adds to {@code errors} if the test class has more than one constructor
     * (do not override)
     */
    protected void validateOnlyOneConstructor(List<Throwable> errors) {
        if (!hasOneConstructor()) {
            String gripe = "Test class should have exactly one public constructor";
            errors.add(new Exception(gripe));
        }
    }

    /**
     * Adds to {@code errors} if the test class's single constructor takes
     * parameters (do not override)
     */
    protected void validateZeroArgConstructor(List<Throwable> errors) {
        if (!getTestClass().isANonStaticInnerClass()
                && hasOneConstructor()
                && (getTestClass().getOnlyConstructor().getParameterTypes().length != 0)) {
            String gripe = "Test class should have exactly one public zero-argument constructor";
            errors.add(new Exception(gripe));
        }
    }

    private boolean hasOneConstructor() {
        return getTestClass().getJavaClass().getConstructors().length == 1;
    }

    /**
     * Adds to {@code errors} for each method annotated with {@code @Test},
     * {@code @Before}, or {@code @After} that is not a public, void instance
     * method with no arguments.
     *
     * @deprecated unused API, will go away in future version
     */
    @Deprecated
    protected void validateInstanceMethods(List<Throwable> errors) {
        validatePublicVoidNoArgMethods(After.class, false, errors);
        validatePublicVoidNoArgMethods(Before.class, false, errors);
        validateTestMethods(errors);

        if (computeTestMethods().size() == 0) {
            errors.add(new Exception("No runnable methods"));
        }
    }

    protected void validateFields(List<Throwable> errors) {
        RULE_VALIDATOR.validate(getTestClass(), errors);
    }

    private void validateMethods(List<Throwable> errors) {
        RULE_METHOD_VALIDATOR.validate(getTestClass(), errors);
    }


    protected String testName(FrameworkMethodWithParameters method) {
        return method.toString();
    }


}
