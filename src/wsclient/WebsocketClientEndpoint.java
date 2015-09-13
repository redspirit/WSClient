/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsclient;

import java.io.IOException;
import java.net.URI;
import javax.websocket.*;
import org.json.JSONObject;
 
/**
 * ChatServer Client
 * 
 * @author Jiji_Sasidharan
 * implements Runnable
 */
@ClientEndpoint
public class WebsocketClientEndpoint {
    Session userSession = null;
    private MessageHandler messageHandler;
 
    
    public void go() {
        
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI("ws://46.254.19.232:8080"));
        } catch (Exception e) {
            System.out.println("OPA");
            throw new RuntimeException(e);
        }
        
    }
    
    public WebsocketClientEndpoint() {
        //URI endpointURI
    }
 
    /**
     * Callback hook for Connection open events.
     * 
     * @param userSession
     *            the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("Hitagi: onOpen");
        this.userSession = userSession;
    }
 
    /**
     * Callback hook for Connection close events.
     * 
     * @param userSession
     *            the userSession which is getting closed.
     * @param reason
     *            the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("Hitagi: onClose");
        this.userSession = null;
    }
 
    /**
     * Callback hook for Message Events. This method will be invoked when a
     * client send a message.
     * 
     * @param message
     *            The text message
     */
    @OnMessage
    public void onMessage(String message) {
        
        JSONObject jsonMessage = new JSONObject(message);
        
        if (this.messageHandler != null)
            this.messageHandler.handleMessage(jsonMessage);
    }
 
    /**
     * register message handler
     * 
     * @param message
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }
 
    public void sendMessage(JSONObject message) {
        System.out.println("Send: " + message.toString());
        this.userSession.getAsyncRemote().sendText(message.toString());
    }

    public void disconnect() throws IOException {
        this.userSession.close();        
    }

    public static interface MessageHandler {
        public void handleMessage(JSONObject message);
    }
}