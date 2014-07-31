package org.cauli.ui.selenium.page;

import org.cauli.ui.FrameLocateType;
import org.cauli.ui.annotation.FindFrame;
import org.cauli.ui.selenium.browser.IBrowser;

/**
 * Created by celeskyking on 14-3-2
 */
public class Frame<T extends ICurrentPage> extends SourcePage{

    private String locate;

    public Frame(IBrowser browser,T parent) throws Exception {
        super(browser);
        this.parent=parent;
        init();
    }

    public Frame(IBrowser browser,T parent,int index) throws Exception {
        super(browser);
        this.parent=parent;
        this.index=index;
        initIndexLocate();
    }

    public Frame(IBrowser browser,T parent,String locate) throws Exception {
        super(browser);
        this.parent=parent;
        this.locate=locate;
        initIndexLocate();
    }


    private void initIndexLocate(){
        this.getBrowser().selectFrame(index);
    }

    private void initLocate(){
        this.getBrowser().selectFrame(locate);
    }

    private int index;

    private T parent;


    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void toDefautlContent(){
        getBrowser().getCurrentBrowserDriver().switchTo().defaultContent();
    }


    private void init(){
        FindFrame findFrame = getClass().getAnnotation(FindFrame.class);
        if(findFrame!=null){
            FrameLocateType frameLocateType=findFrame.type();
            if(frameLocateType==FrameLocateType.INDEX){
                index=Integer.parseInt(findFrame.value());
                this.parent.getBrowser().selectFrame(index);
            }else if(frameLocateType==FrameLocateType.LOCATE){
                locate=findFrame.value();
                this.parent.getBrowser().selectFrame(locate);
            }
        }
    }


    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }


}
