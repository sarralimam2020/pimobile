/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.codename1.capture.Capture;
import com.codename1.main.Controller;
import com.codename1.main.MainView;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;



/**
 *
 * @author ASUS
 */
public class CodeController extends Controller {
        private Image img;
    private String imgPath;


private Resources theme;

   
    public CodeController  ()
    {
        super();
         
    }
    

    @Override
    public void initialize()  {

 
        
        this.rootContainer.removeAll();
        this.rootContainer.setUIID("ForgetCode");
        this.theme = theme;
        theme = UIManager.initFirstTheme("/theme");
       

       Container north = new Container(new FlowLayout(Component.CENTER));
       
        
         this.rootContainer.add(BorderLayout.NORTH, north);
  
         

         
        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS)); 
        center.setUIID("SignUpCenter");
        Container row1 = new Container(new GridLayout(1,2));
        TextField code = new TextField();
        code.setHint("code");
        row1.addComponent(code);
         center.addComponent(row1);
         center.setScrollableY(true);
       

 Container row4 = new Container(new BorderLayout()); 

  this.rootContainer.add(BorderLayout.CENTER, center);
  
  Button getstarted = new Button("Confirm" , theme.getImage("entrer.png"));
  getstarted.setUIID("SignUpButton");
  getstarted.setGap(getstarted.getStyle().getFont().getHeight());
  getstarted.setTextPosition(Component.LEFT);
  this.rootContainer.add(BorderLayout.SOUTH, getstarted);
  
  
  getstarted.addActionListener(new ActionListener() {
        Form mainForm = new Form();
       
            @Override
            public void actionPerformed(ActionEvent evt) {
               
                  System.out.println(MainView.Codex);
                 
                if (code.getText().equals(MainView.Codex)){
                MainView forumController = new MainView();
                forumController.initMainView();

                }
                else{
              //      Dialog.show("Bad Code " , "OK", null);
                }
    
            }
          
        });
  
    }
    
}
