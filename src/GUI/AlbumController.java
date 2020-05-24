/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Album;
import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.io.Storage;
import com.codename1.io.URL;
import com.codename1.io.Util;
import com.codename1.main.Controller;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import service.AlbumService;
import service.UtilService;

/**
 *
 * @author Nacef
 */
public class AlbumController extends Controller {
    
    private Resources theme;
    AlbumService albumService = new AlbumService();
    UtilService utilService = UtilService.getInstance();
    
    public AlbumController()
    {
        super();
    }

    @Override
    public void initialize() {
        theme = UIManager.initFirstTheme("/theme"); 
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
        Container center = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Container butts = new Container(new BoxLayout(BoxLayout.X_AXIS));
        //---------------
        Button addPhotoCamera = new Button(theme.getImage("addcamera.png"));
        addPhotoCamera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Form cimg = new Form(new BoxLayout(BoxLayout.Y_AXIS));
                Container imagec = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Label capturedPhoto = new Label();
                String i = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                if(i != null){
                    try {
                        Image img = Image.createImage(i);                        
                        img = img.scaled(Math.round(Display.getInstance().getDisplayWidth()-40), Math.round(Display.getInstance().getDisplayHeight()-40));
                        imagec.add(img);
                    } catch (IOException ex) {                        
                    }                    
                }
                cimg.add(imagec);
                Button validerPhoto = new Button("Ajouter");
                validerPhoto.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        String DBPhotoName = albumService.uploadPhoto(i);                        
                        albumService.ajouterPhoto(DBPhotoName);
                        
                        Form f = rootContainer.getComponentForm();
                        f.show();
                        rootContainer.removeAll();
                        c.removeAll();
                        initialize();
                    }
                });
                Button annulerPhoto = new Button("Annuler");
                annulerPhoto.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Form f = rootContainer.getComponentForm();
                        f.show();
                    }
                });
                
                Container photoButtons = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
                Container buttsPhoto = new Container(new BoxLayout(BoxLayout.X_AXIS));
                buttsPhoto.add(validerPhoto).add(annulerPhoto);
                photoButtons.add(BorderLayout.CENTER, buttsPhoto);
                cimg.add(photoButtons);
                cimg.show();
            }
        });

        //----------------
        Button addPhotoGallerie = new Button(theme.getImage("addgallery.png"));
        addPhotoGallerie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {               
                Display.getInstance().openImageGallery(new ActionListener() {
                     public void actionPerformed(ActionEvent ev) {
                        Form cimg = new Form(new BoxLayout(BoxLayout.Y_AXIS));
                        Container imagec = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        Label capturedPhoto = new Label();
                        String i = (String) ev.getSource();
                        if(i != null){
                            try {
                                
                                Image img = Image.createImage(i);                        
                                img = img.scaled(Math.round(Display.getInstance().getDisplayWidth()-40), Math.round(Display.getInstance().getDisplayHeight()-40));
                                imagec.add(img);
                            } catch (IOException ex) {                        
                            }                    
                        }
                        cimg.add(imagec);
                        Button validerPhoto = new Button("Ajouter");
                        validerPhoto.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                String DBPhotoName = albumService.uploadPhoto(i);                        
                                albumService.ajouterPhoto(DBPhotoName);
                                Form f = rootContainer.getComponentForm();
                                f.show();
                                rootContainer.removeAll();
                                c.removeAll();
                                initialize();
                            }
                        });
                        Button annulerPhoto = new Button("Annuler");
                        annulerPhoto.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                Form f = rootContainer.getComponentForm();
                                f.show();
                            }
                        });

                        Container photoButtons = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
                        Container buttsPhoto = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        buttsPhoto.add(validerPhoto).add(annulerPhoto);
                        photoButtons.add(BorderLayout.CENTER, buttsPhoto);
                        cimg.add(photoButtons);
                        cimg.show();
                     }
                });
            }
        });
        butts.add(addPhotoCamera).add(addPhotoGallerie);
        center.add(BorderLayout.CENTER,butts);
        c.add(center);
        //-------------------
        Container album = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        ArrayList<Album> albums = albumService.getAlbums();
        for(Album loi:albums){
            Image img = utilService.getImageAlbumFromURL(loi.getUrl());
            //ImageViewer iv = new ImageViewer(img);            
            Container x = new Container();
            //x.add(img);
            Label l = new Label(img);
            l.putClientProperty("imageId", loi.getId().toString());
            l.addPointerReleasedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if(Dialog.show("Suppression !", "Voulez-vous supprimer cetter photo ?", "Oui", "Non")){
                        albumService.supprimerPhoto(loi.getId().toString());
                        x.removeComponent(l);
                        x.revalidate();
                    }
                    
                }
            });
            x.add(l);
            x.setLeadComponent(l);
            album.add(x);            
        }
        Container albBL = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        albBL.add(BorderLayout.CENTER, album);
        c.add(albBL);
        //-------------------
        this.rootContainer.removeAll();
        this.rootContainer.add(BorderLayout.NORTH, c);
        this.rootContainer.revalidate();
    }
    
    
}
