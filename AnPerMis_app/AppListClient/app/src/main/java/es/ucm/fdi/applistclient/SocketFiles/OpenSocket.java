package es.ucm.fdi.applistclient.SocketFiles;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import es.ucm.fdi.applistclient.MainActivity;
import es.ucm.fdi.applistclient.R;

public class OpenSocket implements Runnable {

    //Atributos que definen las caracteristicas del socket
    private String SERVER_IP;
    private int SERVER_PORT;
    private Socket socket;

    //Atributos que definen los Buffer de lectura y escritura del socket
    private PrintWriter output;
    private BufferedReader input;

    private MainActivity activity;

    //Atributos que definen el certificado que se utilizara para la comunicacion segura
    private static final String CERTIFICATE_TYPE = "X509";
    private static final String DEFAULT_TLS_VERSION = "TLSv1.2";
    private final int certificate;

    //Contexto de la aplicacion para comunicaciones con la interfaz principal
    private Context context;

    /*TAG DE LA CLASE PARA LOS LOGS */
    private static final String TAG = "TAG_SocketInfo";

    public OpenSocket(String SERVER_IP, int SERVER_PORT, Context context, MainActivity activity){
        this.SERVER_IP = SERVER_IP;
        this.SERVER_PORT = SERVER_PORT;
        this.context = context;
        this.activity = activity;
        this.certificate = R.raw.certificadotfg;
    }

    @Override
    public void run() {
        //Simplemente llama a opne para abrir el socket
       open();
    }

    public BufferedReader getInput(){
        return this.input;
    }

    private void open(){
        try{
            Log.d(TAG, "Iniciando comunicacion con el servidor en "+SERVER_IP+":"+SERVER_PORT);
            //Comprobamos si el servidor esta en linea
            //if(testIp(SERVER_IP)){
                SSLSocketFactory sslSocketFactory = getSSLSocketFactory();
                socket = sslSocketFactory.createSocket(SERVER_IP, SERVER_PORT);
                output = new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))), true);
                output.println("enviartabla");
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d(TAG, "Conectado a "+SERVER_IP+":"+SERVER_PORT);
                activity.setOutput(output);
                //Solo pueden recivirse mensajes si el servidor se puede alcanzar
                new Thread(new RecieveMessage(SERVER_IP, SERVER_PORT, input, context)).start();

            //}else{
                //socket = null;
            //}
        } catch (UnknownHostException e) {
            //e.printStackTrace();
            Log.d(TAG, "La IP especificada es incorrecta.");
        } catch (IOException e) {
            e.printStackTrace();
            //Log.d(TAG, "La entrada o salida del socket ha fallado");
        }
    }

    //Metodos para introducir el certificado
    private KeyStore getUpdatedKeyStore() {
        KeyStore keyStore = null;
        InputStream is = null;
        try {
            //Obtenemos la instancia del almacen de clves
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //Abrimos el recurso del certificado incluido en la aplicacion
            is = context.getResources().openRawResource(certificate);
            CertificateFactory certificateFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE);
            Certificate ca;
            //Generamos el certificado para introducirlo en el almacen de calves
            ca = certificateFactory.generateCertificate(is);
            keyStore.load(null,null);
            keyStore.setCertificateEntry("certTFG",ca);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (java.security.cert.CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return keyStore;
    }

    private SSLContext getSSLContext(TrustManagerFactory trustManagerFactory) {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance(DEFAULT_TLS_VERSION);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    private TrustManagerFactory getTrustManagerFactory(KeyStore trustedCertificate) {
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustedCertificate);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return trustManagerFactory;
    }


    public SSLSocketFactory getSSLSocketFactory() {

        final KeyStore keyStore = getUpdatedKeyStore();
        final TrustManagerFactory trustManagerFactory = getTrustManagerFactory(keyStore);
        final SSLContext sslContext = getSSLContext(trustManagerFactory);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        return sslSocketFactory;
    }

    //Metodo que comprueba si el servidor esta en linea
   private boolean testIp(String ip) throws UnknownHostException, IOException{
        boolean ipState = false;
        InetAddress test = InetAddress.getByName(ip);
        Log.d(TAG, "Enviando PING a "+ip);
        if(test.isReachable(500)){
            Log.d(TAG, ip+" esta Activo");
            ipState = true;
        }else{
            Log.d(TAG, ip+" esta Inactivo");
        }
        return ipState;
    }

    //Metodo que cierra el socket creado
    public void closeSocket(){
        new Thread(new CloseSocket(socket)).start();
    }

}
