package network.rpcprotocol;

import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;
import aplicatie.service.ChatException;
import aplicatie.service.IClient;
import aplicatie.service.IServer;
import network.dto.CuvantDTO;
import network.dto.DTOUtils;
import network.dto.PersDTO;
import network.dto.SpectacolDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by grigo on 12/15/15.
 */
public class ChatClientRpcWorker implements Runnable, IClient {
    private IServer server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ChatClientRpcWorker(IServer server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();
  //  private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();
    private Response handleRequest(Request request){
        Response response=null;
        if (request.type()==RequestType.LOGIN){
            System.out.println("Login request ..."+request.type());
            PersDTO udto=(PersDTO)request.data();
            Personal user= DTOUtils.getFromDTO(udto);
            try {
                server.login(user, this);
                return okResponse;
            } catch (ChatException e) {
                connected=false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()==RequestType.GET_SPECTACOLS){
            System.out.println("Spectacols request");
           // LogoutRequest logReq=(LogoutRequest)request;
            try {
                server.getSpecacol();

                return okResponse;

            } catch (ChatException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type()==RequestType.SEARCH_SPECTACOLS){
            System.out.println("Spectacols request");
            // LogoutRequest logReq=(LogoutRequest)request;
            try {
                CuvantDTO udto=(CuvantDTO) request.data();
                String cuvant = DTOUtils.getFromDTO(udto);
                List<Spectacol>  specs = server.cautare(cuvant);
                //SpectacolDTO[] specdto =
                //return =new Response.Builder().type(ResponseType.SEARCH_SPECTACOLS).data().build();;
                return okResponse;
            } catch (ChatException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }


//        if (request.type()==RequestType.SEND_MESSAGE){
//            System.out.println("SendMessageRequest ...");
//            MessageDTO mdto=(MessageDTO)request.data();
//            Message message=DTOUtils.getFromDTO(mdto);
//            try {
//                server.sendMessage(message);
//                return okResponse;
//            } catch (ChatException e) {
//                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//            }
//        }
//
//        if (request.type()==RequestType.GET_LOGGED_FRIENDS){
//            System.out.println("GetLoggedFriends Request ...");
//            UserDTO udto=(UserDTO)request.data();
//            User user=DTOUtils.getFromDTO(udto);
//            try {
//                User[] friends=server.getLoggedFriends(user);
//                UserDTO[] frDTO=DTOUtils.getDTO(friends);
//                return new Response.Builder().type(ResponseType.GET_LOGGED_FRIENDS).data(frDTO).build();
//            } catch (ChatException e) {
//                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//            }
//        }
        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void SoldTickets() {

    }
}
