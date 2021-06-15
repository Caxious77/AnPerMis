package es.ucm.fdi.applistclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class CategoryLoaderCallbacks implements LoaderManager.LoaderCallbacks<String> {

    /* Contexto de la aplicacion */
    private Context context;
    /* Objeto aplicacion para avisar al devolver la respuesta */
    private AppInfoActivity activity;

    public CategoryLoaderCallbacks(AppInfoActivity activity){
        this.context = activity;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String pName = "";
        if(args != null){
            pName= args.getString("package_name");
        }

        return new CategoryLoader(context, pName);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        activity.updateCategoryResult(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        //TODO: Este reset de momento no hace nada pero podria volver a realizar la carga de la categoria
    }
}
