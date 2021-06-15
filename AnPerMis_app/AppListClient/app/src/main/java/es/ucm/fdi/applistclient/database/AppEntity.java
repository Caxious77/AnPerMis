package es.ucm.fdi.applistclient.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import es.ucm.fdi.applistclient.Application;

@Entity(tableName = "Application")
public class AppEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "appPackage")
    private String appPackage;

    @NonNull
    @ColumnInfo(name = "appCategory")
    private String appCategory;

    @NonNull
    @ColumnInfo(name = "numPermisos")
    private int numPermisos;

    public AppEntity(String appPackage, String appCategory, int numPermisos){
        this.appPackage = appPackage;
        this.appCategory = appCategory;
        this.numPermisos = numPermisos;
    }

    /* Metodos GET de la clase */
    public String getAppPackage(){
        return this.appPackage;
    }

    public String getAppCategory(){
        return this.appCategory;
    }

    public int getNumPermisos() {
        return this.numPermisos;
    }

    /* Metodos SET de la clase */

    public void setAppPackage(String appPackage){
        this.appPackage = appPackage;
    }

    public void setAppCategory(String appCategory) {
        this.appCategory = appCategory;
    }

    public void setNumPermisos(int numPermisos) {
        this.numPermisos = numPermisos;
    }
}
