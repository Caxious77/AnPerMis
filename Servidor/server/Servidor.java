package server;



import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;


import javax.net.ssl.SSLServerSocketFactory;


import funcionalidad.Controller;
import funcionalidad.Main;

public class Servidor  extends Thread {
	
    private Controller ctrl;
    
	
	public Servidor(Controller ctrl) throws IOException{
		
		this.ctrl = ctrl;
	} //Se usa el constructor para servidor de Conexion

	
	
	
	
    public void run()//Método para iniciar el servidor
    {
		
				
	try {
		File archivo = new File("certificadoTFG.jks");
		if(archivo.exists())
			System.out.println(Main.FechayHora()+"certificadoTFG.jks existe");
		else
			System.out.println(Main.FechayHora()+"certificadoTFG.jks NO existe");
		
		System.setProperty("javax.net.ssl.keyStore","certificadoTFG.jks");
		System.setProperty("javax.net.ssl.keyStorePassword","mallorca2017");


		SSLServerSocketFactory sslserversocketfactory =(SSLServerSocketFactory)SSLServerSocketFactory.getDefault();

		ServerSocket sslserversocket = sslserversocketfactory.createServerSocket(1234);
	
			while (true) {
			System.out.println(Main.FechayHora()+"Esperando..."); // Esperando conexión

			 // Accept comienza el socket y espera una conexión desde un cliente
			ClienteAceptado tunel = new ClienteAceptado(sslserversocket.accept(), ctrl);// se ejecuta un hilo para un solo
																		// cliente
			tunel.start();
			}
		} catch (Exception e) {
			System.out.println(Main.FechayHora()+e.getMessage());
		}
	}

			
		

}

