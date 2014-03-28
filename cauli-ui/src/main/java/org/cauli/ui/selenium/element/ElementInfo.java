package org.cauli.ui.selenium.element;

/**
 * @author tianqing.wang
 */
public class ElementInfo {
    //private Logger logger = Logger.getLogger(ElementInfo.class);
    private String id;
    private String xpath;
    private String by;
    public ElementInfo(String id, String xpath,String by) {
        this.id = id;
        this.by = by;
        this.xpath=xpath;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }



    public TempElement ToTempElement(){
        return null;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }
}
