package org.cauli.junit.statement;


public interface Interceptor {
	public void interceptorBefore(InterceptorStatement statement);
	public void interceptorAfter(InterceptorStatement statement);
    public void interceptorAfterForce(InterceptorStatement statement);
    public void interceptorBeforeRetryTimeConfig(InterceptorStatement statement);
}
