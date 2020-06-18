/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.Commontaire;
import Entity.Blog;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.main.Controller;
import com.codename1.main.Session;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MisterSpy
 */
public class CommentaireService {
    
        public ArrayList<Commontaire> tt(String id) {
         ArrayList<Commontaire> listTasks = new ArrayList<>();
            ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip+"/Pidev-web/web/app_dev.php/api/mobile/showcom/");
        con.addArgument("Blogid",id);
        System.out.println(con.getUrl());
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
           

                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

                    for (Map<String, Object> obj : list) {
                        Commontaire task = new Commontaire();
                        float id = Float.parseFloat(obj.get("id").toString());
                        task.setId((int) id);
                         float IdUser = Float.parseFloat(obj.get("user").toString());
                        task.setUser((int) IdUser);
                        task.setContent(obj.get("content").toString());
                      
  
                        listTasks.add(task);

                    }
                } catch (IOException ex) {
                }

            }
        });
            NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }

    public void ajouter_com(String text) {
       ConnectionRequest con = new ConnectionRequest();
       // con.setUrl(Controller.ip+"/piIntegration/web/app_dev.php/forum/ajouter_commentaire/"+Beblio.getId_user() + "/" + User.getIds() +"/" + text);
        NetworkManager.getInstance().addToQueueAndWait(con);
        
    }
}
