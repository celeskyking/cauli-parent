package org.cauli.pairwise;



import freemarker.template.TemplateException;
import org.cauli.pairwise.core.Parameter;
import org.cauli.pairwise.core.ParameterList;
import org.cauli.pairwise.core.ParameterValuePair;

import java.io.IOException;
import java.util.*;


public class AllPairList {
        private Pair[] availablePairs;
        private boolean[] pairUsed;
        private int pairCount;
        private Long[] parameterIds;
        private String[] parameterNames;
        private String[] parameterValues;
        private int[] parameterValuePositions;
        private Map<Integer, int[]> valueIndexMap;
        private int valueCount;

        /**
         *
         * @param params
         */
        public AllPairList(ParameterList params) {
                super();
                initParameterValues(params.getParameters());
                initAvailablePairs(params.getParameters());
        }

        public Pair[] getAvailablePairs() {
                return availablePairs;
        }

        public int getPairCount() {
                return pairCount;
        }

        public int getParameterCount() {
                return valueIndexMap.keySet().size();
        }

        public boolean isUsed(int pairIndex) {
                return pairUsed[pairIndex];
        }

        public boolean isUsed(int index1, int index2) {
                Pair newPair = new Pair(0, index1, index2);
                int pairIndex = 0;
                for (Pair pair : availablePairs) {
                        if (newPair.equals(pair)) {
                                return isUsed(pairIndex);
                        }
                        pairIndex++;
                }
                return false;
        }

        public void usePair(int pairIndex) {
                pairUsed[pairIndex] = true;
        }

        public void usePair(int index1, int index2) {
                Pair newPair = new Pair(0, index1, index2);

                int pairIndex = 0;
                for (Pair pair : availablePairs) {
                        if (newPair.equals(pair)) {
                                usePair(pairIndex);
                                return;
                        }
                        pairIndex++;
                }
        }

        public Pair nextUnusedPair() {
                for (int i = 0; i < pairCount; i++) {
                        if (!pairUsed[i]) {
                                return availablePairs[i];
                        }
                }
                return null;
        }

        public boolean[] copyPairUsed() {
                return Arrays.copyOf(pairUsed, pairCount);
        }

        public Long getParameterIdByValueIndex(int valueIndex) {
                return parameterIds[valueIndex];
        }

        public int getParameterIndexByValueIndex(int valueIndex) {
                return parameterValuePositions[valueIndex];
        }

        public ParameterValuePair getParameterValuePairByValueIndex(int index) throws IOException, TemplateException {
                if (index >= 0 && index < valueCount) {
                        return new ParameterValuePair(parameterIds[index],
                                        parameterNames[index],
                                        parameterValues[index]);
                }
                return null;
        }

        public Integer[] getPairIndicesByValueIndex(int valueIndex) {
                List<Integer> pairIndexes = new ArrayList<Integer>();
                for (Pair pair : availablePairs) {
                        if (pair.getParam1ValueIndex() == valueIndex
                                        || pair.getParam2ValueIndex() == valueIndex) {
                                pairIndexes.add(pair.getPairIndex());
                        }
                }
                return pairIndexes.toArray(new Integer[pairIndexes.size()]);
        }

        public int getValueCount() {
                return valueCount;
        }

        public int[] getValueIndicesByParameterIndex(int paramIndex) {
                if (valueIndexMap.containsKey(Integer.valueOf(paramIndex))) {
                        return valueIndexMap.get(Integer.valueOf(paramIndex));
                }
                return null;
        }

        public int getValueIndexByParameterValuePair(
                        ParameterValuePair parameterValuePair) {
                Long paramId = parameterValuePair.getParameterId();
                String paramValue = parameterValuePair.getParameterValue();
                for (int i = 0; i < valueCount; i++) {
                        if (parameterIds[i].equals(paramId)
                                        && parameterValues[i]
                                                        .equals(paramValue)) {
                                return i;
                        }
                }
                return -1;
        }

        private void initAvailablePairs(List<Parameter> parameters) {
                pairCount = 0;
                for (int i = 0; i < parameters.size() - 1; i++) {
                        for (int j = i + 1; j < parameters.size(); j++) {
                                pairCount += parameters.get(i).getValues()
                                                .size()
                                                * parameters.get(j).getValues()
                                                                .size();
                        }
                }

                pairUsed = new boolean[pairCount];
                Arrays.fill(pairUsed, false);

                availablePairs = new Pair[pairCount];
                int pairIndex = 0;
                for (int i = 0; i < parameters.size() - 1; i++) {
                        List<String> values1 = parameters.get(i).getValues();
                        int[] value1Indexs = valueIndexMap.get(Integer
                                        .valueOf(i));
                        for (int j = 0; j < values1.size(); j++) {
                                int index1 = value1Indexs[j];

                                for (int k = i + 1; k < parameters.size(); k++) {
                                        List<String> values2 = parameters
                                                        .get(k).getValues();
                                        int[] value2Indexs = valueIndexMap
                                                        .get(Integer.valueOf(k));

                                        for (int l = 0; l < values2.size(); l++) {
                                                int index2 = value2Indexs[l];
                                                availablePairs[pairIndex] = new Pair(
                                                                pairIndex,
                                                                index1, index2);
                                                pairIndex++;
                                        }
                                }
                        }

                }
        }

        private void initParameterValues(List<Parameter> parameters) {
                valueCount = 0;
                for (Parameter param : parameters) {
                        valueCount += param.getValues().size();
                }

                parameterIds = new Long[valueCount];
                parameterNames = new String[valueCount];
                parameterValues = new String[valueCount];
                parameterValuePositions = new int[valueCount];
                valueIndexMap = new HashMap<Integer, int[]>();

                int paramIndex = 0;
                int valueIndex = 0;
                for (Parameter param : parameters) {
                        Long id = param.getId();
                        String name = param.getName();
                        int[] indexs = new int[param.getValues().size()];
                        int index = 0;
                        for (String value : param.getValues()) {
                                parameterIds[valueIndex] = id;
                                parameterNames[valueIndex] = name;
                                parameterValues[valueIndex] = value;
                                parameterValuePositions[valueIndex] = paramIndex;
                                indexs[index++] = valueIndex;
                                valueIndex++;
                        }
                        valueIndexMap.put(paramIndex++, indexs);
                }
        }

}
