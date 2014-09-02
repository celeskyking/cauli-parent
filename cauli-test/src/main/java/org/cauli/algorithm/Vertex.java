package org.cauli.algorithm;

/**
 * Created by tianqing.wang on 2014/8/7
 */
public class Vertex {

    private String name;
    private Vertex parent;
    private Status status;

    public Vertex(String name){
        this.name=name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
