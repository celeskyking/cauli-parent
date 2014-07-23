package org.cauli.pairwise.core;

import java.util.List;

public class Parameter {
        private final Long id;
        private final String name;
        private final List<String> values;

        public Parameter(Long id, String name, List<String> values) {
                super();
                this.id = id;
                this.name = name;
                this.values = values;
        }

        public Long getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        public List<String> getValues() {
                return values;
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((id == null) ? 0 : id.hashCode());
                result = prime * result
                                + ((name == null) ? 0 : name.hashCode());
                result = prime * result
                                + ((values == null) ? 0 : values.hashCode());
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
                Parameter other = (Parameter) obj;
                if (id == null) {
                        if (other.id != null)
                                return false;
                } else if (!id.equals(other.id))
                        return false;
                if (name == null) {
                        if (other.name != null)
                                return false;
                } else if (!name.equals(other.name))
                        return false;
                if (values == null) {
                        if (other.values != null)
                                return false;
                } else if (!values.equals(other.values))
                        return false;
                return true;
        }

}
