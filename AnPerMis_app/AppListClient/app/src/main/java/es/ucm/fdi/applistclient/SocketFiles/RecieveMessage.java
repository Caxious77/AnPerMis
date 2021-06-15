package es.ucm.fdi.applistclient.SocketFiles;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

import es.ucm.fdi.applistclient.database.CategoryFreEntity;
import es.ucm.fdi.applistclient.database.CategoryCriterioEntity;
import es.ucm.fdi.applistclient.tasks.InsertCategoryFreTask;
import es.ucm.fdi.applistclient.tasks.InsertCategoryCriterioTask;

public class RecieveMessage implements Runnable{

    private BufferedReader input;
    private String SERVER_IP;
    private int SERVER_PORT;

    private String centinela = "finenvio";
    private String centinelaOp ="finenviotablafreqs";
    private String dummy = "Petición recibida y aceptada";

    /*TAG DE LA CLASE PARA LOS LOGS */
    private static final String TAG = "TAG_SocketInfo";

    private Context context;

    public RecieveMessage(String SERVER_IP, int SERVER_PORT, BufferedReader input, Context context){
        this.SERVER_IP = SERVER_IP;
        this.SERVER_PORT = SERVER_PORT;
        this.input = input;
        this.context = context;
    }

    @Override
    public void run() {
        //Unicamente llama a la funcion que obtiene los mensajes del socket
        getSocketMsg();
    }

    //Recibe los mensajes del socket y los procesa
    private void getSocketMsg(){
        try{
            String message = input.readLine();
            //Si el mensaje no es el centinela
            while(!message.equals(centinelaOp)){
                procesarMsgTablaFreq(message);
                //Leemos el nuevo mensaje
                message = input.readLine();
            }
            message = input.readLine();
            while (!message.equals(centinela)){
                procesarMsgTablaCriterio(message);
                //Leemos el nuevo mensaje
                message = input.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Procesa un mensaje de la tabla de frecuencias
    private void procesarMsgTablaFreq(String message){
        //Leemos el mensaje
        if(message != null){
            if(!message.equals(dummy)){
                Log.d(TAG, "Server frequency: "+message);
                CategoryFreEntity fre = parsearSocketMsgFre(message);
                InsertCategoryFreTask insertFreq = new InsertCategoryFreTask(context.getApplicationContext(), fre);
                insertFreq.execute();
            }else{
                //Mostramos por pantalla el mensaje dummy
                Log.d(TAG, "Server: "+message);
            }
        }else{
            Log.d(TAG, "Mensaje nulo, vuelvo a abrir la conexion");
            //new Thread(new OpenSocketThread(SERVER_IP, SERVER_PORT, context)).start();
            return;
        }
    }

    //Procesa un mensaje de la tabla de criterios
    private void procesarMsgTablaCriterio(String message){
        //Leemos el mensaje
        if(message != null){
            if(!message.equals(dummy)){
                Log.d(TAG, "Server opinion: "+message);
                CategoryCriterioEntity criterio = parsearSocketMsgCriterio(message);
                InsertCategoryCriterioTask insertCriterio = new InsertCategoryCriterioTask(context.getApplicationContext(), criterio);
                insertCriterio.execute();
            }else{
                //Mostramos por pantalla el mensaje dummy
                Log.d(TAG, "Server: "+message);
            }
        }else{
            Log.d(TAG, "Mensaje nulo, vuelvo a abrir la conexion");
            //new Thread(new OpenSocketThread(SERVER_IP, SERVER_PORT, context)).start();
            return;
        }
    }

    //Funcion que parsea el mensaje de entrada.
    private CategoryFreEntity parsearSocketMsgFre(String msg){
        String s[] = msg.split(";");
        CategoryFreEntity fre = new CategoryFreEntity(
                s[0], Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), Double.parseDouble(s[4]),
                Double.parseDouble(s[5]), Double.parseDouble(s[6]), Double.parseDouble(s[7]), Double.parseDouble(s[8]),
                Double.parseDouble(s[9]), Double.parseDouble(s[10]), Double.parseDouble(s[11]), Double.parseDouble(s[12]), Double.parseDouble(s[13]),
                Double.parseDouble(s[14]), Double.parseDouble(s[15]), Double.parseDouble(s[16]), Double.parseDouble(s[17]), Double.parseDouble(s[18]),
                Double.parseDouble(s[19]), Double.parseDouble(s[20]), Double.parseDouble(s[21]), Double.parseDouble(s[22]), Double.parseDouble(s[23]),
                Double.parseDouble(s[24]), Double.parseDouble(s[25]), Double.parseDouble(s[26]), Double.parseDouble(s[27]), Double.parseDouble(s[28]),
                Double.parseDouble(s[29])
        );
        return fre;
    }

    //Funcion que parsea el mensaje de entrada.
    private CategoryCriterioEntity parsearSocketMsgCriterio(String msg){
        String s[] = msg.split(";");
        CategoryCriterioEntity op = new CategoryCriterioEntity(
                s[0], Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), Double.parseDouble(s[4]),
                Double.parseDouble(s[5]), Double.parseDouble(s[6]), Double.parseDouble(s[7]), Double.parseDouble(s[8]),
                Double.parseDouble(s[9]), Double.parseDouble(s[10]), Double.parseDouble(s[11]), Double.parseDouble(s[12]), Double.parseDouble(s[13]),
                Double.parseDouble(s[14]), Double.parseDouble(s[15]), Double.parseDouble(s[16]), Double.parseDouble(s[17]), Double.parseDouble(s[18]),
                Double.parseDouble(s[19]), Double.parseDouble(s[20]), Double.parseDouble(s[21]), Double.parseDouble(s[22]), Double.parseDouble(s[23]),
                Double.parseDouble(s[24]), Double.parseDouble(s[25]), Double.parseDouble(s[26]), Double.parseDouble(s[27]), Double.parseDouble(s[28]),
                Double.parseDouble(s[29])
        );
        return op;
    }
}
