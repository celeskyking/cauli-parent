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
	protected String info;


    public FrameworkMethodWithParameters(Method method){
        super(method);
    }

    public FrameworkMethodWithParameters(Method method,Object[] parameters,String info){
        super(method);
        this.parameters=parameters;
        this.info=info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
