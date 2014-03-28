/*
 * (c) Copyright 2010-2013 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

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

}
