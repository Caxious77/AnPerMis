package es.ucm.fdi.applistclient;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CategoryLoader extends AsyncTaskLoader <String> {

    /* ENTERO DE DESPLAZAMIENTO */
    private int offset = 44;

    /* Nombre del paquete para buscar en la playstore */
    private String appName;

    /*TAG DE LA CLASE PARA LOS LOGS */
    private static final String TAG = "TAG_CategroryLoader";

    public CategoryLoader(@NonNull Context context, String appName) {
        super(context);
        this.appName = appName;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        String s = getResponse();
        return fromResponse(s);
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    /* Dada una respuesta en HTML de la playstore la parsea y devuelve la categoria */
    private String fromResponse(String s){
        String category = "";
        if(!s.equals("SIN INFORMACION")){
            int pos = s.indexOf("itemprop=\"genre\"");
            if(pos != -1){
                pos = pos + this.offset;
                category = "";
                while(s.charAt(pos) != '"'){
                    category = category + s.charAt(pos);
                    ++pos;
                }
            }
        }else{
            category = s;
        }

        return category;
    }

    /* Metodo que realiza la conexion a la playstore y la almacena en un string */
    private String getResponse(){

        final String BASE_URL = "https://play.google.com/store/apps/details?id="+appName;

        HttpsURLConnection conn = null;
        String contentAsString = null;
        InputStream is = null;

        try{
            Uri builtURI = Uri.parse(BASE_URL).buildUpon().build();
            URL requestURL = new URL(builtURI.toString());
            Log.d(TAG, builtURI.toString());

            conn = (HttpsURLConnection) requestURL.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            //Cambiado, solo establece la categoria de la app si la respuesta es 200 es decir OK
            //En caso contrario establece sin informacion en la categoria
            if(response == 200){
                is = conn.getInputStream();
                contentAsString = convertIsToString(is);
            }else{
                contentAsString = "SIN INFORMACION";
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                conn.disconnect();
            }
            if(is != null){
                try{
                    is.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        Log.d(TAG, contentAsString);

        return contentAsString;
    }

    /* Metodo que convierte la respuesta en un string */
    private String convertIsToString(InputStream stream){

        StringBuilder builder = null;
        BufferedReader reader = null;

        try{
            builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while((line = reader.readLine()) != null){
                builder.append(line+"\n");
            }
            if(builder.length() == 0){
                return null;
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try{
                    reader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        return builder.toString();
    }
}
