package org.cauli.pairwise.core;

import freemarker.template.TemplateException;

import java.io.IOException;

public class Generator {

        private final Algorithm algorithm;

        public Generator(Algorithm algorithm) {
                this.algorithm = algorithm;
        }

        public CombinationList generate(ParameterList parameters,
                        int maxCombinationLimit) throws IOException, TemplateException {
                return algorithm.generate(parameters, maxCombinationLimit);
        }

}
