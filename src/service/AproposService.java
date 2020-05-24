/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.CentreInteret;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nacef
 */
public class AproposService {
    
    public Map<String,ArrayList<CentreInteret>> getCentres() {
        Map<String,ArrayList<CentreInteret>> listCentres = new HashMap<>();
        
        ArrayList<CentreInteret> films = new ArrayList<>();
        ArrayList<CentreInteret> series = new ArrayList<>();
        ArrayList<CentreInteret> artists = new ArrayList<>();
        ArrayList<CentreInteret> livres = new ArrayList<>();
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip+"/Pi-Dev-Web/web/app_dev.php/mobile/centres");
        con.addArgument("user", Session.ConnectedUser.getId().toString());
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listFilms = (List<Map<String, Object>>) tasks.get("films");
                    List<Map<String, Object>> listSeries = (List<Map<String, Object>>) tasks.get("series");
                    List<Map<String, Object>> listArtists = (List<Map<String, Object>>) tasks.get("artists");
                    List<Map<String, Object>> listLivres = (List<Map<String, Object>>) tasks.get("livres");

                    for (Map<String, Object> obj : listFilms) {
                        CentreInteret pub = new CentreInteret();
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        pub.setId((int) id);
                        pub.setContenu(obj.get("contenu").toString());
                        pub.setType("film");
                        User u = new User();
                        Map<String, Object> userMap = (Map<String, Object>) obj.get("user");
                        
                        u.setId((int)Float.parseFloat(userMap.get("id").toString()));
                        u.setNom(userMap.get("nom").toString());
                        u.setPrenom((String) userMap.get("prenom"));
                        pub.setIdUser(u);
                        films.add(pub);
                    }
                    
                    for (Map<String, Object> obj : listSeries) {
                        CentreInteret pub = new CentreInteret();
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        pub.setId((int) id);
                        pub.setContenu(obj.get("contenu").toString());
                        pub.setType("serie");
                        User u = new User();
                        Map<String, Object> userMap = (Map<String, Object>) obj.get("user");
                        
                        u.setId((int)Float.parseFloat(userMap.get("id").toString()));
                        u.setNom(userMap.get("nom").toString());
                        u.setPrenom((String) userMap.get("prenom"));
                        pub.setIdUser(u);
                        series.add(pub);
                    }
                    
                    for (Map<String, Object> obj : listArtists) {
                        CentreInteret pub = new CentreInteret();
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        pub.setId((int) id);
                        pub.setContenu(obj.get("contenu").toString());
                        pub.setType("artist");
                        User u = new User();
                        Map<String, Object> userMap = (Map<String, Object>) obj.get("user");
                        
                        u.setId((int)Float.parseFloat(userMap.get("id").toString()));
                        u.setNom(userMap.get("nom").toString());
                        u.setPrenom((String) userMap.get("prenom"));
                        pub.setIdUser(u);
                        artists.add(pub);
                    }
                    
                    for (Map<String, Object> obj : listLivres) {
                        CentreInteret pub = new CentreInteret();
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        pub.setId((int) id);
                        pub.setContenu(obj.get("contenu").toString());
                        pub.setType("livre");
                        User u = new User();
                        Map<String, Object> userMap = (Map<String, Object>) obj.get("user");
                        
                        u.setId((int)Float.parseFloat(userMap.get("id").toString()));
                        u.setNom(userMap.get("nom").toString());
                        u.setPrenom((String) userMap.get("prenom"));
                        pub.setIdUser(u);
                        livres.add(pub);
                    }
                    
                    
                } catch (IOException ex) {
                }

            }
        });
        
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        con.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(con);
        listCentres.put("films", films);
        listCentres.put("series", series);
        listCentres.put("artists", artists);
        listCentres.put("livres", livres);
        return listCentres;
    }
    

    
}
