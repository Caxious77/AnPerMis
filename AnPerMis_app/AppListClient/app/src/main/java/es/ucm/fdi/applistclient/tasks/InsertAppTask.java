package es.ucm.fdi.applistclient.tasks;

import android.content.Context;
import android.os.AsyncTask;

import es.ucm.fdi.applistclient.database.AppDatabase;
import es.ucm.fdi.applistclient.database.AppEntity;

public class InsertAppTask extends AsyncTask<AppEntity, Integer, AppEntity> {

    private String package_name;
    private String category;
    private Context context;
    private int numPermisos;
    private AppDatabase appDatabase;

    public InsertAppTask(Context context, String package_name, String category, int numPermisos){
        this.package_name = package_name;
        this.category = category;
        this.numPermisos = numPermisos;
        this.context = context;
    }

    @Override
    protected AppEntity doInBackground(AppEntity... appEntities) {
        this.appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        //Creamos la entidad que vamos a insertar en la base de datos;
        AppEntity app = new AppEntity(package_name, category, numPermisos);
        //Insertamos la entidad creada en la base de datos, utilizando la politica OnconflictStrategy.REPLACE
        appDatabase.appDao().insertApplication(app);
        return app;
    }

    //Metodo que se ejecuta tras la inserccion en la base de datos, utilizado para depuracion
    @Override
    protected void onPostExecute(AppEntity appEntity) {
        //Si tras insertar la aplicacion es nula
        if(appEntity == null){
            //Imprimir informacion de depuracion
            //Toast.makeText(context.getApplicationContext(), "La app:" + package_name + " ya existe.", Toast.LENGTH_LONG).show();
        }else{
            //Imprimir informacion de depuracion
            //Toast.makeText(context.getApplicationContext(), "La app:" + package_name + " se inserto correctamente.", Toast.LENGTH_LONG).show();
        }
    }
}
