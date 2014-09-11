package org.cauli.mock.action;

import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cauli.mock.ConfigType;
import org.cauli.mock.annotation.Action;
import org.cauli.mock.entity.ActionInfo;
import org.cauli.mock.entity.ParameterValuePairs;
import org.cauli.mock.exception.ActionExecuteException;
import org.cauli.mock.exception.ServerNameNotSupportChineseException;
import org.cauli.mock.server.AbstractSocketServer;
import org.cauli.mock.template.TemplateSourceEngine;
import org.cauli.mock.util.CommonUtil;
import org.cauli.mock.util.TemplateParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Created by tianqing.wang on 2014/8/29
 */
public abstract class AbstractSocketAction implements MockAction<String,ParameterValuePairs>{

    private Logger logger = LoggerFactory.getLogger(AbstractSocketServer.class);

    private TemplateSourceEngine sourceEngine;

    public AbstractSocketAction(String actionName){
        this.actionInfo.setActionName(actionName);
        parseMethods();
        config(actionInfo);
        if(CommonUtil.checkContainsChinese(actionInfo.getActionName())){
            throw new ServerNameNotSupportChineseException("ActionName不支持非英文:"+actionInfo.getActionName());
        }
        if(actionInfo.getTemplateSourceLoaderClass()!=null){
            this.sourceEngine=checkTemplateSourceEngineClass(actionInfo.getTemplateSourceLoaderClass());
        }
    }

    private AbstractSocketServer server;

    private String request;

    private ParameterValuePairs parameterValuePairs;

    private Set<Method> parameterConfigMethods= Sets.newHashSet();
    private Set<Method> templateConfigMethods=Sets.newHashSet();

    private ActionInfo actionInfo=new ActionInfo();

    public String getActionName(){
        return actionInfo.getActionName();
    }

    public ActionInfo getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public void runTemplateConfig() throws ActionExecuteException {
        for(Method method:this.templateConfigMethods){
            invokeConfigMethod(method);
        }
    }

    public void runParameterConfigs() throws ActionExecuteException {
        for(Method method:this.parameterConfigMethods){
            invokeConfigMethod(method);
        }

    }

    public Map<String,String> getTemplateStatuses(){
        return this.sourceEngine.getAllTemplates();
    }


    public String getServerName(){
        return server.getServerName();
    }


    public String request(){
        return this.request;
    }

    public void setRequest(String request){
        this.request=request;
    }

    @Override
    public void onMessage(ParameterValuePairs pairs) {

    }

    @Override
    public void loadTemplate() {
        this.sourceEngine.init();
    }

    @Override
    public String build() throws Exception {
        runParameterConfigs();
        if(actionInfo.isUseTemplate()){
            String content = templateValue();
            logger.info("获取的Template的值:{}",content);
            this.parameterValuePairs.setTemplateValue(content);
        }
        runTemplateConfig();
        logger.info("响应设置timeout时间:{}",actionInfo.getTimeoutMS());
        Thread.sleep(actionInfo.getTimeoutMS()==0?0:actionInfo.getTimeoutMS());
        return parameterValuePairs.getTemplateValue();
    }


    private String templateValue() throws Exception {
        String content= TemplateParseUtil.getInstance().toString(parameterValuePairs.getValuePairs().getValues(), getTemplateName(getReturnStatus()));
        if(!actionInfo.getTemplateEncoding().equalsIgnoreCase("utf-8")){
            content= IOUtils.toString(IOUtils.toInputStream(content,actionInfo.getTemplateEncoding()));
        }
        return content;
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

    @Override
    public String getTemplateValue(String status) {
        return this.sourceEngine.getTemplate(status);
    }

    public AbstractSocketServer getServer() {
        return server;
    }

    public void setServer(AbstractSocketServer server) {
        this.server = server;
    }

    public ParameterValuePairs getParameterValuePairs() {
        return parameterValuePairs;
    }

    public void setParameterValuePairs(ParameterValuePairs parameterValuePairs) {
        this.parameterValuePairs = parameterValuePairs;
    }

    public abstract void config(ActionInfo configuration);

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

    //-----------------------private help methods-------------------------//

    private void invokeConfigMethod(Method method) throws ActionExecuteException {
        if(method!=null){
            ActionExcuter actionExcuter = new ActionExcuter(method,parameterValuePairs,this);
            actionExcuter.invoke();
        }

    }

    private String getTemplateName(String status){
        return getServerName()+"_"+getActionName()+"_"+status;
    }

    public void addObject(String key,Object value){
        this.parameterValuePairs.getValuePairs().addObject(key,value);
    }

    public void setTemplateValue(String value){
        this.parameterValuePairs.setTemplateValue(value);
    }

    public String getTemplateValue(){
        return parameterValuePairs.getTemplateValue();
    }


    @Override
    public String getReturnStatus() {
        return actionInfo.getReturnStatus();
    }

    @Override
    public void addTemplate(String returnStatus, String content) {
        if(StringUtils.isNotEmpty(content)){
            TemplateParseUtil.getInstance().addTemplate(getServerName()+"-"+getActionName()+"_"+returnStatus,content);
        }
        this.sourceEngine.createTemplate(returnStatus,content);
    }
}
