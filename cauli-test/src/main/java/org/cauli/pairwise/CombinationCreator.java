package org.cauli.pairwise;



import org.cauli.pairwise.core.Combination;
import org.cauli.pairwise.core.ParameterValuePair;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;


public class CombinationCreator {
        private AllPairList allPairList;
        private boolean[] pairUsed;
        private Combination candidate;
        private Random random;

        public CombinationCreator(AllPairList pairs) {
                super();
                random = new Random();
                this.allPairList = pairs;
        }

        public Combination create() {
                Pair newPair = this.allPairList.nextUnusedPair();
                if (newPair == null) {
                        return null;
                }
                this.pairUsed = this.allPairList.copyPairUsed();
                int[] order = shuffleParameterOrders(this.allPairList.getParameterCount(), newPair);
                findCandidateCombinationByOrder(order, newPair);
                updatePairUsage();
                return candidate;
        }

        private void useParameterValuePair(int pairIndex) {
                Pair newPair = this.allPairList.getAvailablePairs()[pairIndex];
                Long param1Id = this.allPairList.getParameterIdByValueIndex(newPair .getParam1ValueIndex());
                if (!candidate.getMap().containsKey(param1Id)) {
                        candidate.getMap().put(param1Id,this.allPairList.getParameterValuePairByValueIndex(newPair.getParam1ValueIndex()));
                }

                Long param2Id = this.allPairList.getParameterIdByValueIndex(newPair.getParam2ValueIndex());
                if (!candidate.getMap().containsKey(param2Id)) {
                        candidate.getMap().put(param2Id,this.allPairList.getParameterValuePairByValueIndex(newPair.getParam2ValueIndex()));
                }

                this.pairUsed[pairIndex] = true;
        }

        private int findBestValueIndex(int[] candidateValueIndices) {
                int bestValueIndex = -1;
                int count = 0;
                int bestCount = -1;
                for (int i = 0; i < candidateValueIndices.length; i++) {
                        count = 0;
                        Integer[] pairIndices = this.allPairList
                                        .getPairIndicesByValueIndex(candidateValueIndices[i]);
                        for (Integer index : pairIndices) {
                                if (!this.pairUsed[index]) {
                                        count++;
                                }
                        }
                        if (count > bestCount) {
                                bestValueIndex = candidateValueIndices[i];
                                bestCount = count;
                        }
                }
                return bestValueIndex;
        }

        private void findCandidateCombinationByOrder(int[] order, Pair fixedPair) {
                this.candidate = new Combination();
                useParameterValuePair(fixedPair.getPairIndex());

                for (int i = 2; i < order.length; i++) {
                        int paramIndex = order[i];
                        int[] valueIndices = this.allPairList
                                        .getValueIndicesByParameterIndex(paramIndex);
                        int bestValueIndex = findBestValueIndex(valueIndices);
                        ParameterValuePair newParameterValuePair = this.allPairList.getParameterValuePairByValueIndex(bestValueIndex);
                        this.candidate.getMap().put(
                                        newParameterValuePair.getParameterId(),
                                        newParameterValuePair);

                        Integer[] pairIndices = this.allPairList
                                        .getPairIndicesByValueIndex(bestValueIndex);

                        for (Iterator<Map.Entry<Long, ParameterValuePair>> iter = this.candidate
                                        .getMap().entrySet().iterator(); iter
                                        .hasNext();) {
                                int valueIndex1 = this.allPairList.getValueIndexByParameterValuePair(iter.next().getValue());
                                Pair newPair = new Pair(0, valueIndex1,
                                                bestValueIndex);
                                for (int j = 0; j < pairIndices.length; j++) {
                                        Pair candidatePair = this.allPairList
                                                        .getAvailablePairs()[pairIndices[j]];
                                        if (candidatePair.equals(newPair)) {
                                                useParameterValuePair(candidatePair
                                                                .getPairIndex());
                                                break;
                                        }
                                }
                        }
                }
        }

        private void updatePairUsage() {
                for (int i = 0; i < pairUsed.length; i++) {
                        if (pairUsed[i]) {
                                this.allPairList.usePair(i);
                        }
                }
        }

        private void swap(int[] list, int pos1, int pos2) {
                int temp = list[pos1];
                list[pos1] = list[pos2];
                list[pos2] = temp;
        }

        private int[] shuffleParameterOrders(int length, Pair fixedPair) {
                int[] order = new int[length];
                int param1Index = allPairList
                                .getParameterIndexByValueIndex(fixedPair
                                                .getParam1ValueIndex());
                int param2Index = allPairList
                                .getParameterIndexByValueIndex(fixedPair
                                                .getParam2ValueIndex());

                order[0] = param1Index;
                order[1] = param2Index;
                int index = 2;
                for (int i = 0; i < length; i++) {
                        if (i != order[0] && i != order[1]) {
                                order[index++] = i;
                        }
                }
                // Knuth Shuffle
                for (int i = 2; i < length; i++) {
                        int j = i + random.nextInt(length - i);
                        if (i != j) {
                                swap(order, i, j);
                        }
                }

                return order;
        }
}
