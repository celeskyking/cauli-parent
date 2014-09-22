package org.cauli.mock.callback;


import org.cauli.mock.action.MockAction;
import org.cauli.mock.response.Response;
import org.cauli.mock.sender.ISender;

/**
 * Created by tianqing.wang on 2014/9/19
 */
public interface ICallback<T extends MockAction> {


    public String getName();

    public String build();

    public Response send();

    public ISender getSender();

    public T getMockAciton();



}
