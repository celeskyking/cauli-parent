package org.cauli.mock.callback;

import org.cauli.mock.context.Context;

/**
 * Created by tianqing.wang on 2014/9/19
 */
public abstract class ContextCallback implements ICallback{

    private Context context;


    private ContextCallback(Context context){
        this.context=context;
    }

    @Override
    public String build() {
        return null;
    }

    @Override
    public Response send() {
        return null;
    }
}
