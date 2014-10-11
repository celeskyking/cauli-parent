package org.cauli.common.keyvalue;

/**
 * Created by tianqing.wang on 2014/9/22
 */
public class NormalSortComparator extends AbstractComparator{
    @Override
    public int compare(KeyValueStore o1, KeyValueStore o2) {
        return o1.getKey().compareTo(o2.getKey());
    }
}
