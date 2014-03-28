/*
 * (c) Copyright 2012-2013 by Volker Bergmann. All rights reserved.
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

import org.cauli.junit.info.DefaultInfoProvider;
import org.databene.benerator.anno.DefaultPathResolver;
import org.databene.benerator.anno.PathResolver;
import org.databene.benerator.engine.BeneratorContext;
import org.databene.benerator.engine.DefaultBeneratorContext;
import org.databene.benerator.factory.EquivalenceGeneratorFactory;
import org.databene.benerator.factory.GeneratorFactory;
import org.databene.commons.ConfigurationError;
import org.databene.commons.IOUtil;
import org.databene.commons.StringUtil;
import org.databene.script.DatabeneScriptParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the Feed4JUnit configuration. By default it reads a file 'feed4junit.properties'. 
 * An alternative configuration file name can be specified by assigning its name to the virtual machine 
 * parameter 'feed4junit.properties'. Example: -Dfeed4junit.properties=alternative_settings.properties<br/><br/>
 * Created: 02.10.2012 10:41:54
 * @since 1.1.7
 * @author Volker Bergmann
 */
public class Feed4JUnitConfig {

	private static final String PROP_PATH_RESOLVER = "pathResolver";
	private static final String PROP_INFO_PROVIDER = "infoProvider";
	private static final String PROP_DEFAULT_GENERATOR_FACTORY = "defaultGeneratorFactory";
	private static final String PROP_XLS_IMPORT_FORMATTED = "xlsImportFormatted";
	private static final String PROP_DEFAULT_LOCALE = "defaultLocale";

	private static final Logger LOGGER = LoggerFactory.getLogger(Feed4JUnitConfig.class);

	public static final String  CONFIG_FILENAME_PROPERTY = "feed4junit.properties";
	public static final String  DEFAULT_CONFIG_FILENAME = "feed4junit.properties";
	private static final String FEED4JUNIT_BASE_PATH = "feed4junit.basepath";
	
	private PathResolver pathResolver;
	private TestInfoProvider infoProvider;
	private String defaultGeneratorFactorySpec;
	
	public Feed4JUnitConfig() {
		String configuredConfigFileName = System.getProperty(CONFIG_FILENAME_PROPERTY);
		String configFileName;
		if (StringUtil.isEmpty(configuredConfigFileName)) {
			configFileName = DEFAULT_CONFIG_FILENAME;
			LOGGER.debug("Trying to use default config file {}", configFileName);
		} else {
			configFileName = configuredConfigFileName;
			LOGGER.debug("Using configured config file {}", configFileName);
		}
		try {
			if (IOUtil.isURIAvailable(configFileName)) {
				// load individual or configured config file
				LOGGER.debug("Config file found, reading...");
				readConfigFile(configFileName);
			} else if (StringUtil.isEmpty(configuredConfigFileName)) {
				// if no explicit config file was configured, then use defaults...
				applyDefaultConfig();
				LOGGER.debug("No config file found, using defaults");
			} else {
				// ...otherwise raise an exception
				LOGGER.error("Specified Feed4JUnit configuration file not found: {}", configuredConfigFileName);
				throw new ConfigurationError("Feed4JUnit configuration file not found: " + configuredConfigFileName);
			}
			LOGGER.debug("Using path resolver {}", pathResolver);
			LOGGER.debug("Using generator info provider {}", infoProvider);
			LOGGER.debug("Using generator factory as default: {}", defaultGeneratorFactorySpec);
		} catch (IOException e) {
			throw new ConfigurationError("Error reading config file '" + configFileName + "'", e);
		}
	}
	
	public PathResolver getPathResolver() {
		return pathResolver;
	}

	public TestInfoProvider getInfoProvider() {
		return infoProvider;
	}
	
	public GeneratorFactory createDefaultGeneratorFactory() {
		return (GeneratorFactory) DatabeneScriptParser.parseBeanSpec(defaultGeneratorFactorySpec).evaluate(new DefaultBeneratorContext());
	}
	
	
	
	// private helper methods ------------------------------------------------------------------------------------------
	
	private void applyDefaultConfig() {
		this.pathResolver = createDefaultPathResolver();
		this.infoProvider = createDefaultInfoProvider();
		this.defaultGeneratorFactorySpec = createDefaultDefaultGeneratorFactorySpec();
	}

	private void readConfigFile(String configFileName) throws IOException {
		BeneratorContext context = new DefaultBeneratorContext();
		Map<String, String> properties = IOUtil.readProperties(configFileName);
		{
			// create path resolver
			String pathResolverSpec = properties.get(PROP_PATH_RESOLVER);
			if (pathResolverSpec != null) {
				this.pathResolver = (PathResolver) DatabeneScriptParser.parseBeanSpec(pathResolverSpec).evaluate(context);
				applyBasePath(pathResolver);
			} else
				this.pathResolver = createDefaultPathResolver();
		}
		{
			// create info provider
			String infoProviderSpec = properties.get(PROP_INFO_PROVIDER);
			if (infoProviderSpec != null)
				this.infoProvider = (TestInfoProvider) DatabeneScriptParser.parseBeanSpec(infoProviderSpec).evaluate(context);
			else
				this.infoProvider = createDefaultInfoProvider();
		}
		{
			// create default generator factory
			String defaultGeneratorSpec = properties.get(PROP_DEFAULT_GENERATOR_FACTORY);
			if (defaultGeneratorSpec != null)
				this.defaultGeneratorFactorySpec = defaultGeneratorSpec;
			else
				this.defaultGeneratorFactorySpec = createDefaultDefaultGeneratorFactorySpec();
		}
		{
			// read xlsImportFormat
			String spec = properties.get(PROP_XLS_IMPORT_FORMATTED);
			if (spec != null)
				PlatformDescriptor.setFormattedByDefault(parseBoolean(spec));
		}
		{
			// read defaultLocale
			String spec = properties.get(PROP_DEFAULT_LOCALE);
			if (spec != null)
				Locale.setDefault(new Locale(spec));
		}
	}

	private PathResolver createDefaultPathResolver() {
		return applyBasePath(new DefaultPathResolver());
	}

	private DefaultInfoProvider createDefaultInfoProvider() {
		return new DefaultInfoProvider();
	}
	
	private String createDefaultDefaultGeneratorFactorySpec() {
		return EquivalenceGeneratorFactory.class.getName();
	}

	private PathResolver applyBasePath(PathResolver resolver) {
		String confdBasePath = System.getProperty(FEED4JUNIT_BASE_PATH);
		if (confdBasePath != null)
			resolver.setBasePath(confdBasePath);
		return resolver;
	}

    public Boolean parseBoolean(String s) {
        return parseBoolean(s, false);
    }
    public Boolean parseBoolean(String s, boolean acceptWhitespace) {
        if (s == null)
            return null;
        if (acceptWhitespace)
            s = s.trim();
        if ("true".equalsIgnoreCase(s))
            return true;
        else if ("false".equalsIgnoreCase(s))
            return false;
        else
            throw new RuntimeException("Not a boolean value");
    }
}
