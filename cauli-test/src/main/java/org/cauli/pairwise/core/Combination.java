package org.cauli.pairwise.core;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Combination {
        /**
         * Map<CombinationValue.id, CombinationValue>
         */
        private final Map<Long, ParameterValuePair> map;

        public Combination(List<ParameterValuePair> combinationValues) {
                super();
                this.map = new TreeMap<Long, ParameterValuePair>();
                for (ParameterValuePair combinationValue : combinationValues) {
                        this.map.put(combinationValue.getParameterId(),
                                        combinationValue);
                }
        }

        public Combination() {
                super();
                this.map = new TreeMap<Long, ParameterValuePair>();
        }

        public Map<Long, ParameterValuePair> getMap() {
                return map;
        }

        public ParameterValuePair get(Long id) {
                if (this.map.containsKey(id)) {
                        return this.map.get(id);
                } else {
                        return null;
                }
        }

        @Override
        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                String delim = "";
                for (ParameterValuePair cbv : map.values()) {
                        sb.append(delim);
                        sb.append(cbv.toString());
                        delim = ",";
                }
                sb.append("]");
                return sb.toString();
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((map == null) ? 0 : map.hashCode());
                return result;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                Combination other = (Combination) obj;
                if (map == null) {
                        if (other.map != null)
                                return false;
                } else if (!map.equals(other.map))
                        return false;
                return true;
        }

}
