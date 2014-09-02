package org.cauli.server.action;

import org.cauli.server.HttpMethod;
import org.cauli.server.controller.Controller;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by tianqing.wang on 2014/9/1
 */
public class Action {

    private String uriTemplate;

    private Method method;

    private Controller controller;

    private ActionBuilder actionBuilder;

    private List<HttpMethod>  httpMethods;


    public Action(Method method,Controller controller){
        this.method=method;
        setController(controller);
        this.actionBuilder=new ActionBuilder(this);
        this.uriTemplate=this.actionBuilder.getUriTemplate();
        this.httpMethods=this.actionBuilder.getMethods();
    }

    public void setUriTemplate(String uriTemplate) {
        this.uriTemplate = uriTemplate;
    }

    public Method getMethod() {
        return method;
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void invoke() throws Exception {
        method.invoke(controller);
    }


    public String getUriTemplate(){
        return this.uriTemplate;
    }

    public List<HttpMethod> methods(){
        return this.httpMethods;
    }


    public void setActionBuilder(ActionBuilder actionBuilder){
        this.actionBuilder=actionBuilder;
    }

    public ActionBuilder getActionBuilder(){
        return this.actionBuilder;
    }


}
