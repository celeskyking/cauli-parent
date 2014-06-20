package org.cauli.pairwise.core;

import freemarker.template.TemplateException;

import java.io.IOException;

public interface Algorithm {
        /**
         * 
         * @param parameters
         * @param maxCombinationLimit
         *                -1 means no up limit
         * @return
         */
        public CombinationList generate(ParameterList parameters,
                                        int maxCombinationLimit) throws IOException, TemplateException;

}
