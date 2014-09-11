package org.cauli.mock;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * @auther sky
 */
public class NetUtil {

    public static boolean isPortUsing(int port) {
        try {
            return isPortUsing("127.0.0.1",port);
        } catch (UnknownHostException e) {
            return true;
        }
    }


    public static boolean isPortUsing(String host,int port) throws UnknownHostException {
        InetAddress theAddress = InetAddress.getByName(host);
        try {
            Socket socket = new Socket(theAddress,port);
            socket.close();
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}
