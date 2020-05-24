/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.main;


import Entity.User;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ASUS
 */
public class Session {
   // private int id;
    private String username;
    private String password;
    private User user;
private static Session session;
   
    public Session() {
    }
  public static Session getInstance()
    {
        if(session == null) session = new Session();
        return session;
    }
    
        public static User ConnectedUser = new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

     
     
        public void setParameters(String username,String password)
    {
        this.username = username;
        this.password = password;
    }
        
    
        public ArrayList<User> getListUsers(String json) {

        ArrayList<User> listUsers = new ArrayList<>();

        try {
           // System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> Users = j.parseJSON(new CharArrayReader(json.toCharArray()));           
            List<Map<String, Object>> list = (List<Map<String, Object>>) Users.get("root");
            System.out.println("97"+list);
            for (Map<String, Object> mapUser : list) {
                User user = new User();
                         user.setId((int) Float.parseFloat(mapUser.get("id").toString()));
                        user.setUsername(mapUser.get("username").toString());
                        user.setRoles(mapUser.get("roles").toString());
                        user.setNom(mapUser.get("nom").toString());
                        user.setPrenom(mapUser.get("prenom").toString());
                        user.setImage_id(mapUser.get("imageId").toString());
                        user.setEmail(mapUser.get("email").toString());
                        user.setPhone(mapUser.get("phone").toString());
    
                           
                listUsers.add(user);

            }

        } catch (IOException ex) {
        }
        System.out.println(listUsers);
        return listUsers;

    }
      ArrayList<User> listUsers = new ArrayList<>();
    
    public ArrayList<User> getListUser2(){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1/Pi-Dev-Web/web/app_dev.php/mobile/allusers");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Session ser = new Session();
                listUsers= ser.getListUsers(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listUsers;
    }  
        
    public boolean initSession()
    {
        ArrayList<User> listUsers = new ArrayList<>();
        listUsers=getListUser2();
        for(User u:listUsers){
    System.out.println("Sesssionss"+u);
    if (u.getUsername().equals(username))
    {
      if (!BCrypt.checkpw(password,u.getPassword())) return false;  
      else return true;
    }
    
}
  
return false;    
    }     
    
   
    
}
