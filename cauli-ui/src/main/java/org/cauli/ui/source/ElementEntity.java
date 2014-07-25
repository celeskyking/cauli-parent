package org.cauli.ui.source;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by tianqing.wang on 2014/7/25
 */
public class ElementEntity {

    @XStreamAsAttribute
    @XStreamAlias("locate")
    private String locate;
    @XStreamAsAttribute
    @XStreamAlias("id")
    private String id;

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
