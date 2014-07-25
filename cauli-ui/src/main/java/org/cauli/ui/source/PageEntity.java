package org.cauli.ui.source;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by tianqing.wang on 2014/7/25
 */

public class PageEntity {

    @XStreamAsAttribute()
    @XStreamAlias("name")
    private String name;

    @XStreamImplicit
    List<ElementEntity> elements= Lists.newArrayList();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ElementEntity> getElements() {
        return elements;
    }

    public void setElements(List<ElementEntity> elements) {
        this.elements = elements;
    }
}
