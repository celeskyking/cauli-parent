package org.cauli.junit.statement;

import jodd.util.StringUtil;
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
    private InterceptorStatement dependencyStatement;
    protected int retryTimes=0;
    private int runLevel;
	public InterceptorStatement(FrameworkMethodWithParameters methodWithParameters, Object target) {
		this.testMethod=methodWithParameters;
		this.target=target;
        String depName = methodWithParameters.getDependencyMethodName();
        if(StringUtil.isNotEmpty(depName)){
            this.dependencyStatement=new InterceptorStatement(MethodManager.get(depName),target);
        }
        this.level=methodWithParameters.getLevel();
	}
    private List<Interceptor> interceptors = new ArrayList<Interceptor>();

    @Override
	public void evaluate() throws Throwable {
        logger.info("*******************测试用例["+this.testMethod.getName()+"]开始执行*****************");
        //*****我们来定义方法级别的监听器注解，可以为专门的一个方法添加注解。也可以去判断类是否有监听器的注解来实现自动的注册和卸载。
        runRetry();
        logger.info("*******************测试用例["+testMethod.getName()+"]执行结束****************");
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
                    this.dependencyStatement.evaluate();
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

    public InterceptorStatement getDependencyStatement() {
        return dependencyStatement;
    }

    public void setDependencyStatement(InterceptorStatement dependencyStatement) {
        this.dependencyStatement = dependencyStatement;
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
}
