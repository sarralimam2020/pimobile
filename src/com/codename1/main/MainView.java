package com.codename1.main;

import APIs.GPSApi;
import Entity.User;
import Service.loginServices;
import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import com.codename1.io.URL;
import com.codename1.io.URL.HttpURLConnection;


import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.util.EasyThread;
import com.twilio.Twilio;
import static com.twilio.example.Example.ACCOUNT_SID;
import static com.twilio.example.Example.AUTH_TOKEN;
import GUI.Beblio;
import GUI.BlogController;
import GUI.CodeController;
import GUI.SignUpController;
import GUI.ProfilController;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;
import com.twilio.type.PhoneNumber;
import GUI.EvenementController;
import GUI.PanierController;
import GUI.ParamsProfil;
import GUI.RentController;
import GUI.ShopController;
import GUI.ajouter_compteController;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import service.ParamsProfilService;
import service.UtilService;


public class MainView {
    UtilService utilService = UtilService.getInstance();
    public static String Codex;
    private Form current;
    private Resources theme;
    private Form f;
    private TextField username;
    private TextField password;
    private Button connecter;
    private Button SignUp;
    private Button Forget_Password;
     public static final String ACCOUNT_SID = "ACf4e25d70d44fd6dfc3d24159bb86e41b";
    public static final String AUTH_TOKEN = "310db9bee2c145adcd9ef824959af7fb";
    
    
    
    int i = 0;

    public void init(Object context) {        
        Toolbar.setGlobalToolbar(true);
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");
        Controller.theme = theme;
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if (err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        theme = UIManager.initFirstTheme("/theme_1");
        f = new Form();

        f.getTitleArea().setUIID("Container");
        f.setUIID("SignIn");

        f.add(new Label(theme.getImage("Logo.png"), "LogoLabel"));

        TextField username = new TextField("", "Username", 20, TextField.ANY);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        username.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");
        Button forget_password = new Button("Forgot password ?");
        signUp.setUIID("Link");
        Label doneHaveAnAccount = new Label("Don't have an account?");
        Button signUp1 = new Button("Sign Up Vie Web");
        Container content = BoxLayout.encloseY(
                new FloatingHint(username),
                new BaseForm().createLineSeparator(),
                new FloatingHint(password),
                new BaseForm().createLineSeparator(),
                signIn,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp, forget_password, signUp1)
        );

        signUp1.addActionListener(e -> {
            WebDriver driver = new ChromeDriver();
            driver.get(Controller.ip+"/Pidev-web/web/app_dev.php/api/login");
        });

        forget_password.addActionListener(e -> {
            forgotPassword();
        });


        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                loginServices ser =new loginServices();
              
                
                ser.login(username.getText(), password.getText());
                if (Session.ConnectedUser.getId()>0) {
                  
                    initMainView();
                } else {
                    Dialog.show("Error!", "Login ou mot de passe incorrect!", "Ok", null);
                }
            }
        });

        signUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Form mainForm = new Form();
                mainForm.setLayout(new BorderLayout());
                mainForm.getToolbar().setHidden(true);
                mainForm.getContentPane().removeAll();
                SignUpController forumController = new SignUpController();
                forumController.initialize();
                mainForm.addComponent(BorderLayout.CENTER, forumController.getView());
                mainForm.revalidate();
                mainForm.show();
      
            }
        });

        content.setScrollableY(true);
        f.add(content);
        signIn.requestFocus();
        signIn.addActionListener(e -> {
        });
        f.revalidate();
        f.show();
    }

    public void forgotPassword() {

        theme = UIManager.initFirstTheme("/theme_1");
        f = new Form();

        f.getTitleArea().setUIID("Container");
        f.setUIID("SignIn");

        f.add(new Label(theme.getImage("Logo.png"), "LogoLabel"));

        TextField phone = new TextField("", "Phone", 20, TextField.ANY);
        phone.setSingleLineTextArea(false);

        Button signIn = new Button("Sign In");

        Container content = BoxLayout.encloseY(
                new FloatingHint(phone),
                new BaseForm().createLineSeparator(),
                new BaseForm().createLineSeparator(),
                signIn,
                FlowLayout.encloseCenter()
        );
        f.add(content);

        signIn.addActionListener(e -> {
            if (phone.getText().isEmpty()) {
                Dialog.show("Error", "Phone est vide ", "Ok", null);

            } else {
                Beblio.setMail(phone.getText());
                code();
                Form taz = new Form();
                taz.setLayout(new BorderLayout());
                taz.getToolbar().setHidden(true);
                taz.getContentPane().removeAll();
                CodeController forumController = new CodeController();
                forumController.initialize();
                taz.addComponent(BorderLayout.CENTER, forumController.getView());
                taz.revalidate();
                taz.show();

            }
        });
        f.show();

    }

    public void initMainView() {
        //----------------------------------------------------------------------------------------
      
        User user = Session.ConnectedUser;
        ParamsProfilService paramsProfilService = new ParamsProfilService();
        //----------------------------------------------------------------------------------------
        theme = UIManager.initFirstTheme("/theme");
        Form mainForm = new Form();
        mainForm.setLayout(new BorderLayout());
       
        final Command profileCommand = new Command("Mon Profil") {

            public void actionPerformed(ActionEvent evt) {
                
                
                mainForm.getContentPane().removeAll();
                ProfilController profilController = new ProfilController();
                profilController.initialize();
                mainForm.addComponent(BorderLayout.CENTER, profilController.getView());
                mainForm.revalidate();
            }
        };

        Image img = utilService.getImageProfilFromURL(user.getImage_id());
        mainForm.addCommand(new Command("aa", img));
        mainForm.addCommand(profileCommand);
        Command c = new Command("Modules");
        Label l = new Label("Acceder à") {

            public void paint(Graphics g) {
                super.paint(g);
                g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
            }
        };
        l.setUIID("Separator");
        c.putClientProperty("SideComponent", l);
        mainForm.addCommand(c);

        mainForm.addCommand(new Command("Rent", theme.getImage("all friends.png")) {

            public void actionPerformed(ActionEvent evt) {
                mainForm.getContentPane().removeAll();
                RentController RentController = new RentController();
                RentController.initialize();
                mainForm.addComponent(BorderLayout.CENTER, RentController.getView());
                mainForm.revalidate();
            }
        });

        mainForm.addCommand(new Command("Shop", theme.getImage("friend feeds.png")) {
            public void actionPerformed(ActionEvent evt) {
                mainForm.getContentPane().removeAll();
                ShopController ShopController = new ShopController();
                ShopController.initialize();
                mainForm.addComponent(BorderLayout.CENTER, ShopController.getView());
                mainForm.revalidate();
                
            }
        });

        mainForm.addCommand(new Command("Blogs", theme.getImage("photos icon.png")) {

             public void actionPerformed(ActionEvent evt) {
                mainForm.getContentPane().removeAll();
                 BlogController blogController = new BlogController();
                blogController.initialize();
                mainForm.addComponent(BorderLayout.CENTER, blogController.getView());
                mainForm.revalidate();
            }
        });

        mainForm.addCommand(new Command("Evenement", theme.getImage("wall post.png")) {

            public void actionPerformed(ActionEvent evt) {
                mainForm.getContentPane().removeAll();
                EvenementController evenementController = new EvenementController();
                evenementController.initialize();
                mainForm.addComponent(BorderLayout.CENTER, evenementController.getView());
                mainForm.revalidate();
            }
        });

        mainForm.addCommand(new Command("Panier", theme.getImage("wall post.png")) {

            public void actionPerformed(ActionEvent evt) {
               mainForm.getContentPane().removeAll();
                PanierController PanierController = new PanierController();
                PanierController.initialize();
                mainForm.addComponent(BorderLayout.CENTER, PanierController.getView());
                mainForm.revalidate();
            }
        });
        mainForm.addCommand(new Command("Maison d'hotes", theme.getImage("wall post.png")) {

            public void actionPerformed(ActionEvent evt) {
                mainForm.getContentPane().removeAll();
                mainForm.addComponent(BorderLayout.CENTER, showMyProfile());
                mainForm.revalidate();
            }
        });
                mainForm.addCommand(new Command("magazin", theme.getImage("wall post.png")) {

            public void actionPerformed(ActionEvent evt) {
                mainForm.getContentPane().removeAll();
                mainForm.addComponent(BorderLayout.CENTER, showMyProfile());
                mainForm.revalidate();
            }
        });
        Command c1 = new Command("ACTIONS");
        Label l1 = new Label("ACTIONS") {

            public void paint(Graphics g) {
                super.paint(g);
                g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
            }
        };
        l1.setUIID("Separator");
        c1.putClientProperty("SideComponent", l1);
        mainForm.addCommand(c1);

        mainForm.addCommand(new Command("Parametres de profil", theme.getImage("wall post.png")) {

            public void actionPerformed(ActionEvent evt) {
             mainForm.getContentPane().removeAll();
                ParamsProfil paramsProfilController = new ParamsProfil();
                paramsProfilController.initialize();
                mainForm.addComponent(BorderLayout.CENTER, paramsProfilController.getView());
                mainForm.revalidate();  
                System.out.println("hoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
            }
        });

        mainForm.addCommand(new Command("Logout") {
            

            public void actionPerformed(ActionEvent evt) {
                  start();
            }
        });

        mainForm.addCommand(new Command("Quitter") {

            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().exitApplication();
            }
        });

        mainForm.show();
    }

    public void stop() {
        current = getCurrentForm();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {

    }

    private Component showMyProfile() {
        final Container c = new Container(new BorderLayout());
        BorderLayout bl = new BorderLayout();
        bl.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        Container p = new Container(bl);
        p.addComponent(BorderLayout.CENTER, new InfiniteProgress());

        c.addComponent(BorderLayout.CENTER, p);

        return c;
    }

    public void code() {

        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        Beblio.setSaltStr(saltStr);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        com.twilio.rest.api.v2010.account.Message messages = com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber("+21658804719"),
                new PhoneNumber("+12057073191"), "Votre Code est : " + saltStr).create();
        Codex =saltStr;
        theme = UIManager.initFirstTheme("/theme_1");
        f = new Form();

        f.getTitleArea().setUIID("Container");
        f.setUIID("SignIn");

        f.add(new Label(theme.getImage("Logo.png"), "LogoLabel"));
        f.add(new Label("Nous Avons Envoyer un code a votre numero"));

        TextField email1 = new TextField("", "Code", 20, TextField.ANY);
        email1.setSingleLineTextArea(false);

        Button signIn1 = new Button("Envoyer !");

        Container content1 = BoxLayout.encloseY(
                new FloatingHint(email1),
                new BaseForm().createLineSeparator(),
                new BaseForm().createLineSeparator(),
                signIn1,
                FlowLayout.encloseCenter()
        );

        
            


        f.add(content1);
        f.show();
    }


}
