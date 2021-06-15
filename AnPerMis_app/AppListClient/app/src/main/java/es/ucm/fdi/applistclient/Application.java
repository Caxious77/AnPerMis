package es.ucm.fdi.applistclient;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import es.ucm.fdi.applistclient.database.CategoryFreEntity;
import es.ucm.fdi.applistclient.database.CategoryCriterioEntity;

public class Application {

    private String name;
    private String packageName;
    private final String [] permisos;
    private String category;
    private String installDate;
    private Drawable appIcon;
    private String version;
    private int dangId;
    private Diccionarios diccionarios;
    private Context context;

    //Numero de permisos peligrosos que tiene la aplicacion this
    private int totalPeligro=0;

    //Porcentaje que determina si un permiso es comun o no.
    private double PORCENTAJE = 0.75;
    private double PORCENTAJE_ORANG = 0.51;
    private int PORCENTAJE_PERM = 60;

    //Para crear los mensajes de advertencia de los permisos
    private boolean rojo_criterio= false, rojo_freq = false, naranja = false;
    //Para contar los permisos abusivos de una aplicacion
    int contAbusivos = 0;

   /* public Application(String name, String packageName, long time, String [] permisos, Drawable appIcon, String version, Context context, Diccionarios diccionarios){
         this.name = name;
        this.packageName = packageName;
        this.category = "SIN INFORMACION";
        this.installDate = installDateParser(time);
        this.permisos = permisos;
        this.appIcon = appIcon;
        this.version = version;
        this.context = context;
        this.diccionarios = diccionarios;
    }*/
    public Application(PackageManager manager, PackageInfo info, Context context, Diccionarios diccionarios){
        this.name = manager.getApplicationLabel(info.applicationInfo).toString().replace(';',' ').replace(',', ' ');
        this.packageName = info.packageName;
        this.category = "SIN INFORMACION";
        this.installDate = installDateParser(info.firstInstallTime);
        this.permisos = info.requestedPermissions;
        this.appIcon =  manager.getApplicationIcon(info.applicationInfo);
        this.version = info.versionName;
        this.context = context;
        this.diccionarios = diccionarios;
    }

    /* METODOS SET */

    public void setCategory(String category){
        this.category = category;
    }

    /* METODOS GET */

    public String getName(){
        return this.name;
    }

    public String getPackageName(){
        return this.packageName;
    }

    public String getCategory(){
        return this.category;
    }

    public String getTraduccion(){
        return diccionarios.getCategoryTraductor().get(this.category);
    }

    public String getInstallDate(){
        return this.installDate;
    }

    public Drawable getAppIcon(){
        return this.appIcon;
    }

    public String [] getPermisos(){
        return this.permisos;
    }

    public Set<String> getPeligrosos(){
        return diccionarios.getPeligrosos();
    }

    public String getVersion(){
        return this.version;
    }

    public int getDangId(){
        return this.dangId;
    }

    /* METODOS SET */

    public void setDangId(int dangId) {
        this.dangId = dangId;
    }

    public void setTotalPeligro() {
        this.totalPeligro = numDan(this.permisos);
    }

    /* METODOS PRIVADOS QUE PARSEAN LA INFORMACION */
    private String installDateParser(long time){
        Date date = new Date(time);
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String ret = formater.format(date);
        return ret;
    }

    public int numDan(String [] lPerm){
        int ret = 0;
        if(lPerm != null){
            for(int i = 0; i < lPerm.length; i++){
                if(diccionarios.getPeligrosos().contains(lPerm[i])) {
                    ret++;
                }
            }
        }
        return ret;
    }

    public String getAppUploadMessage(){
        //Establecemos el nombre de la app y el nombre del paquete tal y como pide el server.
        String s = this.name + "," + this.packageName + "," + this.category;
        int i = 0;
        if(permisos != null){
            if(this.permisos.length != 0){
                if(diccionarios.getPeligrosos().contains(this.permisos[i])){
                    s = s + "," + this.permisos[i];
                }
                i++;
            }
            while(i < this.permisos.length){
                if(diccionarios.getPeligrosos().contains(this.permisos[i])){
                    s = s + "," + this.permisos[i];
                }
                i++;
            }
        }

        return s;
    }

    public int getTotalPeligro(){
        return totalPeligro;
    }

    //Obtiene una lista con los permisos peligrosos ya analizados y clasificados
    public List<Permission> getDangerousPermissions(String [] permisos, CategoryFreEntity categoryFreEntity, CategoryCriterioEntity categoryCriterioEntity){
        //Obtenemos el arbol que contiene los permisos peligrosos
        //TODO: esto se podria implementar directamente sobre uno de los hashMap
        Set<String> peligrosos = this.getPeligrosos();
        List<Permission> lista = new LinkedList<>();
        int imageId = 0;
        contAbusivos = 0;
        //Para cada uno de los permisos de la aplicacion actual, comprobamos si es peligroso y lo analizamos con los resultados
        //de la base de datos
        if(permisos != null){
            for(int i = 0; i < permisos.length; i++){
                //boolean rojo_criterio= false, rojo_freq = false;
                rojo_criterio= false;
                rojo_freq = false;
                naranja = false;
                if(peligrosos.contains(permisos[i])){
                    //Obtiene el porcentaje de su categoria
                    double porcentaje = categoryFreEntity.getPorcentaje(permisos[i]);
                    double criterio = categoryCriterioEntity.getPorcentaje(permisos[i]);
                    imageId = getImageId(porcentaje, criterio);
                    String msg = getAlertMessage(rojo_freq, rojo_criterio, naranja, permisos[i], porcentaje);
                    lista.add(new Permission(permisos[i], diccionarios.getPermInfoName().get(permisos[i]), context.getString(diccionarios.getPermInfoMessage().get(permisos[i])), diccionarios.getPermDescription().get(permisos[i]),
                            diccionarios.getPermInfoImage().get(permisos[i]), imageId, porcentaje,msg));
                }
            }
        }
        //EStablecemos la imagen de advertencia a la aplicacion dependiendo del numero de permisos que no deberian aparecer.
        updateDangId(contAbusivos);
        return lista;
    }

    private int getImageId(double porcentaje, double criterio){
        int imageId = 0;
        if(porcentaje >= PORCENTAJE && (int)criterio == 1){
            imageId = R.drawable.advertencia_amarillo;
            rojo_freq = true;
            rojo_criterio = true;
        }else{
            if(porcentaje >= PORCENTAJE){
                rojo_freq = true;
            }
            if((int)criterio == 1){
                rojo_criterio = true;
            }
            contAbusivos++;
            if(porcentaje >= PORCENTAJE_ORANG && (int)criterio == 1){
                imageId = R.drawable.advertencia_naranja_one;
                naranja = true;
            }else{
                imageId = R.drawable.advertencia_rojo;
            }
        }
        return imageId;
    }

    //Actualiza la imagen del analisis global de permisos de la aplicacion
    private void updateDangId(int contAbusivos){
        //Comrpobamos el porcentaje de permisos que son peligrosos
        if(totalPeligro != 0){
            int percent =  (contAbusivos*100) / totalPeligro;
            if(percent >= PORCENTAJE_PERM){
                this.dangId = R.drawable.advertencia_rojo;
            }else{
                this.dangId = R.drawable.advertencia_amarillo;
            }
        }else{
            this.dangId = R.drawable.correcto;
        }
    }

    public String getAlertMessage(boolean freq, boolean opi, boolean orange, String permiso, double porcentaje){
        String ret = "";
        //Si el permiso ha pasado el control de la frecuencia y el control de la opinion el mensaje es bueno
        if(freq && opi){
            ret = ret + context.getString(R.string.amarillo_ini) + " " + this.getTraduccion() + " " + context.getString(R.string.amarillo_fin);
        }else{
            //No ha pasado ni el control de frecuencias ni el control de la categoria
            if(!freq && !opi){
                ret = ret + context.getString(R.string.rojo_ini ) + context.getString(R.string.rojo_freq_ini) + " " + this.getTraduccion() + " " + context.getString(R.string.rojo_ambos) + context.getString(R.string.recomendacion) + " " + diccionarios.getPermInfoGroup().get(permiso) + ".";
            }else{
                //Sino comprobamos cual ha sido el filtro que no ha pasado
                if(freq){
                    ret = ret + context.getString(R.string.rojo_ini) + context.getString(R.string.rojo_freq_ini) + " " + this.getTraduccion() + " " + context.getString(R.string.rojo_freq_fin) + context.getString(R.string.recomendacion) + " " + diccionarios.getPermInfoGroup().get(permiso)+ ".";
                }else{
                    if(orange){
                        ret = ret + context.getString(R.string.naranja_ini) + (int)(porcentaje*100) + "% " + context.getString(R.string.naranja_fin) + context.getString(R.string.rojo_opi_ini) + " " + this.getTraduccion() + " " + context.getString(R.string.rojo_opi_fin) + context.getString(R.string.recomendacion_naranja) + " " + diccionarios.getPermInfoGroup().get(permiso)+ ".";
                    }else{
                        ret = ret + context.getString(R.string.rojo_ini) + context.getString(R.string.rojo_opi_ini) + " " + this.getTraduccion() + " " + context.getString(R.string.rojo_opi_fin) + context.getString(R.string.recomendacion) + " " + diccionarios.getPermInfoGroup().get(permiso)+ ".";
                    }
                }
            }
        }
        return ret;
    }
}
