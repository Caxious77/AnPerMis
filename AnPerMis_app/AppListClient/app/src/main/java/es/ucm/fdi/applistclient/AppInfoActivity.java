package es.ucm.fdi.applistclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import es.ucm.fdi.applistclient.SocketFiles.SendMessage;
import es.ucm.fdi.applistclient.database.CategoryCriterioEntity;
import es.ucm.fdi.applistclient.database.CategoryFreEntity;
import es.ucm.fdi.applistclient.tasks.InsertAppTask;
import es.ucm.fdi.applistclient.tasks.SearchAppTask;
import es.ucm.fdi.applistclient.tasks.SearchCategoryTask;

public class AppInfoActivity extends AppCompatActivity implements PermissionAdapter.OnPermListener{

    //Manejador de las notificaciones
    private NotificationManager notificationManager;
    private String channel = "AppClientList";

    /* Relacionado con el objeto aplicacion del que estamos consultando la info */
    private Application currentApplication;
    private PackageManager packageManager;

    /* COMPONENTES VISUALES QUE APARECEN POR PANTALLA */
   private TextView appicon;
   private TextView appName;
   private TextView appCategory;
   private TextView appCargando;
   private ImageView appDanImg;
   private Button ajustesButton;
   private Button comentarButton;

   /* PARA RECYCLER VIEW */
    private List<Permission> permissionList;
    private RecyclerView recyclerView;
    private PermissionAdapter permissionAdapter;

    /*TAG DE LA CLASE PARA LOS LOGS */
    private static final String TAG = "TAG_AppInfoActivity";

    /* Identificador del cargador de la categoria */
    private static int CATEGORY_LOADER_ID = 0;

    /* Objeto cargador de categorias */
    private CategoryLoaderCallbacks  categoryLoaderCallbacks = new CategoryLoaderCallbacks(this);

    private String defCat = "SIN INFORMACION";
    private String catAnterior = defCat;
    private boolean cambioCat = false;
    private boolean cambioNumPerm = false;
    private int anyadidos = 0;

    private Diccionarios diccionarios;

    private Set<String> dangCategories = new TreeSet<>();

    private  int pulsadoAjustes = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        //Obtenemos la instancia de los diccionarios de la aplicacion
        this.diccionarios = MainActivity.getDicInstance();

        //Inicializamos las categorias peligrosas
        dangCategories.add("TOOLS");
        dangCategories.add("LIFESTYLE");
        dangCategories.add("LIBRARIES_AND_DEMO");

        /* Recogemos la aplicacion actual */
        packageManager = getPackageManager();
        currentApplication = getCurrentApplication();
        Log.d(TAG, currentApplication.getPackageName());

        /*OBTENEMOS LOS COMPONENTES VISUALES */
        getVisualComponents();
        //Buscamos la categoria de la aplicacion en Google Play
        searchCategory();
    }

    //Obtiene a partir del nombre del paquete la informacion de una aplicacion
    private Application getCurrentApplication(){
        //Recibimos el nombre del paquete de la aplicacion para buscrala en el package manager de android
        Application current;
        Intent mainIntent = getIntent();
        String appName = mainIntent.getStringExtra("appName");
        try {
            PackageInfo info = packageManager.getPackageInfo(appName, packageManager.GET_PERMISSIONS);
            current = new Application(packageManager, info, this, diccionarios);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //El paquete se deberia encontrar pero hay que inicializarlo de todas formas.
            current = null;
        }
        return current;
    }

    //Obtiene las componentes visuales de esta actividad
    private void getVisualComponents(){
        appicon = (TextView) findViewById(R.id.InfoIcon);
        appName = (TextView) findViewById(R.id.InfoName);
        appCategory = (TextView) findViewById(R.id.InfoCategory);
        appCargando = (TextView) findViewById(R.id.infoCargando);
        appDanImg = (ImageView) findViewById(R.id.infoAppDangerous);
        comentarButton = (Button) findViewById(R.id.InfoPLay);
        ajustesButton = (Button) findViewById(R.id.InfoAjustes);
        ajustesButton.setVisibility(View.GONE);
        comentarButton.setVisibility(View.GONE);
    }

    //Muestra la descripcion de cada uno de los permisos de la aplicacion
    //private void viewDescription(String title, String message, int icono, boolean freq, boolean opi, String permiso){
    private void viewDescription(Permission p){
        String msg = p.getDescription() + p.getMsg();
        alertDialog(p.getPermissionImage(), p.getName(), msg);
    }

    //Muestra los mensajes de alerta con el boton cerrar
    private void alertDialog(int icono, String title, String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(icono);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Cerrar", null);
        alertDialog.show();
    }

    //Establece los valores a las componentes visuales de esta actividad
    private void setVisualComponents(){
        /*Nombre de la aplicacion */
        Drawable icono = currentApplication.getAppIcon();
        icono.setBounds(0, 0, 150, 150);
        appicon.setCompoundDrawables(icono, null, null, null);
        appicon.setCompoundDrawablePadding(30);
        appName.setText(currentApplication.getName());
        //Mostramos el aviso del cambio de permisos
        avisoPermisos();
        //Mostramos los posibles avisos del cambio de categoria
        avisosCategoria();
        appDanImg.setImageResource(currentApplication.getDangId());
        //Si no dispone de categoria eliminamos el boton de comentar
        if(currentApplication.getCategory().equals(defCat)){
            comentarButton.setVisibility(View.GONE);
        }else{
            comentarButton.setVisibility(View.VISIBLE);
        }
        ajustesButton.setVisibility(View.VISIBLE);
    }

    //Crea la lista RecyclerView con los permisos peligrosos de la aplicacion
    private void createListaRecycler(){
        recyclerView = (RecyclerView) findViewById(R.id.listaPermisos);
        permissionAdapter = new PermissionAdapter(this, this, currentApplication.getCategory());
        permissionAdapter.setPermissionList(permissionList);
        recyclerView.setAdapter(permissionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //Escribe por pantalla el aviso de aumento de permisos en la aplicacion
    private void avisoPermisos(){
        //Comprueba si el numero de permisos ha cambaido para lanzar el aviso por pantalla
        if(anyadidos != 0){
            appCargando.setText("Nuevo Análisis");
            if(anyadidos > 0){
                aumentoNumPermisos();
            }else{
                decrementoNumPermisos();
            }
        }
    }

    private void aumentoNumPermisos(){
        int icono;
        String title, aux , msg;
        aux = "";
        icono = R.drawable.advertencia_amarillo;
        title = "AVISO: Aumento del número de permisos";
        if(anyadidos == 1){
            aux  = aux + " permiso peligroso";
        }else{
            aux = aux + " permisos peligrosos";
        }
        msg = "La app " + currentApplication.getName() + " tiene " + anyadidos + aux + " más que antes. " +
                "Por favor, revisa de nuevo el análisis de permisos.";
        alertDialog(icono, title, msg);
    }

    private void decrementoNumPermisos(){
        int icono;
        String title, aux , msg;
        icono = R.drawable.correcto;
        aux = "";
        title = "AVISO: Cambio en el número de permisos";
        if(anyadidos == -1){
            aux  = aux + " permiso peligroso";
        }else{
            aux = aux + " permisos peligrosos";
        }
        msg = "La app " + currentApplication.getName() + " tiene " + anyadidos * -1 + aux + " menos que antes. " +
                "Por favor, revisa de nuevo el análisis de permisos.";
        alertDialog(icono, title, msg);
    }

    //Muestra por pantalla avisos sobre la categoria de la aplicacion
    private void avisosCategoria(){
        //Avisa al usuario si la categoria ha cambiado
        if(currentApplication.getCategory().equals(defCat)){
            appCategory.setText(currentApplication.getTraduccion());
            appCargando.setText("No se puede realizar el análisis");
            currentApplication.setDangId(R.drawable.advertencia_rojo);
            String title = "ADVERTENCIA";
            String msg = getString(R.string.no_category);
            alertDialog(R.drawable.advertencia_amarillo, title, msg );
        }else{
            if(cambioCat){
                appCategory.setText("("+currentApplication.getTraduccion()+") Ha cambiado");
                appCargando.setText("Nuevo Análisis");
                int icono = R.drawable.advertencia_amarillo;
                String title = "AVISO: Cambio de Categoría";
                String msg = "La categoría de la aplicación " + currentApplication.getName() + " que anteriormente era " + catAnterior +
                        " ha cambiado.\n\nLa nueva categoría de la aplicación en Google Play Store es " + currentApplication.getTraduccion() + ".";
                alertDialog(icono, title, msg);
            }else{
                //Aviso al usuario de categorias peligrosas
                if(dangCategories.contains(currentApplication.getCategory())){
                    appCategory.setText("("+currentApplication.getTraduccion()+") Peligrosa");
                    appCargando.setText("App potencialmente mal clasificada!");
                    int icono = R.drawable.advertencia_amarillo;
                    String title = "AVISO: App potencialmente mal calsificada";
                    String msg = "Esta aplicacion se encuentra dentro de la categoría "+currentApplication.getTraduccion()+ " " + getString(R.string.dang_cat);
                    alertDialog(icono, title, msg);
                    currentApplication.setDangId(R.drawable.advertencia_rojo);
                }else{
                    appCategory.setText(currentApplication.getTraduccion());
                    if(currentApplication.getTotalPeligro() == 0){
                        appCargando.setText("No tiene permisos perjudiciales");
                    }else{
                        appCargando.setText("Resultados del Análisis");
                    }
                }
            }
        }
    }

    //Busca la categoria de la aplicacion en la Play Store utilizando el Loader de Categorias
    //A partir de Android 6.0 para escanear redes wifi se necesita el permiso de localizacion pero en
    //nuestra app no se necesita esa funcion

    public void searchCategory(){
        String pName = currentApplication.getPackageName();
        if(testNetwork()){
            Bundle bundle = new Bundle();
            bundle.putString("package_name", pName);
            LoaderManager.getInstance(this).restartLoader(CATEGORY_LOADER_ID, bundle, categoryLoaderCallbacks);
        }else{
            //No hay conexion, buscamos la aplicacion en la base de datos por si la hubieramos analizado antes
            SearchAppTask search = new SearchAppTask(this, this, currentApplication.getPackageName());
            search.execute();
        }
    }

    /* Metodo que al terminar de cargar la categoria la establece al objeto actual */
    public void updateCategoryResult(String s){
        //Si el mensaje recibido por google no es nulo
        if(!s.equals(defCat)){
           currentApplication.setCategory(s);
           Log.d(TAG, currentApplication.getCategory());
        }else{
            currentApplication.setCategory(defCat);
            Log.d(TAG, currentApplication.getCategory());
        }
        /* NUEVO METODO */
        //Buscamos la app en la base de datos por si la tuvieramos almacenada y algo hubiera cambiado en esa app con respecto a
        //analisis anteriores
        SearchAppTask search = new SearchAppTask(this, this, currentApplication.getPackageName());
        search.execute();
    }

    //Metodo que se ejecuta tras bucar la app en la base de datos de la aplicacion
    public void onSearchAppResult(String s, int numPeligrosos){
        //currentApplication.setTotalPeligro(c);
        currentApplication.setTotalPeligro();
        //Si la app estaba en la base de datos comprobamos si la categoria o los permisos han cambiado.
        if(!s.equals("")){
            if(!currentApplication.getCategory().equals(defCat)){
                //currentApplication.setCategory("PRODUCTIVITY");
                cambioCat = isCambioCat(s);
                cambioNumPerm = isCambioNumPerm(numPeligrosos);
                //Si cambia la categoria o el numero de permisos actualizamos la base de datos
                if(cambioCat || cambioNumPerm){
                    //Insertamos la app en la base de datos, lo hacemos en segundo plano.
                    InsertAppTask insert = new InsertAppTask(this, currentApplication.getPackageName(), currentApplication.getCategory(), currentApplication.getTotalPeligro());
                    insert.execute();
                }
                //Enviamos la informacion al servidor remoto
                uploadAppInfo();
            }else{
                //No se pudo encontarar la categoria en google play pero si tenemos informciion de la app en la base de datos
                //ya que no es vacia la info de s
                //Modo offline, la app si esta en la bbdd
                currentApplication.setCategory(s);
            }
        }else{
            //La app no existe en la base de datos
            //Si la PlayStore ha devuelto una categoria la insertamos en la base de datos
            if(!currentApplication.getCategory().equals(defCat)){
                //Insertamos la app en la base de datos, lo hacemos en segundo plano.
                InsertAppTask insert = new InsertAppTask(this, currentApplication.getPackageName(), currentApplication.getCategory(), currentApplication.getTotalPeligro());
                insert.execute();
                uploadAppInfo();
            }
        }

        /* Buscamos la informacion de la categoria en la BBDD */
        SearchCategoryTask task = new SearchCategoryTask(this, this, currentApplication.getCategory());
        task.execute();

    }
    //Comprueba si ha cambiado la categoria con respecto a analisis anteriores
    private boolean isCambioCat(String category){
        boolean ret = false;
        if(!currentApplication.getCategory().equals(category)){
            ret = true;
            catAnterior = diccionarios.getCategoryTraductor().get(category);
        }
        return ret;
    }
    //Comprueba si ha cambiado el numero de permisos con respecto a análisis anteriores
    private boolean isCambioNumPerm(int numPeligrosos){
        boolean ret = false;
        if(numPeligrosos != currentApplication.getTotalPeligro()){
            ret = true;
            //if(numPeligrosos < currentApplication.getTotalPeligro()){
                anyadidos = currentApplication.getTotalPeligro() - numPeligrosos;
            //}
        }
        return ret;
    }

    //Metodo que se ejecuta tras buscar la informacion sobre la categoria en la base de datos
    public void onSearchCategoryResult(CategoryFreEntity categoryFreEntity, CategoryCriterioEntity categoryCriterioEntity){

        //Si la categoria no se encuentra en la base de datos, no mostramos resultados de análisis
        if(categoryFreEntity == null || categoryCriterioEntity == null){
            Toast.makeText(this, "La categoria no se ha encontrado en la bbdd", Toast.LENGTH_LONG);
            appCargando.setText("Error en el analisis!!");
        }else{
            //Cuando ya tenemos datos sobre la categoria, las frecuencias ya podemos analizar los permisos
            analizarPermisos(categoryFreEntity, categoryCriterioEntity);
            //Una vez lo tenemos listo
            /* DAMOS VALOR A LOS COMPONENTES VISUALES */
            setVisualComponents();
            createListaRecycler();
        }
    }

    //Metodo que analiza los permisos prligrosos de una aplicacion y los devuelve en una lista
    private void analizarPermisos(CategoryFreEntity categoryFreEntity, CategoryCriterioEntity categoryCriterioEntity){
        //Obtenemos la lista de los permisos peligrosos de la aplicacion actual
        String permisos[] = currentApplication.getPermisos();
        permissionList = currentApplication.getDangerousPermissions(permisos, categoryFreEntity, categoryCriterioEntity);
    }

    //Envia al servidor remoto la informacion sobre la aplicacion
    private void uploadAppInfo(){
        //Si no existe conexion con el servidor no se envia la app
        if(MainActivity.output != null){
            //Si existe un output se envia la app al servidor
            String uploadMessage = currentApplication.getAppUploadMessage();
            Log.d(TAG, "Enviando app: "+uploadMessage);
            new Thread(new SendMessage(uploadMessage, MainActivity.output)).start();
        }
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

    //Metodo Onclick del boton comentar que abre la pagina de la playstore en la aplicacion
    public void onClickcomentar(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(
                "https://play.google.com/store/apps/details?id="+currentApplication.getPackageName()
        ));
        startActivity(intent);
    }

    //Metodo onclick del boton ajuestes que abre la pagina de ajustes de la aplicacion
    public void onClickajustes (View view){
        pulsadoAjustes = 1;
        Intent intent = new Intent((Settings.ACTION_APPLICATION_DETAILS_SETTINGS));
        intent.setData(Uri.parse("package:"+currentApplication.getPackageName()));
        startActivity(intent);
    }

    //Metodo onclick de cada uno de los permisos peligrosos
    @Override
    public void onPermClick(int position) {
        if(!currentApplication.getCategory().equals(defCat)){
            Permission p = permissionList.get(position);
            viewDescription(p);
            //viewDescription(p.getName(), p.getDescription(), p.getPermissionImage(), p.isRojo_freq(), p.isRojo_opi(), p.getOriginal());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (pulsadoAjustes == 1 && !currentApplication.getCategory().equals(defCat)) {
            alertDialog(R.drawable.advertencia_amarillo, "RECOMENDACIÓN", getString(R.string.ajustes));
            pulsadoAjustes = 0;
        }
    }
}

