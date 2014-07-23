package org.cauli.pairwise;

public class Pair {
        private int param1ValueIndex;

        private int param2ValueIndex;

        private int pairIndex;

        /**
         * 
         * @param pairIndex
         * @param param1ValueIndex
         * @param param2ValueIndex
         */
        public Pair(int pairIndex, int param1ValueIndex, int param2ValueIndex) {
                super();
                this.pairIndex = pairIndex;
                if (param1ValueIndex < param2ValueIndex) {
                        this.param1ValueIndex = param1ValueIndex;
                        this.param2ValueIndex = param2ValueIndex;
                } else {
                        this.param1ValueIndex = param2ValueIndex;
                        this.param2ValueIndex = param1ValueIndex;
                }
        }

        public int getPairIndex() {
                return pairIndex;
        }

        public int getParam1ValueIndex() {
                return param1ValueIndex;
        }

        public int getParam2ValueIndex() {
                return param2ValueIndex;
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + pairIndex;
                result = prime * result + param1ValueIndex;
                result = prime * result + param2ValueIndex;
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
                Pair other = (Pair) obj;

                if (param1ValueIndex == other.param1ValueIndex
                                && param2ValueIndex == other.param2ValueIndex) {
                        return true;
                }

                return false;
        }

}
