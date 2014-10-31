package org.cauli.mock.server;


import org.apache.commons.io.IOUtils;
import org.cauli.mock.action.AbstractSocketAction;
import org.cauli.mock.annotation.SocketRequest;
import org.cauli.mock.core.convert.ConvertExecuter;
import org.cauli.mock.core.convert.ConvertManager;
import org.cauli.mock.entity.ParametersModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by tianqing.wang on 2014/9/3
 */
public class SocketIOHandler implements Runnable{

    private Logger logger = LoggerFactory.getLogger(SocketIOHandler.class);

    private Socket socket;

    private AbstractSocketServer server;

    public SocketIOHandler(Socket socket,AbstractSocketServer server){
        this.socket=socket;
        this.server=server;
    }

    @Override
    public void run() {
        try{
            InputStream inputStream = socket.getInputStream();
            int before = inputStream.available();
            while (true){
                Thread.sleep(100);
                int ava = inputStream.available();
                if(before==ava){
                    logger.info("接收完毕,接收内容长度为:{}",ava);
                    break;
                }
                before=ava;
            }
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(buffer);
            final String request = new String(outputStream.toByteArray(),server.getServerInfo().getRequestEncoding());
            logger.info("接收到的内容为:{}",request);
            ConvertManager.ConvertMap convertMap = new ConvertManager.ConvertMap();
            convertMap.register(SocketRequest.class,new ConvertExecuter() {
                @Override
                public Object execute(Object clazz, ParametersModel parameterValuePairs) {
                    parameterValuePairs.getContext().addContext("_request",request);
                    return request;
                }
            });
            final ParametersModel parametersModel = new ParametersModel(convertMap);
            final AbstractSocketAction action=server.route(request);
            action.setRequest(request);
            action.setParametersModel(parametersModel);
            IOUtils.write(action.build(), socket.getOutputStream());
            socket.close();
            action.addRequestHistory(parametersModel);
            if(action.getActionInfo().isUseMessage()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        action.onMessage(parametersModel);
                    }
                }).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(socket);
        }
    }

}
