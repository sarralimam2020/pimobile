/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.Album;
import Entity.User;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
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
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Nacef
 */
public class AlbumService {
    
    public ArrayList<Album> getAlbums() {
        ArrayList<Album> listPubs = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip+"/Pi-Dev-Web/web/app_dev.php/mobile/getalbum");
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
                        Album pub = new Album();
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        pub.setId((int) id);
                        pub.setUrl(obj.get("url").toString());
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
    
    public void ajouterPhoto(String image){
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip+"/Pi-Dev-Web/web/app_dev.php/mobile/addalbumphoto");
        con.addArgument("image", image);
        con.addArgument("user", Session.ConnectedUser.getId().toString());

        con.setPost(true);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void supprimerPhoto(String idImg){
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip+"/Pi-Dev-Web/web/app_dev.php/mobile/supprimeralbum");
        con.addArgument("idImg", idImg);
        con.setPost(true);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    protected String genString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    
    public String uploadPhoto(String path){
        String photoName = genString()+".jpg";
        try {
            MultipartRequest cr = new MultipartRequest();
            cr.setUrl(Controller.ip+"/Pidev-Desktop/savetofile.php");
            cr.setPost(true);
            String mime="image/jpeg";
            cr.addData("file", path, mime);            
            cr.setFilename("file", photoName);//any unique name you want
            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            cr.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (IOException ex) {
        }
        return photoName;
    }
}
