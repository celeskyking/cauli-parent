package org.cauli.unit;

import com.google.common.collect.Maps;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.*;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/8/4
 */
public class TemplateTest {

    @Test
    public void testTemplate() throws IOException, TemplateException {
        Map<String,Object> map= Maps.newHashMap();
        String text = "${hello(\"yes\")}";
        map.put("age.name","hello");
        map.put("sex.name","world");
        map.put("hello",new TemplateMethodModelEx() {
            @Override
            public Object exec(List arguments) throws TemplateModelException {
                return "hello, "+arguments.get(0);
            }
        });
        map.put("obj",new TemplateTest());
        Configuration configuration = new Configuration();
        configuration.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("default",text);
        configuration.setTemplateLoader(stringTemplateLoader);
        configuration.setDefaultEncoding("UTF-8");
        Template template = configuration.getTemplate("default");
        StringWriter stringWriter = new StringWriter();
        template.process(map,stringWriter);
        System.out.println(stringWriter.toString());

    }


    public void hello(String age,String sex){
        System.out.println("age:"+age+", sex:"+sex);
    }
}
