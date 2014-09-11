package org.cauli.test;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

/**
 * Created by tianqing.wang on 2014/8/29
 */
public class SocketTest {

    public static void main(String[] args){
        Configuration configuration =new Configuration();
        configuration.setHostname("localhost");
        configuration.setPort(6666);
        SocketIOServer socketIOServer = new SocketIOServer(configuration);
        socketIOServer.addMessageListener(new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                ackRequest.sendAckData(s);
            }
        });
    }
}
