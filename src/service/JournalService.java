/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.Publication;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nacef
 */
public class JournalService {
    
    public ArrayList<Publication> getPubs() {
        ArrayList<Publication> listPubs = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip+"/Pi-Dev-Web/web/app_dev.php/mobile/publications");
        con.addArgument("user",Session.ConnectedUser.getId().toString());
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
                        Publication pub = new Publication();
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        pub.setId((int) id);
                        pub.setContenu(obj.get("contenu").toString());
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            pub.setDatePublication(format.parse(obj.get("datePublication").toString()));
                        } catch (ParseException ex) {                           
                        }
                        User u = new User();
                        Map<String, Object> userMap = (Map<String, Object>) obj.get("user");
                        
                        u.setId((int)Float.parseFloat(userMap.get("id").toString()));
                        u.setNom(userMap.get("nom").toString());
                        u.setPrenom((String) userMap.get("prenom"));
                        pub.setIdUser(u);
                        listPubs.add(pub);

                    }
                } catch (IOException ex) {
                }

            }
        });
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        con.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listPubs;
    }
    
    public void modifierPublication(String idpub, String newContenu){
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip+"/Pi-Dev-Web/web/app_dev.php/mobile/modifierpublication");
        con.addArgument("idpub", idpub);
        con.addArgument("newContenu", newContenu);
        con.setPost(true);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        con.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void supprimerPublication(String idpub){
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip+"/Pi-Dev-Web/web/app_dev.php/mobile/supprimerpublication");
        con.addArgument("idpub", idpub);
        con.setPost(true);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        con.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void ajouterPublication(String contenu){
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip+"/Pi-Dev-Web/web/app_dev.php/mobile/ajouterpublication");
        con.addArgument("contenu", contenu);
        con.addArgument("user", Session.ConnectedUser.getId().toString());
        con.setPost(true);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        con.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
}
