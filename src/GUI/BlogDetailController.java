/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Blog;
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
import service.BlogService;
import service.EvenementService;

/**
 *
 * @author hero
 */
public class BlogDetailController extends Controller {

    public BlogDetailController()
    {
        super();
    }
    EncodedImage enc ;
    private Resources theme;
    private Container ca ;
    private Container cc ;
    private Container cd;
    private Container cx;
    private Label titre;
    private SpanLabel description;
    private Label titreCordination;
    private Label nbplaces;
    private Label nbcommantaire;
    private  Form f;
    private Button back;
    private Button commantaire;
 
        public void initialize(Blog e) throws IOException {
        try {
            f = new Form(new BoxLayout(BoxLayout.Y_AXIS));
            f.getToolbar().setHidden(true);
            theme = UIManager.initFirstTheme("/theme");           
            titre = new Label();     
                            

            enc = EncodedImage.create("/giphy.gif");
            BlogService serviceTask=new BlogService();
            List<Blog> lis = serviceTask.getList2(e.getId());
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            for(Blog ev : lis){                
                cc = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                ca = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                cd = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                cx = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                titre= new Label();
                description = new SpanLabel();
                titreCordination = new Label();
                nbplaces = new Label();
                titre.setText("Titre :");
                titre.getAllStyles().setUnderline(true);
                Label tt = new Label(e.getTitle());
                Container xx = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                xx.add(titre).add(tt);               
                Image image = URLImage.createToStorage(enc,e.getImage(),Controller.ip+"/Pidev-dev/web/uploads/"+ev.getImage());                               
                image=image.scaled(200, 200);
                description.setText("Description : "+ev.getContent());
                titreCordination.setText("Place&Date : "+ev.getTitle());                
                nbplaces.setText(String.valueOf("LikesNumber : "+ev.getLikesnumber()));
                commantaire=new Button("Commantaires");
                commantaire.addActionListener((evt) -> {
               CommentaireController CommentaireController = new CommentaireController();
               CommentaireController.initialize(ev);
                this.rootContainer.removeAll();
                this.rootContainer.add(BorderLayout.CENTER,CommentaireController.getView());
                this.rootContainer.revalidate();
                });;
                ca.add(xx);
                cd.add(image);               
                cc.add(description);
                cc.add(titreCordination);
                cx.addAll(new Label(format.format(ev.getDateCreation())),nbplaces); 
                f.add(ca); 
                f.add(cd); 
                f.add(cc);   
                f.add(cx); 
                f.add(commantaire);
                }       
                this.rootContainer.add(BorderLayout.CENTER,f);
        } catch (IOException ex) {
        }        
        }    

    @Override
    public void initialize() {
    }
}
