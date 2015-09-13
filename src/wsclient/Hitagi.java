/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsclient;

import java.io.IOException;
import java.util.*;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import org.json.JSONObject;
import org.json.JSONArray;
/**
 *
 * @author Алексей
 */
public class Hitagi {
    
    private Hitagi.EventHandler listener;
    static Map<String, String> usersNicks = new HashMap<String, String>();
    static JSONObject users;
    
    // open websocket
    private WebsocketClientEndpoint client;
    
    public void start(){
    
        try {
            client = new WebsocketClientEndpoint();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null , "Ошибка: URISyntaxException"); 
        }
        
        client.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            @Override
            public void handleMessage(JSONObject result) {

                String resultType = (String) result.get("type");
                System.out.println("Incoming: " + result.toString());

                if(resultType.equals("auth")) {
                    
                    String status = (String) result.get("status");
                    if(status.equals("ok")) {
                        User me = new User();
                        me.setByJson(result);
                        if(listener != null)
                            listener.doAuth(me);
                    } else { 
                        JOptionPane.showMessageDialog(null , "Ошибка авторизации: " + result.get("reason")); 
                    }
                    
                } else if(resultType.equals("joinroom")) {
                    
                    String status = (String) result.get("status");
                    
                    if(status.equals("ok")) {

                        Room r = new Room();
                        r.setByJson(result);

                        if(listener != null)
                            listener.doJoinroom(r);                    
                    
                    } else { 
                        JOptionPane.showMessageDialog(null , "Ошибка входа в комнату: " + result.get("reason")); 
                    }
                    
                } else if(resultType.equals("chat")) {
                    
                    ChatMessage message = new ChatMessage();
                    message.setByJson(result);
                    
                    if(listener != null)
                        listener.doChat(message);
                    
                } else {
                    System.err.println("Unknown Type: " + resultType);
                }
                
            }
        });    
    
        client.go();
        
    }

    public void disconnect(){
        
        try {
        
            client.disconnect();
            
        } catch (IOException e){
    
        }
        
    }    
    
    public void auth(String login, String pass){
        JSONObject json = new JSONObject();

        json.put("type", "auth");
        json.put("login", login);
        json.put("pass", pass);
        json.put("mobile", false);
        json.put("client", "Java Client");

        client.sendMessage(json);
    }    
    
    public void joinroom(String room){
        JSONObject json = new JSONObject();

        json.put("type", "joinroom");
        json.put("room", room);
        json.put("count", 30);
        
        client.sendMessage(json);
    }      
    
    public void chat(String room, String text){
        JSONObject json = new JSONObject();

        json.put("type", "chat");
        json.put("room", room);
        json.put("text", text);
        json.put("cl", "000000");
        
        client.sendMessage(json);        
    }

    public void custom(JSONObject json){        
        client.sendMessage(json); 
    }    
    
    /* CLASSES */
    
    public static class Room{
    
        public String name;
        public String caption;
        public int count;
        public int newmes;
        public String topic;
        public JSONArray messages;
        public JSONObject users;
        
        public void setByJson(JSONObject json){
            
            this.name = json.getString("name");
            this.caption = json.getString("caption");
            this.count = json.getInt("count");
            this.newmes = json.getInt("newmes");
            this.topic = json.getString("topic");
            this.messages = json.getJSONArray("messages");
            this.users = json.getJSONObject("users");
            
            Hitagi.users = this.users;
            
            Iterator<String> keys = this.users.keys();
            while( keys.hasNext() ) {
                String login = (String)keys.next();
                Hitagi.usersNicks.put(login, this.users.getJSONObject(login).getString("nick"));
            }

        }
        
    
    }
    
    public static class User {
    
        //login: "admin", nickname: "Spirit", privilege: 0, state: 0, status: "ok", statustext: "", 
        //textcolor: "ed0915", type: "auth", url: "http://aniavatars.com:8081/avatar/admin?89"
        
        public String login;
        public String nick;
        public int    privilege;
        public int    state;
        public String status;
        public String statustext;
        public String color;
        public String avatarUrl;
        
        public void setByJson(JSONObject json){
            this.login = json.getString("login");
            this.nick = json.getString("nickname");
            this.privilege = json.getInt("privilege");
            this.state = json.getInt("state");
            this.status = json.getString("status");
            this.statustext = json.getString("statustext");
            this.color = json.getString("textcolor");
            this.avatarUrl = json.getString("url");            
        }        
        
    }    

    public static class ChatMessage {
    
        //c: "ed0915", id: "55f3449b1f46350b0f564443", n: "Spirit", r: "public", t: "щзщзщщз", type: "chat", u: "admin" 
        
        public String color;
        public String id;
        public String nick;
        public String room;
        public String text;
        public String user;
        
        public void setByJson(JSONObject json){

            this.id = json.getString("id");
            this.text = json.getString("t");
            this.room = json.getString("r");
            this.color = json.getString("c");
            this.user = json.getString("u");
            
            String userNick = Hitagi.usersNicks.get(this.user);

            this.nick = json.has("n") ? json.getString("n") : userNick;
            
        }
        
        public String toChat(){
            return this.nick + ": " + this.text;
        }
    
    }    
    
    /* EVENTS */
    
    public void setListener (EventHandler listener){
        this.listener = listener;
    }
    
    public static interface EventHandler {
        public void doAuth(User user);
        public void doJoinroom(Room room);
        public void doChat(ChatMessage message);
    }   
    
}
