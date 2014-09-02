package org.cauli.algorithm;

import java.util.LinkedList;
import java.util.Set;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/9/2
 */
public class GraphVisitor {

    public static Vertex[] topologicalSort(Graph graph){
        Set<Vertex> vertexSet = graph.getVertexes();
        if(vertexSet.size()<2){
            return vertexSet.toArray(new Vertex[0]);
        }
        LinkedList<Vertex> sortedList = new LinkedList<Vertex>();
        for(Vertex vertex:vertexSet){
            if(vertex.getStatus()==Status.NOTVisited){
                visitVertext(graph,vertex,sortedList);
            }
        }
        return sortedList.toArray(new Vertex[0]);
    }



    public static void visitVertext(Graph graph,Vertex vertex,LinkedList<Vertex> sortedList){
        vertex.setStatus(Status.Visiting);
        Map<Vertex,Vertex[]> edgeMap = graph.getAdjacents();
        Vertex[] adjacents = edgeMap.get(vertex);
        if(adjacents!=null&&adjacents.length>0){
            for(Vertex adjacent:adjacents){
                if(adjacent.getStatus()==Status.NOTVisited){
                    adjacent.setParent(vertex);
                    visitVertext(graph,adjacent,sortedList);
                }
            }
        }
        vertex.setStatus(Status.Visited);
        sortedList.addLast(vertex);
    }
}
