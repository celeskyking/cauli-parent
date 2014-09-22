package org.cauli.mock.context;

/**
 * Created by tianqing.wang on 2014/8/7
 */
public class ContextFactory {


    public static Context getContext() {
        Context context = new Context();
        TemplateValueInit valueInit;
        try {
            valueInit = TemplateValueInit.getInstance();
            context.addTemplateMethodModelObjects(valueInit.getTemplateConstant());
            context.addContext(valueInit.getTemplateObjects());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }
}
