/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpservidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author wilson.adm
 */
public class TCPServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort); 
            while(true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen :"+e.getMessage( ));
        }
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    public Connection( Socket aClientSocket ) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out =new DataOutputStream(clientSocket.getOutputStream()); 
            this.start( );
        } catch (IOException e) {
            System.out.println("Connection:"+e.getMessage( ));
        }
    }
    @Override
    public void run() {
        try { // an echo server
            String data = in.readUTF( );
            out.writeUTF(data);              
        } catch(EOFException e) {
            System.out.println("EOF:"+e.getMessage( ));
        } catch(IOException e) {
            System.out.println("IO:"+e.getMessage( ));
        } finally { 
            try {
                clientSocket.close();
            } catch(IOException e) {
                /*close falhou*/ 
            }
        }
    }
}

