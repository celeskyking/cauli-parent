package org.cauli.mock.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tianqing.wang on 2014/9/5
 */
public class MultiThreadServer implements ISocketServer{

    private Logger logger = LoggerFactory.getLogger(MultiThreadServer.class);
    private AbstractSocketServer server;
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private final int POOL_SIZE = 10;
    private String responseEncoding;
    private String requestEncoding;

    public MultiThreadServer(AbstractSocketServer server) throws IOException {
        this.server=server;
        serverSocket = new ServerSocket(server.getPort());
        executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
        logger.info("服务器启动,端口号为:{}",server.getPort());
    }

    public void service(){
        while(true){
            Socket socket;
            try{
                socket=serverSocket.accept();
                SocketIOHandler socketIOHandler = new SocketIOHandler(socket,server);
                socketIOHandler.setRequestEncoding(getRequestEncoding());
                socketIOHandler.setResponseEncoding(getResponseEncoding());
                executorService.execute(socketIOHandler);
            }catch (Exception e){
                logger.error("发生了SOCKET异常",e);
            }
        }
    }

    @Override
    public void start() {
        service();
    }

    @Override
    public void stop() {
        executorService.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error("关闭Socket服务器失败：{}",server.getServerName(),e);
            e.printStackTrace();
        }
    }

    public void setServer(AbstractSocketServer server) {
        this.server = server;
    }

    public String getResponseEncoding() {
        return responseEncoding;
    }

    public void setResponseEncoding(String responseEncoding) {
        this.responseEncoding = responseEncoding;
    }

    public String getRequestEncoding() {
        return requestEncoding;
    }

    public void setRequestEncoding(String requestEncoding) {
        this.requestEncoding = requestEncoding;
    }
}

