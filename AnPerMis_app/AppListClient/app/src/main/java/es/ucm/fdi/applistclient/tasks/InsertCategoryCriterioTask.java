package es.ucm.fdi.applistclient.tasks;

import android.content.Context;
import android.os.AsyncTask;

import es.ucm.fdi.applistclient.database.AppDatabase;
import es.ucm.fdi.applistclient.database.CategoryCriterioEntity;

public class InsertCategoryCriterioTask extends AsyncTask<CategoryCriterioEntity, Integer, CategoryCriterioEntity> {

    private Context context;
    private String category;
    private AppDatabase appDatabase;
    private CategoryCriterioEntity categoryCriterioEntity;

    public InsertCategoryCriterioTask(Context context, CategoryCriterioEntity categoryCriterioEntity){
        this.categoryCriterioEntity = categoryCriterioEntity;
        this.category = categoryCriterioEntity.getCategoryName();
        this.context = context;
    }

    @Override
    protected CategoryCriterioEntity doInBackground(CategoryCriterioEntity... categoryOpEntities) {
        this.appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        //Dada una entidad CategoryOpEntity ya creada la inserta en la base de datos utilizando la politica OnconflictStrategy.REPLACE
        appDatabase.categoryOpDao().insertCategory(categoryCriterioEntity);
        return categoryCriterioEntity;
    }

    //Metodo que se ejecuta tras la inserccion en la base de datos, utilizado para depuracion
    @Override
    protected void onPostExecute(CategoryCriterioEntity categoryCriterioEntity) {
        //Si tras insertar la aplicacion es nula
        if(categoryCriterioEntity == null){
            //Toast.makeText(context.getApplicationContext(), "La categoria: " + category + " OPINION.", Toast.LENGTH_LONG).show();
        }else{
            //Toast.makeText(context.getApplicationContext(), "La categoria: " + category + " OPINION.", Toast.LENGTH_LONG).show();
        }
    }
}
