/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.codename1.main.Controller;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Nacef
 */
public class RechercheController extends Controller{

    private Container rechercheContainer;
    
    @Override
    public void initialize() {
    }
    
    private void rechercheView()
    {
        rechercheContainer = new Container(BoxLayout.y());
        
        this.rootContainer.removeAll();
        this.rootContainer.add(rechercheContainer);
        this.rootContainer.revalidate();
    }
    
    private AutoCompleteTextField occupationField()
    {
        AutoCompleteTextField autoComplete = new AutoCompleteTextField();
        return autoComplete;
    }
    
}
