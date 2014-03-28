package org.cauli.ui.selenium.element;

/**
 * 
 */
public class TempElement {
    private String id;
    private String by;
    public TempElement(ElementInfo elementInfo){
        this.id=elementInfo.getId();
        this.by=elementInfo.getBy();
    }
    public TempElement(String id,String by){
        this.id=id;
        this.by=by;
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
}
