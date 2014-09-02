package org.cauli.server;

import org.apache.commons.lang3.StringUtils;
import org.cauli.server.controller.FreeMarkerRender;
import org.cauli.server.handler.CauliHandler;
import org.cauli.server.handler.StaticFileHandler;
import org.cauli.server.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;


/**
 * Created by tianqing.wang on 2014/9/1
 */
public abstract class CauliServer{

    private Logger logger = LoggerFactory.getLogger(CauliServer.class);

    WebServer server;

    Configuration configuration=new Configuration();

    CauliHandler cauliHandler =new CauliHandler();

    Router router=new Router();


    public abstract void configRoute(Router router);

    public abstract void configServer(Configuration configuration);

    public CauliServer() {
        init();
    }

    public void start() throws Exception {
        if(configuration.getPort()==-1){
            throw new RuntimeException("端口号不能够为空");
        }else if(configuration.getServerName()==null||configuration.getServerName().isEmpty()){
            configuration.setServerName(this.getClass().getSimpleName());
        }
        this.server=WebServers.createWebServer(configuration.getPort())
                .add(new StaticFileHandler(configuration.getStaticFile()))
                .add(cauliHandler);
        if(isHttps()){
            server.setupSsl(new FileInputStream(new File(configuration.getKeyStore())),
                    configuration.getKeyStorePassword(),configuration.getKeyPassword()) ;
        }
        server.start().get();
        logger.info("启动HttpServer[{}],端口为:{}",configuration.getServerName(),configuration.getPort());
    }

    protected void init()  {
        configRoute(router);
        configServer(configuration);
        this.cauliHandler.configRoute(router);
        if(StringUtils.isNotEmpty(configuration.getViewPath())){
            try {
                FreeMarkerRender.getInstance().configViewPath(configuration.getViewPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public void stop() throws Exception {
        logger.info("关闭服务器：{},时间:{}",configuration.getServerName(),new Date());
        this.server.stop().get();
    }


    private boolean isHttps(){
        if(StringUtils.isEmpty(configuration.getKeyStore())){
            return false;
        }else{
            return true;
        }
    }

}
