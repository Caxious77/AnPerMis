package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


import funcionalidad.Controller;
import funcionalidad.Main;

public class ClienteAceptado extends Thread{

	private Socket cliente;
	private Controller ctrl;
	
	public ClienteAceptado(Socket cliente,Controller ctrl) {
		this.cliente = cliente;
		this.ctrl = ctrl;
		
	}
	
	public void run() {
		String mensajeServidor,texto = "";
		try
        {
        	
            System.out.println(Main.FechayHora()+"Cliente en línea " + cliente.getInetAddress());
            

            //Se obtiene el flujo de salida del cliente para enviarle mensajes
            PrintWriter salidaCliente1 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream())),true);

            //Se le envía un mensaje al cliente usando su flujo de salida
            //salidaCliente1.println("Petición recibida y aceptada");

            //Se obtiene el flujo entrante desde el cliente
            BufferedReader entrada1 = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            mensajeServidor = entrada1.readLine();
            texto = mensajeServidor.replaceAll("\\s","");
            
            while(mensajeServidor  != null && !texto.equalsIgnoreCase("finconexion")) //Se leen hasta que el cliente lo decida
            {
            	System.out.println(Main.FechayHora()+texto);
            	if(texto.equalsIgnoreCase("enviartabla")) {
            		//El servidor le enviara la tabla actualizada al cliente
            		
            		for(String row:ctrl.sendFreqTabla()){
            			salidaCliente1.println(row);
            		}
            		salidaCliente1.println("finenviotablafreqs");


            		 
            		for(String row:ctrl.sendTcriterios()){ 
            			//System.out.println(Main.FechayHora()+row);
            			salidaCliente1.println(row);
            		}
            		salidaCliente1.println("finenvio");
            		
            	}else if(texto.equalsIgnoreCase("subirapps")) {
            		
            		comprobarApps(salidaCliente1, entrada1);
            	}else {
            		
            		salidaCliente1.println("Lo siento, su peticion no es valida");
            	}
            	
            	
                mensajeServidor = entrada1.readLine();
                if(mensajeServidor != null)
                texto = mensajeServidor.replaceAll("\\s","");
            }

            System.out.println(Main.FechayHora()+"Fin de la conexión " + cliente.getInetAddress());

            cliente.close();//Se finaliza la conexión con el cliente
        }
        catch (Exception e)
        {
            System.out.println(Main.FechayHora()+e.getMessage());
        }
		
	}
	
	private void comprobarApps(PrintWriter salida, BufferedReader entrada) throws IOException {
		String mensaje;
		List<String> aplicaciones = new ArrayList<String>();
		mensaje = entrada.readLine().replaceAll("\\s","");
		while(!mensaje.equalsIgnoreCase("finsubida")) {
			System.out.println(Main.FechayHora()+mensaje);
			aplicaciones.add(mensaje);
			mensaje = entrada.readLine().replaceAll("\\s","");
		}
		if(!aplicaciones.isEmpty())
			ctrl.comprobarApps(aplicaciones);
		
	}
}
