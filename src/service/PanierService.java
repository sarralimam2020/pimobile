/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.Panier;
import Entity.User;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.main.Controller;
import com.codename1.main.Session;

import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DJAZIA
 */
public class PanierService {
    User u = Session.ConnectedUser;
    public ArrayList<Panier> getList() {
        ArrayList<Panier> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/panier");
        con.addArgument("userId",Session.ConnectedUser.getId().toString());
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                    System.out.println("list : " + list);
                    for (Map<String, Object> obj : list) {
                        Panier task = new Panier();
                        Double x = Double.parseDouble(obj.get("id").toString());
                        task.setId(x.intValue());
                    
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            task.setDateP(format.parse(obj.get("dateP").toString()));
                        } catch (ParseException ex) {                           
                        };
                        Double y = Double.parseDouble(obj.get("prix").toString());
                        task.setPrix(y.intValue());
                         Double y1 = Double.parseDouble(obj.get("quantite").toString());
                        task.setQuantite(y1.intValue());
                       
                        listTasks.add(task);
                    
                } 
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }

}
