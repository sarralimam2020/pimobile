/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.Rent;
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
import org.apache.http.annotation.Contract;

/**
 *
 * @author DJAZIA
 */
public class RentService {

    public ArrayList<Rent> getList() {
        ArrayList<Rent> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/getallrent");
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
                        Rent task = new Rent();
                        Double x = Double.parseDouble(obj.get("id").toString());
                        task.setId(x.intValue());
                        task.setMarque(obj.get("marque").toString());
                        task.setImageId(obj.get("imageId").toString());
                        Double y = Double.parseDouble(obj.get("dailyPrice").toString());
                        task.setDailyPrice(y.intValue());
                        
                        listTasks.add(task);
                    }
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }

    public ArrayList<Rent> getList2(int id) {
        ArrayList<Rent> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/getrent/" + id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> eventMap = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));

                    Rent event = new Rent();
                    event.setMarque(eventMap.get("marque").toString());
                    event.setImageId(eventMap.get("imageId").toString());
                    event.setPuissance(eventMap.get("puissance").toString());
                    event.setMatricule(eventMap.get("matricule").toString());
                    Double x = (double) eventMap.get("dailyPrice");


                    listTasks.add(event);
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }


 public void addrent(String debuit , String fin ,String num) {
              ConnectionRequest con=new ConnectionRequest();
  
        con.setUrl(Controller.ip+"/PiMobile/ScriptPHP/Rent/insert.php?debuit=" + debuit+"&fin="+fin+"&num="+num);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
                public void actionPerformed(NetworkEvent evt) {
                    byte[] data = (byte[]) evt.getMetaData(); 
                    String s = new String(data);  
                    if (s.equals("success")) {
                        Dialog.show("Succés", "ajout effectué", "Ok", null);

                 
                    } 
                    else {
                      
                        Dialog.show("Echec", "Utilisateur existe deja", "Ok", null);
                        }
                }
            });
        NetworkManager.getInstance().addToQueue(con);
    }


       
    
}
