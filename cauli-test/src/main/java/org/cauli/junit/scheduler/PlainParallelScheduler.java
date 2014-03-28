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

import org.junit.runners.model.RunnerScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Simple {@link org.junit.runners.model.RunnerScheduler} which executes all tests with a fixed number of threads,
 * regardless of possible interferences between different generator methods.<br/><br/>
 * Created: 18.10.2011 13:30:17
 * @since 1.1.0
 * @author Volker Bergmann
 */
public class PlainParallelScheduler implements RunnerScheduler {

	protected long timeout;
	protected ExecutorService executorService;

	public PlainParallelScheduler(int poolSize, long timeout) {
		this.timeout = timeout;
		this.executorService = Executors.newFixedThreadPool(poolSize);
	}

	public void schedule(Runnable task) {
		executorService.submit(task);
	}

	public void finished() {
		executorService.shutdown();
		try {
			boolean ok = executorService.awaitTermination(timeout, TimeUnit.MILLISECONDS);
			if (!ok)
				throw new RuntimeException("Test timed out after " + timeout + " ms");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} 
	}

}
