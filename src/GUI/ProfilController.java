/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.User;
import com.codename1.components.InfiniteProgress;
import com.codename1.main.Controller;
import com.codename1.main.Session;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import service.UtilService;

/**
 *
 * @author Nacef
 */
public class ProfilController extends Controller {
    UtilService utilService = UtilService.getInstance();

    @Override
    public void initialize() {                      
        Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        c.setScrollableY(true);
        
        Session session = Session.getInstance();
        
        User u = session.ConnectedUser;
        
        Container changec = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        Container entete = new Container(new BoxLayout(BoxLayout.X_AXIS));
        //c.add(theme.getImage(t.getImg()));
        Image img = utilService.getImageProfilFromURL(u.getImage_id());
        Label nt = new Label(u.getNom()+" "+u.getPrenom());
        entete.add(img).add(nt);
        //c.add(entete);        
        Button journal = new Button("Journal");
        journal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                JournalController journalController = new JournalController();
                changec.removeAll();
                journalController.initialize();
                changec.addComponent(journalController.getView());
                changec.revalidate();
            }
        });
        
        Button apropos = new Button("A Propos");
        apropos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
        
            }
        });
        
        Button album = new Button("Album");
        album.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                AlbumController albumController = new AlbumController();
                changec.removeAll();
                albumController.initialize();                
                changec.addComponent(albumController.getView());
                changec.revalidate();
            }
        });
        
        Button sugg = new Button("Suggestion des Profils");
        sugg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
     
            }
        });
        
        c.add(entete);
        c.add(journal);
        c.add(apropos);
        c.add(album);
        c.add(sugg);
        c.add(changec);
        //------------
        this.rootContainer.removeAll();
        this.rootContainer.add(BorderLayout.NORTH, c);
        this.rootContainer.revalidate();
    }
    
}
