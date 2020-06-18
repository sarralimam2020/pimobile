/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Panier;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.main.Controller;
import com.codename1.main.MainView;
import com.codename1.main.Session;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
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
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import service.PanierService;

/**
 *
 * @author hero
 */
public class PanierController extends Controller {

    public PanierController()
    {
        super();
    }
    EncodedImage enc ;
    private Resources theme;
    private Container cc ;
    private Container cx ;
    private Label titre;
    private Form f;
    private Button Valider;
    private Button deleteall;
    private Button Stat;
    private SpanLabel decouvrir;
    private SpanLabel ssssss;
    private Label titleeee;
    private SpanLabel prix;
        
    @Override
    public void initialize() {
        this.rootContainer.removeAll();
        try {
            SpanLabel lb;
            theme = UIManager.initFirstTheme("/theme");
            titre = new Label();
            PanierService serviceTask=new PanierService();
            ArrayList<Panier> lis=serviceTask.getList();
            Container parentContainer = new Container(BoxLayout.y());
            parentContainer.setScrollableY(true);
            enc = EncodedImage.create("/giphy.gif");            
            decouvrir = new SpanLabel();
            decouvrir.setText("Découvrir les évenements qui ont plus des participants :");
            Stat = new Button("Découvrir");
            ssssss = new SpanLabel();
            titleeee = new Label();
              cx = new Container();

            for (Panier e : lis)
            {                
                cc = new Container();
                titre= new Label();
                prix = new SpanLabel();
                Valider = new Button("Valider");
            deleteall = new Button("Vider Panier");
          
                prix.setText("Qantity & prix : "+e.getPrix() + e.getQuantite());

                titre.setText("Produict :");
                
           
                titre.getAllStyles().setUnderline(true); 
                Container xx = new Container(new BoxLayout(BoxLayout.X_AXIS));
                xx.add(titre);
                cc.add(xx);
                cc.add(prix);
                
                parentContainer.add(cc);                                                              
            }      
            cx.add(Valider);
            Valider.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                    ConnectionRequest con = new ConnectionRequest();
                 con.setUrl("http://localhost/Pidev-web/web/app_dev.php/api/mobile/order?userId="+Session.ConnectedUser.getId());
                 NetworkManager.getInstance().addToQueueAndWait(con);
                    
                }
            });
            
                cx.add(deleteall);
           parentContainer.add(cx);
        this.rootContainer.add(BorderLayout.CENTER,parentContainer);   
        } catch (IOException ex) {
        }        
        this.rootContainer.revalidate();       
    }    
}
