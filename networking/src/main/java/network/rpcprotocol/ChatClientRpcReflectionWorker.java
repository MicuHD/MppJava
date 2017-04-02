package network.rpcprotocol;


import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;
import aplicatie.service.ChatException;
import aplicatie.service.IClient;
import aplicatie.service.IServer;
import network.dto.CuvantDTO;
import network.dto.DTOUtils;
import network.dto.PersDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

/**
 * Created by grigo on 3/26/16.
 */
public class ChatClientRpcReflectionWorker implements Runnable, IClient {
    private IServer server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ChatClientRpcReflectionWorker(IServer server, Socket connection) {
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
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request){
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


    private Response handleGET_SPECTACOLS(Request request){
        System.out.println("GET SPECTACOLS request ..."+request.type());

        try {
            List<Spectacol> specacol = server.getSpecacol();
            Response getResponse=new Response.Builder().type(ResponseType.GET_SPECTACOLS).data(DTOUtils.getDTO(specacol)).build();
            return getResponse;
        } catch (ChatException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleSEARCH_SPECTACOLS(Request request){
        System.out.println("SEARCH SPECTACOLS request ..."+request.type());

        try {
            CuvantDTO cdto = (CuvantDTO)request.data();
            String cuv = DTOUtils.getFromDTO(cdto);
            List<Spectacol> specacol = server.cautare(cuv);
            Response getResponse=new Response.Builder().type(ResponseType.SEARCH_SPECTACOLS).data(DTOUtils.getDTO(specacol)).build();
            return getResponse;
        } catch (ChatException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


//    private Response handleLOGOUT(Request request){
//        System.out.println("Logout request...");
//        UserDTO udto=(UserDTO)request.data();
//        User user=DTOUtils.getFromDTO(udto);
//        try {
//            server.logout(user, this);
//            connected=false;
//            return okResponse;
//
//        } catch (ChatException e) {
//            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//        }
//    }
//    private Response handleSEND_MESSAGE(Request request){
//            System.out.println("SendMessageRequest ...");
//            MessageDTO mdto=(MessageDTO)request.data();
//            Message message=DTOUtils.getFromDTO(mdto);
//            try {
//                server.sendMessage(message);
//                return okResponse;
//            } catch (ChatException e) {
//                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//            }
//
//
//    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void SoldTickets() {

    }
}
