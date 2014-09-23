package org.cauli.mock.action;

import org.cauli.mock.entity.ActionInfo;
import org.cauli.mock.server.MockServer;

import java.util.Map;

/**
 * Created by tianqing.wang on 2014/7/4
 */
public interface MockAction<T,K> {


    public void  onMessage(K pairs);

    public T build() throws Exception;

    public MockServer getServer();

    public String getReturnStatus();

    public String getActionName();

    public ActionInfo getActionInfo();

    public String getTemplateValue(String status);

    public String getCallbackTemplateValue(String status);

    public void addTemplate(String returnStatus,String content);

    public Map<String,String> getTemplateStatuses();

    public void load();




}
