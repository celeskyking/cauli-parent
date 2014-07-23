package org.cauli.junit;

import org.cauli.Constant;
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
    private String name;
    private int level=Constant.DEFAULT_LEVEL;
    private String[] dependencyMethodName;
    private String feature=Constant.DEFAULT_FEATURE;
    private String release=Constant.DEFAULT_RELEASE;
    private long timeout= Constant.DEFAULT_TIME_OUT;



    public FrameworkMethodWithParameters(Method method){
        super(method);
    }

    public FrameworkMethodWithParameters(Method method,Object[] parameters,String info){
        super(method);
        this.parameters=parameters;
        this.info=info;
    }

    public FrameworkMethodWithParameters(Method method,Object[] parameters,String info,int level,String[] dependencyMethodName,String tagName){
        super(method);
        this.parameters=parameters;
        this.info=info;
        this.level=level;
        this.dependencyMethodName=dependencyMethodName;
        this.name=tagName;
    }



	
	@Override
	public Object invokeExplosively(Object target, Object... parameters) throws Throwable {
        return super.invokeExplosively(target, this.parameters);
	}

	@Override
	public String toString() {
        if(info==null){
            return getMethod().getName();
        }else{
            return getMethod().getName() + '<' + info + '>';
        }

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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String[] getDependencyMethodName() {
        return dependencyMethodName;
    }

    public void setDependencyMethodName(String[] dependencyMethodName) {
        this.dependencyMethodName = dependencyMethodName;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
