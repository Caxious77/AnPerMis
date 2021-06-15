package es.ucm.fdi.applistclient.SocketFiles;

import android.util.Log;

import java.io.PrintWriter;

public class SendMessage implements Runnable {

    private String message;
    private PrintWriter output;

    /*TAG DE LA CLASE PARA LOS LOGS */
    private static final String TAG = "TAG_SocketInfo";

    public SendMessage(String message, PrintWriter output){
        this.message = message;
        this.output = output;
    }

    @Override
    public void run() {
        //Envia el mensaje al servidro remoto
        sendMsg();
    }

    private void sendMsg(){
        output.println("subirapps");
        output.println(message);
        output.println("finsubida");
        Log.d(TAG, "Mensaje enviado: "+message+"\n");
    }
}
