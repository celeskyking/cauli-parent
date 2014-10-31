package org.cauli.mock.server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class SocketNIOServer implements ISocketServer{

    private Logger logger = LoggerFactory.getLogger(SocketNIOServer.class);


    private AbstractSocketServer server;

    private ChannelFactory factory ;
    private ServerBootstrap serverBootstrap;

    public SocketNIOServer(AbstractSocketServer server){
        this.server=server;
        init(server);
    }

    public void init(final AbstractSocketServer server) {
        this.server = server;
        this.factory= new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        this.serverBootstrap=new ServerBootstrap(factory);
        this.serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("encode",new StringEncoder());
                pipeline.addLast("decode",new StringDecoder());
                pipeline.addLast("handler",new NettyServerHandler(server));
                return pipeline;
            }
        });
        serverBootstrap.setOption("child.tcpNoDelay", true);
        serverBootstrap.setOption("child.keepAlive", true);
    }


    public void start(){
        serverBootstrap.bind(new InetSocketAddress(server.getPort()));
        logger.info("NIO SOCKET SERVER:{} 启动成功,port:{}",server.getServerName(),server.getPort());
    }

    @Override
    public void stop(){
        try {
            this.serverBootstrap.shutdown();
        } catch (Exception e) {
            logger.error("关闭SocketServer失败：{}",server.getServerName(),e);
            e.printStackTrace();
        }

    }


}
