package es.ucm.fdi.applistclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.PrintWriter;

import java.util.LinkedList;
import java.util.List;

import es.ucm.fdi.applistclient.SocketFiles.OpenSocket;
import es.ucm.fdi.applistclient.database.AppDatabase;
import es.ucm.fdi.applistclient.tasks.SearchAppTask;


public class MainActivity extends AppCompatActivity implements ApplicationAdapter.OnAppListener {

    private PackageManager packageManager;
    //Objeto aplicacion con el que estamos trabajando
    private Application application;
    private List<Application> applicationList = new LinkedList<>();

    /*RELACIONADO CON RECYCLER VIEW*/
    private RecyclerView recyclerView;
    private ApplicationAdapter applicationAdapter;

    /*TAG DE LA CLASE PARA LOS LOGS */
    private static final String TAG = "TAG_MainActivity";

    //Referencia unica a los diccionarios de la aplciacion
    private static Diccionarios diccionarios = null;

    /*PARAMETROS DEL SOCKET */
    private String SERVER_IP = "54.234.140.208";
    int SERVER_PORT = 1234;
    public static PrintWriter output;
    private OpenSocket open;

    //Instancia de la base de datos
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Abrimos la comunicacion con el servidor al arrnacar la aplicacion
        openSocket();
        //Creamos la lista con las aplicaciones instaladas que no son de sistema
        createAplicationList();
        //Damos formato a la lista RecyclerView que contiene la lista de aplicaciones.
        createRecyclerList();
    }

    public static Diccionarios getDicInstance(){
        //Si la instancia no existe se crea, si no se devuelve la instancia ya creada
        if(diccionarios == null) {
            diccionarios = new Diccionarios();
        }
        return diccionarios;
    }

    //Inicia la communicacion con el servidor
    private void openSocket(){
        //Creamos el socket para la comunicacion con el servidor
        if(testNetwork()){
            open = new OpenSocket(SERVER_IP, SERVER_PORT, this, this);
            new Thread(open).start();
        }else{
            open = null;
            output = null;
            Log.d(TAG, "La red esta Inactiva y no se puede acceder al servidor "+SERVER_IP);
        }
    }

    //Prepara y crea la RecyclerView de la lista de aplicaciones
    private void createRecyclerList(){
        recyclerView = (RecyclerView) findViewById(R.id.lista_apps_recycler);
        applicationAdapter = new ApplicationAdapter(this, this);
        applicationAdapter.setApplicationList(applicationList);
        recyclerView.setAdapter(applicationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(applicationList.size()==0){
            TextView mainInfo = (TextView) findViewById(R.id.mainInfo);
            mainInfo.setText("No hay aplicaciones que mostrar.");
        }
    }

    //Crea la lista de aplicaciones que pueden ser analizadas
    private void createAplicationList(){
        packageManager = getPackageManager();
        List<PackageInfo> auxAppList = packageManager.getInstalledPackages(packageManager.GET_PERMISSIONS);
        for(int i = 0; i < auxAppList.size(); ++i){
            PackageInfo info = auxAppList.get(i);
            if((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
                if(!info.packageName.equals(this.getPackageName())){
                    applicationList.add(new Application(packageManager, info, this, null));
                }
            }
        }
    }

    //Metodo on click de los item de la lista de aplicaciones
    @Override
    public void onAppClick(int position) {
        application = applicationList.get(position);
        Log.d(TAG, application.getName());
        Intent appInfointent = new Intent(this, AppInfoActivity.class);
        appInfointent.putExtra("appName", application.getPackageName());
        startActivity(appInfointent);
    }

    public void setOutput(PrintWriter output) {
        this.output = output;
    }

    //Metodo que comprueba si la conexion a Internet esta activa
    private boolean testNetwork(){
        boolean networkState = false;
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            network = connManager.getActiveNetwork();
            if(network != null){
                NetworkCapabilities netCap = connManager.getNetworkCapabilities(network);
                if((netCap != null) && (netCap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        netCap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        netCap.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        netCap.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH))){
                    networkState = true;
                }
            }
        }else{
            //La version Android del dispositivo es menor que Android 6.0 por lo que segimos utilizando el metodo antiguo
            NetworkInfo networkInfo = null;
            if(connManager != null){
                networkInfo = connManager.getActiveNetworkInfo();
            }

            if(networkInfo != null && networkInfo.isConnected()){
                networkState = true;
            }
        }

        return networkState;
    }

    public void onClickHelp (View view){
        Intent helpIntent = new Intent(this, HelpActivity.class);
        startActivity(helpIntent);
    }

    //Cierra la comunicacion con el servidor cuando la app se cierra
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Si el hilo del socket logro crearse
        if(open != null){
            open.closeSocket();
        }
    }
}


