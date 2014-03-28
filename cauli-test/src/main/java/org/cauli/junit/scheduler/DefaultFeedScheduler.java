/*
 * (c) Copyright 2011 by Volker Bergmann. All rights reserved.
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

package org.cauli.junit.scheduler;

import org.cauli.junit.ChildRunner;
import org.cauli.junit.FrameworkMethodWithParameters;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.RunnerScheduler;

/**
 * FReed4JUnit's default {@link org.junit.runners.model.RunnerScheduler}. It uses sub the {@link PlainParallelScheduler}
 * as delegate to perform all invocations of a single generator method in parallel and then waits for
 * the invocations to finish before starting tests on the next generator method.<br/><br/>
 * Created: 18.10.2011 12:24:19
 * @since 1.1.0
 * @author Volker Bergmann
 */
public class DefaultFeedScheduler implements RunnerScheduler {

	protected int defaultPoolSize;
	protected long defaultTimeout;
	protected FrameworkMethod lastMethod;
	protected RunnerScheduler subScheduler;
	
	public DefaultFeedScheduler(int defaultPoolSize, long defaultTimeout) {
		this.defaultPoolSize = defaultPoolSize;
		this.defaultTimeout = defaultTimeout;
		this.lastMethod = null;
		this.subScheduler = null;
	}

	public void schedule(Runnable task) {
		ChildRunner runner = (ChildRunner) task;
		FrameworkMethod method = runner.getMethod();
		if (!method.equals(lastMethod)) {
			if (subScheduler != null)
				subScheduler.finished();
			int threads = defaultPoolSize;
			if (method instanceof FrameworkMethodWithParameters) {
				Integer tmp = ((FrameworkMethodWithParameters) method).getThreadPoolSize();
				if (tmp != null)
					threads = tmp;
			}
			long timeout = (method instanceof FrameworkMethodWithParameters ? 
					((FrameworkMethodWithParameters) method).getTimeout() : defaultTimeout);
			subScheduler = new PlainParallelScheduler(threads, timeout);
		}
		subScheduler.schedule(runner);
		lastMethod = method;
	}

	public void finished() {
		subScheduler.finished();
	}

}
