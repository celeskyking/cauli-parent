package org.cauli.pairwise.core;

public class ParameterValuePair {
        private final Long parameterId;
        private final String parameterName;
        private final String parameterValue;
        private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ParameterValuePair(String parameterName,String parameterValue) {
            this.parameterId=null;
            this.parameterName=parameterName;
            this.parameterValue= parameterValue;
        }

        public ParameterValuePair(Long parameterId, String parameterName,
                        String parameterValue) {
                this.parameterId = parameterId;
                this.parameterName = parameterName;
                this.parameterValue = parameterValue;
        }

        public Long getParameterId() {
                return parameterId;
        }

        public String getParameterName() {
                return parameterName;
        }

        public String getParameterValue() {
                return parameterValue;
        }

        @Override
        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("{id:");
                sb.append(getParameterId());
                sb.append(",name:");
                sb.append(getParameterName());
                sb.append(",value:");
                sb.append(getParameterValue());
                sb.append("}");
                return sb.toString();
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime
                                * result
                                + ((parameterId == null) ? 0 : parameterId
                                                .hashCode());
                result = prime
                                * result
                                + ((parameterName == null) ? 0 : parameterName
                                                .hashCode());
                result = prime
                                * result
                                + ((parameterValue == null) ? 0
                                                : parameterValue.hashCode());
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
                ParameterValuePair other = (ParameterValuePair) obj;
                if (parameterId == null) {
                        if (other.parameterId != null)
                                return false;
                } else if (!parameterId.equals(other.parameterId))
                        return false;
                if (parameterName == null) {
                        if (other.parameterName != null)
                                return false;
                } else if (!parameterName.equals(other.parameterName))
                        return false;
                if (parameterValue == null) {
                        if (other.parameterValue != null)
                                return false;
                } else if (!parameterValue.equals(other.parameterValue))
                        return false;
                return true;
        }

}
