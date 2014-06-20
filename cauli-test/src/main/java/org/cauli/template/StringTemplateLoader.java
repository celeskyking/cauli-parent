package org.cauli.template;

import freemarker.cache.TemplateLoader;
import jodd.util.StringUtil;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/20
 */
public class StringTemplateLoader  implements TemplateLoader{

    private static final String DEFAULT_TEMPLATE_KEY = "_default_template_key";
    private Map templates = new HashMap();

    public StringTemplateLoader(String defaultTemplate) {
        if (defaultTemplate != null && !defaultTemplate.equals("")) {
            templates.put(DEFAULT_TEMPLATE_KEY, defaultTemplate);
        }
    }

    @Override
    public Object findTemplateSource(String name) throws IOException {
        if (name == null || name.equals("")) {
            name = DEFAULT_TEMPLATE_KEY;
        }
        return templates.get(name);
    }

    @Override
    public long getLastModified(Object templateSource) {
        return 0;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return new StringReader((String) templateSource);
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
    }


    public void addTemplate(String name,String template){
        if (StringUtil.isEmpty(name)||StringUtil.isEmpty(template)) {
            return;
        }
        if (!templates.containsKey(name)) {
            templates.put(name, template);
        }
    }
}
