package org.cauli.mock.sender;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by tianqing.wang on 2014/9/22
 */
public class SocketSender{

    private boolean isAync;

    private String content;

    private String host;

    private int port;

    private Charset charset;

    public SocketSender content(String content){
        this.content=content;
        return this;
    }


    public SocketSender aync(boolean isAync){
        this.isAync=isAync;
        return this;
    }

    public SocketSender host(String host){
        this.host=host;
        return this;
    }

    public SocketSender port(int port){
        this.port=port;
        return this;
    }

    public SocketSender encoding(Charset charset){
        this.charset=charset;
        return this;
    }

    public String send() throws Exception {
        if(isAync){
            return ayncSend();
        }else{
            return syncSend();
        }
    }

    private String syncSend() throws Exception{
        Socket socket = new Socket(host,port);
        IOUtils.write(content,socket.getOutputStream(),charset);
        String response = IOUtils.toString(socket.getInputStream(),charset);
        socket.close();
        return response;
    }

    private String ayncSend() throws Exception{
        byte[] response=null;
        SocketAddress address = new InetSocketAddress(host,port);
        SocketChannel client = SocketChannel.open(address);
        client.configureBlocking(false);
        Selector selector = Selector.open();
        client.register(selector, SelectionKey.OP_READ);
        client.write(ByteBuffer.wrap(content.getBytes(charset)));
        ByteBuffer readBuffer = ByteBuffer.allocate(64);
        for (SelectionKey key : selector.selectedKeys()) {
            selector.selectedKeys().remove(key);
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                readBuffer.clear();
                while (sc.read(readBuffer) > 0) {
                    bos.write(readBuffer.array());
                    readBuffer.clear();
                }
                response = bos.toByteArray();
                sc.close();
            }
        }
        return IOUtils.toString(response,charset.displayName());
    }



}
