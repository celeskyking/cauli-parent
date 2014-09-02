package org.cauli.algorithm;


import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/8/7
 */
public class Graph<T extends Vertex> {

    private Set<T> vertexes = Sets.newHashSet();

    private Map<T,T[]> adjacents = new HashMap<T, T[]>();

    public Set<T> getVertexes() {
        return vertexes;
    }

    public void setVertexes(Set<T> vertexes) {
        this.vertexes = vertexes;
    }

    public Map<T, T[]> getAdjacents() {
        return adjacents;
    }

    public void setAdjacents(Map<T, T[]> adjacents) {
        this.adjacents = adjacents;
    }
}
