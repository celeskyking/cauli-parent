/*
 * (c) Copyright 2012 by Volker Bergmann. All rights reserved.
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

import org.databene.commons.ConfigurationError;

import java.lang.reflect.Method;

/**
 * Inherits from {@link FrameworkMethodWithParameters} and overwrites the parent class'
 * behavior to just rethrow an exception that was originally raised upon generator data retrieval.
 * This enables JUnit extensions (e.g. TestRules) to get aware of such errors, which would otherwise remain 
 * 'hidden' in the JUnit framework.<br/><br/>
 * Created: 25.10.2012 12:20:33
 * @since 1.1.9
 * @author Volker Bergmann
 */
public class ErrorReportingFrameworkMethod extends FrameworkMethodWithParameters {
	
	private Throwable error;

	public ErrorReportingFrameworkMethod(Method method, Throwable error, String documentation) {
		super(method, new Object[method.getParameterTypes().length], 1, Feeder.DEFAULT_TIMEOUT, documentation);
		this.error = error;
	}
	
	@Override
	public Object invokeExplosively(Object target, Object... parameters) throws Throwable {
		throw new ConfigurationError("Error initializing generator", error);
	}
	
}
