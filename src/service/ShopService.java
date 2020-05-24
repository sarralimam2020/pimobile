/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.Panier;
import Entity.Produict;
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
 * @author DJAZIA
 */
public class ShopService {

    public ArrayList<Produict> getList() {
        ArrayList<Produict> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/shopall");
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
                        Produict task = new Produict();
                        Double x = Double.parseDouble(obj.get("id").toString());
                        task.setId(x.intValue());
                        task.setNom(obj.get("nom").toString());
                        task.setImageId(obj.get("imageId").toString());
                        Double y = Double.parseDouble(obj.get("quantity").toString());
                        task.setQuantity(y.intValue());
                        
                        listTasks.add(task);
                    }
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }

    public ArrayList<Produict> getList2(int id) {
        ArrayList<Produict> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/getprouidct/" + id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> eventMap = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));

                    Produict event = new Produict();
                    event.setDescription(eventMap.get("description").toString());
                    event.setImageId(eventMap.get("imageId").toString());
                    event.setNom(eventMap.get("nom").toString());
                    Double x = (double) eventMap.get("prix");

                    event.setPrix(x.intValue());
                     Double x1 = (double) eventMap.get("quantity");

                    event.setQuantity(x1.intValue());

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
                    Date dateEvenement = format.parse(eventMap.get("date").toString());

                    event.setDate(dateEvenement);
                    listTasks.add(event);
                } catch (IOException ex) {
                } catch (ParseException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }


       public void addtopanier(int user_id  , String produit_id  , String quantite, String prix ) {
      
        
        ConnectionRequest con=new ConnectionRequest();
           DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();

  
        con.setUrl(Controller.ip+"/PiMobile/ScriptPHP/Panier/insert.php?userid=" + user_id+"&produitid="+produit_id+"&quantite="+quantite+"&prix=" +prix );
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
