/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

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
public class BlogService {

    public ArrayList<Blog> getList() {
        ArrayList<Blog> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/blogall");
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
                        Blog task = new Blog();
                        Double x = Double.parseDouble(obj.get("id").toString());
                        task.setId(x.intValue());
                        task.setTitle(obj.get("title").toString());
                        task.setImage(obj.get("image").toString());
                        Double y = Double.parseDouble(obj.get("repliesnumber").toString());
                        task.setLikesnumber(y.intValue());
                        Double y1 = Double.parseDouble(obj.get("likesnumber").toString());
                        task.setRepliesnumber(y1.intValue());
                        listTasks.add(task);
                    }
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }

    public ArrayList<Blog> getList2(int id) {
        ArrayList<Blog> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Controller.ip + "/Pidev-web/web/app_dev.php/api/mobile/blogdetails/" + id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> eventMap = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));

                    Blog event = new Blog();
                    event.setTitle(eventMap.get("title").toString());
                    event.setImage(eventMap.get("image").toString());
                    event.setContent(eventMap.get("content").toString());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
                    Date dateEvenement = format.parse(eventMap.get("dateCreation").toString());

                    event.setDateCreation(dateEvenement);
                    listTasks.add(event);
                } catch (IOException ex) {
                } catch (ParseException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }


    
}
