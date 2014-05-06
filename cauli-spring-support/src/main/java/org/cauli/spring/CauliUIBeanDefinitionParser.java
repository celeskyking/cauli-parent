package org.cauli.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * Created by tianqing.wang on 2014/5/4
 */
public class CauliUIBeanDefinitionParser extends AbstractSingleBeanDefinitionParser{
    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        super.doParse(element, builder);
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return super.getBeanClass(element);
    }
}
