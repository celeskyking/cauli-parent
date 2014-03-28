package org.cauli.junit.statement;

import org.junit.runners.model.FrameworkMethod;

public interface Interceptor {
	public void interceptorBefore(FrameworkMethod method, Object test);
	public void interceptorAfter(FrameworkMethod method, Object test);
}
