/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.User;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.main.Session;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ISLEM_PC
 */
public class loginServices {

    public void login(String username, String password) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1/Pidev-web/web/app_dev.php/api/login/" + username + "/" + password);
        User user = new User();
        con.addResponseListener((NetworkEvent evt) -> {
            try {
                JSONParser jsonp = new JSONParser();
                Map<String, Object> mapUser = (Map<String, Object>) jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                                           System.out.println("mapUser : "+mapUser.get("id").toString());

                float id =(int) Float.parseFloat(mapUser.get("id").toString());
                   System.out.println("id : "+mapUser);
                user.setId((int) id);
               
                    Session.ConnectedUser.setPassword(password);
                    try {
                        Session.ConnectedUser.setId((int) Float.parseFloat(mapUser.get("id").toString()));
                        Session.ConnectedUser.setUsername(mapUser.get("username").toString());
                        Session.ConnectedUser.setRoles(mapUser.get("roles").toString());
                        Session.ConnectedUser.setNom(mapUser.get("nom").toString());
                        Session.ConnectedUser.setPrenom(mapUser.get("prenom").toString());
                        Session.ConnectedUser.setImage_id(mapUser.get("imageId").toString());
                        Session.ConnectedUser.setEmail(mapUser.get("email").toString());
                        Session.ConnectedUser.setPhone(mapUser.get("phone").toString());
                                           System.out.println("connected usr : "+Session.ConnectedUser);

                    } catch (NullPointerException n) {

                    }
             

                //}
            } catch (IOException ex) {
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);


    }
    
    

}
