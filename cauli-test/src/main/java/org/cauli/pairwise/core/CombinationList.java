package org.cauli.pairwise.core;

import java.util.List;

public class CombinationList {
        private final List<Combination> combinations;

        public CombinationList(List<Combination> combinations) {
                super();
                this.combinations = combinations;
        }

        public List<Combination> getCombinations() {
                return combinations;
        }

        @Override
        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                String delim = "";
                for (Combination cb : combinations) {
                        sb.append(delim);
                        sb.append(cb.toString());
                        delim = ",";
                }
                sb.append("]");
                return sb.toString();
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime
                                * result
                                + ((combinations == null) ? 0 : combinations
                                                .hashCode());
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
                CombinationList other = (CombinationList) obj;
                if (combinations == null) {
                        if (other.combinations != null)
                                return false;
                } else if (!combinations.equals(other.combinations))
                        return false;
                return true;
        }

}
