package es.ucm.fdi.applistclient.SocketFiles;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class CloseSocket implements Runnable {

    private Socket socket;
    /*TAG DE LA CLASE PARA LOS LOGS */
    private static final String TAG = "TAG_SocketInfo";

    //Cierra el socket pasado como parametro
    public CloseSocket(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        //Simplemente llama a close;
        close();
    }

    //Metodo que se encarga de cerrar el socket en el caso de que este abierto
    private void close(){
        if(socket != null){
            try{
                socket.close();
            } catch (IOException e){
                Log.d(TAG, "Cerrando el socket...");
            }
        }
    }
}
