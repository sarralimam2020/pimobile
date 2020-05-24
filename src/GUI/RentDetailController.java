/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Contract;
import Entity.Rent;
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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import service.RentService;

/**
 *
 * @author hero
 */
public class RentDetailController extends Controller {

    public RentDetailController()
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
    private TextField num;
 
        public void initialize(Rent e) throws IOException {
        try {
            Contract contr = new Contract();
            User u = Session.ConnectedUser;
            f = new Form();
            f.getToolbar().setHidden(true);
            theme = UIManager.initFirstTheme("/theme");           
            titre = new Label();
            num = new TextField("","Your Number :");
            
            enc = EncodedImage.create("/giphy.gif");
            RentService serviceTask=new RentService();
            List<Rent> lis = serviceTask.getList2(e.getId());
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            for(Rent ev : lis){                
                cc = new Container();
                //cd = new Container();
                titre= new Label();
                description = new SpanLabel();
                titreCordination = new Label();
                nbplaces = new Label();
                Picker datePicker = new Picker();
                datePicker.setType(Display.PICKER_TYPE_DATE);
                Picker datePicker2 = new Picker();
                datePicker.setType(Display.PICKER_TYPE_DATE);


                
                mail = new SpanLabel();
                mail.setText("Donner votre avis par mail à l'admin :");
                send=new Button("Rent");
                send.addActionListener(s->{   
                System.out.println("=======================================================");
                System.out.println("=======================================================");
                System.out.println("=======================================================");
                System.out.println("=======================================================");
                System.out.println("=======================================================");
                System.out.println("=======================================================");
                RentService RentService = new RentService();
                String debuitt=(new SimpleDateFormat("yyyy-MM-dd")).format(datePicker.getDate());
               String finn=(new SimpleDateFormat("yyyy-MM-dd")).format(datePicker2.getDate());
                RentService.addrent(debuitt,finn,num.getText());
                
                });
                titre.setText("Titre :");
                titre.getAllStyles().setUnderline(true);
                Label tt = new Label(e.getMarque());
                Container xx = new Container(new BoxLayout(BoxLayout.X_AXIS));
                xx.add(titre).add(tt);               
                Image image = URLImage.createToStorage(enc,e.getImageId(),Controller.ip+"/Pidev-web/web/uploads/"+ev.getImageId());                               
                image=image.scaled(200, 200);
                description.setText("Marque : "+ev.getMarque());
                titreCordination.setText("Matricuile : "+ev.getMatricule());                
                nbplaces.setText(String.valueOf("Puissance : "+ev.getPuissance()));

                cc.add(xx);
                cc.add(image);               
                cc.add(description);
                cc.add(titreCordination);
                cc.add(datePicker);
                cc.add(datePicker2);
                cc.add(mail);
                cc.add(send);
                cc.add(num);
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
