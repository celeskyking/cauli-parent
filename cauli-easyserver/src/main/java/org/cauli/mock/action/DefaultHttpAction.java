package org.cauli.mock.action;

import org.cauli.mock.entity.ActionInfo;

/**
 * Created by tianqing.wang on 2014/9/9
 */
public class DefaultHttpAction extends AbstractHttpAction{

    public DefaultHttpAction(){
        this("default");
    }
    public DefaultHttpAction(String actionName) {
        super(actionName);
    }

    @Override
    public void config(ActionInfo httpActionInfo) {
    }

}
