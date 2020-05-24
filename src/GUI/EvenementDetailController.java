/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Evenement;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.main.Controller;
import com.codename1.main.MainView;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import service.EvenementService;

/**
 *
 * @author hero
 */
public class EvenementDetailController extends Controller {

    public EvenementDetailController()
    {
        super();
    }
    EncodedImage enc ;
    private Resources theme;
    private Container cc ;
    private Container cd;
    private Label titre;
    private SpanLabel description;
    private Label titreCordination;
    private Label nbplaces;
    private  Form f;
    private Button participer;
    private Button send;
    private SpanLabel mail;
    private Button back;
 
        public void initialize(Evenement e) throws IOException {
        try {
            f = new Form();
            f.getToolbar().setHidden(true);
            theme = UIManager.initFirstTheme("/theme");           
            titre = new Label();     
            
            enc = EncodedImage.create("/giphy.gif");
            EvenementService serviceTask=new EvenementService();
            List<Evenement> lis = serviceTask.getList2(e.getId());
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            for(Evenement ev : lis){                
                cc = new Container();
                //cd = new Container();
                titre= new Label();
                description = new SpanLabel();
                titreCordination = new Label();
                nbplaces = new Label();
                boolean b = serviceTask.checkParticipation(e.getId());
                participer=new Button("participer");
                if(!b) participer.setEnabled(false);
                participer.addActionListener((evt) -> {
                if (EvenementService.partEvent(e)) {
                    ev.setNbplaces(ev.getNbplaces()-1);
                    nbplaces.setText(String.valueOf("Number of places : "+ev.getNbplaces()));
                    f.show();
                    serviceTask.participer(e.getId());
                    participer.setEnabled(false);
                    Dialog.show("Great!","you are now participated","ok",null);
                }   else {
                    Dialog.show("Add failed", "", "ok", null);
                }                   
                }); 
                
                mail = new SpanLabel();
                mail.setText("Donner votre avis par mail à l'admin :");
                send=new Button("Envoyer Mail");
                send.addActionListener(s->{               
                Message m = new Message("Sur l'evenement : "+ev.getTitre()+" : ");
                Display.getInstance().sendMessage(new String[]{"soumaya.snoussi@esprit.tn"}, "evenement", m);   
                });
                titre.setText("Titre :");
                titre.getAllStyles().setUnderline(true);
                Label tt = new Label(e.getTitre());
                Container xx = new Container(new BoxLayout(BoxLayout.X_AXIS));
                xx.add(titre).add(tt);               
                Image image = URLImage.createToStorage(enc,e.getImageEve(),Controller.ip+"/Pidev-Desktop/src/Images/"+ev.getImageEve());                               
                image=image.scaled(200, 200);
                description.setText("Description : "+ev.getDescription());
                titreCordination.setText("Place&Date : "+ev.getTitreCordination());                
                nbplaces.setText(String.valueOf("Number of places : "+ev.getNbplaces()));
                cc.add(xx);
                cc.add(image);               
                cc.add(description);
                cc.add(titreCordination);
                cc.addAll(new Label(format.format(ev.getDateEvenement())),nbplaces);            
                cc.add(participer);
                cc.add(mail);
                cc.add(send);
                f.add(cc);               
                }       
                this.rootContainer.add(BorderLayout.CENTER,f);
        } catch (IOException ex) {
        }        
        }    

    @Override
    public void initialize() {
    }
}
