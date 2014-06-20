package org.cauli.pairwise.algorithm;


import freemarker.template.TemplateException;
import org.cauli.pairwise.core.*;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


public class FullCombinationAlgorithm implements Algorithm {

        public CombinationList generate(ParameterList parameters,
                        int maxCombinationLimit) throws IOException, TemplateException {

                Deque<ParameterValuePair> stack = new ArrayDeque<ParameterValuePair>();
                List<Combination> resultList = new ArrayList<Combination>();
                if (maxCombinationLimit != 0) {
                        combine(parameters.getParameters(), 0, stack,
                                        resultList, maxCombinationLimit);
                }

                return new CombinationList(resultList);
        }

        /**
         * 
         * @param params
         * @param fromPosition
         * @param stack
         * @param resultList
         * @param maxCombinationLimit
         */
        private boolean combine(List<Parameter> params, int fromPosition,
                        Deque<ParameterValuePair> stack,
                        List<Combination> resultList, int maxCombinationLimit) throws IOException, TemplateException {

                if (fromPosition >= params.size()) {
                        return true;
                }

                Parameter dim = params.get(fromPosition);
                for (String value : dim.getValues()) {
                        stack.push(new ParameterValuePair(dim.getId(), dim
                                        .getName(), value));
                        if (!combine(params, fromPosition + 1, stack,
                                        resultList, maxCombinationLimit)) {
                                return false;
                        }
                        if (fromPosition == params.size() - 1) {
                                resultList.add(new Combination(
                                                new ArrayList<ParameterValuePair>(
                                                                stack)));
                                if (maxCombinationLimit > 0
                                                && resultList.size() >= maxCombinationLimit) {
                                        return false;
                                }
                        }
                        stack.pop();
                }

                return true;
        }
}
