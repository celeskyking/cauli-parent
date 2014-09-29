package org.cauli.mock.action;

import org.cauli.mock.entity.ActionInfo;
import org.cauli.common.keyvalue.KeyValueStore;
import org.cauli.mock.entity.ParametersModel;
import org.cauli.mock.exception.ActionExecuteException;
import org.cauli.mock.server.MockServer;

import java.util.Map;

/**
 * Created by tianqing.wang on 2014/7/4
 */
public interface MockAction<T,V,K> {


    public void  onMessage(V pairs);

    /**
     * 这个方法是构建响应的方法
     * */
    public T build() throws Exception;

    public MockServer getServer();

    /**
     * 返回当前配置的ReturnStatus
     * */
    public String getReturnStatus();

    public String getCallbackReturnStatus();

    public String getActionName();

    public ActionInfo getActionInfo();

    public String getTemplateValue(String status);

    public String getCallbackTemplateValue(String status);

    public void addTemplate(String returnStatus,String content);

    public Map<String,String> getTemplateStatuses();

    /***这个方法是初始化的时候加载方法。*/
    public void load();

    public void addContext(String name,Object object);

    public void addContext(KeyValueStore store);

    public void updateTemplateValue(String returnStatus,String value);

    public K callback() throws ActionExecuteException;




}
