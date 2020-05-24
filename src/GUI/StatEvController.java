/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author DJAZIA
 */

import Entity.Evenement;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.main.Controller;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import service.EvenementService;


/**
 *
 * @author radhwen
 */
public class StatEvController extends Controller{
    
 /**
 * Creates a renderer for the specified colors.
 */
    
    EvenementService ev =new EvenementService();
        ArrayList<Evenement> lis = ev.getList();
       
    public Form f;

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    private Button back;
    private DefaultRenderer buildEvenementRenderer(int[] colors) {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(30);
    renderer.setLegendTextSize(30);
    renderer.setMargins(new int[]{20, 30, 15, 0});
    for (int color : colors) {
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(color);
        renderer.addSeriesRenderer(r);
    }
    return renderer;
    }

/**
 * Builds a category series using the provided values.
 *
 * @param titles the series titles
 * @param values the values
 * @return the category series
 */
    protected CategorySeries buildEvenementDataset() {
    CategorySeries series = new CategorySeries("Stataistique des Evenements");
    int k = 0;   
    for (int i = 0; i < lis.size(); i++) {
                           
    series.add(lis.get(i).getTitre(), lis.get(i).getNbplaces());
   
    }
    return series;
    }

    @Override
    public void initialize() {
        int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA, ColorUtil.YELLOW, ColorUtil.CYAN};
    DefaultRenderer renderer = buildEvenementRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitleTextSize(40);
    renderer.setDisplayValues(true);
    renderer.setShowLabels(true);
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setGradientEnabled(true);
    r.setGradientStart(0, ColorUtil.BLUE);
    r.setGradientStop(0, ColorUtil.GREEN);
    r.setHighlighted(true);
    PieChart chart = new PieChart(buildEvenementDataset(), renderer);
    ChartComponent c = new ChartComponent(chart);
    Form f = new Form("Statisique", new BorderLayout());
    f.getStyle().setBgColor(0xf740a);
    f.add(BorderLayout.CENTER, c);
    back = new Button("back");
    f.add(BorderLayout.SOUTH, back);
     this.rootContainer.addComponent(BorderLayout.CENTER,f);
        
    }
    }