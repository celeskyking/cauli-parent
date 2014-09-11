package org.cauli.mock.entity;

import org.cauli.mock.constant.Constant;
import org.cauli.mock.template.TemplateSourceBuilder;
import org.cauli.mock.template.TemplateSourceEngine;

import java.io.Serializable;

/**
 * Created by tianqing.wang on 2014/8/14
 */
public class ActionInfo implements Serializable{

    private String templateEncoding= Constant.DEFAULT_TEMPLATE_ENCODING;
    /**支付操作类型，比如:支付，查询*/
    private String actionName;
    private String returnStatus=Constant.DEFAULT_RETURN_STATUS;
    private long timeoutMS;
    private boolean isUseTemplate=Constant.IS_USE_TEMPLATE;
    private String requestUri;

    private Class<? extends TemplateSourceEngine> templateSourceLoaderClass= TemplateSourceBuilder.getInstance().getTemplateSourceEngineClass();

    public String getTemplateEncoding() {
        return templateEncoding;
    }

    public void setTemplateEncoding(String templateEncoding) {
        this.templateEncoding = templateEncoding;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public long getTimeoutMS() {
        return timeoutMS;
    }

    public void setTimeoutMS(long timeoutMS) {
        this.timeoutMS = timeoutMS;
    }


    public boolean isUseTemplate() {
        return isUseTemplate;
    }

    public void setUseTemplate(boolean isUseTemplate) {
        this.isUseTemplate = isUseTemplate;
    }

    public Class<? extends TemplateSourceEngine> getTemplateSourceLoaderClass() {
        return templateSourceLoaderClass;
    }

    public void setTemplateSourceLoaderClass(Class<? extends TemplateSourceEngine> templateSourceLoaderClass) {
        this.templateSourceLoaderClass = templateSourceLoaderClass;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }
}
