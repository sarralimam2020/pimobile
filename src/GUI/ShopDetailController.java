/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Panier;
import Entity.Produict;
import Entity.User;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.main.Controller;
import com.codename1.main.MainView;
import com.codename1.main.Session;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import service.ShopService;

/**
 *
 * @author hero
 */
public class ShopDetailController extends Controller {

    public ShopDetailController()
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
    private TextArea qantity;
 
        public void initialize(Produict e) throws IOException {
        try {
            Panier panier = new Panier();
            User u = Session.ConnectedUser;
            f = new Form(new BoxLayout(BoxLayout.Y_AXIS));
            f.getToolbar().setHidden(true);
            theme = UIManager.initFirstTheme("/theme");           
            titre = new Label();     
            
            enc = EncodedImage.create("/giphy.gif");
            ShopService serviceTask=new ShopService();
            List<Produict> lis = serviceTask.getList2(e.getId());
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            for(Produict ev : lis){                
                cc = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                //cd = new Container();
                titre= new Label();
                description = new SpanLabel();
                titreCordination = new Label();
                nbplaces = new Label();
                qantity = new TextField("","Qantity Disponbile");
               
                
                send=new Button("Add To Panier");
                send.addActionListener(s->{   
                System.out.println("=======================================================");
                System.out.println("=======================================================");
                System.out.println("=======================================================");
                System.out.println("=======================================================");
                System.out.println("=======================================================");
                System.out.println("=======================================================");
                // new instance of  service project 
                ShopService ShopService = new ShopService();
                
                String prixx;
                String idpro;
                // jibdna valeur   prix wlil id produit 
                prixx = Integer.toString(ev.getPrix());
                idpro = Integer.toString(ev.getId());
                
                
                
                // 
                ShopService.addtopanier(u.getId(),idpro,qantity.getText(),prixx);
                System.out.println(u.getId() +"   "+idpro+"      "+qantity.getText()+"      "+prixx);
               // System.out.println( Name : "+ev.getNom()+ " Qantity : "+qantity.getText()+  "   Prix : " + ev.getPrix() + "Description   " + ev.getDescription() + " last : " + ev.getQuantity());
                });
                titre.setText("Titre :");
                titre.getAllStyles().setUnderline(true);
                Label tt = new Label(e.getNom());
                Container xx = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                xx.add(titre).add(tt);               
                Image image = URLImage.createToStorage(enc,e.getImageId(),Controller.ip+"/Pidev-web/web/uploads/"+ev.getImageId());                               
                image=image.scaled(200, 200);
                description.setText("Description : "+ev.getDescription());
                titreCordination.setText("Qantity : "+ev.getQuantity());                
                nbplaces.setText(String.valueOf("Date : "+ev.getDate()));

                cc.add(xx);
                cc.add(image);               
                cc.add(description);
                cc.add(titreCordination);
                cc.add(qantity);
                cc.addAll(new Label(format.format(ev.getDate())),nbplaces);
                
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
