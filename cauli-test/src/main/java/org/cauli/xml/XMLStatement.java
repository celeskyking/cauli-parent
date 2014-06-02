package org.cauli.xml;

import org.dom4j.Element;
import org.junit.runners.model.Statement;

/**
 * Created by celeskyking on 2014/5/8
 */
public abstract class XMLStatement extends Statement{
    private Element element;
    public XMLStatement(Element element){
        this.element=element;
    }
    public abstract void evaluate() throws Throwable;
}
