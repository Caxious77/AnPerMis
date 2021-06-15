package es.ucm.fdi.applistclient.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import es.ucm.fdi.applistclient.AppInfoActivity;
import es.ucm.fdi.applistclient.database.AppDatabase;
import es.ucm.fdi.applistclient.database.CategoryFreEntity;
import es.ucm.fdi.applistclient.database.CategoryCriterioEntity;

public class SearchCategoryTask extends AsyncTask<CategoryFreEntity, Integer, CategoryFreEntity> {

    private Context context;
    private String category;
    private AppDatabase appDatabase;
    private AppInfoActivity activity;
    private CategoryCriterioEntity op = null;

    public SearchCategoryTask(Context context, AppInfoActivity activity, String category){
        this.context = context;
        this.category = category;
        this.activity = activity;
    }


    @Override
    protected CategoryFreEntity doInBackground(CategoryFreEntity... categoryFreEntities) {
        this.appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        op = appDatabase.categoryOpDao().loadCategory(category);
        CategoryFreEntity fre = appDatabase.categoryFreDao().loadCategoryFrequency(category);
        return fre;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onPostExecute(CategoryFreEntity categoryFreEntity) {
        //Si tras buscar la categoria esta esta en la base de datos
        if(categoryFreEntity != null && op != null){
            //Toast.makeText(context.getApplicationContext(), "La categoria " + category + " y la primera frecuencia es: " + categoryFreEntity.getACCESS_COARSE_LOCATION(), Toast.LENGTH_LONG).show();
            activity.onSearchCategoryResult(categoryFreEntity, op);
        }else{
            //Si es nula en la bbdd la buscamos en la playstore.
            //Toast.makeText(context.getApplicationContext(), "La categoria "+ category + " no se ha encontrado.", Toast.LENGTH_LONG).show();
            activity.onSearchCategoryResult(categoryFreEntity, op);
        }
    }
}
