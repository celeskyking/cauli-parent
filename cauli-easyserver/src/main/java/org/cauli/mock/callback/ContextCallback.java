package org.cauli.mock.callback;

import org.cauli.mock.constant.Constant;
import org.cauli.mock.context.Context;
import org.cauli.mock.data.DataProviderBuilder;
import org.cauli.mock.data.IDataProvider;
import org.cauli.mock.entity.KeyValueStores;
import org.cauli.mock.response.Response;

/**
 * Created by tianqing.wang on 2014/9/19
 */
public abstract class ContextCallback implements ICallback{


    private Context context;

    private String returnStatus = Constant.DEFAULT_RETURN_STATUS;

    private IDataProvider dataProvider;


    private ContextCallback(Context context){
        this.context=context;
        dataProvider= DataProviderBuilder.getInstance().getDataProvider();
    }

    private KeyValueStores load(){
        return dataProvider.loadDatas(returnStatus);
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
