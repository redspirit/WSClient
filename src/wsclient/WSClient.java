/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsclient;

import java.util.*;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author Алексей
 */
public class WSClient {

    private static String defaultRoom = "public"; //tyomnie_
    
    public static void main(String[] args){
        
        ChatFrame chatFrame = new ChatFrame();        
        AuthFrame authFrame = new AuthFrame();          
        
        chatFrame.setVisible(true);
        
        Hitagi hitagi = new Hitagi();
        
        hitagi.setListener(new Hitagi.EventHandler() {

            @Override
            public void doAuth(Hitagi.User user) {
                
                //System.out.println();
                
                authFrame.setVisible(false);
                
                chatFrame.setMe(user);
                
                hitagi.joinroom(defaultRoom);
                
            }

            @Override
            public void doJoinroom(Hitagi.Room room) {

                JSONObject item;
                
                for(int i = 0 ; i < room.messages.length(); i++){
                    item = room.messages.getJSONObject(i);
                    chatFrame.addMessage(item.getString("n") + ": " + item.getString("t"));
                }
                
            }

            @Override
            public void doChat(Hitagi.ChatMessage message) {

                chatFrame.addMessage(message.toChat());
                
            }
        });
        
        
        
        chatFrame.setListener(new ChatFrame.EventHandler() {

            @Override
            public void sendMessage(String message) {
                
                hitagi.chat(defaultRoom, message);
                
            }

            @Override
            public void clickSignIn() {
                authFrame.setVisible(true);
            }

            @Override
            public void clickSignOut() {
                
            }

            @Override
            public void clickExit() {
                hitagi.disconnect();
                System.exit(0);
            }
            
        });
        
        authFrame.setListener(new AuthFrame.EventHandler() {

            @Override
            public void doEnter(String login, String password) {

                hitagi.auth(login, password);
                
            }

            @Override
            public void doCancel() {

                
                
            }
        });        
        
        hitagi.start();
        
//        for(int n = 0; n <= 40; n++) {
//            chatFrame.addMessage("Line " + n +"\n");
//        }
        
//        clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
//            @Override
//            public void handleMessage(JSONObject jsonResult) {
//

//                String out = "<html>";
//
//                Iterator<String> keys = jsonResult.keys();
//                while( keys.hasNext() ) {
//                    String key = (String)keys.next();
//                    out += key + ": " + jsonResult.get(key) + "<br>";
//                }
//
//                debugFrame.setResponseText(out);
//
//            }
//        });
        
    }
    
}
