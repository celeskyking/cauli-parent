package org.cauli.mock.action;

import org.cauli.mock.entity.ActionInfo;
import org.cauli.mock.entity.KeyValueStore;
import org.cauli.mock.server.MockServer;

import java.util.Map;

/**
 * Created by tianqing.wang on 2014/7/4
 */
public interface MockAction<T,K> {


    public void  onMessage(K pairs);

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




}
