package org.cauli.mock.action;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.cauli.mock.context.Context;
import org.cauli.mock.entity.ActionInfo;
import org.cauli.mock.strategy.IStrategy;
import org.cauli.mock.util.TemplateParseUtil;
import org.cauli.mock.util.XMLUtil;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by tianqing.wang on 2014/8/14
 */
public abstract class AbstractHttpAction extends AbstractAction<HttpRequest>{

    private Logger logger = LoggerFactory.getLogger(AbstractHttpAction.class);

    private HttpResponse response;

    public AbstractHttpAction(String name) {
        super(name);
    }


    public abstract void config(ActionInfo httpActionInfo);


    public Map<String,String> requestMapWithSingleValue(){
        Map<String,String> requestMap = Maps.newHashMap();
        if(request().method().equalsIgnoreCase("GET")){
            for(String key:request().queryParamKeys()){
                requestMap.put(key,request().queryParam(key));
            }
        }else{
            for(String key:request().postParamKeys()){
                requestMap.put(key,request().postParam(key));
            }
        }
        return requestMap;
    }

    public Map<String,String[]> requestMap(){
        Map<String,String[]> requestMap = Maps.newHashMap();
        if(request().method().equalsIgnoreCase("GET")){
            for(String key:request().queryParamKeys()){
                String[] strings = new String[request().queryParamKeys().size()];
                requestMap.put(key,request().queryParams(key).toArray(strings));
            }
        }else{
            for(String key:request().postParamKeys()){
                String[] strings = new String[request().postParamKeys().size()];
                requestMap.put(key,request().postParams(key).toArray(strings));
            }
        }
        return requestMap;
    }






    @Override
    public String build() throws Exception {
        runParameterConfigs();
        if(getActionInfo().isUseTemplate()){
            logger.info("请求的模板状态为:{}",getReturnStatus());
            String content= TemplateParseUtil.getInstance().toString(getParametersModel().getContext().getValues(),getTemplateValue(getReturnStatus()));
            if(!getActionInfo().getTemplateEncoding().equalsIgnoreCase("utf-8")){
                content= IOUtils.toString(IOUtils.toInputStream(content,getActionInfo().getTemplateEncoding()));
            }
            logger.info("获取的Template的值:{}", content);
            setTemplateValue(content);
        }
        runTemplateConfig();
        logger.info("响应设置timeout时间:{}", getActionInfo().getTimeoutMS());
        Thread.sleep(getActionInfo().getTimeoutMS()==0?0:getActionInfo().getTimeoutMS());
        return getParametersModel().getTemplateValue();
    }



    public Document requestToXml() {
        String text = request().body();
        if (XMLUtil.isXML(text)) {
            return XMLUtil.load(request().body());
        } else {
            throw new RuntimeException("the body of request is not format for xml");
        }
    }

    public String xmlValue(String xpath){
        return XMLUtil.getStringValue(requestToXml(),xpath);
    }


    public HttpResponse response() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    @Override
    public String process(HttpRequest request, Context context) {
        return null;
    }



}
