package org.cauli.server.handler;

import org.cauli.server.*;
import org.cauli.server.action.Action;
import org.cauli.server.controller.Controller;
import org.cauli.server.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;

/**
 * Created by tianqing.wang on 2014/9/1
 */
public class CauliHandler implements HttpHandler{

    private Logger logger = LoggerFactory.getLogger(CauliHandler.class);

    private Router router;

    private HttpHandler before;

    private HttpHandler after;


    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        String uri = URI.create(request.uri()).getPath();
        logger.debug("请求的URI:{}",uri);
        if(before!=null){
            this.before.handleHttpRequest(request,response,control);
        }
        try{
            Controller controller = router.matchController(uri);
            if(controller==null){
                response.status(404).end();
                return;
            }
            logger.debug("映射的Controller:{}",controller.getClass().getName());
            controller.setRequest(request);
            controller.setResponse(response);
            controller.parseAction();
            Action action = controller.getMatchAction(uri);
            if(action==null){
                logger.info("未发现可映射的uri：{}",uri);
                response.status(404).end();
                return;
            }else{
                logger.debug("映射的Action:{}",action.getMethod().getName());
            }
            if(action.methods().contains(HttpMethod.valueOf(request.method().toUpperCase()))){
                controller.before();
                action.invoke();
                controller.after();
            }else{
                logger.info("未找到对应匹配HTTP请求方法{}的Action",request.method());
                response.status(404).end();
                return;
            }
        }catch (Exception e){
            logger.error("程序发生未知错误",e);
            response.status(500).content("程序发生了错误,请查看日志检查").end();
            return;
        }

        if(after!=null){
            this.after.handleHttpRequest(request,response,control);
        }
    }


    public void before(HttpHandler httpHandler){
        this.before=httpHandler;
    }

    public void after(HttpHandler httpHandler){
        this.after=httpHandler;
    }

    public void configRoute(Router router){
        this.router=router;
    }
}
