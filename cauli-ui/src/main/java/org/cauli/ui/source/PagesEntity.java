package org.cauli.ui.source;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by tianqing.wang on 2014/7/25
 */
@XStreamAlias("pages")
public class PagesEntity {

    @XStreamImplicit
    List<PageEntity> pages;


    public List<PageEntity> getPages() {
        return pages;
    }

    public void setPages(List<PageEntity> pages) {
        this.pages = pages;
    }
}
