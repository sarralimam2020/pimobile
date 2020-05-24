/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Album;
import Entity.User;
import com.codename1.main.Controller;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;
import service.AutreAlbumService;
import service.UtilService;

/**
 *
 * @author Nacef
 */
public class AutreAlbumController extends Controller {
    
    AutreAlbumService autreAlbumService = new AutreAlbumService();
    UtilService utilService = UtilService.getInstance();
    
    public static User autreUser;

    public static User getAutreUser() {
        return autreUser;
    }

    public static void setAutreUser(User autreUser) {
        AutreAlbumController.autreUser = autreUser;
    }
    
    public AutreAlbumController()
    {
        super();
    }

    @Override
    public void initialize() {
        Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Font boldLarge = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE);
        
        Label ci = new Label("Album Photos"){
            public void paint(Graphics g) {
                super.paint(g);
                g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
            }
        };                
        ci.getUnselectedStyle().setFont(boldLarge);        
        c.add(ci);
        
        Container album = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        ArrayList<Album> albums = autreAlbumService.getAlbums(autreUser.getId().toString());
        for(Album loi:albums){
            Image img = utilService.getImageAlbumFromURL(loi.getUrl());
            //ImageViewer iv = new ImageViewer(img);            
            album.add(img);            
        }
        Container albBL = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        albBL.add(BorderLayout.CENTER, album);
        c.add(albBL);
        //-----------------------------------
        this.rootContainer.removeAll();
        this.rootContainer.add(BorderLayout.NORTH, c);
        this.rootContainer.revalidate();
    }
    
}
