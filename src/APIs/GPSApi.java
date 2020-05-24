/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APIs;

import Entity.User;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.processing.Result;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nacef
 */
public class GPSApi {
    String location="";
    public String getLocationFromGPS(){
        Display.getInstance().callSerially(() -> {
        Location l = Display.getInstance().getLocationManager().getCurrentLocationSync();
        ConnectionRequest request = new ConnectionRequest("http://maps.googleapis.com/maps/api/geocode/json", false) {
            private String country;
            private String region;
            private String city;
            private String json;

            @Override
            protected void readResponse(InputStream input) throws IOException {
                    Result result = Result.fromContent(input, Result.JSON); 
                    country = result.getAsString("/results/address_components[types='country']/long_name");
                    region = result.getAsString("/results/address_components[types='administrative_area_level_1']/long_name");
                    city = result.getAsString("/results[1]/address_components[types='political']/long_name");           
                    json = result.toString();
            }

            @Override
            protected void postResponse() {
                //System.out.println(country);
                //System.out.println(region);
                //System.out.println(city);
                location = city;                
                
            }
        };
        request.setContentType("application/json");
        request.addRequestHeader("Accept", "application/json");
        request.addArgument("sensor", "true");
        request.addArgument("latlng", l.getLatitude() + "," + l.getLongitude());

        NetworkManager.getInstance().addToQueue(request);        
        });
        return location;
    }
    
    public String getGPS(){
        Location position = LocationManager.getLocationManager().getCurrentLocationSync();
        double lat = position.getLatitude();
        double longi = position.getLongitude();
        
        /*ConnectionRequest con = new ConnectionRequest();
        con.setPost(false);
        con.setUrl("https://maps.googleapis.com/maps/api/geocode/json?latlng="+String.valueOf(lat)+","+String.valueOf(longi)+"&key=%20AIzaSyCIy79y4ICwk47WGF7bzQaI9cIuwUPQuqk");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    //System.out.println(tasks);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("results");
                    
                    List<Map<String, Object>> list2 = (List<Map<String, Object>>) list.get(1).get("address_components");
                    
                    String list3 = (String) list2.get(0).get("long_name");
                    
                    System.out.println(list3);
                } catch (IOException ex) {
                }
            }
        });
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        con.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(con);*/
        
        String locationName = "";
        try {
            ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng="+String.valueOf(lat)+","+String.valueOf(longi)+"&key=%20AIzaSyCIy79y4ICwk47WGF7bzQaI9cIuwUPQuqk", false);

            NetworkManager.getInstance().addToQueueAndWait(request);
            JSONParser jsonp = new JSONParser();
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
            //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(request.getResponseData()).toCharArray()));
                    //System.out.println(tasks);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("results");
                    
                    List<Map<String, Object>> list2 = (List<Map<String, Object>>) list.get(1).get("address_components");
                    
                    locationName = (String) list2.get(0).get("long_name");
                    
                    //System.out.println(locationName);
                    
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locationName;
    }
    
}
