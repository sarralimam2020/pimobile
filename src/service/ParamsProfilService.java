/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.User;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.main.Controller;
import com.codename1.main.Session;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nacef
 */
public class ParamsProfilService {
    
   
    
    public void modifierUserImage(String image){
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip+"/Pi-Dev-Web/web/app_dev.php/mobile/modifieruserimage");
        con.addArgument("user", Session.ConnectedUser.getId().toString());
        con.addArgument("imageurl", image);
        con.setPost(true);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        con.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void modifierUser(User user){
        ConnectionRequest con = new ConnectionRequest();
             con.addArgument("user",Session.ConnectedUser.getId().toString());
           
        con.addArgument("nom", user.getNom());
        con.addArgument("prenom", user.getPrenom());
        con.addArgument("tel", user.getPhone());
       
        
        con.setUrl(Controller.ip+"/Pi-Dev-Web/web/app_dev.php/mobile/modifieruser");
        con.setPost(true);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        con.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    
}
