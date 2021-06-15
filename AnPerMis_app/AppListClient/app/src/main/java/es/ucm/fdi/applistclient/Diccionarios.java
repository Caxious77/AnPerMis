package es.ucm.fdi.applistclient;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class Diccionarios {

    //Para los permisos de la base de datos
    private HashMap<String, String> permInfoName;
    private HashMap<String, String> permInfoGroup;
    private HashMap<String, String> permDescription;
    private HashMap<String, Integer> permInfoImage;
    private HashMap<String, Integer> permInfoMessage;
    private Set<String> peligrosos;
    private HashMap<String, String> categoryTraductor;

    public Diccionarios(){
        //Inicializamos las estructuras de datos necesarias
        inicializarPermDescription();
        inicializarPermInfoImage();
        inicializarPermInfoName();
        inicializarPeligrosos();
        inicializarCategorias();
        inicializarPermGroup();
        inicializarPermInfoMessage();
    }

    /* METODOS GET */

    public HashMap<String, Integer> getPermInfoImage() {
        return permInfoImage;
    }

    public HashMap<String, String> getPermInfoName() {
        return permInfoName;
    }

    public Set<String> getPeligrosos() {
        return peligrosos;
    }

    public HashMap<String, String> getCategoryTraductor() {
        return categoryTraductor;
    }

    public HashMap<String, String> getPermDescription() {
        return permDescription;
    }

    public HashMap<String, String> getPermInfoGroup() {
        return permInfoGroup;
    }

    public HashMap<String, Integer> getPermInfoMessage() {
        return permInfoMessage;
    }

    private void inicializarPermInfoName(){
        permInfoName = new HashMap<>();
        permInfoName.put("android.permission.WRITE_EXTERNAL_STORAGE", "ESCRIBIR ALMACENAMIENTO EXTERNO");
        permInfoName.put("android.permission.READ_EXTERNAL_STORAGE", "LEER ALMACENAMIENTO EXTERNO ");
        permInfoName.put("android.permission.ACCESS_COARSE_LOCATION", "ACCESO A UBICACIÓN APROXIMADA");
        permInfoName.put("android.permission.ACCESS_FINE_LOCATION", "ACCESO A UBICACIÓN DETALLADA ");
        permInfoName.put("android.permission.ACCESS_BACKGROUND_LOCATION", "ACCESO A UBICACION EN SEGUNDO PLANO");
        permInfoName.put("android.permission.ACCESS_MEDIA_LOCATION", "ACCESO A HISTORIAL DE UBICACIONES");
        permInfoName.put("android.permission.ACTIVITY_RECOGNITION", "ROCONOCER ACTIVIDADES");
        permInfoName.put("android.permission.ACCEPT_HANDOVER", "ACEPTAR TRANSFERENCIA DE LLAMADA");
        permInfoName.put("android.permission.CALL_PHONE", "REALIZAR LLAMADAS");
        permInfoName.put("android.permission.READ_PHONE_STATE", "LEER ESTADO DEL TELÉFONO");
        permInfoName.put("android.permission.READ_PHONE_NUMBERS", "LEER NÚMEROS DE TELÉFONO");
        permInfoName.put("android.permission.READ_CALL_LOG", "LEER EL REGISTRO DE LLAMADAS");
        permInfoName.put("android.permission.WRITE_CALL_LOG", "ESCRIBIR EL REGISTRO DE LLAMADAS");
        permInfoName.put("android.permission.ADD_VOICEMAIL", "AÑADIR VOICEMAIL");
        permInfoName.put("android.permission.USE_SIP", "USAR SIP");
        permInfoName.put("android.permission.PROCESS_OUTGOING_CALLS", "PROCESAR LLAMADAS");
        permInfoName.put("android.permission.ANSWER_PHONE_CALLS", "CONTESTAR LLAMADAS");
        permInfoName.put("android.permission.SEND_SMS", "ENVIAR SMS");
        permInfoName.put("android.permission.RECIVE_SMS", "RECIBIR SMS");
        permInfoName.put("android.permission.READ_SMS", "LEER SMS");
        permInfoName.put("android.permission.RECIVE_MMS", "ENVIAR_MMS");
        permInfoName.put("android.permission.RECIVE_WAP_PUSH", "RECIBIR WAP PUSH");
        permInfoName.put("android.permission.READ_CONTACTS", "LEER CONTACTOS");
        permInfoName.put("android.permission.WRITE_CONTACTS", "ESCRIBIR CONTACTOS");
        permInfoName.put("android.permission.GET_ACCOUNTS", "ACCEDER A CUENTAS");
        permInfoName.put("android.permission.READ_CALENDAR", "LEER CALENDARIO");
        permInfoName.put("android.permission.CAMERA", "CÁMARA");
        permInfoName.put("android.permission.RECORD_AUDIO", "GRABAR AUDIO");
        permInfoName.put("android.permission.BODY_SENSORS", "SENSORES CORPORALES");
    }

    private void inicializarPermGroup(){
        permInfoGroup = new HashMap<>();
        permInfoGroup.put("android.permission.WRITE_EXTERNAL_STORAGE", "ALMACENAMIENTO");
        permInfoGroup.put("android.permission.READ_EXTERNAL_STORAGE", "ALMACENAMIENTO");
        permInfoGroup.put("android.permission.ACCESS_COARSE_LOCATION", "UBICACIÓN");
        permInfoGroup.put("android.permission.ACCESS_FINE_LOCATION", "UBICACIÓN");
        permInfoGroup.put("android.permission.ACCESS_BACKGROUND_LOCATION", "UBICACIÓN");
        permInfoGroup.put("android.permission.ACCESS_MEDIA_LOCATION", "UBICACIÓN");
        permInfoGroup.put("android.permission.ACTIVITY_RECOGNITION", "ACTIVIDAD FÍSICA");
        permInfoGroup.put("android.permission.ACCEPT_HANDOVER", "TELÉFONO");
        permInfoGroup.put("android.permission.CALL_PHONE", "TELÉFONO");
        permInfoGroup.put("android.permission.READ_PHONE_STATE", "TELÉFONO");
        permInfoGroup.put("android.permission.READ_PHONE_NUMBERS", "TELÉFONO");
        permInfoGroup.put("android.permission.READ_CALL_LOG", "REGISTROS DE LLAMADAS");
        permInfoGroup.put("android.permission.WRITE_CALL_LOG", "REGISTROS DE LLAMADAS");
        permInfoGroup.put("android.permission.ADD_VOICEMAIL", "AÑADIR VOICEMAIL");
        permInfoGroup.put("android.permission.USE_SIP", "TELÉFONO");
        permInfoGroup.put("android.permission.PROCESS_OUTGOING_CALLS", "TELÉFONO");
        permInfoGroup.put("android.permission.ANSWER_PHONE_CALLS", "TELÉFONO");
        permInfoGroup.put("android.permission.SEND_SMS", "SMS");
        permInfoGroup.put("android.permission.RECIVE_SMS", "SMS");
        permInfoGroup.put("android.permission.READ_SMS", "SMS");
        permInfoGroup.put("android.permission.RECIVE_MMS", "SMS");
        permInfoGroup.put("android.permission.RECIVE_WAP_PUSH", "SMS");
        permInfoGroup.put("android.permission.READ_CONTACTS", "CONTACTOS");
        permInfoGroup.put("android.permission.WRITE_CONTACTS", "CONTACTOS");
        permInfoGroup.put("android.permission.GET_ACCOUNTS", "CONTACTOS");
        permInfoGroup.put("android.permission.READ_CALENDAR", "CALENDARIO");
        permInfoGroup.put("android.permission.CAMERA", "CÁMARA");
        permInfoGroup.put("android.permission.RECORD_AUDIO", "MICRÓFONO");
        permInfoGroup.put("android.permission.BODY_SENSORS", "SENSORES CORPORALES");
    }

    private void inicializarPermDescription(){
        permDescription = new HashMap<>();
        permDescription.put("android.permission.WRITE_EXTERNAL_STORAGE", "permission.WRITE_EXTERNAL_STORAGE");
        permDescription.put("android.permission.READ_EXTERNAL_STORAGE", "permission.READ_EXTERNAL_STORAGE");
        permDescription.put("android.permission.ACCESS_COARSE_LOCATION", "permission.ACCESS_COARSE_LOCATION");
        permDescription.put("android.permission.ACCESS_FINE_LOCATION", "permission.ACCESS_FINE_LOCATION");
        permDescription.put("android.permission.ACCESS_BACKGROUND_LOCATION", "permission.ACCESS_BACKGROUND_LOCATION");
        permDescription.put("android.permission.ACCESS_MEDIA_LOCATION", "permission.ACCESS_MEDIA_LOCATION");
        permDescription.put("android.permission.ACTIVITY_RECOGNITION", "permission.ACTIVITY_RECOGNITION");
        permDescription.put("android.permission.ACCEPT_HANDOVER", "permission.ACCEPT_HANDOVER");
        permDescription.put("android.permission.CALL_PHONE", "permission.CALL_PHONE");
        permDescription.put("android.permission.READ_PHONE_STATE", "permission.READ_PHONE_STATE");
        permDescription.put("android.permission.READ_PHONE_NUMBERS", "permission.READ_PHONE_NUMBERS");
        permDescription.put("android.permission.READ_CALL_LOG", "permission.READ_CALL_LOG");
        permDescription.put("android.permission.WRITE_CALL_LOG", "permission.WRITE_CALL_LOG");
        permDescription.put("android.permission.ADD_VOICEMAIL", "permission.ADD_VOICEMAIL");
        permDescription.put("android.permission.USE_SIP", "permission.USE_SIP");
        permDescription.put("android.permission.PROCESS_OUTGOING_CALLS", "permission.PROCESS_OUTGOING_CALLS");
        permDescription.put("android.permission.ANSWER_PHONE_CALLS", "permission.ANSWER_PHONE_CALLS");
        permDescription.put("android.permission.SEND_SMS", "permission.SEND_SMS");
        permDescription.put("android.permission.RECIVE_SMS", "permission.RECIVE_SMS");
        permDescription.put("android.permission.READ_SMS", "permission.READ_SMS");
        permDescription.put("android.permission.RECIVE_MMS", "permission.RECIVE_MMS");
        permDescription.put("android.permission.RECIVE_WAP_PUSH", "permission.RECIVE_WAP_PUSH");
        permDescription.put("android.permission.READ_CONTACTS", "permission.READ_CONTACTS");
        permDescription.put("android.permission.WRITE_CONTACTS", "permission.WRITE_CONTACTS");
        permDescription.put("android.permission.GET_ACCOUNTS", "permission.GET_ACCOUNTS");
        permDescription.put("android.permission.READ_CALENDAR", "permission.READ_CALENDAR");
        permDescription.put("android.permission.CAMERA", "permission.CAMERA");
        permDescription.put("android.permission.RECORD_AUDIO", "permission.RECORD_AUDIO");
        permDescription.put("android.permission.BODY_SENSORS", "permission.BODY_SENSORS");
    }

    private void inicializarPermInfoMessage(){
        permInfoMessage = new HashMap<>();
        permInfoMessage.put("android.permission.WRITE_EXTERNAL_STORAGE", R.string.write_external_storage);
        permInfoMessage.put("android.permission.READ_EXTERNAL_STORAGE", R.string.read_external_storage);
        permInfoMessage.put("android.permission.ACCESS_COARSE_LOCATION", R.string.access_coarse);
        permInfoMessage.put("android.permission.ACCESS_FINE_LOCATION", R.string.access_fine);
        permInfoMessage.put("android.permission.ACCESS_BACKGROUND_LOCATION", R.string.access_background);
        permInfoMessage.put("android.permission.ACCESS_MEDIA_LOCATION", R.string.access_media);
        permInfoMessage.put("android.permission.ACTIVITY_RECOGNITION", R.string.activity_recognition);
        permInfoMessage.put("android.permission.ACCEPT_HANDOVER", R.string.accept_handover);
        permInfoMessage.put("android.permission.CALL_PHONE", R.string.call_phone);
        permInfoMessage.put("android.permission.READ_PHONE_STATE", R.string.read_phone_state);
        permInfoMessage.put("android.permission.READ_PHONE_NUMBERS", R.string.read_phone_numbers);
        permInfoMessage.put("android.permission.READ_CALL_LOG", R.string.read_call_log);
        permInfoMessage.put("android.permission.WRITE_CALL_LOG", R.string.write_call_log);
        permInfoMessage.put("android.permission.ADD_VOICEMAIL", R.string.add_voicemail);
        permInfoMessage.put("android.permission.USE_SIP", R.string.use_sip);
        permInfoMessage.put("android.permission.PROCESS_OUTGOING_CALLS", R.string.process_outgoing_calls);
        permInfoMessage.put("android.permission.ANSWER_PHONE_CALLS", R.string.answer_phone_calls);
        permInfoMessage.put("android.permission.SEND_SMS", R.string.send_sms);
        permInfoMessage.put("android.permission.RECIVE_SMS", R.string.recive_sms);
        permInfoMessage.put("android.permission.READ_SMS", R.string.read_sms);
        permInfoMessage.put("android.permission.RECIVE_MMS", R.string.recive_mms);
        permInfoMessage.put("android.permission.RECIVE_WAP_PUSH", R.string.recive_wap_push);
        permInfoMessage.put("android.permission.READ_CONTACTS", R.string.read_contacts);
        permInfoMessage.put("android.permission.WRITE_CONTACTS", R.string.write_contacts);
        permInfoMessage.put("android.permission.GET_ACCOUNTS", R.string.get_accounts);
        permInfoMessage.put("android.permission.READ_CALENDAR", R.string.read_calendar);
        permInfoMessage.put("android.permission.CAMERA", R.string.camera);
        permInfoMessage.put("android.permission.RECORD_AUDIO", R.string.record_audio);
        permInfoMessage.put("android.permission.BODY_SENSORS", R.string.body_sensors);
    }

    private void inicializarPermInfoImage(){
        permInfoImage = new HashMap<>();
        permInfoImage.put("android.permission.WRITE_EXTERNAL_STORAGE", R.drawable.write_external_storage);
        permInfoImage.put("android.permission.READ_EXTERNAL_STORAGE", R.drawable.read_external_storage);
        permInfoImage.put("android.permission.ACCESS_COARSE_LOCATION", R.drawable.access_coarse_location);
        permInfoImage.put("android.permission.ACCESS_FINE_LOCATION", R.drawable.accesss_fine_location);
        permInfoImage.put("android.permission.ACCESS_BACKGROUND_LOCATION", R.drawable.access_background_location);
        permInfoImage.put("android.permission.ACCESS_MEDIA_LOCATION", R.drawable.access_media_location);
        permInfoImage.put("android.permission.ACTIVITY_RECOGNITION", R.drawable.activity_recognition);
        permInfoImage.put("android.permission.ACCEPT_HANDOVER", R.drawable.accept_handover);
        permInfoImage.put("android.permission.CALL_PHONE", R.drawable.call_phone);
        permInfoImage.put("android.permission.READ_PHONE_STATE", R.drawable.read_phone_state);
        permInfoImage.put("android.permission.READ_PHONE_NUMBERS", R.drawable.read_phone_numbers);
        permInfoImage.put("android.permission.READ_CALL_LOG", R.drawable.read_call_log);
        permInfoImage.put("android.permission.WRITE_CALL_LOG", R.drawable.write_call_log);
        permInfoImage.put("android.permission.ADD_VOICEMAIL", R.drawable.add_voicemail);
        permInfoImage.put("android.permission.USE_SIP", R.drawable.use_sip);
        permInfoImage.put("android.permission.PROCESS_OUTGOING_CALLS", R.drawable.process_outgoing_cals);
        permInfoImage.put("android.permission.ANSWER_PHONE_CALLS", R.drawable.answer_phone_calls);
        permInfoImage.put("android.permission.SEND_SMS", R.drawable.send_sms);
        permInfoImage.put("android.permission.RECIVE_SMS", R.drawable.recive_sms);
        permInfoImage.put("android.permission.READ_SMS", R.drawable.read_sms);
        permInfoImage.put("android.permission.RECIVE_MMS", R.drawable.recive_mms);
        permInfoImage.put("android.permission.RECIVE_WAP_PUSH", R.drawable.recive_wap_push);
        permInfoImage.put("android.permission.READ_CONTACTS", R.drawable.read_contacts);
        permInfoImage.put("android.permission.WRITE_CONTACTS", R.drawable.write_contacts);
        permInfoImage.put("android.permission.GET_ACCOUNTS", R.drawable.get_acounts);
        permInfoImage.put("android.permission.READ_CALENDAR", R.drawable.read_calendar);
        permInfoImage.put("android.permission.CAMERA", R.drawable.camera);
        permInfoImage.put("android.permission.RECORD_AUDIO", R.drawable.record_audio);
        permInfoImage.put("android.permission.BODY_SENSORS", R.drawable.body_sensors);
    }

    private void inicializarPeligrosos(){
        peligrosos = new TreeSet<>();
        peligrosos.add("android.permission.WRITE_EXTERNAL_STORAGE"); //
        peligrosos.add("android.permission.READ_EXTERNAL_STORAGE");
        peligrosos.add("android.permission.ACCESS_COARSE_LOCATION");
        peligrosos.add("android.permission.ACCESS_FINE_LOCATION");
        peligrosos.add("android.permission.ACCESS_BACKGROUND_LOCATION");
        peligrosos.add("android.permission.ACCESS_MEDIA_LOCATION");
        peligrosos.add("android.permission.ACTIVITY_RECOGNITION");
        peligrosos.add("android.permission.ACCEPT_HANDOVER");
        peligrosos.add("android.permission.CALL_PHONE");
        peligrosos.add("android.permission.READ_PHONE_STATE");
        peligrosos.add("android.permission.READ_PHONE_NUMBERS");
        peligrosos.add("android.permission.READ_CALL_LOG");
        peligrosos.add("android.permission.WRITE_CALL_LOG");
        peligrosos.add("android.permission.ADD_VOICEMAIL");
        peligrosos.add("android.permission.USE_SIP");
        peligrosos.add("android.permission.PROCESS_OUTGOING_CALLS");
        peligrosos.add("android.permission.ANSWER_PHONE_CALLS");
        peligrosos.add("android.permission.SEND_SMS");
        peligrosos.add("android.permission.RECIVE_SMS");
        peligrosos.add("android.permission.READ_SMS");
        peligrosos.add("android.permission.RECIVE_MMS");
        peligrosos.add("android.permission.RECIVE_WAP_PUSH");
        peligrosos.add("android.permission.READ_CONTACTS");
        peligrosos.add("android.permission.WRITE_CONTACTS");
        peligrosos.add("android.permission.GET_ACCOUNTS");
        peligrosos.add("android.permission.READ_CALENDAR");
        peligrosos.add("android.permission.CAMERA");
        peligrosos.add("android.permission.RECORD_AUDIO");
        peligrosos.add("android.permission.BODY_SENSORS");
    }

    private void inicializarCategorias(){
        categoryTraductor = new HashMap<>();
        categoryTraductor.put("ART_AND_DESIGN","ARTE Y DISEÑO");
        categoryTraductor.put("AUTO_AND_VEHICLES","AUTOMOCIÓN");
        categoryTraductor.put("BEAUTY", "BELLEZA");
        categoryTraductor.put("BOOKS_AND_REFERENCE","LIBROS");
        categoryTraductor.put("BUSINESS", "EMPRESA");
        categoryTraductor.put("COMICS","CÓMICS");
        categoryTraductor.put("COMMUNICATION","COMUNICACIÓN");
        categoryTraductor.put("DATING","CITAS");
        categoryTraductor.put("EDUCATION","EDUCACIÓN");
        categoryTraductor.put("ENTERTAINMENT","ENTRETENIMIENTO");
        categoryTraductor.put("EVENTS","EVENTOS");
        categoryTraductor.put("FINANCE","FINANZAS");
        categoryTraductor.put("FOOD_AND_DRINK","COMER Y BEBER");
        categoryTraductor.put("HOUSE_AND_HOME","CASA Y HOGAR");
        categoryTraductor.put("LIBRARIES_AND_DEMO","BIBLIOTECAS Y DEMOS");
        categoryTraductor.put("LIFESTYLE","ESTILO DE VIDA");
        categoryTraductor.put("MEDICAL","MEDICINA");
        categoryTraductor.put("MUSIC_AND_AUDIO","MÚSICA Y AUDIO");
        categoryTraductor.put("NEWS_AND_MAGAZINES","NOTICIAS Y REVISTAS");
        categoryTraductor.put("PARENTING","SER PADRES");
        categoryTraductor.put("PRODUCTIVITY", "PRODUCTIVIDAD");
        categoryTraductor.put("PERSONALIZATION","PERSONALIZACIÓN");
        categoryTraductor.put("PHOTOGRAPHY","FOTOGRAFÍA");
        categoryTraductor.put("SHOPPING","COMPRAS");
        categoryTraductor.put("SOCIAL","SOCIAL");
        categoryTraductor.put("SPORTS", "DEPORTES");
        categoryTraductor.put("TOOLS","HERRAMIENTAS");
        categoryTraductor.put("TRAVEL_AND_LOCAL","VIAJES Y GUÍAS");
        categoryTraductor.put("VIDEO_PLAYERS","APLICACIONES DE VIDEO");
        categoryTraductor.put("WEATHER","TIEMPO");
        categoryTraductor.put("GAME_ACTION","JUEGOS DE ACCIÓN");
        categoryTraductor.put("GAME_ADVENTURE","JUEGOS DE AVENTURA");
        categoryTraductor.put("GAME_ARCADE","JUEGOS ARCADE");
        categoryTraductor.put("GAME_BOARD", "JUEGOS DE MESA");
        categoryTraductor.put("GAME_CARD","JUEGOS DE CARTAS");
        categoryTraductor.put("GAME_CASINO","JUEGOS DE CASINO");
        categoryTraductor.put("GAME_CASUAL","JUEGOS CASUAL");
        categoryTraductor.put("GAME_EDUCATIONAL","JUEGOS EDUCATIVOS");
        categoryTraductor.put("GAME_MUSIC","JUEGOS MUSICALES");
        categoryTraductor.put("GAME_PUZZLE","JUEGOS DE PUZLES");
        categoryTraductor.put( "GAME_RACING","JUEGOS DE CARRERAS");
        categoryTraductor.put("GAME_ROLE_PLAYING","JUEGOS DE ROL");
        categoryTraductor.put("GAME_SIMULATION","JUEGOS DE SIMULACIÓN");
        categoryTraductor.put("GAME_SPORTS","JUEGOS DE DEPORTE");
        categoryTraductor.put("GAME_STRATEGY","JUEGOS DE ESTRATEGIA");
        categoryTraductor.put("GAME_TRIVIA", "PREGUNTAS Y RESPUESTAS");
        categoryTraductor.put("GAME_WORD","JUEGOS DE PALABRAS");
        categoryTraductor.put("HEALTH_AND_FITNESS","SALUD Y BIENESTAR");
        categoryTraductor.put("MAPS_AND_NAVIGATION","MAPAS Y NAVEGACION");
        categoryTraductor.put("SIN INFORMACION","SIN INFORMACION");
    }
}
