package org.cauli.junit.statement;

import org.apache.commons.lang3.StringUtils;
import org.cauli.db.DBCore;
import org.cauli.db.DbManager;
import org.cauli.db.annotation.SQL;
import org.cauli.junit.anno.Retry;
import org.cauli.junit.exception.TestFailedError;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class InterceptorStatement extends Statement {
	private Logger logger = LoggerFactory.getLogger(InterceptorStatement.class);
	protected final FrameworkMethod testMethod;
    protected Object target;
    protected int times=0;
	public InterceptorStatement(FrameworkMethod testMethod, Object target) {
		this.testMethod=testMethod;
		this.target=target;
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
        execSQL();
        initRetryTimes();
        for(int i=0;i<=times;i++){
            try{
                for(Interceptor interceptor:interceptors){
                    interceptor.interceptorBefore(testMethod, target);
                }
                testMethod.invokeExplosively(target);
                for(Interceptor interceptor:interceptors){
                    interceptor.interceptorAfter(testMethod, target);
                }
                break;
            }catch(Throwable e){
                e.printStackTrace();
                for(Interceptor interceptor:interceptors){
                    interceptor.interceptorAfterForce(testMethod, target);
                }
                logger.error("用例执行失败了,异常信息->" + e.getMessage());
                if(i==times){
                    throw new TestFailedError("["+this.testMethod.getName()+"]用例执行失败了！",e);
                }else{
                    logger.info("用例执行失败，重新执行失败的方法-->"+testMethod.getName());
                }
            }
        }
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    private void execSQL(){
        if(getTestMethod().getMethod().isAnnotationPresent(SQL.class)){
            SQL sql = getTestMethod().getAnnotation(SQL.class);
            String id = sql.id();
            String value = sql.value();
            if(!StringUtils.startsWith(value.toLowerCase(),"select")){
                if(DbManager.getDBCore(id)!=null){
                    DBCore core = DbManager.getDBCore(id);
                    core.update(value);
                }
            }
        }
    }

    private void initRetryTimes(){
        if(getTestMethod().getMethod().isAnnotationPresent(Retry.class)){
            setTimes(getTestMethod().getMethod().getAnnotation(Retry.class).value());
            logger.info("["+getTestMethod().getName()+"]>>>这个case执行失败的话会被重新执行"+getTimes()+"次");
            //System.out.println(this.testMethod.getMethod().getDeclaringClass().getName());
        }else if(getTestMethod().getMethod().getDeclaringClass().isAnnotationPresent(Retry.class)){
            setTimes(getTestMethod().getMethod().getDeclaringClass().getAnnotation(Retry.class).value());
            logger.info("["+getTestMethod().getName()+"]>>>这个case执行失败的话会被重新执行"+getTimes()+"次");
        }else{
            setTimes(0);
        }
    }

    public void addInterceptor(Interceptor interceptor){
        interceptors.add(interceptor);
    }
}
