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

package org.cauli.junit;

import org.databene.benerator.util.UnsafeGenerator;

/**
 * Simple {@link org.databene.benerator.Generator} implementation for Feed4JUnit.<br/><br/>
 * Created: 30.06.2011 17:54:41
 * @since 0.6.7
 * @author Volker Bergmann
 * @deprecated replaced with org.databene.benerator.util.SimpleGenerator
 */
@SuppressWarnings("unused")
public abstract class Feed4JUnitGenerator extends UnsafeGenerator<Object[]> { // TODO v1.2 remove class
	
	public Feed4JUnitGenerator() {
		System.out.println("WARNING: " + getClass() + " extends the deprecated class " + 
				Feed4JUnitGenerator.class.getName() + " which will be removed in version 1.2. " +
				getClass() + " should extend " + UnsafeGenerator.class.getName() + " instead");
	}

}
