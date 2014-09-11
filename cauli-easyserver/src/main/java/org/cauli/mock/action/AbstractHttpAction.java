package org.cauli.mock.action;

import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.cauli.mock.ConfigType;
import org.cauli.mock.annotation.Action;
import org.cauli.mock.entity.ActionInfo;
import org.cauli.mock.entity.ParameterValuePairs;
import org.cauli.mock.exception.ActionExecuteException;
import org.cauli.mock.exception.ServerNameNotSupportChineseException;
import org.cauli.mock.server.MockServer;
import org.cauli.mock.template.TemplateSourceEngine;
import org.cauli.mock.util.CommonUtil;
import org.cauli.mock.util.TemplateParseUtil;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Created by tianqing.wang on 2014/8/14
 */
public abstract class AbstractHttpAction implements MockAction<String,ParameterValuePairs>{

    private Logger logger = LoggerFactory.getLogger(AbstractHttpAction.class);

    private ActionInfo actionInfo=new ActionInfo();

    private HttpRequest request;

    private HttpResponse response;

    public TemplateSourceEngine sourceEngine;

    public String getActionName() {
        return actionInfo.getActionName();
    }

    public String getRequestUri(){
        return actionInfo.getRequestUri();
    }

    public String getServerName(){
        return this.server.getServerName();
    }

    public String getTemplateEncoding(){
        return actionInfo.getTemplateEncoding();
    }

    public Map<String,String> getTemplateStatuses() {
        return this.sourceEngine.getAllTemplates();
    }


    public String getReturnStatus(){
        return actionInfo.getReturnStatus();
    }

    public long getTimeoutMS(){
        return actionInfo.getTimeoutMS();
    }

    public void setRequestUri(String requestUri){
        this.actionInfo.setRequestUri(requestUri);
    }

    @Override
    public String getTemplateValue(String status) {
        return this.sourceEngine.getTemplate(status);
    }

    private Set<Method> parameterConfigMethods= Sets.newHashSet();
    private Set<Method> templateConfigMethods=Sets.newHashSet();
    private MockServer server;

    private ParameterValuePairs parameterValuePairs;

    public ParameterValuePairs getParameterValuePairs() {
        return parameterValuePairs;
    }

    public void setParameterValuePairs(ParameterValuePairs parameterValuePairs) {
        this.parameterValuePairs = parameterValuePairs;
    }

    @Override
    public void loadTemplate() {
        this.sourceEngine.init();
    }

    public AbstractHttpAction(String actionName){
        actionInfo.setActionName(actionName);
        parseMethods();
        config(actionInfo);
        if(CommonUtil.checkContainsChinese(actionInfo.getActionName())){
            throw new ServerNameNotSupportChineseException("ActionName不支持非英文:"+actionInfo.getActionName());
        }
        if(actionInfo.getTemplateSourceLoaderClass()!=null){
            this.sourceEngine=checkTemplateSourceEngineClass(actionInfo.getTemplateSourceLoaderClass());
        }

    }


    public abstract void config(ActionInfo httpActionInfo);


    public void runParameterConfigs() throws ActionExecuteException {
        for(Method method:this.parameterConfigMethods){
            invokeConfigMethod(method);
        }

    }



    public void addObject(String name,Object object){
        this.parameterValuePairs.getValuePairs().addObject(name,object);
    }

    public String getTemplateValue(){
        return this.parameterValuePairs.getTemplateValue();
    }

    public void setTemplateValue(String content){
        this.parameterValuePairs.setTemplateValue(content);
    }



    public void runTemplateConfig() throws ActionExecuteException {
        for(Method method:this.templateConfigMethods){
            invokeConfigMethod(method);
        }
    }

    @Override
    public String build() throws Exception {
        runParameterConfigs();
        if(actionInfo.isUseTemplate()){
            String content= TemplateParseUtil.getInstance().toString(parameterValuePairs.getValuePairs().getValues(), getTemplateName(getReturnStatus()));
            if(!actionInfo.getTemplateEncoding().equalsIgnoreCase("utf-8")){
                content= IOUtils.toString(IOUtils.toInputStream(content,actionInfo.getTemplateEncoding()));
            }
            logger.info("获取的Template的值:{}", content);
            setTemplateValue(content);
        }
        runTemplateConfig();
        logger.info("响应设置timeout时间:{}",actionInfo.getTimeoutMS());
        Thread.sleep(actionInfo.getTimeoutMS()==0?0:actionInfo.getTimeoutMS());
        return parameterValuePairs.getTemplateValue();
    }

    public ActionInfo getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public MockServer getServer() {
        return server;
    }

    public void setServer(MockServer server) {
        this.server = server;
    }

    //--------------------------help method-----------------------

    private void parseMethods(){
        Method[] methods = getClass().getDeclaredMethods();
        for(Method method:methods){
            if(method.isAnnotationPresent(Action.class)){
                Action action = method.getAnnotation(Action.class);
                if(action.value()== ConfigType.PARAMETERS){
                    this.parameterConfigMethods.add(method);
                }else if(action.value()==ConfigType.TEMPLATE){
                    this.templateConfigMethods.add(method);
                }
            }
        }
    }

    private TemplateSourceEngine checkTemplateSourceEngineClass(Class<? extends TemplateSourceEngine> clazz){
        try {
            Constructor constructor=clazz.getConstructor(MockAction.class);
            return (TemplateSourceEngine) constructor.newInstance(this);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("加载模板运行器的时候出现了错误,构造方法只能够有一个参数MockAction.class,class:"+clazz.getName(),e);
        } catch (Exception e) {
            throw new RuntimeException("构造模板运行期的时候出现了系统异常",e);
        }
    }




    private void invokeConfigMethod(Method method) throws ActionExecuteException {
        if(method!=null){
            ActionExcuter actionExcuter = new ActionExcuter(method,parameterValuePairs,this);
            actionExcuter.invoke();
        }

    }

    private String getTemplateName(String status){
        return getServerName()+"_"+getActionName()+"_"+status;
    }


    @Override
    public void onMessage(ParameterValuePairs pairs) {
        //do nothing
    }


    public HttpRequest request() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public HttpResponse response() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }


    @Override
    public void addTemplate(String returnStatus, String content) {
        TemplateParseUtil.getInstance().addTemplate(getTemplateName(returnStatus),content);
        this.sourceEngine.createTemplate(returnStatus,content);
    }



}
