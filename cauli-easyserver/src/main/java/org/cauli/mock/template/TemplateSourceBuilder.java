package org.cauli.mock.template;

/**
 * Created by tianqing.wang on 2014/9/10
 */
public class TemplateSourceBuilder {

    private Class<? extends TemplateSourceEngine> templateSourceEngineClass= DefaultTemplateSourceEngine.class;

    private static TemplateSourceBuilder builder;

    private TemplateSourceBuilder(){}

    public void setTemplateSourceEngineClass(Class<? extends TemplateSourceEngine> clazz){
        this.templateSourceEngineClass=clazz;
    }

    public static TemplateSourceBuilder getInstance(){
        if(builder==null){
            synchronized (TemplateSourceBuilder.class){
                if(builder==null){
                    builder=new TemplateSourceBuilder();
                }
            }
        }
        return builder;
    }

    public Class<? extends TemplateSourceEngine> getTemplateSourceEngineClass(){
        return this.templateSourceEngineClass;
    }
}
