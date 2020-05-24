/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.User;
import com.codename1.capture.Capture;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import static com.codename1.io.Log.p;
import com.codename1.main.Controller;
import com.codename1.main.Session;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.spinner.Picker;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import service.ParamsProfilService;
import service.UtilService;

/**
 *
 * @author Nacef
 */
public class ParamsProfil extends Controller {
    
    ParamsProfilService paramsProfilService = new ParamsProfilService();
    UtilService utilService = UtilService.getInstance();
    
    
    public ParamsProfil()
    {
        super();
    }
    
    public boolean isValidInput(String input){
        if(input.contains("a") || input.contains("b") || input.contains("c") 
        || input.contains("d") || input.contains("e") || input.contains("f")
        || input.contains("g") || input.contains("h") || input.contains("i")
        || input.contains("j") || input.contains("k") || input.contains("l")
        || input.contains("m") || input.contains("n") || input.contains("o")
        || input.contains("p") || input.contains("q") || input.contains("r")
        || input.contains("s") || input.contains("t") || input.contains("u")
        || input.contains("v") || input.contains("w") || input.contains("x")
        || input.contains("y") || input.contains("z")) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void initialize() {
        Session session = Session.getInstance();        
        User u = Session.ConnectedUser;
        
        Font normalSmall = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        Font normalMedium = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        Font boldSmall = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
        Font boldMedium = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Font boldLarge = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE);
        
        User user = u;
        Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        c.setScrollableY(true);
        //----------
        Label l1 = new Label("Parametres de profil"){
            public void paint(Graphics g) {
                super.paint(g);
                g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
            }
        }; 
        l1.getUnselectedStyle().setFont(boldLarge);
        l1.getAllStyles().setUnderline(true);
        c.add(l1);
        //--------        
        Container c1 = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Container cbuttons = new Container(new BoxLayout(BoxLayout.X_AXIS));
        //-----
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
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Form cimg = new Form(new BoxLayout(BoxLayout.Y_AXIS));
                        Container imagec = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        Label capturedPhoto = new Label();
                        String i = (String) evt.getSource();
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
        cbuttons.add(addPhotoCamera).add(addPhotoGallerie);
        //-----
        Container c0 = new Container(new BoxLayout(BoxLayout.Y_AXIS));                
        Image img = utilService.getImageAlbumFromURL(u.getImage_id());
        Container x = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        x.add(BorderLayout.CENTER, cbuttons);
        c0.add(img).add(x);
        c1.add(BorderLayout.CENTER, c0);
        //--------
        Container c2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        c2.add(new Label("Nom"));
        TextField nom = new TextField();
        nom.setHint("Nom");
        nom.setText(u.getNom());
        c2.add(nom);
        //--------
        Container c3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        c3.add(new Label("Prénom"));
        TextField prenom = new TextField();
        prenom.setHint("Prénom");
        prenom.setText(u.getPrenom());
        c3.add(prenom);
        //--------
       
        //--------
        
        //sexe.addActionListener(e -> ToastBar.showMessage("You picked " + sexe.getSelectedString(), FontImage.MATERIAL_INFO));
        
        //--------                
        Container c6 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        c6.add(new Label("Tel."));
        TextField tel = new TextField(u.getPhone(), "Numéro téléphone", 20, TextArea.PHONENUMBER);
        tel.addDataChangedListener((i, ii) -> {
            if(isValidInput(tel.getText())) {
               tel.putClientProperty("LastValid", tel.getText());
            } else {
                ToastBar.showMessage("Saisir un numéro valid", FontImage.MATERIAL_INFO);
                tel.stopEditing();
                tel.setText((String)tel.getClientProperty("LastValid"));
                tel.startEditingAsync();
            }
        });
        c6.add(tel);
        //--------
       
        //--------
//        
//        Container c10 = new Container(new BoxLayout(BoxLayout.X_AXIS));
//        c10.add(new Label("Pays"));
//        String[] paysvalues = paramsProfilService.getPays().toArray(new String[0]);
//        Picker pays = new Picker();
//        pays.setStrings(paysvalues);
//        //String p = u.getPays();
//        
//        int index1 = Arrays.asList(paysvalues).indexOf(u.getPays());
//        
//        pays.setSelectedString(paysvalues[index1]);
//        c10.add(pays);
//        //--------
//        Container c11 = new Container(new BoxLayout(BoxLayout.X_AXIS));
//        c11.add(new Label("Ville"));
//        
//        String[] villesvalues = paramsProfilService.getVille().toArray(new String[0]);
//        Picker villes = new Picker();
//        villes.setStrings(villesvalues);
//        //String p = u.getPays();
//        
//        int index2 = Arrays.asList(villesvalues).indexOf(u.getVille());
//        
//        villes.setSelectedString(villesvalues[index2]);
//        c11.add(villes);
        //--------
       
        //-------
        Button valider = new Button("Enregistrer");
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                user.setNom(nom.getText());
                user.setPrenom(prenom.getText());
               
                //user.setPays();
                //user.setVille();
                //user.setRegion();
                paramsProfilService.modifierUser(user);
                rootContainer.removeAll();
                c.removeAll();
                initialize();
            }
        });
        //-------
        c.add(c1).add(c2).add(c3).add(c6).add(valider);
        //-------------------------
        this.rootContainer.removeAll();
        this.rootContainer.add(BorderLayout.NORTH, c);
        this.rootContainer.revalidate();
    }
    
    private Map<String, String> createListEntry(String name, String value) {
        Map<String, String> entry = new HashMap<>();
        entry.put(value, name);
        return entry;
    }
}
