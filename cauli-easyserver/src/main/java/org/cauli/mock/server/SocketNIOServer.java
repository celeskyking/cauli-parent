package org.cauli.mock.server;

import org.cauli.mock.action.AbstractSocketAction;
import org.cauli.mock.annotation.SocketRequest;
import org.cauli.mock.core.convert.ConvertExecuter;
import org.cauli.mock.core.convert.ConvertManager;
import org.cauli.mock.entity.ParametersModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class SocketNIOServer implements ISocketServer{

    private Logger logger = LoggerFactory.getLogger(SocketNIOServer.class);
    private AbstractSocketServer server;
    private SelectorLoop connectionBell;
    private SelectorLoop readBell;
    private boolean isReadBellRunning=false;
    private ServerSocketChannel channel;
    private String requestEncoding;
    private String responseEncoding;

    public SocketNIOServer(AbstractSocketServer server){
        this.server=server;
    }

    public void setServer(AbstractSocketServer server) {
        this.server = server;
    }

    private ServerSocket socket;

    public void start(){
        try{
            connectionBell = new SelectorLoop();
            readBell = new SelectorLoop();
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            socket = channel.socket();
            socket.bind(new InetSocketAddress("localhost",server.getPort()));
            channel.register(connectionBell.getSelector(), SelectionKey.OP_ACCEPT);
            new Thread(connectionBell).start();
            logger.info("启动Server：{}",server.getServerName());
        }catch (Exception e){
            logger.error("启动Server失败:{}",server.getServerName(),e);
            e.printStackTrace();
        }

    }

    @Override
    public void stop(){
        try {
            this.socket.close();
            this.channel.close();
        } catch (IOException e) {
            logger.error("关闭SocketServer失败：{}",server.getServerName(),e);
            e.printStackTrace();
        }

    }

    @Override
    public void setRequestEncoding(String encoding) {
        this.requestEncoding=encoding;
    }

    @Override
    public void setResponseEncoding(String encoding) {
        this.responseEncoding=encoding;
    }

    public String getRequestEncoding() {
        return requestEncoding;
    }

    public String getResponseEncoding() {
        return responseEncoding;
    }

    public class SelectorLoop implements Runnable {

        private Selector selector;
        private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        public SelectorLoop()throws IOException {
            this.selector = Selector.open();
        }

        public Selector getSelector() {
            return selector;
        }


        @Override
        public void run() {
            while(true){
                try{
                    this.selector.select();
                    Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
                    Iterator<SelectionKey> iterator= selectionKeys.iterator();
                    while(iterator.hasNext()){
                        SelectionKey key= iterator.next();
                        iterator.remove();
                        this.dispatch(key);
                    }
                }catch (Exception e){
                    logger.error("Socket 接收出现异常",e);
                }
            }
        }


        public void dispatch(SelectionKey selectionKey) throws Exception {
            if(selectionKey.isAcceptable()){
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(readBell.getSelector(),SelectionKey.OP_READ);
                synchronized (SocketNIOServer.this){
                    if(!SocketNIOServer.this.isReadBellRunning){
                        SocketNIOServer.this.isReadBellRunning=true;
                        new Thread(readBell).start();
                    }
                }
            }else if(selectionKey.isReadable()){
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                int count = socketChannel.read(byteBuffer);
                if(count<0){
                    selectionKey.cancel();
                    socketChannel.close();
                    return;
                }
                byteBuffer.flip();
                final String msg = Charset.forName(getRequestEncoding()).decode(byteBuffer).toString();
                logger.info("Server received [{}] from client address:"+ socketChannel.getRemoteAddress() ,msg);
                ConvertManager.ConvertMap convertMap = new ConvertManager.ConvertMap();
                convertMap.register(SocketRequest.class,new ConvertExecuter() {
                    @Override
                    public Object execute(Object clazz, ParametersModel parameterValuePairs) {
                        return msg;
                    }
                });
                final ParametersModel parametersModel = new ParametersModel(convertMap);
                final AbstractSocketAction action = server.route(msg);
                action.setParametersModel(parametersModel);
                action.setRequest(msg);
                String response = action.build();
                socketChannel.write(ByteBuffer.wrap(response.getBytes(Charset.forName(getResponseEncoding()))));
                byteBuffer.clear();
                if(action.getActionInfo().isUseMessage()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            action.onMessage(parametersModel);
                        }
                    }).start();
                }

            }
        }
    }
}
