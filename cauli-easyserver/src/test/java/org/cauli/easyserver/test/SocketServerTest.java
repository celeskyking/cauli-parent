package org.cauli.easyserver.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by tianqing.wang on 2014/10/31
 */
public class SocketServerTest {

    public static void main(String[] args) throws IOException {
        byte[] response = null;
        SocketChannel client = null;
        SocketChannel sc = null;
        Selector selector = null;
        try {
            selector = Selector.open();
            SocketAddress serverAddress = new InetSocketAddress("127.0.0.1", 7777);
            client = SocketChannel.open(serverAddress);
            client.configureBlocking(false); // 设置为异步
            client.register(selector, SelectionKey.OP_READ);
            client.write(ByteBuffer.wrap("request".getBytes()));
            ByteBuffer readBuffer = ByteBuffer.allocate(64);
            int eventNum = selector.select(3000);
            if (eventNum == 0) {
                throw new RuntimeException("中行响应超时，timeout:" + 7777);
            }
            for (SelectionKey key : selector.selectedKeys()) {
                selector.selectedKeys().remove(key);
                if (key.isReadable()) {
                    sc = (SocketChannel) key.channel();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    readBuffer.clear();
                    while (sc.read(readBuffer) > 0) {
                        bos.write(readBuffer.array());
                        readBuffer.clear();
                    }
                    response = bos.toByteArray();
                    sc.close();
                    selector.close();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(new String(response));
        client.close();
    }
}
