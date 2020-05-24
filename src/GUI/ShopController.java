/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Produict;
import com.codename1.components.SpanLabel;
import com.codename1.main.Controller;
import com.codename1.main.MainView;
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
import service.EvenementService;
import service.ShopService;

/**
 *
 * @author hero
 */
public class ShopController extends Controller {

    public ShopController()
    {
        super();
    }
    EncodedImage enc ;
    private Resources theme;
    private Container cc ;
    private Label titre;
    private Form f;
    private Button readmore;
    private Button Stat;
    private SpanLabel decouvrir;
        
    @Override
    public void initialize() {
        this.rootContainer.removeAll();
        try {
            SpanLabel lb;
            theme = UIManager.initFirstTheme("/theme");
            titre = new Label();
            ShopService serviceTask=new ShopService();
            ArrayList<Produict> lis=serviceTask.getList();
            Container parentContainer = new Container(BoxLayout.y());
            parentContainer.setScrollableY(true);
            enc = EncodedImage.create("/giphy.gif");            
            decouvrir = new SpanLabel();
            decouvrir.setText("Découvrir les évenements qui ont plus des participants :");
          
            parentContainer.add(decouvrir);
            for (Produict e : lis)
            {                
                cc = new Container();
                titre= new Label();
                
                Image image = URLImage.createToStorage(enc,e.getImageId(),Controller.ip+"/Pidev-web/web/uploads/"+e.getImageId());
                cc.add(image);
                readmore=new Button("Read More");
                readmore.addActionListener((evt) -> {
                ShopDetailController detailController = new ShopDetailController();
                try {
                    detailController.initialize(e);
                    this.rootContainer.removeAll();
                    this.rootContainer.add(BorderLayout.CENTER,detailController.getView());
                    this.rootContainer.revalidate();
                } catch (IOException ex) {
                ex.printStackTrace();
                }
                });
                titre.setText("Titre :");
                titre.getAllStyles().setUnderline(true);
                Label tt = new Label(e.getNom());
                Container xx = new Container(new BoxLayout(BoxLayout.X_AXIS));
                xx.add(titre).add(tt);
                cc.add(xx);
                cc.add(readmore);
                parentContainer.add(cc);                                                              
            }            
        this.rootContainer.add(BorderLayout.CENTER,parentContainer);   
        } catch (IOException ex) {
        }        
        this.rootContainer.revalidate();       
    }    
}
