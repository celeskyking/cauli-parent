package org.cauli.mock.entity;


import org.cauli.mock.context.Context;
import org.cauli.mock.context.ContextFactory;
import org.cauli.mock.core.convert.ConvertExecuter;
import org.cauli.mock.core.convert.ConvertManager;

/**
 * Created by tianqing.wang on 2014/7/14
 */
public class ParametersModel {
    private String templateValue;
    private Context context= ContextFactory.getContext();
    private ConvertManager.ConvertMap convertMap;

    public ParametersModel(ConvertManager.ConvertMap convertMap){
        setConvertMap(convertMap);
        convertMap.register(Context.class,new ConvertExecuter<Class<Context>,Context>() {
            @Override
            public Context execute(Class<Context> clazz, ParametersModel parameterValuePairs) {
                return parameterValuePairs.getContext();
            }
        });
    }


    public ConvertManager.ConvertMap getConvertMap() {
        return convertMap;
    }

    public void setConvertMap(ConvertManager.ConvertMap convertMap) {
        this.convertMap = convertMap;
    }

    public String getTemplateValue() {
        return templateValue;
    }

    public void setTemplateValue(String templateValue) {
        this.templateValue = templateValue;
    }

    public Context getContext() {
        return context;
    }

}
