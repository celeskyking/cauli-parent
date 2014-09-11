package org.cauli.mock.core;

/**
 * Created by tianqing.wang on 2014/8/7
 */
public class ValuePairsFactory {


    public static ValuePairs getValuePairs() {
        ValuePairs valuePairs = new ValuePairs();
        TemplateValueInit valueInit;
        try {
            valueInit = TemplateValueInit.getInstance();
            valuePairs.addTemplateMethodModelObjects(valueInit.getTemplateConstant());
            valuePairs.addObjects(valueInit.getTemplateObjects());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return valuePairs;
    }
}
