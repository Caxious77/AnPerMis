package es.ucm.fdi.applistclient.tasks;

import android.content.Context;
import android.os.AsyncTask;

import es.ucm.fdi.applistclient.database.AppDatabase;
import es.ucm.fdi.applistclient.database.CategoryFreEntity;

public class InsertCategoryFreTask extends AsyncTask<CategoryFreEntity, Integer, CategoryFreEntity> {

    private Context context;
    private String category;
    private AppDatabase appDatabase;
    private CategoryFreEntity categoryFreEntity;

    public InsertCategoryFreTask(Context context, CategoryFreEntity categoryFreEntity){
        this.context = context;
        this.categoryFreEntity = categoryFreEntity;
        this.category = categoryFreEntity.getCategoryName();
    }

    @Override
    protected CategoryFreEntity doInBackground(CategoryFreEntity... categoryFreEntities) {
        this.appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        //Dada una entidad CategoryFreEntity ya creada la inserta en la base de datos utilizando la politica OnconflictStrategy.REPLACE
        appDatabase.categoryFreDao().insertCategoryFrequency(categoryFreEntity);
        return categoryFreEntity;
    }

    //Metodo que se ejecuta tras la inserccion en la base de datos, utilizado para depuracion
    @Override
    protected void onPostExecute(CategoryFreEntity categoryFreEntity) {
        //Si tras insertar la aplicacion es nula
        if(categoryFreEntity == null){
            //Toast.makeText(context.getApplicationContext(), "La categoria: " + category + " se inserto correctamente.", Toast.LENGTH_LONG).show();
        }else{
            //Toast.makeText(context.getApplicationContext(), "La categoria: " + category + " ya existe.", Toast.LENGTH_LONG).show();
        }
    }
}
