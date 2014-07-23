package org.cauli.pairwise.core;

import java.util.List;

public class ParameterList {

        private final List<Parameter> parameters;
        private final int size;

        public ParameterList(List<Parameter> params) {
                super();
                this.parameters = params;
                int size = 1;
                for (Parameter dimension : params) {
                        size = size * dimension.getValues().size();
                }
                this.size = size;

        }

        public List<Parameter> getParameters() {
                return parameters;
        }

        public int getSize() {
                return size;
        }

        /**
         * 
         * @param dimensionIndex
         * @param dimensionValueIndex
         * @return
         */
        public String getValue(int dimensionIndex, int dimensionValueIndex) {
                return parameters.get(dimensionIndex).getValues()
                                .get(dimensionValueIndex);
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime
                                * result
                                + ((parameters == null) ? 0 : parameters
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
                ParameterList other = (ParameterList) obj;
                if (parameters == null) {
                        if (other.parameters != null)
                                return false;
                } else if (!parameters.equals(other.parameters))
                        return false;
                return true;
        }

}
