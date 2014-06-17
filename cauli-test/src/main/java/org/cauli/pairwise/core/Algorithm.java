package org.cauli.pairwise.core;

public interface Algorithm {
        /**
         * 
         * @param parameters
         * @param maxCombinationLimit
         *                -1 means no up limit
         * @return
         */
        public CombinationList generate(ParameterList parameters,
                                        int maxCombinationLimit);

}
