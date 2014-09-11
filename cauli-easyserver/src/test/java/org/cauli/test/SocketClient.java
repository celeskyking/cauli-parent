package org.cauli.test;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by tianqing.wang on 2014/9/3
 */
public class SocketClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.setSoTimeout(3000);  //读写超时
        socket.connect(new InetSocketAddress("localhost", 8989), 3000);//连接超时
        System.out.println("Server:"+IOUtils.toString(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        //BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        out.println("I`m Client");
        out.flush();
        IOUtils.write("Client",out);

        System.out.println("client:Client");

        //out.close();
        //input.close();
        //IOUtils.write("Client",socket.getOutputStream());

        //System.out.println("Server:"+IOUtils.toString(socket.getInputStream()));

        socket.close();
    }
}
