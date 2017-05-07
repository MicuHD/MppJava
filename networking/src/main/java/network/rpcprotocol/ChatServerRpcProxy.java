package network.rpcprotocol;


import aplicatie.domain.Cumparator;
import aplicatie.domain.Personal;
import aplicatie.domain.Spectacol;
import aplicatie.service.ShowException;
import aplicatie.service.IClient;
import aplicatie.service.IServer;
import network.dto.*;
import network.stringprotocol.StringRequest;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
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

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<String> qresponses;
    private volatile boolean finished;
    public ChatServerRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<String>();
    }


    public List<Spectacol> getSpecacol() throws ShowException {
        //Request req = new Request.Builder().type(RequestType.GET_SPECTACOLS).build();
        String request = StringRequest.createGetShowsRequest();
        sendRequest(request);
        String response = readResponse();
        if(StringRequest.getResponseType(response) == ResponseType.ERROR){

        }
        List<Spectacol> friends = StringRequest.getShowsFromRequest(response);
        //SpectacolDTO[] frDTO=(SpectacolDTO[])response.data();
        //Spectacol[] friends=DTOUtils.getFromDTO(frDTO);
        return friends;
    }

    public boolean cumparare(Cumparator cump) throws ShowException {
        CumparatorDTO cdto = DTOUtils.getDTO(cump);
        String req = StringRequest.CumparareRequest(cump);
        sendRequest(req);
        String response = readResponse();
        if(StringRequest.getResponseType(response) == ResponseType.ERROR){
            return false;
        }
        System.out.print("Ok Received");
        return true;
    }

    public List<Spectacol> cautare(String data) throws ShowException {
        List<Spectacol> specs = getSpecacol();
        List<Spectacol> trimmed = new ArrayList<>();
        for (Spectacol spec:specs) {
            if(spec.getData().equals(data)){
                trimmed.add(spec);
            }
        }
        return trimmed;
        /// /CuvantDTO cdto= DTOUtils.getDTO(data);
        //Request req = new Request.Builder().type(RequestType.SEARCH_SPECTACOLS).data(cdto).build();
        //sendRequest(req);
//        Response response = readResponse();
//        if(response.type() == ResponseType.ERROR){
//
//        }
//        SpectacolDTO[] frDTO=(SpectacolDTO[])response.data();
        //Spectacol[] friends=DTOUtils.getFromDTO(frDTO);
        // return Arrays.asList(friends);
    }

    public Personal login(Personal user, IClient client) throws ShowException {
        //try{
            initializeConnection();
    //        PersDTO udto= DTOUtils.getDTO(user);
    //        Request req=new Request.Builder().type(RequestType.LOGIN).data(udto).build();
            String req = StringRequest.createLoginRequest(user);
            sendRequest(req);
            String response = readResponse();
            if (StringRequest.getResponseType(response)== ResponseType.OK){
                this.client=client;
                //Personal pers = DTOUtils.getFromDTO((PersDTO) response.data());
                return user;
            }
            if (StringRequest.getResponseType(response)== ResponseType.ERROR){
                String err=StringRequest.getErrorMessage(response);
                closeConnection();
                throw new ShowException(err);
            }
        return null;
    }

    public void logout(Personal user, IClient client) throws ShowException {
        Personal pers;
        PersDTO udto=DTOUtils.getDTO(user);
        //Request req=new Request.Builder().type(RequestType.LOGOUT).data(udto).build();
        String request = StringRequest.createLogoutRequest(user);
        sendRequest(request);
        String response=readResponse();
        closeConnection();
        if (StringRequest.getResponseType(response)== ResponseType.ERROR){
            String err=StringRequest.getErrorMessage(response);
            throw new ShowException(err);
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

    private void sendRequest(String request)throws ShowException {
        try {
            System.out.println(request);
            char[] charArray = request.toCharArray();
            byte[] byteArray = request.getBytes();
            System.out.println(byteArray);
            System.out.println("Mesaj");
            output.write(byteArray);
            output.flush();
        } catch (IOException e) {
            throw new ShowException("Error sending object "+e);
        }

    }

    private String readResponse() throws ShowException {
        String response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws ShowException {
        try {
            connection=new Socket(host,port);
            output=connection.getOutputStream();
            output.flush();
            input=connection.getInputStream();
            finished=false;
            startReader();
        }catch (ConnectException err){
            throw new ShowException("Connection refused");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(String response){
        if (StringRequest.getResponseType(response) == ResponseType.SOLD_TICKET){

            System.out.println("SOLT TICKET");
            try {
                Spectacol spec = StringRequest.getShowFromRequest(response);
                //Spectacol spec = DTOUtils.getFromDTO(sdto);
                client.SoldTickets(spec);
            } catch (ShowException e) {
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
                    byte[] respArray = new byte[1024];
                    input.read(respArray);
                    //Object response=input.read();
                    String response = new String(respArray);

                    System.out.println("response received "+response);
                      if (StringRequest.getResponseType(response) == ResponseType.SOLD_TICKET){
                           handleUpdate(response);
                      }else {

                          try {
                              qresponses.put((String) response);
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                      }

                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }







}
