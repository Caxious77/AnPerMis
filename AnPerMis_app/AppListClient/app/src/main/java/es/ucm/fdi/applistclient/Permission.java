package es.ucm.fdi.applistclient;

public class Permission {

    private int permissionImage;
    private String name;
    private String completeName;
    private String description;
    private int circleImage;
    private double porcentaje;
    private String msg;
    private boolean rojo_opi;
    private boolean rojo_freq;
    private boolean naranja;
    private String original;

    //public Permission(String id, String name, String description, String completeName, int permissionImage, int circleImage, double porcentaje, boolean rojo_freq, boolean rojo_opi){
    public Permission(String id, String name, String description, String completeName, int permissionImage, int circleImage, double porcentaje, String msg){
        this.original = id;
        this.name = name;
        this.description = description;
        this.completeName = completeName;
        this.permissionImage = permissionImage;
        this.circleImage = circleImage;
        this.porcentaje = porcentaje;
        this.msg = msg;
        //this.rojo_freq = rojo_freq;
        //this.rojo_opi = rojo_opi;
        //this.naranja = naranja;
    }

    /* Metodos get de la clase */

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getOriginal() {
        return original;
    }

    public String getCompleteName() {
        return completeName;
    }

    public int getPermissionImage() {
        return permissionImage;
    }

    public int getCircleImage() {
        return circleImage;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isRojo_freq() {
        return rojo_freq;
    }

    public boolean isRojo_opi(){
        return rojo_opi;
    }

    public boolean isNaranja(){
        return naranja;
    }


    /* Metodos set de la clase */

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public void setPermissionImage(int permissionImage) {
        this.permissionImage = permissionImage;
    }

    public void setCircleImage(int circleImage) {
        this.circleImage = circleImage;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

}
