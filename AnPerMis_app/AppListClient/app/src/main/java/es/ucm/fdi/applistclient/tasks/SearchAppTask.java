package es.ucm.fdi.applistclient.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import es.ucm.fdi.applistclient.AppInfoActivity;
import es.ucm.fdi.applistclient.database.AppDatabase;
import es.ucm.fdi.applistclient.database.AppEntity;

public class SearchAppTask extends AsyncTask<AppEntity, Integer, AppEntity> {

    private Context context;
    private String package_name;
    private String category;
    private AppDatabase appDatabase;
    private AppInfoActivity activity;
    int numPeligrosos;

    public SearchAppTask(Context context, AppInfoActivity activity, String package_name){
        this.context = context;
        this.package_name = package_name;
        this.activity = activity;
    }

    @Override
    protected AppEntity doInBackground(AppEntity... appEntities) {
        this.appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        AppEntity app = appDatabase.appDao().loadApplication(this.package_name);
        if(app != null){
            this.category = app.getAppCategory();
            this.numPeligrosos = app.getNumPermisos();
        }else{
            app = null;
        }
        return app;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onPostExecute(AppEntity appEntity) {
        //Si tras insertar la aplicacion no es nula ya existe en la base de datos luego no buscamos en la playstore
        if(appEntity != null){
            //Toast.makeText(context.getApplicationContext(), "La app:" + package_name + " ya existe y su categoria es: " + category + ".", Toast.LENGTH_LONG).show();
            activity.onSearchAppResult(category, numPeligrosos);
        }else{
            //Si es nula en la bbdd la buscamos en la playstore.
            //Toast.makeText(context.getApplicationContext(), "La app: "+package_name+ " no existe se tiene que insertar.", Toast.LENGTH_LONG).show();
            //activity.searchCategory();
            activity.onSearchAppResult("", 0);
        }
    }

}
