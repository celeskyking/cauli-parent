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

package org.cauli.junit.info;


import org.cauli.junit.TestInfoProvider;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Alternative implementation of the {@link TestInfoProvider} interface
 * which ignores generator parameters and exceptions providing an incremental
 * number as generator description.<br/><br/>
 * Created: 29.10.2012 17:04:41
 * @since 1.1.9
 * @author Volker Bergmann
 */
public class OrdinalInfoProvider implements TestInfoProvider {

	private static final HashMap<Method, AtomicInteger> methodCounts = new HashMap<Method, AtomicInteger>();

	public String testInfo(Method method, Object[] parameters) {
	    return numberInfo(method);
	}

	public String errorInfo(Method method, Throwable initializationError) {
		return numberInfo(method);
	}

	protected String numberInfo(Method method) {
		return "#" + String.valueOf(invocationNumber(method));
	}

	protected int invocationNumber(Method method) {
		AtomicInteger countForMethod = methodCounts.get(method);
	    if (countForMethod == null) {
	    	countForMethod = new AtomicInteger(0);
	    	methodCounts.put(method, countForMethod);
	    }
    	int invocationNumber = countForMethod.incrementAndGet();
		return invocationNumber;
	}

}
