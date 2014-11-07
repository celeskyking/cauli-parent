package org.cauli.mock.server;

import org.apache.commons.io.IOUtils;
import org.cauli.mock.action.AbstractSocketAction;
import org.cauli.mock.annotation.SocketRequest;
import org.cauli.mock.core.convert.ConvertExecuter;
import org.cauli.mock.core.convert.ConvertManager;
import org.cauli.mock.entity.ParametersModel;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by tianqing.wang on 2014/10/31
 */
public class NettyServerHandler extends SimpleChannelUpstreamHandler{

    private AbstractSocketServer server;

    private Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);


    public NettyServerHandler(AbstractSocketServer server){
        this.server=server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        final String request = (String) e.getMessage();
        String host = ((InetSocketAddress)e.getRemoteAddress()).getHostName();
        logger.info("Server received [{}] from client address:"+ host ,request);
        ConvertManager.ConvertMap convertMap = new ConvertManager.ConvertMap();
        convertMap.register(SocketRequest.class,new ConvertExecuter() {
            @Override
            public Object execute(Object clazz, ParametersModel parameterValuePairs) {
                parameterValuePairs.getContext().addContext("_request",request);
                return request;
            }
        });
        final ParametersModel parametersModel = new ParametersModel(convertMap);
        final AbstractSocketAction action = server.route(request);
        action.setParametersModel(parametersModel);
        action.setRequest(request);
        String response = action.build();
        e.getChannel().write(IOUtils.toString(response.getBytes(),server.getServerInfo().getResponseEncoding()));
        action.addRequestHistory(parametersModel);
        if(action.getActionInfo().isUseMessage()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    action.onMessage(parametersModel);
                }
            }).start();
        }
        e.getChannel().close();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        Channel ch = e.getChannel();
        ch.close();
    }
}
