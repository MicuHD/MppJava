package network.dto;

import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 20, 2009
 * Time: 8:07:36 AM
 */
public class DTOUtils {
//    public static User getFromDTO(PersDTO usdto){
//        String id=usdto.getId();
//        String pass=usdto.getPasswd();
//        return new User(id, pass);
//
//    }
      public static Personal getFromDTO(PersDTO persDTO){
            String user = persDTO.getUser();
            String pass = persDTO.getPasswd();
            return new Personal(user,pass);
      }

      public static PersDTO getDTO(Personal pers){
            String user = pers.getUsername();
            String pass = pers.getParola();
            return new PersDTO(user,pass);
      }

    public static SpectacolDTO getDTO(Spectacol spectacol){
          Integer id = spectacol.getId();
          String locatie = spectacol.getLocatie();
          Integer disponibile = spectacol.getDisponibile();
          Integer vandute =spectacol.getVandute();
          String artist = spectacol.getArtist();
          String data = spectacol.getData();
          String ora = spectacol.getOra();
        return new SpectacolDTO(id,locatie,disponibile,vandute,artist,data,ora);
    }
    public static CuvantDTO getDTO(String str){
        return new CuvantDTO(str);
    }
//
    public static Spectacol getFromDTO(SpectacolDTO mdto){
        Integer id = mdto.getId();
        String locatie = mdto.getLocatie();
        Integer disponibile = mdto.getDisponibile();
        Integer vandute =mdto.getVandute();
        String artist = mdto.getArtist();
        String data = mdto.getData();
        String ora = mdto.getOra();
        return new Spectacol(id,locatie,disponibile,vandute,artist,data,ora);

    }

    public static String getFromDTO(CuvantDTO mdto){
        String str = mdto.getCuvant();
        return str;

    }

//
//    public static MessageDTO getDTO(Message message){
//        String senderId=message.getSender().getId();
//        String receiverId=message.getReceiver().getId();
//        String txt=message.getText();
//        return new MessageDTO(senderId, txt, receiverId);
//    }
//
    public static SpectacolDTO[] getDTO(List<Spectacol> users){
          Spectacol[] bar = users.toArray(new Spectacol[users.size()]);
        SpectacolDTO[] frDTO=new SpectacolDTO[bar.length];
        for(int i=0;i<bar.length;i++)
            frDTO[i]=getDTO(bar[i]);
        return frDTO;
    }

    public static Spectacol[] getFromDTO(SpectacolDTO[] users){
        Spectacol[] friends=new Spectacol[users.length];
        for(int i=0;i<users.length;i++){
            friends[i]=getFromDTO(users[i]);
        }
        return friends;
    }
}
