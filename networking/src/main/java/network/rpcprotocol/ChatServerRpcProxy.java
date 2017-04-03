package network.rpcprotocol;


import aplicatie.domain.Cumparator;
import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;
import aplicatie.service.ChatException;
import aplicatie.service.IClient;
import aplicatie.service.IServer;
import network.dto.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by grigo on 12/15/15.
 */
public class ChatServerRpcProxy implements IServer {
    private String host;
    private int port;

    private IClient client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ChatServerRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }


    public List<Spectacol> getSpecacol() throws ChatException {
        Request req = new Request.Builder().type(RequestType.GET_SPECTACOLS).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){

        }
        SpectacolDTO[] frDTO=(SpectacolDTO[])response.data();
        Spectacol[] friends=DTOUtils.getFromDTO(frDTO);
        return Arrays.asList(friends);
    }

    public boolean cumparare(Cumparator cump) throws ChatException {
        CumparatorDTO cdto = DTOUtils.getDTO(cump);
        Request req = new Request.Builder().type(RequestType.SELL_TICKET).data(cdto).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
            return false;
        }
        return true;
    }

    public List<Spectacol> cautare(String data) throws ChatException {
        CuvantDTO cdto= DTOUtils.getDTO(data);
        Request req = new Request.Builder().type(RequestType.SEARCH_SPECTACOLS).data(cdto).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){

        }
        SpectacolDTO[] frDTO=(SpectacolDTO[])response.data();
        Spectacol[] friends=DTOUtils.getFromDTO(frDTO);
        return Arrays.asList(friends);
    }

    public Personal login(Personal user, IClient client) throws ChatException {
        initializeConnection();
        PersDTO udto= DTOUtils.getDTO(user);
        Request req=new Request.Builder().type(RequestType.LOGIN).data(udto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            Personal pers = DTOUtils.getFromDTO((PersDTO) response.data());
            return pers;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new ChatException(err);
        }
        return null;
    }

    public void logout(Personal user, IClient client) throws ChatException {
        Personal pers;
        PersDTO udto=DTOUtils.getDTO(user);
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(udto).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ChatException(err);
        }
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request)throws ChatException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ChatException("Error sending object "+e);
        }

    }

    private Response readResponse() throws ChatException {
        Response response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws ChatException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response){
        if (response.type()== ResponseType.SOLD_TICKET){

            System.out.println("SOLT TICKET");
            try {
                SpectacolDTO sdto = (SpectacolDTO)response.data();
                Spectacol spec = DTOUtils.getFromDTO(sdto);
                client.SoldTickets(spec);
            } catch (ChatException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean isUpdate(Response response){
        return response.type()== ResponseType.SOLD_TICKET;
    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }







}
