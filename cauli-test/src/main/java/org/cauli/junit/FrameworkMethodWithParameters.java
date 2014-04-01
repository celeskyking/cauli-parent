package org.cauli.junit;

import org.junit.runners.model.FrameworkMethod;

import java.lang.reflect.Method;

/**
 * Enhances JUnit's original {@link org.junit.runners.model.FrameworkMethod} class with invocation parameters.<br/><br/>
 * Created: 07.05.2010 23:04:16
 * @since 0.6.2
 * @author Volker Bergmann
 */
public class FrameworkMethodWithParameters extends FrameworkMethod {
	
	protected Object[] parameters;
	protected Integer threadPoolSize;
	protected long timeout;
	protected String info;
	public FrameworkMethodWithParameters(Method method, Object[] parameters, Integer threadPoolSize, long timeout, String info) {
	    super(method);
	    this.parameters = parameters;
	    this.threadPoolSize = threadPoolSize;
	    this.timeout = timeout;
	    this.info = info;
    }

    public FrameworkMethodWithParameters(Method method){
        super(method);
    }

    public FrameworkMethodWithParameters(Method method,Object[] parameters,String info){
        this(method,parameters,null,3000,info);
    }

	public Integer getThreadPoolSize() {
		return threadPoolSize;
	}
	
	public long getTimeout() {
		return timeout;
	}
	
	@Override
	public Object invokeExplosively(Object target, Object... parameters) throws Throwable {
		return super.invokeExplosively(target, this.parameters);
	}

	@Override
	public String toString() {
		return getMethod().getName() + '<' + info + '>';
	}

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public void setThreadPoolSize(Integer threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
