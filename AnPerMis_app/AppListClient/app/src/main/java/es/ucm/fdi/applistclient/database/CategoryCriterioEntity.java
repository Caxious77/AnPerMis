package es.ucm.fdi.applistclient.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categoryCriterio")
public class CategoryCriterioEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "category")
    private String categoryName;
    @NonNull
    @ColumnInfo(name = "ACCEPT_HANDOVER")
    private double ACCEPT_HANDOVER;
    @NonNull
    @ColumnInfo(name = "ACCESS_BACKGROUND_LOCATION")
    private double ACCESS_BACKGROUND_LOCATION;
    @NonNull
    @ColumnInfo(name = "ACCESS_COARSE_LOCATION")
    private double ACCESS_COARSE_LOCATION;
    @NonNull
    @ColumnInfo(name = "ACCESS_FINE_LOCATION")
    private double ACCESS_FINE_LOCATION;
    @NonNull
    @ColumnInfo(name = "ACCESS_MEDIA_LOCATION")
    private double ACCESS_MEDIA_LOCATION;
    @NonNull
    @ColumnInfo(name = "ACTIVITY_RECOGNITION")
    private double ACTIVITY_RECOGNITION;
    @NonNull
    @ColumnInfo(name = "ADD_VOICEMAIL")
    private double ADD_VOICEMAIL;
    @NonNull
    @ColumnInfo(name = "ANSWER_PHONE_CALLS")
    private double ANSWER_PHONE_CALLS;
    @NonNull
    @ColumnInfo(name = "BODY_SENSORS")
    private double BODY_SENSORS;
    @NonNull
    @ColumnInfo(name = "CALL_PHONE")
    private double CALL_PHONE;
    @NonNull
    @ColumnInfo(name = "CAMERA")
    private double CAMERA;
    @NonNull
    @ColumnInfo(name = "GET_ACCOUNTS")
    private double GET_ACCOUNTS;
    @NonNull
    @ColumnInfo(name = "PROCESS_OUTGOING_CALLS")
    private double PROCESS_OUTGOING_CALLS;
    @NonNull
    @ColumnInfo(name = "READ_CALENDAR")
    private double READ_CALENDAR;
    @NonNull
    @ColumnInfo(name = "READ_CALL_LOG")
    private double READ_CALL_LOG;
    @NonNull
    @ColumnInfo(name = "READ_CONTACTS")
    private double READ_CONTACTS;
    @NonNull
    @ColumnInfo(name = "READ_EXTERNAL_STORAGE")
    private double READ_EXTERNAL_STORAGE;
    @NonNull
    @ColumnInfo(name = "READ_PHONE_NUMBERS")
    private double READ_PHONE_NUMBERS;
    @NonNull
    @ColumnInfo(name = "READ_PHONE_STATE")
    private double READ_PHONE_STATE;
    @NonNull
    @ColumnInfo(name = "READ_SMS")
    private double READ_SMS;
    @NonNull
    @ColumnInfo(name = "RECIVE_MMS")
    private double RECIVE_MMS;
    @NonNull
    @ColumnInfo(name = "RECIVE_SMS")
    private double RECIVE_SMS;
    @NonNull
    @ColumnInfo(name = "RECIVE_WAP_PUSH")
    private double RECIVE_WAP_PUSH;
    @NonNull
    @ColumnInfo(name = "RECORD_AUDIO")
    private double RECORD_AUDIO;
    @NonNull
    @ColumnInfo(name = "SEND_SMS")
    private double SEND_SMS;
    @NonNull
    @ColumnInfo(name = "USE_SIP")
    private double USE_SIP;
    @NonNull
    @ColumnInfo(name = "WRITE_CALL_LOG")
    private double WRITE_CALL_LOG;
    @NonNull
    @ColumnInfo(name = "WRITE_CONTACTS")
    private double WRITE_CONTACTS;
    @NonNull
    @ColumnInfo(name = "WRITE_EXTERNAL_STORAGE")
    private double WRITE_EXTERNAL_STORAGE;

    public CategoryCriterioEntity(){}

    public CategoryCriterioEntity(String categoryName, double ACCEPT_HANDOVER, double ACCESS_BACKGROUND_LOCATION,
                                  double ACCESS_COARSE_LOCATION, double ACCESS_FINE_LOCATION, double ACCESS_MEDIA_LOCATION,
                                  double ACTIVITY_RECOGNITION, double ADD_VOICEMAIL, double ANSWER_PHONE_CALLS, double BODY_SENSORS,
                                  double CALL_PHONE, double CAMERA, double GET_ACCOUNTS, double PROCESS_OUTGOING_CALLS, double READ_CALENDAR,
                                  double READ_CALL_LOG, double READ_CONTACTS, double READ_EXTERNAL_STORAGE, double READ_PHONE_NUMBERS,
                                  double READ_PHONE_STATE, double READ_SMS, double RECIVE_MMS, double RECIVE_SMS, double RECIVE_WAP_PUSH,
                                  double RECORD_AUDIO, double SEND_SMS, double USE_SIP, double WRITE_CALL_LOG, double WRITE_CONTACTS,
                                  double WRITE_EXTERNAL_STORAGE){
        this.categoryName = categoryName;
        this.ACCEPT_HANDOVER = ACCEPT_HANDOVER;
        this.ACCESS_BACKGROUND_LOCATION = ACCESS_BACKGROUND_LOCATION;
        this.ACCESS_COARSE_LOCATION = ACCESS_COARSE_LOCATION;
        this.ACCESS_FINE_LOCATION = ACCESS_FINE_LOCATION;
        this.ACCESS_MEDIA_LOCATION = ACCESS_MEDIA_LOCATION;
        this.ACTIVITY_RECOGNITION = ACTIVITY_RECOGNITION;
        this.ADD_VOICEMAIL = ADD_VOICEMAIL;
        this.ANSWER_PHONE_CALLS = ANSWER_PHONE_CALLS;
        this.BODY_SENSORS = BODY_SENSORS;
        this.CALL_PHONE = CALL_PHONE;
        this.CAMERA = CAMERA;
        this.GET_ACCOUNTS = GET_ACCOUNTS;
        this.PROCESS_OUTGOING_CALLS = PROCESS_OUTGOING_CALLS;
        this.READ_CALENDAR = READ_CALENDAR;
        this.READ_CALL_LOG = READ_CALL_LOG;
        this.READ_CONTACTS = READ_CONTACTS;
        this.READ_EXTERNAL_STORAGE = READ_EXTERNAL_STORAGE;
        this.READ_PHONE_NUMBERS = READ_PHONE_NUMBERS;
        this.READ_PHONE_STATE = READ_PHONE_STATE;
        this.READ_SMS = READ_SMS;
        this.RECIVE_MMS = RECIVE_MMS;
        this.RECIVE_SMS = RECIVE_SMS;
        this.RECIVE_WAP_PUSH = RECIVE_WAP_PUSH;
        this.RECORD_AUDIO = RECORD_AUDIO;
        this.SEND_SMS = SEND_SMS;
        this.USE_SIP = USE_SIP;
        this.WRITE_CALL_LOG = WRITE_CALL_LOG;
        this.WRITE_CONTACTS = WRITE_CONTACTS;
        this.WRITE_EXTERNAL_STORAGE = WRITE_EXTERNAL_STORAGE;
    }

    /* Metodos GET de la clase */
    public String getCategoryName(){
        return this.categoryName;
    }

    public double getWRITE_EXTERNAL_STORAGE(){
        return this.WRITE_EXTERNAL_STORAGE;
    }

    public double getREAD_EXTERNAL_STORAGE(){
        return this.READ_EXTERNAL_STORAGE;
    }

    public double getACCESS_COARSE_LOCATION(){
        return this.ACCESS_COARSE_LOCATION;
    }

    public double getACCESS_FINE_LOCATION(){
        return this.ACCESS_FINE_LOCATION;
    }

    public double getACCEPT_HANDOVER() {
        return ACCEPT_HANDOVER;
    }

    public double getACCESS_BACKGROUND_LOCATION() {
        return ACCESS_BACKGROUND_LOCATION;
    }

    public double getACCESS_MEDIA_LOCATION() {
        return ACCESS_MEDIA_LOCATION;
    }

    public double getACTIVITY_RECOGNITION() {
        return ACTIVITY_RECOGNITION;
    }

    public double getADD_VOICEMAIL() {
        return ADD_VOICEMAIL;
    }

    public double getANSWER_PHONE_CALLS() {
        return ANSWER_PHONE_CALLS;
    }

    public double getBODY_SENSORS() {
        return BODY_SENSORS;
    }

    public double getCALL_PHONE() {
        return CALL_PHONE;
    }

    public double getCAMERA() {
        return CAMERA;
    }

    public double getGET_ACCOUNTS() {
        return GET_ACCOUNTS;
    }

    public double getPROCESS_OUTGOING_CALLS() {
        return PROCESS_OUTGOING_CALLS;
    }

    public double getREAD_CALENDAR() {
        return READ_CALENDAR;
    }

    public double getREAD_CALL_LOG() {
        return READ_CALL_LOG;
    }

    public double getREAD_CONTACTS() {
        return READ_CONTACTS;
    }

    public double getREAD_PHONE_NUMBERS() {
        return READ_PHONE_NUMBERS;
    }

    public double getREAD_PHONE_STATE() {
        return READ_PHONE_STATE;
    }

    public double getREAD_SMS() {
        return READ_SMS;
    }

    public double getRECIVE_MMS() {
        return RECIVE_MMS;
    }

    public double getRECIVE_SMS() {
        return RECIVE_SMS;
    }

    public double getRECIVE_WAP_PUSH() {
        return RECIVE_WAP_PUSH;
    }

    public double getRECORD_AUDIO() {
        return RECORD_AUDIO;
    }


    public double getSEND_SMS() {
        return SEND_SMS;
    }

    public double getUSE_SIP() {
        return USE_SIP;
    }

    public double getWRITE_CALL_LOG() {
        return WRITE_CALL_LOG;
    }

    public double getWRITE_CONTACTS() {
        return WRITE_CONTACTS;
    }

    /* Metodos SET de la clase */

    public void setACCEPT_HANDOVER(double ACCEPT_HANDOVER) {
        this.ACCEPT_HANDOVER = ACCEPT_HANDOVER;
    }

    public void setACCESS_BACKGROUND_LOCATION(double ACCESS_BACKGROUND_LOCATION) {
        this.ACCESS_BACKGROUND_LOCATION = ACCESS_BACKGROUND_LOCATION;
    }

    public void setACCESS_COARSE_LOCATION(double ACCESS_COARSE_LOCATION) {
        this.ACCESS_COARSE_LOCATION = ACCESS_COARSE_LOCATION;
    }

    public void setACCESS_FINE_LOCATION(double ACCESS_FINE_LOCATION) {
        this.ACCESS_FINE_LOCATION = ACCESS_FINE_LOCATION;
    }

    public void setACCESS_MEDIA_LOCATION(double ACCESS_MEDIA_LOCATION) {
        this.ACCESS_MEDIA_LOCATION = ACCESS_MEDIA_LOCATION;
    }

    public void setACTIVITY_RECOGNITION(double ACTIVITY_RECOGNITION) {
        this.ACTIVITY_RECOGNITION = ACTIVITY_RECOGNITION;
    }

    public void setADD_VOICEMAIL(double ADD_VOICEMAIL) {
        this.ADD_VOICEMAIL = ADD_VOICEMAIL;
    }

    public void setANSWER_PHONE_CALLS(double ANSWER_PHONE_CALLS) {
        this.ANSWER_PHONE_CALLS = ANSWER_PHONE_CALLS;
    }

    public void setBODY_SENSORS(double BODY_SENSORS) {
        this.BODY_SENSORS = BODY_SENSORS;
    }

    public void setCALL_PHONE(double CALL_PHONE) {
        this.CALL_PHONE = CALL_PHONE;
    }

    public void setCAMERA(double CAMERA) {
        this.CAMERA = CAMERA;
    }

    public void setCategoryName(@NonNull String categoryName) {
        this.categoryName = categoryName;
    }

    public void setGET_ACCOUNTS(double GET_ACCOUNTS) {
        this.GET_ACCOUNTS = GET_ACCOUNTS;
    }

    public void setPROCESS_OUTGOING_CALLS(double PROCESS_OUTGOING_CALLS) {
        this.PROCESS_OUTGOING_CALLS = PROCESS_OUTGOING_CALLS;
    }

    public void setREAD_CALENDAR(double READ_CALENDAR) {
        this.READ_CALENDAR = READ_CALENDAR;
    }

    public void setREAD_CALL_LOG(double READ_CALL_LOG) {
        this.READ_CALL_LOG = READ_CALL_LOG;
    }

    public void setREAD_CONTACTS(double READ_CONTACTS) {
        this.READ_CONTACTS = READ_CONTACTS;
    }

    public void setREAD_EXTERNAL_STORAGE(double READ_EXTERNAL_STORAGE) {
        this.READ_EXTERNAL_STORAGE = READ_EXTERNAL_STORAGE;
    }

    public void setREAD_PHONE_NUMBERS(double READ_PHONE_NUMBERS) {
        this.READ_PHONE_NUMBERS = READ_PHONE_NUMBERS;
    }

    public void setREAD_PHONE_STATE(double READ_PHONE_STATE) {
        this.READ_PHONE_STATE = READ_PHONE_STATE;
    }

    public void setREAD_SMS(double READ_SMS) {
        this.READ_SMS = READ_SMS;
    }

    public void setRECIVE_MMS(double RECIVE_MMS) {
        this.RECIVE_MMS = RECIVE_MMS;
    }

    public void setRECIVE_SMS(double RECIVE_SMS) {
        this.RECIVE_SMS = RECIVE_SMS;
    }

    public void setRECIVE_WAP_PUSH(double RECIVE_WAP_PUSH) {
        this.RECIVE_WAP_PUSH = RECIVE_WAP_PUSH;
    }

    public void setRECORD_AUDIO(double RECORD_AUDIO) {
        this.RECORD_AUDIO = RECORD_AUDIO;
    }

    public void setSEND_SMS(double SEND_SMS) {
        this.SEND_SMS = SEND_SMS;
    }

    public void setUSE_SIP(double USE_SIP) {
        this.USE_SIP = USE_SIP;
    }

    public void setWRITE_CALL_LOG(double WRITE_CALL_LOG) {
        this.WRITE_CALL_LOG = WRITE_CALL_LOG;
    }

    public void setWRITE_CONTACTS(double WRITE_CONTACTS) {
        this.WRITE_CONTACTS = WRITE_CONTACTS;
    }

    public void setWRITE_EXTERNAL_STORAGE(double WRITE_EXTERNAL_STORAGE) {
        this.WRITE_EXTERNAL_STORAGE = WRITE_EXTERNAL_STORAGE;
    }

    //Devueleve el valor asociado a cada una de las columnas de permiso
    public double getPorcentaje(String p){
        switch (p){
            case "android.permission.ACCEPT_HANDOVER": return getACCEPT_HANDOVER();
            case "android.permission.ACCESS_BACKGROUND_LOCATION": return getACCESS_BACKGROUND_LOCATION();
            case "android.permission.ACCESS_COARSE_LOCATION": return getACCESS_COARSE_LOCATION();
            case "android.permission.ACCESS_FINE_LOCATION": return getACCESS_FINE_LOCATION();
            case "android.permission.ACCESS_MEDIA_LOCATION": return getACCESS_MEDIA_LOCATION();
            case "android.permission.ACTIVITY_RECOGNITION": return getACTIVITY_RECOGNITION();
            case "android.permission.ADD_VOICEMAIL": return getADD_VOICEMAIL();
            case "android.permission.ANSWER_PHONE_CALLS": return getANSWER_PHONE_CALLS();
            case "android.permission.BODY_SENSORS": return getBODY_SENSORS();
            case "android.permission.CALL_PHONE": return getCALL_PHONE();
            case "android.permission.CAMERA": return getCAMERA();
            case "android.permission.GET_ACCOUNTS": return getGET_ACCOUNTS();
            case "android.permission.PROCESS_OUTGOING_CALLS": return getPROCESS_OUTGOING_CALLS();
            case "android.permission.READ_CALENDAR": return getREAD_CALENDAR();
            case "android.permission.EAD_CALL_LOG": return getREAD_CALL_LOG();
            case "android.permission.READ_CONTACTS": return getREAD_CONTACTS();
            case "android.permission.READ_EXTERNAL_STORAGE": return getREAD_EXTERNAL_STORAGE();
            case "android.permission.READ_PHONE_NUMBERS": return getREAD_PHONE_NUMBERS();
            case "android.permission.READ_PHONE_STATE": return getREAD_PHONE_STATE();
            case "android.permission.READ_SMS": return getREAD_SMS();
            case "android.permission.RECIVE_MMS": return getRECIVE_MMS();
            case "android.permission.RECIVE_SMS": return getRECIVE_SMS();
            case "android.permission.RECIVE_WAP_PUSH": return getRECIVE_WAP_PUSH();
            case "android.permission.RECORD_AUDIO": return getRECORD_AUDIO();
            case "android.permission.SEND_SMS": return getSEND_SMS();
            case "android.permission.USE_SIP": return getUSE_SIP();
            case "android.permission.WRITE_CALL_LOG": return getWRITE_CALL_LOG();
            case "android.permission.WRITE_CONTACTS": return getWRITE_CONTACTS();
            case "android.permission.WRITE_EXTERNAL_STORAGE": return getWRITE_EXTERNAL_STORAGE();
            default: return 0;
        }
    }

    /* Initial data, Datos que se van a precargar en la bbdd durante el primer arranque de la aplciacion */
    static final String[] OPINIONS = {
            "ART_AND_DESIGN;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;1;0;0;0;0;0;0;1;0;0;0;0;1",
            "AUTO_AND_VEHICLES;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "BEAUTY;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "BOOKS_AND_REFERENCE;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "BUSINESS;0;0;0;0;0;0;0;0;0;0;1;1;0;0;0;0;1;0;0;0;0;0;0;1;0;0;0;0;1",
            "COMICS;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "COMMUNICATION;0;0;1;1;0;0;0;0;0;0;1;1;0;0;0;1;1;1;1;0;0;0;0;1;0;0;0;1;1",
            "DATING;0;0;0;0;0;0;0;0;0;0;1;1;0;0;0;0;1;0;0;0;0;0;0;1;0;0;0;0;1",
            "EDUCATION;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;1;0;0;0;0;1",
            "ENTERTAINMENT;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "EVENTS;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "FINANCE;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "FOOD_AND_DRINK;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "HOUSE_AND_HOME;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;1;0;0;0;0;0;0;1;0;0;0;0;1",
            "LIBRARIES_AND_DEMO;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "LIFESTYLE;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "MEDICAL;0;0;0;0;0;0;0;0;1;0;1;0;0;0;0;0;1;0;0;0;0;0;0;1;0;0;0;0;1",
            "MUSIC_AND_AUDIO;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;1;0;0;0;0;1",
            "NEWS_AND_MAGAZINES;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "PARENTING;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0",
            "PRODUCTIVITY;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "PERSONALIZATION;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "PHOTOGRAPHY;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "SHOPPING;0;0;0;0;0;0;0;0;0;0;1;1;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "SOCIAL;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;1;1;0;1;0;0;0;0;1;0;0;0;1;1",
            "SPORTS;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "TOOLS;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "TRAVEL_AND_LOCAL;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "VIDEO_PLAYERS;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0;0;1",
            "WEATHER;0;0;1;1;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_ACTION;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_ADVENTURE;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_ARCADE;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_BOARD;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_CARD;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_CASINO;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_CASUAL;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_EDUCATIONAL;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_MUSIC;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_PUZZLE;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_RACING;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_ROLE_PLAYING;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_SIMULATION;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_SPORTS;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_STRATEGY;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_TRIVIA;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "GAME_WORD;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
            "HEALTH_AND_FITNESS;0;1;1;1;0;1;0;0;1;0;0;1;0;0;0;0;0;0;1;0;0;0;0;0;0;0;0;0;0",
            "MAPS_AND_NAVIGATION;0;1;1;1;0;1;0;0;0;0;0;1;0;0;0;0;0;0;1;0;0;0;0;1;0;0;0;0;0",
            "SIN INFORMACION;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0",
    };

}
