/*
 * (c) Copyright 2011-2012 by Volker Bergmann. All rights reserved.
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

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;

/**
 * {@link Runnable} implementation that calls {@link org.cauli.junit.Feeder} to execute a generator method.<br/><br/>
 * Created: 18.10.2011 13:15:21
 * @since 1.1.0
 * @author Volker Bergmann
 */
public class ChildRunner implements Runnable {
	
	private Feeder feeder;
	private FrameworkMethod method;
	private RunNotifier notifier;
	
	public ChildRunner(Feeder feeder, FrameworkMethod method, RunNotifier notifier) {
		this.feeder = feeder;
		this.method = method;
		this.notifier = notifier;
	}
	
	public FrameworkMethod getMethod() {
		return method;
	}
	
	public String getPath() {
		return method.getMethod().getDeclaringClass().getName() + '.' + method.getMethod().getName() + '#';
	}

	public String getPathType() {
		return "INVOCATION";
	}

	public void run() {
		feeder.runChild(method, notifier);
	}

	public String toString() {
		return method.toString();
	}
	
}

