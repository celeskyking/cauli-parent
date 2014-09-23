package org.cauli.mock.action;

import org.cauli.mock.entity.ActionInfo;

/**
 * Created by tianqing.wang on 2014/9/9
 */
public class DefaultSocketAction extends AbstractSocketAction{

    public DefaultSocketAction(String actionName) {
        super(actionName);
    }


    public DefaultSocketAction(){
        this("default");
    }

    @Override
    public void config(ActionInfo configuration) {

    }
}
