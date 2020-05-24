/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Publication;
import Entity.User;
import com.codename1.components.SpanLabel;
import com.codename1.io.URL;
import com.codename1.main.Controller;
import com.codename1.main.Session;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import service.JournalService;
import service.UtilService;

/**
 *
 * @author Nacef
 */
public class JournalController extends Controller {
    
    JournalService journalService = new JournalService();
    UtilService utilService = UtilService.getInstance();
    
    Container changec = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    
    Session session = Session.getInstance();
        
    User u = session.ConnectedUser;
    
    
    public JournalController()
    {
        super();
    }

    @Override
    public void initialize() {        
        
        Font normalSmall = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        Font normalMedium = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        Font boldSmall = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
        Font boldMedium = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Font boldLarge = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE);
        
        //System.out.println(service.getPubs());
        List<Publication> pubs = journalService.getPubs();
        
        //-------------
        /*Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        c.setScrollableY(true);
        
        
        
        Container entete = new Container(new BoxLayout(BoxLayout.X_AXIS));
        //c.add(theme.getImage(t.getImg()));
        Label nt = new Label("Achraf");
        entete.add(nt);
        //c.add(entete);        
        Button journal = new Button("Journal");
        journal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                c.removeAll();
                JournalController journalController = new JournalController();
                journalController.initialize();
                //c.add(BorderLayout.NORTH, c);
                c.revalidate();
            }
        });
        
        Button apropos = new Button("A Propos");
        apropos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                changec.removeAll();
                AProposController aProposController = new AProposController();
                aProposController.initialize();
                changec.addComponent(aProposController.getView());
                changec.revalidate();
            }
        });
        Button album = new Button("Album");
        Button sugg = new Button("Suggestion des Profils");
        //c.add(apropos);
        //c.add(album);
        //c.add(sugg);*/
        //------
        
        
        //------
        Container publicationSend = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        publicationSend.add(new Label("Publication"));
        TextArea pub = new TextArea();
        pub.setUIID("TextField");
        pub.setHint("écrire içi ...");
        pub.setRows(3);
        pub.setGrowByContent(true);
        Button addPub = new Button("Publier");
        Label sep = new Label("\u0000") {
                public void paint(Graphics g) {
                    super.paint(g);
                    g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
                }
        };
        
        //addPub.addActionListener(this::ajouterPublication);
        addPub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                journalService.ajouterPublication(pub.getText());
                rootContainer.removeAll();
                changec.removeAll();
                initialize();
            }
        });
        
        publicationSend.add(pub);
        publicationSend.add(addPub);
        publicationSend.add(sep);
        
        //c.add(publicationSend);
        changec.add(publicationSend);
        //-------------
        for (Publication p:pubs)
        {
            Container pubCont = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            
            Container pubEntete = new Container(new BoxLayout(BoxLayout.X_AXIS));
            
            Image img = utilService.getImageProfilFromURL(u.getImage_id());
            pubEntete.add(img);
            
            

            Label np = new Label(p.getIdUser().getNom()+" "+p.getIdUser().getPrenom());
            np.getUnselectedStyle().setFont(boldMedium);
            SpanLabel contenu = new SpanLabel(p.getContenu());
            contenu.getUnselectedStyle().setFont(normalSmall);
            
            Container upDel = new Container(new BoxLayout(BoxLayout.X_AXIS));
            //----
            Button update = new Button("Modifier");
            update.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Dialog d = new Dialog("Modifier publication");
                    TextArea popupBody = new TextArea(p.getContenu(), 3, 10);
                    popupBody.setUIID("PopupBody");
                    popupBody.setEditable(true);
                    d.setLayout(new BorderLayout());
                    d.add(BorderLayout.NORTH, popupBody);
                    Container butts = new Container(new BoxLayout(BoxLayout.X_AXIS));
                    Button valider = new Button("Valider");
                    Button annuler = new Button("Annuler");
                    
                    valider.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            //System.out.println(p.getId()+" - "+popupBody.getText());
                            journalService.modifierPublication(p.getId().toString(), popupBody.getText());
                            d.dispose();                            
                            contenu.setText(popupBody.getText());
                            contenu.animateLayout(1000);
                            changec.revalidate();
                            
                        }
                    });
                    annuler.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {                            
                            d.dispose();
                        }
                    });
                    butts.add(valider).add(annuler);
                    d.add(BorderLayout.SOUTH, butts);
                    //d.showPopupDialog(update);
                    d.show();
                }
            });
            //-----
            Button delete = new Button("Supprimer");
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (Dialog.show("Supprimer Publication", "Voulez-vous supprimer cette publication ?", "Oui", "Non")){
                        journalService.supprimerPublication(p.getId().toString());
                        changec.removeComponent(pubCont);
                        changec.revalidate();
                    }
                }
            });
            //----
            upDel.add(update);
            upDel.add(delete);
                        
            Container npDate = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            SimpleDateFormat formater = new SimpleDateFormat("EEEE, d MMM yyyy hh:mm");
            Label datePub = new Label(formater.format(p.getDatePublication()));
            datePub.getUnselectedStyle().setFont(normalSmall);
            npDate.add(np).add(datePub);
            pubEntete.add(npDate);
            
            
            pubCont.add(pubEntete);                      
            pubCont.add(contenu);
            pubCont.add(upDel);
            
            Label l = new Label("\u0000") {
                public void paint(Graphics g) {
                    super.paint(g);
                    g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
                }
            };
            pubCont.add(l);
            
            //c.add(pubCont);
            changec.add(pubCont);
            
            //c.add(changec);
            /*c.removeAll();
            c.add(entete);
            c.add(journal);
            c.add(apropos);
            c.add(album);
            c.add(sugg);
            c.add(changec);*/
        }
        //-------------
        this.rootContainer.removeAll();
        this.rootContainer.add(BorderLayout.NORTH, changec);
        this.rootContainer.revalidate();
    }
    
    public void ajouterPublication(ActionEvent evt) {
        Button x = (Button) evt.getSource();        
        
        journalService.ajouterPublication(x.getName());
        this.rootContainer.removeAll();
        this.rootContainer.add(BorderLayout.NORTH, changec);
        this.rootContainer.revalidate();
    }
}
