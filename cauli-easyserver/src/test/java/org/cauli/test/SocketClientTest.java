package org.cauli.test;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.parser.Packet;
import org.junit.Test;

import java.net.SocketAddress;
import java.util.Set;
import java.util.UUID;

/**
 * Created by tianqing.wang on 2014/8/29
 */
public class SocketClientTest {


    @Test
    public void testSocketClient(){
        SocketIOClient client  = new SocketIOClient() {
            @Override
            public HandshakeData getHandshakeData() {
                return null;
            }

            @Override
            public Transport getTransport() {
                return null;
            }

            @Override
            public void sendEvent(String s, AckCallback<?> ackCallback, Object... objects) {

            }

            @Override
            public void send(Packet packet, AckCallback<?> ackCallback) {

            }

            @Override
            public void sendJsonObject(Object o, AckCallback<?> ackCallback) {

            }

            @Override
            public void sendMessage(String s, AckCallback<?> ackCallback) {

            }

            @Override
            public SocketIONamespace getNamespace() {
                return null;
            }

            @Override
            public UUID getSessionId() {
                return null;
            }

            @Override
            public SocketAddress getRemoteAddress() {
                return null;
            }

            @Override
            public boolean isChannelOpen() {
                return false;
            }

            @Override
            public void joinRoom(String s) {

            }

            @Override
            public void leaveRoom(String s) {

            }

            @Override
            public Set<String> getAllRooms() {
                return null;
            }

            @Override
            public void sendMessage(String s) {

            }

            @Override
            public void sendJsonObject(Object o) {

            }

            @Override
            public void send(Packet packet) {

            }

            @Override
            public void disconnect() {

            }

            @Override
            public void sendEvent(String s, Object... objects) {

            }

            @Override
            public void set(String s, String s2) {

            }

            @Override
            public String get(String s) {
                return null;
            }

            @Override
            public boolean has(String s) {
                return false;
            }

            @Override
            public void del(String s) {

            }
        };
    }
}
