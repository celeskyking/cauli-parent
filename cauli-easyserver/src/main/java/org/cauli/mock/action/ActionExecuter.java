package org.cauli.mock.action;


import org.cauli.mock.entity.ParametersModel;
import org.cauli.mock.exception.ActionExecuteException;
import org.cauli.mock.util.ActionMethodUtil;
import org.cauli.mock.util.CommonUtil;

import java.lang.reflect.Method;

/**
 * @auther sky
 */
public class ActionExecuter{

    private Object object;

    private Method method;

    private Object[] params;

    public ActionExecuter(Method method,ParametersModel pairs,Object object){
        this.method=method;
        this.method.setAccessible(true);
        this.parameterValuePairs=pairs;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    private ParametersModel parameterValuePairs;

    public ParametersModel getParameterValuePairs() {
        return parameterValuePairs;
    }



    public void setParameterValuePairs(ParametersModel parameterValuePairs) {
        this.parameterValuePairs = parameterValuePairs;
    }

    private Object[] initParams(){
        int length = CommonUtil.getMethodParameterCount(method);
        Object[] objects;
        if(length>0){
            objects = ActionMethodUtil.parse(method, parameterValuePairs);
        }else{
            objects=null;
        }

        return objects;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }


    public Object invoke() throws ActionExecuteException {
        this.params=initParams();
        try{
            if(method.getReturnType()!=Void.TYPE){
                if(this.params==null){
                    return this.method.invoke(object);
                }else{
                    return this.method.invoke(object,params);
                }
            }else{
                if(this.params==null){
                    this.method.invoke(object);
                }else{
                    this.method.invoke(object,params);
                }
            }
        }catch (Exception e){
            throw new ActionExecuteException(method.getName()+" 执行的时候出现了错误...",e);
        }
        return null;
    }
}
