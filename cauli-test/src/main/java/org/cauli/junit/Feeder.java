
package org.cauli.junit;

import org.cauli.junit.anno.Param;
import org.cauli.junit.scheduler.DefaultFeedScheduler;
import org.databene.benerator.Generator;
import org.databene.benerator.anno.AnnotationMapper;
import org.databene.benerator.anno.Source;
import org.databene.benerator.anno.ThreadPoolSize;
import org.databene.benerator.engine.BeneratorContext;
import org.databene.benerator.engine.DefaultBeneratorContext;
import org.databene.benerator.wrapper.ProductWrapper;
import org.databene.commons.ConfigurationError;
import org.databene.commons.Period;
import org.databene.commons.converter.AnyConverter;
import org.databene.platform.java.Entity2JavaConverter;
import org.databene.script.DatabeneScriptParser;
import org.databene.script.Expression;
import org.junit.Test;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 */
public class Feeder extends BlockJUnit4ClassRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Feeder.class);
	
	public static final long DEFAULT_TIMEOUT = Period.WEEK.getMillis();
	
	static {
		ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
	}
	
	private Feed4JUnitConfig config;
	private BeneratorContext context;
	private AnnotationMapper annotationMapper;
	
	private List<FrameworkMethod> children;
	private RunnerScheduler scheduler;
	
	public Feeder(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	protected String testName(FrameworkMethod method) {
		return (method instanceof FrameworkMethodWithParameters ? method.toString() : super.testName(method));
	}
	
	@Override
	public void setScheduler(RunnerScheduler scheduler) {
		this.scheduler = scheduler;
		super.setScheduler(scheduler);
	}
	
	/**
	 * Instantiates a generator class and initializes attributes
	 * which have been marked with a @Source annotation.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Object createTest() throws Exception {
		Object testObject = super.createTest();
		for (FrameworkField attribute : getTestClass().getAnnotatedFields(org.databene.benerator.anno.Source.class)) {
			if ((attribute.getField().getModifiers() & Modifier.PUBLIC) == 0)
				throw new ConfigurationError("Attribute '" + attribute.getField().getName() + "' must be public");
			Generator<?> generator = getAnnotationMapper().createAndInitAttributeGenerator(attribute.getField(), getContext());
			if (generator != null) {
				ProductWrapper wrapper = new ProductWrapper();
				wrapper = generator.generate(wrapper);
				if (wrapper != null)
					attribute.getField().set(testObject, wrapper.unwrap());
			}
		}
		return testObject;
	}

    private File getSimilarFile(String dir,String partFileName) throws FileNotFoundException {
        File dirFile = new File(dir);
        if(dirFile==null){
            throw new FileNotFoundException("文件没有找到,查找的目录不存在");
        }
        if(dirFile.isDirectory()){
            File[] files = dirFile.listFiles();
            for(File file:files){
                if(file.getName().contains(partFileName)){
                    return file;
                }
            }
        }else if(!dirFile.isDirectory()){
            return dirFile;
        }else{
            throw new FileNotFoundException("文件没有找到,查找的目录不存在");
        }
        return null;
    }

	
	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		if (children == null) {
			children = new ArrayList<FrameworkMethod>();
			TestClass testClass = getTestClass();
			AnnotationMapper annotationMapper = getAnnotationMapper();
			for (FrameworkMethod method : testClass.getAnnotatedMethods(Test.class)) {
				if (method.getMethod().getParameterTypes().length == 0) {
					// standard JUnit generator method
					children.add(method);
					continue;
				} else if(method.getMethod().isAnnotationPresent(Param.class)){
                    Param param = method.getAnnotation(Param.class);
                    String path = param.value();
                    String type = param.type();
                    File file;
                    if("default".equals(path)){
                        try {
                            file=getSimilarFile("data/"+method.getMethod().getDeclaringClass().getSimpleName(),method.getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException("文件没有找到,查找的目录不存在。在进行参数化的时候出现了错误...",e);
                        }
                    }else{
                        file= new File(path);
                    }
                    ParameterGenerator generator = new ParameterGenerator(file);
                    generator.setReadType(type);
                    List<FrameworkMethodWithParameters> methods;
                    try {
                        methods = generator.generator(method);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException("初始化方法的时候出现了错误.."+method.getName());
                    }
                    children.addAll(methods);
                }else{
                    if(!method.getMethod().isAnnotationPresent(Source.class)&&method.getMethod().getParameterTypes().length>0){
                        throw new RuntimeException(method.getName()+":参数化数据和方法参数个数不匹配，请检查..");
                    }
                    // parameterized Feed4JUnit generator method
                    BeneratorContext context = getContext();
                    context.setGeneratorFactory(config.createDefaultGeneratorFactory());
                    annotationMapper.parseClassAnnotations(testClass.getAnnotations(), context);
                    List<? extends FrameworkMethod> parameterizedTestMethods;
                    parameterizedTestMethods = computeParameterizedTestMethods(method.getMethod(), context);
                    children.addAll(parameterizedTestMethods);
                }
			}
		}
		return children;
	}

	@Override
    protected void validateTestMethods(List<Throwable> errors) {
		validatePublicVoidMethods(Test.class, false, errors);
	}

	// generator execution --------------------------------------------------------------------------------------------------
	
//	protected Statement childrenInvoker(final RunNotifier notifier) {
//		return new Statement() {
//			@Override
//			public void evaluate() {
//				runChildren(notifier);
//			}
//		};
//	}
//
//	private void runChildren(final RunNotifier notifier) {
//        RunnerScheduler scheduler = getScheduler();
//		for (FrameworkMethod method : getChildren())
// 			scheduler.schedule(new ChildRunner(this, method, notifier));
//		scheduler.finished();
//	}
	
	/** this is needed to make the runChild() method public and thus accessible from other classes, especially {@link ChildRunner}. */
	@Override
	public void runChild(FrameworkMethod method, RunNotifier notifier) {
		super.runChild(method, notifier);
	}

	public RunnerScheduler getScheduler() {
		if (scheduler == null)
			scheduler = createDefaultScheduler();
		return scheduler;
	}
	
	protected RunnerScheduler createDefaultScheduler() {
		TestClass testClass = getTestClass();
		Scheduler annotation = testClass.getJavaClass().getAnnotation(Scheduler.class);
		if (annotation != null) {
			String spec = annotation.value();
			Expression<?> bean = DatabeneScriptParser.parseBeanSpec(spec);
			return (RunnerScheduler) bean.evaluate(null);
		} else {
			return new DefaultFeedScheduler(1, DEFAULT_TIMEOUT);
		}
	}
	
	
	
	// helpers ---------------------------------------------------------------------------------------------------------

	private void validatePublicVoidMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors) {
		List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);
		for (FrameworkMethod eachTestMethod : methods)
			eachTestMethod.validatePublicVoid(isStatic, errors);
	}


	private List<FrameworkMethodWithParameters> computeParameterizedTestMethods(Method method, BeneratorContext context) {
		Integer threads = getThreadCount(method);
		long timeout = getTimeout(method);
		List<FrameworkMethodWithParameters> result = new ArrayList<FrameworkMethodWithParameters>();
		Class<?>[] parameterTypes = method.getParameterTypes();
		AnnotationMapper annotationMapper = getAnnotationMapper();
		TestInfoProvider infoProvider = getConfig().getInfoProvider();
		try {
			Generator<Object[]> paramGenerator = annotationMapper.createAndInitMethodParamsGenerator(method, context);
			Class<?>[] expectedTypes = parameterTypes;
			ProductWrapper<Object[]> wrapper = new ProductWrapper<Object[]>();
			int count = 0;
			while ((wrapper = paramGenerator.generate(wrapper)) != null) {
				Object[] generatedParams = wrapper.unwrap();
				if (generatedParams.length > expectedTypes.length) // imported data may have more columns than the method parameters, ...
					generatedParams = Arrays.copyOfRange(generatedParams, 0, expectedTypes.length); // ...so cut them
				for (int i = 0; i < generatedParams.length; i++) {
					generatedParams[i] = Entity2JavaConverter.convertAny(generatedParams[i]);
					generatedParams[i] = AnyConverter.convert(generatedParams[i], parameterTypes[i]);
				}
				Object[] usedParams = new Object[parameterTypes.length];
				System.arraycopy(generatedParams, 0, usedParams, 0, Math.min(generatedParams.length, usedParams.length));
				String info = infoProvider.testInfo(method, usedParams);
				result.add(new FrameworkMethodWithParameters(method, usedParams, threads, timeout, info));
				count++;
			}
			if (count == 0)
				throw new RuntimeException("No parameter values available for method: " + method);
		} catch (Exception e) {
			LOGGER.error("Error creating generator parameters", e);
			String info = infoProvider.errorInfo(method, e);
			result.add(new ErrorReportingFrameworkMethod(method, e, info));
		}
		return result;
	}

	private Integer getThreadCount(Method method) {
		ThreadPoolSize methodAnnotation = method.getAnnotation(ThreadPoolSize.class);
		if (methodAnnotation != null)
			return methodAnnotation.value();
		Class<?> testClass = method.getDeclaringClass();
		ThreadPoolSize classAnnotation = testClass.getAnnotation(ThreadPoolSize.class);
		if (classAnnotation != null)
			return classAnnotation.value();
		return null;
	}

	private long getTimeout(Method method) {
		return DEFAULT_TIMEOUT;
	}
	
	protected Feed4JUnitConfig getConfig() {
		if (this.config == null)
			init();
		return this.config;
	}
	
	public BeneratorContext getContext() {
		if (this.config == null)
			init();
		return this.context;
	}
	
	private AnnotationMapper getAnnotationMapper() {
		if (this.config == null)
			init();
		return this.annotationMapper;
	}
	
	private void init() {
		this.config = new Feed4JUnitConfig();
		this.context = new DefaultBeneratorContext();
		this.annotationMapper = new AnnotationMapper(context.getGeneratorFactory(),context.getDataModel(), config.getPathResolver());
	}

}
