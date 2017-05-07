package network.stringprotocol;

import aplicatie.domain.Cumparator;
import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;
import network.rpcprotocol.ResponseType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Micu on 5/1/2017.
 */
public class StringRequest{
    public static String createLoginRequest(Personal user){
        String req = "1.";
        req += user.getUsername()+",";
        req += user.getParola()+",";
        return req;
    }
    public static String createLogoutRequest(Personal user){
        String req = "2.";
        req += user.getUsername()+",";
        return req;
    }
    public static String createGetShowsRequest(){
        String req = "3.";
        return req;
    }

    public static String CumparareRequest(Cumparator cmp){
        String req = "4.";
        req+=cmp.getNume()+",";
        req+=cmp.getBilete()+",";
        req+=cmp.getIdSpectacol()+",";
        return req;
    }

    public static String getErrorMessage(String response){
        if(response.charAt(0) == '2'){
            String[] items = response.split("\\.");
            return items[1];
        }
        return "";
    }
    public static Spectacol getShowFromRequest(String response){
        String[] items = response.split("\\.");
            String[] showItems = items[1].split(",");
            if(showItems.length>=7){
                Spectacol sp = new Spectacol(Integer.parseInt(showItems[0]),showItems[1],Integer.parseInt(showItems[2]),Integer.parseInt(showItems[3]),showItems[4],showItems[5],showItems[6]);
                return sp;
            }
        return null;
    }

    public static List<Spectacol> getShowsFromRequest(String response){
        List<Spectacol> specs = new ArrayList<>();
        if(response.charAt(0) == '1'){
            String[] items = response.split("\\.");
            String[] shows = items[1].split(";");
            for (String show: shows) {

                String[] showItems = show.split(",");
                if(showItems.length==7){
                    Spectacol sp = new Spectacol(Integer.parseInt(showItems[0]),showItems[1],Integer.parseInt(showItems[2]),Integer.parseInt(showItems[3]),showItems[4],showItems[5],showItems[6]);
                    specs.add(sp);
                }

            }
        }
        return specs;
    }
    public static ResponseType getResponseType(String response){
        if(response.charAt(0) == '1'){
            return ResponseType.OK;
        }
        if(response.charAt(0) =='9'){
            return ResponseType.SOLD_TICKET;
        }
        return ResponseType.ERROR;
    }
}
