package es.ucm.fdi.applistclient.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AppEntity.class, CategoryFreEntity.class, CategoryCriterioEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    //Instancia de la base de datos, siguiendo el patron de diseño Singleton.
    private static AppDatabase sInstance;
    //Nombre de la base de datos
    public static final String DB_NAME = "app-db";

    //Dao, para acceder a la informacion de la base de datos.
    public abstract AppDao appDao();
    public abstract CategoryFreDao categoryFreDao();
    public abstract CategoryCriterioDao categoryOpDao();

    //Metodo que crea o devuelve una instancia de la base de datos en el caso de que ya exxista.
    public static AppDatabase getInstance(Context context){
        //Si la instancia no existe se crea, si no se devuelve la instancia ya creada
        if(sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME).build();
                    //Inicializamos la base de datos con datos precargados
                    sInstance.populateInitialData();
                }
            }
        }
        return sInstance;
    }

    //Metodo que inicializa la base de datos con datos precargados por si no existiera conexion con el servidor en el
    //arranque de la aplicacion. (Servidor fuera de linea, se utilizan datos por defector).
    private void populateInitialData(){
        runInTransaction(new Runnable() {
            @Override
            public void run() {
                CategoryCriterioEntity categoryCriterioEntity = new CategoryCriterioEntity();
                CategoryFreEntity categoryFreEntity = new CategoryFreEntity();
                //Leemos las frecuencias y categorias que se van a precargar y las cargaos en la base de datos.
                for(int i = 0; i < categoryFreEntity.FREQUENCIES.length; i++){
                    String fre = categoryFreEntity.FREQUENCIES[i];
                    CategoryFreEntity freInsert = parsearStrFre(fre);
                    String op = categoryCriterioEntity.OPINIONS[i];
                    CategoryCriterioEntity opInsert = parsearStrOp(op);
                    categoryFreDao().insertCategoryFrequency(freInsert);
                    categoryOpDao().insertCategory(opInsert);
                }
            }
        });
    }

    //Funcion que parsea una cadena que define las frecuencias de una categoria de la base de datos.
    private CategoryFreEntity parsearStrFre(String msg){
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

    //Funcion que parsea una cadena que define un criterio de permisos de una categoria de la base de datos
    private CategoryCriterioEntity parsearStrOp(String msg){
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
