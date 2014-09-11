package org.cauli.mock.entity;

import org.cauli.mock.core.ValuePairs;
import org.cauli.mock.core.ValuePairsFactory;
import org.cauli.mock.core.convert.ConvertExecuter;
import org.cauli.mock.core.convert.ConvertManager;

/**
 * Created by tianqing.wang on 2014/7/14
 */
public class ParameterValuePairs {
    private String templateValue;
    private ValuePairs valuePairs= ValuePairsFactory.getValuePairs();
    private ConvertManager.ConvertMap convertMap;

    public ParameterValuePairs(ConvertManager.ConvertMap convertMap){
        setConvertMap(convertMap);
        convertMap.register(ValuePairs.class,new ConvertExecuter<Class<ValuePairs>,ValuePairs>() {
            @Override
            public ValuePairs execute(Class<ValuePairs> clazz, ParameterValuePairs parameterValuePairs) {
                return parameterValuePairs.getValuePairs();
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

    public ValuePairs getValuePairs() {
        return valuePairs;
    }

}
