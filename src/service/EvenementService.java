/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.Evenement;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.main.Controller;
import com.codename1.main.Session;

import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DJAZIA
 */
public class EvenementService {

    public ArrayList<Evenement> getList() {
        ArrayList<Evenement> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/getall");
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
                        Evenement task = new Evenement();
                        Double x = Double.parseDouble(obj.get("id").toString());
                        task.setId(x.intValue());
                        task.setTitre(obj.get("nom").toString());
                        task.setImageEve(obj.get("nomImage").toString());
                        Double y = Double.parseDouble(obj.get("nbreParticipants").toString());
                        task.setNbplaces(y.intValue());
                        listTasks.add(task);
                    }
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }

    public ArrayList<Evenement> getList2(int id) {
        ArrayList<Evenement> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/getev/" + id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> eventMap = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));

                    Evenement event = new Evenement();
                    event.setTitre(eventMap.get("nom").toString());
                    event.setImageEve(eventMap.get("nomImage").toString());
                    event.setTitreCordination(eventMap.get("nom").toString());
                    Double x = (double) eventMap.get("nbreParticipants");

                    event.setNbplaces(x.intValue());

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
                    Date dateEvenement = format.parse(eventMap.get("datesortie").toString());

                    event.setDateEvenement(dateEvenement);
                    listTasks.add(event);
                } catch (IOException ex) {
                } catch (ParseException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }

    public static boolean partEvent(Evenement e) {

        String url = Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/getp/" + e.getId();
        ConnectionRequest cr = new ConnectionRequest(url);
        cr.setPost(false);
        NetworkManager.getInstance().addToQueue(cr);
        return true;
    }
    
    public boolean checkParticipation(int id)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/checkparticipation");
        con.addArgument("ide", String.valueOf(id));
        con.addArgument("user", Session.ConnectedUser.getId().toString());
        NetworkManager.getInstance().addToQueueAndWait(con);
        String response = new String(con.getResponseData());
        System.out.println(response);
        if(response.equals("\"OK\"")) return true;
        return false;
    }
    
    public void participer(int id)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/participation/"+id);
        con.addArgument("user", Session.ConnectedUser.getId().toString());
        con.addArgument("event",String.valueOf(id));
        NetworkManager.getInstance().addToQueueAndWait(con);
        con.getResponseData();
    }
    
}
