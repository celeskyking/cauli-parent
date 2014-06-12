package org.cauli.junit.statement;

import com.google.common.collect.Lists;
import jodd.util.StringUtil;
import org.cauli.RunConfig;
import org.cauli.junit.CauliRunner;
import org.cauli.junit.ExcuteScheduler;
import org.cauli.junit.FrameworkMethodWithParameters;
import org.cauli.junit.MethodManager;
import org.cauli.exception.TestFailedError;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class InterceptorStatement extends Statement {
	private Logger logger = LoggerFactory.getLogger(InterceptorStatement.class);
	protected final FrameworkMethodWithParameters testMethod;
    protected Object target;
    private int level;
    private List<InterceptorStatement> dependencyStatement= Lists.newArrayList();
    protected int retryTimes=0;
    private int runLevel;
	public InterceptorStatement(FrameworkMethodWithParameters methodWithParameters, Object target) {
		this.testMethod=methodWithParameters;
		this.target=target;
        String depName = methodWithParameters.getDependencyMethodName();
        if(StringUtil.isNotEmpty(depName)){
            List<FrameworkMethodWithParameters> methods = MethodManager.get(depName);
            for(FrameworkMethodWithParameters method:methods){
                if(method!=null){
                    this.dependencyStatement.add(new InterceptorStatement(method,target));
                }
            }
        }
        this.level=methodWithParameters.getLevel();
        this.interceptors.add(new AnnotationInterceptor());
	}
    private List<Interceptor> interceptors = new ArrayList<Interceptor>();

    @Override
	public void evaluate() throws Throwable {
            runRetry();
	}


    public FrameworkMethod getTestMethod() {
        return testMethod;
    }

    public Object getTarget() {
        return target;
    }

    protected void runRetry(){
        for(Interceptor interceptor:interceptors){
            interceptor.interceptorBeforeRetryTimeConfig(this);
        }
        for(int i=0;i<=retryTimes;i++){
            try{
                for(Interceptor interceptor:interceptors){
                    interceptor.interceptorBefore(this);
                }
                if(this.dependencyStatement==null){
                    testMethod.invokeExplosively(target);
                }else{
                    ExcuteScheduler scheduler = new ExcuteScheduler(target.getClass());
                    for(final InterceptorStatement statement:this.dependencyStatement){
                        scheduler.schedule(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    statement.evaluate();
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        });
                    }
                    scheduler.finished();
                    testMethod.invokeExplosively(target);
                }

                for(Interceptor interceptor:interceptors){
                    interceptor.interceptorAfter(this);
                }
                break;
            }catch(Throwable e){
                e.printStackTrace();
                for(Interceptor interceptor:interceptors){
                    interceptor.interceptorAfterForce(this);
                }
                logger.error("用例执行失败了,异常信息->" + e.getMessage());
                if(i==retryTimes){
                    throw new TestFailedError("["+this.testMethod.getName()+"]用例执行失败了！",e);
                }else{
                    logger.info("用例执行失败，重新执行失败的方法-->"+testMethod.getName());
                }
            }
        }
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public void addInterceptor(Interceptor interceptor){
        interceptors.add(interceptor);
    }

    public int getRunLevel() {
        return runLevel;
    }

    public void setRunLevel(int runLevel) {
        this.runLevel = runLevel;
    }

    public List<InterceptorStatement> getDependencyStatement() {
        return dependencyStatement;
    }

    public void setDependencyStatement(List<InterceptorStatement> dependencyStatement) {
        this.dependencyStatement = dependencyStatement;
    }
}
